package adrenaline.data.data_for_client.data_for_game;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.view.cli.CliUserInterface;
import adrenaline.view.gui.GUIController;

import java.io.File;

/**
 * This class is sent to the client to inform them of the map chosen for the game.
 */

public class MapInfo extends DataForClient {
    private String mapPath;
    private String simpleName;

    private static final String PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "maps";
    private static final String SMALL = PATH + File.separatorChar + "smallmap.json";
    private static final String MEDIUM_1 = PATH + File.separatorChar + "mediummap_1.json";
    private static final String MEDIUM_2 = PATH + File.separatorChar + "mediummap_2.json";
    private static final String LARGE = PATH + File.separatorChar + "largemap.json";

    private static final String IMAGE_PATH = "src" + File.separatorChar + "Resources" + File.separatorChar + "images" + File.separatorChar + "maps";
    private static final String IMAGE_SMALL = IMAGE_PATH + File.separatorChar + "small_map.png";
    private static final String IMAGE_MEDIUM_1 = IMAGE_PATH + File.separatorChar + "medium_map_1.png";
    private static final String IMAGE_MEDIUM_2 = IMAGE_PATH + File.separatorChar + "medium_map_2.png";
    private static final String IMAGE_LARGE = IMAGE_PATH + File.separatorChar + "large_map.png";

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
