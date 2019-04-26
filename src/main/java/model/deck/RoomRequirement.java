package model.deck;

import model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RoomRequirement implements WeaponEffectRequirement {

    private enum RoomIdentifier {

        SAME("Same"),
        DIFFERENT("Different");

        private String identifier;

        RoomIdentifier(String identifier) {
            this.identifier = identifier;
        }

        private String getIdentifier() {
            return this.identifier;
        }
    }

    private RoomIdentifier roomIdentifier;

    protected RoomRequirement(RoomIdentifier identifier) {
        this.roomIdentifier = identifier;
    }

    @Override
    public List<Player> findTargets(Player attacker) {

        List<Player> inSameRoom = new ArrayList<>();
        List<Player> inDifferentRoom = new ArrayList<>();
        List<Player> players = WeaponEffectRequirement.super.findTargets(attacker);

        for (Player player : players) {
            if (attacker.canSee(player)) {
                if (attacker.isInTheSameRoom(player))
                    inSameRoom.add(player);
                else
                    inDifferentRoom.add(player);
            }
        }

        if (roomIdentifier.getIdentifier().equals("Same"))
            return inSameRoom;
        else
            return inDifferentRoom;
    }
}
