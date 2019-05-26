package adrenaline.data.data_for_client.responses_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;

public class BoardsResponse extends DataForClient {

    public BoardsResponse() {

    }

    @Override
    public void updateView(CliUserInterface view) {
        view.printAllBoards ();
    }
}
