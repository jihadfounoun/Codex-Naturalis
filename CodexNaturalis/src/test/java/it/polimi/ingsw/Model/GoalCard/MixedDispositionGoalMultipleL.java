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

public class MixedDispositionGoalMultipleL {
    Player player;
    DiagonalGoal fungiDiagonal;
    DiagonalGoal animalDiagonal;
    DiagonalGoal insectDiagonal;
    DiagonalGoal plantDiagonal;
    LGoal fungiL;
    LGoal animalL;
    LGoal insectL;
    LGoal plantL;

    @BeforeEach
    void setUp() {
        player = new Player("Player6");

        fungiDiagonal = new DiagonalGoal("87", FUNGI, true);
        animalDiagonal = new DiagonalGoal("89", ANIMAL, true);
        insectDiagonal = new DiagonalGoal("90", INSECT, false);
        plantDiagonal = new DiagonalGoal("88", PLANT, false);

        fungiL = new LGoal("91", FUNGI, PLANT, 2);
        animalL = new LGoal("93", ANIMAL, FUNGI, 1);
        insectL = new LGoal("94", INSECT, ANIMAL, 0);
        plantL = new LGoal("92", PLANT, INSECT, 3);

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
        player.getPlayerBoard().addToBoard(r1, new Coordinate(40 + (-3), 40 - (1)));
        ResourceCard r2 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r2, new Coordinate(40 + (-2), 40 - (2)));
        ResourceCard r3 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r3, new Coordinate(40 + (-2), 40 - (4)));
        ResourceCard r4 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r4, new Coordinate(40 + (-1), 40 - (3)));
        ResourceCard r5 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r5, new Coordinate(40 + (3), 40 - (1)));
        ResourceCard r6 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r6, new Coordinate(40 + (-1), 40 - (5)));
        ResourceCard r7 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r7, new Coordinate(40 + (2), 40 - (4)));


        ResourceCard a1 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a1, new Coordinate(40 + (1), 40 - (-3)));
        ResourceCard a2 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a2, new Coordinate(40 + (2), 40));
        ResourceCard a3 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a3, new Coordinate(40 + (2), 40 - (-2)));
        ResourceCard a4 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a4, new Coordinate(40 + (3), 40 - (-1)));
        ResourceCard a5 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a5, new Coordinate(40 + (1), 40 - (1)));
        ResourceCard a6 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a6, new Coordinate(40 + (1), 40 - (3)));
        ResourceCard a7 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a7, new Coordinate(40 + (-1), 40 - (7)));


        ResourceCard i1 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i1, new Coordinate(40 + (-4), 40 - (2)));
        ResourceCard i2 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i2, new Coordinate(40 + (1), 40 - (-1)));
        ResourceCard i3 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i3, new Coordinate(40 + (3), 40 - (-3)));
        ResourceCard i4 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i4, new Coordinate(40 + (4), 40 - (-2)));
        ResourceCard i5 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i5, new Coordinate(40 + (4), 40 - (-4)));
        ResourceCard i6 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i6, new Coordinate(40 + (5), 40 - (-5)));
        ResourceCard i7 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i7, new Coordinate(40 + (-6), 40 - (2)));
        ResourceCard i8 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i8, new Coordinate(40, 40 - (6)));
        ResourceCard i9 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i9, new Coordinate(40, 40 - (4)));


        ResourceCard p1 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p1, new Coordinate(40 + (-5), 40 - (5)));
        ResourceCard p2 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p2, new Coordinate(40 + (-4), 40 - (4)));
        ResourceCard p3 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p3, new Coordinate(40 + (-3), 40 - (5)));
        ResourceCard p4 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p4, new Coordinate(40 + (-3), 40 - (3)));
        ResourceCard p5 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p5, new Coordinate(40 + (-1), 40 - (1)));
        ResourceCard p6 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p6, new Coordinate(40 + (-5), 40 - (3)));
        ResourceCard p7 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p7, new Coordinate(40, 40 - (2)));
    }


    @Test
    void testUpdate() {
        int[] expected = {2, 2, 2, 2, 6, 6, 6, 6};

        assertEquals(expected[0], fungiDiagonal.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[1], animalDiagonal.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[2], insectDiagonal.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[3], plantDiagonal.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[4], fungiL.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[5], animalL.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[6], insectL.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[7], plantL.checkPoints(player.getPlayerBoard()));
    }
}
