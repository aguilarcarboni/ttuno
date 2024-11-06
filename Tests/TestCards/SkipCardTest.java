package UNO.Cards.TestCards;
import UNO.Cards.SkipCard;
import UNO.Game;
import UNO.Players.HumanPlayer;
import UNO.Players.Player;
import UNO.Cards.BasicCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class SkipCardTest {
    private SkipCard card;
    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    public void setUp() {
        card = new SkipCard(new BasicCard("Red", "Skip"));
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
        assertEquals("Red", card.getColor());
        assertEquals("Skip", card.getType());
    }

    @Test
    public void testPlay() {
        Player initialPlayer = game.getCurrentPlayer();
        card.play(game);
        assertNotEquals(initialPlayer, game.getCurrentPlayer(), "Current player should change after playing Skip card");
        assertEquals(player2, game.getCurrentPlayer(), "Current player should be the player after the skipped player");
    }

    @Test
    public void testDifferentColors() {
        SkipCard blueCard = new SkipCard(new BasicCard("Blue", "Skip"));
        SkipCard greenCard = new SkipCard(new BasicCard("Green", "Skip"));
        SkipCard yellowCard = new SkipCard(new BasicCard("Yellow", "Skip"));

        assertEquals("Blue", blueCard.getColor());
        assertEquals("Green", greenCard.getColor());
        assertEquals("Yellow", yellowCard.getColor());
    }

    @Test
    public void testToString() {
        assertEquals("Red_Skip", card.toString());
    }
}
