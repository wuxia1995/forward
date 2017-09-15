//动态控制div宽高
function width() {
	var body = $("body").width();
	$(".right_content").css("width", body - 240);
	var img_border=$(".img_border").height();
	$(".img_border").css({"width":img_border,"margin-left":-img_border/2});
}
$(document).ready(function() {
	width();
	$(window).resize(function() { //当窗口大小发生变化时
		width();
	});
	//菜单鼠标移入移出效果
    $(".nav_ul li").mousemove(function(){
    	var cls=$(this).attr("class");
    	if(cls!="active"){
    		$(this).css('background-color','#45e0d5');
    	}
    })
    $(".nav_ul li").mouseout(function(){
    	var cls=$(this).attr("class");
    	if(cls!="active"){
    		$(this).css('background-color','');
    	}
    })
    
    //单选按钮判断
     $(".select_btn input").click(function(){
            if($(this).is(":checked")){
		         $(".select_btn img").attr("src","../img/information/radio_no.png");
		         $("select").removeClass("sel_active");
		         $("select").attr("disabled","disabled")
		         $(this).siblings("img").attr("src","../img/information/radio_yes.png");
		         $(this).parent().siblings(".select_down").children().addClass("sel_active");
		         $(this).parent().siblings(".select_down").children().attr("disabled",false);
		    }
         });

    $('#buyMeal').click(function () {

        var type=$('input:radio[name="type"]:checked').val();
        alert(type);
        //console.log(type); $("select[name=items] option[selected]").text();

        var value;
        if(type=="date"){
            value=$("#date").find("option:selected").val();
        }else if(type=="times"){
            value=$("#times").find("option:selected").val();
        }
        //console.log(value)
        alert(value);
        $.ajax({
            url:'setMealBuy',
            type: 'POST',
            dataType: "json",
            async:false,
            data:{
                "type":type,
                "value":value
            },
            success:function (data) {
                if(data){
                    alert("购买成功！")
                    $("#msg").text("购买成功");
                }else{
                    alert("你选择的套餐与之前购买的套餐不一样")
                    $("#msg").text("购买失败");
                }
            },
            error:function (data) {
                alert(data)
            }
        })

    });
    
})