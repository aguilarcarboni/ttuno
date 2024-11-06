package leomessi.uno.Cards;
import leomessi.uno.Game;

public class ReverseCard extends CardDecorator{ //ReverseCard decorator
    public ReverseCard(Card decoratedCard){ //Constructor
        super(decoratedCard); //Calls the constructor of the parent class (CardDecorator)
    }

    @Override
    public String getType(){
        return "Reverse"; //Returns the type of the card
    }

    @Override 
    public void play(Game game){
        super.play(game); //Plays the decorated card
        game.reverseDirection(); // Reverses the direction of the game
    }

    @Override public String toString(){
        return decoratedCard.toString(); //Returns the string representation of the decorated card
    }
}
