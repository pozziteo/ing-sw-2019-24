package adrenaline.network;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_client.data_for_view.MapData;
import adrenaline.data.data_for_client.data_for_view.FirstPlayerSetUp;
import adrenaline.model.GameModel;

import java.util.ArrayList;

public class Lobby {
    private ArrayList<Account> players;
    private GameModel game;
    private Controller controller;
    private boolean full;

    public Lobby() {
        this.players = new ArrayList<> ();
        this.full = false;
    }

    public void createGame() {
        if (players.size () > 2 && players.size () < 6) {
            String[] playerNames = new String[players.size ( )];
            int i = 0;
            for (Account a : players) {
                playerNames[i] = a.getNickName ( );
                i++;
            }
            this.game = new GameModel (playerNames);
            this.controller = new Controller(game);
        } else {
            System.out.println ("Lobby is not ready.");
        }
    }

    public Controller getController() {
        return this.controller;
    }

    public GameModel getGameModel() {
        return this.game;
    }

    private boolean checkFull() {
        if (this.players.size () == 5)
            full = true;
        return full;
    }

    public boolean isFull() {
        return checkFull ();
    }

    public void setPlayers(Account a) {
        checkFirst(a);
        this.players.add (a);
    }

    private void checkFirst(Account a) {
        if (players.isEmpty ()) {
            FirstPlayerSetUp data = new FirstPlayerSetUp (true, a);
            data.sendToView ();
        } else {
            FirstPlayerSetUp data = new FirstPlayerSetUp (false, a);
            data.sendToView ();
        }
    }

    public void updateMapData() {
        for (Account a : players) {
            MapData data = this.game.updateMapData (a);
            data.sendToView ();
        }
    }

}
