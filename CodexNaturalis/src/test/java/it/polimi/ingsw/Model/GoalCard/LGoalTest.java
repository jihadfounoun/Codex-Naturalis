package it.polimi.ingsw.Model.GoalCard;

import it.polimi.ingsw.Model.Card.Coordinate;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ObjCorner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Card.enumerations.TypeObject;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LGoalTest {
    private LGoal lGoal;
    private Player player;
    private ResourceCard played1;
    private ResourceCard played2;
    private ResourceCard played3;
    private InitialCard initialCard;
    private Corner[] backInitialCorners;
    private Corner[] frontInitialCorner;
    private Corner[] frontCorners;
    private int expected;

    @BeforeEach
    void setUp() throws IOException {
        lGoal = new LGoal("93", Resource.ANIMAL, Resource.FUNGI, 1);
        player = new Player("ciao");
        backInitialCorners = new Corner[4];
        frontCorners = new Corner[4];
        frontInitialCorner = new Corner[4];
        List<Resource> permanent = new ArrayList<>();
        permanent.add(Resource.INSECT);
        backInitialCorners[0] = new Corner(true, false);
        backInitialCorners[1] = new ResCorner(Resource.PLANT);
        backInitialCorners[2] = new Corner(true, false);
        backInitialCorners[3] = new ResCorner(Resource.INSECT);
        frontInitialCorner[0] = new ResCorner(Resource.FUNGI);
        frontInitialCorner[1] = new ResCorner(Resource.PLANT);
        frontInitialCorner[2] = new ResCorner(Resource.ANIMAL);
        frontInitialCorner[3] = new ResCorner(Resource.INSECT);
        frontCorners[0] = new ResCorner(Resource.PLANT);
        frontCorners[1] = new ResCorner(Resource.ANIMAL);
        frontCorners[2] = new ObjCorner(TypeObject.MANUSCRIPT);
        frontCorners[3] = new Corner(false, true);
        initialCard = new InitialCard("81", backInitialCorners, frontInitialCorner, permanent);
        played1 = new ResourceCard("26", frontCorners, Resource.ANIMAL);
        played2 = new ResourceCard("26", frontCorners, Resource.ANIMAL);
        frontCorners[0] = new ObjCorner(TypeObject.INKWELL);
        frontCorners[1] = new ResCorner(Resource.FUNGI);
        frontCorners[2] = new ResCorner(Resource.ANIMAL);
        frontCorners[3] = new Corner(false, true);
        played3 = new ResourceCard("6", frontCorners, Resource.FUNGI);
        expected = 3;
    }

    @Test
    void testUpdate() {
        player.setUpPlayerItems(81, initialCard, null);
        player.setPlayedSideInitialCard(true);
        player.cardSetUp(played1, false, new Coordinate(41, 41));
        player.cardSetUp(played2, false, new Coordinate(41, 39));
        player.cardSetUp(played3, false, new Coordinate(42, 38));
        assertEquals(expected, lGoal.checkPoints(player.getPlayerBoard()));
    }
}
