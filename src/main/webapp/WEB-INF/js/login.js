$(function() {
    $('#submit').click(function () {
        var result;
        // return false;
        $.ajax({
            type:"POST",
            url:"loginCheck",
            async: false,
            data:{name:$("#name").val(),password:$("#password").val()},
            dataType:'json',
            success:function(msg){
                //alert(msg);
                if(msg){
                    result = true
                }else{
                    $('#msg').text("用户未激活或者用户名或密码错误");
                    result = false
                }
            }
        });
        return result
    });
})