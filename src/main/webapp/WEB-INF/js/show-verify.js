//定义两个全局变量用于记录两个框的上传文件内容
var leftUploadFile
var rightUploadFile

function verifyUrl(id) {
    removeDiv()
    var img = new Image();
    img.src = $("#inputUrl" + id).val();
    verifyReq(img, id);
}

function uploadPic(obj, id) {
    removeDiv()
    var img = document.getElementById("imgShow" + id)
    var file = obj.files[0];
    if(!file){
        return false;
    }
    var reader = new FileReader();
    reader.onloadend = function (e) {
        console.log("成功读取....");
        img.src = e.target.result;
        if(id==1){
            leftUploadFile=file
        }
        if(id==2){
            rightUploadFile=file
        }
        //或者 img.src = this.result;  //e.target == this
    }
    reader.readAsDataURL(file)
    //上传文件时当前的图片内容是文件,对比的对象可能时文件或者url
    another=id==1?2:1
    var imgAno = document.getElementById("imgShow" + another)
    var formData = new FormData();
    if(imgAno.src.indexOf("data:image")==0){
        formData.append("photo1", file)
        if(id==1) {
            formData.append("photo2", rightUploadFile)
        }else if(id==2){
            formData.append("photo2", leftUploadFile)
        }else {
            return false
        }
    }else {
        formData.append("photo1", file)
        formData.append("photo2", imgAno.src)
    }

    $.ajax({
        url: '../n-tech/v0/verify',
        type: 'POST',
        dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        async: true,
        success: function (data) {

            console.log("success")
            readResData(data,id)
        },
        error:function (data) {
            removeDiv()
            img.src=""
            alert("no face")
            return false
        }
    });

}
function drawDiv(image,result,parentDiv){
    var widthRate = 400 / image.naturalWidth
    var heightRate = 400 / image.naturalHeight

    result.x1 = result.x1 * widthRate - 4
    result.x2 = result.x2 * widthRate - 4
    result.y1 = result.y1 * heightRate
    result.y2 = result.y2 * heightRate

    width = (result.x2 - result.x1) * 0.8
    height = (result.y2 - result.y1) * 0.9
    left = result.x1 + width * 0.15
    divtop = result.y1 + 2
    // var result = new Result(width, height, left, divtop);
    picId=image.id+"1"
    parentDiv.prepend("<div id="+picId + "></div>");
    // $('#picShow2').prepend("<div id=" + id + "></div>");
    $('#' + picId).css({
        "position": "absolute",
        "outline": "rgb(70, 171, 232) solid 2px",
        "width": width,
        "height": height,
        "left": left,
        "top": divtop
    })

}
function readResData(data,id) {
    removeDiv()
    //移除之前的画框
    // if ($('#imgShow11')) {
    //     $('#imgShow11').remove();
    // }
    // if ($('#imgShow21')) {
    //     $('#imgShow21').remove();
    // }

    // console.log({width: w, height: h});

    console.log(data);

    //读取返回的json数据
    var dataObj = eval(data);
    $('#reponse').html(syntaxHighlight(dataObj))
    var confidence = dataObj['results']['0']['confidence'];
    var val = dataObj['verified'];
    //第一个正方形框
    var rect1 = dataObj['results']['0']['bbox1'];
    //第二个正方形框
    var rect2 = dataObj['results']['0']['bbox2'];


    console.log(rect1);
    console.log(rect2);
    removeDiv();
    if(id==1){
        drawDiv(document.getElementById("imgShow1"),rect1,$('#picDiv1'))
        drawDiv(document.getElementById("imgShow2"),rect2,$('#picDiv2'))
    }else{
        drawDiv(document.getElementById("imgShow1"),rect2,$('#picDiv1'))
        drawDiv(document.getElementById("imgShow2"),rect1,$('#picDiv2'))
    }




    $("#result").val("");
    if (val == true && confidence >= 0.78) {
        $("#result").val("两张照片是同一张人脸");
    } else {
        $("#result").val("两张照片不是同一张人脸");
    }


    //
    // console.log("width:" + img.width)
    // console.log("height:" + img.height)
    // if(img.naturalWidth>400){

}

function removeDiv() {
    if ($('#imgShow11')) {
        $('#imgShow11').remove();
    }
    if ($('#imgShow21')) {
        $('#imgShow21').remove();
    }
}

$(document).ready(function () {
}); //ready
function verifyReq(img, id) {
    if ($('#imgShow11')) {
        $('#imgShow11').remove();
    }
    if ($('#imgShow21')) {
        $('#imgShow21').remove();
    }

    $("#imgShow" + id).attr("src", img.src);
    var notId=id==1?2:1


    var selfImg = document.getElementById("imgShow" + id)
    var anotherImg = document.getElementById("imgShow" + notId)
    var formData = new FormData();
    if(anotherImg.src.indexOf("data:image")==0){
        formData.append("photo1", selfImg.src)
        if(id==1) {
            formData.append("photo2", rightUploadFile)
        }else if(id==2){
            formData.append("photo2", leftUploadFile)
        }else {
            return false
        }
    }else {
        formData.append("photo1", selfImg.src)
        formData.append("photo2", anotherImg.src)
    }

    $.ajax({
        url: '../n-tech/v0/verify',
        type: 'POST',
        dataType: "json",
        data:formData,
        processData: false,
        contentType: false,
        // data: {
        //     "photo1": $("#imgShow"+id).attr("src"),
        //     "photo2": $("#imgShow"+notId).attr("src")
        // },
        async: true,
        success: function (data) {
            readResData(data,id)
        }
    });
}

function Result(width, height, left, top) {
    this.width = width;
    this.height = height;
    this.left = left;
    this.top = top;
}

function getImageWidth(url, callback) {
    var img = new Image();
    img.src = url;

    // 如果图片被缓存，则直接返回缓存数据
    if (img.complete) {
        callback(img.width, img.height);
    } else {
        // 完全加载完毕的事件
        img.onload = function () {
            callback(img.width, img.height);
        }
    }

}
function syntaxHighlight(json) {
    if (typeof json != 'string') {
        json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}