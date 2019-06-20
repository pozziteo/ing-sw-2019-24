package adrenaline.view.gui.game;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;


public class MarkLoader {
    private GridPane marks;
    private List<Button> playerMarks;

    MarkLoader(){ }

    public GridPane getMarks(){

        GridPane pane = new GridPane();
        pane.getRowConstraints().add(new RowConstraints(5));
        this.playerMarks = new ArrayList<>();
//top right bottom left -> padding
        for(int i=0; i<8; i++){
            Button button = new Button();
            button.setId("board");
            button.setDisable(true);
            button.setStyle("-fx-background-color: white");
            button.setMaxSize(5,5);
            pane.add(button, i, 0);
            playerMarks.add(button);
        }
        pane.setHgap(5);
        this.marks = pane;
        return marks;
    }

    public List<Button> getPlayerMarks(){
        return this.playerMarks;
    }
}
