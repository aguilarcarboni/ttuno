package leomessi.uno.Cards;
import leomessi.uno.Game;

public class ColorCard extends CardDecorator{ //ColorCard class extends CardDecorator
    public ColorCard(Card decoratedCard){ //Constructor
        super(decoratedCard); //Calls the constructor of the parent class (CardDecorator)
    }


    @Override
    public void play(Game game){ //Overrides the play method
        super.play(game); //Calls the play method of the parent class (CardDecorator)
    }

    @Override
    public String toString() { //Overrides the toString method
        return "Color: " + getColor(); //Returns a string representation of the card
    }
}
