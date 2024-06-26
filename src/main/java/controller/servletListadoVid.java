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
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import model.Video;

/**
 *
 * @author davidpb0
 */
@WebServlet(urlPatterns = {"/servletListadoVid"})
public class servletListadoVid extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");

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
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
                break;
        }
    }
    
    private void fetchVideos(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            String videosFolderPath = "/home/alumne/WebAppVideos";
            List<Video> videoMetadataList = Video.getAllVideos();
            List<File> fileVideos = getAllVideoFiles(videosFolderPath);

            Map<String, AbstractMap.SimpleEntry<Video, String>> videoMap = new HashMap<>();
            String secretKey = "5Uo7r8YfGhJk2NmP";

            if (videoMetadataList != null && fileVideos != null){
                
                for (Video videoMetadata : videoMetadataList) {
                    String key = videoMetadata.getTitle() + "_" + videoMetadata.getAuthor();
                    AbstractMap.SimpleEntry<Video, String> entry = new AbstractMap.SimpleEntry<>(videoMetadata, null);
                    videoMap.put(key, entry);
                }

                for (File file : fileVideos) {
                    String fileName = file.getName();
                    String titleAuthor = fileName.substring(0, fileName.lastIndexOf('.'));
                    
                    try {
                        byte[] decryptedBytes = VideoEncryptionUtil.decryptVideo(videosFolderPath + "/" + fileName, secretKey);
                        String base64Video = Base64.getEncoder().encodeToString(decryptedBytes);
                        AbstractMap.SimpleEntry<Video, String> entry = videoMap.get(titleAuthor);
                        if (entry != null) {
                            entry.setValue(base64Video);
                        }
                    }catch (Exception e) {
                        System.err.println("Failed to decrypt video: " + e.getMessage());
                    }                    

                }
            }
            request.setAttribute("videoMap", videoMap);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

}
