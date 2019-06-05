package adrenaline.data.data_for_client.responses_for_view.fake_model;

import java.io.Serializable;
import java.util.List;

public class BoardDetails implements Serializable {
    private String nickname;
    private String color;
    private int position;
    private List<WeaponDetails> loadedWeapons;
    private List<String> damageTaken;
    private List<String> receivedMarks;
    private List<WeaponDetails> unloadedWeapons;
    private List<String> ownedAmmo;
    private int[] pointsForKill;

    public BoardDetails(String nickname, String color, int pos, List<WeaponDetails> loadedWeapons, List<String> damage, List<String> marks, List<WeaponDetails> unloadedWeapons, List<String> ammo, int[] points) {
        this.nickname = nickname;
        this.color = color;
        this.position = pos;
        this.loadedWeapons = loadedWeapons;
        this.damageTaken = damage;
        this.receivedMarks = marks;
        this.unloadedWeapons = unloadedWeapons;
        this.ownedAmmo = ammo;
        this.pointsForKill = points;
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
}
