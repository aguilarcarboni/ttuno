package leomessi.uno.Cards;
import leomessi.uno.Game;

public class SkipCard extends CardDecorator{ //SkipCard decorator
    public SkipCard(Card decoratedCard){ //Constructor
        super(decoratedCard); //Calls the constructor of the parent class (CardDecorator)
    }

    @Override
    public String getType(){
        return "Skip"; //Returns the type of the card
    }

    @Override 
    public void play(Game game){ //Overrides the play method
        super.play(game); //Calls the play method of the parent class (CardDecorator)
        game.SkipTurn(); // Skips the turn of the next player
    }

    @Override public String toString(){ //Overrides the toString method
        return decoratedCard.toString(); //Returns a string representation of the card
    }
}
