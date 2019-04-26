package adrenaline.data.data_for_view;

import adrenaline.data.DataForClient;
import adrenaline.model.player.Player;
import adrenaline.network.Account;

/**
 * Class that represents data containing updated player info.
 */

public class PlayerData extends DataForClient {
    private Player player;

    /**
     * Constructor that calls the one of the superclass and initializes player.
     * @param account to notify
     * @param player updated
     */

    public PlayerData(Account account, Player player) {
        super(account);
        this.player = player;
    }

    /**
     * Getter to obtain updated player info.
     * @return player
     */

    public Player getPlayer() {
        return this.player;
    }
}
