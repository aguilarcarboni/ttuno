package UNO.Decks.TestDecks;
import UNO.Decks.WildCardFactory;
import UNO.Cards.Card;
import UNO.Cards.WildCard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WildCardFactoryTest {
    @Test
    public void testCreateCard(){ //Test the createCard method
        WildCardFactory wildCardFactory = new WildCardFactory(); //Create a new WildCardFactory
        Card card = wildCardFactory.createCard(null, "Wild"); //Create a new WildCard
        assertTrue(card instanceof WildCard); //Check if the card is a WildCard
        assertEquals("Black", card.getColor()); //Check if the color of the card is Black

        card = wildCardFactory.createCard(null, "Wild Draw 4"); //Create a new WildCard_Draw4
        assertTrue(card.getType().equals("Wild Draw 4")); //Check if the card is a WildCard and if the type is "Wild Draw 4"
        assertEquals("Black", card.getColor()); //Check if the color of the card is Black
    }
}
