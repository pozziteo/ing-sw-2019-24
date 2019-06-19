package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.SquareDetails;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PowerUpEffectOptions extends DataForClient {
    private String powerUpName;
    private List<SquareDetails> map;
    private Map<String, List<Integer>> targetPaths;

    public PowerUpEffectOptions(String powerUpName, List<SquareDetails> map, Map<String, List<Integer>> targetPaths) {
        this.powerUpName = powerUpName;
        this.map = map;
        this.targetPaths = targetPaths;
    }

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
            view.chooseSquare (getValidSquares ());
        }
    }

    @Override
    public void updateView(GUIController view) {
        if (powerUpName.equals("Newton")) {
            List<String> names = getValidNames();
            view.chooseSquareForTarget(names, map);
        } else {
            view.chooseSquare();
        }
    }
}
