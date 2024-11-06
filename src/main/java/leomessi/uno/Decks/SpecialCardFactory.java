//This class is used to instantiate special cards
package leomessi.uno.Decks;
import leomessi.uno.Cards.Card;
import leomessi.uno.Cards.Draw_2_Card;
import leomessi.uno.Cards.SkipCard;
import leomessi.uno.Cards.ReverseCard;
import leomessi.uno.Cards.BasicCard;
public class SpecialCardFactory implements CardFactory{ //SpecialCardFactory implements the interface CardFactory
    public Card createCard(String color, String type){ //Create a card with the specified color and type
        switch(type){ //Switch statement to create the card with the specified type
            case "Draw 2":
                return new Draw_2_Card(new BasicCard(color, type)); //Create a Draw_2_Card with the specified color
            case "Skip":
                return new SkipCard(new BasicCard(color, type)); //Create a SkipCard with the specified color
            case "Reverse":
                return new ReverseCard(new BasicCard(color, type)); //Create a ReverseCard with the specified color
            default:
                throw new IllegalArgumentException("Invalid special card type"); //Throw an exception if the type is not valid
        }
    }
}
