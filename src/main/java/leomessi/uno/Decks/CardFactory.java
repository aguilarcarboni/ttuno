//This interface is used to instantiate cards with the specified color and number
package leomessi.uno.Decks;
import leomessi.uno.Cards.Card;

public interface CardFactory { //Interface to create cards
    Card createCard(String color, String number); // Create a card with the specified color and number
}
