<%-- 
    Document   : home
    Created on : 25 feb 2024, 19:59:34
    Author     : davidpb0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello <%= session.getAttribute("user") %></h1>
    </body>
    <form action="UserController" method="post">
        <input type="hidden" name="action" value="logoutUser">
        <button type="submit">Logout</button>
    </form>
</html>
