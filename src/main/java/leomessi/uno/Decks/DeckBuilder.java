package leomessi.uno.Decks;
import java.util.ArrayList;
import java.util.List;

import leomessi.uno.Cards.Card;

public class DeckBuilder {
    private List<Card> cards = new ArrayList<>(); //List of cards
    private CardFactory numberCardFactory = new NumberCardFactory(); //NumberCardFactory
    private CardFactory specialCardFactory = new SpecialCardFactory(); //SpecialCardFactory
    private CardFactory wildCardFactory = new WildCardFactory(); //WildCardFactory

    public DeckBuilder addNumberCards(){ //Constructor for DeckBuilder number cards
        String[] colors = {"Red", "Green", "Blue", "Yellow"}; //Array of colors
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9"}; //Array of numbers
        for(String color : colors){ //For each color in the array, add 19 cards to the list
            cards.add(numberCardFactory.createCard(color, "0")); //Add the card to the list
            for(String number : numbers){
                cards.add(numberCardFactory.createCard(color, number)); //Add the card to the list
                cards.add(numberCardFactory.createCard(color, number)); //Add the card to the list
            }
        }
        return this; //Return the current instance of DeckBuilder
    }

    public DeckBuilder addSpecialCards(){ //Constructor for DeckBuilder special cards
        String[] colors = {"Red", "Green", "Blue", "Yellow"}; //Array of colors
        String[] specials = {"Draw 2", "Skip", "Reverse"}; //Array of special cards
        for(String color : colors){ //For each color in the array, add 2 cards to the list
            for(String special : specials){ //For each special card in the array, add 2 cards to the list
                cards.add(specialCardFactory.createCard(color, special)); //Add the card to the list
                cards.add(specialCardFactory.createCard(color, special)); //Add the card to the list
            }
        }
        return this; //Return the current instance of DeckBuilder
    }

    public DeckBuilder addWildCards(){ //Constructor for DeckBuilder wild cards
        String[] wilds = {"Wild", "Wild Draw 4"}; //Array of wild cards
        for(String wild : wilds){ //For each wild card in the array, add 4 cards to the list
            for(int i = 0; i < 4; i++){ //Add 4 cards to the list
                cards.add(wildCardFactory.createCard(null, wild)); //Add the card to the list
            }
        }
        return this; //Return the current instance of DeckBuilder
    }

    public List<Card> build(){ //Build the deck
        return new ArrayList<>(cards); //Return a copy of the built deck, protecting the DeckBuilder state
    }
}
