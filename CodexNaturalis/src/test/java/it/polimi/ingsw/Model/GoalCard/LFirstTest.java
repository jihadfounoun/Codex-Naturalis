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

public class LFirstTest {
    Player player;
    LGoal fungiL;
    LGoal animalL;
    LGoal insectL;
    LGoal plantL;

    @BeforeEach
    void setUp() {
        player = new Player("Player3");

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
        player.getPlayerBoard().addToBoard(r1, new Coordinate(40 + (1), 40 - (1)));
        ResourceCard r2 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r2, new Coordinate(40 + (1), 40 - (-1)));
        ResourceCard r3 = new ResourceCard("id", c, FUNGI);
        player.getPlayerBoard().addToBoard(r3, new Coordinate(40 + (-1), 40 - (3)));


        ResourceCard a1 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a1, new Coordinate(40 + (-2), 40 - (2)));
        ResourceCard a2 = new ResourceCard("id", c, ANIMAL);
        player.getPlayerBoard().addToBoard(a2, new Coordinate(40 + (-2), 40));


        ResourceCard i1 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i1, new Coordinate(40 + (-1), 40 - (1)));
        ResourceCard i2 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i2, new Coordinate(40 + (-1), 40 - (-1)));
        ResourceCard i3 = new ResourceCard("id", c, INSECT);
        player.getPlayerBoard().addToBoard(i3, new Coordinate(40 + (1), 40 - (-3)));


        ResourceCard p1 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p1, new Coordinate(40 + (2), 40));
        ResourceCard p2 = new ResourceCard("id", c, PLANT);
        player.getPlayerBoard().addToBoard(p2, new Coordinate(40 + (2), 40 - (-2)));
    }

    @Test
    void testUpdate() {
        int[] expected = {3, 3, 3, 3};

        assertEquals(expected[0], fungiL.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[1], animalL.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[2], insectL.checkPoints(player.getPlayerBoard()));
        assertEquals(expected[3], plantL.checkPoints(player.getPlayerBoard()));

    }
}
