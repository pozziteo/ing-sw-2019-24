package adrenaline.data.data_for_client.responses_for_view;

import java.io.Serializable;
import java.util.List;

public class BoardDetails implements Serializable {
    private String nickname;
    private List<String> damageTaken;
    private List<String> receivedMarks;
    private List<WeaponDetails> unloadedWeapons;
    private List<String> ownedAmmo;
    private int[] pointsForKill;

    public BoardDetails(String nickname, List<String> damage, List<String> marks, List<WeaponDetails> weapons, List<String> ammo, int[] points) {
        this.nickname = nickname;
        this.damageTaken = damage;
        this.receivedMarks = marks;
        this.unloadedWeapons = weapons;
        this.ownedAmmo = ammo;
        this.pointsForKill = points;
    }

    public String getNickname() {
        return this.nickname;
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
