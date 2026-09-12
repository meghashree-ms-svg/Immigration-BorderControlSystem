package util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.UUID;

public class CloudinaryUploader {

    private static final String CLOUD_NAME = "gqubrgya";
    private static final String UPLOAD_PRESET = "immigration_documents";

    public static String uploadDocument(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("\033[1;31m✗ File not found at provided path!\033[0m");
            return null;
        }

        String boundary = "---Boundary" + UUID.randomUUID().toString();
        String url = "https://api.cloudinary.com/v1_1/" + CLOUD_NAME + "/auto/upload";

        try {
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            StringBuilder sb = new StringBuilder();

            // Preset param
            sb.append("--").append(boundary).append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"upload_preset\"\r\n\r\n");
            sb.append(UPLOAD_PRESET).append("\r\n");

            // File param header
            sb.append("--").append(boundary).append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                    .append(file.getName()).append("\"\r\n");
            sb.append("Content-Type: application/octet-stream\r\n\r\n");

            byte[] header = sb.toString().getBytes();
            byte[] footer = ("\r\n--" + boundary + "--\r\n").getBytes();

            byte[] payload = new byte[header.length + fileBytes.length + footer.length];
            System.arraycopy(header, 0, payload, 0, header.length);
            System.arraycopy(fileBytes, 0, payload, header.length, fileBytes.length);
            System.arraycopy(footer, 0, payload, header.length + fileBytes.length, footer.length);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(payload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("\033[1;32m✓ Document successfully uploaded to Cloudinary!\033[0m");
                return response.body(); // Returns JSON response containing public URL
            } else {
                System.out.println("\033[1;31m✗ Cloudinary Upload Failed: " + response.body() + "\033[0m");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}