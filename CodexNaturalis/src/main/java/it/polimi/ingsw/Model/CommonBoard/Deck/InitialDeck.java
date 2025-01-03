package it.polimi.ingsw.Model.CommonBoard.Deck;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static it.polimi.ingsw.Model.Card.enumerations.Resource.*;

/**
 * <strong>InitialDeck</strong>
 * <p>
 * InitialDeck is a class that represents the deck of initial cards in the game. It is an {@link ArrayList} of initial cards that can be shuffled and drawn from.
 * It implements the {@link DeckInterface} interface.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class InitialDeck implements DeckInterface {

    private static final int n = 3;
    private final ArrayList<InitialCard> initialDeck;

    /**
     * Initialdeck builder
     * Firstly, it finds the path of the database.
     * Then, it reads the characteristic of a {@link InitialCard} from the database and creates the deck of initial cards.
     *
     * @throws IOException thrown if the database can't be read
     */
    public InitialDeck() throws IOException {
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

        initialDeck = new ArrayList<InitialCard>();
        for (int i = 2; i < 8; i++) {
            ResCorner[] corners = new ResCorner[4];
            Corner[] backCorners = new Corner[4];
            List<Resource> permanentResources = new ArrayList<>();
            InitialCard initialCard;
            for (int j = 0; j < 11; j++) {
                String cellValue = sheet.getRow(i).getCell(j).getStringCellValue();
                switch (cellValue) {
                    case "Empty":
                        backCorners[j] = new Corner(true, false);
                        break;
                    case "Hidden":
                        backCorners[j] = new Corner(false, true);
                        break;
                    case "Plant":
                        if (j < 4) {
                            backCorners[j] = new ResCorner(PLANT);
                        } else if (j < 7) {
                            permanentResources.add(PLANT);
                        } else {
                            corners[j - 7] = new ResCorner(PLANT);
                        }
                        break;
                    case "Animal":
                        if (j < 4) {
                            backCorners[j] = new ResCorner(ANIMAL);
                        } else if (j < 7) {
                            permanentResources.add(ANIMAL);
                        } else {
                            corners[j - 7] = new ResCorner(ANIMAL);
                        }
                        break;
                    case "Fungi":
                        if (j < 4) {
                            backCorners[j] = new ResCorner(FUNGI);
                        } else if (j < 7) {
                            permanentResources.add(FUNGI);
                        } else {
                            corners[j - 7] = new ResCorner(FUNGI);
                        }
                        break;
                    case "Insect":
                        if (j < 4) {
                            backCorners[j] = new ResCorner(INSECT);
                        } else if (j < 7) {
                            permanentResources.add(INSECT);
                        } else {
                            corners[j - 7] = new ResCorner(INSECT);
                        }
                        break;
                }
            }
            String id = String.valueOf((int) sheet.getRow(i).getCell(11).getNumericCellValue());
            initialCard = new InitialCard(id, backCorners, corners, permanentResources);
            initialDeck.add(initialCard);
        }
        fis.close();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(initialDeck);
    }

    @Override
    public Card getFirstCard() throws IllegalStateException {
        if (initialDeck.isEmpty()) {
            throw new IllegalStateException();
        }
        return initialDeck.remove(0);
    }

    @Override
    public Card seeFirstCard() throws IllegalStateException {
        if (initialDeck.isEmpty()) {
            throw new IllegalStateException();
        }
        return initialDeck.get(0);
    }

    /**
     * Method that returns the initial deck
     *
     * @return the initial deck
     */
    public ArrayList<InitialCard> getInitialDeck() {
        return initialDeck;
    }
}
