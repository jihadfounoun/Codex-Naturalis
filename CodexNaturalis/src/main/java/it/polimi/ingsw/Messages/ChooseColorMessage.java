package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player.Color;

import java.util.List;

/**
 * <strong>ChooseColorMessage</strong>
 * <p>
 * ChooseColorMessage is a message sent to the client with the available colors to choose from.
 * It contains a {@link List} of {@link Color}.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class ChooseColorMessage extends Message {
    private final List<Color> availableColors;

    /**
     * ChooseColorMessage builder
     *
     * @param availableColors the available colors
     */
    public ChooseColorMessage(List<Color> availableColors) {
        this.availableColors = availableColors;
    }

    /**
     * getter for the available colors
     * @return the available colors
     */
    public List<Color> getAvailableColors() {
        return availableColors;
    }
}
