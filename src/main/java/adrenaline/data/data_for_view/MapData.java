package adrenaline.data.data_for_view;

import adrenaline.data.DataForClient;
import adrenaline.model.map.Square;
import adrenaline.network.Account;

//TODO javadoc

public class MapData extends DataForClient {
    private Square[] arena;

    public MapData(Account account, Square[] arena) {
        super(account);
        this.arena = arena;
    }
}
