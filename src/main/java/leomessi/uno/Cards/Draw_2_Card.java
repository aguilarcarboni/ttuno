package leomessi.uno.Cards;
import leomessi.uno.Game;

public class Draw_2_Card extends CardDecorator{ //Draw 2 Card decorator
    public Draw_2_Card(Card decoratedCard){ //Constructor
        super(decoratedCard); //Calls the constructor of the parent class (CardDecorator)
    }

    @Override
    public String getType(){
        return "Draw 2"; //Returns the type of the card
    }

    @Override 
    public void play(Game game){ //Overrides the play method
        super.play(game); //Calls the play method of the parent class (CardDecorator)
        game.nextPlayerDrawCards(2); //Moves to the next player and makes them draw 2 cards
        game.SkipTurn(); //Skips the turn of the current player
    }

    @Override 
    public String toString(){ //Overrides the toString method
        return decoratedCard.toString(); //Returns a string representation of the card
    }
}
