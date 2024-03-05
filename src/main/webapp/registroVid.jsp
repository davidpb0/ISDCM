<%-- 
    Document   : newjsf
    Created on : 5 mar 2024, 18:27:11
    Author     : alumne
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Video Registration</title>
</head>
<body>
    <form action="servletRegistroVid" method="post">
        <label for="id">ID:</label>
        <input type="number" name="id" required><br>

        <label for="title">Title:</label>
        <input type="text" name="title" required><br>

        <label for="autor">Author:</label>
        <input type="text" name="autor" required><br>

        <label for="creationDate">Creation Date:</label>
        <input type="date" name="creationDate" required><br>

        <label for="durations">Duration:</label>
        <input type="time" name="durations" required><br>

        <label for="reproductions">Reproductions:</label>
        <input type="number" name="reproductions" required><br>

        <label for="description">Description:</label>
        <textarea name="description" rows="4" cols="50" required></textarea><br>

        <label for="format">Format:</label>
        <input type="text" name="format" required><br>

        <input type="submit" value="Submit">
    </form>
</body>
</html>
