package adrenaline.view.gui.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class MapLoader {

    private ImageView map;

    public MapLoader(String path) {
        try {
            Image mapImage = new Image(new FileInputStream(path));
            ImageView mapView = new ImageView(mapImage);
            mapView.setPreserveRatio(true);
            mapView.setFitHeight(800);
            mapView.setFitWidth(800);
            this.map = mapView;
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    public ImageView getMap() {
        return this.map;
    }
}
