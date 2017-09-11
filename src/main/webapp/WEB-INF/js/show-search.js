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
    //
    // $.ajax({
    //     url: 'customer/getDemoFace',
    //     type: 'POST',
    //     // dataType: "json",
    //     data: formData,
    //     processData: false,
    //     contentType: false,
    //     async: true,
    //     success: function (data) {
    //         if(data==""){
    //             $('#reponseSearchDemo').html("有图片未检测到人脸")
    //             $('#searchResultDemo').html("")
    //             $('#resultShowSearchDemo').html("")
    //             return false;
    //         }
    //         $('#imgShowSearchDemo').attr("src",img.src);
    //         // console.log("success")
    //         console.log(data)
    //         dataObj=eval('(' + data + ')');
    //         showResult(dataObj.results)
    //         $('#reponseSearchDemo').html(syntaxHighlight(dataObj))
    //
    //     },
    //     error: function (data) {
    //         // if(data==""){
    //         $('#reponseSearchDemo').html("有图片未检测到人脸")
    //         $('#resultShowSearchDemo').html("")
    //         $('#searchResultDemo').html("")
    //         // }
    //         return false
    //     }
    // });



}); //ready

var preSize=0;
function searchUrlDemo(input) {
    var img = new Image();
    img.src = $("#inputUrlSearchDemo").val();
    searchReqDemo(img);
}

function searchReqDemo(img) {

    var formData = new FormData();
    formData.append("n",4);
    formData.append("photo",img.src)
    $.ajax({
        url: 'customer/getDemoFace',
        type: 'POST',
        // dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        async: true,
        success: function (data) {
            if(data==""){
                $('#reponseSearchDemo').html("有图片未检测到人脸")
                $('#searchResultDemo').html("")
                $('#resultShowSearchDemo').html("")
                return false;
            }
            $('#imgShowSearchDemo').attr("src",img.src);
            // console.log("success")
            console.log(data)
            dataObj=eval('(' + data + ')');
            showResult(dataObj.results)
            $('#reponseSearchDemo').html(syntaxHighlight(dataObj))

        },
        error: function (data) {
            // if(data==""){
                $('#reponseSearchDemo').html("有图片未检测到人脸")
                $('#resultShowSearchDemo').html("")
                $('#searchResultDemo').html("")
            // }
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
function uploadImgSearcheDemo(img) {

    var imgShow = document.getElementById("imgShowSearchDemo")
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
    // formData.append("n",4);
    formData.append("photo",file)
    //清除input框的文件状态,解决两次同一张照片不触发事件的问题
    img.value="";
    $.ajax({
        url: 'customer/getDemoFace',
        type: 'POST',
        dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        async: true,
        success: function (data) {
            if(data==""){
                $('#reponseSearchDemo').html("有图片未检测到人脸")
                $('#searchResultDemo').html("")
                $('#resultShowSearchDemo').html("")
            }
            dataObj=eval(data.results);
            showResult(dataObj)
            $('#reponseSearchDemo').html(syntaxHighlight(data))
        },
        error: function (data) {
            // if(data==""){
                $('#reponseSearchDemo').html("有图片未检测到人脸")
                $('#searchResultDemo').html("")
                $('#resultShowSearchDemo').html("")
            // }
            // alert("no face")
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
            var imgEle=
                "<div>第"+preSize+"张</div>" +
                "<div class='graphic_img4'><img src='"+dataObj[prop][v]['face']['normalized']+"'></div>"
            var confidence="第"+preSize+"张是同一个人的可信度是:"+dataObj[prop][v]['confidence']
            $("#searchResultDemo").append("<div  id='divResult"+preSize+"'>"+ confidence +"</div>");
            $("#resultShowSearchDemo").append("<div class='results_graphic4'  class='imgboxShow' id='div"+preSize+"'>"+ imgEle +"</div>");
            console.log(dataObj[prop][v]['face']['normalized'])
        }
    }
}



//
// function syntaxHighlight(json) {
//     if (typeof json != 'string') {
//         json = JSON.stringify(json, undefined, 2);
//     }
//     json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
//     return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
//         var cls = 'number';
//         if (/^"/.test(match)) {
//             if (/:$/.test(match)) {
//                 cls = 'key';
//             } else {
//                 cls = 'string';
//             }
//         } else if (/true|false/.test(match)) {
//             cls = 'boolean';
//         } else if (/null/.test(match)) {
//             cls = 'null';
//         }
//         return '<span class="' + cls + '">' + match + '</span>';
//     });
// }