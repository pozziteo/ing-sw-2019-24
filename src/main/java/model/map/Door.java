package model.map;

public class Door {
    private Square firstSquare;
    private Square secondSquare;

    public Door(Square s1, Square s2){
        this.firstSquare = s1;
        this.secondSquare = s2;
    }
}
