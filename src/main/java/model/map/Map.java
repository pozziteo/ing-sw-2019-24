package model.map;

import model.player.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;

/**
 * Map is a Singleton class which contains the description about the map currently
 * used in a game. There can be only one instance of Map in a single game
 */
public class Map {

    private static final String SMALL = "src\\Resources\\maps\\smallmap.json";
    private static final String MEDIUM_1 = "src\\Resources\\maps\\mediummap_1.json";
    private static final String MEDIUM_2 = "src\\Resources\\maps\\mediummap_2.json";
    private static final String LARGE = "src\\Resources\\maps\\largemap.json";

    /**
     * map is the instance of Map currently used in the game
     */
    private static Map map;

    private static String actualMap;

    /**
     * dimension is the number of squares which compose the map
     */
    private int dimension;

    /**
     * arena is a static array which contains the squares of the map
     */
    private Square[] arena;

    /**
     * Constructor is private to avoid the creation of other Map instances,
     * and never used
     */
    private Map() {
    }

    /**
     * Method to get the instance of Map currently used in the game. If it doesn't exist,
     * it creates one and stores it
     * @return an instance of Map
     */
    public static Map getInstance() {
        try {
            if (map == null) {
                String fileName = chooseMap();
                actualMap = fileName;
                map = (new ArenaBuilder()).createMap(fileName);
            }
        } catch (FileNotFoundException exc) {
            System.err.println("Error: Invalid map file selected");
            exc.printStackTrace();
        }
        return map;
    }

    public static String getActualMapName() {
        return actualMap;
    }

    /**
     * Getter to obtain the dimension in squares of the map
     * @return the map's number of squares
     */
    public int getDimension() {
        return this.dimension;
    }

    /**
     * Getter to obtain a single Square of the map
     * @param id is the index of the square in arena attribute
     * @return the square selected
     */
    public Square getSquare(int id) {
        return this.arena[id];
    }

    private static String chooseMap() {
        System.out.println("Choose map:");
        System.out.println("0 - Small Map");
        System.out.println("1 - Medium Map 1");
        System.out.println("2 - Medium Map 2");
        System.out.println("3 - Large Map");
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

    /**
     * Method to determine which players are in the same room and can therefore see each other
     * @param color
     * @return list of players in the same room
     */

   /* public ArrayList<Player> getPlayersInRoom(String color){

    } */
}
