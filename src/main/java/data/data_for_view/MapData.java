package data.data_for_view;

import data.DataForClient;
import model.map.Square;
import network.visitors.Account;

public class MapData extends DataForClient {
    private Square[] arena;

    public MapData(Account account, Square[] arena) {
        super(account);
        this.arena = arena;
    }
}
