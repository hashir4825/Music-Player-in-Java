import javax.sound.sampled.*;
import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.sound.sampled.FloatControl;
import javax.swing.Timer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class PausablePlayer {

    private final static int NOTSTARTED = 0;
    private final static int PLAYING = 1;
    private final static int PAUSED = 2;
    private final static int FINISHED = 3;
    private static Player player;
    private static final Object playerLock = new Object();
    private static int playerStatus = NOTSTARTED;

    private static boolean isRepeating = false;

    private static boolean repeat = false;
    private static Timer repeatTimer;
    private static String currentFilePath; // Store the path of the currently playing song

    public static void setPlayer(final String filePath) throws Exception {
        if (player != null) {
            synchronized (playerLock) {
                playerStatus = FINISHED;
                playerLock.notifyAll();
            }
            player.close();
        }
        InputStream inputStream = new FileInputStream(filePath);
        player = new Player(inputStream);
        currentFilePath = filePath; // Store the current file path
        playerStatus = NOTSTARTED;
    }

    public static void play() throws Exception {
        System.out.println(player);
        synchronized (playerLock) {
            if (playerStatus == NOTSTARTED) {
                final Runnable r = new Runnable() {
                    public void run() {
                        playInternal();
                    }
                };
                final Thread t = new Thread(r);
                t.setDaemon(true);
                t.setPriority(Thread.MAX_PRIORITY);
                playerStatus = PLAYING;
                t.start();
            }
        }
    }

    public static void pause() {
        synchronized (playerLock) {
            if (playerStatus == PLAYING) {
                playerStatus = PAUSED;
            }
        }
    }

    public static void resume() {
        synchronized (playerLock) {
            if (playerStatus == PAUSED) {
                playerStatus = PLAYING;
                playerLock.notifyAll();
                System.out.print("HI");
            }
        }
    }

    public static void stop() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
            playerLock.notifyAll();
        }
    }

    public static void setRepeat(boolean repeat) {
        PausablePlayer.repeat = repeat;
    }

    private static void playInternal() {
        while (playerStatus != FINISHED) {
            try {
                if (!player.play(1)) {
                    if (repeat) {
                        synchronized (playerLock) { // Add this block
                            while (playerStatus == PAUSED) {
                                try {
                                    playerLock.wait();
                                } catch (InterruptedException e) {
// Handle exception here
                                }
                            }
                        }
                        playerStatus = NOTSTARTED; // Reset the player status
                        setPlayer(currentFilePath); // Set up the player again with the current file
                        player.play(); // Play the audio again
                    } else {
                        break;
                    }
                }
                synchronized (playerLock) {
                    while (playerStatus == PAUSED) {
                        try {
                            playerLock.wait();
                        } catch (InterruptedException e) {
// Handle exception here
                        }
                    }
                }
            } catch (final Exception e) {
                break;
            }
        }
    }


public static void setVolume(float volume) {
    new Thread(() -> {
        try {
            // Introduce a small delay
            Thread.sleep(200);

            Mixer.Info[] mixers = AudioSystem.getMixerInfo();
            for (Mixer.Info mixerInfo : mixers) {
                Mixer mixer = AudioSystem.getMixer(mixerInfo);
                Line.Info[] lineInfos = mixer.getTargetLineInfo();
                for (Line.Info lineInfo : lineInfos) {
                    Line line = mixer.getLine(lineInfo);
                    line.open();
                    if (line.isControlSupported(FloatControl.Type.VOLUME)) {
                        FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                        volumeControl.setValue(volume);
                    }
                    line.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to set volume");
            e.printStackTrace();
        }
    }).start();
}




}
