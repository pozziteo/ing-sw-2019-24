package model.map;

import java.io.*;

public class ArenaReader {
    private Map map;

    public Map getMap() {
        return this.map;
    }

    public void setMap(Map m) {
        this.map = m;
    }

    public Map createArena() throws Exception {
        Map m = new Map();
        Square s = null;
        FileInputStream file = new FileInputStream(new File("firstarena.ser"));
        try (ObjectInputStream stream = new ObjectInputStream (file)) {
            boolean value = true;
            while (value) {
                s = (Square) stream.readObject ( );
                m.setArena (s);
                if (s == null) {
                    value = false;
                }
            }
        } catch (EOFException e) {
            setMap(m);
        }
        return m;
    }
}
