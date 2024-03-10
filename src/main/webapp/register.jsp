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
        h1 {
            text-align: center;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="email"],
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
    </style>
</head>
<body>
    <div class="container">
        <h1>User Registration</h1>
        <form action="UserController" method="post">
            <label for="nickname">Username:</label>
            <input type="text" id="nickname" name="nickname" required><br>
            
            <label for="username">Name:</label>
            <input type="text" id="username" name="username" required><br>
            
            <label for="surnames">Surnames:</label>
            <input type="text" id="surnames" name="surnames"><br>
            
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>
            
            <label for="repeatPassword">Repeat Password:</label>
            <input type="password" id="repeatPassword" name="repeatPassword" required><br>
            
            <input type="hidden" name="action" value="registerUser">
            <input type="submit" value="Register">
        </form>
    </div>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var form = document.querySelector('form');
            var passwordInput = document.getElementById('password');
            var repeatPasswordInput = document.getElementById('repeatPassword');

            form.addEventListener('submit', function(event) {
                if (passwordInput.value !== repeatPasswordInput.value) {
                    alert('Passwords do not match');
                    event.preventDefault();
                }
            });
        });
    </script>
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
</body>
</html>
