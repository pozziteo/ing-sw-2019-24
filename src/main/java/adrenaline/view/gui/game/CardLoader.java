package adrenaline.view.gui.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class that's used to load all the cards in a game
 */
public class CardLoader {

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
        Image cardImage = new Image(getClass().getResourceAsStream("/images/cards/" + path + ".png"));
        cardView = new ImageView(cardImage);
        cardView.setPreserveRatio(true);
        cardView.setFitHeight(220);
//            cardView.setFitWidth(170);

        return cardView;
    }
}
