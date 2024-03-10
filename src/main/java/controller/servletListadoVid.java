package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.Video;

@WebServlet(urlPatterns = {"/servletListadoVid"})
public class servletListadoVid extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // Helper method to read binary data from a file
    private byte[] readBinaryData(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Error reading video file: " + e.getMessage());
            return null;
        }
    }

    
    private List<File> getAllVideoFiles(String folderPath) {
        List<File> videoFiles = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    videoFiles.add(file);
                }
            }
        }
        return videoFiles;
    }


//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String videosFolderPath = "/home/alumne/WebAppVideos";
//        List<Video> videoMetadataList = Video.getAllVideos();
//        List<File> fileVideos =  getAllVideoFiles(videosFolderPath);
//        
//        
//        System.out.println(fileVideos);
//
//        // Pass the videoDataMap to your JSP for further processing
//        request.setAttribute("fileVideos", fileVideos);
//        request.getRequestDispatcher("home.jsp").forward(request, response);
//    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String action = request.getParameter("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
            return;
        }

        switch (action) {
            case "fetchVideos":
                fetchVideos(request, response);
                break;
            case "addVisualization":
                addVisualization(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
                break;
        }
    }
    
    private void fetchVideos(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String videosFolderPath = "/home/alumne/WebAppVideos";
        List<Video> videoMetadataList = Video.getAllVideos();
        System.out.println(videoMetadataList.get(3).getTitle());
        System.out.println(videoMetadataList.get(3).getReproductions());
        List<File> fileVideos = getAllVideoFiles(videosFolderPath);

        // Create a map to hold video metadata keyed by title_author
        Map<String, AbstractMap.SimpleEntry<Video, File>> videoMap = new HashMap<>();

        // Fill the map with metadata using title_author as the key
        for (Video videoMetadata : videoMetadataList) {
            String key = videoMetadata.getTitle() + "_" + videoMetadata.getAuthor();
            AbstractMap.SimpleEntry<Video, File> entry = new AbstractMap.SimpleEntry<>(videoMetadata, null);
            videoMap.put(key, entry);
        }

        // Pair each file with its corresponding metadata in the map
        for (File file : fileVideos) {
            String fileName = file.getName();
            String titleAuthor = fileName.substring(0, fileName.lastIndexOf('.'));
            AbstractMap.SimpleEntry<Video, File> entry = videoMap.get(titleAuthor);
            if (entry != null) {
                entry.setValue(file);
            }
        }
//        // Pass the map and file list to the JSP
//        request.setAttribute("videoMetadataMap", videoMetadataMap);
        request.setAttribute("videoMap", videoMap);
        //request.setAttribute("fileVideos", fileVideos);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    private void addVisualization(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Your code to add visualization
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        System.out.println(title);
        Video.updateReproductions(title, author);
        request.getRequestDispatcher("home.jsp").forward(request, response);

        
       
    }

    @Override
    public String getServletInfo() {
        return "Servlet to retrieve and display videos";
    }
}
