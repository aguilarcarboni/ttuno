package leomessi.uno.Cards;
import leomessi.uno.Game;

public class WildDraw_4_Card extends CardDecorator{ //Draw 4 Card decorator
    public WildDraw_4_Card(Card decoratedCard){ //Constructor
        super(decoratedCard);
    }

    @Override
    public String getType(){
        return "Wild Draw 4"; //Returns the type of the card
    }

    @Override 
    public void play(Game game){ //Overrides the play method
        String newColor = game.chooseColor(); //Chooses a new color
        game.setCurrentColor(newColor); //Sets the current color to the new color
        game.nextPlayerDrawCards(4); //Moves to the next player and makes them draw 4 cards
        game.SkipTurn(); //Skips the turn of the current player
    }

    @Override 
    public String toString(){ //Overrides the toString method
        return decoratedCard.toString(); //Returns a string representation of the card
    }

    @Override 
    public String getColor(){ //Overrides the getColor method
        return "Black"; //Returns the color of the card
    }
}
