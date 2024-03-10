<%-- 
    Document   : login
    Created on : 2 mar 2024, 19:24:48
    Author     : alumne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 400px;
            margin: 50px auto 15px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #18314F;
            border: none;
            border-radius: 3px;
            color: #fff;
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        .error-container {
            max-width: 400px;
            margin: 0px auto 0px auto;
            padding: 10px;
            background-color: #FFA98F;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            font-family: fantasy;
            font-weight: 700; 
              
        }
        .link-container {
            max-width: 400px;
            margin: 0px auto 0px auto;
            padding: 10px;
            font-family: fantasy;
            font-weight: 700; 
              
        }
        .register-link {
            display: inline-block;
            padding: 10px;
            color: blue;
            cursor: pointer;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>User Login</h1>
        <form action="UserController" method="post">
            <label for="nickname">Nickname:</label>
            <input type="text" id="nickname" name="nickname" required><br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>
            <input type="hidden" name="action" value="loginUser">
            <input type="submit" value="Sign In">
        </form>
    </div>
    <% 
    HttpSession sessionObj = request.getSession();
    String errorMessage = (String) sessionObj.getAttribute("errorMessage");
    System.out.println(errorMessage);
    if (errorMessage != null) { 
    %>
        <div class="error-container">
            <%= errorMessage %>
        </div>
        <% 
        sessionObj.removeAttribute("errorMessage");
        %>
    <% } %>
    <div>
        <form action="register.jsp" method="get">
            <div class="link-container">
                <span>Don't have an account yet?</span>
                <a href="register.jsp" class="register-link">Register here</a>
            </div>
        </form>
    </div>
</body>
</html>


