package UNO.Decks.TestDecks;

import java.util.List;
import UNO.Decks.DeckBuilder;
import UNO.Cards.Card;
import UNO.Cards.Draw_2_Card;
import UNO.Cards.SkipCard;
import UNO.Cards.ReverseCard;
import UNO.Cards.WildCard;
import UNO.Cards.WildDraw_4_Card;
import UNO.Cards.BasicCard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckBuilderTest {
    @Test
    public void testAddNumberCards() {
        DeckBuilder builder = new DeckBuilder();
        List<Card> cards = builder.addNumberCards().build(); // Build the deck for number cards
        
        String[] colors = {"Red", "Green", "Blue", "Yellow"};
        int expectedTotalCards = 76; // 19 number cards per color (0-9, 1-9)
        
        assertEquals(expectedTotalCards, cards.size());
        
        for (String color : colors) {
            assertEquals(1, cards.stream().filter(card -> card.getColor().equals(color) && ((BasicCard)card).getNumber() == 0).count()); //Check if there's one '0' card for each color, checks the color and the number 0, then counts the number of cards that match the condition
            
            // Check if there are two of each number from 1 to 9 for each color
            for (int i = 1; i <= 9; i++) {
                final int value = i; //Set the value to the number in the loop
                assertEquals(2, cards.stream().filter(card -> card.getColor().equals(color) && ((BasicCard)card).getNumber() == value).count()); //Check if there are two of each number from 1 to 9 for each color, checks the color and the number, then counts the number of cards that match the condition
            }
        }
    }

    @Test
    public void testAddSpecialCards(){ //Test for the special cards
        DeckBuilder builder = new DeckBuilder(); //Create a new instance of DeckBuilder
        List<Card> cards = builder.addSpecialCards().build(); //Build the deck for special cards

        String[] colors = {"Red", "Green", "Blue", "Yellow"}; //Array of colors
        int expectedTotalCards = 24; //Expected total number of cards

        assertEquals(expectedTotalCards, cards.size()); //Check if the number of cards is equal to the expected total number of cards

        for(String color : colors){ //For each color in the array
            assertEquals(2, cards.stream().filter(card -> card.getColor().equals(color) && card instanceof Draw_2_Card).count()); //Check if there are 2 Draw_2_Card for each color
            assertEquals(2, cards.stream().filter(card -> card.getColor().equals(color) && card instanceof SkipCard).count()); //Check if there are 2 SkipCard for each color
            assertEquals(2, cards.stream().filter(card -> card.getColor().equals(color) && card instanceof ReverseCard).count()); //Check if there are 2 ReverseCard for each color
        }
    }

    @Test
    public void testAddWildCards(){ //Test for the wild cards
        DeckBuilder builder = new DeckBuilder(); //Create a new instance of DeckBuilder
        List<Card> cards = builder.addWildCards().build(); //Build the deck for wild cards

        int expectedTotalCards = 8; //Expected total number of cards

        assertEquals(expectedTotalCards, cards.size()); //Check if the number of cards is equal to the expected total number of cards
        assertEquals(4, cards.stream().filter(card -> card instanceof WildCard).count()); //Check if there are 4 WildCard
        assertEquals(4, cards.stream().filter(card -> card instanceof WildDraw_4_Card).count()); //Check if there are 4 WildDraw_4_Card
    }
}
