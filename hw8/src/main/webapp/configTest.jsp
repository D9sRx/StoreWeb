<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>获取的初始化参数</h1><br>
    学校是:<%=config.getInitParameter("school")%><br>
    专业是:<%=config.getInitParameter("major")%>
</body>
</html>
