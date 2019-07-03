package adrenaline.model.deck;

import adrenaline.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class RoomRequirement implements WeaponEffectRequirement {

    private enum RoomIdentifier {

        SAME("same"),
        DIFFERENT("different");

        private String identifier;

        RoomIdentifier(String identifier) {
            this.identifier = identifier;
        }

        private String getIdentifier() {
            return this.identifier;
        }
    }

    private RoomIdentifier roomIdentifier;

    protected RoomRequirement(String identifier) {
        if (identifier.equals("same"))
            this.roomIdentifier = RoomIdentifier.SAME;
        else
            this.roomIdentifier = RoomIdentifier.DIFFERENT;
    }

    /**
     * Method to find the available targets
     * @param attacker is the attacker
     * @return a list of available targets
     */
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

        if (roomIdentifier.getIdentifier().equals("same"))
            return inSameRoom;
        else
            return inDifferentRoom;
    }
}
