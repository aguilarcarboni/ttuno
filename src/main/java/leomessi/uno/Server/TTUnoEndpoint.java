package leomessi.uno.Server;

import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.OnClose;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import leomessi.uno.Game;
import leomessi.uno.Players.HumanPlayer;
import leomessi.uno.Cards.Card;
import leomessi.uno.Players.Player;

import java.io.IOException;

import leomessi.uno.Utils.Logger;

@ServerEndpoint("/ttuno")
public class TTUnoEndpoint {

    private Game game;
    private static Session adminSession = null;
    private static final java.util.Map<Session, String> sessionPlayerMap = new java.util.concurrent.ConcurrentHashMap<>();
    private static final Logger logger = Logger.getInstance();
    
    @OnOpen
    public void onOpen(Session session) {
        Websocket.getClients().add(session);
        
        logger.info("SYSTEM: Client connected: " + session.getId() + ". Connected clients: " + Websocket.getClients().size());
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        logger.info("CLIENT: " + session.getId() + " REQUESTS " + message);
        
        try {
            String[] parts = message.split(":");
            String action = parts[0];

            switch (action) {
                case "create_game":
                    Game.resetGame();
                    game = Game.getInstance();
                    return "Game created successfully";

                case "add_player":
                    String playerName = parts[1];
                    game = Game.getInstance();
                    HumanPlayer newPlayer = new HumanPlayer(playerName);
                    game.addPlayer(newPlayer);
                    
                    // Map this session to the player name
                    sessionPlayerMap.put(session, playerName);
                    
                    // Always send player name in response
                    StringBuilder response = new StringBuilder();
                    response.append("PLAYER_NAME:").append(playerName).append("\n");
                    
                    // If this is the first player, make them admin
                    if (adminSession == null) {
                        adminSession = session;
                        broadcastPlayers();
                        response.append("PLAYER_ROLE:admin\n")
                               .append(getPlayerSpecificData(session));
                    } else {
                        // Regular player response
                        response.append("PLAYER_ROLE:player\n")
                               .append(getPlayerSpecificData(session));
                    }
                    
                    // Notify all clients about the new players
                    broadcastPlayers();
                    return response.toString();

                case "start_game":
                    if (session.equals(adminSession)) {
                        game.startGame();
                        broadcastMessage("GAME_STARTED");
                        return "Admin started the game successfully.";
                    }
                    return "Error: Only admin can start the game";

                case "get_players":
                    game = Game.getInstance();
                    return "PLAYERS:" + game.getPlayerListString();

                case "check_game_exists":
                    game = Game.getInstance();
                    boolean gameExists = (game != null && game.getPlayers() != null);
                    return "GAME_EXISTS:" + gameExists;

                case "get_game_state":
                    return getGameStateMessage();

                case "get_my_data":
                    String name = sessionPlayerMap.get(session);
                    if (name != null) {
                        return "PLAYER_NAME:" + name + "\n" + getPlayerSpecificData(session);
                    }
                    return getPlayerSpecificData(session);

                case "play_card":
                    String cardString = parts[1];
                    return handlePlayCard(session, cardString);

                case "draw_card":
                    return handleDrawCard(session);

                default:
                    return "Error: Unknown action";
            }
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    @OnClose
    public void onClose(Session session) {
        logger.warning("SYSTEM: Client disconnected: " + session.getId());
        Websocket.getClients().remove(session);
        sessionPlayerMap.remove(session);
        
        // If admin disconnects, assign admin role to next player if available
        if (session.equals(adminSession)) {
            adminSession = null;
            Game.resetGame();
            broadcastMessage("GAME_ENDED:admin_disconnected");
        }
    }

    // Broadcast information to all clients
    private void broadcastPlayers() {
        String message = "PLAYERS:" + game.getPlayerListString();
        broadcastMessage(message);
    }

    private void broadcastMessage(String message) {
        for (Session client : Websocket.getClients()) {
            try {
                logger.info("BROADCAST: " + message);
                client.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.error("Failed to broadcast message: " + e.getMessage());
            }
        }
    }


    // Create parsable data
    private String getGameStateMessage() {
        if (game == null) return "Error: No game in progress";

        StringBuilder state = new StringBuilder();
        
        // Current player
        Player currentPlayer = game.getCurrentPlayer();
        state.append("CURRENT_PLAYER:").append(currentPlayer.getName()).append("\n");
        
        // Top card
        Card topCard = game.getTopCard();
        state.append("TOP_CARD:").append(topCard.toString()).append("\n");
        
        return state.toString();
    }

    private String getPlayerSpecificData(Session session) {
        if (game == null) return "Error: No game in progress";
        
        String playerName = sessionPlayerMap.get(session);
        if (playerName == null) return "Error: Player not found";
        
        Player player = game.getPlayerByName(playerName);
        if (player == null) return "Error: Player not found in game";
        
        StringBuilder playerData = new StringBuilder();
        playerData.append("PLAYER_DATA\n");
        playerData.append("name:").append(player.getName()).append("\n");
        playerData.append("hand:").append(getPlayerHandString(player)).append("\n");
        playerData.append("cards_count:").append(player.getHandSize()).append("\n");
        
        return playerData.toString();
    }

    private String getPlayerHandString(Player player) {
        StringBuilder hand = new StringBuilder();
        for (Card card : player.getHand()) {
            hand.append(card.toString()).append(",");
        }
        if (hand.length() > 0) {
            hand.setLength(hand.length() - 1);
        }
        return hand.toString();
    }

    // Handlers
    private String handlePlayCard(Session session, String cardString) {
        game = Game.getInstance();
        String playerName = sessionPlayerMap.get(session);
        
        // Parse card and color if it's a wild card
        String[] cardParts = cardString.split(":");
        String cardValue = cardParts[0];
        String chosenColor = null;
        
        // Make sure we have enough parts and the last part is a valid color
        if (cardParts.length > 1) {
            chosenColor = cardParts[cardParts.length - 1];  // Take the last part as color
            logger.info("ENDPOINT: Parsed color choice from message: " + chosenColor);
        }
        
        logger.info("ENDPOINT: Received play card request - Card: " + cardValue + 
                    (chosenColor != null ? ", Color choice: " + chosenColor : ""));
        
        // If it's a wild card, validate and set the color
        if (cardValue.toLowerCase().contains("wild")) {
            if (chosenColor == null || !game.isValidColor(chosenColor)) {
                logger.warning("ENDPOINT: Invalid or missing color for wild card: " + chosenColor);
                chosenColor = "RED";
            }
            logger.info("ENDPOINT: Setting pending color for wild card: " + chosenColor);
            game.setPendingColor(chosenColor);
        }
        
        // Verify it's the player's turn
        if (!game.getCurrentPlayer().getName().equals(playerName)) {
            return "Error: Not your turn";
        }
        
        Player player = game.getPlayerByName(playerName);
        if (player == null) {
            return "Error: Player not found";
        }

        logger.info("PLAYER: " + playerName + " is trying to play card: " + cardString);
        
        // Find and validate the card in player's hand
        Card cardToPlay = null;
        for (Card card : player.getHand()) {
            if (card.toString().equals(cardString)) {
                // Verify if the card can be played according to game rules
                if (game.isValidPlay(card)) {
                    cardToPlay = card;
                    break;
                } else {
                    return "Error: Invalid card play";
                }
            }
        }
        
        if (cardToPlay == null) {
            return "Error: Card not found in hand or invalid play";
        }
        
        // Play the card
        player.getHand().remove(cardToPlay);
        game.setTopCard(cardToPlay);
        cardToPlay.play(game);
        
        // Check for game end condition
        if (player.getHandSize() == 0) {
            broadcastMessage("GAME_ENDED:" + playerName);
            return "SUCCESS:Player won";
        }
        
        // Move to next player
        game.moveToNextPlayer();
        
        // Broadcast updated game state to all players
        broadcastMessage(getGameStateMessage());
        broadcastMessage("TURN_PLAYED:" + playerName + ":" + cardString);
        
        return "SUCCESS:Card played successfully";
    }

    private String handleDrawCard(Session session) {
        game = Game.getInstance();
        String playerName = sessionPlayerMap.get(session);
        
        // Verify it's the player's turn
        if (!game.getCurrentPlayer().getName().equals(playerName)) {
            return "Error: Not your turn";
        }
        
        Player player = game.getPlayerByName(playerName);
        if (player == null) {
            return "Error: Player not found";
        }

        logger.info("PLAYER: " + playerName + " is drawing a card");
        
        // Draw a card and add it to player's hand
        Card drawnCard = game.deck.drawCard();
        player.addCardToHand(drawnCard);
        
        // Move to next player
        game.moveToNextPlayer();
        
        // Broadcast updated game state to all players
        broadcastMessage(getGameStateMessage());
        broadcastMessage("CARD_DRAWN:" + playerName);
        
        // Return the drawn card information only to the player who drew it
        return "SUCCESS:DRAWN_CARD:" + drawnCard.toString();
    }

}
