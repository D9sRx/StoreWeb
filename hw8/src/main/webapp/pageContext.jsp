<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%session.setAttribute("test","session");%>
    <%request.setAttribute("test","request");%>
    <%pageContext.setAttribute("test","page");%>
    <%application.setAttribute("test","application");%>
    <%=pageContext.getAttribute("test", pageContext.SESSION_SCOPE)%>
    <%=pageContext.getAttribute("test", pageContext.REQUEST_SCOPE)%>
    <%=pageContext.getAttribute("test", pageContext.PAGE_SCOPE)%>
    <%=pageContext.getAttribute("test", pageContext.APPLICATION_SCOPE)%>
    <br>
    <%=pageContext.findAttribute("test")%>
</body>
</html>

