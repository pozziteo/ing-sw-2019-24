package adrenaline.model.player;

import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.PowerUp;
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

        System.out.println("You can (optionally) move into squares:");
        if (!frenzy) {
            if (player.getBoard().getDamageTaken().size() >= 3) {
                System.out.println("( Adrenaline Action! )");
                paths = Action.findPaths(player, 2);
            } else
                paths = Action.findPaths(player, 1);
        }
        else {
            System.out.println("( Final Frenzy Turn! )");
            if (!player.equals(firstPlayer) &&
                    players.indexOf(player) < players.indexOf(firstPlayer)) {
                paths = Action.findPaths(player, 2);
            }
            else
                paths = Action.findPaths(player, 3);
        }
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
            player.setPosition(player.getGame().getMap().getSquare(squareId));
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
