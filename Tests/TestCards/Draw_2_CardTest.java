package UNO.Cards.TestCards;
import UNO.Cards.Draw_2_Card;
import UNO.Cards.BasicCard;
import UNO.Cards.Card;
import UNO.Game;
import UNO.Players.HumanPlayer;
import UNO.Players.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class Draw_2_CardTest { //Test class for Draw_2_Card
    
    private Card card; //Declares a Card object
    private Game game; //Declares a Game object
    private Player player1; //Declares a Player object
    private Player player2; //Declares a Player object
    private Player player3; //Declares a Player object

    @BeforeEach
    public void setUp() {
        card = new Draw_2_Card(new BasicCard("Red", "Draw 2")); //Creates a new Draw_2_Card object with a BasicCard of color Red and type "Draw 2"
        game = new Game(); //Creates a new Game object
        player1 = new HumanPlayer("Player 1"); //Creates a new HumanPlayer object with the name "Player 1"
        player2 = new HumanPlayer("Player 2"); //Creates a new HumanPlayer object with the name "Player 2"
        player3 = new HumanPlayer("Player 3"); //Creates a new HumanPlayer object with the name "Player 3"
        game.addPlayer(player1); //Adds player1 to the game
        game.addPlayer(player2); //Adds player2 to the game
        game.addPlayer(player3); //Adds player3 to the game
        game.startGame(); //This will initialize hands and set the first player
    }

    @Test
    public void testConstructor() {
        assertEquals("Red", card.getColor()); //Checks if the color of the card is Red
        assertEquals("Draw 2", card.getType()); //Checks if the type of the card is "Draw 2"
    }

    @Test
    public void testToString() {
        assertEquals("Red_Draw 2", card.toString()); //Checks if the string representation of the card is "Red_Draw 2"
    }

    @Test
    public void testDifferentColors() {
        Card blueCard = new Draw_2_Card(new BasicCard("Blue", "Draw 2")); //Creates a new Draw_2_Card object with a BasicCard of color Blue and type "Draw 2"
        Card greenCard = new Draw_2_Card(new BasicCard("Green", "Draw 2")); //Creates a new Draw_2_Card object with a BasicCard of color Green and type "Draw 2"
        Card yellowCard = new Draw_2_Card(new BasicCard("Yellow", "Draw 2")); //Creates a new Draw_2_Card object with a BasicCard of color Yellow and type "Draw 2"

        assertEquals("Blue", blueCard.getColor()); //Checks if the color of the blueCard is Blue
        assertEquals("Green", greenCard.getColor()); //Checks if the color of the greenCard is Green
        assertEquals("Yellow", yellowCard.getColor()); //Checks if the color of the yellowCard is Yellow
    }

    @Test
    public void testPlay() {
        game.startGame(); //Reinitializes the game with 3 players

        int initialHandSize = player2.getHandSize(); //Gets the initial hand size of player2
        Player initialCurrentPlayer = game.getCurrentPlayer(); //Gets the initial current player
        card.play(game); //Plays the card
        assertEquals(initialHandSize + 2, player2.getHandSize(), "Next player should draw 2 cards"); //Checks if the hand size of player2 is increased by 2
        assertNotEquals(initialCurrentPlayer, game.getCurrentPlayer(), "Turn should be skipped"); //Checks if the current player is not the same as the initial current player
        assertEquals(player3, game.getCurrentPlayer(), "Turn should skip to the player after next"); //Checks if the current player is player3
    }
}
