package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;
import java.util.List;

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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setLoadedWeapons(List<WeaponDetails> loadedWeapons) {
        this.loadedWeapons = loadedWeapons;
    }

    public void setPowerUps(List<PowerUpDetails> powerUps) {
        this.powerUps = powerUps;
    }

    public void setDamageTaken(List<String> damageTaken) {
        this.damageTaken = damageTaken;
    }

    public void setReceivedMarks(List<String> receivedMarks) {
        this.receivedMarks = receivedMarks;
    }

    public void setUnloadedWeapons(List<WeaponDetails> unloadedWeapons) {
        this.unloadedWeapons = unloadedWeapons;
    }

    public void setOwnedAmmo(List<String> ownedAmmo) {
        this.ownedAmmo = ownedAmmo;
    }

    public void setPointsForKill(int[] pointsForKill) {
        this.pointsForKill = pointsForKill;
    }

    public void setPointsToken(int pointsToken) {
        this.pointsToken = pointsToken;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getColor() {
        return this.color;
    }

    public int getPosition() {
        return this.position;
    }

    public List<WeaponDetails> getLoadedWeapons() {
        return this.loadedWeapons;
    }

    public List<String> getDamageTaken() {
        return this.damageTaken;
    }

    public List<String> getReceivedMarks() {
        return this.receivedMarks;
    }

    public List<WeaponDetails> getUnloadedWeapons() {
        return this.unloadedWeapons;
    }

    public List<String> getOwnedAmmo() {
        return this.ownedAmmo;
    }

    public int[] getPointsForKill() {
        return this.pointsForKill;
    }

    public int getPointsToken() {return this.pointsToken;}

    public List<PowerUpDetails> getPowerUps() {
        return powerUps;
    }
}
