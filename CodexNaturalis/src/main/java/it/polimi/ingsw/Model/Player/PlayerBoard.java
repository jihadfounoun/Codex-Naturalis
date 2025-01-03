package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Card.Coordinate;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.PlayableCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ObjCorner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Card.enumerations.TypeObject;
import it.polimi.ingsw.Model.GoalCard.GoalCard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
/**
 * This class contains the objects that are the personal boards of each player.
 * It has been implemented by using a matrix of PlayableCard (see the doc of PlayableCard class
 * for further information about it) in order to manage the event of playing a card quite easily associating the coordinates of the played cards with the index of the matrix.
 * Other important attributes are the inventory, which contains the resources gained by the player during the game, the list of the available positions on the board where the player
 * can play a card, the list of the played cards and the secret goal of the player.
 *
 *
 */


public class PlayerBoard {

    private final PlayableCard[][] board;
    private final int boardSize;
    private final InitialCard initialCard;
    private final Inventory inventory;
    private final ArrayList<Coordinate> playablePos;
    private final Map<Resource, ArrayList<ResourceCard>> playedCards;
    private GoalCard secretGoal;
    /**
     * Constructs a new PlayerBoard with the specified size of the board and the initial card chosen by the player.
     *
     * @param size        the size of the board
     * @param initialCard chosen initial card
     */

    public PlayerBoard(int size, InitialCard initialCard) {
        this.secretGoal = null;
        this.initialCard = initialCard;
        boardSize = size;
        board = new PlayableCard[boardSize][boardSize];
        inventory = new Inventory();
        playablePos = new ArrayList<Coordinate>();
        playedCards = new HashMap<Resource, ArrayList<ResourceCard>>();
        for (Resource res : Resource.values()) {
            playedCards.put(res, new ArrayList<ResourceCard>());
        }
    }
    /**
     * this method is used to initialise the goal card chosen by the player in the dedicated phase.
     *
     * @param secretGoal chosen secret goal
     */

    public void goalCardSetUp(GoalCard secretGoal) {
        this.secretGoal = secretGoal;
    }
    /**
     * this method is used to initialise the initial card.
     * it needs to be placed because it represents the first card that each player has to play at the
     * beginning of the game, so he unlocks the first available positions and can start the game.
     *
     * @param playedSide 1 if initialCard is played on the front
     */

    public void initialCardSetUp(boolean playedSide) {
        if (!playedSide)
            initialCard.useBack();
        inventory.updateInv(initialCard);

        placeCard(initialCard, new Coordinate(boardSize / 2, boardSize / 2));

        updatePlayablePos(initialCard);

    }
    /**
     * the addToBoard function calls some other functions of this class and completes the action of playing a card.
     * First it adds the card passed as argument to the list of played cards, then updates the inventory, places the card
     * on the matrix in the position specified by the coordinates passed as argument and updates the playable positions,
     * removing some of them in some cases, too. At the end it sets the corner used to play the card hidden and updates
     * the scored points.
     *
     * @param card the card that the player wants to play
     * @param c    the coordinates where the player wants to play the card
     */

    public int addToBoard(ResourceCard card, Coordinate c) {

        int gainedPoints = 0;
        int count=0;

        addPlayedCard(card);


        count=inventory.updateInv(card);

        if(card.getId().equals("0")){
            System.err.println(inventory);
        }

        placeCard(card, c);


        removePlayablePosIfPresent(c);


        Corner currentMatchingCorner = null;

        int overlappingCorners = 0;

        for (int i = 0; i < 4; i++) {
            currentMatchingCorner = matchingCorner(c, i);
            if (currentMatchingCorner != null) {

                overlappingCorners++;

                if (!currentMatchingCorner.isEmpty()) {

                    if (currentMatchingCorner.getClass().equals(ResCorner.class)) {
                        subResource(((ResCorner) currentMatchingCorner).getType());

                    } else if (currentMatchingCorner.getClass().equals(ObjCorner.class))
                        subObject(((ObjCorner) currentMatchingCorner).getType());

                }
                currentMatchingCorner.setHidden();
            }
        }


        if (card.isFront()) {
            int cardPoints = card.computePoints();

            if (card.getClass().equals(ResourceCard.class))
                gainedPoints = cardPoints;
            else {
                switch (cardPoints) {
                    case 1:
                        gainedPoints = count * cardPoints;
                        break;
                    case 2:
                        gainedPoints = overlappingCorners * cardPoints;
                        break;
                    case 3:
                    case 5:
                        gainedPoints = cardPoints;
                        break;
                }
            }
        }

        updatePlayablePos(card);
        System.out.println("Gained points: " + gainedPoints + "inventory" + inventory);
        return gainedPoints;
    }

    /**
     * the updatePlayablePos function refreshes the list of the playable positions, recalculating them after a new card
     * has been played.
     * It makes an iteration upon the corners of the card: if a corner is hidden it doesn't add the correspondent position,
     * and if that position was added before it does a removal, else checks if that position is valid, in other words
     * if there is no corner of another card that makes it illegal, and if yes adds it to the list.
     *
     * @param card the card has just been played
     */
    public void updatePlayablePos(PlayableCard card) {
        Corner[] corners = card.getPlayableCorners();

        Coordinate tmp = null;
        for (int i = 0; i < 4; i++) {

            tmp = matchingCoordinate(card.getCoordinate(), i);
            if (tmp != null) {
                if (!corners[i].isHidden()) {
                    if (validPosition(tmp))
                        addPlayablePosIfNotPresent(tmp);
                } else
                    removePlayablePosIfPresent(tmp);

            }
        }
    }
    /**
     * the matchingCoordinate function associates the corners of a card with the relative coordinates. More precisely,
     * a card is played at specific coordinates; then in order to add the playable positions, it is needed to link each
     * corner of the corners array to some adjacent coordinates. As convention, the first corner of the array is associated
     * with the Nord-west coordinate ((0,0) of the matrix is at the top-left), and proceeding in clock-wise order.
     *
     * @param c            coordinates of the reference card
     * @param cornerNumber index of the corner
     * @return the correspondent coordinates, or null if cornerNumber is greater than 3 (out of bound)
     */


    public Coordinate matchingCoordinate(Coordinate c, int cornerNumber) {
        int x, y;
        x = y = 0;
        switch (cornerNumber) {
            case 0:
                x = c.getX() - 1;
                y = c.getY() - 1;
                break;
            case 1:
                x = c.getX() + 1;
                y = c.getY() - 1;
                break;
            case 2:
                x = c.getX() + 1;
                y = c.getY() + 1;
                break;
            case 3:
                x = c.getX() - 1;
                y = c.getY() + 1;
                break;
        }
        if (x >= 0 && y >= 0 && x < boardSize && y < boardSize)
            return new Coordinate(x, y);
        return null;
    }
    /**
     * this function returns the corner linked to a card placed at specific coordinates.
     * for example, if a card is played at (40,40) and the argument cornerNumber is 0, we know that 0 is associated with
     * the Nord-West coordinates, so the linked corner to that is the second corner of the adjacent card, if present.
     * if there is no card played at Nord-West of the reference card, it returns null.
     *
     * @param c            coordinates of the reference card
     * @param cornerNumber the corner of the reference card we want to know the returned corner is linked with
     * @return corner linked
     */

    public Corner matchingCorner(Coordinate c, int cornerNumber) {
        Coordinate tmp = matchingCoordinate(c, cornerNumber);
        if (tmp != null) {
            PlayableCard card = cardInPosition(tmp);
            if (card != null)
                return card.getPlayableCorners()[(cornerNumber + 2) % 4];
        }
        return null;
    }
    /**
     * the validPosition function allows to determinate if a position could be added to the list of playable ones.
     * it checks if there are some hidden corners that make that position illegal.
     *
     * @param cor the coordinates of the position we want to validate
     * @return 1 if is valid, 0 otherwise
     */

    private boolean validPosition(Coordinate cor) {
        if (cardInPosition(cor) != null)
            return false;

        for (int i = 0; i < 4; i++) {
            Coordinate tmp = matchingCoordinate(cor, i);
            if (tmp != null && cardInPosition(tmp) != null) {
                switch (i) {
                    case 0:
                        if (cardInPosition(tmp).getPlayableCorners()[2].isHidden())
                            return false;
                        break;
                    case 1:
                        if (cardInPosition(tmp).getPlayableCorners()[3].isHidden())
                            return false;
                        break;
                    case 2:
                        if (cardInPosition(tmp).getPlayableCorners()[0].isHidden())
                            return false;
                        break;
                    case 3:
                        if (cardInPosition(tmp).getPlayableCorners()[1].isHidden())
                            return false;
                        break;
                }
            }

        }
        return true;
    }

    /**
     *
     *
     * @param c coordinates
     * @return the card placed at the coordinates passed as arguments
     */

    public PlayableCard cardInPosition(Coordinate c) {
        return board[c.getY()][c.getX()];
    }
    /**
     *
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return the card placed at the coordinates passed as arguments
     */

    public PlayableCard cardInPosition(int x, int y) {
        return board[y][x];
    }
    /**
     * placeCard function physically puts a card in a specific position on the board.
     *
     * @param card the card we want to place
     * @param c    the position in coordinates
     */

    private void placeCard(PlayableCard card, Coordinate c) {
        board[c.getY()][c.getX()] = card;
        card.setCoordinate(c);
    }
    /**
     * addPlayedCard adds a card to the list of playedCards
     *
     * @param card the card to add
     */

    public void addPlayedCard(ResourceCard card) {
        playedCards.get(card.getType()).add(card);
    }

    /**
     * subtract of one unit the specified type of resource in the inventory, if present
     * @param res the type of resources
     */

    public void subResource(Resource res) {
        inventory.subResource(res);
    }

    /**
     * subtract of one unit the specified type of object in the inventory, if present
     *
     * @param obj
     */

    public void subObject(TypeObject obj) {
        inventory.subObject(obj);
    }

    /**
     * removes a position specified by coordinates, if present
     * @param c coordinates of position
     */


    public void removePlayablePosIfPresent(Coordinate c) {
        playablePos.remove(c);
    }

    /**
     * adds a playable position
     * @param c the coordinates of the position
     */

    public void addPlayablePosIfNotPresent(Coordinate c) {
        if (!playablePos.contains(c))
            playablePos.add(c);
    }

    /**
     *
     * @return the objects of the inventory
     */


    public Map<TypeObject, Integer> getObjects() {
        return inventory.getObjects();
    }

    /**
     *
     * @return the resources of the inventory
     */

    public Map<Resource, Integer> getResources() {
        return inventory.getResources();
    }

    /**
     *
     * @return the playable positions
     */

    public ArrayList<Coordinate> getPlayablePos() {
        return this.playablePos;
    }

    /**
     *
     * @return the secret goal
     */

    public GoalCard getSecretGoal() {
        return secretGoal;
    }

    /**
     *
     * @return the board
     */


    public PlayableCard[][] getBoard() {
        return board;
    }

    /**
     * @param res the resource type
     * @return how many resources of a specific type there are in the inventory.
     */


    public Integer getResourceNumber(Resource res) {
        return inventory.getResource(res);
    }

    /**
     * return the playedCards, sorted for each Resource
     * @return the playedCards, sorted for each Resource
     */

    public Map<Resource, ArrayList<ResourceCard>> getPlayedCards() {
        Comparator<ResourceCard> comp = new CoordinateComparator();
        for (Resource res : Resource.values())
            playedCards.get(res).sort(comp);
        return playedCards;
    }

    /**
     *return the initial card
     * @return the initial card
     */

    public InitialCard getInitialCard() {
        return initialCard;
    }

    @Override
    public String toString() {
        String boardString = "";
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] != null)
                    boardString += "- " + board[i][j].getCoordinate() + "\n" + board[i][j].toStringToPlay() + "\n";

            }
        }
        return "Board : \n" + boardString + "- Inventory : " + inventory + "- Playable Coordinates : " + playablePos + "\n";
    }

    /**
     *return the inventory
     * @return the inventory
     */


    public Inventory getInventory() {
        return inventory;
    }
}
