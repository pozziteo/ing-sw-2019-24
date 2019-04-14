package model.player;

public interface Action {

    default void executedAction(Player player) {
        Action[] actions = player.getPerformedActions();
        for (int i=0; i < actions.length; i++) {
            if (actions[i] != null) {
                actions[i] = this;
                break;
            }
        }
    }


    default String getActionInfo() {
        return "Action information:\n";
    }

}
