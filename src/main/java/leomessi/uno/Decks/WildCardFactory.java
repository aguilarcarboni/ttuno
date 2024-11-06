// This class is used to instantiate wild cards
package leomessi.uno.Decks;
import leomessi.uno.Cards.Card;
import leomessi.uno.Cards.WildCard;
import leomessi.uno.Cards.BasicCard;
import leomessi.uno.Cards.WildDraw_4_Card;

public class WildCardFactory implements CardFactory{ //WildCardFactory implements the interface CardFactory
    public Card createCard(String color, String number){ //Create a card with the specified color and number
        switch(number){ //Switch statement to create the card with the specified number
            case "Wild":
                return new WildCard(new BasicCard(color, "Wild")); //Create a WildCard
            case "Wild Draw 4":
                return new WildDraw_4_Card(new BasicCard("Black", "Wild Draw 4")); //Create a WildDraw_4_Card
            default:
                throw new IllegalArgumentException("Invalid wild card number"); //Throw an exception if the number is not valid
        }
    }
}

