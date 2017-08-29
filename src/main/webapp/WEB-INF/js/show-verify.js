function verifyUrl(id){
    var img=new Image();
    img.src= $("#inputUrl"+id).val();
    verifyReq(img,id);
}
// function verifyUrl2(){
//     var img=new Image();
//     img.src= $("#inputUrl1").val();
//     verifyReq2(img);
// }

function uploadPic(obj,id){
    var img = document.getElementById("imgShow"+id)
    var file = obj.files[0];
    var reader = new FileReader();
    reader.onload = function (e) {
        console.log("成功读取....");
        img.src = e.target.result;
        //或者 img.src = this.result;  //e.target == this
    }
    reader.readAsDataURL(file)
    test=id==1?2:1
    var testImg=document.getElementById("imgShow"+test)
    var options = {
        url:"../n-tech/v0/verify",
        type:"post",
        dataType:"json",
        data: {"photo2": testImg.src,
            // "photo2":$("#imgShow").attr("src")
        },
        success:function(data) {
            console.log(data + "fasdfasdfasdfasdfsadf")
            //console.log(data);
            var dataObj=eval(data);
            var confidence=dataObj['results']['0']['confidence'];
            var val=dataObj['verified'];
            //第一个正方形框
            var rect1=dataObj['results']['0']['bbox1'];
            drawPic(rect1,testImg,1)
            //第二个正方形框
            var rect2=dataObj['results']['0']['bbox2'];
            drawPic(rect2,testImg,2)
            console.log(rect1);
            console.log(rect2);
            $("#result").val("");
            if(val==true&&confidence>=0.78){
                $("#result").val("两张照片是同一张人脸");
            }else{
                $("#result").val("两张照片不是同一张人脸");
            }
        },
        error:function (data) {
            alert(data+"出现错误")
        }
    };
    //jquery.form使用方式
    $("#jvForm"+id).ajaxSubmit(options);



}
function drawPic(result,img,id) {

    // console.log({width: w, height: h});



        if ($('#' + id).length > 0) {
            $('#' + id).remove();
        }

    console.log("width:" + img.width)
    console.log("height:" + img.height)
    // if(img.naturalWidth>400){
    var widthRate = 400 / img.naturalWidth
    // }
    // if(img.naturalHeight>400){
    var heightRate = 400 / img.naturalHeight

    result.x1 = result.x1 * widthRate - 4
    result.x2 = result.x2 * widthRate - 4
    // }
    // if(heightRate){
    result.y1 = result.y1 * heightRate
    result.y2 = result.y2 * heightRate
    // }
    width = (result.x2 - result.x1) * 0.8
    height = (result.y2 - result.y1) * 0.9
    left = result.x1 + width * 0.15
    divtop = result.y1 + 2
    var result = new Result(width, height, left, divtop);

    $('#picShow').prepend("<div id=" + id + "></div>");
    $('#pic' + id).css({
        "position": "absolute",
        "outline": "rgb(70, 171, 232) solid 2px",
        "width": result.width,
        "height":result.height,
        "left": result.left,
        "top": result.top
    })

}
$(document).ready(function () {
    //
    // $('#check-btn').click(function () {
    //     var check = document.getElementById("inputUrl1");
    //     $('#imgShow1').src="";
    //
    //     console.log(check.tagName);
    //     verifyReq(check);
    //
    // })

//            $("#img").click(function () {
//                $('#imgShow').src=$('#img'+i).attr("src")
//                console.log()
////            var formData = new FormData();
////            formData.append("file",document.getElementById("picForm"))
//
//
//            });
}); //ready
//
// function testForm() {
//     var options = {
//         url:"../n-tech/v0/verify",
//         type:"post",
//         dataType:"json",
//         success:function(data) {
//             console.log(data + "fasdfasdfasdfasdfsadf")
//             //console.log(data);
//
//             var dataObj=eval(data);
//             var confidence=dataObj['results']['0']['confidence'];
//             var val=dataObj['verified'];
//             //第一个正方形框
//             var rect1=dataObj['results']['0']['bbox1'];
//             //第二个正方形框
//             var rect2=dataObj['results']['0']['bbox2'];
//             console.log(rect1);
//             console.log(rect2);
//             $("#result").val("");
//             if(val==true&&confidence>=0.78){
//                 $("#result").val("两张照片是同一张人脸");
//             }else{
//                 $("#result").val("两张照片不是同一张人脸");
//             }
//         },
//         error:function (data) {
//             alert(data+"出现错误")
//         }
//     };
//     $("#testForm").ajaxSubmit(options);
// }


function verifyReq(img,id) {
    $("#imgShow"+id).attr("src",img.src);
    $.ajax({
        //请求的地址
        url: '../n-tech/v0/verify',
        type: 'POST',
//                    data: {photo: address},
        dataType:"json",
        data: {"photo1": $("#imgShow1").attr("src"),
            "photo2":$("#imgShow2").attr("src")
        },
        async: true,
//                dataType : 'json',
        success: function (data) {
            console.log(data);

            var dataObj=eval(data);
            var confidence=dataObj['results']['0']['confidence'];
            var val=dataObj['verified'];
            //第一个正方形框
            var rect1=dataObj['results']['0']['bbox1'];
            drawPic(rect1,img,id)
            //第二个正方形框
            var rect2=dataObj['results']['0']['bbox2'];
            drawPic(rect2,$('#imgShow2'),id)
            console.log(rect1);
            console.log(rect2);
            $("#result").val("");
            if(val==true&&confidence>=0.78){
                $("#result").val("两张照片是同一张人脸");
            }else{
                $("#result").val("两张照片不是同一张人脸");
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