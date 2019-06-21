package adrenaline.view.gui.game;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;

public class MaxPointPlayer {
    private List<Button> maxPoints;
    private GridPane points;

    MaxPointPlayer(){}

    GridPane getPointsPane(){

        maxPoints = new ArrayList<>();
        GridPane pane = new GridPane();
        pane.getRowConstraints().add(new RowConstraints(5));

        for(int i=0; i<6; i++){
            Button button = new Button();
            button.setDisable(true);
            button.setId("board");
            button.setStyle("-fx-background-color: white");
            pane.add(button, i, 0);
            maxPoints.add(button);
        }
        pane.setHgap(8);
        points = pane;
        return this.points;
    }

    public List<Button> getMaxPoints(){return this.maxPoints;}
}
