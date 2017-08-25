<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>

<body>
<a href="/customer/register">jump to register</a>
<h2>H请求提交测试页面</h2>
<hr>
<h3>n-tech/v0/detect  file</h3>
<form action="/n-tech/v0/detect" method="post"  enctype="multipart/form-data">
picture path:<input type="file" name="photo"><br>
    <input type="submit" value="submit">
</form>

<h3>n-tech/v0/detect url</h3>
<form action="/n-tech/v0/detect" method="post">
picture path url:<input type="input" name="photo" value="http://static.findface.pro/sample.jpg"><br>
    <input type="submit" value="submit">
</form>
<hr>
<h3>n-tech/v0/verify file</h3>
<form action="/n-tech/v0/verify" method="post"  enctype="multipart/form-data">
    photo1:<input type="file" name="photo1" ><br>
    photo2:<input type="file" name="photo2" ><br>
    <input type="submit" value="submit">
</form>
<h3>n-tech/v0/verify url</h3>
<form action="/n-tech/v0/verify" method="post">
    photo1:<input type="input" name="photo1" value="http://static.findface.pro/sample.jpg"><br>
    photo2:<input type="input" name="photo2" value="http://static.findface.pro/sample.jpg"><br>
    <input type="submit" value="submit">
</form>

<hr>
</body>
</html>
