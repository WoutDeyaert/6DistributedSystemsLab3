package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Main {

    private static final String BASE_URL = "http://localhost:8080/namingServer";
    private static final HttpClient client = HttpClient.newHttpClient();

    static void main() throws Exception {
        String nodeName = "node" + (int)(Math.random() * 1000);
        String nodeIP = "192.168." + (int)(Math.random() * 255) + "." + (int)(Math.random() * 255);

        System.out.println("Name of the node: " + nodeName);
        System.out.println("IP of the node: " + nodeIP);

        post("/newnode/" + nodeName + "/" + nodeIP);

        int numFiles = 7;
        for (int i = 0; i < numFiles; i++) {
            String fileName = "file" + (int)(Math.random() * 50) + ".txt";
            post("/newfile/" + nodeName + "/" + fileName);
            System.out.println("File registered: " + fileName + " on " + nodeName);
        }

        int selection;
        String textualInput;
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("-------------------------\n");
            System.out.println("1 - Find IP of name");
            System.out.println("2 - Find node containing a file");
            System.out.println("3 - Stop");
            selection = input.nextInt();
            input.nextLine();
            if (selection == 1) {
                System.out.println("------------------------\n");
                System.out.println("Please enter the name of the node");
                textualInput = input.nextLine();
                System.out.println("IP of " + textualInput +": "+ get("/ipaddress/"+textualInput));
            }
            else if (selection == 2) {
                System.out.println("------------------------\n");
                System.out.println("Please enter the file name");
                textualInput = input.nextLine();
                System.out.println("File can be found at: " + get("/nodename/"+textualInput));
            }
            else if (selection == 3) {
                System.exit(0);
            }
            else {
                System.out.println("------------------------\n");
                System.out.println("Invalid input");
            }
        }
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
