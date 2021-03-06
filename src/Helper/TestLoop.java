package Helper;

import java.util.Timer;

/**
 * @author Jérémie Beaudoin-Dion
 */
public class TestLoop {

    private java.util.Timer timer;
    private boolean isRunning;

    public void gameLoop() {
        timer = new Timer();
        timer.schedule(new LoopyStuff(), 0, 1000 / 60); //new timer at 60 fps, the timing mechanism
    }

    private class LoopyStuff extends java.util.TimerTask {
        public void run() {

            if (!isRunning) {
                timer.cancel();
            }
        }
    }


}
