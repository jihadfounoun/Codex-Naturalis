package it.polimi.ingsw.Model.GoalCard;

import it.polimi.ingsw.Model.Card.Coordinate;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.PlayableCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static it.polimi.ingsw.Model.Card.enumerations.Resource.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DiagonalFirstTest {
    Player player;
    DiagonalGoal fungiDiagonal;
    DiagonalGoal animalDiagonal;
    DiagonalGoal insectDiagonal;
    DiagonalGoal plantDiagonal;

    @BeforeEach
    void setUp() {
        player = new Player("Player2");

        fungiDiagonal = new DiagonalGoal("87", FUNGI, true);
        animalDiagonal = new DiagonalGoal("89", ANIMAL, true);
        insectDiagonal = new DiagonalGoal("90", INSECT, false);
        plantDiagonal = new DiagonalGoal("88", PLANT, false);

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

        ResourceCard r1 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r1, new Coordinate(40 + (1), 40 - (1)));
        ResourceCard r2 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r2, new Coordinate(40 + (2), 40 - (2)));
        ResourceCard r3 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r3, new Coordinate(40 + (3), 40 - (3)));
        ResourceCard r4 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r4, new Coordinate(40 + (4), 40 - (4)));

        ResourceCard a1 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a1, new Coordinate(40 + (-1), 40 - (-1)));
        ResourceCard a2 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a2, new Coordinate(40 + (-2), 40 - (-2)));
        ResourceCard a3 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a3, new Coordinate(40 + (-3), 40 - (-3)));
        ResourceCard a4 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a4, new Coordinate(40 + (-4), 40 - (-4)));

        ResourceCard i1 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i1, new Coordinate(40 + (-1), 40 - (1)));
        ResourceCard i2 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i2, new Coordinate(40 + (-2), 40 - (2)));
        ResourceCard i3 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i3, new Coordinate(40 + (-3), 40 - (3)));
        ResourceCard i4 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i4, new Coordinate(40 + (-4), 40 - (4)));

        ResourceCard p1 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p1, new Coordinate(40 + (1), 40 - (-1)));
        ResourceCard p2 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p2, new Coordinate(40 + (2), 40 - (-2)));
        ResourceCard p3 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p3, new Coordinate(40 + (3), 40 - (-3)));
        ResourceCard p4 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p4, new Coordinate(40 + (4), 40 - (-4)));
    }

    @Test
    void testUpdate() {
        int[] expected = {2, 2, 2, 2};

        assertEquals(expected[0], fungiDiagonal.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[1], animalDiagonal.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[2], insectDiagonal.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[3], plantDiagonal.checkPoints(player.getPlayerBoard()));

    }
}
