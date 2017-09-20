$(document).ready(function() {
    getOs();
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
			navigationTooltips:["主页","人脸检测","人脸对比","人脸搜索","联系我们"],//项目导航的 tip
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
		//人脸对比中的左侧代码收缩、展开点击效果
		$(".attribute_title3").click(function(){
			var src1="img/index/two/icon-bottom.png";
			var src2="img/index/two/icon-right.png";
			if($(".attribute_img3").attr('src')==src1){
				$(".attribute_img3").attr('src',src2);
				$(".attribute_text3").css('display','none');
				$(".attribute_box3").css({'height':'55px','bottom':'69%'});
				$(".code_img3").attr('src',src1);
				$(".code_text3").css('display','block');
				$(".code_box3").css('height','67%');
			}else{
				$(".attribute_img3").attr('src',src1);
				$(".attribute_text3").css('display','block');
				$(".attribute_box3").css({'height':'67%','bottom':'70px'});
				$(".code_img3").attr('src',src2);
				$(".code_text3").css('display','none');
				$(".code_box3").css('height','55px');
			}
		})
		$(".code_title3").click(function(){
			var src1="img/index/two/icon-bottom.png";
			var src2="img/index/two/icon-right.png";
			if($(".code_img3").attr('src')==src1){
				$(".attribute_img3").attr('src',src1);
				$(".attribute_text3").css('display','block');
				$(".attribute_box3").css({'height':'67%','bottom':'70px'});
				$(".code_img3").attr('src',src2);
				$(".code_text3").css('display','none');
				$(".code_box3").css('height','55px');
			}else{
				$(".attribute_img3").attr('src',src2);
				$(".attribute_text3").css('display','none');
				$(".attribute_box3").css({'height':'55px','bottom':'69%'});
				$(".code_img3").attr('src',src1);
				$(".code_text3").css('display','block');
				$(".code_box3").css('height','67%');
			}
		})

		//人脸检测复选框判断
		$(".attribute2").click(function(){
			if($(this).is(':checked')){
				$(this).siblings(".checkbox_box2").children().attr("src","img/index/two/yes.png");
			}else{
				$(this).siblings(".checkbox_box2").children().attr("src","img/index/two/no.png");
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
			var img_bgbox2=$(".img_bgbox2").width();
			$(".select_box2").css("bottom",img_bgbox2+25);
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
		$(".contrast_btn3").mouseover(function(){
			$(this).css({"background-color":"#79f0b4"});
		})
		$(".contrast_btn3").mouseout(function(){
			$(this).css({"background-color":""});
		})
		$(".contrast_btn4").mouseover(function(){
			$(this).css({"background-color":"#79f0b4"});
		})
		$(".contrast_btn4").mouseout(function(){
			$(this).css({"background-color":""});
		})
		//图片URL检测鼠标移入移出效果
		$(".detect_btn2").mouseover(function(){
			$(this).css({"background-color":"white","color":"#00c3bb"});
		})
		$(".detect_btn2").mouseout(function(){
			$(this).css({"background-color":"#00c3bb","color":"white"});
		})
		$(".detect_btn3").mouseover(function(){
			$(this).css("background-color","#79f0b4");
		})
		$(".detect_btn3").mouseout(function(){
			$(this).css("background-color","");
		})
		$(".detect_btn4").mouseover(function(){
			$(this).css("background-color","#79f0b4");
		})
		$(".detect_btn4").mouseout(function(){
			$(this).css("background-color","");
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
		//人脸检测小图片点击效果
		$(".detect_fiveimg2").click(function(){
			var minsrc=$(this).children().attr("src");
			$(this).siblings().children().removeClass("img_active");
			$(this).children().addClass("img_active");
			$(this).parent().siblings(".results_maxbox").find(".showcasing_img").attr('src',minsrc);
		})
		//人脸对比小图片点击效果
		$(".detect_fiveimg3").click(function(){
			 var minsrc=$(this).children().attr("src");
			 $(this).siblings().children().removeClass("img_active");
			 $(this).children().addClass("img_active");
			 $(this).parents(".upload_box3").siblings(".results_maxbox3").find(".showcasing_img3").attr('src',minsrc);
		})
		//人脸搜索小图片点击效果
		$(".detect_fiveimg4").click(function(){
			 var minsrc=$(this).children().attr("src");
			 $(this).siblings().children().removeClass("img_active");
			 $(this).children().addClass("img_active");
			 $(this).parent().siblings(".results_maxbox4").find(".showcasing_img4").attr('src',minsrc);
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
				$(".code_text4-2").css('display','none');
				$(".code_box4_2").css({'height':'55px'});
				$(".attribute_img4").attr('src',src1);
				$(".attribute_text4").css('display','block');
				$(".attribute_box4").css('height','88%');
			}else{
				$(".code_img4").attr('src',src1);
				$(".code_text4-2").css('display','block');
				$(".code_box4_2").css({'height':'88%'});
				$(".attribute_img4").attr('src',src2);
				$(".attribute_text4").css('display','none');
				$(".attribute_box4").css('height','55px');
			}
		})
		$(".attribute_title4").click(function(){
			var src1="img/index/two/icon-bottom.png";
			var src2="img/index/two/icon-right.png";
			if($(".attribute_img4").attr('src')==src1){
				$(".code_img4").attr('src',src1);
				$(".code_text4-2").css('display','block');
				$(".code_box4_2").css({'height':'88%'});
				$(".attribute_img4").attr('src',src2);
				$(".attribute_text4").css('display','none');
				$(".attribute_box4").css('height','55px');
			}else{
				$(".code_img4").attr('src',src2);
				$(".code_text4-2").css('display','none');
				$(".code_box4_2").css({'height':'55px'});
				$(".attribute_img4").attr('src',src1);
				$(".attribute_text4").css('display','block');
				$(".attribute_box4").css('height','88%');
			}
		})

	//在主页面载入时触发相关页面事件


    //对比
    //
    // $.ajax({
    //     url: 'customer/verify-face',
    //     type: 'POST',
    //     // dataType: "json",
    //     data:{photo1:"http://192.168.10.212:8080/img/index/two/max_img5.png",
    //         photo2:"http://192.168.10.212:8080/img/index/two/max_img5.png"},
    //     processData: false,
    //     contentType: false,
    //     async: true,
    //     success: function (data) {
    //         if(data==""){
    //             $("#resultVerify").html("有图片未检测到人脸");
    //             $("#reponseVerify").html("有图片未检测到人脸");
    //             return false;
    //         }
    //         readResData(eval('(' + data + ')'),id)
    //     },
    //     error:function(data){
    //         // if(data==""){
    //         $("#resultVerify").html("文件格式不符或文件太大");
    //         $("#reponseVerify").html("");
    //         return false;
    //         // }
    //         // console.log(data)
    //     }
    // });


    // 搜索
    // var formData = new FormData();
    // formData.append("n",3);
    // formData.append("photo","http://192.168.10.212:8080/img/index/two/max_img2.png")
    // //清除input框的文件状态,解决两次同一张照片不触发事件的问题
    // $.ajax({
    //     url: 'customer/getDemoFace',
    //     type: 'POST',
    //     data: formData,
    //     processData: false,
    //     contentType: false,
    //     async: true,
    //     success: function (data) {
    //         if(data==""||data.length==0){
    //             $('#reponseSearchDemo').html("")
    //             $('#searchResultDemo').html("图库中未搜索相似人脸")
    //             $('#resultShowSearchDemo').html("")
    //         }
    //         dataObj=eval(data);
    //         showResult(dataObj)
    //         $('#reponseSearchDemo').html(syntaxHighlight(filter(dataObj)))
    //     },
    //     error: function (data) {
    //         $('#reponseSearchDemo').html("")
    //         $('#searchResultDemo').html("未在库中搜索到相似人脸或图片格式不符")
    //         $('#resultShowSearchDemo').html("")
    //         return false
    //     }
    // });





	});
//百度地图引入
//创建和初始化地图函数：
    function initMap(){
        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
        addMarker();//向地图中添加marker
    }
    
    //创建地图函数：
    function createMap(){
        var map = new BMap.Map("dituContent");//在百度地图容器中创建一个地图
        var point = new BMap.Point(113.951977,22.53999);//定义一个中心点坐标
        map.centerAndZoom(point,18);//设定地图的中心点和坐标并将地图显示在地图容器中
        window.map = map;//将map变量存储在全局
    }
    
    //地图事件设置函数：
    function setMapEvent(){
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    }
    
    //地图控件添加函数：
    function addMapControl(){
        //向地图中添加缩放控件
	var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
	map.addControl(ctrl_nav);
        //向地图中添加缩略图控件
	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
	map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
	var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
	map.addControl(ctrl_sca);
    }
    
    //标注点数组
    var markerArr = [{title:"深圳市恩钛控股有限公司",content:"联系电话：0755-86537776",point:"113.951802|22.539898",isOpen:1,icon:{w:23,h:25,l:46,t:21,x:9,lb:12}}
		 ];
    //创建marker
    function addMarker(){
        for(var i=0;i<markerArr.length;i++){
            var json = markerArr[i];
            var p0 = json.point.split("|")[0];
            var p1 = json.point.split("|")[1];
            var point = new BMap.Point(p0,p1);
			var iconImg = createIcon(json.icon);
            var marker = new BMap.Marker(point,{icon:iconImg});
			var iw = createInfoWindow(i);
			var label = new BMap.Label(json.title,{"offset":new BMap.Size(json.icon.lb-json.icon.x+10,-20)});
			marker.setLabel(label);
            map.addOverlay(marker);
            label.setStyle({
                        borderColor:"#808080",
                        color:"#333",
                        cursor:"pointer"
            });
			
			(function(){
				var index = i;
				var _iw = createInfoWindow(i);
				var _marker = marker;
				_marker.addEventListener("click",function(){
				    this.openInfoWindow(_iw);
			    });
			    _iw.addEventListener("open",function(){
				    _marker.getLabel().hide();
			    })
			    _iw.addEventListener("close",function(){
				    _marker.getLabel().show();
			    })
				label.addEventListener("click",function(){
				    _marker.openInfoWindow(_iw);
			    })
				if(!!json.isOpen){
					label.hide();
					_marker.openInfoWindow(_iw);
				}
			})()
        }
    }
    //创建InfoWindow
    function createInfoWindow(i){
        var json = markerArr[i];
        var iw = new BMap.InfoWindow("<b class='iw_poi_title' title='" + json.title + "'>" + json.title + "</b><div class='iw_poi_content'>"+json.content+"</div>");
        return iw;
    }
    //创建一个Icon
    function createIcon(json){
        var icon = new BMap.Icon("http://app.baidu.com/map/images/us_mk_icon.png", new BMap.Size(json.w,json.h),{imageOffset: new BMap.Size(-json.l,-json.t),infoWindowOffset:new BMap.Size(json.lb+5,1),offset:new BMap.Size(json.x,json.h)})
        return icon;
    }

	//判断当前浏览器
	function getOs()
	{
		var OsObject = "";
		//Safari浏览器
		if(isSafari=navigator.userAgent.indexOf("Safari")>0) {
			$("body").addClass("safari_body");
			$(".inputting").css("line-height","0px");
		}
	}
    initMap();//创建和初始化地图
	