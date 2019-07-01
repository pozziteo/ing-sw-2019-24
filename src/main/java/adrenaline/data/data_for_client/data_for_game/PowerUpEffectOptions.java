package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.SquareDetails;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is sent to ask the client in which way they want to use the chosen power up.
 */

public class PowerUpEffectOptions extends DataForClient {
    private String powerUpName;
    private List<SquareDetails> map;
    private Map<String, List<Integer>> targetPaths;

    public PowerUpEffectOptions(String powerUpName, List<SquareDetails> map, Map<String, List<Integer>> targetPaths) {
        this.powerUpName = powerUpName;
        this.map = map;
        this.targetPaths = targetPaths;
    }

    /**
     * Method to select valid targets from the information in map.
     * @return list of valid targets
     */

    private List<String> getValidNames() {
        List<String> names = new ArrayList<> ();
        for (SquareDetails s : map) {
            for (String name : s.getPlayersOnSquare ()) {
                if (! name.equals(super.getAccount ().getNickName ()))
                    names.add(name);
            }
        }
        return names;
    }

    /**
     * Method to select valid square ids from the information in map.
     * @return list of valid ids
     */

    private List<Integer> getValidSquares() {
        List<Integer> ids = new ArrayList<> ();
        for (SquareDetails s : map)
            ids.add(s.getId ());
        return ids;
    }

    @Override
    public void updateView(CliUserInterface view) {
        if (powerUpName.equals("Newton")) {
            List<String> names = getValidNames();
            view.chooseSquareForTarget (names, map, targetPaths);
        } else {
            view.chooseSquare (getValidSquares (), null);
        }
    }

    @Override
    public void updateView(GUIController view) {
        if (powerUpName.equals("Newton")) {
            List<String> names = getValidNames();
            view.chooseSquareForTarget(names, targetPaths);
        } else {
            view.chooseSquare(getValidSquares());
        }
    }
}
