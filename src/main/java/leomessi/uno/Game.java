package leomessi.uno;
import leomessi.uno.Decks.Deck;
import java.util.ArrayList;
import leomessi.uno.Players.Lobby;
import leomessi.uno.Players.Player;
import leomessi.uno.Cards.ColorCard;
import leomessi.uno.Cards.BasicCard;


import leomessi.uno.Cards.Card;
import leomessi.uno.Utils.Logger;

public class Game {
    private static Game instance; // Singleton instance
    private static final int INITIAL_HAND_SIZE = 7; // The initial hand size
    public Deck deck; // The deck of cards
    private ArrayList<Player> players; // The players in the game
    private Lobby lobby; // The lobby of the game
    private Card topCard; // The top card of the deck
    private int currentPlayerIndex; // The index of the current player
    private boolean isClockwise; // Determine the direction of the game

    private String testColor; // Add this field for testing purposes

    private static final Logger logger = Logger.getInstance();

    private String pendingColor = null; // Add this field to store pending color choice

    private boolean unoIsCalled = false;
    private String unoCallingPlayer = null;

    private Game(){ // Constructor for the Game class
        this.deck = new Deck(); // Initialize the deck
        this.players = new ArrayList<>(); // Initialize the players
        this.lobby = new Lobby(); // Initialize the lobby
        this.isClockwise = true; // Initialize the direction of the game
    }

    public static synchronized Game getInstance() { // Singleton getter
        if (instance == null) {
            logger.announcement("SYSTEM: Creating new game instance", "info");
            instance = new Game();
        }
        return instance;
    }

    public static void resetGame() { // Add method to reset/clear game instance
        instance = null;
    }

    public void addPlayer(Player player){ // Adds a player to the game
        players.add(player); // Adds the player to the players list
        lobby.addPlayer(player); // Adds the player to the lobby
    }

    public ArrayList<Player> getPlayers(){ // Returns the players list
        return players; // Returns the players list
    }

    public String getPlayerListString() {
        StringBuilder playerList = new StringBuilder();
        for (Player player : getPlayers()) {
            playerList.append(player.getName()).append(",");
        }
        // Remove trailing comma if there are players
        if (playerList.length() > 0) {
            playerList.setLength(playerList.length() - 1);
        }
        return playerList.toString();
    }

    public void GiveHands(){ // Gives hands to the players
        for(Player player : players){ // For each player in the players list
            for(int i = 0; i < INITIAL_HAND_SIZE; i++){ // For each card in the player's hand
                Card card = deck.drawCard(); // Draws a card from the deck
                player.addCardToHand(card); // Adds the card to the player's hand
            }
        }
    }

    public void startGame(){ // Starts the game
        GiveHands(); // Gives hands to the players
        topCard = deck.drawCard(); // Draws the top card from the deck
        currentPlayerIndex = 0; // Sets the current player index to 0
        lobby.setGameState("Game Started"); // Sets the game state to "Game Started"
    }

    public void PlayerTurn(Player player){ // Handles the player's turn
        Player currentPlayer = players.get(currentPlayerIndex); // Gets the current player
        lobby.setGameState(currentPlayer.getName() + "'s turn"); // Sets the game state to the current player's name
        Card cardToPlay = currentPlayer.playCard(topCard); // Plays a card from the current player's hand

        if(cardToPlay != null){ // If the card that was played is valid
            lobby.setGameState(currentPlayer.getName() + " played " + cardToPlay.toString()); // Sets the game state to the current player's name and that they played a card
            cardToPlay.play(this); // Plays the card

            if(currentPlayer.getHandSize() == 0){ // If the current player has no cards left
                endGame(currentPlayer); // Ends the game
                return; // Ends the method
            }

            else{
                Card drawnCard = deck.drawCard(); // Draws a card from the deck
                currentPlayer.addCardToHand(drawnCard); // Adds the drawn card to the current player's hand
                lobby.setGameState(currentPlayer.getName() + " drew a card"); // Sets the game state to the current player's name and that they drew a card
            }
        }
        moveToNextPlayer(); // Moves to the next player
    }

    public void moveToNextPlayer(){ // Moves to the next player
        if(isClockwise){
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // Moves to the next player, the % is used to loop back to the first player if the end of the list is reached
        }
        else{
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size(); // Moves to the previous player, the % is used to loop back to the last player if the beginning of the list is reached
        }
    }

    public Player getCurrentPlayer(){ // Returns the current player, for testing purposes
        return players.get(currentPlayerIndex);
    }

    private void endGame(Player winner){ // Ends the game
        lobby.setGameState("Game Over! " + winner.getName() + " wins!"); // Sets the game state to the winner's name and that they won
    }

    // Game Moves

    public void setTopCard(Card card){ // Sets the top card
        topCard = card; // Sets the top card to the card that was played
    }

    public void SkipTurn(){
        moveToNextPlayer(); // Moves to the next player
    }

    public void reverseDirection(){ // Reverses the direction of the game
        isClockwise = !isClockwise;
    }

    public boolean isClockwise(){ // Returns the direction of the game, for testing purposes
        return isClockwise;
    }

    public void nextPlayerDrawCards(int amount){ // Draws cards from the deck to the next player
        moveToNextPlayer(); // Moves to the next player
        Player nextPlayer = players.get(currentPlayerIndex); // Gets the next player
        for (int i = 0; i < amount; i++) { // For each card to draw
            Card drawnCard = deck.drawCard(); // Draws a card from the deck
            nextPlayer.addCardToHand(drawnCard); // Adds the drawn card to the next player's hand
        }
    }

    public String chooseColor() {
        // If there's a pending color choice, use it
        if (pendingColor != null) {
            String color = pendingColor;
            pendingColor = null;
            logger.info("GAME: Using pending color: " + color);
            return color;
        }
        
        // For testing purposes
        if (testColor != null) {
            String color = testColor;
            testColor = null;
            logger.info("GAME: Using test color: " + color);
            return color;
        }
        
        // Default to RED if no color is specified
        logger.info("GAME: No color choice available - defaulting to RED");
        return "RED";
    }

    public void setPendingColor(String color) {
        logger.info("GAME: Setting pending color: " + color);
        this.pendingColor = color.toUpperCase();
        logger.info("GAME: Pending color set to: " + this.pendingColor);
    }

    public void setCurrentColor(String color) { 
        color = color.toUpperCase();
        logger.info("GAME: Setting current color to: " + color);
        topCard = new ColorCard(new BasicCard(color, "Number")); 
        logger.info("GAME: New top card after color change: " + topCard.toString());
        lobby.setGameState("The color has been changed to " + color); 
    }

    public void setTestColor(String color){ // Sets the test color for testing purposes
        this.testColor = color; // Sets the test color to the chosen color
    }

    public Card getTopCard(){ // Returns the top card, for testing purposes
        return topCard; // Returns the top card
    }

    public Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public boolean isValidPlay(Card card) {
        // Log the validation attempt
        logger.info("Validating card play: " + card.toString() + " against top card: " + topCard.toString());
        
        // Wild cards can always be played - check this first!
        if (card.getColor().equalsIgnoreCase("BLACK")) {
            logger.info("Wild card played - valid play");
            return true;
        }
        
        // Get the actual color of the top card (handle both regular cards and color cards)
        String topCardColor = topCard.toString().startsWith("Color:") ? 
            topCard.toString().substring(7).trim() : // Handle "Color: RED" format
            topCard.getColor();                      // Handle regular cards
        
        // Check if either the color or the value matches
        boolean colorMatch = card.getColor().equalsIgnoreCase(topCardColor);
        boolean valueMatch = card.getType().equals(topCard.getType());
        
        logger.info("Top card color: " + topCardColor);
        logger.info("Played card color: " + card.getColor());
        logger.info("Color match: " + colorMatch + ", Value match: " + valueMatch);
        
        return colorMatch || valueMatch;
    }

    public void callUno(String playerName) {
        Player player = getPlayerByName(playerName);
        if (player != null && player.getHandSize() == 2) {
            unoIsCalled = true;
            unoCallingPlayer = playerName;
            logger.info("UNO called by " + playerName);
        }
    }

    public boolean isUnoCalledFor(String playerName) {
        return unoIsCalled && unoCallingPlayer != null && unoCallingPlayer.equals(playerName);
    }

    public void resetUnoCalled() {
        unoIsCalled = false;
        unoCallingPlayer = null;
    }

    public boolean checkWinCondition(Player player) {
        // Player must have called UNO when they had 2 cards to win with their last card
        if (player.getHandSize() == 0) {
            if (player.getHandSize() == 0 && isUnoCalledFor(player.getName())) {
                return true;
            } else {
                // Player didn't call UNO - they must draw 2 cards as penalty
                for (int i = 0; i < 2; i++) {
                    Card drawnCard = deck.drawCard();
                    player.addCardToHand(drawnCard);
                }
                return false;
            }
        }
        return false;
    }

    public boolean isValidColor(String color) {
        if (color == null) return false;
        String upperColor = color.toUpperCase();
        return upperColor.equals("RED") || 
               upperColor.equals("BLUE") || 
               upperColor.equals("GREEN") || 
               upperColor.equals("YELLOW");
    }

}
