<%-- 
    Document   : home
    Created on : 25 feb 2024, 19:59:34
    Author     : davidpb0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            display: flex;
            height: 100vh;
        }

        .sidebar {
            width: 150px;
            background-color: #1e2852; /* Dark blue */
            color: #fff;
            padding: 10px;
        }

        .main-content {
            margin-top: 85px;
            flex: 1;
            padding: 20px;
            background-color: #fff; /* White */
            color: #000; /* Black text */
            position: relative; /* Add position relative */
        }

        .toolbar {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 10px 25px;
            background-color: #1e2852; /* Dark blue */
            color: #fff;
            position: fixed;
            width: 100%;
            top: 0;
            left: 0;
            z-index: 1000;
        }

        .menu-button {
            display: block;
            width: 100%;
            padding: 10px 20px;
            margin-bottom: 10px;
            text-align: left;
            background-color: #44506a; /* Darker blue */
            color: #fff;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .menu-button:hover {
            background-color: #1e2852; /* Dark blue */
        }

        .content {
            margin-top: 200px; /* Adjust based on toolbar height */
        }

        .sidebar ul {
            margin-top: 150px; /* Add more space between toolbar and ul */
            padding: 0;
            list-style: none;
        }
        .logout-button {
            margin-right: 50px;
            padding-right: 50px;
            display: block;
            width: 100%;
            padding: 10px 20px;
            margin-bottom: 10px;
            text-align: left;
            background-color: #44506a;
            color: #fff;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .success-message {
            color: green;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="toolbar">
        <h1>FIBTube</h1>
        <form action="UserController" method="post">
                    <input type="hidden" name="action" value="logoutUser">
                    <button class="logout-button" type="submit">Logout</button>
         </form>
    </div>

    <div class="container">
        <div class="sidebar">
            <ul>
                <button class="menu-button" onclick="navigateToRegisterVideo()">Register Video</button>
                
            </ul>
        </div>

        <div class="main-content">
            <!-- Main content goes here -->
            <h1>Welcome back <%= session.getAttribute("user") %></h1>
            <%-- Display success message if available --%>
            <% String successMessage = (String) session.getAttribute("successMessage");
               if (successMessage != null && !successMessage.isEmpty()) { %>
                <div class="success-message">
                    <%= successMessage %>
                </div>
                <script>
                    // Hide the success message after 5 seconds
                    setTimeout(function() {
                        document.querySelector('.success-message').style.display = 'none';
                    }, 5000); // 5000 milliseconds = 5 seconds
                </script>
            <% } %>
            <jsp:include page="listadoVid.jsp" />
        </div>
    </div>

    <script>
        function navigateToRegisterVideo() {
            window.location.href = 'registroVid.jsp';
        }
    </script>
</body>
</html>




