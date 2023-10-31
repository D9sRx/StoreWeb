<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        response.setContentType("text/html;charset=utf-8");
        if(session.getAttribute("uname")!=null){
            session.invalidate();
            out.println("退出成功，3秒后跳转到登录页面");
            response.setHeader("refresh","3;url=login.jsp");
        }
    %>
</body>
</html>
