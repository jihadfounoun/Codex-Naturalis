package it.polimi.ingsw.Model.CommonBoard.Deck;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ObjCorner;
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

import static it.polimi.ingsw.Model.Card.enumerations.Resource.*;
import static it.polimi.ingsw.Model.Card.enumerations.TypeObject.*;

/**
 * <strong>ResourceDeck</strong>
 * <p>
 * ResourceDeck is a class that represents the deck of resource cards in the game. It is an {@link ArrayList} of resource cards that can be shuffled and drawn from.
 * It implements the {@link DeckInterface} interface.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class ResourceDeck implements DeckInterface {

    private static final int n = 0;
    private final ArrayList<ResourceCard> resourceDeck;

    /**
     * Resource deck builder
     * Firstly, it finds the path of the database.
     * Then, it reads the characteristic of a {@link ResourceCard} from the database and creates the deck of resource cards.
     *
     * @throws IOException thrown if the database can't be read
     */
    public ResourceDeck() throws IOException {
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

        resourceDeck = new ArrayList<>();

        //rows
        for (int i = 1; i < 41; i++) {
            //parameters to hand to the card builder
            Resource type = null;
            Corner[] corners = new Corner[4];
            ResourceCard resourceCard;
            for (int j = 0; j < 5; j++) {
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
                            corners[j - 1] = new ResCorner(PLANT);
                        }
                        break;
                    case "Animal":
                        if (j == 0) {
                            type = ANIMAL;
                        } else {
                            corners[j - 1] = new ResCorner(ANIMAL);
                        }
                        break;
                    case "Fungi":
                        if (j == 0) {
                            type = FUNGI;
                        } else {
                            corners[j - 1] = new ResCorner(FUNGI);
                        }
                        break;
                    case "Insect":
                        if (j == 0) {
                            type = INSECT;
                        } else {
                            corners[j - 1] = new ResCorner(INSECT);
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
            String id = String.valueOf((int) sheet.getRow(i).getCell(5).getNumericCellValue());
            resourceCard = new ResourceCard(id, corners, type);
            resourceDeck.add(resourceCard);
        }
        fis.close();
    }

    /**
     * method that returns the type of the first card of the deck
     *
     * @return the type of the first card of the deck
     */
    public Resource seeFirst() throws IllegalStateException {
        if (resourceDeck.isEmpty()) {
            throw new IllegalStateException();
        }
        return resourceDeck.get(0).getType();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(resourceDeck);
    }

    @Override
    public Card getFirstCard() throws IllegalStateException {
        if (resourceDeck.isEmpty()) {
            throw new IllegalStateException();
        }
        return resourceDeck.remove(0);
    }

    @Override
    public Card seeFirstCard() throws IllegalStateException {
        if (resourceDeck.isEmpty()) {
            throw new IllegalStateException();
        }
        return resourceDeck.get(0);
    }

    /**
     * method that returns the resource deck
     *
     * @return the resource deck
     */
    public ArrayList<ResourceCard> getResourceDeck() {
        return resourceDeck;
    }
}

