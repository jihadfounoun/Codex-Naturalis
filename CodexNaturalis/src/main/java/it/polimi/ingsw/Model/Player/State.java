package it.polimi.ingsw.Model.Player;

import java.io.Serializable;
/**
 * This enumeration is used to describe the state of the player.
 * If it's set to Winner, the player has won the game.
 * Loser is the opposite.
 * Pending if it's expected the player does something
 * Skipped if the player can't do anything during his turn
 * Disconnect if the player lost the connection
 *
 *
 */

public enum State implements Serializable {
    WINNER, LOSER, PENDING, SKIPPED, DISCONNECTED;

    public String toString() {
        switch (this) {
            case WINNER:
                return "Winner";
            case LOSER:
                return "Loser";
            case PENDING:
                return "In progress";
            case SKIPPED:
                return "Skipped";
            case DISCONNECTED:
                return "Disconnected";
            default:
                return "Error";
        }
    }
}
