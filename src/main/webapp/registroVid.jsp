<%-- 
    Document   : newjsf
    Created on : 5 mar 2024, 18:27:11
    Author     : alumne
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload Video</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        form {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
        }

        input[type="file"], input[type="text"], textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="text"]#author, input[type="text"]#format {
            border: none;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4caf50;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .error-container {
            max-width: 400px;
            margin: 0px auto 10px auto;
            padding: 10px;
            background-color: #FFA98F;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            font-family: fantasy;
            font-weight: 700; 
              
        }
        .back-button {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            background-color: #18314F;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
    <script>
        function validateForm() {
            var fileInput = document.getElementById('file');
            var errorMessage = document.getElementById('file-error-message');

            if (fileInput.files.length === 0) {
                errorMessage.innerHTML = "Please select a file.";
                return false;
            } else {
                errorMessage.innerHTML = "";
                return true;
            }
        }

        function updateAuthorAndFormat() {
            // Get the username from the session
            var authorInput = document.getElementById('author');
            var authorInputHidden = document.getElementById('authorHidden');
            var sessionUsername = '<%= session.getAttribute("user") %>';
            authorInput.value = sessionUsername;
            authorInputHidden.value = sessionUsername;

            // Get the selected file and extract its extension
            var fileInput = document.getElementById('file');
            var formatInput = document.getElementById('format');
            var formatInputHidden = document.getElementById('formatHidden');
            var fileName = fileInput.value;
            var lastIndex = fileName.lastIndexOf('.');
            if (lastIndex !== -1) {
                var format = fileName.substring(lastIndex + 1);
                formatInput.value = format;
                formatInputHidden.value = format;
            }
        }
        function goBack() {
            window.history.back();
        }


    </script>
</head>
<body>
    <h1>Upload Video</h1>
    <form action="serverletRegistroVid" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
        <label for="file">Choose video file:</label>
        <input type="file" id="file" name="file" onchange="updateAuthorAndFormat()">
        <div id="file-error-message" class="error-message"></div>

        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required>

        <label for="description">Description:</label>
        <input type="text" id="description" name="description"></textarea>

        <label for="author">Author:</label>
        <input type="text" id="author" name="author" readonly>

        <label for="format">Format:</label>
        <input type="text" id="format" name="format" readonly>
        <input type="hidden" id="authorHidden" name="authorHidden" value="">
        <input type="hidden" id="formatHidden" name="formatHidden" value="">

        
        <% 
        HttpSession sessionObj = request.getSession();
        String errorMessage = (String) sessionObj.getAttribute("errorMessage");
        if (errorMessage != null) { 
        %>
            <div class="error-container">
                <%= errorMessage %>
            </div>
            <% 
            sessionObj.removeAttribute("errorMessage");
        }
        %>

        <input type="submit" value="Upload">
        <button class="back-button" onclick="goBack()">Go Back</button>
    </form>
</body>
</html>





