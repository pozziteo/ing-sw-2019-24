package model.player;

public class Shoot implements Action {

    public Shoot(Player shooter) {
        System.out.println("You can use the following weapons:\n");
        getWeapons(shooter);
    }

}
