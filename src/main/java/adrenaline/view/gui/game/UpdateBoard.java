package adrenaline.view.gui.game;

import adrenaline.view.gui.GUIController;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class UpdateBoard {

    private BoardLoader boardLoader;

    private String boardColor;
    private String owner;
    private String blue = "blue";
    private String red = "red";
    private String yellow = "yellow";
    private List<String> life = new ArrayList<>();
    private List<String> marks = new ArrayList<>();
    private List<String> ammo = new ArrayList<>();
    private List<Button> ammoButton;
    private GUIController controller = GUIController.getController();

    public UpdateBoard(String owner){
        this.boardColor = controller.getPlayerColors().get(owner);
        this.owner = owner;
    }

    /**
     * Method to update the players' life
     * @param damage is the number of hits
     * @param attackerColor is the color of the perpetrator
     */
    public void updateLifeBar(int damage, String attackerColor){
        List<Button> lifeBarButton = new BoardLoader(owner).getBoardLifeBar();
        changeButtonList(damage, attackerColor, life, lifeBarButton);
    }

    /**
     * Method to update the players' marks
     * @param amount is the number of marks
     * @param attackerColor is the color of the perpetrator
     */
    public void updateMarks(int amount, String attackerColor){
        List<Button> marksButton = new BoardLoader(owner).getMarks();
        changeButtonList(amount, attackerColor, marks, marksButton);
    }


//    public void addAmmo(int r, int y, int b){
//        List<Button> ammoButton = new BoardLoader(owner).getAmmo();
//        int oldSize = ammo.size();
//        for(int i=0; i<r; i++){
//            if (ammo.size()<9) ammo.add(red);
//        }
//        for(int i=0; i<y; i++){
//            if (ammo.size()<9) ammo.add(yellow);
//        }
//        for(int i=0; i<b; i++){
//            if (ammo.size()<9) ammo.add(blue);
//        }
//        int newSize = ammo.size();
//
//        for(int i=oldSize; i<newSize; i++){
//
//        }
//    }



    /**
     * Method to update the graphic board of a player
     * @param amount is the number of hits
     * @param attackerColor is the color of the perpetrator
     * @param toChange is the list of string to keep track
     * @param toUpdate is the list of button to update
     */
    private void changeButtonList(int amount, String attackerColor, List<String> toChange, List<Button> toUpdate){
        int oldSize = toChange.size();
        for(int i=0; i<amount; i++) toChange.add(attackerColor);
        int newSize = toChange.size();
        for(int i = oldSize; i< newSize; i++){
            if(amount>0){
                toUpdate.get(i).setStyle("-fx-background-color: " + attackerColor);
                amount--;
            }
        }
    }
}
