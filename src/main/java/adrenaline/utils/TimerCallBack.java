package adrenaline.utils;

/**
 * This interface is used to define the callback classes for timers.
 * It is implemented by Lobby and Controller.
 */

public interface TimerCallBack {
    void timerCallBack();

    void timerCallBack(String nickname);
}
