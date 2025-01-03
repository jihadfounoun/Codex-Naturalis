package it.polimi.ingsw.Model.CommonBoard.Deck;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.Card.GoldCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ObjCorner;
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
import static it.polimi.ingsw.Model.Card.enumerations.TypeObject.*;

/**
 * <strong>GoldDeck</strong>
 * <p>
 * The GoldDeck class represents the deck of gold cards in the game. It is an {@link ArrayList} of gold cards that can be shuffled and drawn from.
 * It implements the {@link DeckInterface} interface.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class GoldDeck implements DeckInterface {
    private static final int n = 1;
    private final ArrayList<GoldCard> goldDeck;

    /**
     * Resource deck builder
     * Firstly, it finds the path of the database.
     * Then, it reads the characteristic of a {@link GoldCard} from the database and creates the deck of gold cards.
     *
     * @throws IOException thrown if the database can't be read
     */
    public GoldDeck() throws IOException {
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

        goldDeck = new ArrayList<>();

        //rows
        for (int i = 1; i < 41; i++) {
            //parameters to hand to the card builder
            Resource type = null;
            Corner[] corners = new Corner[4];
            List<Resource> requirements = new ArrayList<>();
            GoldCard goldCard;
            for (int j = 0; j < 6; j++) {
                String cellValue = sheet.getRow(i).getCell(j).getStringCellValue();
                switch (cellValue) {
                    case "Empty":
                        corners[j - 1] = new Corner(true, false);
                        break;
                    case "Hidden":
                        corners[j - 1] = new Corner(false, true);
                        break;
                    case "Plant":
                        if (j == 0) {
                            type = PLANT;
                        } else {
                            requirements.add(PLANT);
                        }
                        break;
                    case "Animal":
                        if (j == 0) {
                            type = ANIMAL;
                        } else {
                            requirements.add(ANIMAL);
                        }
                        break;
                    case "Fungi":
                        if (j == 0) {
                            type = FUNGI;
                        } else {
                            requirements.add(FUNGI);
                        }
                        break;
                    case "Insect":
                        if (j == 0) {
                            type = INSECT;
                        } else {
                            requirements.add(INSECT);
                        }
                        break;
                    case "Inkwell":
                        corners[j - 1] = new ObjCorner(INKWELL);
                        break;
                    case "Quill":
                        corners[j - 1] = new ObjCorner(QUILL);
                        break;
                    case "Manuscript":
                        corners[j - 1] = new ObjCorner(MANUSCRIPT);
                        break;
                    default:
                        break;
                }
            }
            int k;
            if (i % 10 == 0) {
                k = 5;
            } else {
                k = i % 10 > 3 ? 3 : 2;
            }
            while (k > 0) {
                requirements.add(type);
                k--;
            }
            String id = String.valueOf((int) sheet.getRow(i).getCell(7).getNumericCellValue());
            goldCard = new GoldCard(id, corners, type, requirements);
            goldDeck.add(goldCard);
        }
        fis.close();
    }

    /**
     * method that returns the type of the first card of the deck
     *
     * @return the type of the first card of the deck
     * @throws IllegalStateException if the deck is empty
     */
    public Resource seeFirst() throws IllegalStateException {
        if (goldDeck.isEmpty()) {
            throw new IllegalStateException();
        }
        return goldDeck.get(0).getType();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(goldDeck);
    }

    @Override
    public Card getFirstCard() throws IllegalStateException {
        if (goldDeck.isEmpty()) {
            throw new IllegalStateException();
        }
        return goldDeck.remove(0);
    }

    @Override
    public Card seeFirstCard() throws IllegalStateException {
        if (goldDeck.isEmpty()) {
            throw new IllegalStateException();
        }
        return goldDeck.get(0);
    }

    /**
     * Method that returns the gold deck.
     *
     * @return the gold deck
     */
    public ArrayList<GoldCard> getGoldDeck() {
        return goldDeck;
    }
}
