<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>

<%
    out.println("getRequestURL(): " + request.getRequestURL() + "<br>");
    out.println("getRequestURI(): " + request.getRequestURI() + "<br>");
    out.println("getContextPath(): " + request.getContextPath() + "<br>");
    out.println("getServletPath(): " + request.getServletPath() + "<br>");
    out.println("getPathInfo(): " + request.getPathInfo() + "<br>");
%>

</body>
</html>