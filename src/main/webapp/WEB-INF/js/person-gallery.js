$(document).ready(function () {


    // var mylib;

    var app = new Vue({
        el: '#person-gallery',
        data: {
            message: "abcdef",
            myLibUrl: "",
            testModel: "",
            seen: true,
            todos: []
        },
        mounted: function () {
            getMyGallery()
        },
        methods: {
            initDemo: function () {

            },
            myLibUploadInput: function () {
                var fileContent = document.getElementById('myLibUpload')
                console.log(fileContent.files[0])
                var file = fileContent.files[0];
                if (!file) {
                    return false;
                }
                var formData = new FormData();
                formData.append("photo", file)
                $.ajax({
                    url: 'addToGallery',
                    type: 'POST',
                    dataType: "json",
                    data: formData,
                    processData: false,
                    contentType: false,
                    async: true,
                    success: function (data) {
                        console.log(data)
//                        dataObj = eval(data.results);
                        app.todos = data;

                    },
                    error: function (data) {
                        alert("no face")
                        return false
                    }
                });
            },


            getMyGallery: function () {
                $.ajax({
                    url: 'getMyGallery',
                    type: 'GET',
                    dataType: "json",
                    success: function (data) {
                        console.log(data)
                        app.todos = data;
                    },
                    error: function (data) {
                        alert("no face")
                        app.todos = data;

                    }
                });
            },

            deleteMyGallery: function (dom) {
                console.log(dom);
                $.ajax({
                    url: 'deleteToGallery',
                    type: 'DELETE',
                    dataType: "json",
                    data: "id:" + dom.alt,
                    success: function (data) {
                        console.log(data)
                        app.todos = data;
//                        $('#' + dom.alt ).remove();
                    },
                    error: function (data) {
                        console.log(data)
                        app.todos = data;
//                        alert("no face")
//                        $('#' + dom.alt ).remove();
                    }
                });
            },

            myLibUrlCheck:function () {
                if(app.myLibUrl==""){
                    return false
                }else{
                    var formData = new FormData();
                    formData.append("photo", app.myLibUrl)
                    $.ajax({
                        url: 'addToGallery',
                        type: 'POST',
                        dataType: "json",
                        data: formData,
                        processData: false,
                        contentType: false,
                        async: true,
                        success: function (data) {
                            console.log(data)
//                        dataObj = eval(data.results);
                            app.todos = data;

                        },
                        error: function (data) {
                            alert("no face")
                            return false
                        }
                    });
                }
            },

            myLibSearchUrl:function () {
                var img = new Image();
                img.src = $("#myLibSearchInputUrl").val();
                app.myLibSearchReq(img);
            },
            myLibSearchReq:function (img) {

                var formData = new FormData();
                // formData.append("n",4);
                formData.append("photo",img.src)
                $.ajax({
                    url: 'getDemoFace',
                    type: 'POST',
                    dataType: "json",
                    data: formData,
                    processData: false,
                    contentType: false,
                    async: true,
                    success: function (data) {
                        $('#imgShow').attr("src",img.src);
                        console.log("success")
                        console.log(data)
                        dataObj=eval(data.results);
                        showResult(dataObj)
                        $('#reponse').html(syntaxHighlight(data))

                    },
                    error: function (data) {
                        // img.src = ""
                        alert("no face")
                        return false
                    }
                });
            },
            myLibSearchUpload(img){
                var imgShow = document.getElementById("imgShow")
                var file = img.files[0];
                if (!file) {
                    return false;
                }
                var reader = new FileReader();
                reader.onloadend = function (e) {
                    console.log("成功读取....");
                    imgShow.src = e.target.result;
                }
                reader.readAsDataURL(file)
                //上传文件时当前的图片内容是文件,对比的对象可能时文件或者url
                var formData = new FormData();
                // formData.append("n",4);
                formData.append("photo",file)
                $.ajax({
                    url: 'getDemoFace',
                    type: 'POST',
                    dataType: "json",
                    data: formData,
                    processData: false,
                    contentType: false,
                    async: true,
                    success: function (data) {
                        dataObj=eval(data.results);
                        showResult(dataObj)
                        $('#reponse').html(syntaxHighlight(data))
                    },
                    error: function (data) {
                        alert("no face")
                        return false
                    }
                });
            }
        }
    });

}); //ready

var preSize=0;

function getMyGallery(){
    $.ajax({
        url: 'getMyGallery',
        type: 'GET',
        dataType: "json",
        success: function (data) {
            console.log(data)
            app.todos=data
            return data;
        },
        error: function (data) {
            alert("no face")
            app.todos=data
           return data;

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
function showResult(dataObj) {

    removeBefore(preSize)
    preSize=0;
    for (var prop in dataObj)
    {
        // console.log("jsonObj[" + prop + "]=" + dataObj[prop]);
        for (var v in dataObj[prop]){
            preSize++;
            var imgEle="<div>第"+preSize+"张</div><img style='' height='200' width='200' src='"+dataObj[prop][v]['face']['thumbnail']+"'>"
            var confidence="第"+preSize+"张是同一个人的可信度是:"+dataObj[prop][v]['confidence']
            $("#searchResult").append("<div  id='divResult"+preSize+"'>"+ confidence +"</div>");
            $("#resultShow").append("<div class='imgboxShow' id='div"+preSize+"'>"+ imgEle +"</div>");
            console.log(dataObj[prop][v]['face']['thumbnail'])
        }
    }
}




function syntaxHighlight(json) {
    if (typeof json != 'string') {
        json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}