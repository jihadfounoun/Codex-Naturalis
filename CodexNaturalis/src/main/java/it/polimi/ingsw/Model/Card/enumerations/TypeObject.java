package it.polimi.ingsw.Model.Card.enumerations;
/**
 * This enum represents an object in the Codex Naturalis application.
 * The types include inkwell, quill, and manuscript.
 *
 * @author Jihad Founoun
 */
public enum TypeObject {
    INKWELL, QUILL, MANUSCRIPT;

    /**
     * Returns a string representation of the type of the object.
     * @return a string representation of the type of the object
     */
    public String toString() {
        switch (this) {
            case INKWELL:
                return "Inkwell";
            case QUILL:
                return "Quill";
            case MANUSCRIPT:
                return "Manuscript";
        }
        return null;
    }

    /**
     * Returns a string representation of the type of the object for display.
     * @return a string representation of the type of the object for display
     */
    public String display() {
        return switch (this) {
            case INKWELL -> "\u26B1\uFE0F";
            case MANUSCRIPT -> "\uD83D\uDCDC";
            case QUILL -> "\uD83E\uDEB6";
        };
    }
}
