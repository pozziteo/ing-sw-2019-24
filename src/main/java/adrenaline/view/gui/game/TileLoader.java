package adrenaline.view.gui.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class that loads the tiles requested
 */
public class TileLoader {

    public TileLoader() {
        super();
    }

    /**
     * Method to load a tile image
     * @param tile is the name of the image
     * @return an ImageView of the tile
     */
    public ImageView loadTile(String tile) {
        ImageView tileView = null;
        Image figureImage = new Image(getClass().getResourceAsStream("/images/ammo/"+ tile + ".png"));
        tileView = new ImageView(figureImage);
        tileView.setPreserveRatio(true);
        tileView.setFitHeight(40);

        return tileView;
    }
}
