package adrenaline.data.data_for_client.responses_for_view;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.BoardDetails;
import adrenaline.view.cli.CliUserInterface;

import java.util.List;

public class BoardsResponse extends DataForClient {
    private List<BoardDetails> boards;

    public BoardsResponse(List<BoardDetails> boards) {
        this.boards = boards;
    }

    @Override
    public void updateView(CliUserInterface view) {
        if (boards.size() == 1)
            view.printBoard (boards.get(0));
        else
            view.printAllBoards (boards);
    }
}
