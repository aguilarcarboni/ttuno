package leomessi.uno.Moves;
import leomessi.uno.Cards.*;

public class MoveValidator {
    public static boolean isValidMove(Card card, Card topCard) {

        if (card instanceof WildCard || card instanceof WildDraw_4_Card) { // Wild cards and Wild Draw 4 cards can always be played
            return true;
        }

        if (card.getColor().equals(topCard.getColor())) { // If colors match, the move is valid
            return true;
        }

        if (card.getType().equals(topCard.getType())) { // If types match (for special cards), the move is valid
            return true;
        }

        if (card instanceof BasicCard && topCard instanceof BasicCard) { // For number cards, if the numbers match, the move is valid
            BasicCard basicCard = (BasicCard) card; // Casts the card to a BasicCard
            BasicCard basicTopCard = (BasicCard) topCard; // Casts the top card to a BasicCard
            int cardNumber = basicCard.getNumber(); // Gets the number of the card
            int topCardNumber = basicTopCard.getNumber(); // Gets the number of the top card
            if (cardNumber != -1 && topCardNumber != -1 && cardNumber == topCardNumber) { // If the numbers are not -1 and the numbers match, the move is valid
                return true;
            }
        }

        return false;
    }
}
