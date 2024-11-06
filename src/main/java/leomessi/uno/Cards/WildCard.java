package leomessi.uno.Cards;
import leomessi.uno.Game;

public class WildCard extends CardDecorator{ //WildCard decorator
    public WildCard(Card decoratedCard){ //Constructor
        super(decoratedCard); //Calls the constructor of the parent class (CardDecorator)
    }

    @Override
    public String getType(){
        return "Wild"; //Returns the type of the card
    }

    @Override
    public void play(Game game){ //Overrides the play method
        String newColor = game.chooseColor(); //Chooses a new color
        game.setCurrentColor(newColor); //Sets the current color to the new color
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
