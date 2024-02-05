
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SongDatabase {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Music_Player";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    public SongDatabase() {
        try {
            // Establish the connection in the constructor
            this.connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            createSongTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSongTable() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS songs ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "name VARCHAR(255),"
                    + "singer VARCHAR(255),"
                    + "duration VARCHAR(255),"
                    + "image LONGBLOB,"
                    + "genre VARCHAR(255),"
                    + "path VARCHAR(255)"
                    + ")";
            try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
                preparedStatement.executeUpdate();
                System.out.print("Done");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert Table
    public static void insertIntoSongsTable(String name, String singer, String duration, byte[] image, String genre, String path) {
        try {
            // Check if the song already exists in the database
            if (!isSongAlreadyExists(name)) {
                String insertSQL = "INSERT INTO songs (name, singer, duration, image, genre, path) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, singer);
                    preparedStatement.setString(3, duration);
                    preparedStatement.setBytes(4, image); // Assuming image is a byte array
                    preparedStatement.setString(5, genre);
                    preparedStatement.setString(6, path);

                    preparedStatement.executeUpdate();
                    System.out.println("Song Inserted Successfully");
                }
            }
//        else {
//            System.out.println("Song already exists in the database. Not inserting again.");
//        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// Helper method to check if a song with the given name already exists
    private static boolean isSongAlreadyExists(String name) throws SQLException {
        String checkSQL = "SELECT COUNT(*) FROM songs WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkSQL)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }

    // Display Song Table
    ArrayList<Song> displaySongs() {
        ArrayList<Song> songs = new ArrayList<>();

        try {
            String selectSQL = "SELECT * FROM songs";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL); ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String singer = resultSet.getString("singer");
                    String duration = resultSet.getString("duration");
                    String genre = resultSet.getString("genre");
                    String path = resultSet.getString("path");

                    // Create a Song object and add it to the ArrayList
                    Song song = new Song(id, name, singer, duration, genre, path);
                    songs.add(song);
                    System.out.println("Added");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public Song getSongById(int songId) {
        try {
            String selectSQL = "SELECT * FROM songs WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
                preparedStatement.setInt(1, songId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String singer = resultSet.getString("singer");
                        String duration = resultSet.getString("duration");
                        String genre = resultSet.getString("genre");
                        String path = resultSet.getString("path");

                        // Create a Song object and return it
                        return new Song(id, name, singer, duration, genre, path);
                    } else {
                        System.out.println("No song found with ID: " + songId);
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
