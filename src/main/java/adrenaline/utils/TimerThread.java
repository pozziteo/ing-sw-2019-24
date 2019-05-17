package adrenaline.utils;

import java.util.concurrent.atomic.AtomicBoolean;

public class TimerThread implements Runnable {
    private long timeout;
    private Thread thread;
    private TimerCallBack timerCallBack;
    private final AtomicBoolean running;
    private boolean forSpecificPlayer;
    private String nickname;

    public TimerThread(TimerCallBack timerCallBack, long timeout) {
        this.timeout = timeout;
        this.timerCallBack = timerCallBack;
        this.running = new AtomicBoolean (false);
        this.forSpecificPlayer = false;
    }

    public void startThread() {
        this.forSpecificPlayer = false;
        this.thread = new Thread(this);
        this.running.set (true);
        thread.start();
    }

    public void startThread(String nickname) {
        this.forSpecificPlayer = true;
        this.nickname = nickname;
        this.thread = new Thread (this);
        this.running.set(true);
        this.thread.start();
    }

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

    public void shutDownThread() {
        this.running.set(false);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            System.out.print (e.getMessage ());
        }
    }

}
