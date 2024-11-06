package UNO.Decks.TestDecks;
import UNO.Decks.Deck;
import UNO.Cards.Card;
import UNO.Cards.BasicCard;
import UNO.Cards.WildCard;
import UNO.Cards.Draw_2_Card;
import UNO.Cards.WildDraw_4_Card;
import UNO.Cards.SkipCard;
import UNO.Cards.ReverseCard;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    @Test
    public void testDeckConstructor() {
        Deck deck = new Deck();
        
        // Test that the deck is not empty
        assertFalse(deck.size() == 0, "Deck should not be empty after construction");
        
        // Test that the deck has the correct number of cards
        assertEquals(108, deck.size(), "Deck should have 108 cards after construction");
        
        // Test that the deck contains different types of cards
        boolean hasNumberCard = false;
        boolean hasSpecialCard = false;
        
        for (int i = 0; i < 108; i++) {
            Card card = deck.drawCard();
            if (card instanceof BasicCard) hasNumberCard = true;
            if (card instanceof WildCard || card instanceof Draw_2_Card || card instanceof WildDraw_4_Card || card instanceof SkipCard || card instanceof ReverseCard) hasSpecialCard = true;
            
            if (hasNumberCard && hasSpecialCard) break;
        }
        
        assertTrue(hasNumberCard, "Deck should contain number cards");
        assertTrue(hasSpecialCard, "Deck should contain special cards");
    }

    @Test
    public void testShuffle() { //Test the shuffle method
        Deck deck = new Deck(); //Create a new deck
        List<Card> originalCards = new ArrayList<>(); //Create a list to store the original cards
        int deckSize = deck.size(); //Get the size of the deck
        
        for (int i = 0; i < deckSize; i++) { //Draw all cards from the deck and add them to originalCards to get the original order of cards
            originalCards.add(deck.drawCard());
        }
        
        deck = new Deck(); //Recreate the deck
        deck.shuffle(); //Shuffle the deck
        
        List<Card> shuffledCards = new ArrayList<>(); //Create a list to store the shuffled cards
        for (int i = 0; i < deckSize; i++) { //Draw all cards from the deck and add them to shuffledCards to get the shuffled order of cards
            shuffledCards.add(deck.drawCard());
        }
        
        assertNotEquals(originalCards, shuffledCards, "Deck should be shuffled"); //Test that the original (originalCards) and shuffled (shuffledCards) cards are not the same
    }

    @Test
    public void testDrawCard(){ //Test the drawCard method
        Deck deck = new Deck(); //Create a new deck
        Card card = deck.drawCard(); //Draw a card from the deck
        assertEquals(107, deck.size(), "Deck should have 107 cards after drawing one"); //Test that the deck has 107 cards after drawing one
        assertNotNull(card, "The drawn card should not be null"); //Test that the drawn card is not null
    }

    @Test
    public void testAddCard(){ //Test the addCard method
        Deck deck = new Deck(); //Create a new deck
        deck.AddCard(new BasicCard("Red", "1")); //Add a card to the deck
        assertEquals(109, deck.size(), "Deck should have 109 cards after adding one"); //Test that the deck has 109 cards after adding one
    }

    @Test
    public void testSize(){ //Test the size method
        Deck deck = new Deck(); //Create a new deck
        assertEquals(108, deck.size(), "Deck should have 108 cards after construction"); //Test that the deck has 108 cards after construction
    }
}
