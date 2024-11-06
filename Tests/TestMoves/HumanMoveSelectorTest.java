package UNO.Moves.TestMoves;
import UNO.Moves.HumanMoveSelector;
import UNO.Cards.BasicCard;
import UNO.Cards.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HumanMoveSelectorTest { //Class for the HumanMoveSelectorTest

    private List<Card> hand; //List of cards in the player's hand
    private Card topCard; //The top card of the discard pile

    @BeforeEach
    void setUp() { //Sets up the test
        hand = Arrays.asList( //Initializes the hand with 3 cards
            new BasicCard("Red", "5"),
            new BasicCard("Blue", "7"),
            new BasicCard("Green", "2")
        );
        topCard = new BasicCard("Yellow", "4"); //Initializes the top card of the discard pile
    }

    @Test
    void testSelectValidCard() { //Tests if the user selects a valid card
        String input = "1\n"; //The user's choice
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); //Creates a new input stream with the user's choice
        Scanner scanner = new Scanner(inputStream); //Creates a new scanner with the input stream
        
        HumanMoveSelector selector = new HumanMoveSelector(scanner); //Creates a new HumanMoveSelector with the scanner
        
        Card selectedCard = selector.selectMove(hand, topCard);
        
        assertNotNull(selectedCard); //Checks if the selected card is not null
        assertEquals("Blue", selectedCard.getColor()); //Checks if the selected card is the second card in the hand
        assertEquals("7", selectedCard.getType()); //Checks if the selected card is the second card in the hand
    }

    @Test
    void testSelectDraw() {
        String input = "-1\n"; //The user's choice
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); //Creates a new input stream with the user's choice
        Scanner scanner = new Scanner(inputStream); //Creates a new scanner with the input stream
        
        HumanMoveSelector selector = new HumanMoveSelector(scanner); //Creates a new HumanMoveSelector with the scanner
        
        Card selectedCard = selector.selectMove(hand, topCard); //Selects the move
        
        assertNull(selectedCard); //Checks if the selected card is null
    }

    @Test
    void testInvalidInputThenValidInput() {
        String input = "5\n1\n";  // First an invalid input, then a valid one
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes()); //Creates a new input stream with the user's choice
        Scanner scanner = new Scanner(inputStream); //Creates a new scanner with the input stream
        
        HumanMoveSelector selector = new HumanMoveSelector(scanner);
        
        Card selectedCard = selector.selectMove(hand, topCard); //Selects the move
        
        assertNotNull(selectedCard); //Checks if the selected card is not null
        assertEquals("Blue", selectedCard.getColor()); //Checks if the selected card is the second card in the hand
        assertEquals("7", selectedCard.getType()); //Checks if the selected card is the second card in the hand
    }
}
