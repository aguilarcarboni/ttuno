package leomessi.uno.GUI;

import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.application.Platform;
import com.jfoenix.controls.JFXButton;
import javax.annotation.PostConstruct;
import leomessi.uno.Server.WebsocketClient;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;

import leomessi.uno.Utils.Logger;

import java.util.function.Consumer;

@ViewController(value = "/fxml/Uno.fxml", title = "UNO Game")
public class UnoController {
    
    private static final Logger logger = Logger.getInstance();
    
    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label deckCountLabel;

    @FXML
    private HBox cardContainer;

    @FXML
    private HBox playerCards;

    @FXML
    private JFXButton unoButton;

    @FXML
    private JFXButton drawCardButton;

    @FXML
    private VBox colorPickerContainer;

    private WebsocketClient client;

    private boolean isShowingColorDialog = false;

    @PostConstruct
    public void init() {
        client = (WebsocketClient) context.getRegisteredObject("WebsocketClient");
        
        // Add debug logging for initialization
        logger.info("Initializing UnoController");
        logger.info("Client player name: " + client.getPlayerName());

        // Initialize UI elements
        unoButton.setOnAction(event -> {
            client.sendMessage("uno_called");
        });

        drawCardButton.setOnAction(event -> {
            // Disable the button temporarily to prevent double-clicks
            drawCardButton.setDisable(true);
            
            // Send draw card request
            client.sendMessage("draw_card");
            
            // Re-enable after a short delay
            Platform.runLater(() -> {
                try {
                    Thread.sleep(100);
                    drawCardButton.setDisable(false);
                } catch (InterruptedException e) {
                    logger.error("Error in draw card delay: " + e.getMessage());
                }
            });
        });

        // Request initial game state and player data
        client.sendMessage("get_game_state");
        client.sendMessage("get_my_data");

        // Set up message handling for game events
        client.setMessageHandler(message -> {
            Platform.runLater(() -> handleMessage(message));
        });

        createColorPicker();
        colorPickerContainer.setVisible(false);  // Hide it initially
    }

    private void handleMessage(String message) {
        String[] messages = message.split("\n");
        
        for (String msg : messages) {
            if (msg.startsWith("CURRENT_PLAYER:")) {
                String playerName = msg.substring("CURRENT_PLAYER:".length());
                currentPlayerLabel.setText(playerName + "'s turn");
                
                // Add debug logging
                logger.info("Current player: " + playerName);
                logger.info("My name: " + client.getPlayerName());
                
                // Enable/disable controls based on whether it's player's turn
                boolean isPlayerTurn = isCurrentPlayersTurn(playerName);
                logger.info("Is my turn? " + isPlayerTurn);
                updateControlsForTurn(isPlayerTurn);
            }
            else if (msg.startsWith("TOP_CARD:")) {
                String card = msg.substring("TOP_CARD:".length());
                updateTopCardDisplay(card);
            }
            else if (msg.startsWith("PLAYER_DATA")) {
                handlePlayerData(messages);
            }
            else if (msg.startsWith("GAME_STARTED")) {
                client.sendMessage("get_game_state");
                client.sendMessage("get_my_data");
            }
            else if (msg.startsWith("GAME_ENDED:")) {
                String reason = msg.substring("GAME_ENDED:".length());
                showGameEndDialog(reason);
                clearDisplay();
            }
            else if (msg.startsWith("Error:")) {
                showErrorDialog(msg.substring("Error:".length()));
            }
            else if (msg.startsWith("SUCCESS:")) {
                // Refresh game state after successful card play
                client.sendMessage("get_game_state");
                client.sendMessage("get_my_data");
            }
            else if (msg.startsWith("SUCCESS:DRAWN_CARD:")) {
                // A card was successfully drawn
                String drawnCard = msg.substring("SUCCESS:DRAWN_CARD:".length());
                logger.info("Drew card: " + drawnCard);
                
                // Request updated game state and player data
                client.sendMessage("get_game_state");
                client.sendMessage("get_my_data");
            }
        }
    }

    private void updateControlsForTurn(boolean isPlayerTurn) {
        // Add debug logging
        logger.info("Updating controls. Is player turn: " + isPlayerTurn);
        
        Platform.runLater(() -> {
            drawCardButton.setDisable(!isPlayerTurn);
            playerCards.setDisable(!isPlayerTurn);
            
            // Visual feedback for turn state
            playerCards.setOpacity(isPlayerTurn ? 1.0 : 0.7);
            
            // Enable UNO button only when player has exactly 2 cards
            int cardCount = playerCards.getChildren().size();
            unoButton.setDisable(cardCount != 2);
            
            // Update card interaction based on turn
            for (javafx.scene.Node node : playerCards.getChildren()) {
                if (node instanceof VBox) {
                    VBox cardBox = (VBox) node;
                    cardBox.setMouseTransparent(!isPlayerTurn);
                }
            }
        });
    }

    private void handlePlayerData(String[] messages) {
        String handString = "";
        for (String line : messages) {
            if (line.startsWith("hand:")) {
                handString = line.substring(5);
                break;
            }
        }
        
        if (!handString.isEmpty()) {
            String[] cards = handString.split(",");
            updatePlayerHand(cards);
        }
    }

    private void updateTopCardDisplay(String card) {
        cardContainer.getChildren().clear();
        VBox cardBox = createCardBox(card, false);
        cardContainer.getChildren().add(cardBox);
    }

    private void clearDisplay() {
        currentPlayerLabel.setText("");
        cardContainer.getChildren().clear();
        playerCards.getChildren().clear();
    }

    private VBox createCardBox(String cardText, boolean isPlayable) {
        VBox cardBox = new VBox(5);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPrefWidth(100);
        cardBox.setPrefHeight(150);

        // Parse card text to determine color and value
        String[] cardParts = cardText.trim().split("_");
        String color = cardParts[0].toLowerCase();
        String value = cardText;  // Keep the full text for value initially

        // Special handling for color-only cards (after wild card plays)
        if (cardText.startsWith("Color:")) {
            color = cardText.trim().split(":")[1].toLowerCase().trim(); // Skip "color:" prefix
            value = "0"; // Use 0 as default value for color-only cards
        } else if (cardParts.length >= 2) {
            value = cardParts[1].toLowerCase();
        }

        System.out.println("Card text: " + cardText + " | Color: " + color + " | Value: " + value);

        String imagePath = getCardImagePath(color, value);
        String fullPath = "/cards/" + imagePath;
        
        try {
            // Debug print
            logger.info("Attempting to load card image: " + fullPath);
            
            // Try to get the resource stream first
            var resourceStream = getClass().getResourceAsStream(fullPath);
            if (resourceStream == null) {
                throw new IllegalStateException("Could not find resource: " + fullPath);
            }
            
            Image cardImage = new Image(resourceStream);
            if (cardImage.isError()) {
                throw new IllegalStateException("Failed to load image: " + cardImage.getException());
            }
            
            ImageView imageView = new ImageView(cardImage);
            imageView.setFitWidth(90);
            imageView.setFitHeight(140);
            imageView.setPreserveRatio(true);
            
            cardBox.getChildren().add(imageView);
            
        } catch (Exception e) {
            logger.error("Error loading card image: " + fullPath);
            e.printStackTrace();
            
            // Fallback to a colored rectangle with text
            VBox fallbackBox = new VBox(5);
            fallbackBox.setAlignment(Pos.CENTER);
            fallbackBox.setPrefWidth(90);
            fallbackBox.setPrefHeight(140);
            
            // Set background color based on card color
            String style = "-fx-background-color: ";
            switch (color.toUpperCase()) {
                case "RED": style += "#ff6b6b;"; break;
                case "BLUE": style += "#4dabf7;"; break;
                case "GREEN": style += "#69db7c;"; break;
                case "YELLOW": style += "#ffd43b;"; break;
                default: style += "#ffffff;"; break;
            }
            style += "-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;";
            fallbackBox.setStyle(style);
            
            Label valueLabel = new Label(value);
            valueLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            fallbackBox.getChildren().add(valueLabel);
            
            cardBox.getChildren().add(fallbackBox);
        }

        if (isPlayable) {
            // Add hover effect only if it's the player's turn
            cardBox.setOnMouseEntered(event -> {
                if (!cardBox.isDisabled()) {
                    cardBox.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0);");
                    cardBox.setTranslateY(-10);
                }
            });
            
            cardBox.setOnMouseExited(event -> {
                cardBox.setStyle("");
                cardBox.setTranslateY(0);
            });
        }

        return cardBox;
    }

    private String getCardImagePath(String color, String value) {
        // Standardize the card naming
        color = color.toLowerCase();
        value = value.toLowerCase();
        
        // Handle the special case where value is "color: {color}"
        if (value.startsWith("color:")) {
            // For a color-only card (after wild card color selection),
            // show a basic card of that color
            System.out.println("Returning color-only card: " + color + "_0.png");
            return color + "_0.png";
        }
        
        // Handle special cards
        switch (value) {
            case "wild":
                return "wild.png";
            case "wild draw 4":
                return "wild_draw4.png";
            case "skip":
                return color + "_skip.png";
            case "reverse":
                return color + "_reverse.png";
            case "draw 2":
                return color + "_draw2.png";
            default:
                // Handle number cards
                try {
                    int number = Integer.parseInt(value);
                    return color + "_" + number + ".png";
                } catch (NumberFormatException e) {
                    logger.error("Unable to parse card value: " + value + ", color: " + color);
                    return "card_back.png";
                }
        }
    }

    private void updatePlayerHand(String[] cards) {
        playerCards.getChildren().clear();
        for (String card : cards) {
            VBox cardBox = createCardBox(card, true);
            
            // Add click handler for playing cards
            cardBox.setOnMouseClicked(event -> {
                playCard(card);
            });
            
            playerCards.getChildren().add(cardBox);
        }
    }

    private void playCard(String card) {
        // Check if it's a wild card
        if (card.toLowerCase().contains("wild")) {
            if (!isShowingColorDialog) {
                isShowingColorDialog = true;
                showColorSelectionDialog(chosenColor -> {
                    client.sendMessage("play_card:" + card + ":" + chosenColor);
                });
            }
        } else {
            // Regular card play
            client.sendMessage("play_card:" + card);
        }
    }

    private void showColorSelectionDialog(Consumer<String> colorCallback) {
        Platform.runLater(() -> {
            // Clear existing content
            colorPickerContainer.getChildren().clear();
            
            // Create new components
            Label prompt = new Label("Choose a color:");
            prompt.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            
            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            
            // Create color buttons with their handlers
            JFXButton redButton = createColorButton("Red", "#ff6b6b");
            redButton.setOnAction(e -> {
                colorPickerContainer.setVisible(false);
                isShowingColorDialog = false;
                colorCallback.accept("RED");
            });
            
            JFXButton blueButton = createColorButton("Blue", "#4dabf7");
            blueButton.setOnAction(e -> {
                colorPickerContainer.setVisible(false);
                isShowingColorDialog = false;
                colorCallback.accept("BLUE");
            });
            
            JFXButton greenButton = createColorButton("Green", "#69db7c");
            greenButton.setOnAction(e -> {
                colorPickerContainer.setVisible(false);
                isShowingColorDialog = false;
                colorCallback.accept("GREEN");
            });
            
            JFXButton yellowButton = createColorButton("Yellow", "#ffd43b");
            yellowButton.setOnAction(e -> {
                colorPickerContainer.setVisible(false);
                isShowingColorDialog = false;
                colorCallback.accept("YELLOW");
            });
            
            buttonBox.getChildren().addAll(redButton, blueButton, greenButton, yellowButton);
            colorPickerContainer.getChildren().addAll(prompt, buttonBox);
            
            // Show the color picker
            colorPickerContainer.setVisible(true);
        });
    }

    private JFXButton createColorButton(String color, String hexColor) {
        JFXButton button = new JFXButton(color);
        button.setStyle(
            "-fx-background-color: " + hexColor + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10px 20px;" +
            "-fx-background-radius: 5px;"
        );
        return button;
    }

    private boolean isCurrentPlayersTurn(String currentPlayerName) {
        String myName = client.getPlayerName();
        
        // Add debug logging
        logger.info("Checking turn. Current player: " + currentPlayerName + ", My name: " + myName);
        
        if (myName == null) {
            logger.error("Player name is null!");
            return false;
        }
        
        return currentPlayerName.equals(myName);
    }

    private void showGameEndDialog(String reason) {
        Platform.runLater(() -> {
            String message = reason.equals("admin_disconnected") ? 
                "Game ended: Admin disconnected" : 
                "Game Over! " + reason + " wins!";
                
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showErrorDialog(String errorMessage) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();
        });
    }

    private void createColorPicker() {
        if (colorPickerContainer == null) {
            colorPickerContainer = new VBox(10);
            colorPickerContainer.setAlignment(Pos.CENTER);
            colorPickerContainer.setStyle("-fx-background-color: rgba(0,0,0,0.1); -fx-padding: 20px;");
            colorPickerContainer.setVisible(false);
        }
    }

} 