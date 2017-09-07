
//上传图片探测
function uploadPicDetect(obj) {
    // var img = document.getElementById("imgShowDetectDiv");
    var file = obj.files[0];
    var reader = new FileReader();
    reader.onload = function (e) {
        // img.src = e.target.result;
        $("#imgShowDetect").attr("src",e.target.result)
    }
    reader.readAsDataURL(file)

    var detectForm = new FormData();
    detectForm.append("photo",file);
    $.ajax({
        url: 'customer/detect-face',
        type: 'POST',
        dataType: "json",
        data: detectForm,
        processData: false,
        contentType: false,
        async: true,
        success: function (data) {
            handleData($('#imgShowDetect').attr("src"),data)
        },
        error: function (data) {
            alert("no face")
            return false
        }
    });
    //
    // var options = {
    //     url: "customer/detect-face",
    //     type: "post",
    //     dataType: "json",
    //     success: function (data) {
    //         handleData(img, data)
    //     },
    //     error: function (data) {
    //         alert(data + "出现错误")
    //     }
    // };
    // $("#jvForm").ajaxSubmit(options);
}

$(document).ready(function () {

}); //ready


//定义全局变量num记录图片中人脸的个数
var num = 0;
function detectUrl() {
    // var check = document.getElementById("inputUrlDetect");
    // console.log(check.tagName)
    detectReq($("#inputUrlDetect").val())
}
//url图片探测,并画人脸div
function detectReq(img) {
    // var reqData;
    // if (img.tagName == 'INPUT') {
    //     reqData = img.value;
    // } else if (img.tagName == 'IMG') {
    //     reqData = img.src;
    // } else {
    //     console.log(img.tagName + ":error tag");
    //     return false
    // }
    $.ajax({
        url: 'customer/detect-face',
        type: 'POST',
        dataType: "json",
        data: {photo: img},
        async: true,
        success: function (data) {
            handleData(img, data)
        }
    });
}
//画人脸div
function handleData(imgContent,data) {
    $('#responseDetect').removeClass("hidden")
    $('#responseDetect').html(syntaxHighlight(data))
    // var reqData;
    // if (eleType.tagName == 'INPUT') {
    //     reqData = eleType.value;
    // } else if (eleType.tagName == 'IMG') {
    //     reqData = eleType.src;
    // } else {
    //     console.log(eleType.tagName + ":error tag");
    //     return false
    // }
    //
    img = document.getElementById("imgShowDetect");
    imgDiv = document.getElementById("imgShowDetectDiv");
    img.src = imgContent
    // $('#imgShowDetect').attr("src",reqData);
    var imgSrc = $('#imgShowDetect').attr("src");
    getImageWidth(imgSrc, function (w, h) {
        console.log({width: w, height: h});


        for (var i = 0; i < num; i++) {
            if ($('#detect' + i).length > 0) {
                $('#detect' + i).remove();
            }
        }


        // console.log("width:" + img.width)
        // console.log("height:" + img.height)
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
        if (results) {
            for (var i = 0; i < num; i++) {
                if ($('#detect' + i).length > 0) {
                    $('#detect' + i).remove();
                }
            }
            num = results.length;
            for (var i = 0; i < results.length; i++) {
                $('#imgShowDetectDiv').prepend("<div id='detect"+i+"'></div>");
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
        return match;
    });
}