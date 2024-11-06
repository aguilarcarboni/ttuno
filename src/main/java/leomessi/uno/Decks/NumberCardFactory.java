//This class is used to instantiate number cards with the specified color and number
package leomessi.uno.Decks;
import leomessi.uno.Cards.Card;
import leomessi.uno.Cards.BasicCard;
public class NumberCardFactory implements CardFactory{ //NumberCardFactory implements the interface CardFactory
    public Card createCard(String color, String number){ //Create a card with the specified color and number
        switch(color){ //Switch statement to create the card with the specified color
            case "Red": //If the color is Red, create a RedCard with the specified number
                return new BasicCard("Red", number);
            case "Blue": //If the color is Blue, create a BlueCard with the specified number    
                return new BasicCard("Blue", number);
            case "Green": //If the color is Green, create a GreenCard with the specified number
                return new BasicCard("Green", number);
            case "Yellow": //If the color is Yellow, create a YellowCard with the specified number
                return new BasicCard("Yellow", number);
            default: //If the color is not valid, return null
                throw new IllegalArgumentException("Invalid color"); //Throw an exception if the color is not valid
        }
    }
}
