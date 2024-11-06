package UNO.Cards.TestCards;
import UNO.Cards.WildCard;
import UNO.Game;
import UNO.Players.HumanPlayer;
import UNO.Players.Player;
import UNO.Cards.BasicCard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class WildCardTest {
    private WildCard card;
    private Game game;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        card = new WildCard(new BasicCard("Black", "Wild"));
        game = new Game();
        player1 = new HumanPlayer("Player 1");
        player2 = new HumanPlayer("Player 2");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.startGame();
    }

    @Test
    public void testConstructor() {
        assertEquals("Black", card.getColor());
        assertEquals("Wild", card.getType());
    }

    @Test
    public void testPlay(){
        game.setTopCard(new BasicCard("Red", "5")); // Set the top card to a Red 5
        game.setTestColor("Blue"); // Set the test color to Blue
        String initialColor = game.getTopCard().getColor();
        card.play(game);
        assertEquals("Blue", game.getTopCard().getColor(), "Color should change to Blue after playing Wild");
        assertNotEquals(initialColor, game.getTopCard().getColor(), "Color should change after playing Wild");
    }

    @Test
    public void testToString() {
        assertEquals("Black_Wild", card.toString());
    }
}
