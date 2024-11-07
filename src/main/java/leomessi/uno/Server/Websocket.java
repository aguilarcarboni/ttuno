package leomessi.uno.Server;

import org.glassfish.tyrus.server.Server;

import leomessi.uno.Utils.Logger;

import java.util.ArrayList;
import java.util.List;
import jakarta.websocket.Session;

public class Websocket {

    private static List<Session> clients = new ArrayList<>();
    private static final Logger logger = Logger.getInstance();

    public static List<Session> getClients() {
        return clients;
    }

    public static void main(String[] args) {
        logger.announcement("Starting TTUno server...", "info");
        startWebSocketServer();
    }

    public static String getIpAddress() {
        try {
            return java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (java.net.UnknownHostException e) {
            logger.announcement("Failed to get IP address, falling back to localhost", "error");
            return "localhost";
        }
    }

    public static void startWebSocketServer() {
        Server server = new Server(
            getIpAddress(), 
            8025, 
            "/websockets", 
            null, 
            TTUnoEndpoint.class
        );

        try {

            server.start();
            logger.announcement("TTUno server started on ws://" + getIpAddress() + ":8025/websockets/ttuno", "success");
            
            // Keep the server running
            Thread.currentThread().join();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
