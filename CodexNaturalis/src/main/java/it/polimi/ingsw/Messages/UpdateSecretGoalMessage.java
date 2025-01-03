package it.polimi.ingsw.Messages;

import it.polimi.ingsw.ModelView.CardView.GoalCardView;
import it.polimi.ingsw.ModelView.PlayerView.PlayerView;
import it.polimi.ingsw.ModelView.PlayerView.PublicPlayerView;

/**
 * <strong>UpdateSecretGoalMessage</strong>
 * <p>
 *     Message used to notify the client that the secret goal of the player has been updated.
 *     It contains the updated secret goal, the updated player view and the updated public player view.
 *     Extends {@link Message}.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class UpdateSecretGoalMessage extends Message {
    private final GoalCardView secretGoalView;
    private final PlayerView playerView;
    private final PublicPlayerView publicPlayerView;

    /**
     * UpdateSecretGoalMessage builder
     * @param secretGoalView the updated secret goal of the player
     * @param playerView the updated player view
     * @param publicPlayerView the updated public player view
     */
    public UpdateSecretGoalMessage(GoalCardView secretGoalView, PlayerView playerView, PublicPlayerView publicPlayerView) {

        this.secretGoalView = secretGoalView;
        this.playerView = playerView;
        this.publicPlayerView = publicPlayerView;
    }

    /**
     * Get the updated secret goal of the player
     * @return the updated secret goal of the player
     */
    public GoalCardView getSecretGoal() {
        return secretGoalView;
    }
}
