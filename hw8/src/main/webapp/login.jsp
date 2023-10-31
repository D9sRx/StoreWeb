<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div align="center">
    <h1>用户登录</h1>
    <form action="/check.jsp" method="post">
        用户名:<input type="text" name="username" placeholder="请输入用户名"><br>
        密码&nbsp;&nbsp;&nbsp;码:<input type="password" name="pwd" placeholder="请输入密码"><br>
        <input type="submit" value="登录"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="reset" value="重填"/>
    </form>
</div>
</body>
</html>