<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title></title>
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/jquery.fullPage.css"/>
    <!--竖屏切换css-->
    <link rel="stylesheet" href="css/index.css"/>
    <script type="text/javascript" src="js/lib/jquery.min.js"></script>
    <script type="text/javascript" src="js/lib/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/lib/jquery.fullPage.js"></script>
    <!--竖屏切换js-->
    <script type="text/javascript" src="js/index.js"></script>
</head>

<body>
<div id="fullpage">

    <!--第一屏(主页)-->
    <div class="section" id="section1">
        <div class="container-fluid fluid_one">
            <nav class="navbar navbar-default" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#target-menu">
                        <span class="sr-only"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <div class="navbar-brand"><img src="img/index/one/logo.png"></div>
                </div>
                <div class="collapse navbar-collapse navbar-right" id="target-menu">
                    <ul class="nav navbar-nav">
                        <li class="active">
                            <a href="#">主页</a>
                        </li>
                        <li class="class-dropdown">
                            <a href="#" class="dropdown-toggle click_css" data-toggle="dropdown">核心技术<span
                                    class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a class="click_css" href="#page2">人脸检测</a>
                                </li>
                                <li>
                                    <a class="click_css" href="#page3">人脸对比</a>
                                </li>
                                <li>
                                    <a class="click_css" href="#page4">人脸搜索</a>
                                </li>
                                <li>
                                    <a class="click_css" href="#page5">人脸属性</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a class="click_css" href="#page6">联系我们</a>
                        </li>
                        <li>
                            <a class="click_css" href="#">注册</a>
                        </li>
                        <li>
                            <a class="click_css" href="#">登录</a>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="text-img">
                <img class="img-responsive center-block" src="img/index/one/text_img.png"/>
            </div>
            <div class="prospect">
                <img class="img-responsive center-block" src="img/index/one/prospect.png"/>
            </div>
        </div>
    </div>
    <!--第二屏(人脸检测)-->
    <div class="section">
        <div class="container-fluid fluid_two" id="fluid_two">
            <!--右侧倒三角背景以及文字-->
            <div class="title_bg2">
                <img class="img-responsive center-block" src="img/index/two/title_img1.png"/>
                <div class="title_text2">人脸检测</div>
                <div class="introduce_text2">检测并定位图片中的人脸，返回<br/>高精度的人脸<br/>框坐标。</div>
            </div>
            <div class="formal_box2">
                <div class="left_formal2">
                    <!--左侧代码框-->
                    <div class="code_box2">
                        <div class="code_title2">
                            <span><img class="code_img2" src="img/index/two/icon-bottom.png"></span>&nbsp;&nbsp;Response
                            JSON
                        </div>
                        <div class="code_text2">
									<textarea disabled="disabled">


									</textarea>
                        </div>
                    </div>
                    <!--左侧人脸属性框-->
                    <div class="attribute_box2">
                        <div class="attribute_title2">
                            <span><img class="attribute_img2" src="img/index/two/icon-right.png"></span>&nbsp;&nbsp;人脸属性
                        </div>
                        <div class="attribute_text2"></div>
                    </div>
                </div>
                <div class="middle_formal2">
                    <div class="detect_box2" reveal_sign="width">
                        <div class="detect_img2"><img src="img/index/two/max_img1.png"></div>
                    </div>
                </div>
                <div class="right_formal2">
                    <div class="bottom_left_f2">
                        <div class="upload2-1">
                            <div class="upload_btn2">
                                本地上传
                                <input class="upload_file5" type="file" value="本地上传">
                            </div>
                        </div>
                        <div class="upload2-2">
                            <div class="upload_text5">
                                <input class="upload_mintext5" type="text" placeholder="请输入图片URL"/>
                                <div class="detect_btn5">检测</div>
                            </div>
                        </div>
                        <div class="upload2-3">
                            <div class="upload5-3img">
                                <div class="detect_fiveimg5" reveal_sign="width"><img class="img_active"
                                                                                      src="img/index/two/max_img1.png">
                                </div>
                                <div class="detect_fiveimg5" reveal_sign="width"><img src="img/index/two/max_img2.png">
                                </div>
                                <div class="detect_fiveimg5" reveal_sign="width"><img src="img/index/two/max_img3.png">
                                </div>
                                <div class="detect_fiveimg5" reveal_sign="width"><img src="img/index/two/max_img4.png">
                                </div>
                                <div class="detect_fiveimg5" reveal_sign="width"><img src="img/index/two/max_img5.png">
                                </div>
                                <br style="clear:both;"/><!--清除浮动-->
                            </div>
                        </div>
                    </div>
                    <!--<div class="detect_dome2">
                        <div class="upload2">
                            <div class="upload_btn2">
                                本地上传
                                <input class="upload_file2" type="file" value="本地上传">
                            </div>
                            <div class="upload_text2">
                                <input class="upload_mintext2" type="text" placeholder="请输入图片URL"/>
                                <div class="detect_btn2">检测</div>
                            </div>
                            <div class="detect_minimg2">
                                <div class="detect_fiveimg2" reveal_sign="width"><img class="img_active" src="img/index/two/max_img1.png"></div>
                                <div class="detect_fiveimg2" reveal_sign="width"><img src="img/index/two/max_img2.png"></div>
                                <div class="detect_fiveimg2" reveal_sign="width"><img src="img/index/two/max_img3.png"></div>
                                <div class="detect_fiveimg2" reveal_sign="width"><img src="img/index/two/max_img4.png"></div>
                                <div class="detect_fiveimg2" reveal_sign="width"><img src="img/index/two/max_img5.png"></div>
                            </div>
                        </div>
                    </div>-->
                </div>
            </div>

            <!--右下方功能演示-->

        </div>
    </div>
    <!--第三屏(人脸对比)-->
    <div class="section">
        <div class="container-fluid fluid_three">
            <!--左侧倒三角背景以及文字-->
            <div class="title_bg3">
                <img class="img-responsive center-block" src="img/index/three/title_img2.png">
                <div class="title_text3">人脸对比</div>
                <div class="introduce_text3">分析两张人脸属<br/>于同一个人的<br/>可能性大小</div>
            </div>
            <!--左侧对比结果框-->
            <div class="results_box3">
                <div class="results_title3">
                    <span><img class="results_img3" src="img/index/two/icon-bottom.png"></span>&nbsp;&nbsp;对比结果
                </div>
                <div class="results_text3">是否同一个人：可能性很高</div>
            </div>
            <!--左侧代码框-->
            <div class="code_box3">
                <div class="code_title3">
                    <span><img class="code_img23" src="img/index/two/icon-right.png"></span>&nbsp;&nbsp;Response JSON
                </div>
                <div class="code_text3">
							<textarea disabled="disabled">{
  "faces1": [
    {
      "face_rectangle": {
        "width": 264,
        "top": 211,
        "left": 125,
        "height": 264
      },
      "face_token": "d61fb1d231d2d6c7fbfdeafc2a4fb31d"
    }
  ],
  "faces2": [
    {
      "face_rectangle": {
        "width": 260,
        "top": 215,
        "left": 133,
        "height": 260
      },
      "face_token": "ebf0fc16a382f6052467b20c272d871f"
    }
  ],
  "time_used": 537,
  "thresholds": {
    "1e-3": 62.327,
    "1e-5": 73.975,
    "1e-4": 69.101
  },
  "confidence": 88.488,
  "image_id2": "SPedNTNWqgPmupahuTaSZQ==",
  "image_id1": "uADw4RAdkIsQxhaDMwiqig==",
  "request_id": "1504517581,585348db-0d80-442d-bbd3-6021a1c82006"
}
							</textarea>
                </div>
            </div>
            <!--右侧人脸对比演示-->
            <div class="contrast_box3">
                <!--左侧图片上传区-->
                <div class="results_leftimg3">
                    <div class="results_maxbox3">
                        <div class="results_maximg3" reveal_sign="width">
                            <img class="showcasing_img3" src="img/index/two/max_img5.png"/>
                        </div>
                    </div>
                    <div class="contrast_btn3">
                        本地上传
                        <input class="contrast_file3" type="file" value="本地上传">
                    </div>
                    <div class="upload_text3">
                        <input class="upload_mintext3" type="text" placeholder="请输入图片URL"/>
                        <div class="detect_btn3">检测</div>
                    </div>
                    <div class="detect_minimg3">
                        <div class="detect_fiveimg3" reveal_sign="width"><img src="img/index/two/max_img1.png"></div>
                        <div class="detect_fiveimg3" reveal_sign="width"><img src="img/index/two/max_img2.png"></div>
                        <div class="detect_fiveimg3" reveal_sign="width"><img src="img/index/two/max_img3.png"></div>
                        <div class="detect_fiveimg3" reveal_sign="width"><img src="img/index/two/max_img4.png"></div>
                        <div class="detect_fiveimg3" reveal_sign="width"><img class="img_active"
                                                                              src="img/index/two/max_img5.png"></div>
                    </div>
                </div>
                <!--右侧图片上传区-->
                <div class="results_gightimg3">
                    <div class="results_maxbox3">
                        <div class="results_maximg3" reveal_sign="width">
                            <img class="showcasing_img3" src="img/index/two/max_img5.png"/>
                        </div>
                    </div>
                    <div class="contrast_btn3">
                        本地上传
                        <input class="contrast_file3" type="file" value="本地上传">
                    </div>
                    <div class="upload_text3">
                        <input class="upload_mintext3" type="text" placeholder="请输入图片URL"/>
                        <div class="detect_btn3">检测</div>
                    </div>
                    <div class="detect_minimg3">
                        <div class="detect_fiveimg3" reveal_sign="width"><img src="img/index/two/max_img1.png"></div>
                        <div class="detect_fiveimg3" reveal_sign="width"><img src="img/index/two/max_img2.png"></div>
                        <div class="detect_fiveimg3" reveal_sign="width"><img src="img/index/two/max_img3.png"></div>
                        <div class="detect_fiveimg3" reveal_sign="width"><img src="img/index/two/max_img4.png"></div>
                        <div class="detect_fiveimg3" reveal_sign="width"><img class="img_active"
                                                                              src="img/index/two/max_img5.png"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--第四屏(人脸搜索)-->
    <div class="section">
        <div class="container-fluid fluid_four">
            <!--顶部倒三角背景以及文字-->
            <div class="title_bg4">
                <img class="img-responsive center-block" src="img/index/four/title_img3.png"/>
                <div class="title_text4">人脸搜索</div>
                <div class="introduce_text4">针对一个新的人脸，在一个已有的人脸<br/>集合中搜索相似的人脸。</div>
            </div>
            <!--左侧搜索框-->
            <div class="results_gightimg4">
                <div class="results_maxbox4" reveal_sign="width">
                    <div class="results_maximg4">
                        <img class="showcasing_img4" src="img/index/two/max_img2.png"/>
                    </div>
                </div>
                <div class="contrast_btn4">
                    本地上传
                    <input class="contrast_file4" type="file" value="本地上传">
                </div>
                <div class="upload_text4">
                    <input class="upload_mintext4" type="text" placeholder="请输入图片URL"/>
                    <div class="detect_btn4">检测</div>
                </div>
                <div class="detect_minimg4">
                    <div class="detect_fiveimg4"><img src="img/index/two/max_img1.png"></div>
                    <div class="detect_fiveimg4"><img class="img_active" src="img/index/two/max_img2.png"></div>
                    <div class="detect_fiveimg4"><img src="img/index/two/max_img3.png"></div>
                    <div class="detect_fiveimg4"><img src="img/index/two/max_img4.png"></div>
                    <div class="detect_fiveimg4"><img src="img/index/two/max_img5.png"></div>
                </div>
            </div>
            <!--中间搜索结果-->
            <div class="results_gightimg3_1">
                <!--左侧结果图片-->
                <div class="code_box4">
                    <div class="code_title4">
                        &nbsp;&nbsp;搜索结果
                    </div>
                    <div class="code_text3">
                        <div class="results_graphic4">
                            <div class="graphic_img4" reveal_sign="width"><img src="img/index/four/results_img1.png">
                            </div>
                            <div class="graphic_text">结果1</div>
                        </div>
                        <div class="results_graphic4">
                            <div class="graphic_img4" reveal_sign="width"><img src="img/index/four/results_img2.png">
                            </div>
                            <div class="graphic_text">结果2</div>
                        </div>
                        <div class="results_graphic4">
                            <div class="graphic_img4" reveal_sign="width"><img src="img/index/four/results_img3.png">
                            </div>
                            <div class="graphic_text">结果3</div>
                        </div>
                        <div class="results_graphic4">
                            <div class="graphic_img4" reveal_sign="width"><img src="img/index/four/results_img4.png">
                            </div>
                            <div class="graphic_text">结果4</div>
                        </div>
                        <div class="results_graphic4">
                            <div class="graphic_img4" reveal_sign="width"><img src="img/index/four/results_img5.png">
                            </div>
                            <div class="graphic_text">结果5</div>
                        </div>
                    </div>
                </div>
                <!--左侧结果文字-->
                <div class="results_box4">
                    <div class="results_title4">
                        &nbsp;&nbsp;搜索结果
                    </div>
                    <div class="results_text4">
                        <p>结果1：是同一个人可能性很高</p>
                        <p>结果2：是同一个人可能性高</p>
                        <p>结果3：是同一个人可能性低</p>
                        <p>结果4：是同一个人可能性低</p>
                        <p>结果5：是同一个人可能性低</p>
                    </div>
                </div>

            </div>
            <!--右边代码展示-->
            <div class="results_gightimg3_2">
                <!--上边代码框-->
                <div class="code_box4_2">
                    <div class="code_title4">
                        <span><img class="code_img24" src="img/index/two/icon-bottom.png"></span>&nbsp;&nbsp;Response
                        JSON
                    </div>
                    <div class="code_text4">
									<textarea disabled="disabled">{
  "time_used": 610,
  "thresholds": {
    "1e-3": 62.327,
    "1e-5": 73.975,
    "1e-4": 69.101
  },
  "faces": [
    {
      "face_token": "a2248a612eb3c6c829473b5b92bfbd3f",
      "face_rectangle": {
        "width": 264,
        "top": 211,
        "height": 264,
        "left": 125
      }
    }
  ],
  "results": [
    {
      "confidence": 96.688,
      "user_id": "",
      "face_token": "436c22eb84caa2c6d3f310d7ae998d50"
    },
    {
      "confidence": 88.72,
      "user_id": "",
      "face_token": "0241ef20cb128bbf6110a1e2a1c2ad8f"
    },
    {
      "confidence": 53.102,
      "user_id": "",
      "face_token": "6840793274dd9560e3712ee446c4aafa"
    },
    {
      "confidence": 45.328,
      "user_id": "",
      "face_token": "5923ea0781c2f0829694ffa73ba5e41e"
    },
    {
      "confidence": 29.972,
      "user_id": "",
      "face_token": "07341c61e51eb28a78c847d56a8f5534"
    }
  ],
  "image_id": "uADw4RAdkIsQxhaDMwiqig==",
  "request_id": "1504599434,c4c016d8-6316-4872-a4be-22ddcb6d79fe"
}
									</textarea>
                    </div>
                </div>
                <!--下边图片集合框-->
                <div class="attribute_box4">
                    <div class="attribute_title4">
                        <span><img class="attribute_img4" src="img/index/two/icon-right.png"></span>&nbsp;&nbsp;管理您的人脸图片集合
                    </div>
                    <div class="attribute_text4"></div>
                </div>
            </div>
        </div>
    </div>
    <!--第五屏(人脸属性)-->
    <div class="section">
        <div class="container-fluid fluid_five">
            <!--左侧倒三角背景以及文字-->
            <div class="title_bg5">
                <img class="img-responsive center-block" src="img/index/five/title_img4.png"/>
                <div class="title_text5">人脸检测</div>
                <div class="introduce_text5">检测并定位图片中的人脸，返回<br/>高精度的人脸<br/>框坐标。</div>
            </div>
            <!--正式内容-->
            <div class="formal_box5">
                <div class="left_formal5">
                    <div class="bottom_left_f5">
                        <div class="upload5-1">
                            <div class="upload_btn5">
                                本地上传
                                <input class="upload_file5" type="file" value="本地上传">
                            </div>
                        </div>
                        <div class="upload5-2">
                            <div class="upload_text5">
                                <input class="upload_mintext5" type="text" placeholder="请输入图片URL"/>
                                <div class="detect_btn5">检测</div>
                            </div>
                        </div>
                        <div class="upload5-3">
                            <div class="upload5-3img">
                                <div class="detect_fiveimg5" reveal_sign="width"><img class="img_active"
                                                                                      src="img/index/two/max_img1.png">
                                </div>
                                <div class="detect_fiveimg5" reveal_sign="width"><img src="img/index/two/max_img2.png">
                                </div>
                                <div class="detect_fiveimg5" reveal_sign="width"><img src="img/index/two/max_img3.png">
                                </div>
                                <div class="detect_fiveimg5" reveal_sign="width"><img src="img/index/two/max_img4.png">
                                </div>
                                <div class="detect_fiveimg5" reveal_sign="width"><img src="img/index/two/max_img5.png">
                                </div>
                                <br style="clear:both;"/><!--清除浮动-->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="middle_formal5">
                    <div class="maximg_box5">
                        <div class="img_bgbox5" reveal_sign="width">
                            <div class="detect_img5">
                                <img src="img/index/two/max_img1.png">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="right_formal5">
                    <!--右侧代码框-->
                    <div class="code_box5">
                        <div class="code_title5">
                            <span><img class="code_img25" src="img/index/two/icon-bottom.png"></span>&nbsp;&nbsp;Response
                            JSON
                        </div>
                        <div class="code_text5">
									<textarea disabled="disabled">{
  "image_id": "O2alrpeRIXFejHWe6WlRqw==",
  "request_id": "1504661393,6d2cb7bd-3a42-4022-b6dd-5ac6b130b003",
  "time_used": 910,
  "faces": [
    {
      "landmark": {
        "mouth_upper_lip_left_contour2": {
          "y": 489,
          "x": 519
        },
        "mouth_upper_lip_top": {
          "y": 484,
          "x": 527
        },
        "mouth_upper_lip_left_contour1": {
          "y": 483,
          "x": 521
        },
        "left_eye_upper_left_quarter": {
          "y": 408,
          "x": 511
        },
        "left_eyebrow_lower_middle": {
          "y": 395,
          "x": 512
        },
        "mouth_upper_lip_left_contour3": {
          "y": 494,
          "x": 524
        },
        "left_eyebrow_lower_left_quarter": {
          "y": 394,
          "x": 505
        },
        "right_eyebrow_lower_left_quarter": {
          "y": 392,
          "x": 568
        },
        "right_eye_pupil": {
          "y": 409,
          "x": 577
        },
        "mouth_lower_lip_right_contour1": {
          "y": 498,
          "x": 547
        },
        "mouth_lower_lip_left_contour2": {
          "y": 503,
          "x": 522
        },
        "mouth_lower_lip_right_contour3": {
          "y": 508,
          "x": 544
        },
        "mouth_lower_lip_right_contour2": {
          "y": 504,
          "x": 556
        },
        "contour_chin": {
          "y": 546,
          "x": 535
        },
        "contour_left9": {
          "y": 536,
          "x": 522
        },
        "left_eye_lower_right_quarter": {
          "y": 415,
          "x": 523
        },
        "mouth_lower_lip_top": {
          "y": 498,
          "x": 531
        },
        "right_eyebrow_upper_middle": {
          "y": 381,
          "x": 583
        },
        "right_eyebrow_left_corner": {
          "y": 391,
          "x": 552
        },
        "right_eye_lower_right_quarter": {
          "y": 414,
          "x": 587
        },
        "right_eye_bottom": {
          "y": 415,
          "x": 577
        },
        "contour_left7": {
          "y": 506,
          "x": 511
        },
        "contour_left6": {
          "y": 491,
          "x": 506
        },
        "contour_left5": {
          "y": 475,
          "x": 503
        },
        "contour_left4": {
          "y": 460,
          "x": 499
        },
        "contour_left3": {
          "y": 444,
          "x": 499
        },
        "contour_left2": {
          "y": 429,
          "x": 501
        },
        "contour_left1": {
          "y": 415,
          "x": 504
        },
        "left_eye_lower_left_quarter": {
          "y": 415,
          "x": 511
        },
        "contour_right1": {
          "y": 415,
          "x": 660
        },
        "contour_right3": {
          "y": 459,
          "x": 659
        },
        "contour_right2": {
          "y": 437,
          "x": 660
        },
        "mouth_left_corner": {
          "y": 498,
          "x": 522
        },
        "contour_right4": {
          "y": 482,
          "x": 654
        },
        "contour_right7": {
          "y": 531,
          "x": 607
        },
        "left_eyebrow_left_corner": {
          "y": 395,
          "x": 499
        },
        "nose_right": {
          "y": 461,
          "x": 552
        },
        "nose_tip": {
          "y": 446,
          "x": 516
        },
        "contour_right5": {
          "y": 503,
          "x": 644
        },
        "nose_contour_lower_middle": {
          "y": 464,
          "x": 523
        },
        "right_eye_top": {
          "y": 404,
          "x": 576
        },
        "mouth_lower_lip_left_contour3": {
          "y": 508,
          "x": 525
        },
        "right_eye_right_corner": {
          "y": 411,
          "x": 595
        },
        "mouth_upper_lip_right_contour1": {
          "y": 482,
          "x": 534
        },
        "mouth_upper_lip_right_contour2": {
          "y": 488,
          "x": 550
        },
        "right_eyebrow_lower_right_quarter": {
          "y": 393,
          "x": 597
        },
        "left_eye_left_corner": {
          "y": 412,
          "x": 507
        },
        "mouth_right_corner": {
          "y": 497,
          "x": 566
        },
        "right_eye_lower_left_quarter": {
          "y": 414,
          "x": 568
        },
        "left_eyebrow_right_corner": {
          "y": 395,
          "x": 527
        },
        "left_eyebrow_lower_right_quarter": {
          "y": 396,
          "x": 519
        },
        "right_eye_center": {
          "y": 411,
          "x": 577
        },
        "left_eye_pupil": {
          "y": 411,
          "x": 518
        },
        "nose_left": {
          "y": 455,
          "x": 511
        },
        "mouth_lower_lip_left_contour1": {
          "y": 498,
          "x": 525
        },
        "left_eye_upper_right_quarter": {
          "y": 408,
          "x": 523
        },
        "right_eyebrow_lower_middle": {
          "y": 391,
          "x": 583
        },
        "left_eye_center": {
          "y": 412,
          "x": 517
        },
        "contour_left8": {
          "y": 521,
          "x": 516
        },
        "contour_right9": {
          "y": 546,
          "x": 560
        },
        "right_eye_left_corner": {
          "y": 413,
          "x": 560
        },
        "left_eyebrow_upper_left_quarter": {
          "y": 389,
          "x": 504
        },
        "left_eye_bottom": {
          "y": 416,
          "x": 517
        },
        "left_eye_right_corner": {
          "y": 413,
          "x": 528
        },
        "right_eyebrow_upper_left_quarter": {
          "y": 383,
          "x": 567
        },
        "contour_right8": {
          "y": 540,
          "x": 584
        },
        "right_eyebrow_right_corner": {
          "y": 396,
          "x": 611
        },
        "right_eye_upper_left_quarter": {
          "y": 406,
          "x": 567
        },
        "left_eyebrow_upper_middle": {
          "y": 388,
          "x": 512
        },
        "right_eyebrow_upper_right_quarter": {
          "y": 384,
          "x": 599
        },
        "nose_contour_left1": {
          "y": 416,
          "x": 529
        },
        "nose_contour_left2": {
          "y": 442,
          "x": 516
        },
        "nose_contour_left3": {
          "y": 460,
          "x": 516
        },
        "nose_contour_right1": {
          "y": 417,
          "x": 551
        },
        "nose_contour_right2": {
          "y": 446,
          "x": 548
        },
        "mouth_lower_lip_bottom": {
          "y": 509,
          "x": 532
        },
        "contour_right6": {
          "y": 519,
          "x": 628
        },
        "nose_contour_right3": {
          "y": 463,
          "x": 537
        },
        "left_eye_top": {
          "y": 407,
          "x": 516
        },
        "mouth_upper_lip_right_contour3": {
          "y": 493,
          "x": 545
        },
        "left_eyebrow_upper_right_quarter": {
          "y": 390,
          "x": 520
        },
        "right_eye_upper_right_quarter": {
          "y": 406,
          "x": 587
        },
        "mouth_upper_lip_bottom": {
          "y": 492,
          "x": 529
        }
      },
      "attributes": {
        "emotion": {
          "sadness": 1.093,
          "neutral": 96.9,
          "disgust": 0.179,
          "anger": 0.087,
          "surprise": 0.12,
          "fear": 0.087,
          "happiness": 1.536
        },
        "gender": {
          "value": "Female"
        },
        "age": {
          "value": 34
        },
        "eyestatus": {
          "left_eye_status": {
            "normal_glass_eye_open": 0.002,
            "no_glass_eye_close": 0,
            "occlusion": 0.168,
            "no_glass_eye_open": 99.83,
            "normal_glass_eye_close": 0,
            "dark_glasses": 0
          },
          "right_eye_status": {
            "normal_glass_eye_open": 0.245,
            "no_glass_eye_close": 0,
            "occlusion": 0,
            "no_glass_eye_open": 99.754,
            "normal_glass_eye_close": 0,
            "dark_glasses": 0
          }
        },
        "glass": {
          "value": "None"
        },
        "headpose": {
          "yaw_angle": 45.32558,
          "pitch_angle": -5.7740755,
          "roll_angle": 5.490004
        },
        "blur": {
          "blurness": {
            "threshold": 50,
            "value": 0.086
          },
          "motionblur": {
            "threshold": 50,
            "value": 0.086
          },
          "gaussianblur": {
            "threshold": 50,
            "value": 0.086
          }
        },
        "smile": {
          "threshold": 30.1,
          "value": 6.339
        },
        "facequality": {
          "threshold": 70.1,
          "value": 0.006
        },
        "ethnicity": {
          "value": "White"
        }
      },
      "face_rectangle": {
        "width": 161,
        "top": 381,
        "left": 505,
        "height": 161
      },
      "face_token": "bc0fc7394450b637ea1a6e8931288d72"
    }
  ]
}
									</textarea>
                        </div>
                    </div>
                    <!--右侧人脸属性框-->
                    <div class="attribute_box5">
                        <div class="attribute_title5">
                            <span><img class="attribute_img5" src="img/index/two/icon-right.png"></span>&nbsp;&nbsp;人脸属性
                        </div>
                        <div class="attribute_text5"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--第六屏(联系我们)-->
    <div class="section">
        <div class="container-fluid fluid_six">555555555</div>
    </div>

</div>
</body>

</html>