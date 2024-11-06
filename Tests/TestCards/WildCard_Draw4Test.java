package UNO.Cards.TestCards;
import UNO.Game;
import UNO.Players.HumanPlayer;
import UNO.Players.Player;
import UNO.Cards.BasicCard;
import UNO.Cards.WildDraw_4_Card;
import UNO.Cards.Card;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class WildCard_Draw4Test {
    private Card card;
    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    public void setUp() {
        card = new WildDraw_4_Card(new BasicCard("Black", "Wild Draw 4"));
        game = new Game();
        player1 = new HumanPlayer("Player 1");
        player2 = new HumanPlayer("Player 2");
        player3 = new HumanPlayer("Player 3");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.startGame();
    }

    @Test
    public void testConstructor() {
        assertEquals("Black", card.getColor());
        assertEquals("Wild Draw 4", card.getType());
    }

    @Test
    public void testPlay() {
        game.startGame(); // Reinitialize the game with 3 players
        game.setTestColor("Red"); // Set the test color to Red

        int initialHandSize = player2.getHandSize(); //Get the initial hand size of the second player
        Player initialCurrentPlayer = game.getCurrentPlayer(); //Get the initial current player
        card.play(game); //Play the card
        
        assertNotEquals(initialCurrentPlayer, game.getCurrentPlayer(), "Current player should change after playing Wild Draw 4");
        assertEquals(initialHandSize + 4, player2.getHandSize(), "Next player should draw 4 cards");
        assertEquals("Red", game.getTopCard().getColor(), "Color should change to Red after playing Wild Draw 4");
    }

    @Test
    public void testToString() { //Test the toString method
        assertEquals("Black_Wild Draw 4", card.toString());
    }

    @Test
    public void testColorChangeAfterPlay() { //Test the color change after playing the card
        game.setTopCard(new BasicCard("Red", "5")); // Set the top card to a Red 5
        game.setTestColor("Blue"); // Set the test color to Blue
        String initialColor = game.getTopCard().getColor(); //Get the initial color of the top card
        card.play(game); //Play the card
        assertEquals("Blue", game.getTopCard().getColor(), "Color should change to Blue after playing Wild Draw 4"); //Check if the color of the top card is Blue
        assertNotEquals(initialColor, game.getTopCard().getColor(), "Color should change after playing Wild Draw 4"); //Check if the color of the top card has changed
    }
}
