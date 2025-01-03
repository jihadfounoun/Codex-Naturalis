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

class UpdateInventoryTest {
    private PlayerBoard playerBoard;


    @BeforeEach
    void setUp() throws IOException {
        
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

        frontCorners2[0] = new Corner(false, true);
        frontCorners2[1] = new ObjCorner(TypeObject.MANUSCRIPT);
        frontCorners2[2] = new ResCorner(Resource.ANIMAL);
        frontCorners2[3] = new ResCorner(Resource.PLANT);

        List<Resource> permanentResource = new ArrayList<>();
        permanentResource.add(Resource.INSECT);
        InitialCard initialCard = new InitialCard("81", backCorners, frontCorners, permanentResource);
        playerBoard = new PlayerBoard(81, initialCard);

        ResourceCard playedCard = new ResourceCard("26", frontCorners2, Resource.ANIMAL);
        playerBoard.initialCardSetUp(true);
        playerBoard.addToBoard(playedCard, new Coordinate(41, 41));
    }

    @Test
    void testUpdate() {
        int[] expectedRes = {1, 1, 2, 1, 0, 1, 0};
        assertEquals(expectedRes[0], playerBoard.getInventory().getResources().get(Resource.INSECT));
        assertEquals(expectedRes[1], playerBoard.getInventory().getResources().get(Resource.ANIMAL));
        assertEquals(expectedRes[2], playerBoard.getInventory().getResources().get(Resource.PLANT));
        assertEquals(expectedRes[3], playerBoard.getInventory().getResources().get(Resource.FUNGI));
        assertEquals(expectedRes[4], playerBoard.getInventory().getObjects().get(TypeObject.QUILL));
        assertEquals(expectedRes[5], playerBoard.getInventory().getObjects().get(TypeObject.MANUSCRIPT));
        assertEquals(expectedRes[6], playerBoard.getInventory().getObjects().get(TypeObject.INKWELL));
    }
}

