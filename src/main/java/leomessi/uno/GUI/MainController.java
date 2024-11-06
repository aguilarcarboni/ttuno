package leomessi.uno.GUI;

import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javax.annotation.PostConstruct;

import leomessi.uno.App;
import leomessi.uno.Server.WebsocketClient;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;


@ViewController(value = "/fxml/Main.fxml", title = "UNO Game")
public class MainController {
    
    @FXMLViewFlowContext
    private ViewFlowContext flowContext;
    
    @FXML
    private Label titleLabel;

    @FXML
    private VBox mainContent;
    
    @FXML
    private JFXButton startGameButton;

    @FXML
    private JFXButton joinGameButton;

    @FXML
    private FlowPane playersContainer;

    private WebsocketClient client;

    private String playerName;
    private boolean isAdmin = false;

    public MainController() {
        // Required no-args constructor
    }

    @PostConstruct
    public void init() {
        try {


            // Initially disable join button
            startGameButton.setDisable(true);
            
            // Initialize WebSocket client that automatically handles connection status
            client = new WebsocketClient(connected -> {
                Platform.runLater(() -> {
                    if (connected) {
                        startGameButton.setDisable(false);
                        mainContent.setVisible(true);
                        checkExistingGame();

                        // Generate a random player name
                        playerName = "Player" + (int)(Math.random() * 1000);
                        client.sendMessage("add_player:" + playerName);

                    } else {
                        startGameButton.setDisable(true);
                        mainContent.setVisible(false);
                    }
                });
            });

            // Initialize message handler that will handle messages from the server
            client.setMessageHandler(message -> {
                Platform.runLater(() -> {

                    if (message.startsWith("GAME_EXISTS:")) {
                        String[] parts = message.split(":", 2);
                        if (parts.length > 1) {
                            boolean gameExists = Boolean.parseBoolean(parts[1]);
                            if (gameExists) {
                                startGameButton.setText("Start Game");
                            }
                        }
                    }
                    else if (message.startsWith("PLAYERS:")) {
                        String[] parts = message.split(":", 2);
                        if (parts.length > 1) {
                            String playerList = parts[1];
                            playersContainer.getChildren().clear();
                            
                            if (!playerList.isEmpty()) {
                                String[] players = playerList.split(",");
                                for (String player : players) {
                                    VBox playerBox = new VBox(10);
                                    playerBox.setAlignment(javafx.geometry.Pos.CENTER);
                                    
                                    Circle avatar = new Circle(30);
                                    avatar.setFill(Color.web("#b0b0b0"));
                                    
                                    Label nameLabel = new Label(player);
                                    
                                    playerBox.getChildren().addAll(avatar, nameLabel);
                                    playersContainer.getChildren().add(playerBox);
                                }
                            }
                        }
                    }
                    else if (message.startsWith("PLAYER_ROLE:")) {
                        String[] parts = message.split("\n", 2);
                        if (parts.length > 0) {
                            String[] roleParts = parts[0].split(":", 2);
                            if (roleParts.length > 1) {
                                String role = roleParts[1];
                                isAdmin = role.equals("admin");
                                if (!isAdmin) {
                                    startGameButton.setVisible(false);
                                }
                            }
                        }
                        
                        if (parts.length > 1) {
                            String playerData = parts[1];
                            String[] lines = playerData.split("\n");
                            for (String line : lines) {
                                if (line.startsWith("name:")) {
                                    String playerName = line.substring(5);
                                    titleLabel.setText("Welcome " + playerName + " to TTUno.");
                                }
                            }
                        }
                    }
                    else if (message.equals("GAME_STARTED")) {
                        try {
                            Flow flow = new Flow(UnoController.class);
                            DefaultFlowContainer container = new DefaultFlowContainer();
                            ViewFlowContext newContext = new ViewFlowContext();
                            newContext.register("WebsocketClient", client);
                            FlowHandler flowHandler = flow.createHandler(newContext);
                            flowHandler.start(container);
                            
                            Scene scene = App.getPrimaryStage().getScene();
                            scene.setRoot(container.getView());
                        } catch (FlowException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (message.startsWith("GAME_ENDED:")) {
                        titleLabel.setText("Game Ended - Admin Disconnected");
                        startGameButton.setVisible(true);
                        startGameButton.setDisable(true);
                        playersContainer.getChildren().clear();
                    }
                });
            });

            // Connect to WebSocket server
            new Thread(() -> client.connect()).start();

            // Start game button action
            startGameButton.setOnAction(event -> {
                if (client.isConnected() && startGameButton.getText().equals("Start Game")) {
                    client.sendMessage("start_game");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkExistingGame() {
        if (client.isConnected()) {
            client.sendMessage("check_game_exists");
        }
    }
}