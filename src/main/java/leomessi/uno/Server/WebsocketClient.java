package leomessi.uno.Server;

import jakarta.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.function.Consumer;

import leomessi.uno.Utils.Logger;
@ClientEndpoint
public class WebsocketClient {

    private static final Logger logger = Logger.getInstance();
    
    private Session session;
    private Consumer<String> messageHandler;
    private Consumer<Boolean> connectionStatusHandler;
    private String playerName;

    public WebsocketClient(Consumer<Boolean> connectionStatusHandler) {
        this.connectionStatusHandler = connectionStatusHandler;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        connectionStatusHandler.accept(true);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        this.session = null;
        connectionStatusHandler.accept(false);
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("BROADCAST: " + message);
        
        // Split message into lines for parsing
        String[] lines = message.split("\n");
        for (String line : lines) {
            // Check for player name message
            if (line.startsWith("PLAYER_NAME:")) {
                String name = line.substring("PLAYER_NAME:".length());
                setPlayerName(name);
                logger.info("Player name set from message: " + name);
            }
        }
        
        if (messageHandler != null) {
            messageHandler.accept(message);
        }
    }

    public void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI("ws://" + Websocket.getIpAddress() + ":8025/websockets/ttuno"));
            logger.announcement("Initialized connection to server", "success");
        } catch (Exception e) {
            connectionStatusHandler.accept(false);
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        logger.info("CLIENT: " + message);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMessageHandler(Consumer<String> messageHandler) {
        this.messageHandler = messageHandler;
    }

    public boolean isConnected() {
        logger.announcement("Checking connection status...", "info");
        boolean isOpen = session != null && session.isOpen();
        logger.announcement("Connection status: " + isOpen, isOpen ? "success" : "error");
        return isOpen;
    }

    public String getPlayerName() {
        logger.info("Getting player name: " + playerName);
        return playerName;
    }

    public void setPlayerName(String name) {
        this.playerName = name;
        logger.info("Set player name to: " + name);
    }
}
