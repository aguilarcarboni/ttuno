package UNO.Moves.TestMoves;
import UNO.Moves.MoveValidator;
import UNO.Cards.BasicCard;
import UNO.Cards.Card;
import UNO.Cards.WildCard;
import UNO.Cards.WildDraw_4_Card;
import UNO.Cards.SkipCard;
import UNO.Cards.Draw_2_Card;
import UNO.Cards.ReverseCard;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MoveValidatorTest {
    
    @Test
    public void testWildCardIsAlwaysValid() {
        Card wildCard = new WildCard(new BasicCard("Black", "Wild"));
        Card topCard = new BasicCard("Red", "5");
        assertTrue(MoveValidator.isValidMove(wildCard, topCard));
    }

    @Test
    public void testWildDraw4CardIsAlwaysValid() {
        Card wildDraw4Card = new WildDraw_4_Card(new BasicCard("Black", "Wild Draw Four"));
        Card topCard = new BasicCard("Blue", "2");
        assertTrue(MoveValidator.isValidMove(wildDraw4Card, topCard));
    }

    @Test
    public void testMatchingColorIsValid() {
        Card card = new BasicCard("Green", "7");
        Card topCard = new BasicCard("Green", "4");
        assertTrue(MoveValidator.isValidMove(card, topCard));
    }

    @Test
    public void testMatchingTypeIsValid() {
        Card card = new SkipCard(new BasicCard("Red", "Skip"));
        Card topCard = new SkipCard(new BasicCard("Blue", "Skip"));
        assertTrue(MoveValidator.isValidMove(card, topCard));
        card = new ReverseCard(new BasicCard("Red", "Reverse"));
        topCard = new ReverseCard(new BasicCard("Blue", "Reverse"));
        assertTrue(MoveValidator.isValidMove(card, topCard));
        card = new Draw_2_Card(new BasicCard("Red", "Draw 2"));
        topCard = new Draw_2_Card(new BasicCard("Blue", "Draw 2"));
        assertTrue(MoveValidator.isValidMove(card, topCard));
    }

    @Test
    public void testMatchingNumberIsValid() {
        String[] colors = {"Red", "Yellow", "Green", "Blue"};
        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (String color : colors) {
            for (String number : numbers) {
                Card card = new BasicCard(color, number);
                Card topCard = new BasicCard(color, number);
                assertTrue(MoveValidator.isValidMove(card, topCard));
            }
        }
    }

    @Test
    public void testNonMatchingMoveIsInvalid() {
        Card card = new BasicCard("Yellow", "4");
        Card topCard = new BasicCard("Red", "7");
        assertFalse(MoveValidator.isValidMove(card, topCard));
        card = new ReverseCard(new BasicCard("Red", "Reverse"));
        topCard = new SkipCard(new BasicCard("Blue", "Skip"));
        assertFalse(MoveValidator.isValidMove(card, topCard));
        card = new SkipCard(new BasicCard("Red", "Skip"));
        topCard = new Draw_2_Card(new BasicCard("Blue", "Draw 2"));
        assertFalse(MoveValidator.isValidMove(card, topCard));
    }
}
