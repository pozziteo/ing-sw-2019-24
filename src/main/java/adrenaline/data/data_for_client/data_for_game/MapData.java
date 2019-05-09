package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.model.map.Square;
import adrenaline.network.Account;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GraphicUserInterface;

/**
 * Class that represents data containing updated map info.
 */

public class MapData extends DataForClient {
    private Square[] arena;

    /**
     * Constructor that calls the one of the superclass and initializes arena.
     * @param account to notify
     * @param arena updated
     */

    public MapData(Account account, Square[] arena) {
        super(account);
        this.arena = arena;
    }

    /**
     * Method to print the array of Squares making up an arena.
     */

    @Override
    public void updateView(CliUserInterface view) {
        view.printMap(arena);
    }

    @Override
    public void updateView(GraphicUserInterface view) {
        //TODO
    }
}
