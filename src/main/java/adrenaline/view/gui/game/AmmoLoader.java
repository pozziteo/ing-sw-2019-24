package adrenaline.view.gui.game;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;

public class AmmoLoader {
    private List<Button> ammos;
    private GridPane ammoPane;

    AmmoLoader(){}

     GridPane getAmmoPane(){

        ammos = new ArrayList<>();
        GridPane pane = new GridPane();

        pane.getRowConstraints().add(new RowConstraints(4));
        pane.getRowConstraints().add(new RowConstraints(4));
        pane.getRowConstraints().add(new RowConstraints(4));

        for(int i=0; i<9; i++){
            Button button = new Button();
            button.setDisable(true);
            button.setStyle("-fx-background-color: yellow; -fx-opacity: 1");
            button.setMaxSize(16,16);
            button.setMinSize(16,16);
            pane.add(button, i%3, i/3);
            ammos.add(button);
        }
        pane.setVgap(15);
        pane.setHgap(5);
        ammoPane = pane;
        return this.ammoPane;
    }

    public List<Button> getBoardAmmos(){return this.ammos;}
}
