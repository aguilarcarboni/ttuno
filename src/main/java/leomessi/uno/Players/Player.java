package leomessi.uno.Players;
import java.util.ArrayList;
import java.util.List;

import leomessi.uno.Cards.Card;
import leomessi.uno.Moves.MoveSelector;
import leomessi.uno.Moves.MoveValidator;

public abstract class Player implements PlayerObserver {
    protected String name; //Name of the player
    protected List<Card> hand; //Hand is the cards that the player has in their hand
    protected MoveSelector moveSelector; //Move selector is the strategy for selecting a move

    public Player(String name, MoveSelector moveSelector){ //Constructor
        this.name = name; //Sets the name of the player
        this.hand = new ArrayList<>(); //Initializes the hand of the player
        this.moveSelector = moveSelector; //Sets the move selector of the player
    }

    public Card playCard(Card topCard){ //Abstract method to play a card
        Card selectedCard = moveSelector.selectMove(hand, topCard); //Initializes the selected card
        if(selectedCard != null && MoveValidator.isValidMove(selectedCard, topCard)){ // If the selected card is valid
            hand.remove(selectedCard); // Removes the selected card from the player's hand
            return selectedCard; // Returns the selected card
        }
        return null; // Returns null if the selected card is not valid
    } //Abstract method to play a card
    
    public String getName(){// Returns the name of the player
        return name;
    }

    public void addCardToHand(Card card){ //Adds a card to the player's hand
        hand.add(card);
    }

    public int getHandSize(){ //Returns the size of the player's hand
        return hand.size();
    }

    @Override
    public void update(String message){ //Updates the player with a message
        System.out.println(name + " received update: " + message); //Prints the message to the console
    }

    public List<Card> getHand() {
        return hand;
    }
}
