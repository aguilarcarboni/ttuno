package UNO.Players.TestPlayers;
import UNO.Players.Lobby;
import UNO.Players.PlayerObserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LobbyTest {

    private Lobby lobby;
    private PlayerObserver mockPlayer1;
    private PlayerObserver mockPlayer2;

    @BeforeEach
    void setUp() {
        lobby = new Lobby();
        mockPlayer1 = new MockPlayer();
        mockPlayer2 = new MockPlayer();
    }

    @Test
    void testAddPlayer() {
        lobby.addPlayer(mockPlayer1);
        lobby.setGameState("Test State");
        assertTrue(((MockPlayer) mockPlayer1).wasNotified);
    }

    @Test
    void testRemovePlayer() {
        lobby.addPlayer(mockPlayer1);
        lobby.addPlayer(mockPlayer2);
        lobby.removePlayer(mockPlayer1);
        lobby.setGameState("Test State");
        assertFalse(((MockPlayer) mockPlayer1).wasNotified);
        assertTrue(((MockPlayer) mockPlayer2).wasNotified);
    }

    @Test
    void testSetGameState() {
        lobby.addPlayer(mockPlayer1);
        lobby.addPlayer(mockPlayer2);
        lobby.setGameState("New State");
        assertTrue(((MockPlayer) mockPlayer1).wasNotified);
        assertTrue(((MockPlayer) mockPlayer2).wasNotified);
        assertEquals("New State", ((MockPlayer) mockPlayer1).lastState);
        assertEquals("New State", ((MockPlayer) mockPlayer2).lastState);
    }

    private class MockPlayer implements PlayerObserver {
        boolean wasNotified = false;
        String lastState = null;

        @Override
        public void update(String message) {
            wasNotified = true;
            lastState = message;
        }
    }
}
