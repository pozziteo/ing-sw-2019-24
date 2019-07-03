package adrenaline.view.gui.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Class that's used to load all the cards in a game
 */
public class CardLoader {

    private String basePath = "src" + File.separatorChar + "Resources" + File.separatorChar + "images" + File.separatorChar + "cards";

    public CardLoader() {
        super();
    }

    /**
     * Method to load images from cards folder
     * @param path is the name of the card to load
     * @return the image
     */
    public ImageView loadCard(String path) {
        ImageView cardView = null;
        try {
            Image cardImage = new Image(new FileInputStream(basePath + File.separatorChar + path + ".png"));
            cardView = new ImageView(cardImage);
            cardView.setPreserveRatio(true);
            cardView.setFitHeight(220);
//            cardView.setFitWidth(170);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }

        return cardView;
    }
}
