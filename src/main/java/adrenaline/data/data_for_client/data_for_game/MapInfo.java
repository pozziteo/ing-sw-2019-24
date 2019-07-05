package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

/**
 * This class is sent to the client to inform them of the map chosen for the game.
 */

public class MapInfo extends DataForClient {
    private String mapPath;
    private String simpleName;

    private static final String PATH = "/maps/";
    private static final String SMALL = PATH +  "smallmap.json";
    private static final String MEDIUM_1 = PATH + "mediummap_1.json";
    private static final String MEDIUM_2 = PATH +  "mediummap_2.json";
    private static final String LARGE = PATH +  "largemap.json";

    private static final String IMAGE_PATH = "/images/maps/";
    private static final String IMAGE_SMALL = IMAGE_PATH + "small_map.png";
    private static final String IMAGE_MEDIUM_1 = IMAGE_PATH + "medium_map_1.png";
    private static final String IMAGE_MEDIUM_2 = IMAGE_PATH +  "medium_map_2.png";
    private static final String IMAGE_LARGE = IMAGE_PATH + "large_map.png";

    public MapInfo(String path, String name) {
        this.mapPath = path;
        this.simpleName = name;
    }

    @Override
    public void updateView(CliUserInterface view) {
        view.printMapInit (simpleName);
    }

    @Override
    public void updateView(GUIController view) {
        String map = null;
        if (mapPath.equals(SMALL))
            map = IMAGE_SMALL;
        else if (mapPath.equals(MEDIUM_1))
            map = IMAGE_MEDIUM_1;
        else if (mapPath.equals(MEDIUM_2))
            map = IMAGE_MEDIUM_2;
        else if (mapPath.equals(LARGE))
            map = IMAGE_LARGE;

        view.setMap(map);
        view.initGame();
    }
}
