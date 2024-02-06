
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
           createRecentSongsTable();
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
    
    
    // Helper method to check if a song with the given name already exists in "recentsongs" table
    private static boolean isSongAlreadyExistsInRecent(String name) throws SQLException {
        String checkSQL = "SELECT COUNT(*) FROM recentsongs WHERE name = ?";
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
    
        public Song getSongByIdFromRecentSongs(int songId) {
        try {
            String selectSQL = "SELECT * FROM recentsongs WHERE id = ?";
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

    // counting total number of songs
    public static int getTotalNumberOfSongs() {
        int totalSongs = 0;
        try {
            String countSQL = "SELECT COUNT(*) FROM songs";
            try (PreparedStatement preparedStatement = connection.prepareStatement(countSQL); ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                totalSongs = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalSongs;
    }
    
        // count number of song in "recentsongs" table
        public static int getTotalNumberOfRecentSongs() {
        int totalSongs = 0;
        try {
            String countSQL = "SELECT COUNT(*) FROM recentsongs";
            try (PreparedStatement preparedStatement = connection.prepareStatement(countSQL); ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                totalSongs = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalSongs;
    }
    
    public void createRecentSongsTable() {
    try {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS recentsongs ("
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
            System.out.println("Recent Songs Table Created Successfully");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public static void insertIntoRecentSongsTable(String name, String singer, String duration, byte[] image, String genre, String path) {
        try {
            // Check if the song already exists in the recent songs table
            if (!isSongAlreadyExistsInRecent(name)) {
                // Check the total number of songs in the recent songs table
                int totalRecentSongs = getTotalNumberOfRecentSongs();
                // If the total is already 5, delete the oldest song to make space for the new one
                if (totalRecentSongs >= 5) {
                    deleteOldestRecentSong();
                }

                // Insert the new song into the recent songs table
                String insertSQL = "INSERT INTO recentsongs (name, singer, duration, image, genre, path) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, singer);
                    preparedStatement.setString(3, duration);
                    preparedStatement.setBytes(4, image);
                    preparedStatement.setString(5, genre);
                    preparedStatement.setString(6, path);

                    preparedStatement.executeUpdate();
                    System.out.println("Song Inserted into Recent Songs Table Successfully");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteOldestRecentSong() throws SQLException {
        // Delete the oldest song based on some ordering criteria, for example, by ID
        String deleteSQL = "DELETE FROM recentsongs ORDER BY id LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.executeUpdate();
            System.out.println("Oldest Recent Song Deleted Successfully");
        }
    }
    
    public ArrayList<Song> displayRecentSongs() {
    ArrayList<Song> recentSongs = new ArrayList<>();

    try {
        // Select the latest 5 songs from the recent songs table
        String selectSQL = "SELECT * FROM (SELECT * FROM recentsongs ORDER BY id DESC LIMIT 5) AS recent ORDER BY id ASC";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String singer = resultSet.getString("singer");
                String duration = resultSet.getString("duration");
                String genre = resultSet.getString("genre");
                String path = resultSet.getString("path");

                // Create a Song object and add it to the ArrayList
                Song song = new Song(id, name, singer, duration, genre, path);
                recentSongs.add(song);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return recentSongs;
}


    // Helper function to get the ID of the first song in the recent songs table
     static int getFirstSongId() throws SQLException {
        String selectSQL = "SELECT MIN(id) AS first_id FROM recentsongs";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("first_id");
            } else {
                // If the table is empty, return -1 or handle accordingly
                return -1;
            }
        }
    }

}
