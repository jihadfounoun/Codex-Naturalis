package it.polimi.ingsw.UI;

/**
 * <strong>ViewState</strong>
 * <p>
 * Enum representing the possible states of the view during the game.
 * </p>
 *
 * @author Locatelli Mattia
 */
public enum ViewState {
    /**
     * The view is waiting for the player to make a choice.
     */
    WAITING_FOR_PLAYER,
    /**
     * The view is waiting for the outcome of the player's choice.
     */
    WAITING_FOR_OUTCOME,
    /**
     * The game has ended.
     */
    GAME_ENDED
}
