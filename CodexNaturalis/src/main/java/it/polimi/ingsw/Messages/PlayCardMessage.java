package it.polimi.ingsw.Messages;

import it.polimi.ingsw.ModelView.CommonBoardView.CommonBoardView;
import it.polimi.ingsw.ModelView.PlayerView.PlayerView;
import it.polimi.ingsw.ModelView.PlayerView.PublicPlayerView;

import java.util.List;

/**
 * <strong>PlayCardMessage</strong>
 * <p>
 * PlayCardMessage is a message sent to the client to ask the player to play a card.
 * It contains the {@link PlayerView} of the player that has to play the card, the {@link PublicPlayerView} of the other players and the {@link CommonBoardView}.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class PlayCardMessage extends Message {
    private final PlayerView player;
    private final List<PublicPlayerView> otherPlayers;
    private final CommonBoardView commonBoard;

    /**
     * PlayCardMessage builder
     * @param player {@link PlayerView} of the player that has to play the card
     * @param otherPlayers list of {@link PublicPlayerView} of all the players
     * @param commonBoard {@link CommonBoardView} of the game
     */
    public PlayCardMessage(PlayerView player, List<PublicPlayerView> otherPlayers, CommonBoardView commonBoard) {
        this.player = player;
        this.otherPlayers = otherPlayers;
        this.commonBoard = commonBoard;
    }

    /**
     * Get the {@link PublicPlayerView} of a given player
     * @param username the username of the player
     * @return the {@link PublicPlayerView} of the other players
     */
    public PublicPlayerView getOtherPlayers(String username) {
        for (PublicPlayerView otherPlayer : otherPlayers) {
            if (otherPlayer.getUsername().equals(username)) {
                return otherPlayer;
            }
        }
        return null;
    }

    /**
     * Get the list of {@link PlayerView} of the players
     * @return the list of {@link PlayerView} of the players
     */
    public PlayerView getPlayer() {
        return player;
    }

    /**
     * Get the {@link CommonBoardView} of the game
     * @return the {@link CommonBoardView} of the game
     */
    public CommonBoardView getCommonBoard() {
        return commonBoard;
    }
}
