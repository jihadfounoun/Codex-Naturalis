package it.polimi.ingsw.Model;
/**
 * This enum represents the various states that a game can be in during its lifecycle.
 * The states include waiting for setup, started, in the final turn, and ended.
 *
 * @author Jihad Founoun
 */
public enum GameState {
    WAITING,
    WAITING_FOR_TOKENS_SETUPS,
    WAITING_FOR_INITIAL_CARDS_SETUPS,
    WAITING_FOR_SECRET_GOALS_SETUPS,
    STARTED,
    PRE_FINAL_TURN,
    FINAL_TURN,
    ENDED;

    @Override
    public String toString() {
        return switch (this) {
            case WAITING -> "Waiting";
            case WAITING_FOR_SECRET_GOALS_SETUPS -> "Waiting for secret goals setups";
            case WAITING_FOR_INITIAL_CARDS_SETUPS -> "Waiting for initial cards setups";
            case WAITING_FOR_TOKENS_SETUPS -> "Waiting for tokens setups";
            case STARTED -> "Started";
            case PRE_FINAL_TURN -> "Pre final turn";
            case FINAL_TURN -> "Final turn";
            case ENDED -> "Ended";
        };
    }
}
