<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html><head>
    <meta charset="UTF-8">
    <title>登录验证</title>
</head><body>
<%
    request.setCharacterEncoding("utf-8");
    String name = request.getParameter("username");
    String pwd = request.getParameter("pwd");
    if(name.equals("jhy") && pwd.equals("123456")){
        session.setMaxInactiveInterval(60*60);
        session.setAttribute("uname",name);
        Cookie cookie = new Cookie("JSESSIONID",session.getId());
        cookie.setMaxAge(60*60);
        response.addCookie(cookie);
        response.sendRedirect("/welcome.jsp");
    }else{
        out.print("用户名或密码错误，3秒后跳转到登录页面");
        response.setHeader("refresh","3;url=login.jsp");
    }
%>
</body></html>

