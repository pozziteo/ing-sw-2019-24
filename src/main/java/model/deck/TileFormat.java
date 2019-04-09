package model.deck;

import java.util.*;

/**
 * TileFormat enumerates all the format of tile available in the game
 */
public enum TileFormat {

    TILE_FORMAT_1(false, Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_2(false, Ammo.YELLOW_AMMO, Ammo.RED_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_3(false, Ammo.RED_AMMO, Ammo.BLUE_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_4(false, Ammo.RED_AMMO, Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO),
    TILE_FORMAT_5(false, Ammo.BLUE_AMMO, Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO),
    TILE_FORMAT_6(false, Ammo.BLUE_AMMO, Ammo.RED_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_7(false, Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_8(false, Ammo.YELLOW_AMMO, Ammo.RED_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_9(false, Ammo.RED_AMMO, Ammo.BLUE_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_10(false, Ammo.RED_AMMO, Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO),
    TILE_FORMAT_11(false, Ammo.BLUE_AMMO, Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO),
    TILE_FORMAT_12(false, Ammo.BLUE_AMMO, Ammo.RED_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_13(false, Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_14(false, Ammo.YELLOW_AMMO, Ammo.RED_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_15(false, Ammo.RED_AMMO, Ammo.BLUE_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_16(false, Ammo.RED_AMMO, Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO),
    TILE_FORMAT_17(false, Ammo.BLUE_AMMO, Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO),
    TILE_FORMAT_18(false, Ammo.BLUE_AMMO, Ammo.RED_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_19(true, Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO),
    TILE_FORMAT_20(true, Ammo.RED_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_21(true, Ammo.BLUE_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_22(true, Ammo.YELLOW_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_23(true, Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_24(true, Ammo.RED_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_25(true, Ammo.YELLOW_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_26(true, Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_27(true, Ammo.RED_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_28(true, Ammo.YELLOW_AMMO, Ammo.YELLOW_AMMO),
    TILE_FORMAT_29(true, Ammo.RED_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_30(true, Ammo.BLUE_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_31(true, Ammo.YELLOW_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_32(true, Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_33(true, Ammo.RED_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_34(true, Ammo.YELLOW_AMMO, Ammo.RED_AMMO),
    TILE_FORMAT_35(true, Ammo.YELLOW_AMMO, Ammo.BLUE_AMMO),
    TILE_FORMAT_36(true, Ammo.RED_AMMO, Ammo.BLUE_AMMO);

    /**
     * description describes the content of the tile
     */
    private String description;

    /**
     * powerUpPresent states if a PowerUp is inside the tile
     */
    private boolean powerUpPresent;

    /**
     * ammo lists the ammos present in the tile
     */
    private ArrayList<Ammo> ammo;

    /**
     * Constructor which initialize the attributes of the tile
     * @param gotPUp states if a PowerUp is present on the tile
     * @param ammos lists the ammos in the tile
     */
    TileFormat(boolean gotPUp, Ammo... ammos) {
        this.powerUpPresent = gotPUp;
        this.ammo = new ArrayList<>(Arrays.asList(ammos));
        if (powerUpPresent)
            this.description = "PowerUp, ";
        for (Ammo a : ammos)
            this.description += a.name() + ", ";
    }

    /**
     * Getter to obtain the content of a tile
     * @return the tile's content description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter to establish if a PowerUp is in the tile
     * @return a boolean which states if a PowerUp is present
     */
    public boolean isPowerUpIsPresent() {
        return powerUpPresent;
    }

    /**
     * Getter to obtain the list of ammos in the tile
     * @return the list of ammos in the tile
     */
    public ArrayList<Ammo> getAmmo() {
        return this.ammo;
    }
}
