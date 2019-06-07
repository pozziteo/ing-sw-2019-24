package adrenaline.model;

import adrenaline.data.data_for_client.responses_for_view.fake_model.EffectDetails;
import adrenaline.data.data_for_client.responses_for_view.*;
import adrenaline.data.data_for_client.responses_for_view.fake_model.*;
import adrenaline.model.deck.*;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.map.NormalSquare;
import adrenaline.model.map.SpawnPoint;
import adrenaline.model.player.Action;
import adrenaline.model.player.Player;
import adrenaline.network.Account;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//TODO javadoc

public class GameModel {

    private Game game;

    public GameModel(String[] playerNames) {
        this.game = new Game(playerNames);
    }

    public Game getGame() {
        return this.game;
    }

    public List<SquareDetails> createSquareDetails() {
        List<SquareDetails> map = new LinkedList<> ();

        for (int i = 0; i < game.getMap ().getDimension (); i++) {
            SquareDetails square;
            List<String> playersNames = new ArrayList<> ();

            for (Player p : game.getMap ().getSquare (i).getPlayersOnSquare ())
                playersNames.add(p.getPlayerName ());
            if (game.getMap ().getSquare (i).isSpawnPoint ()) {
                WeaponDetails[] weapons = new WeaponDetails[3];
                for (int j = 0; j < 3; j++) {
                    weapons[j] = createWeaponDetail (((SpawnPoint) game.getMap ().getSquare (i)).getWeapons ()[j]);
                }
                square = new SpawnPointDetails (i, playersNames, weapons);

            } else {
                String tile;
                try {
                    tile = ((NormalSquare) game.getMap ( ).getSquare (i)).getPlacedTile ( ).getTileDescription ( );
                } catch (NullPointerException e ) {
                    tile = null;
                }
                square = new NormalSquareDetails (i, playersNames, tile);
            }
            map.add(square);
        }
        return map;
    }

    public List<PowerUpDetails> createPowerUpDetails(Player p) {
        List<PowerUpDetails> powerUps = new ArrayList<> ();
        for (PowerUp pup : p.getOwnedPowerUps ()) {
            PowerUpDetails powerUpDetails = new PowerUpDetails (pup.getPowerUpsName (), pup.getAmmo ().getColor ());
            powerUps.add(powerUpDetails);
        }
        return powerUps;
    }

    public WeaponDetails createWeaponDetail(Weapon w) {
        List<String> ammoList = new ArrayList<> ();
        List<String> grabList = new ArrayList<> ();

        if (w == null) {
            ammoList.add("empty");
            grabList.add("empty");
            return new WeaponDetails ("empty", "none", ammoList, grabList);
        }

        for (Ammo a : w.getType ( ).getReloadingAmmo ( ))
            ammoList.add (a.getColor ( ));

        for (Ammo a : w.getType ( ).getGrabbingCost ( ))
            grabList.add (a.getColor ( ));

        return new WeaponDetails (w.getWeaponsName (), w.getWeaponsDescription (), ammoList, grabList);
    }

    public void updateMap(Account account) {
        MapResponse response = new MapResponse (game.getMap ().getMapName ());
        response.setAccount (account);
        response.sendToView ();
    }

    public void updateSquareDetails(Account account) {
        SquareDetailsResponse response = new SquareDetailsResponse (createSquareDetails ());
        response.setAccount (account);
        response.sendToView ();
    }

    public void updateRanking(Account account) {
        List<String> ranking = new ArrayList<> ();
        for (Player p : game.getRanking ())
            ranking.add(p.getPlayerName ());
        RankingResponse response = new RankingResponse (ranking);
        response.setAccount (account);
        response.sendToView ();
    }

    public void updateBoards(Account account) {
        List<BoardDetails> boards = new ArrayList<> ();
        for (Player p : game.getPlayers ()) {
            boards.add(createBoardDetails (p));
        }
        BoardsResponse response = new BoardsResponse (boards);
        response.setAccount (account);
        response.sendToView ();
    }

    public void updatePlayerBoard(Account account) {
        List<BoardDetails> boards = new ArrayList<> ();
        boards.add (createBoardDetails (game.findByNickname (account.getNickName ())));
        BoardsResponse response = new BoardsResponse (boards);
        response.setAccount (account);
        response.sendToView ();
    }

    private BoardDetails createBoardDetails(Player p) {
        String nickname = p.getPlayerName ();
        String color = p.getPlayerColor ();
        int n = p.getPosition ().getSquareId ();
        List<String> damageTaken = p.getBoard ().getDamageTaken ();
        List<String> receivedMarks = p.getBoard ().getReceivedMarks ();
        List<WeaponDetails> loadedWeapons = new ArrayList<> ();
        for (Weapon w : p.getOwnedWeapons ()) {
            loadedWeapons.add(createWeaponDetail (w));
        }
        List<WeaponDetails> unloadedWeapons = new ArrayList<> ();
        for (Weapon w : p.getBoard ().getUnloadedWeapons ()) {
            unloadedWeapons.add(createWeaponDetail (w));
        }
        List<String> ownedAmmo = new ArrayList<> ();
        for (Ammo a : p.getBoard ().getOwnedAmmo ())
            ownedAmmo.add(a.getColor ());
        int[] pointsForKill = p.getBoard ().getPointsForKill ();
        int pointsToken = p.getBoard().getPointTokens();
        return new BoardDetails (nickname, color, n, loadedWeapons, damageTaken, receivedMarks, unloadedWeapons, ownedAmmo, pointsForKill, pointsToken);
    }

    public List<EffectDetails> createWeaponEffects(Weapon weapon) {
        List<EffectDetails> effects = new ArrayList<> ();
        EffectDetails effect = new EffectDetails ("base effect", false, false);
        effects.add(effect);
        for (OptionalEffect optEffect : weapon.getOptionalEffects ()) {
            boolean alternative = optEffect.isAlternativeMode ();
            boolean usable = optEffect.isUsableBeforeBase ();
            effect = new EffectDetails ("optional effect", alternative, usable);
            effects.add(effect);
        }
        return effects;
    }

    public List<TargetDetails> createTargetDetails(WeaponEffect effect) {
        List<TargetDetails> targets = new ArrayList<> ();
        for (TargetType type : effect.getTargetTypes ())
            targets.add(new TargetDetails (type.getTargetValue (), type.isAreaType (), type.getMovements ()));
        return targets;
    }

    public List<String> findCompliantTargets(WeaponEffect effect, String attacker) {
        if (effect.getTargetTypes ().get (0).getTargetValue () != -1) {
            List<String> targets = new ArrayList<> ( );
            List<Player> compliantTargets;
            if (effect.getRequirement ( ) != null) {
                compliantTargets = effect.getRequirement ( ).findTargets (game.findByNickname (attacker));
            } else {
                NullRequirement requirement = new NullRequirement ();
                compliantTargets = requirement.findTargets (game.findByNickname (attacker));
            }
            for (Player p : compliantTargets)
                targets.add (p.getPlayerName ( ));
            return targets;
        }
        return null;
    }
}
