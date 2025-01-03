package it.polimi.ingsw.Controller;

/**
 * The ErrorMessages class provides a set of static error message strings that can be used throughout the Codex Naturalis application.
 * These messages are used to inform the user about various error conditions related to game actions such as playing a card, drawing a card, or selecting a color.
 * <p>
 * The class includes the following error messages:
 * - `cardNonPlayable`: Indicates that the selected card cannot be played.
 * - `cardNotFound`: Indicates that the card the user is looking for is not in their hand.
 * - `positionNonPlayable`: Indicates that the card cannot be played in the selected position.
 * - `cardNonDrawable`: Indicates that a card cannot be drawn from the deck.
 * - `colorNotAvailable`: Indicates that the selected color is not available.
 *
 * @author Jihad Founoun
 */

public class ErrorMessages {
    public static String cardNonPlayable = "You can't play the selected card";
    public static String cardNotFound = "The card you are looking for is not in the hand";
    public static String positionNonPlayable = "You can't play the card in the selected position";
    public static String cardNonDrawable = " You can't draw a card from the deck";
    public static String colorNotAvailable = " The selected color is not available";

}