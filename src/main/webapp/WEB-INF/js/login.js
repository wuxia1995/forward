$(function() {
    var result;
    $('#submit').click(function () {
        if($("#name").val()==''){
            $('#msg').text("用户名不能为空");
            return false;
        }
        if($("#password").val()==''){
            $('#msg').text("密码不能为空");
            return false;
        }
        $.ajax({
            type:"POST",
            url:"loginCheck",
            async: false,
            data:{name:$("#name").val(),password:$("#password").val()},
            dataType:'json',
            success:function(msg){
                if(msg){
                    $('#msg').text("");
                    // $('#login-form').submit();
                    result=true;
                }else{
                    $('#msg').text("用户未激活或者用户名或密码错误");
                    result=false;
                }
            }
        });
        return result;
    });

    // $('#login-form').bind("submit", function(){
    //     var options = {
    //         url: 'loginCheck',
    //         type: 'post',
    //         dataType: 'json',
    //         data: {name:$("#name").val(),password:$("#password").val()},
    //         success: function (data) {
    //             if(!data){
    //                 $('#msg').text("用户未激活或者用户名或密码错误");
    //                 return false;
    //             }
    //         }
    //     };
    //     $.ajax(options);
    //     return false;
    // })
    //
    // $('#search').click(function(){
    //     $('#search_form').submit();
    // })
})