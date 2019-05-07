package adrenaline.misc;

public class TimerThread implements Runnable {
    private long timeout;
    private Thread timerThread;
    private TimerCallBack timerCallBack;
    private long startTime;
    private boolean running;
    private boolean forSpecificPlayer;
    private String nickname;

    public TimerThread(TimerCallBack timerCallBack, long timeout) {
        this.timeout = timeout;
        this.timerCallBack = timerCallBack;
        this.running = false;
        this.forSpecificPlayer = false;
    }

    public void setRunning(boolean value) {
        this.running = value;
    }

    public void startThread() {
        this.forSpecificPlayer = false;
        this.timerThread = new Thread(this);
        this.running = true;
        timerThread.start();
    }

    public void startThread(String nickname) {
        this.forSpecificPlayer = true;
        this.nickname = nickname;
        this.timerThread = new Thread (this);
        this.running = true;
        this.timerThread.start();
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis ();
        while(System.currentTimeMillis () - startTime <= timeout) {
            if (!running) {
                return;
            }
        }
        if (running) {
            if (forSpecificPlayer) {
                timerCallBack.timerCallBack (nickname);
            } else {
                timerCallBack.timerCallBack ();
            }
        }
        running = false;
    }

    public void shutDownThread() throws InterruptedException {
        this.running = false;
        Thread.sleep(1);
    }

}
