package it.polimi.ingsw.Model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * This enumeration is used to set the token of the player. This token is only defined by a color of this enum,
 * and it allows to distinguish the players graphically.
 *
 *
 */

public enum Color implements Serializable {
    YELLOW, RED, BLUE, GREEN, NONE;

    public static List<Color> getColors() {
        return new ArrayList<Color>() {{
            add(YELLOW);
            add(RED);
            add(BLUE);
            add(GREEN);
        }};
    }

    public String toString() {
        switch (this) {
            case YELLOW:
                return "yellow";
            case RED:
                return "red";
            case BLUE:
                return "blue";
            case GREEN:
                return "green";
            default:
                return "none";
        }
    }
}
