package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.SquareDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.ArrayList;
import java.util.List;

public class PowerUpEffectOptions extends DataForClient {
    private String powerUpName;
    private List<SquareDetails> map;

    public PowerUpEffectOptions(String powerUpName, List<SquareDetails> map) {
        this.powerUpName = powerUpName;
        this.map = map;
    }

    @Override
    public void updateView(CliUserInterface view) {
        if (powerUpName.equals("Newton")) {
            List<String> names = new ArrayList<> ();
            for (SquareDetails s : map) {
                for (String name : s.getPlayersOnSquare ()) {
                    if (! name.equals(super.getAccount ().getNickName ()))
                        names.add(name);
                }
            }
            view.chooseSquareForTarget (names, map);
        } else {
            view.chooseSquare ();
        }
    }
}
