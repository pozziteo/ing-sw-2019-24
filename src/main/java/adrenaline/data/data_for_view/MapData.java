package adrenaline.data.data_for_view;

import adrenaline.data.DataForClient;
import adrenaline.model.map.Square;
import adrenaline.network.Account;

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
     * Getter to obtain the array of Squares making up an arena.
     * @return arena
     */

    public Square[] getArena() {
        return this.arena;
    }
}
