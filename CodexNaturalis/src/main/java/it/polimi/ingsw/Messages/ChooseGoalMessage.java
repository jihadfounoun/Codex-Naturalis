package it.polimi.ingsw.Messages;

import it.polimi.ingsw.ModelView.CardView.GoalCardView;
import it.polimi.ingsw.ModelView.CommonBoardView.CommonBoardView;
import it.polimi.ingsw.ModelView.PlayerView.PlayerView;
import it.polimi.ingsw.ModelView.PlayerView.PublicPlayerView;

import java.util.List;


/**
 * <strong>ChooseGoalMessage</strong>
 * <p>
 * ChooseGoalMessage is a message sent to the client to ask the player to choose between two goals.
 * It contains the two {@link GoalCardView} to choose from, the player's {@link PlayerView}, the {@link CommonBoardView} and all the players' {@link PublicPlayerView}.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class ChooseGoalMessage extends Message {
    private final GoalCardView[] goals;
    private final List<PublicPlayerView> othersPlayersView;
    private final CommonBoardView commonBoard;
    private final PlayerView player;

    /**
     * ChooseGoalMessage builder
     * @param goals1 the first goal
     * @param goals2 the second goal
     * @param players the list of {@link PublicPlayerView} of all the players
     * @param player the player's {@link PlayerView}
     * @param commonBoard the {@link CommonBoardView}
     */
    public ChooseGoalMessage(GoalCardView goals1, GoalCardView goals2, List<PublicPlayerView> players, PlayerView player, CommonBoardView commonBoard) {
        this.goals = new GoalCardView[2];
        this.goals[0] = goals1;
        this.goals[1] = goals2;
        this.othersPlayersView = players;
        this.commonBoard = commonBoard;
        this.player = player;
    }

    /**
     * getter for the goals
     * @return the goals
     */
    public GoalCardView[] getGoals() {
        return goals;
    }

    /**
     * getter for the player
     * @return the player
     */
    public PlayerView getPlayer() {
        return player;
    }

    /**
     * getter for the common board
     * @return the common board
     */
    public CommonBoardView getCommonBoard() {
        return commonBoard;
    }

    /**
     * getter for all the players' view
     * @return all the players' view
     */
    public List<PublicPlayerView> getOthersPlayersView() {
        return othersPlayersView;
    }
}
