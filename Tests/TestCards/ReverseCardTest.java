package UNO.Cards.TestCards;
import UNO.Cards.ReverseCard;
import UNO.Cards.BasicCard;
import UNO.Cards.Card;
import UNO.Game;
import UNO.Players.HumanPlayer;
import UNO.Players.Player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ReverseCardTest { //Test class for the ReverseCard class
    private Card card; //Card object
    private Game game; //Game object
    private Player player1; //Player 1 object
    private Player player2; //Player 2 object
    private Player player3; //Player 3 object

    @BeforeEach //Before each test
    public void setUp() {
        card = new ReverseCard(new BasicCard("Red", "Reverse")); //Creates a new ReverseCard with the specified color and type
        game = new Game(); //Creates a new Game object
        player1 = new HumanPlayer("Player 1"); //Creates a new HumanPlayer object with the specified name
        player2 = new HumanPlayer("Player 2"); //Creates a new HumanPlayer object with the specified name
        player3 = new HumanPlayer("Player 3"); //Creates a new HumanPlayer object with the specified name
        game.addPlayer(player1); //Adds the player to the game
        game.addPlayer(player2); //Adds the player to the game
        game.addPlayer(player3); //Adds the player to the game
        game.startGame(); // This will initialize hands and set the first player
    }

    @Test //Test method for the constructor
    public void testConstructor() {
        assertEquals("Red", card.getColor()); //Check if the color is correct
        assertEquals("Reverse", card.getType()); //Check if the type is correct
    }

    @Test
    public void testDifferentColors() {
        Card blueCard = new ReverseCard(new BasicCard("Blue", "Reverse")); //Creates a new ReverseCard with the specified color and type
        Card greenCard = new ReverseCard(new BasicCard("Green", "Reverse")); //Creates a new ReverseCard with the specified color and type
        Card yellowCard = new ReverseCard(new BasicCard("Yellow", "Reverse")); //Creates a new ReverseCard with the specified color and type

        assertEquals("Blue", blueCard.getColor()); //Check if the color is correct
        assertEquals("Green", greenCard.getColor()); //Check if the color is correct
        assertEquals("Yellow", yellowCard.getColor()); //Check if the color is correct
    }

    @Test
    public void testPlay() {
        boolean initialDirection = game.isClockwise(); //Gets the initial direction of the game
        card.play(game); //Plays the card
        assertNotEquals(initialDirection, game.isClockwise(), "Game direction should be reversed after playing a Reverse card"); //Check if the game direction is reversed
    }

    @Test
    public void testToString() {
        assertEquals("Red_Reverse", card.toString(), "toString should return the color and type of the card"); //Check if the toString method returns the correct string
    }

    @Test
    public void testPlayTwice() {
        boolean initialDirection = game.isClockwise(); //Gets the initial direction of the game
        card.play(game); //Plays the card
        card.play(game); //Plays the card
        assertEquals(initialDirection, game.isClockwise(), "Playing Reverse twice should return to the initial direction"); //Check if the game direction is reversed
    }
}
