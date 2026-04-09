package Daniel.lab3;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {

    private static final String BASE_URL = "http://localhost:8080/namingServer";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        String nodeName = "node" + (int)(Math.random() * 1000);
        String nodeIP = "192.168." + (int)(Math.random() * 255) + "." + (int)(Math.random() * 255);

        post("/newnode/" + nodeName + "/" + nodeIP);

        int numFiles = 7;
        for (int i = 0; i < numFiles; i++) {
            String fileName = "file" + (int)(Math.random() * 50) + ".txt";
            post("/newfile/" + nodeName + "/" + fileName);
            System.out.println("File registered: " + fileName + " on " + nodeName);
        }

        System.out.println("IP of node1: " + get("/ipaddress/node1"));
        System.out.println("Nodes with file1.txt: " + get("/nodename/file1.txt"));
    }

    private static String get(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private static void post(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}