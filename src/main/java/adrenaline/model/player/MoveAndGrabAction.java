package adrenaline.model.player;

import adrenaline.exceptions.MustDiscardWeaponException;
import adrenaline.exceptions.NotEnoughAmmoException;
import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.deck.Tile;
import adrenaline.model.deck.Weapon;
import adrenaline.model.map.NormalSquare;
import adrenaline.model.map.SpawnPoint;
import adrenaline.model.map.Square;

import java.util.*;

/**
 * Implementation of Action interface, it contains the Move&Grab Action methods
 */
public class MoveAndGrabAction implements Action {

    private List<Integer> paths;

    public MoveAndGrabAction(Player player, boolean frenzy) {

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

    /**
     * Getter method
     * @return the available paths
     */
    public List<Integer> getPaths() {
        return this.paths;
    }

    /**
     * Method to grab an object from the square
     * @param player is the player
     * @param weapon is the weapon
     * @return the new position of the player
     * @throws NotEnoughAmmoException if the player doesn't have enough ammo
     * @throws MustDiscardWeaponException is the player must discard a weapon before picking a new one
     */
    public Square grabObject(Player player, Weapon weapon) throws NotEnoughAmmoException, MustDiscardWeaponException {
        if (weapon != null) {
            if (player.hasEnoughAmmo (weapon.getType ().getGrabbingCost ())) {
                ((SpawnPoint) player.getPosition ()).removeWeapon(weapon);
                player.getOwnedWeapons().add(weapon);
                player.payAmmo (weapon.getType ().getGrabbingCost ());
                int numberOfLoaded = player.getOwnedWeapons ().size ();
                int numberOfUnloaded = player.getBoard ().getUnloadedWeapons ().size();
                if (numberOfLoaded + numberOfUnloaded > 3) {
                    throw new MustDiscardWeaponException ();
                }
            } else
                throw new NotEnoughAmmoException("You don't have enough ammo to grab this weapon.");
        } else
           grabTileContent(player, (NormalSquare) player.getPosition ());

        Action.super.executedAction(player);
        return player.getPosition();
    }

    /**
     * Method to grab an object from a square id
     * @param player is the player
     * @param squareId is the id of the square
     * @param weapon is the weapon
     * @return the new player's position
     * @throws NotEnoughAmmoException if the player doesn't have enough ammo
     * @throws MustDiscardWeaponException is the player must discard a weapon before picking a new one
     */
    public Square grabObject(Player player, int squareId, Weapon weapon) throws NotEnoughAmmoException, MustDiscardWeaponException {
        if (paths.contains(squareId)) {
            player.getPosition ().removePlayerFromSquare(player);
            player.setPosition (player.getGame ().getMap ().getSquare (squareId));
            grabObject(player, weapon);
        } else {
            player.setPosition(player.getPosition());
        }
        return player.getPosition();
    }

    /**
     * Method to grab the tile content of a square
     * @param player is the player
     * @param position is the position of the tile
     */
    private void grabTileContent(Player player, NormalSquare position) {
        Ammo ammo;
        Tile tile = position.getPlacedTile();
        if (tile.getFormat ().isPowerUpIsPresent () && player.getOwnedPowerUps ().size() < 3) {
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
        if (player.getGame ().getTilesDeck ().getCards ().isEmpty ()) {
            player.getGame ( ).getTilesDeck ( ).reloadDeck ( );
            player.getGame ( ).getTilesDeck ( ).deckShuffle ();
        }
    }

    /**
     * Getter method
     * @return the action information
     */
    @Override
    public String getActionInfo() {
        return Action.super.getActionInfo() + "With this action you can (optionally) move through a single square " +
                " and grab the object contained by the square you are on.";
    }
}
