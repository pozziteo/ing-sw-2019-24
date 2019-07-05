package adrenaline.model.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
     * @return an instance of Map class
     * @throws FileNotFoundException if the file's name is invalid
     */
    public Map createMap(String fileName) throws FileNotFoundException {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Square.class, new SquareTypeAdapter());
            Gson gson = builder.create();
            JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName))));
            return gson.fromJson(reader, Map.class);
    }
}
