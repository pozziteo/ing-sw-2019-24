package main.java.model.map;

public class Door {
    private Square firstSquare;
    private Square secondSquare;

    Door(Square s1, Square s2){
        this.firstSquare = s1;
        this.secondSquare = s2;
    }
}
