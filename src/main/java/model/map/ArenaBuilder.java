package model.map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;

/**
 * ArenaReader is a class which parses a JSON file containing the description
 * about a map and creates an instance of Map
 */
public class ArenaBuilder {

    /**
     * Method to create an instance of Map by reading its description from a
     * JSON file.
     * @param fileMap is the file's name where the map is described
     * @return an instance of Map class
     * @throws FileNotFoundException if the file's name is invalid
     */
    public Map createMap(String fileMap) throws FileNotFoundException {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(fileMap));
            return gson.fromJson(reader, Map.class);
    }
}
