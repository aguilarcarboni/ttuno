package leomessi.uno.Moves;
import java.util.List;

import leomessi.uno.Cards.Card;

public interface MoveSelector { // Interface for the move selector
    Card selectMove(List<Card> hand, Card topCard); // Selects a move from the player's hand
}
