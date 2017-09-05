$(document).ready(function () {
    $("#mamageMyGallery").click(function () {
        $("#loginTip").removeClass("hidden")
    })
    // $('#check-btn').click(function () {
    //     var check = document.getElementById("inputUrl");
    //     console.log(check.tagName)
    //     detectReq(check)
    //
    // })

}); //ready


var preSize=0;
function searchUrl(input) {
    var img = new Image();
    img.src = $("#inputUrl").val();
    searchReq(img);
}

function searchReq(img) {

    var formData = new FormData();
    formData.append("n",4);
    formData.append("photo",img.src)
    $.ajax({
        url: 'getDemoFace',
        type: 'POST',
        dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        async: true,
        success: function (data) {
            $('#imgShow').attr("src",img.src);
            console.log("success")
            console.log(data)
            dataObj=eval(data.results);
            showResult(dataObj)
            $('#reponse').html(syntaxHighlight(data))

        },
        error: function (data) {
            // img.src = ""
            alert("no face")
            return false
        }
    });
}

function removeBefore(size) {
    for(var i=1;i<=size;i++){
        if ($("#div"+i)) {
            $("#div"+i).remove();
        }
        if ($("#divResult"+i)) {
            $("#divResult"+i).remove();
        }
    }
}
function uploadImg(img) {

    var imgShow = document.getElementById("imgShow")
    var file = img.files[0];
    if (!file) {
        return false;
    }
    var reader = new FileReader();
    reader.onloadend = function (e) {
        console.log("成功读取....");
        imgShow.src = e.target.result;
    }
    reader.readAsDataURL(file)
    //上传文件时当前的图片内容是文件,对比的对象可能时文件或者url
    var formData = new FormData();
    formData.append("n",4);
    formData.append("photo",file)
    $.ajax({
        url: 'getDemoFace',
        type: 'POST',
        dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        async: true,
        success: function (data) {
            dataObj=eval(data.results);
            showResult(dataObj)
            $('#reponse').html(syntaxHighlight(data))
        },
        error: function (data) {
            alert("no face")
            return false
        }
    });

}
function showResult(dataObj) {

    removeBefore(preSize)
    preSize=0;
    for (var prop in dataObj)
    {
        // console.log("jsonObj[" + prop + "]=" + dataObj[prop]);
        for (var v in dataObj[prop]){
            preSize++;
            var imgEle="<div>第"+preSize+"张</div><img style='' height='200' width='200' src='"+dataObj[prop][v]['face']['photo']+"'>"
            var confidence="第"+preSize+"张是同一个人的可信度是:"+dataObj[prop][v]['confidence']
            $("#searchResult").append("<div  id='divResult"+preSize+"'>"+ confidence +"</div>");
            $("#resultShow").append("<div class='imgboxShow' id='div"+preSize+"'>"+ imgEle +"</div>");
            console.log(dataObj[prop][v]['face']['photo'])
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