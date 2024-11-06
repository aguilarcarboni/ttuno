package leomessi.uno.Players;
import leomessi.uno.Moves.HumanMoveSelector;

public class HumanPlayer extends Player {

    public HumanPlayer(String name){ //Constructor
        super(name, new HumanMoveSelector()); //Sets the name and move selector of the player
    }

    public HumanMoveSelector getMoveSelector(){ //Returns the move selector of the player, used for testing
        return (HumanMoveSelector) super.moveSelector; //Returns the move selector of the player
    }
}
