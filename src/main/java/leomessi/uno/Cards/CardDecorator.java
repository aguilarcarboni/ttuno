package leomessi.uno.Cards;
import leomessi.uno.Game;

public abstract class CardDecorator implements Card { //CardDecorator class implements the Card interface
    protected Card decoratedCard; //The card that is being decorated

    public CardDecorator(Card decoratedCard){ //Constructor that takes a card as a parameter
        this.decoratedCard = decoratedCard; //Sets the decorated card to the card passed as a parameter
    }

    
    public String getColor(){ //Returns the color of the decorated card
        return decoratedCard.getColor();
    }

    
    public String getType(){ //Returns the type of the decorated card
        return decoratedCard.getType();
    }

    
    public void play(Game game){ //Plays the decorated card
        decoratedCard.play(game);
    }

    
    public String toString(){ //Returns the string representation of the decorated card
        return decoratedCard.toString();
    }
}
