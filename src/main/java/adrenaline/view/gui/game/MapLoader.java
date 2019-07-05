package adrenaline.view.gui.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class that loads the selected map
 */
public class MapLoader {

    private ImageView map;

    /**
     * Method to load the game's map
     * @param path is the directory of the map file
     */
    public MapLoader(String path) {
        Image mapImage = new Image(getClass().getResourceAsStream(path));
        ImageView mapView = new ImageView(mapImage);
        mapView.setPreserveRatio(true);
        mapView.setFitHeight(800);
        mapView.setFitWidth(800);
        this.map = mapView;
    }

    /**
     * Getter Method
     * @return the map image
     */
    public ImageView loadMap() {
        return this.map;
    }
}
