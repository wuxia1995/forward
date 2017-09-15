$(document).ready(function () {
    // $("#mamageMyGallery").click(function () {
    //     $("#loginTip").removeClass("hidden")
    // })
    //
}); //ready

var preSize=0;
function searchUrlDemo(input) {
    $("#imgShowSearchDemo").attr("src",$("#inputUrlSearchDemo").val())
    var img = new Image();
    img.src = $("#inputUrlSearchDemo").val();
    searchReqDemo(img);

}

function searchReqDemo(img) {
    document.getElementById("imgShowSearchDemo").style.height="100%";
    document.getElementById("imgShowSearchDemo").style.width="100%";
    getImageWidth(img.src,function (widthImg,heightImg) {
        var imgShowDiv = document.getElementById("imgShowSearchDemoDiv");
        var imgShow = document.getElementById("imgShowSearchDemo");
        console.log("height:"+heightImg)
        console.log("widht:"+widthImg)
        console.log(imgShowDiv.offsetHeight)
        if(heightImg!=widthImg){
            if(heightImg>widthImg){
                imgShow.style.width=widthImg/heightImg*100+"%"
                imgShow.style.height="100%"
            }
            if(heightImg<widthImg){
                imgShow.style.height=heightImg/widthImg*100+"%"
                imgShow.style.width="100%"
            }
        }else{
            imgShow.style.height="100%"
            imgShow.style.width="100%"
        }
        // imgShow.src = e.target.result;
        // $("#imgShowDetect").attr("src", imgUrl)
    })
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
            if(data==""||data.length==0){
                $('#reponseSearchDemo').html("图库中未搜索相似人脸")
                $('#searchResultDemo').html("")
                $('#resultShowSearchDemo').html("")
                return false;
            }
            $('#imgShowSearchDemo').attr("src",img.src);
            // console.log(data)
            dataObj=eval(data);
            showResult(dataObj)
            $('#reponseSearchDemo').html(syntaxHighlight(filter(dataObj)))

        },
        error: function (data) {
                $('#reponseSearchDemo').html("文件格式不符或文件太大")
                $('#resultShowSearchDemo').html("")
                $('#searchResultDemo').html("")
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
    document.getElementById("imgShowSearchDemo").style.height="100%";
    document.getElementById("imgShowSearchDemo").style.width="100%";

    var imgShow = document.getElementById("imgShowSearchDemo")
    var file = img.files[0];
    if (!file) {
        return false;
    }
    var reader = new FileReader();
    reader.onload = function (e) {

        // imgShow.src = e.target.result;

        imgShow.src = e.target.result;
        getImageWidth(imgShow.src,function (widthImg,heightImg) {
            var imgShowDiv = document.getElementById("imgShowSearchDemoDiv");
            // var imgShow = document.getElementById("imgShowSearchDemo");
            console.log("height:"+heightImg)
            console.log("widht:"+widthImg)
            console.log(imgShowDiv.offsetHeight)
            if(heightImg!=widthImg){
                if(heightImg>widthImg){
                    imgShow.style.width=widthImg/heightImg*100+"%"
                    imgShow.style.height="100%"
                }
                if(heightImg<widthImg){
                    imgShow.style.height=heightImg/widthImg*100+"%"
                    imgShow.style.width="100%"
                }
            }else{
                imgShow.style.height="100%"
                imgShow.style.width="100%"
            }
            // imgShow.src = e.target.result;
            // $("#imgShowDetect").attr("src", imgUrl)
        })


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
        // dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        async: true,
        success: function (data) {
            if(data==""||data.length==0){
                $('#reponseSearchDemo').html("图库中未搜索相似人脸")
                $('#searchResultDemo').html("")
                $('#resultShowSearchDemo').html("")
            }
            dataObj=eval(data.results);
            showResult(dataObj)
            $('#reponseSearchDemo').html(syntaxHighlight(filter(dataObj)))
        },
        error: function (data) {
            // if(data==""){
                $('#reponseSearchDemo').html("文件格式不符或文件太大")
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



    // for (var prop in dataObj)
    // {
        // console.log("jsonObj[" + prop + "]=" + dataObj[prop]);
        for (var v in dataObj){
            preSize++;
            var imgEle=
                "<div>第"+preSize+"张</div>" +
                "<div class='graphic_img4'><img src='"+dataObj[v]['face']['normalized']+"'></div>"
            var confidence="第"+preSize+"张是同一个人的可信度是:"+dataObj[v]['confidence']
            $("#searchResultDemo").append("<div  id='divResult"+preSize+"'>"+ confidence +"</div>");
            $("#resultShowSearchDemo").append("<div class='results_graphic4'  class='imgboxShow' id='div"+preSize+"'>"+ imgEle +"</div>");
            // console.log(dataObj[v]['face']['normalized'])
        }
    // }
}

function filter(data) {
    if(data.length==0){
        return false;
    }
    for(var obj in data){
        data[obj]['face'].normalized="normalizedUrl"
    }
    return data;
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