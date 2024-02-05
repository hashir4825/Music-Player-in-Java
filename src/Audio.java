
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Audio {

    private static AdvancedPlayer player;
    private static int lastPosition; // Variable to store the last position
    private static String filePath; // Variable to store the file path
    private static volatile boolean isPlaying = false;

    static void stopSong() {
        if (player != null) {
            player.close();
        }
    }



static void playSong(String path) {
    // Stop the currently playing song if any
    stopSong();

    SwingWorker<Void, Void> worker = new SwingWorker<>() {
        @Override
        protected Void doInBackground() {
            try {
                // Create a new AdvancedPlayer with the given song path
                FileInputStream fileInputStream = new FileInputStream(path);
                player = new AdvancedPlayer(fileInputStream);

                // Set the playback listener to capture playback events
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent evt) {
                        lastPosition = evt.getFrame();
                    }
                });

                // Play the song
                player.play();

            } catch (JavaLayerException | FileNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void done() {
            // Close resources or perform any necessary cleanup
            if (player != null) {
                player.close();
            }
            try {
                // Close the FileInputStream
                if (filePath != null) {
                    FileInputStream fileInputStream = new FileInputStream(filePath);
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    // Execute the SwingWorker in the background
    worker.execute();
}



    static void pauseSong() {
        if (player != null) {
            player.close();
        }
    }

    static void resumeSong() {
                        System.out.println("Resume function");
                        System.out.println(lastPosition);
        if (filePath != null && lastPosition > 0) {
            try {
                // Create a new FileInputStream with the given song path
                FileInputStream fileInputStream = new FileInputStream(filePath);

                // Skip to the last position
                fileInputStream.skip(lastPosition);

                // Create a new AdvancedPlayer with the FileInputStream
                player = new AdvancedPlayer(fileInputStream);

                // Set the playback listener to capture playback events
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent evt) {
                        lastPosition = evt.getFrame();
                    }
                });

                // Create a new thread to resume playing the song
                new Thread(() -> {
                    try {
                        player.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }).start();

            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

}
