package controller;

/**
 *
 * @author davidpb0
 */
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;

public class VideoEncryptionUtil {

    private static final String AES = "AES";

    public static void encryptVideo(String inputFilePath, String outputFilePath, String secretKey) throws Exception {
    try {
        Key key = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] fileContent = Files.readAllBytes(Paths.get(inputFilePath));
        byte[] encryptedBytes = cipher.doFinal(fileContent);

        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            fos.write(encryptedBytes);
            System.out.println("Video encrypted successfully.");
        }
    } catch (Exception e) {
        System.err.println("Failed to encrypt video: " + e.getMessage());
        throw e;
    }
}

    public static byte[] decryptVideo(String inputFilePath, String secretKey) throws Exception{
        try {
             Key key = generateKey(secretKey);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] encryptedBytes = Files.readAllBytes(Paths.get(inputFilePath));
            return cipher.doFinal(encryptedBytes);
            
        }
        catch (Exception e) {
            System.err.println("Failed to decrypt video: " + e.getMessage());
            throw e;
        }
        
    }

    private static Key generateKey(String secretKey) throws Exception {
        return new SecretKeySpec(secretKey.getBytes(), AES);
    }
}
