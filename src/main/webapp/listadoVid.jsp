<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.Video" %>
<%@ page import="java.util.AbstractMap.SimpleEntry" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Videos Page</title>
    
</head>
<body>
    <!-- Form to trigger the POST request -->
    <form id="fetchVideosForm" action="servletListadoVid" method="post">
        <!-- You can include any form fields or data you need here -->
        <!-- For example, you can include hidden input fields with additional data -->

        <!-- Submit button to submit the form -->
        <input type="hidden" name="action" value="fetchVideos">
        <button type="submit">Fetch Videos</button>
    </form>
    <%
         Map<String, SimpleEntry<Video, File>> videoFileMap = (Map<String, SimpleEntry<Video, File>>) request.getAttribute("videoMap");
         if (videoFileMap != null) {
            for (Map.Entry<String, SimpleEntry<Video, File>> entry : videoFileMap.entrySet()) {
                SimpleEntry<Video, File> videoEntry = entry.getValue();
                Video videoMetadata = videoEntry.getKey();
                File videoFile = videoEntry.getValue();
                try {
                    // Read the video file as bytes
                    byte[] videoBytes = Files.readAllBytes(videoFile.toPath());
                    // Convert the video bytes to Base64 encoded string
                    String base64Video = Base64.getEncoder().encodeToString(videoBytes);
    %>
                    <div style="margin: 10px; margin-top: 20px;">
                        <h3 style="margin: 0px;"><%= videoMetadata.getTitle() %></h3>
                        <p style="margin: 0px;"><%= videoMetadata.getDescription() %></p>
                        <video id="<%= videoFile.getName() %>" width="320" height="200" controls>
                            <source src="data:video/mp4;base64, <%= base64Video %>" type="video/mp4">
                            Your browser does not support the video tag.
                        </video>
                        <div style="font-size: 12px; display: flex; align-items: center;">
                            <p style="margin: 0; margin-right: 180px;">Uploaded by <%= videoMetadata.getAuthor() %></p>
                            <p style="margin: 0;"><%= videoMetadata.getReproductions() %> views</p>
                        </div>
                    </div>
                    <script>
                        // JavaScript code to track video play events
                        var videoElement = document.getElementById("<%= videoFile.getName() %>");
                        
                        videoElement.addEventListener('play', function() {
                            event.preventDefault();
                            // Create a hidden form element and submit it
                            var form = new FormData(); // Create a FormData object
                            form.append('action', 'addVisualization'); // Add the 'action' parameter

                            // Add other form fields
                            form.append('title', '<%= videoMetadata.getTitle() %>');
                            form.append('author', '<%= videoMetadata.getAuthor() %>');

                            // Submit the form asynchronously using fetch API
                            fetch('servletListadoVid?action=addVisualization&title=<%= videoMetadata.getTitle() %>&author=<%= videoMetadata.getAuthor() %>', {
                                method: 'POST',
                                body: form
                            })
                            .then(response => {
                                // Handle the response as needed
                                console.log('Form submitted successfully');
                            })
                            .catch(error => {
                                // Handle any errors
                                console.error('Error submitting form:', error);
                            });
                            
                        });
                    </script>
    <%
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    %>
 
</body>
</html>
</body>
</html>
