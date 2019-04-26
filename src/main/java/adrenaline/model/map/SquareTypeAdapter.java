package adrenaline.model.map;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.*;

import java.io.IOException;
import java.util.*;

public class SquareTypeAdapter extends TypeAdapter<Square> {

    @Override
    public void write(JsonWriter writer, Square square) throws IOException {

    }

    //TODO add JavaDoc
    @Override
    public Square read(JsonReader reader) throws IOException {

        Square square;
        int id = 0;
        String room = null;
        boolean sp = false;
        List<Integer> links = null;

        reader.beginObject();
        while (reader.peek().equals(JsonToken.NAME)) {
            switch (reader.nextName()) {
                case "squareId":
                    id = reader.nextInt();
                    break;
                case "room":
                    room = reader.nextString();
                    break;
                case "spawn":
                    sp = reader.nextBoolean();
                    break;
                case "links":
                    links = new ArrayList<>();
                    reader.beginArray();
                    while (reader.hasNext()) {
                        int link = reader.nextInt();
                        links.add(link);
                    }
                    reader.endArray();
                    break;
                default:
                    reader.skipValue();
            }
        }
        if (sp)
            square = new SpawnPoint(id, room, links);
        else
            square = new NormalSquare(id, room, links);
        reader.endObject();
        return square;
    }
}