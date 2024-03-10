<%-- 
    Document   : login
    Created on : 2 mar 2024, 19:24:48
    Author     : davidpb0
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
            display: flex;
            flex-direction: row;
            height: 100vh;
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
         .left-side {
            flex: 2;
            background-color: #fff;
            padding: 20px;
        }
        .right-side {
            flex: 1;
            background-color: #18314F;
            padding: 20px;
            color: #fff;
        }
        .right-container {
            margin: 50px auto 15px auto;
            padding: 20px;
        }
        .register-button {
            display: block;
            width: 200px; 
            margin: 0 auto;
            padding: 15px; 
            background-color: #4da6ff;
            border: none;
            border-radius: 10px;
            color: #fff;
            cursor: pointer;
            font-size: 18px;
            text-align: center;
            text-decoration: none;
        }

        .register-button:hover {
            background-color: #3385ff;
        }
        
    </style>
</head>
<body>
    <div class="left-side">
        <div class="container">
            <h1>Sign In</h1>
            <form action="UserController" method="post">
                <label for="nickname">Username:</label>
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
    </div>
    <div class="right-side">
        <div class="right-container">
            <h1>New here?</h1>
            <p>Signup and discover a new world of opportunities</p>
            <form action="register.jsp" method="get">
                <button type="submit" class="register-button">Sign Up</button>
            </form>
        </div>
    </div>
</body>
</html>


