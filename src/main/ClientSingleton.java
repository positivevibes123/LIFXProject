package main;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class ClientSingleton {
    private static ClientSingleton clientSingleton;

    private HttpClient client;
    private String token;

    private ClientSingleton() {
        client = HttpClient.newHttpClient();
    }

    public static ClientSingleton getInstance() {
        if (clientSingleton == null) {
            clientSingleton = new ClientSingleton();
        }

        return clientSingleton;
    }

    public void init(String token) {
        this.token = token;
    }

    // Generate encoded credentials to be passed to authorization header
    private String getCredentials() {
        String credentials = token + ":";

        return "Basic " + Base64.getEncoder()
                .encodeToString(credentials.getBytes());
    }

    public void togglePower() throws IOException, InterruptedException {
        String requestBody = "{\"duration\": \"1\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.lifx.com/v1/lights/all/toggle"))
                .header("Authorization", getCredentials())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Status %s \n", response.statusCode());
    }

    public void setColor(Color color) throws IOException, InterruptedException {
        String requestBody = "{\"color\": \"rgb:" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "\"}";
        System.out.println(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.lifx.com/v1/lights/all/state"))
                .header("Authorization", getCredentials())
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Status %s \n", response.statusCode());
    }
}
