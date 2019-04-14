package model.player;

import model.deck.*;
import model.map.*;

import java.util.*;

//TODO add JavaDoc
public class MoveAndGrab implements Action {

    private List<Integer> paths;

    public MoveAndGrab(Player player) {
        System.out.println("You can (optionally) move into squares:");
        Square position = player.getPosition();
        if (player.getBoard().getDamageTaken().size() >= 3) {
            System.out.println("( Adrenaline Action! )");
            paths = findPaths(player, 2);
        }
        else
            paths = findPaths(player, 1);
    }

    public Square grabObject(Player player) {
        Square position = player.getPosition();
        if (position.isSpawnPoint()) {
            Weapon weapon = chooseWeapon((SpawnPoint) position);
            ((SpawnPoint) player.getPosition ()).removeWeapon(weapon);
            player.getOwnedWeapons().add(weapon);
        }
        else
           grabTileContent(player, (NormalSquare) position);

        Action.super.executedAction(player);
        return player.getPosition();
    }

    public Square grabObject(Player player, int xSquare, int ySquare) {
        int squareId = xSquare*4 + ySquare;
        if (paths.contains(squareId)) {
            player.setPosition(player.getGame().getArena().getSquare(squareId));
            grabObject(player);
        }
        else {
            System.out.println("Error: you can't reach the square!\n");
            player.setPosition(player.getPosition());
        }
        return player.getPosition();
    }

    private Weapon chooseWeapon(SpawnPoint position) {
        System.out.println("Choose a weapon:");
        for (int i=0; i < position.getWeapons().length; i++)
            System.out.println(i + " - " + position.getWeapons()[i].getWeaponsName());
//        int index = new Scanner(System.in).nextInt();
        int index = 0;
        Weapon weapon = position.getWeapons()[index];
        System.out.println("Grabbing weapon from square " + position.getSquareId() + ": " + weapon.getWeaponsName());
        return weapon;
    }

    private void grabTileContent(Player player, NormalSquare position) {
        Ammo ammo;
        Tile tile = position.getPlacedTile();
        System.out.println("Grabbing tile content from square " + position.getSquareId() + ": "
                + tile.getTileDescription());
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
