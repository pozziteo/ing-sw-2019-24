package adrenaline.model.player;

import adrenaline.exceptions.NotEnoughAmmoException;
import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.deck.Tile;
import adrenaline.model.deck.Weapon;
import adrenaline.model.map.NormalSquare;
import adrenaline.model.map.SpawnPoint;
import adrenaline.model.map.Square;

import java.util.*;

//TODO add JavaDoc
public class MoveAndGrab implements Action {

    private List<Integer> paths;

    public MoveAndGrab(Player player, boolean frenzy) {

        List<Player> players = player.getGame().getPlayers();
        Player firstPlayer = player.getGame().getFirstPlayer();

        if (!frenzy) {
            if (player.getBoard().getDamageTaken().size() >= 3) {
                paths = Action.findPaths(player, 2);
            } else
                paths = Action.findPaths(player, 1);
        }
        else {
            if (!player.equals(firstPlayer) &&
                    players.indexOf(player) < players.indexOf(firstPlayer)) {
                paths = Action.findPaths(player, 2);
            }
            else
                paths = Action.findPaths(player, 3);
        }
    }

    public List<Integer> getPaths() {
        return this.paths;
    }

    public Square grabObject(Player player, Weapon weapon) throws NotEnoughAmmoException {
        if (weapon != null) {
            ((SpawnPoint) player.getPosition ()).removeWeapon(weapon);
            player.getOwnedWeapons().add(weapon);
            if (player.getBoard ().getOwnedAmmo ().containsAll (weapon.getType ().getGrabbingCost ()))
                player.getBoard ().getOwnedAmmo ().removeAll(weapon.getType ().getGrabbingCost ());
            else
                throw new NotEnoughAmmoException();
        } else
           grabTileContent(player, (NormalSquare) player.getPosition ());

        Action.super.executedAction(player);
        return player.getPosition();
    }

    public Square grabObject(Player player, int squareId, Weapon weapon) throws NotEnoughAmmoException {
        if (paths.contains(squareId)) {
            player.getPosition ().removePlayerFromSquare(player);
            player.setPosition (player.getGame ().getMap ().getSquare (squareId));
            grabObject(player, weapon);
        }
        else {
            player.setPosition(player.getPosition());
        }
        return player.getPosition();
    }

    private void grabTileContent(Player player, NormalSquare position) {
        Ammo ammo;
        Tile tile = position.getPlacedTile();
        if (tile.getFormat ().isPowerUpIsPresent ()) {
            player.getOwnedPowerUps ().add((PowerUp) player.getGame().getPowerUpsDeck ().drawCard ());
        }
        for (int i = 0; i < tile.getTileContent ().size(); i++) {
            ammo = tile.getTileContent ( ).get (i);
            if (player.getBoard ( ).getAmountOfAmmo (ammo) < 3) {
                player.getBoard ( ).getOwnedAmmo ( ).add (ammo);
            }
        }
        position.setPlacedTile(null);
        player.getGame().getTilesDeck ().discardCard(tile);
    }

    @Override
    public String getActionInfo() {
        return Action.super.getActionInfo() + "With this action you can (optionally) move through a single square " +
                " and grab the object contained by the square you are on.";
    }
}
