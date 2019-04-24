package network.rmi;

public class RmiServer {
    private boolean running;

    public RmiServer() {
        this.running = false;
    }

    public void startServer() {
        running = true;
    }


    public boolean getRunning() {
        return this.running;
    }
}
