<%-- 
    Document   : listadoVid
    Created on : 03 feb 2024, 19:26:54
    Author     : davidpb0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.Video" %>
<%@ page import="java.util.AbstractMap.SimpleEntry" %>
<%
    // Check if user is logged in
    String user = (String) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp"); 
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Videos Page</title>
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
    <style>
        .swiper-container {
            width: 500px;
            margin: 0 auto; 
        }

        .swiper-slide {
            text-align: center;
            font-size: 18px;
            background: #fff;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .video-container {
            max-width: 100%;
            height: auto;
        }

    </style>
</head>
<body>
    <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
    <form id="fetchVideosForm" action="servletListadoVid" method="post">
        <input type="hidden" name="action" value="fetchVideos">
        <button type="submit">Fetch Videos</button>
    </form>

    <div class="swiper-container">
        <div class="swiper-wrapper">
            <% 
            Map<String, SimpleEntry<Video, File>> videoFileMap = (Map<String, SimpleEntry<Video, File>>) request.getAttribute("videoMap");
            if (videoFileMap != null && !videoFileMap.isEmpty()) {
                for (Map.Entry<String, SimpleEntry<Video, File>> entry : videoFileMap.entrySet()) {
                    SimpleEntry<Video, File> videoEntry = entry.getValue();
                    Video videoMetadata = videoEntry.getKey();
                    File videoFile = videoEntry.getValue();
                    try {
                        byte[] videoBytes = Files.readAllBytes(videoFile.toPath());
                        String base64Video = Base64.getEncoder().encodeToString(videoBytes);
            %>
                        <div class="swiper-slide">
                            <div style="margin: 10px;">
                                <h3 style="margin: 0;"><%= videoMetadata.getTitle() %></h3>
                                <p style="margin: 0;"><%= videoMetadata.getDescription() %></p>
                                <video id ="<%= videoFile.getName() %>" class="video-container" controls>
                                    <source src="data:video/<%= videoMetadata.getFormat() %>;base64, <%= base64Video %>" type="video/<%= videoMetadata.getFormat() %>">
                                    Your browser does not support the video tag.
                                </video>
                                <div style="font-size: 12px; display: flex; align-items: center;">
                                    <p style="margin: 0; margin-right: 330px;">Uploaded by <%= videoMetadata.getAuthor() %></p>
                                    <p style="margin: 0;"><%= videoMetadata.getReproductions() %> views</p>
                                </div>
                            </div>
                             <script>
                                var videoElement = document.getElementById("<%= videoFile.getName() %>");

                                videoElement.addEventListener('play', function() {
                                    event.preventDefault();
                                    var form = new FormData(); 
                                    form.append('action', 'addVisualization');

                                    form.append('title', '<%= videoMetadata.getTitle() %>');
                                    form.append('author', '<%= videoMetadata.getAuthor() %>');

                                    fetch('servletListadoVid?action=addVisualization&title=<%= videoMetadata.getTitle() %>&author=<%= videoMetadata.getAuthor() %>', {
                                        method: 'POST',
                                        body: form
                                    })
                                    .then(response => {
                                        console.log('Form submitted successfully');
                                    })
                                    .catch(error => {
                                        console.error('Error submitting form:', error);
                                    });

                                });
                            </script>
                        </div>
                        
            <% 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
            %>
                <p style="margin-top: 100px">There are no videos yet</p>
            <%
            }
            %>
        </div>
        <div class="swiper-pagination"></div>
        
        <div class="swiper-button-prev"></div>
        <div class="swiper-button-next"></div>
    </div>

    <script>
        // Initialization of Swiper after DOM ready
        document.addEventListener('DOMContentLoaded', function () {
            var swiper = new Swiper('.swiper-container', {
                loop: true,
                spaceBetween: 100,
                slidesPerView: '1',

                pagination: {
                    el: '.swiper-pagination',
                    clickable: true,
                },

                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },
            });
        });
        
    </script>
</body>
</html>
