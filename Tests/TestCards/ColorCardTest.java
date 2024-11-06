package UNO.Cards.TestCards;
import UNO.Cards.ColorCard;
import UNO.Cards.BasicCard;
import UNO.Game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColorCardTest { //Test class for the ColorCard class
    
    @Test
    public void testColorCardConstructor() { //Test method for the ColorCard constructor
        ColorCard card = new ColorCard(new BasicCard("Red", "Number")); //Creates a new ColorCard object
        assertEquals("Red", card.getColor()); //Checks if the color is correct
    }

    @Test
    public void testToString() { //Test method for the toString method
        ColorCard card = new ColorCard(new BasicCard("Blue", "Number")); //Creates a new ColorCard object
        assertEquals("Color: Blue", card.toString()); //Checks if the toString method returns the correct string
    }

    @Test
    public void testDifferentColors() {
        ColorCard redCard = new ColorCard(new BasicCard("Red", "Number")); //Creates a new ColorCard object
        ColorCard blueCard = new ColorCard(new BasicCard("Blue", "Number")); //Creates a new ColorCard object
        ColorCard greenCard = new ColorCard(new BasicCard("Green", "Number")); //Creates a new ColorCard object
        ColorCard yellowCard = new ColorCard(new BasicCard("Yellow", "Number")); //Creates a new ColorCard object

        assertEquals("Red", redCard.getColor()); //Checks if the color is correct
        assertEquals("Blue", blueCard.getColor()); //Checks if the color is correct
        assertEquals("Green", greenCard.getColor()); //Checks if the color is correct
        assertEquals("Yellow", yellowCard.getColor()); //Checks if the color is correct
    }

    @Test
    public void testPlay() {
        ColorCard card = new ColorCard(new BasicCard("Red", "Number"));
        Game game = new Game();
        card.play(game);
        assertEquals("Red", game.getTopCard().getColor());
    }
}
