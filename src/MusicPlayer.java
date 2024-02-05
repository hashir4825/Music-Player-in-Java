import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.Player;

public class MusicPlayer {
  private final static int NOTSTARTED = 0;
  private final static int PLAYING = 1;
  private final static int PAUSED = 2;
  private final static int FINISHED = 3;

  private Player player;
  private final Object playerLock = new Object();
  private int playerStatus = NOTSTARTED;

  public MusicPlayer() throws JavaLayerException {
    this.player = null;
  }

  public  void play(String filePath) throws JavaLayerException, FileNotFoundException {
    InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
    synchronized (playerLock) {
      switch (playerStatus) {
        case NOTSTARTED:
          final Runnable r = new Runnable() {
            public void run() {
              try {
                player = new Player(inputStream);
                playInternal();
              } catch (JavaLayerException e) {
                e.printStackTrace();
              }
            }
          };
          final Thread t = new Thread(r);
          t.setDaemon(true);
          t.setPriority(Thread.MAX_PRIORITY);
          playerStatus = PLAYING;
          t.start();
          break;
        case PAUSED:
          resume();
          break;
        default:
          break;
      }
    }
  }

  public boolean pause() {
    synchronized (playerLock) {
      if (playerStatus == PLAYING) {
        playerStatus = PAUSED;
      }
      return playerStatus == PAUSED;
    }
  }

  public void resume() {
    synchronized (playerLock) {
      if (playerStatus == PAUSED) {
        playerStatus = PLAYING;
        playerLock.notifyAll();
      }
    }
  }

  public void stop() {
    synchronized (playerLock) {
      playerStatus = FINISHED;
      playerLock.notifyAll();
    }
  }

  private void playInternal() {
    while (playerStatus != FINISHED) {
      try {
        if (!player.play(1)) {
          break;
        }
      } catch (final JavaLayerException e) {
        break;
      }
      synchronized (playerLock) {
        while (playerStatus == PAUSED) {
          try {
            playerLock.wait();
          } catch (final InterruptedException e) {
            break;
          }
        }
      }
    }
    close();
  }

  private void close() {
    synchronized (playerLock) {
      playerStatus = FINISHED;
    }
    try {
      player.close();
    } catch (final Exception e) {
      // ignore, we are terminating anyway
    }
  }
}
