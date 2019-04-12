package model.map;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.*;

import java.io.IOException;
import java.util.*;

public class SquareTypeAdapter extends TypeAdapter<Square> {

    @Override
    public void write(JsonWriter writer, Square square) throws IOException {

    }

    @Override
    public Square read(JsonReader reader) throws IOException {

        JsonToken token = reader.peek();
        Square square;
        int id = 0;
        String room = null;
        boolean sp = false;
        List<Integer> links = null;

        if (token.equals(JsonToken.BEGIN_OBJECT)) {
            reader.beginObject();
            while (!reader.peek().equals(JsonToken.END_OBJECT)) {
                if (reader.peek().equals(JsonToken.NAME)) {
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
                            links = new ArrayList<Integer>();
                            reader.beginArray();
                            while (reader.hasNext()) {
                                int link = reader.nextInt();
                                links.add(link);
                            }
                            reader.endArray();
                            break;
                        default: reader.skipValue();
                            break;
                    }
                }
            } reader.endObject();
        }
        if (sp)
            square = new SpawnPoint(id, room, links);
        else
            square = new NormalSquare(id, room, links);
        return square;
    }
}