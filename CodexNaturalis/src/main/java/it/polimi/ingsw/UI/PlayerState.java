package it.polimi.ingsw.UI;

/**
 * <strong>PlayerState</strong>
 * <p>
 * Enum representing the possible states of a player during the game.
 * </p>
 *
 * @author Locatelli Mattia
 */
public enum PlayerState {
    /**
     * The player is waiting for the game to start or for his turn.
     */
    WAITING,
    /**
     * The player is choosing a card to play.
     */
    PLAYING_CARD,
    /**
     * The player is drawing a card from the deck.
     */
    DRAWING_CARD,
    /**
     * The player is choosing his color.
     */
    SETTING_COLOR,
    /**
     * The player is choosing the initial card play side.
     */
    SETTING_INITIAL,
    /**
     * The player is choosing the goal to achieve.
     */
    SETTING_GOAL
}
