package leomessi.uno.Players;
import java.util.List;
import java.util.ArrayList;

public class Lobby { //Lobby is a class that manages the players in the game
    private List<PlayerObserver> Players; //List of players in the game
    private String gameState; //State of the game

    public Lobby(){ //Constructor
        Players = new ArrayList<>(); //Initializes the list of players
    }

    public void addPlayer(PlayerObserver player){ //Adds a player to the list
        Players.add(player);
    }

    public void removePlayer(PlayerObserver player){ //Removes a player from the list
        Players.remove(player);
    }

    public void setGameState(String state){ //Sets the state of the game
        this.gameState = state; //Sets the state of the game
        notifyPlayers(); //Notifies all players in the list
    }

    public void notifyPlayers(){ //Notifies all players in the list
        for (PlayerObserver player : Players) { //For each player in the list
            player.update(gameState); //Notify the player
        }
    }
}
