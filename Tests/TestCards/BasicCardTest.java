package UNO.Cards.TestCards;
import UNO.Cards.BasicCard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BasicCardTest { //Test class for the NumberCard class
    @Test
    public void testNumberCard(){ //Test method for the NumberCard class
        String[] colors = {"Red", "Green", "Blue", "Yellow"}; //Array of colors
        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}; //Array of numbers
        for(String color : colors){ //For each color
            for(String number : numbers){ //For each number
                BasicCard card = new BasicCard(color, number); //Creates a new BasicCard with the specified color and number
                assertEquals(color, card.getColor()); //Check if the color is correct
                assertEquals(Integer.parseInt(number), card.getNumber()); //Check if the number is correct
            }
        }
    }
}
