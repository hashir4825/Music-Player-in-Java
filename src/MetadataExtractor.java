import java.io.File;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.images.Artwork;


public class MetadataExtractor {
    String name;
    String singer;
    String duration;
    byte[] image;
    String genre;
    String path;

    public void extractMetadata(String filePath) {
        try {
            // Read metadata using JAudioTagger
            
            AudioFile audioFile = AudioFileIO.read(new File(filePath));
            Tag tag = audioFile.getTag();

            this.name = tag.getFirst(FieldKey.TITLE);
            this.singer = tag.getFirst(FieldKey.ARTIST);
            long durationInMilliseconds = audioFile.getAudioHeader().getTrackLength() * 1000; // Convert to milliseconds
            this.duration = formatDuration(durationInMilliseconds);
try {
    Artwork artwork = tag.getFirstArtwork();
    if (artwork != null) {
        this.image = artwork.getBinaryData(); // Image as byte array
    } else {
        // Handle the case where the artwork is null (e.g., set a default image)
        // For now, let's set image to an empty byte array
        this.image = new byte[0];
    }
} catch (Exception e) {
    e.printStackTrace(); // Handle the exception according to your needs
    // For now, let's set image to an empty byte array
    this.image = new byte[0];
}
            this.genre = tag.getFirst(FieldKey.GENRE);
            this.path = filePath; // The path to the audio file
            
            System.out.println("Name: " + name);
            System.out.println("Singer: " + singer);
            System.out.println("Duration: " + duration);
            System.out.println("Genre: " + genre);
            System.out.println("Path: " + path);
            
            System.out.println("\n\nNow\n");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        private static String formatDuration(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}