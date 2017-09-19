$(document).ready(function () {

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
                $('#reponseSearchDemo').html("文件格式不符或文件太大")
                $('#searchResultDemo').html("")
                $('#resultShowSearchDemo').html("")
            return false
        }
    });

}

//search showResult
function showResult(dataObj) {
    removeBefore(preSize)
    preSize=0;
        for (var v in dataObj){
            preSize++;
            var imgEle=
                "<div>第"+preSize+"张</div>" +
                "<div class='graphic_img4'><img src='"+dataObj[v]['face']['normalized']+"'></div>"
            var confidence="第"+preSize+"张是同一个人的可信度是:"+dataObj[v]['confidence']
            $("#searchResultDemo").append("<div  id='divResult"+preSize+"'>"+ confidence +"</div>");
            $("#resultShowSearchDemo").append("<div class='results_graphic4'  class='imgboxShow' id='div"+preSize+"'>"+ imgEle +"</div>");
        }

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
