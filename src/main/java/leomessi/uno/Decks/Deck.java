// Create an instance of DeckBuilder to build the deck and shuffle it
package leomessi.uno.Decks;
import java.util.Collections;
import java.util.List;

import leomessi.uno.Cards.Card;

public class Deck { //Class for the deck of cards
    private List<Card> cards; //List of cards in the deck

    public Deck(){ //Constructor for the deck
        DeckBuilder builder = new DeckBuilder(); //Create a instance of DeckBuilder
        cards = builder.addNumberCards().addSpecialCards().addWildCards().build(); //Build the deck and add it to the list cards
        Collections.shuffle(cards); //Shuffle the deck
        
        // Reshuffle if top card is a wild card
        while (cards.get(cards.size() - 1).getType().equals("Wild")) {
            System.out.println("Reshuffling deck due to wild card on top");
            Collections.shuffle(cards);
        }
    }

    public void shuffle(){ //Method to shuffle the deck
        Collections.shuffle(cards); //Shuffle the deck
    }

    public Card drawCard(){ //Method to draw a card from the deck
        if(cards.isEmpty()){ //If the deck is empty
            throw new IllegalStateException("Deck is empty"); //Throw an exception
        }
        return cards.remove(cards.size() - 1); //Remove and return the top card from the deck
    }

    public void AddCard(Card card){ //Method to add a card to the deck
        if(cards.size() > 108){ //If the deck is full
            throw new IllegalStateException("Deck is full"); //Throw an exception
        }
        cards.add(card); //Add the card to the deck
    }

    public int size(){ //Method to get the size of the deck
        return cards.size(); //Return the size of the deck
    }
}
