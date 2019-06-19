package adrenaline.view.gui.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class FiguresLoader {

    private String basePath = "src" + File.separatorChar + "Resources" + File.separatorChar + "images" + File.separatorChar + "players";

    public FiguresLoader() {
        super();
    }

    public ImageView loadFigure(String color) {
        ImageView figureView = null;
        try {
            Image figureImage = new Image(new FileInputStream(basePath + File.separatorChar + color + ".png"));
            figureView = new ImageView(figureImage);
            figureView.setPreserveRatio(true);
            figureView.setFitHeight(150);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }

        return figureView;
    }
}