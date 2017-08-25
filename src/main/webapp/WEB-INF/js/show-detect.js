function xmTanUploadImg(obj) {
    var file = obj.files[0];

    console.log(obj);console.log(file);
    console.log("file.size = " + file.size);  //file.size 单位为byte

    var reader = new FileReader();

//            //读取文件过程方法
//            reader.onloadstart = function (e) {
//                console.log("开始读取....");
//            }
//            reader.onprogress = function (e) {
//                console.log("正在读取中....");
//            }
//            reader.onabort = function (e) {
//                console.log("中断读取....");
//            }
//            reader.onerror = function (e) {
//                console.log("读取异常....");
//            }
    reader.onload = function (e) {
        console.log("成功读取....");

        var img = document.getElementById("xmTanImg");
        img.src = e.target.result;
        //或者 img.src = this.result;  //e.target == this
    }

    reader.readAsDataURL(file)
}

function uploadPic(obj){
    var img = document.getElementById("imgShow");
    var file = obj.files[0];
    var reader = new FileReader();
    reader.onload = function (e) {
        console.log("成功读取....");
        img.src = e.target.result;
        //或者 img.src = this.result;  //e.target == this
    }
    reader.readAsDataURL(file)
    var options = {
        url:"../n-tech/v0/detect",
        type:"post",
        dataType:"json",
//                data: {photo: img.src},
        success:function(data) {
            console.log(data + "fasdfasdfasdfasdfsadf")
            handleData(img, data)
        },
        error:function (data) {
            alert(data+"出现错误")
        }
    };
    //jquery.form使用方式
    $("#jvForm").ajaxSubmit(options);



}
$(document).ready(function () {

$('#check-btn').click(function () {
    var check = document.getElementById("inputUrl");
    $('#imgShow').src=""

    console.log(check.tagName)
    detectReq(check)

})

//            $("#img").click(function () {
//                $('#imgShow').src=$('#img'+i).attr("src")
//                console.log()
////            var formData = new FormData();
////            formData.append("file",document.getElementById("picForm"))
//
//
//            });
}); //ready
var num=0;
function detectReq(img) {
//            for (var i = 0; i < num; i++) {
//                if ($('#' + i).length > 0) {
//                    $('#' + i).remove();
//                }
//            }
//            console.log("width:"+img.naturalWidth)
//            console.log("height:"+img.naturalHeight)
//            if(img.naturalWidth>400){
//                var widthRate=400/img.naturalWidth
//            }
//            if(img.naturalHeight>400){
//                var heightRate=400/img.naturalHeight
//            }
//            $('#imgShow').attr("src",img.src);
    var reqData;
    if(img.tagName=='INPUT'){
        reqData=img.value;
    }else if(img.tagName=='IMG'){
        reqData=img.src;
    }else{
        console.log(img.tagName+":error tag");

        return false
    }
    // if
    $.ajax({
        url: '../n-tech/v0/detect',
        type: 'POST',
//                    data: {photo: address},
        dataType:"json",
        data: {photo: reqData},
        async: true,
//                dataType : 'json',
        success: function (data) {
            // var img = document.getElementById("imgShow");
            handleData(img,data)

        }
    });
}
function handleData(eleType,data) {
    var reqData;
    if(eleType.tagName=='INPUT'){
        reqData=eleType.value;
    }else if(eleType.tagName=='IMG'){
        reqData=eleType.src;
    }else{
        console.log(eleType.tagName+":error tag");
        return false
    }

    // $('#imgShow').attr("src",reqData);
    img = document.getElementById("imgShow");
    img.src=reqData
    var imgSrc= $('#imgShow').attr("src");
    getImageWidth(imgSrc,function(w,h){
        console.log({width:w,height:h});


    for (var i = 0; i < num; i++) {
        if ($('#' + i).length > 0) {
            $('#' + i).remove();
        }
    }
    console.log("width:"+img.width)
    console.log("height:"+img.height)
    // if(img.naturalWidth>400){
        var widthRate=400/img.naturalWidth
    // }
    // if(img.naturalHeight>400){
        var heightRate=400/img.naturalHeight
    // }



    var dataObj=eval(data)

    var rect;
    var results = [];
    if (dataObj.hasOwnProperty("faces")) {
        rect = dataObj['faces'];
        for (var i = 0; i < rect.length; i++) {
            // if(widthRate){
                rect[i].x1=rect[i].x1 *widthRate-4
                rect[i].x2=rect[i].x2 *widthRate-4
            // }
            // if(heightRate){
                rect[i].y1=rect[i].y1 *heightRate
                rect[i].y2=rect[i].y2 *heightRate
            // }
            width = (rect[i].x2 - rect[i].x1)*0.8
            height = (rect[i].y2 - rect[i].y1)*0.9
            left = rect[i].x1 + width*0.15+54
            divtop = rect[i].y1 +2
            var result= new Result(width,height,left,divtop);
            results[i] = result;
            console.log(results);
        }
    }
    if (results) {

        for (var i = 0; i < num; i++) {
            if ($('#' + i).length > 0) {
                $('#' + i).remove();
            }
        }
        num=results.length;
        for (var i = 0; i < results.length; i++) {
            $('#picShow').prepend("<div id="+i+"></div>");
            $('#' + i).css({
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
function Result(width,height,left,top) {
    this.width=width;
    this.height=height;
    this.left=left;
    this.top=top;
}

function getImageWidth(url,callback){
    var img = new Image();
    img.src = url;

    // 如果图片被缓存，则直接返回缓存数据
    if(img.complete){
        callback(img.width, img.height);
    }else{
        // 完全加载完毕的事件
        img.onload = function(){
            callback(img.width, img.height);
        }
    }

}
