import java.util.TimerTask;

public class TimerHelper extends TimerTask {
    public static int ms = 0;

    public void run() {
        ms++;

        if (ms % 1000 == 0) {
            System.err.println(ms / 1000 + " seconds");
        }
    }
}
