package model.map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;

/**
 * ArenaReader is a class which parses a JSON file containing the description
 * about a map and creates an instance of Map
 */
public class ArenaBuilder {

    private static final String SMALL = "src\\Resources\\maps\\smallmap.json";
    private static final String MEDIUM_1 = "src\\Resources\\maps\\mediummap_1.json";
    private static final String MEDIUM_2 = "src\\Resources\\maps\\mediummap_2.json";
    private static final String LARGE = "src\\Resources\\maps\\largemap.json";

    public ArenaBuilder() {

    }

    /**
     * Method to create an instance of Map by reading its description from a
     * JSON file.
     * @return an instance of Map class
     * @throws FileNotFoundException if the file's name is invalid
     */
    public Map createMap() throws FileNotFoundException {
            String fileMap = chooseMap();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(fileMap));
            return gson.fromJson(reader, Map.class);
    }

    private String chooseMap() {
        /*System.out.println("Choose map:");
        System.out.println("0 - Small Map");
        System.out.println("1 - Medium Map 1");
        System.out.println("2 - Medium Map 2");
        System.out.println("3 - Large Map"); */
        //TODO for now map is selected statically, in future may be selected by user
        String filename = SMALL;
        /*switch (selection) {
            case 0: filename = SMALL;
                    break;
            case 1: filename = MEDIUM_1;
                    break;
            case 2: filename = MEDIUM_2;
                    break;
            case 3: filename = LARGE;
                    break;
            default: filename = null;
                    break;
        } */
        System.out.println("Selected map: " + filename);
        return filename;

    }
}
