package it.polimi.ingsw.Model.PlayerBoard;

import it.polimi.ingsw.Model.Card.Coordinate;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ObjCorner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Card.enumerations.TypeObject;
import it.polimi.ingsw.Model.Player.PlayerBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CannotAddPlayablePosTest {
    private PlayerBoard playerBoard;

    @BeforeEach
    void setUp() throws IOException {

        Corner[] backCorners = new Corner[4];
        Corner[] frontCorners = new Corner[4];
        Corner[] frontCorners2 = new Corner[4];
        Corner[] frontCorners3 = new Corner[4];

        frontCorners[0] = new ResCorner(Resource.FUNGI);
        frontCorners[1] = new ResCorner(Resource.PLANT);
        frontCorners[2] = new ResCorner(Resource.ANIMAL);
        frontCorners[3] = new ResCorner(Resource.INSECT);

        backCorners[0] = new Corner(true, false);
        backCorners[1] = new ResCorner(Resource.PLANT);
        backCorners[2] = new Corner(true, false);
        backCorners[3] = new ResCorner(Resource.INSECT);

        frontCorners2[0] = new Corner(false, true);
        frontCorners2[1] = new ResCorner(Resource.ANIMAL);
        frontCorners2[2] = new ResCorner(Resource.INSECT);
        frontCorners2[3] = new ObjCorner(TypeObject.INKWELL);

        frontCorners3[0] = new ResCorner(Resource.INSECT);
        frontCorners3[1] = new ResCorner(Resource.ANIMAL);
        frontCorners3[2] = new ResCorner(Resource.INSECT);
        frontCorners3[3] = new ObjCorner(TypeObject.INKWELL);

        List<Resource> permanentResource = new ArrayList<>();
        permanentResource.add(Resource.INSECT);
        InitialCard initialCard = new InitialCard("81", backCorners, frontCorners, permanentResource);
        playerBoard = new PlayerBoard(81, initialCard);

        ResourceCard playedCard = new ResourceCard("25", frontCorners2, Resource.ANIMAL);
        ResourceCard playedCard2 = new ResourceCard("14", frontCorners3, Resource.PLANT);

        playerBoard.initialCardSetUp(true);
        playerBoard.addToBoard(playedCard, new Coordinate(41, 39));
        playerBoard.addToBoard(playedCard2, new Coordinate(39, 39));
    }

    @Test
    void testUpdate() {
        
        ArrayList<Coordinate> expectedRes = new ArrayList<>();
        expectedRes.add(new Coordinate(41, 41));
        expectedRes.add(new Coordinate(39, 41));
        expectedRes.add(new Coordinate(42, 38));
        expectedRes.add(new Coordinate(42, 40));
        expectedRes.add(new Coordinate(38, 38));
        expectedRes.add(new Coordinate(38, 40));

        assertEquals(expectedRes, playerBoard.getPlayablePos());
    }
}
