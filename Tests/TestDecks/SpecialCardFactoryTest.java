package UNO.Decks.TestDecks;

import org.junit.Test;
import static org.junit.Assert.*;

import UNO.Decks.SpecialCardFactory;
import UNO.Cards.Card;
import UNO.Cards.Draw_2_Card;
import UNO.Cards.SkipCard;
import UNO.Cards.ReverseCard;

public class SpecialCardFactoryTest { //Test class for SpecialCardFactory
    @Test
    public void testCreateCard() { //Test for creating a card with a valid color and number
        SpecialCardFactory factory = new SpecialCardFactory(); //Create a SpecialCardFactory
        String[] colors = {"Red", "Green", "Blue", "Yellow"}; //Array of colors
        String[] types = {"Draw 2", "Skip", "Reverse"}; //Array of types
        for(String color : colors){ //For each color in the array, create a new card with the specified color and number
            for(String type : types){ //For each type in the array, create a new card with the specified color and type
                Card card = factory.createCard(color, type); //Create a new card with the specified color and type
                assertEquals(color, card.getColor()); //Check if the color of the card is the same as the color in the loop
                switch (type) { //Switch statement to check if the card is a Draw_2_Card, SkipCard, or ReverseCard
                    case "Draw 2":
                        assertTrue(card instanceof Draw_2_Card); //Check if the card is a Draw_2_Card
                        break;
                    case "Skip":
                        assertTrue(card instanceof SkipCard); //Check if the card is a SkipCard
                        break;
                    case "Reverse":
                        assertTrue(card instanceof ReverseCard); //Check if the card is a ReverseCard
                        break;
                }
            }
        }
    }
}
