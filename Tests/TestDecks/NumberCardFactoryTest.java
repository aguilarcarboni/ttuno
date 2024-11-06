package UNO.Decks.TestDecks;
import UNO.Decks.NumberCardFactory;
import UNO.Cards.Card;
import UNO.Cards.BasicCard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NumberCardFactoryTest { //Test class for NumberCardFactory
    @Test
    public void testcreateCard(){ //Test for creating a card with a valid color and number
        NumberCardFactory factory = new NumberCardFactory(); //Create a NumberCardFactory
        String colors[] = {"Red", "Green", "Blue", "Yellow"};
        String numbers[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for(String color : colors){ //For each color in the array, create a card with the specified color and number
            for(String number : numbers){ //For each number in the array, create a card with the specified color and number
                Card card = factory.createCard(color, number); //Create a card with the specified color and number
                assertEquals(color, card.getColor()); //Check if the color of the card is the same as the color in the loop
                assertEquals(Integer.parseInt(number), ((BasicCard)card).getNumber()); //Check if the number of the card is the same as the number in the loop, cast the card to a BasicCard to access the getNumber method
            }
        }
    }

    @Test
    public void testInvalidColor(){ //Test for creating a card with an invalid color
        NumberCardFactory factory = new NumberCardFactory(); //Create a NumberCardFactory
        assertThrows(IllegalArgumentException.class, () -> factory.createCard("Purple", "0")); //Check if an exception is thrown for an invalid color
    }
}
