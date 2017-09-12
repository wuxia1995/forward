//上传图片探测
function uploadPicDetect(obj) {
    // var img = document.getElementById("imgShowDetectDiv");
    for (var i = 0; i < num; i++) {
        if ($('#detect' + i).length > 0) {
            $('#detect' + i).remove();
        }
    }
    var attributeCheckedUpload = [];
    $('input[name="attribute"]:checked').each(function(){
        console.log($(this).val());
        attributeCheckedUpload.push($(this).val());
    });
    // attributeCheckedUpload.push("age")
    // attributeCheckedUpload.push("gender")
    // attributeCheckedUpload.push("emotions")
    var file = obj.files[0];
    // attributeChecked["photo"]=file
    var reader = new FileReader();
    reader.onload = function (e) {
        // img.src = e.target.result;
        $("#imgShowDetect").attr("src", e.target.result)
    }
    reader.readAsDataURL(file)

    var detectForm = new FormData();
    detectForm.append("photo", file);
    //清除input框的状态
    obj.value = "";
    for (var inx in attributeCheckedUpload) {
        detectForm.append(attributeCheckedUpload[inx], "true");
    }
    $.ajax({
        url: 'customer/detect-face',
        type: 'POST',
        // dataType: "json",
        data: detectForm,
        processData: false,
        contentType: false,
        async: true,
        success: function (data) {
            if (data == "") {
                $('#responseDetect').html("no face detect")
                $('#faceProperties').html("")
            }
            checkProperties(eval('(' + data + ')'));
            handleData($('#imgShowDetect').attr("src"), eval('(' + data + ')'))
        },
        error: function (data) {
            // if (data == "") {
            $('#responseDetect').html("no face detect")
            $('#faceProperties').html("")
            // }
            return false
        }
    });
}

$(document).ready(function () {

}); //ready


//定义全局变量num记录图片中人脸的个数
var num = 0;

function detectUrl() {
    $('#imgShowDetect').attr("src", $("#inputUrlDetect").val());
    detectReq($("#inputUrlDetect").val())
}

//url图片探测,并画人脸div
function detectReq(img) {
    for (var i = 0; i < num; i++) {
        if ($('#detect' + i).length > 0) {
            $('#detect' + i).remove();
        }
    }
    var attributeChecked = {};
    $('input[name="attribute"]:checked').each(function(){
        console.log($(this).val());
        attributeChecked[$(this).val()]="true";
    });
    attributeChecked["photo"] = img
    // attributeChecked["age"] = "true"
    // attributeChecked["gender"] = "true"
    // attributeChecked["emotions"] = "true"
    $.ajax({
        url: 'customer/detect-face',
        type: 'POST',
        // dataType: "json",
        data: attributeChecked,
        async: true,
        success: function (data) {
            if (data == "") {
                $('#responseDetect').html("no face detected")
                $('#faceProperties').html("")
                return false
            }
            localData = eval('(' + data + ')')
            checkProperties(localData);
            handleData(img, localData)
        },
        error: function (data) {
            $('#responseDetect').html("no face detected")
            $('#faceProperties').html("")
            // handleData(img, data)
        }
    });
}

//画人脸div
function handleData(imgContent, data) {
    // $('#responseDetect').removeClass("hidden")
    $('#responseDetect').html(syntaxHighlight(data))
    img = document.getElementById("imgShowDetect");
    imgDiv = document.getElementById("imgShowDetectDiv");
    img.src = imgContent
    // $('#imgShowDetect').attr("src",reqData);
    var imgSrc = $('#imgShowDetect').attr("src");
    getImageWidth(imgSrc, function (w, h) {
        // console.log({width: w, height: h});
        console.log({width: imgDiv.offsetWidth, height: imgDiv.offsetHeight});
        console.log({picwidth: img.naturalWidth, picheight: img.naturalWidth});

        hratew = img.naturalHeight / img.naturalWidth
        /*
        * 画图处理根据图片的尺寸分三种情况,分别是
        * 1.长宽比例是1;
        * 2.长宽比例大于1;
        * 3.长宽比例小于1;
        * */
        var widthRate = imgDiv.offsetWidth / img.naturalWidth
        var heightRate = imgDiv.offsetHeight / img.naturalHeight
        var dataObj = eval(data)
        var rect;
        var results = [];
        if (dataObj.hasOwnProperty("faces")) {
            rect = dataObj['faces'];
            for (var i = 0; i < rect.length; i++) {
                rect[i].x1 = rect[i].x1 * widthRate - 4
                rect[i].x2 = rect[i].x2 * widthRate - 4
                rect[i].y1 = rect[i].y1 * heightRate
                rect[i].y2 = rect[i].y2 * heightRate
                width = (rect[i].x2 - rect[i].x1) * 0.8
                height = (rect[i].y2 - rect[i].y1) * 0.9
                left = rect[i].x1 + width * 0.15
                divtop = rect[i].y1 + 2
                var result = new Result(width, height, left, divtop);
                results[i] = result;
                console.log(results);
            }
        }

        // if (heightRate == 1) {
        //     var widthRate = imgDiv.offsetWidth / img.naturalWidth
        //     var heightRate = imgDiv.offsetHeight / img.naturalHeight
        //     var dataObj = eval(data)
        //     var rect;
        //     var results = [];
        //     if (dataObj.hasOwnProperty("faces")) {
        //         rect = dataObj['faces'];
        //         for (var i = 0; i < rect.length; i++) {
        //             rect[i].x1 = rect[i].x1 * widthRate - 4
        //             rect[i].x2 = rect[i].x2 * widthRate - 4
        //             rect[i].y1 = rect[i].y1 * heightRate
        //             rect[i].y2 = rect[i].y2 * heightRate
        //             width = (rect[i].x2 - rect[i].x1) * 0.8
        //             height = (rect[i].y2 - rect[i].y1) * 0.9
        //             left = rect[i].x1 + width * 0.15
        //             divtop = rect[i].y1 + 2
        //             var result = new Result(width, height, left, divtop);
        //             results[i] = result;
        //             console.log(results);
        //         }
        //     }
        // }else if(hratew > 1){  //高度比宽度大于一时
        //     var widthRate = imgDiv.offsetWidth / img.naturalWidth
        //     var heightRate = imgDiv.offsetHeight / img.naturalHeight
        //     var dataObj = eval(data)
        //     var rect;
        //     var results = [];
        //     if (dataObj.hasOwnProperty("faces")) {
        //         rect = dataObj['faces'];
        //         for (var i = 0; i < rect.length; i++) {
        //             rect[i].x1 = rect[i].x1 * widthRate - 4
        //             rect[i].x2 = rect[i].x2 * widthRate - 4
        //             rect[i].y1 = rect[i].y1 * heightRate
        //             rect[i].y2 = rect[i].y2 * heightRate
        //             width = (rect[i].x2 - rect[i].x1) * 0.8
        //             height = (rect[i].y2 - rect[i].y1) * 0.9
        //             left = rect[i].x1 + width * 0.15
        //             divtop = rect[i].y1 + 2
        //             var result = new Result(width, height, left, divtop);
        //             results[i] = result;
        //             console.log(results);
        //         }
        //     }
        // }else{  //长宽比例小于1
        //     var widthRate = imgDiv.offsetWidth / img.naturalWidth
        //     var heightRate = imgDiv.offsetHeight / img.naturalHeight
        //     var dataObj = eval(data)
        //     var rect;
        //     var results = [];
        //     if (dataObj.hasOwnProperty("faces")) {
        //         rect = dataObj['faces'];
        //         for (var i = 0; i < rect.length; i++) {
        //             rect[i].x1 = rect[i].x1 * widthRate - 4
        //             rect[i].x2 = rect[i].x2 * widthRate - 4
        //             rect[i].y1 = rect[i].y1 * heightRate
        //             rect[i].y2 = rect[i].y2 * heightRate
        //             width = (rect[i].x2 - rect[i].x1) * 0.8
        //             height = (rect[i].y2 - rect[i].y1) * 0.9
        //             left = rect[i].x1 + width * 0.15
        //             divtop = rect[i].y1 + 2
        //             var result = new Result(width, height, left, divtop);
        //             results[i] = result;
        //             console.log(results);
        //         }
        //     }
        // }
        if (results) {
            for (var i = 0; i < num; i++) {
                if ($('#detect' + i).length > 0) {
                    $('#detect' + i).remove();
                }
            }
            num = results.length;
            for (var i = 0; i < results.length; i++) {
                $('#imgShowDetectDiv').prepend("<div id='detect" + i + "'><span style='color: #00c3bb' >"+(i+1)+"</span></div>");
                $('#detect' + i).css({
                    "position": "absolute",
                    "outline": "rgb(70, 171, 232) solid 2px",
                    "width": results[i].width,
                    "height": results[i].height,
                    "left": results[i].left,
                    "top": results[i].top
                })
            }


        }
    });
}

//检查人脸属性
function checkProperties(data) {

    faceData = data['faces']
    elementData = [];
    var status;
    for (var single in faceData) {
        var status = null;
        elementData.push("<p>第" + (parseInt(single) + 1) + "张人脸的属性</p>")
        // emotion=faceData[single]['emotions']
        // age=faceData[single]['age']
        // gender=faceData[single] ['gender']
        if (faceData[single]['age']) {
            status = true
            elementData.push("<p>年龄:" + Math.round(faceData[single]['age']) + "</p>");
        }
        if (faceData[single]['emotions']) {
            status = true
            elementData.push("<p>情绪:" + faceData[single]['emotions'] + "</p>");
        }
        if (faceData[single] ['gender']) {
            status = true
            elementData.push("<p>性别:" + faceData[single] ['gender'] + "</p>");
        }
    }
    if (status) {
        $('#faceProperties').html(elementData.join(""))
    } else {
        $('#faceProperties').html("")
    }
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
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
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
        return match;
    });
}