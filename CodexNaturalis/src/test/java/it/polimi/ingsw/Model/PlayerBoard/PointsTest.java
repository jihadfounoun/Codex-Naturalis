package it.polimi.ingsw.Model.PlayerBoard;

import it.polimi.ingsw.Model.Card.Coordinate;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointsTest {
    private Player player;

    @BeforeEach
    void setUp() throws IOException {

        player = new Player("ciao");

        Corner[] backCorners = new Corner[4];
        Corner[] frontCorners = new Corner[4];
        Corner[] frontCorners2 = new Corner[4];

        frontCorners[0] = new ResCorner(Resource.FUNGI);
        frontCorners[1] = new ResCorner(Resource.PLANT);
        frontCorners[2] = new ResCorner(Resource.ANIMAL);
        frontCorners[3] = new ResCorner(Resource.INSECT);

        backCorners[0] = new Corner(true, false);
        backCorners[1] = new ResCorner(Resource.PLANT);
        backCorners[2] = new Corner(true, false);
        backCorners[3] = new ResCorner(Resource.INSECT);

        frontCorners2[0] = new Corner(true, false);
        frontCorners2[1] = new Corner(true, false);
        frontCorners2[2] = new ResCorner(Resource.ANIMAL);
        frontCorners2[3] = new Corner(true, true);

        List<Resource> permanentResource = new ArrayList<>();
        permanentResource.add(Resource.INSECT);
        InitialCard initialCard = new InitialCard("81", backCorners, frontCorners, permanentResource);

        ResourceCard playedCard = new ResourceCard("26", frontCorners2, Resource.ANIMAL);

        player.setUpPlayerItems(81, initialCard, null);
        player.setPlayedSideInitialCard(true);
        player.cardSetUp(playedCard, true, new Coordinate(41, 41));
    }

    @Test
    void testUpdate() {

        int expectedRes = 1;
        assertEquals(expectedRes, player.getScore());
    }
}
