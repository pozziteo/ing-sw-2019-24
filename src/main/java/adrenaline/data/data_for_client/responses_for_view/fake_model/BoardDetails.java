package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains information for player boards shown to the client
 */

public class BoardDetails implements Serializable {
    private String nickname;
    private String color;
    private int position;
    private List<WeaponDetails> loadedWeapons;
    private List<PowerUpDetails> powerUps;
    private List<String> damageTaken;
    private List<String> receivedMarks;
    private List<WeaponDetails> unloadedWeapons;
    private List<String> ownedAmmo;
    private int[] pointsForKill;
    private int pointsToken;

    /**
     * Setter method for nickname
     * @param nickname to set
     */

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Setter method for player color
     * @param color to set
     */

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Setter method for player position
     * @param position to set
     */

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Setter method for loaded weapons
     * @param loadedWeapons is the list of loaded weapons
     */

    public void setLoadedWeapons(List<WeaponDetails> loadedWeapons) {
        this.loadedWeapons = loadedWeapons;
    }

    /**
     * Setter method for power ups
     * @param powerUps is the list of power ups
     */

    public void setPowerUps(List<PowerUpDetails> powerUps) {
        this.powerUps = powerUps;
    }

    /**
     * Setter method for damage taken
     * @param damageTaken is the list of player colors which gave damage to this board's owner
     */

    public void setDamageTaken(List<String> damageTaken) {
        this.damageTaken = damageTaken;
    }

    /**
     * Setter method for received marks
     * @param receivedMarks is the list of player colors which gave marks to this board's owner
     */

    public void setReceivedMarks(List<String> receivedMarks) {
        this.receivedMarks = receivedMarks;
    }

    /**
     * Setter method for unloaded weapons
     * @param unloadedWeapons is the list of unloaded weapons
     */

    public void setUnloadedWeapons(List<WeaponDetails> unloadedWeapons) {
        this.unloadedWeapons = unloadedWeapons;
    }

    /**
     * Setter method for owned ammo
     * @param ownedAmmo is the list of owned ammo
     */

    public void setOwnedAmmo(List<String> ownedAmmo) {
        this.ownedAmmo = ownedAmmo;
    }

    /**
     * Setter method for points for kill
     * @param pointsForKill to set
     */

    public void setPointsForKill(int[] pointsForKill) {
        this.pointsForKill = pointsForKill;
    }

    /**
     * Setter method for points tokens
     * @param pointsToken to set
     */

    public void setPointsToken(int pointsToken) {
        this.pointsToken = pointsToken;
    }

    /**
     * Getter method for nickname
     * @return nickname
     */

    public String getNickname() {
        return this.nickname;
    }

    /**
     * Getter method for player color
     * @return color
     */

    public String getColor() {
        return this.color;
    }

    /**
     * Getter method for position
     * @return position
     */

    public int getPosition() {
        return this.position;
    }

    /**
     * Getter method for loaded weapons
     * @return loaded weapons
     */

    public List<WeaponDetails> getLoadedWeapons() {
        return this.loadedWeapons;
    }

    /**
     * Getter method for damage taken
     * @return damage taken
     */

    public List<String> getDamageTaken() {
        return this.damageTaken;
    }

    /**
     * Getter method for received marks
     * @return received marks
     */

    public List<String> getReceivedMarks() {
        return this.receivedMarks;
    }

    /**
     * Getter method for unloaded weapons
     * @return unloaded weapons
     */

    public List<WeaponDetails> getUnloadedWeapons() {
        return this.unloadedWeapons;
    }

    /**
     * Getter method for owned ammo
     * @return owned ammo
     */

    public List<String> getOwnedAmmo() {
        return this.ownedAmmo;
    }

    /**
     * Getter method for points for kill
     * @return points for kill
     */

    public int[] getPointsForKill() {
        return this.pointsForKill;
    }

    /**
     * Getter method for points tokens
     * @return points tokens
     */

    public int getPointsToken() {return this.pointsToken;}

    /**
     * Getter method for power ups
     * @return power ups
     */

    public List<PowerUpDetails> getPowerUps() {
        return powerUps;
    }
}
