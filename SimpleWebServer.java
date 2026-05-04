import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleWebServer {

    static List<String> users = new ArrayList<>();

    static String homePage = "index.html";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        System.out.println("Server läuft auf http://localhost");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleRequest(clientSocket);
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream out = clientSocket.getOutputStream()
        ) {

            String requestLine = in.readLine();
            if (requestLine == null) return;

            System.out.println("Request: " + requestLine);

            String[] parts = requestLine.split(" ");
            String method = parts[0];
            String path = parts[1];

            // Body lesen (für POST)
            String line;
            int contentLength = 0;
            while (!(line = in.readLine()).isEmpty()) {
                if (line.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
            }

            char[] bodyChars = new char[contentLength];
            in.read(bodyChars);
            String body = new String(bodyChars);

            // Routing
            if (method.equals("GET") && path.equals("/users")) {
                sendJsonResponse(out, 200, getUsersJson());
            } 
            else if (method.equals("POST") && path.equals("/users")) {
                users.add(body); // sehr simpel
                sendJsonResponse(out, 200, "{\"status\":\"user added\"}");
            }
            else if (method.equals("GET") && path.equals("/")) {
                String html = readFile("index.html");
                sendHtmlResponse(out, 200, html);
            }
            else {
                String html = readFile("404.html");
                sendHtmlResponse(out, 404, html);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getUsersJson() {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < users.size(); i++) {
            json.append("{\"name\":\"").append(users.get(i)).append("\"}");
            if (i < users.size() - 1) json.append(",");
        }
        json.append("]");
        return json.toString();
    }

    private static void sendJsonResponse(OutputStream out, int statusCode, String body) throws IOException {
        String response =
            "HTTP/1.1 " + statusCode + " OK\r\n" +
            "Content-Type: application/json\r\n" +
            "Content-Length: " + body.length() + "\r\n" +
            "\r\n" +
            body;

        out.write(response.getBytes());
        out.flush();
    }

    private static void sendHtmlResponse(OutputStream out, int statusCode, String body) throws IOException {
        String response =
            "HTTP/1.1 " + statusCode + " OK\r\n" +
            "Content-Type: text/html\r\n" +
            "Content-Length: " + body.length() + "\r\n" +
            "\r\n" +
            body;

        out.write(response.getBytes());
        out.flush();
    }

    private static String readFile(String fileName) throws IOException {
        return new String(java.nio.file.Files.readAllBytes(
            java.nio.file.Paths.get(fileName)
        ));
    }
}