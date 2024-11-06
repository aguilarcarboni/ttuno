package leomessi.uno.Moves;
import java.util.List;
import java.util.Scanner;

import leomessi.uno.Cards.Card;

public class HumanMoveSelector implements MoveSelector {

    private Scanner scanner; //Scanner to read the user's input

    public HumanMoveSelector() { //Constructor
        this(new Scanner(System.in)); //Initializes the scanner, using the default scanner
    }

    public HumanMoveSelector(Scanner scanner) { //Constructor
        this.scanner = scanner; //Initializes the scanner, using the scanner passed as a parameter, for testing purposes
    }

    @Override
    public Card selectMove(List<Card> hand, Card topCard){ //Selects a move from the player's hand
        System.out.println("Top card: " + topCard); //Prints the top card
        System.out.println("Your hand:");
        for (int i = 0; i < hand.size(); i++) { //Prints the player's hand
            System.out.println(i + ": " + hand.get(i));
        }
        int choice;
        while (true) { //Reads the user's choice
            System.out.println("Enter the number of the card you want to play, or -1 to draw:"); //Prints the prompt
            try { //Tries to read the user's choice
                choice = scanner.nextInt(); //Reads the user's choice
                if (choice == -1 || (choice >= 0 && choice < hand.size())) { //Checks if the choice is valid
                    break; //Breaks the loop if the choice is valid
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (java.util.InputMismatchException e) { //Catches the exception if the user's choice is not a number
                System.out.println("Invalid input. Please enter a number."); //Prints the error message if the user's choice is not a number
                scanner.next(); // Clears the invalid input
            }
        }
        
        if (choice == -1) { //If the user wants to draw
            return null; //Returns null to draw a card
        }
        return hand.get(choice); //Returns the selected card
    }
    
}
