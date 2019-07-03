package adrenaline.view.gui.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class TileLoader {

    private String basePath = "src" + File.separatorChar + "Resources" + File.separatorChar + "images" + File.separatorChar + "ammo";

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
        try {
            Image figureImage = new Image(new FileInputStream(basePath + File.separatorChar + tile + ".png"));
            tileView = new ImageView(figureImage);
            tileView.setPreserveRatio(true);
            tileView.setFitHeight(40);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }

        return tileView;
    }
}
