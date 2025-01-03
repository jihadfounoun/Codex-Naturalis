package it.polimi.ingsw.Model.CommonBoard.Deck;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.GoalCard.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static it.polimi.ingsw.Model.Card.enumerations.Resource.*;
import static it.polimi.ingsw.Model.Card.enumerations.TypeObject.*;

/**
 * <strong>GoalDeck</strong>
 * <p>
 * GoalDeck is a class that represents the deck of goal cards in the game. It is an {@link ArrayList} of goal cards that can be shuffled and drawn from.
 * It implements the {@link DeckInterface} interface.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class GoalDeck implements DeckInterface {
    private static final int n = 2;
    private final ArrayList<GoalCard> goalDeck;

    /**
     * Goaldeck builder
     * Firstly, it finds the path of the database.
     * Then, it reads the characteristic of a {@link GoalCard} from the database and creates the deck of goal cards.
     *
     * @throws IOException thrown if the database can't be read
     */
    public GoalDeck() throws IOException {
        String path = System.getProperty("user.dir");
        path = path.replace(File.separator + "deliverables", "");
        path = path.replace(File.separator + "jar", "");
        if (path.contains("CodexNaturalis")) {
            path += File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "it" + File.separator + "polimi" + File.separator + "ingsw" + File.separator + "Utilities" + File.separator + "Database.xlsx";
        } else {
            path += File.separator + "CodexNaturalis" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "it" + File.separator + "polimi" + File.separator + "ingsw" + File.separator + "Utilities" + File.separator + "Database.xlsx";
        }
        FileInputStream fis = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(n);
        goalDeck = new ArrayList<GoalCard>();

        for (int i = 1; i < 17; i++) {
            GoalCard goalCard = null;
            String cellValue = sheet.getRow(i).getCell(0).getStringCellValue();
            String id = String.valueOf((int) sheet.getRow(i).getCell(5).getNumericCellValue());
            switch (cellValue) {
                case "Animal":
                    if (i % 3 == 0) {
                        goalCard = new ResourceGoal(id, ANIMAL);
                    } else if (i % 3 == 1) {
                        goalCard = new DiagonalGoal(id, ANIMAL, true);
                    } else {
                        goalCard = new LGoal(id, ANIMAL, FUNGI, 1);
                    }
                    break;
                case "Plant":
                    if (i % 3 == 0) {
                        goalCard = new ResourceGoal(id, PLANT);
                    } else if (i % 3 == 1) {
                        goalCard = new DiagonalGoal(id, PLANT, false);
                    } else {
                        goalCard = new LGoal(id, PLANT, INSECT, 3);
                    }
                    break;
                case "Fungi":
                    if (i % 3 == 0) {
                        goalCard = new ResourceGoal(id, FUNGI);
                    } else if (i % 3 == 1) {
                        goalCard = new DiagonalGoal(id, FUNGI, true);
                    } else {
                        goalCard = new LGoal(id, FUNGI, PLANT, 2);
                    }
                    break;
                case "Insect":
                    if (i % 3 == 0) {
                        goalCard = new ResourceGoal(id, INSECT);
                    } else if (i % 3 == 1) {
                        goalCard = new DiagonalGoal(id, INSECT, false);
                    } else {
                        goalCard = new LGoal(id, INSECT, ANIMAL, 0);
                    }
                    break;
                default:
                    if (i == 16) {
                        goalCard = new ObjectGoal(id);
                    } else if (i == 13) {
                        goalCard = new ObjectGoal(id, MANUSCRIPT);
                    } else if (i == 14) {
                        goalCard = new ObjectGoal(id, INKWELL);
                    } else {
                        goalCard = new ObjectGoal(id, QUILL);
                    }
                    break;

            }
            goalDeck.add(goalCard);

        }
        fis.close();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(goalDeck);
    }

    @Override
    public Card getFirstCard() throws IllegalStateException {
        if (goalDeck.isEmpty()) {
            throw new IllegalStateException();
        }
        return goalDeck.remove(0);
    }

    @Override
    public Card seeFirstCard() throws IllegalStateException {
        if (goalDeck.isEmpty()) {
            throw new IllegalStateException();
        }
        return goalDeck.get(0);
    }

    /**
     * Method that returns the goal deck.
     *
     * @return the type of the first card of the deck
     */
    public ArrayList<GoalCard> getGoalDeck() {
        return goalDeck;
    }
}
