package adrenaline.view.gui.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * Class to display the players' figures
 */
public class FiguresLoader {

    private String basePath = "src" + File.separatorChar + "Resources" + File.separatorChar + "images" + File.separatorChar + "players";

    public FiguresLoader() {
        super();
    }

    /**
     * Method to load the player's figure
     * @param color is the color of the player
     * @return the figure
     */
    public ImageView loadFigure(String color) {
        ImageView figureView = null;
        Image figureImage = new Image(getClass().getResourceAsStream("/images/players/" + color + ".png"));
        figureView = new ImageView(figureImage);
        figureView.setPreserveRatio(true);
        figureView.setFitHeight(150);

        return figureView;
    }

    /**
     * Method to load a small figure
     * @param color is the color of the player
     * @return a small figure
     */
    public ImageView loadSmallFigure(String color) {
        ImageView figureView = null;
        Image figureImage = new Image(getClass().getResourceAsStream("/images/players/" + color + ".png"));
        figureView = new ImageView(figureImage);
        figureView.setPreserveRatio(true);
        figureView.setFitHeight(65);

        return figureView;
    }
}
