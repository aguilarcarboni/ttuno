package UNO.Players.TestPlayers;
import UNO.Players.HumanPlayer;
import UNO.Moves.HumanMoveSelector;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HumanPlayerTest {

    @Test
    void testConstructor() {
        HumanPlayer player = new HumanPlayer("Test Player");
        assertEquals("Test Player", player.getName());
        assertTrue(player.getMoveSelector() instanceof HumanMoveSelector);
    }
}
