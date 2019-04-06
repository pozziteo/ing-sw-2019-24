package model.map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;

/**
 * ArenaReader is a class which parses a JSON file containing the description
 * about a map and creates an instance of Map
 */
public class ArenaBuilder {

    private static final String SMALL_MAP = "smallmap.json";

    public Map createMap(String fileMap) throws FileNotFoundException {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(fileMap));
            return gson.fromJson(reader, Map.class);
    }
}
