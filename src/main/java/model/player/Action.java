package model.player;

public interface Action {

    void executeAction(Player player);

    default String getActionInfo() {
        return "Action information:\n";
    }

}
