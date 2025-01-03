package it.polimi.ingsw.Model.GoalCard;


import it.polimi.ingsw.Model.Card.Coordinate;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.PlayableCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ObjCorner;
import it.polimi.ingsw.Model.Card.enumerations.TypeObject;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.Model.Card.enumerations.Resource.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectGoalTest {
    Player player;
    ObjectGoal inkwellGoal;
    ObjectGoal manuscriptGoal;
    ObjectGoal quillGoal;
    ObjectGoal trioGoal;

    @BeforeEach
    void setUp() {
        player = new Player("Player7");

        inkwellGoal = new ObjectGoal("101", TypeObject.INKWELL);
        manuscriptGoal = new ObjectGoal("100", TypeObject.MANUSCRIPT);
        quillGoal = new ObjectGoal("102", TypeObject.QUILL);
        trioGoal = new ObjectGoal("99");

        Corner[] redCorner = new Corner[4];
        redCorner[0] = new ObjCorner(TypeObject.MANUSCRIPT);
        redCorner[1] = new Corner(false, false);
        redCorner[2] = new Corner(false, false);
        redCorner[3] = new Corner(false, false);

        Corner[] blueCorner = new Corner[4];
        blueCorner[0] = new ObjCorner(TypeObject.INKWELL);
        blueCorner[1] = new Corner(false, false);
        blueCorner[2] = new Corner(false, false);
        blueCorner[3] = new Corner(false, false);

        Corner[] purpleCorner = new Corner[4];

        purpleCorner[0] = new Corner(false, false);
        purpleCorner[1] = new ObjCorner(TypeObject.QUILL);
        purpleCorner[2] = new Corner(false, false);
        purpleCorner[3] = new Corner(false, false);

        Corner[] greenCorner = new Corner[4];
        greenCorner[0] = new Corner(false, false);
        greenCorner[1] = new Corner(false, false);
        greenCorner[2] = new Corner(false, false);
        greenCorner[3] = new ObjCorner(TypeObject.MANUSCRIPT);

        Corner[] c = new Corner[4];
        c[0] = new Corner(false, false);
        c[1] = new Corner(false, false);
        c[2] = new Corner(false, false);
        c[3] = new Corner(false, false);

        ArrayList<PlayableCard> hand = new ArrayList<>(3);
        ResourceCard q1 = new ResourceCard("id", c, FUNGI);
        hand.add(q1);
        ResourceCard q2 = new ResourceCard("id", c, FUNGI);
        hand.add(q2);
        ResourceCard q3 = new ResourceCard("id", c, FUNGI);
        hand.add(q3);

        InitialCard init = new InitialCard("id", c, c, null);

        player.setUpPlayerItems(80, init, hand);


        ResourceCard r1 = new ResourceCard("id", redCorner, FUNGI);
        player.getPlayerBoard().addToBoard(r1, new Coordinate(40 + (1), 40 - (1)));
        ResourceCard r2 = new ResourceCard("id", redCorner, FUNGI);
        player.getPlayerBoard().addToBoard(r2, new Coordinate(40 + (2), 40 - (2)));
        ResourceCard r3 = new ResourceCard("id", redCorner, FUNGI);
        player.getPlayerBoard().addToBoard(r3, new Coordinate(40 + (3), 40 - (3)));
        ResourceCard r4 = new ResourceCard("id", redCorner, FUNGI);
        player.getPlayerBoard().addToBoard(r4, new Coordinate(40 + (4), 40 - (4)));

        ResourceCard a1 = new ResourceCard("id", blueCorner, ANIMAL);
        player.getPlayerBoard().addToBoard(a1, new Coordinate(40 + (-1), 40 - (-1)));
        ResourceCard a2 = new ResourceCard("id", blueCorner, ANIMAL);
        player.getPlayerBoard().addToBoard(a2, new Coordinate(40 + (-2), 40 - (-2)));
        ResourceCard a3 = new ResourceCard("id", blueCorner, ANIMAL);
        player.getPlayerBoard().addToBoard(a3, new Coordinate(40 + (-3), 40 - (-3)));
        ResourceCard a4 = new ResourceCard("id", blueCorner, ANIMAL);
        player.getPlayerBoard().addToBoard(a4, new Coordinate(40 + (-4), 40 - (-4)));

        ResourceCard i1 = new ResourceCard("id", purpleCorner, INSECT);
        player.getPlayerBoard().addToBoard(i1, new Coordinate(40 + (-1), 40 - (1)));
        ResourceCard i2 = new ResourceCard("id", purpleCorner, INSECT);
        player.getPlayerBoard().addToBoard(i2, new Coordinate(40 + (-2), 40 - (2)));
        ResourceCard i3 = new ResourceCard("id", purpleCorner, INSECT);
        player.getPlayerBoard().addToBoard(i3, new Coordinate(40 + (-3), 40 - (3)));
        ResourceCard i4 = new ResourceCard("id", purpleCorner, INSECT);
        player.getPlayerBoard().addToBoard(i4, new Coordinate(40 + (-4), 40 - (4)));

        ResourceCard p1 = new ResourceCard("id", greenCorner, PLANT);
        player.getPlayerBoard().addToBoard(p1, new Coordinate(40 + (1), 40 - (-1)));
        ResourceCard p2 = new ResourceCard("id", greenCorner, PLANT);
        player.getPlayerBoard().addToBoard(p2, new Coordinate(40 + (2), 40 - (-2)));
        ResourceCard p3 = new ResourceCard("id", greenCorner, PLANT);
        player.getPlayerBoard().addToBoard(p3, new Coordinate(40 + (3), 40 - (-3)));
        ResourceCard p4 = new ResourceCard("id", greenCorner, PLANT);
        player.getPlayerBoard().addToBoard(p4, new Coordinate(40 + (4), 40 - (-4)));
    }

    @Test
    void testUpdate() {
        int[] expected = {2, 4, 2, 12};

        assertEquals(expected[0], inkwellGoal.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[1], manuscriptGoal.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[2], quillGoal.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[3], trioGoal.checkPoints(player.getPlayerBoard()));
    }
}
