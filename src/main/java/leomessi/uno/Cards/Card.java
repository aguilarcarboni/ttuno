//This class is used to create cards with the specified color and number
package leomessi.uno.Cards;
import leomessi.uno.Game;

public interface Card { //Card interface
    String getColor(); //Returns the color of the card
    String getType(); //Returns the type of the card
    void play(Game game); //Plays the card
}