$(document).ready(function() {
		//设置竖屏切换
		$('#fullpage').fullpage({
			sectionsColor:['white','#269ab3','#ac79f8','#6bef6e','#6269f3','white'], //控制每个section的背景颜色
			controlArrow:true,   //是否隐藏左右滑块的箭头(默认为true)
			verticalCentered: true,  //内容是否垂直居中(默认为true)
			css3: true, //是否使用 CSS3 transforms 滚动(默认为false)
			resize:false, //字体是否随着窗口缩放而缩放(默认为false)
			scrolllingSpeed:500,  //滚动速度，单位为毫秒(默认为700)
			anchors:['page1','page2','page3','page4','page5','page6'],  //定义锚链接(值不能和页面中任意的id或name相同，尤其是在ie下，定义时不需要加#)  
			lockAnchors:false,  //是否锁定锚链接，默认为false。设置weitrue时，锚链接anchors属性也没有效果。
			loopBottom:false,  //滚动到最底部后是否滚回顶部(默认为false)
			loopTop:false, //滚动到最顶部后是否滚底部
			loopHorizontal:false,//左右滑块是否循环滑动
			autoScrolling:true, // 是否使用插件的滚动方式，如果选择 false，则会出现浏览器自带的滚动条
			scrollBar:false,//是否显示滚动条，为true是一滚动就是一整屏
			fixedElements:".logo", //固定元素
			menu:".menu",
			keyboardScrolling:true, //是否使用键盘方向键导航(默认为true)
			keyboardScrolling:true, //页面是否循环 滚动（默认为false）
			navigation:true, //是否显示项目导航（默认为false）
			navigationTooltips:["主页","人脸检测","人脸对比","人脸搜索","人脸属性","联系我们"],//项目导航的 tip
			//navigationColor:'#fff', //项目导航的颜色
			slidesNavigation:true,
			normalScrollElements:"textarea,.code_box3,.code_box4_2,.results_box4",
        });
		//导航栏菜单悬浮、点击效果
		$("a.click_css").click(function(){
			$(this).css({'background-color':'#00c3bb','color':'white'});
		})
		$("a.click_css").mouseover(function(){
			$(this).css({'background-color':'white','color':'#00c3bb'});
		})
		$("a.click_css").mouseout(function(){
			$(this).css({'background-color':'#00c3bb','color':'white'});
		})
		
		//人脸检测中的左侧代码收缩、展开点击效果
		$(".code_title2").click(function(){
			var src1="img/index/two/icon-bottom.png";
			var src2="img/index/two/icon-right.png";
			if($(".code_img2").attr('src')==src1){
				$(".code_img2").attr('src',src2);
				$(".code_text2").css('display','none');
				$(".code_box2").css({'height':'55px','bottom':'82%'})
				$(".attribute_img2").attr('src',src1);
				$(".attribute_text2").css('display','block');
				$(".attribute_box2").css('height','80%');
		      }else{
		      	$(".code_img2").attr('src',src1);
				$(".code_text2").css('display','block');
				$(".code_box2").css({'height':'80%','bottom':'70px'})
				$(".attribute_img2").attr('src',src2);
				$(".attribute_text2").css('display','none');
				$(".attribute_box2").css('height','55px');
		      }
		})
		$(".attribute_title2").click(function(){
			var src1="img/index/two/icon-bottom.png";
			var src2="img/index/two/icon-right.png";
			if($(".attribute_img2").attr('src')==src1){
				$(".code_img2").attr('src',src1);
				$(".code_text2").css('display','block');
				$(".code_box2").css({'height':'80%','bottom':'70px'})
				$(".attribute_img2").attr('src',src2);
				$(".attribute_text2").css('display','none');
				$(".attribute_box2").css('height','55px');
		      }else{
		      	$(".code_img2").attr('src',src2);
				$(".code_text2").css('display','none');
				$(".code_box2").css({'height':'55px','bottom':'82%'})
				$(".attribute_img2").attr('src',src1);
				$(".attribute_text2").css('display','block');
				$(".attribute_box2").css('height','80%');
		      }
		})

		
		/**
		 * 加载展示imgdiv,获取具有特征码的div元素
		 * reveal_sign：width，height : 展示标记
		 * 宽高转换相等-主体：默认：width
		 */
		function loadRevealImgDiv(){
			var signDiv = $("div[reveal_sign]");
			$.each(signDiv, function() {
				var signtype = $(this).attr("reveal_sign");
				if(signtype == "height"){
					$(this).css("width",$(this).height());
				}else{
					$(this).css("height",$(this).width()); 
				}
			});
			var detect_white=$(".detect_box2").width();
			$(".upload2").css('height',detect_white);
		}
		loadRevealImgDiv();
		//設置各个div的宽高
		$(window).resize(function(){//当窗口大小发生变化时
		   loadRevealImgDiv();
		});
		
		//本地上传鼠标移入移出效果
		$(".upload_btn2").mouseover(function(){
			$(this).css({"background-color":"white","color":"#00c3bb"});
		})
		$(".upload_btn2").mouseout(function(){
			$(this).css({"background-color":"#00c3bb","color":"white"});
		})
		$(".upload_btn25").mouseover(function(){
			$(this).css({"background-color":"#00c3bb"});
		})
		$(".upload_btn25").mouseout(function(){
			$(this).css({"background-color":""});
		})
		//图片URL检测鼠标移入移出效果
		$(".detect_btn2").mouseover(function(){
			$(this).css({"background-color":"white","color":"#00c3bb"});
		})
		$(".detect_btn2").mouseout(function(){
			$(this).css({"background-color":"#00c3bb","color":"white"});
		})
		$(".detect_btn5").mouseover(function(){
			$(this).css({"background-color":"#00c3bb"});
		})
		$(".detect_btn5").mouseout(function(){
			$(this).css({"background-color":""});
		})
		//点击小图片之后获取焦点
		$(".detect_fiveimg2 img").click(function(){
			var imgsrc=$(this).attr("src");
			$(".detect_fiveimg2 img").removeClass("img_active");
			$(this).addClass("img_active");
			$(".detect_img2 img").attr("src",imgsrc);
		})
		//人脸对比中的左侧对比结果以及代码收缩、展开点击效果
		$(".code_title3").click(function(){
			var src1="img/index/two/icon-bottom.png";
			var src2="img/index/two/icon-right.png";
			if($(".code_img3").attr('src')==src1){
				$(".code_img3").attr('src',src2);
				$(".code_text3").css('display','none');
				$(".code_box3").css('height','55px')
				$(".results_img3").attr('src',src1);
				$(".results_text3").css('display','block');
				$(".results_box3").css('height','40%')
		      }else{
		      	$(".code_img3").attr('src',src1);
				$(".code_text3").css('display','block');
				$(".code_box3").css('height','40%')
				$(".results_img3").attr('src',src2);
				$(".results_text3").css('display','none');
				$(".results_box3").css('height','55px')
		      }
		})
		$(".results_title3").click(function(){
			var src1="img/index/two/icon-bottom.png";
			var src2="img/index/two/icon-right.png";
			if($(".code_img3").attr('src')==src1){
				$(".code_img3").attr('src',src2);
				$(".code_text3").css('display','none');
				$(".code_box3").css('height','55px')
				$(".results_img3").attr('src',src1);
				$(".results_text3").css('display','block');
				$(".results_box3").css('height','40%')
		      }else{
		      	$(".code_img3").attr('src',src1);
				$(".code_text3").css('display','block');
				$(".code_box3").css('height','40%')
				$(".results_img3").attr('src',src2);
				$(".results_text3").css('display','none');
				$(".results_box3").css('height','55px')
		      }
		})
		//人脸属性中的右侧对比结果以及代码收缩、展开点击效果
		$(".code_title5").click(function(){
			var src1="img/index/two/icon-bottom.png";
			var src2="img/index/two/icon-right.png";
			if($(".code_img5").attr('src')==src1){
				$(".code_img5").attr('src',src2);
				$(".code_text5").css('display','none');
				$(".code_box5").css({'height':'55px','bottom':'82%'})
				$(".attribute_img5").attr('src',src1);
				$(".attribute_text5").css('display','block');
				$(".attribute_box5").css('height','80%');
		      }else{
		      	$(".code_img5").attr('src',src1);
				$(".code_text5").css('display','block');
				$(".code_box5").css({'height':'80%','bottom':'70px'})
				$(".attribute_img5").attr('src',src2);
				$(".attribute_text5").css('display','none');
				$(".attribute_box5").css('height','55px');
		      }
		})
		$(".attribute_title5").click(function(){
			var src1="img/index/two/icon-bottom.png";
			var src2="img/index/two/icon-right.png";
			if($(".attribute_img5").attr('src')==src1){
				$(".code_img5").attr('src',src1);
				$(".code_text5").css('display','block');
				$(".code_box5").css({'height':'80%','bottom':'70px'})
				$(".attribute_img5").attr('src',src2);
				$(".attribute_text5").css('display','none');
				$(".attribute_box5").css('height','55px');
		      }else{
		      	$(".code_img5").attr('src',src2);
				$(".code_text5").css('display','none');
				$(".code_box5").css({'height':'55px','bottom':'82%'})
				$(".attribute_img5").attr('src',src1);
				$(".attribute_text5").css('display','block');
				$(".attribute_box5").css('height','80%');
		      }
		})
		//人脸对比小图片点击效果
		$(".detect_fiveimg2").click(function(){
			var minsrc=$(this).children().attr("src");
			$(this).siblings().children().removeClass("img_active");
			$(this).children().addClass("img_active");
			$(this).parent().siblings(".results_maxbox").find(".showcasing_img").attr('src',minsrc);
		})
		//人脸搜索小图片点击效果
		$(".detect_fiveimg3").click(function(){
			 var minsrc=$(this).children().attr("src");
			 $(this).siblings().children().removeClass("img_active");
			 $(this).children().addClass("img_active");
			 $(this).parent().siblings(".results_maxbox4").find(".showcasing_img3").attr('src',minsrc);
		})
		//人脸属性小图片点击效果
		$(".detect_fiveimg5").click(function(){
			 var minsrc=$(this).children().attr("src");
			 $(this).siblings().children().removeClass("img_active");
			 $(this).children().addClass("img_active");
			 $(this).parents(".left_formal5").siblings().find(".detect_img5").children().attr("src",minsrc);
		})
		//人脸搜索中的右侧代码收缩、展开点击效果
		$(".code_title4").click(function(){
			var src1="img/index/two/icon-bottom.png";
			var src2="img/index/two/icon-right.png";
			if($(".code_img4").attr('src')==src1){
				$(".code_img4").attr('src',src2);
				$(".code_text4").css('display','none');
				$(".code_box4_2").css('height','55px')
				$(".attribute_img4").attr('src',src1);
				$(".attribute_text4").css('display','block');
				$(".attribute_box4").css('height','85%')
		      }else{
		      	$(".code_img4").attr('src',src1);
				$(".code_text4").css('display','block');
				$(".code_box4_2").css('height','85%')
				$(".attribute_img4").attr('src',src2);
				$(".attribute_text4").css('display','none');
				$(".attribute_box4").css('height','55px')
		      }
		})
		$(".attribute_title4").click(function(){
			var src1="img/index/two/icon-bottom.png";
			var src2="img/index/two/icon-right.png";
			if($(".code_img4").attr('src')==src1){
				$(".code_img4").attr('src',src2);
				$(".code_text4").css('display','none');
				$(".code_box4_2").css('height','55px')
				$(".attribute_img4").attr('src',src1);
				$(".attribute_text4").css('display','block');
				$(".attribute_box4").css('height','85%')
		      }else{
		      	$(".code_img4").attr('src',src1);
				$(".code_text4").css('display','block');
				$(".code_box4_2").css('height','85%')
				$(".attribute_img4").attr('src',src2);
				$(".attribute_text4").css('display','none');
				$(".attribute_box4").css('height','55px')
		      }
		})
	});