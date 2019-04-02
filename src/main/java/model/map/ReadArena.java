package model.map;

import java.io.*;

public class ReadArena {
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
            while (true) {
                s = (Square) stream.readObject ( );
                m.setArena (s);
            }
        } catch (EOFException e) {
            setMap(m);
        }
        return m;
    }
}
