<%-- 
    Document   : listadoVid
    Created on : 5 mar 2024, 18:31:42
    Author     : alumne
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Video" %>
<%@ page import="java.util.Iterator" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Video List</title>
</head>
<body>
    <h2>Video List</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Creation Date</th>
            <th>Duration</th>
            <th>Reproductions</th>
            <th>Description</th>
            <th>Format</th>
        </tr>
        <% List<Video> videos = Video.getAllVideos(); %>
        <% Iterator<Video> it = videos.iterator(); %>
        <% while (it.hasNext()) { %>
            <% Video video = it.next(); %>
            <tr>
                <td><%= video.getId() %></td>
                <td><%= video.getTitle() %></td>
                <td><%= video.getAutor() %></td>
                <td><%= video.getCreationDate() %></td>
                <td><%= video.getDurations() %></td>
                <td><%= video.getReproductions() %></td>
                <td><%= video.getDescription() %></td>
                <td><%= video.getFormat() %></td>
            </tr>
        <% } %>
    </table>
</body>
</html>
