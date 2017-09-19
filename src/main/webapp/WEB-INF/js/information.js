//动态控制div宽高
function width() {
	var body = $("body").width();
	$(".right_content").css("width", body - 240);
	var img_border=$(".img_border").height();
	$(".img_border").css({"width":img_border,"margin-left":-img_border/2});
	var administer_imgbg=$(".administer_imgbg").height();
	$(".administer_imgbg").css("width",administer_imgbg);
	var administer_img=$(".administer_img").width();
	$(".administer_img").css("height",administer_img);
	var results_minimg=$(".results_minimg").width();
	$(".results_minimg").css("height",results_minimg);
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
   //本地上传效果
   $(".contrast_file").mouseover(function(){
   	 $(this).parent().css({"background-color":"#58e7dd","color":"white"});
   })
   $(".contrast_file").mouseout(function(){
   	$(this).parent().css({"background-color":"white","color":"#58e7dd"});
   })
   $(".contrast_file2").mouseover(function(){
   	 $(this).parent().css({"background-color":"#58e7dd","color":"white"});
   })
   $(".contrast_file2").mouseout(function(){
   	$(this).parent().css({"background-color":"white","color":"#58e7dd"});
   })
   //检测效果
   $(".detect_btn").mouseover(function(){
	 $(this).css({"background-color":"#58e7dd","color":"white"});
   })
   $(".detect_btn").mouseout(function(){
   	$(this).css({"background-color":"white","color":"#58e7dd"});
   })
   $(".detect_btn2").mouseover(function(){
	 $(this).css({"background-color":"#58e7dd","color":"white"});
   })
   $(".detect_btn2").mouseout(function(){
   	$(this).css({"background-color":"white","color":"#58e7dd"});
   })
   //点击小图片之后获取焦点
		$(".detect_fiveimg img").click(function(){
			var imgsrc=$(this).attr("src");
			$(".detect_fiveimg img").removeClass("img_active");
			$(this).addClass("img_active");
			$(".administer_imgbox img").attr("src",imgsrc);
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
      
})