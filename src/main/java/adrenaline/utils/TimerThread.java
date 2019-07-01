package adrenaline.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class is used as timer for player time out.
 */

public class TimerThread implements Runnable {
    private long timeout;
    private Thread thread;
    private TimerCallBack timerCallBack; //is the callback class for this timer
    private final AtomicBoolean running;
    private boolean forSpecificPlayer;
    private String nickname;

    public TimerThread(TimerCallBack timerCallBack, long timeout) {
        this.timeout = timeout;
        this.timerCallBack = timerCallBack;
        this.running = new AtomicBoolean (false);
        this.forSpecificPlayer = false;
    }

    /**
     * Method to start running the timer thread for everyone.
     */

    public void startThread() {
        this.forSpecificPlayer = false;
        this.thread = new Thread(this);
        this.running.set (true);
        thread.start();
    }

    /**
     * Method to start running the timer thread for a specific player.
     */

    public void startThread(String nickname) {
        this.forSpecificPlayer = true;
        this.nickname = nickname;
        this.thread = new Thread (this);
        this.running.set(true);
        this.thread.start();
    }

    /**
     * Getter method to know if the thread is running or not.
     * @return true if running, false otherwise
     */

    public boolean isRunning() {
        return this.running.get ();
    }

    /**
     * Method to run the thread until the timer runs out.
     */

    @Override
    public void run() {
        Thread.currentThread().setName("Time Out Thread");
        long startTime = System.currentTimeMillis ();
        while(System.currentTimeMillis () - startTime <= timeout) {
            if (!running.get ()) {
                System.out.print(Thread.currentThread ().getName () + " stopped...\n");
                return;
            }
        }
        if (running.get()) {
            if (forSpecificPlayer) {
                timerCallBack.timerCallBack (nickname);
            } else {
                timerCallBack.timerCallBack ();
            }
        }
    }

    /**
     * Method to stop the thread.
     */

    public void shutDownThread() {
        this.running.set(false);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            System.out.print (e.getMessage ());
            Thread.currentThread().interrupt();
        }
    }

}
