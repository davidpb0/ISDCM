<%-- 
    Document   : register
    Created on : 25 feb 2024, 19:26:54
    Author     : davidpb0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Registration</title>
</head>
<body>
    <h1>User Registration</h1>
    <form action="UserController" method="post">
        <label for="nickname">Nickname:</label>
        <input type="text" id="nickname" name="nickname" required><br><br>
        
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        
        <label for="surnames">Surnames:</label>
        <input type="text" id="surnames" name="surnames"><br><br>
        
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <input type="submit" value="Register">
    </form>
</body>
</html>
