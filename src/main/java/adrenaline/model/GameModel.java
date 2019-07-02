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

import java.util.*;

//TODO javadoc

public class GameModel {

    private Game game;

    public GameModel(String[] playerNames) {
        this.game = new Game(playerNames);
    }

    /**
     * Getter method for game
     * @return this game
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Method to create every square
     * @return a the map (list of squares)
     */
    public List<SquareDetails> createSquareDetails() {
        List<SquareDetails> map = new LinkedList<> ();

        for (int i = 0; i < game.getMap ().getDimension (); i++) {
            SquareDetails square;
            List<String> playersNames = new ArrayList<> ();

            if (! game.getMap ().getSquare (i).getLinks ().isEmpty ()) {
                for (Player p : game.getMap ( ).getSquare (i).getPlayersOnSquare ( ))
                    playersNames.add (p.getPlayerName ( ));
                if (game.getMap ( ).getSquare (i).isSpawnPoint ( )) {
                    WeaponDetails[] weapons = new WeaponDetails[3];
                    for (int j = 0; j < 3; j++) {
                        weapons[j] = createWeaponDetail (((SpawnPoint) game.getMap ( ).getSquare (i)).getWeapons ( )[j]);
                    }
                    square = new SpawnPointDetails (i, playersNames, weapons);

                } else {
                    String tile;
                    String tileName;
                    try {
                        tileName = ((NormalSquare) game.getMap ( ).getSquare (i)).getPlacedTile().getFormat().name();
                        tile = ((NormalSquare) game.getMap ( ).getSquare (i)).getPlacedTile ( ).getTileDescription ( );
                    } catch (NullPointerException e) {
                        tileName = null;
                        tile = "empty";
                    }
                    square = new NormalSquareDetails (i, playersNames, tile, tileName);
                }
                map.add (square);
            }
        }
        return map;
    }

    /**
     * Method to create the powerup details
     * @param p is the player
     * @return the list of powerup details
     */
    public List<PowerUpDetails> createPowerUpDetails(Player p) {
        List<PowerUpDetails> powerUps = new ArrayList<> ();
        for (PowerUp pup : p.getOwnedPowerUps ()) {
            PowerUpDetails powerUpDetails = new PowerUpDetails (pup.getPowerUpsName (), pup.getAmmo ().getColor ());
            powerUps.add(powerUpDetails);
        }
        return powerUps;
    }

    /**
     * Method to create a weapon's details
     * @param w is the weapon
     * @return the details of a weapon
     */
    public WeaponDetails createWeaponDetail(Weapon w) {
        List<String> ammoList = new ArrayList<> ();
        List<String> grabList = new ArrayList<> ();
        List<String> additionalList1 = new ArrayList<> ();
        List<String> additionalList2 = new ArrayList<> ();
        String empty = "empty";

        if (w == null) {
            ammoList.add(empty);
            grabList.add(empty);
            additionalList1.add(empty);
            additionalList2.add(empty);
            return new WeaponDetails (empty, "none", ammoList, grabList, additionalList1, additionalList2);
        }

        for (Ammo a : w.getType ( ).getReloadingAmmo ( ))
            ammoList.add (a.getColor ( ));

        for (Ammo a : w.getType ( ).getGrabbingCost ( ))
            grabList.add (a.getColor ( ));

        if (! w.getOptionalEffects ().isEmpty ()) {
            for (Ammo a : w.getOptionalEffects ( ).get (0).getAdditionalCost ( ))
                additionalList1.add (a.getColor ( ));

            if (w.getOptionalEffects ().size () == 2) {
                for (Ammo a : w.getOptionalEffects ( ).get (1).getAdditionalCost ( ))
                    additionalList2.add (a.getColor ( ));
            }
        }

        return new WeaponDetails (w.getWeaponsName (), w.getWeaponsDescription (), ammoList, grabList, additionalList1, additionalList2);
    }

    /**
     * Method to update the map
     * @param account is the account of the player
     */
    public void updateMap(Account account) {
        MapResponse response = new MapResponse (game.getMap ().getMapName ());
        response.setAccount (account);
        response.sendToView ();
    }

    /**
     * Method to update the map
     * @param account is the account of the player
     */
    public void updateSquareDetails(Account account) {
        SquareDetailsResponse response = new SquareDetailsResponse (createSquareDetails ());
        response.setAccount (account);
        response.sendToView ();
    }

    /**
     * Method to update the temporary ranking
     * @param account is the account of the player
     */
    public void updateRanking(Account account) {
        List<String> ranking = new ArrayList<> ();
        for (Player p : game.getRanking ())
            ranking.add(p.getPlayerName ());
        RankingResponse response = new RankingResponse (ranking);
        response.setAccount (account);
        response.sendToView ();
    }

    /**
     * Method to update the players' boards
     * @param account is the account of the player
     */
    public void updateBoards(Account account) {
        List<BoardDetails> boards = new ArrayList<> ();
        for (Player p : game.getPlayers ()) {
            boards.add(createBoardDetails (p, account.getNickName().equals(p.getPlayerName())));
        }
        BoardsResponse response = new BoardsResponse (boards);
        response.setAccount (account);
        response.sendToView ();
    }

    /**
     * Method to update a player's board
     * @param account is the account of the player
     */
    public void updatePlayerBoard(Account account) {
        List<BoardDetails> boards = new ArrayList<> ();
        boards.add (createBoardDetails (game.findByNickname (account.getNickName ()), true));
        BoardsResponse response = new BoardsResponse (boards);
        response.setAccount (account);
        response.sendToView ();
    }

    /**
     * Method to create the details of a player's board
     * @param p is the player
     * @param showPowerUps is true if the information is for the player's board
     * @return the details of the board
     */
    private BoardDetails createBoardDetails(Player p, boolean showPowerUps) {
        BoardDetails details = new BoardDetails ();

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
        List<PowerUpDetails> powerUps = new ArrayList<> ();
        if (showPowerUps) {
            powerUps = createPowerUpDetails (p);
        }
        List<String> ownedAmmo = new ArrayList<> ();
        for (Ammo a : p.getBoard ().getOwnedAmmo ())
            ownedAmmo.add(a.getColor ());
        int[] pointsForKill = p.getBoard ().getPointsForKill ();
        int pointsToken = p.getBoard().getPointTokens();

        details.setNickname (nickname);
        details.setColor (color);
        details.setPosition (n);
        details.setDamageTaken (damageTaken);
        details.setReceivedMarks (receivedMarks);
        details.setLoadedWeapons (loadedWeapons);
        details.setUnloadedWeapons (unloadedWeapons);
        details.setPowerUps (powerUps);
        details.setOwnedAmmo (ownedAmmo);
        details.setPointsForKill (pointsForKill);
        details.setPointsToken (pointsToken);
        return details;
    }

    /**
     * Method to create a weapon's effect
     * @param weapon is the weapon
     * @return the list of effects
     */
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

    /**
     * Method to create the target's details
     * @param effect is the weapon effect that require a specific target type
     * @return the list of available targets
     */
    public List<TargetDetails> createTargetDetails(WeaponEffect effect) {
        List<TargetDetails> targets = new ArrayList<> ();
        for (TargetType type : effect.getTargetTypes ())
            targets.add(new TargetDetails (type.getTargetValue (), type.isAreaType (), type.getMovements ()));
        return targets;
    }

    /**
     * Method to find the compliant targets for a specific weapon effect
     * @param effect is the effect of the weapon
     * @param attacker is the name of the attacker
     * @return the list of available targets
     */
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

    /**
     * Method to check which players have a tagback grenade
     * @return
     */
    public List<Player> findPlayersEnabledToTagback() {
        List<Player> players = new ArrayList<>();
        for (Player p : game.getPlayers ()) {
            if (p.canTagbackGrenade ())
                players.add(p);
        }
        return players;
    }

    /**
     * Method to remove from a player the possibility of using the tagback in the same action
     */
    public void resetCanTagback() {
        for (Player p : game.getPlayers ())
            p.setCanTagbackGrenade (false);
    }

    /**
     * Method to create the list of possible paths for a player
     * @param user is the name of the player
     * @return the list of path
     */
    public Map<String, List<Integer>> createPossiblePaths(String user) {
        Map<String, List<Integer>> paths = new HashMap<> ();
        Player userPlayer = game.findByNickname (user);
        NullRequirement req = new NullRequirement ();
        List<Player> targets = req.findTargets (userPlayer);
        for (Player p : targets)
            paths.put(p.getPlayerName (), Action.findPaths (p, 2));
        return paths;
    }
}