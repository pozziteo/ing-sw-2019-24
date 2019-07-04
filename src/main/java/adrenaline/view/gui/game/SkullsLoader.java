//package adrenaline.view.gui.game;
//
//import adrenaline.utils.ConfigFileReader;
//import javafx.scene.control.Button;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.RowConstraints;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Class the shows the skulls on the board
// */
//public class SkullsLoader {
//
//    private List<Button> skullsList;
//    private List<Button> overkill;
//    private int skulls = ConfigFileReader.readConfigFile("skulls");
//    SkullsLoader(){    }
//
//    /**
//     * Method to create a grid for the skulls slot on the board
//     * @return a grid of skulls
//     */
//     GridPane getSkullsPane(){
//        GridPane pane = new GridPane();
//        pane.getRowConstraints().add(new RowConstraints(10));
//        pane.getRowConstraints().add(new RowConstraints(10));
//        this.skullsList = new ArrayList<>();
//        this.overkill = new ArrayList<>();
//        for(int i=0; i<8; i++){
//            Button button = new Button();
//            button.setDisable(true);
//            if(i<(8-skulls))
//                button.setStyle("-fx-background-color: trasparent; -fx-opacity: 1");
//            else
//                button.setStyle("-fx-background-color: red; -fx-opacity: 1");
//            skullsList.add(button);
//            pane.add(button, i, 0);
//        }
//        for(int i=0; i<8; i++){
//            Button button = new Button();
//            button.setId("board");
//            button.setDisable(true);
//            button.setStyle("-fx-background-color: transparent");
//            overkill.add(button);
//            pane.add(button, i, 1);
//        }
//        pane.setHgap(18);
//        pane.setVgap(20);
//        return pane;
//    }
//
//    /**
//     * Method to remove a skull. add the color of the killer on the death track
//     */
//    public void removeSkull(int totalDeaths){
//        getSkullsList().get(totalDeaths+(8-ConfigFileReader.readConfigFile("skulls"))).setStyle("-fx-background-color: transparent;");
//    }
//
//
//    /**
//     * Getter method
//     * @return the list of players' color on the death track
//     */
//     List<Button> getSkullsList(){return this.skullsList;}
//
//    /**
//     * Getter method
//     * @return the list of player's color one the overkill track, under the death track
//     */
//     List<Button> getOverkill(){return this.overkill;}
//}
