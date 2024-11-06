package leomessi.uno.Cards;
import leomessi.uno.Game;

public class BasicCard implements Card { //BasicCard class implements the Card interface
    
    private String color; //Color of the card
    private String type; //Type of the card, either number or action

    public BasicCard(String color, String type){ //Constructor
        this.color = color; //Sets the color of the card
        this.type = type; //Sets the type of the card
    }

    public int getNumber() {
        try {
            return Integer.parseInt(type); //Returns the number of the card as an integer
        } catch (NumberFormatException e) { //If the type is not a valid number
            return -1; //Returns -1
        }
    }

    @Override 
    public String getColor(){ //Returns the color of the card
        return color;
    }

    @Override 
    public String getType(){ //Returns the type of the card
        return type;
    }

    @Override 
    public void play(Game game){ //Plays the card
        game.setTopCard(this); //Sets the top card to the card that was played, "this" is the card that was played
    }

    @Override 
    public String toString(){ //Returns a string representation of the card
        return color + "_" + type;
    }
}
