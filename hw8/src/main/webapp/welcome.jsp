<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html><head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
欢迎您：
<%
    response.setContentType("text/html;charset=utf-8");
    if(!"jhy".equals(session.getAttribute("uname"))){
        out.println("非法用户，3秒后跳转到登录页面");
        response.setHeader("refresh","3;url=login.jsp");
    }else{
        out.println(session.getAttribute("uname"));
        out.println("<a href='logout.jsp'>注销</a>");
    }
%>

</body></html>
