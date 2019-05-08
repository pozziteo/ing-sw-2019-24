package adrenaline.controller;

import adrenaline.data.data_for_client.data_for_view.MessageForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.GameModel;
import adrenaline.network.MainServer;
import adrenaline.misc.TimerCallBack;
import adrenaline.misc.TimerThread;

public class Controller implements TimerCallBack {
    private MainServer server;
    private GameModel gameModel;
    private long timeout;
    private TimerThread timer;

    public Controller(MainServer server) {
        this.server = server;
        this.timeout = (long) 120 * 1000;
        this.timer = new TimerThread (this, timeout);
    }

    public void startController(GameModel model) {
        this.gameModel = model;
        this.timer = new TimerThread (this, timeout);
    }

    public GameModel getGameModel() {
        return this.gameModel;
    }

    public void receiveData(DataForServer data) {
        data.updateServer (server);
    }

    @Override
    public void timerCallBack() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void timerCallBack(String nickname) {
        MessageForClient message = new MessageForClient (server.findClient(nickname), "Time is up. You took too long to make your move.");
        message.sendToView ();
    }
}
