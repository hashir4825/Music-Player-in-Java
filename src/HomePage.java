
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Hp
 */
public class HomePage extends javax.swing.JFrame {

    SongDatabase db;
    private JPanel currentlyPlayingPanel;
    boolean toggle = false;
    boolean repeat = true;
    int id;
    String play_pause_icon = "play";
    Music m = new Music();

    /**
     * Creates new form HomePage
     */
    public HomePage() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        db = new SongDatabase();
        volume_slider.setVisible(false);
        int countSongs = SongDatabase.getTotalNumberOfSongs();
        if (countSongs > 0) {
            this.ChooseButton.setVisible(false);
            this.pathField.setVisible(false);
            this.RecentSongsLabel.setVisible(true);
        }
        else
        {
            this.RecentSongsLabel.setVisible(false);
        }
        displayRecentSongsOnPanel();
    }

    public void playSongAndUpdateUI(String path) {
        try {
            // Play the song
            PausablePlayer.setPlayer(path);
            PausablePlayer.play();

            // Extract metadata
            MetadataExtractor mx = new MetadataExtractor();
            mx.extractMetadata(path);

            // Update UI elements
            String name = mx.name;
            byte[] pic = mx.image;

            // Update the name label
            SongNameLabel.setText(name);

            // Update the picture label
            ImageIcon icon = new ImageIcon(pic);
            Image scaledImage = icon.getImage().getScaledInstance(SongPicLabel.getWidth(), SongPicLabel.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            SongPicLabel.setIcon(scaledIcon);

        } catch (Exception ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayRecentSongsOnPanel() {
        // Fetch recent songs from the database
        List<Song> recentSongs = db.displayRecentSongs();

        if (recentSongs.isEmpty()) {
            System.out.println("No recent songs to display.");
        } else {
            // Create a new panel for storing the recent song panels vertically
            JPanel recentSongListPanel = new JPanel();
            recentSongListPanel.setLayout(new BoxLayout(recentSongListPanel, BoxLayout.Y_AXIS));

            // Iterate through the recent songs and create panels dynamically
            for (Song song : recentSongs) {
                JPanel songPanel = new JPanel();
                songPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

                // Create and add labels to the songPanel
                JLabel nameLabel = createFormattedLabel(song.name);
                JLabel singerLabel = createFormattedLabel(song.singer);
                JLabel durationLabel = createFormattedLabel(song.duration);
                JLabel genreLabel = createFormattedLabel(song.genre);
                JLabel pathLabel = createFormattedLabel(song.path);

                // Set preferred size for labels
                Dimension labelSize = new Dimension(150, 50); // Fixed height of 20
                nameLabel.setPreferredSize(labelSize);
                singerLabel.setPreferredSize(labelSize);
                durationLabel.setPreferredSize(labelSize);
                genreLabel.setPreferredSize(labelSize);
                pathLabel.setPreferredSize(labelSize);

                // Add labels to the songPanel
                songPanel.add(nameLabel);
                songPanel.add(singerLabel);
                songPanel.add(durationLabel);
                songPanel.add(genreLabel);
                songPanel.add(pathLabel);

                // Set a background color for the panel
                songPanel.setBackground(new Color(240, 240, 240));

                // Add the dynamically created panel to recentSongListPanel
                recentSongListPanel.add(songPanel);

                // Add mouse listeners for hover effect and click event
                songPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        if (songPanel != currentlyPlayingPanel) {
                            songPanel.setBackground(new Color(250, 250, 250));
                        }
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        if (songPanel != currentlyPlayingPanel) {
                            songPanel.setBackground(new Color(240, 240, 240));
                        }
                    }

                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        MetadataExtractor mx = new MetadataExtractor();

                        id = song.id;
                        String pathe = song.path;

                        play_pause_icon = "pause";
                        PlayButton.setIcon(new ImageIcon("C:\\Users\\Hp\\Desktop\\Music_Player\\src\\Images\\pause.png"));
                        // Call function to show pic and name of song
                        playSongAndUpdateUI(pathe);

                        // Change the background color of the currently playing panel to light blue
                        if (currentlyPlayingPanel != null) {
                            currentlyPlayingPanel.setBackground(new Color(240, 240, 240));
                        }
                        songPanel.setBackground(new Color(173, 216, 230)); // Light blue
                        currentlyPlayingPanel = songPanel;
                    }
                });
            }

            // Create a JScrollPane and set its viewport
            JScrollPane recentSongsScrollPane = new JScrollPane(recentSongListPanel);

            // Add the JScrollPane to the RecentSongsLoadPanel viewport
            RecentSongsLoadPanel.setViewportView(recentSongsScrollPane);
        }

        // Repaint and revalidate to reflect changes
        RecentSongsLoadPanel.revalidate();
        RecentSongsLoadPanel.repaint();
    }

    private JLabel createFormattedLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 12)); // Adjusted font size here
        return label;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HomePanel = new javax.swing.JPanel();
        NavBar = new javax.swing.JPanel();
        HomePageButton = new javax.swing.JButton();
        MusicPageButton = new javax.swing.JButton();
        PlayListPageButton = new javax.swing.JButton();
        ControlPanel = new javax.swing.JPanel();
        MusicSlider = new javax.swing.JSlider();
        PreviousButton = new javax.swing.JButton();
        PlayButton = new javax.swing.JButton();
        NextButton = new javax.swing.JButton();
        volume_slider = new javax.swing.JSlider();
        VolumeButton = new javax.swing.JButton();
        SongPicLabel = new javax.swing.JLabel();
        SongNameLabel = new javax.swing.JLabel();
        repeatButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        RecentSongsLoadPanel = new javax.swing.JScrollPane();
        BrowsePanel = new javax.swing.JPanel();
        ChooseButton = new javax.swing.JButton();
        pathField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        RecentSongsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        HomePanel.setBackground(new java.awt.Color(102, 153, 255));

        NavBar.setBackground(new java.awt.Color(102, 153, 255));

        HomePageButton.setBackground(new java.awt.Color(255, 0, 0));
        HomePageButton.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        HomePageButton.setForeground(new java.awt.Color(255, 255, 255));
        HomePageButton.setText("Home");
        HomePageButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HomePageButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                HomePageButtonMouseExited(evt);
            }
        });
        HomePageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HomePageButtonActionPerformed(evt);
            }
        });

        MusicPageButton.setBackground(new java.awt.Color(255, 0, 0));
        MusicPageButton.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        MusicPageButton.setForeground(new java.awt.Color(255, 255, 255));
        MusicPageButton.setText("Music");
        MusicPageButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MusicPageButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MusicPageButtonMouseExited(evt);
            }
        });
        MusicPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MusicPageButtonActionPerformed(evt);
            }
        });

        PlayListPageButton.setBackground(new java.awt.Color(255, 0, 0));
        PlayListPageButton.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        PlayListPageButton.setForeground(new java.awt.Color(255, 255, 255));
        PlayListPageButton.setText("PlayList");
        PlayListPageButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PlayListPageButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PlayListPageButtonMouseExited(evt);
            }
        });
        PlayListPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayListPageButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout NavBarLayout = new javax.swing.GroupLayout(NavBar);
        NavBar.setLayout(NavBarLayout);
        NavBarLayout.setHorizontalGroup(
            NavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(NavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NavBarLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(NavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(HomePageButton)
                            .addComponent(MusicPageButton)))
                    .addComponent(PlayListPageButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        NavBarLayout.setVerticalGroup(
            NavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavBarLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(HomePageButton)
                .addGap(21, 21, 21)
                .addComponent(MusicPageButton)
                .addGap(27, 27, 27)
                .addComponent(PlayListPageButton)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        ControlPanel.setBackground(new java.awt.Color(102, 153, 255));

        PreviousButton.setBackground(new java.awt.Color(255, 0, 0));
        PreviousButton.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        PreviousButton.setForeground(new java.awt.Color(255, 255, 255));
        PreviousButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/previous.png"))); // NOI18N
        PreviousButton.setPreferredSize(new java.awt.Dimension(75, 33));
        PreviousButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PreviousButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PreviousButtonMouseExited(evt);
            }
        });
        PreviousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreviousButtonActionPerformed(evt);
            }
        });

        PlayButton.setBackground(new java.awt.Color(255, 0, 0));
        PlayButton.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        PlayButton.setForeground(new java.awt.Color(255, 255, 255));
        PlayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/play.png"))); // NOI18N
        PlayButton.setPreferredSize(new java.awt.Dimension(72, 33));
        PlayButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PlayButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PlayButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PlayButtonMouseExited(evt);
            }
        });
        PlayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayButtonActionPerformed(evt);
            }
        });

        NextButton.setBackground(new java.awt.Color(255, 0, 0));
        NextButton.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        NextButton.setForeground(new java.awt.Color(255, 255, 255));
        NextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/next.png"))); // NOI18N
        NextButton.setPreferredSize(new java.awt.Dimension(72, 33));
        NextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                NextButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                NextButtonMouseExited(evt);
            }
        });
        NextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextButtonActionPerformed(evt);
            }
        });

        volume_slider.setMajorTickSpacing(20);
        volume_slider.setMinorTickSpacing(5);
        volume_slider.setPaintLabels(true);
        volume_slider.setPaintTicks(true);
        volume_slider.setSnapToTicks(true);
        volume_slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volume_sliderStateChanged(evt);
            }
        });

        VolumeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/volume-down.png"))); // NOI18N
        VolumeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VolumeButtonActionPerformed(evt);
            }
        });

        SongNameLabel.setBackground(new java.awt.Color(255, 0, 0));
        SongNameLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        SongNameLabel.setForeground(new java.awt.Color(255, 255, 255));

        repeatButton.setBackground(new java.awt.Color(255, 51, 51));
        repeatButton.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        repeatButton.setForeground(new java.awt.Color(255, 255, 255));
        repeatButton.setText("Repeat");
        repeatButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repeatButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                repeatButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                repeatButtonMouseExited(evt);
            }
        });
        repeatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repeatButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ControlPanelLayout = new javax.swing.GroupLayout(ControlPanel);
        ControlPanel.setLayout(ControlPanelLayout);
        ControlPanelLayout.setHorizontalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(MusicSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SongPicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SongNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ControlPanelLayout.createSequentialGroup()
                        .addComponent(VolumeButton)
                        .addGap(82, 82, 82))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ControlPanelLayout.createSequentialGroup()
                        .addComponent(volume_slider, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(PreviousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(PlayButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(NextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(repeatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        ControlPanelLayout.setVerticalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MusicSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NextButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PreviousButton, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ControlPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(repeatButton))
                    .addComponent(PlayButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ControlPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(volume_slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(VolumeButton))
                    .addGroup(ControlPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(ControlPanelLayout.createSequentialGroup()
                                .addComponent(SongPicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(ControlPanelLayout.createSequentialGroup()
                                .addComponent(SongNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14))))))
        );

        jPanel1.setBackground(new java.awt.Color(255, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Welcome to Music Player");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        BrowsePanel.setBackground(new java.awt.Color(102, 153, 255));

        ChooseButton.setBackground(new java.awt.Color(255, 0, 0));
        ChooseButton.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        ChooseButton.setForeground(new java.awt.Color(255, 255, 255));
        ChooseButton.setText("Choose Folder");
        ChooseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ChooseButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ChooseButtonMouseExited(evt);
            }
        });
        ChooseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseButtonActionPerformed(evt);
            }
        });

        pathField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BrowsePanelLayout = new javax.swing.GroupLayout(BrowsePanel);
        BrowsePanel.setLayout(BrowsePanelLayout);
        BrowsePanelLayout.setHorizontalGroup(
            BrowsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BrowsePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pathField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ChooseButton)
                .addGap(79, 79, 79))
        );
        BrowsePanelLayout.setVerticalGroup(
            BrowsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BrowsePanelLayout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addGroup(BrowsePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ChooseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(136, Short.MAX_VALUE))
        );

        RecentSongsLoadPanel.setViewportView(BrowsePanel);

        jPanel2.setBackground(new java.awt.Color(255, 0, 0));

        RecentSongsLabel.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        RecentSongsLabel.setForeground(new java.awt.Color(255, 255, 255));
        RecentSongsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        RecentSongsLabel.setText("Recent Songs");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(RecentSongsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(RecentSongsLabel)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout HomePanelLayout = new javax.swing.GroupLayout(HomePanel);
        HomePanel.setLayout(HomePanelLayout);
        HomePanelLayout.setHorizontalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(ControlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addComponent(NavBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HomePanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RecentSongsLoadPanel)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomePanelLayout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(122, 122, 122))))))
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(NavBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(RecentSongsLoadPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(ControlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PreviousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviousButtonActionPerformed

        try {
            int LastSong = SongDatabase.getFirstSongId() + 4;
            int firstSong = SongDatabase.getFirstSongId();
            if (id == firstSong) {
                id = LastSong;
            } else {
                id = id - 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        Song NextSong = db.getSongByIdFromRecentSongs(id);
        String path = NextSong.path;
        playSongAndUpdateUI(path);
    }//GEN-LAST:event_PreviousButtonActionPerformed

    private void PlayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayButtonActionPerformed
        // TODO add your handling code here:
        if (play_pause_icon == "play") {
            // Logic to start playing the music
            // Example: musicPlayer.play();
            PlayButton.setIcon(new ImageIcon("C:\\Users\\Hp\\Desktop\\Music_Player\\src\\Images\\pause.png"));
            PausablePlayer.resume();
            play_pause_icon = "pause";
        } else {
            // Logic to stop the music
            // Example: musicPlayer.stop();
            PlayButton.setIcon(new ImageIcon("C:\\Users\\Hp\\Desktop\\Music_Player\\src\\Images\\play.png"));
            PausablePlayer.pause();
            play_pause_icon = "play";
            System.out.println("HU");
        }

    }//GEN-LAST:event_PlayButtonActionPerformed

    private void VolumeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolumeButtonActionPerformed
        // TODO add your handling code here:
        if (toggle == false) {
            volume_slider.setVisible(true);
            toggle = true;
        } else {
            volume_slider.setVisible(false);
            toggle = false;

        }
    }//GEN-LAST:event_VolumeButtonActionPerformed

    private void HomePageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomePageButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HomePageButtonActionPerformed

    private void MusicPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MusicPageButtonActionPerformed
        // TODO add your handling code here:
        Music m = new Music();
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_MusicPageButtonActionPerformed

    private void PlayListPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayListPageButtonActionPerformed
        // TODO add your handling code here:
        Playlist p = new Playlist();
        p.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_PlayListPageButtonActionPerformed

    private void PlayListPageButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayListPageButtonMouseEntered
        // TODO add your handling code here:
        PlayListPageButton.setBackground(Color.green.darker());
        PlayListPageButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_PlayListPageButtonMouseEntered

    private void PlayListPageButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayListPageButtonMouseExited
        // TODO add your handling code here:
        PlayListPageButton.setBackground(Color.red);
        PlayListPageButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_PlayListPageButtonMouseExited

    private void MusicPageButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MusicPageButtonMouseEntered
        // TODO add your handling code here:
        MusicPageButton.setBackground(Color.green.darker());
        MusicPageButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_MusicPageButtonMouseEntered

    private void MusicPageButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MusicPageButtonMouseExited
        // TODO add your handling code here:
        MusicPageButton.setBackground(Color.red);
        MusicPageButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_MusicPageButtonMouseExited

    private void HomePageButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomePageButtonMouseEntered
        // TODO add your handling code here:
        HomePageButton.setBackground(Color.green.darker());
        HomePageButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_HomePageButtonMouseEntered

    private void HomePageButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomePageButtonMouseExited
        // TODO add your handling code here:
        HomePageButton.setBackground(Color.red);
        HomePageButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_HomePageButtonMouseExited

    private void ChooseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = chooser.getSelectedFile();

            // Now, you can use the selectedFolder to load songs from the chosen directory
            // For example, you can list all files in the directory using selectedFolder.listFiles()
            // Example:
            File[] files = selectedFolder.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    // Check if the file is an MP3 file before processing
                    if (isMP3File(file)) {
                        // Process each MP3 file (e.g., add it to your playlist)
                        // Example: playlist.add(file.getAbsolutePath());
                        String songfile = file.getAbsolutePath();
                        try {
                            MetadataExtractor mx = new MetadataExtractor();
                            mx.extractMetadata(songfile);
                            String name = mx.name;
                            String singer = mx.singer;
                            String duration = mx.duration;
                            byte[] image = mx.image;
                            String genre = mx.genre;
                            String path = mx.path;
                            SongDatabase.insertIntoSongsTable(name, singer, duration, image, genre, path);
                        } catch (Exception e) {
                            // Handle the exception (print stack trace or show an error message)
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error processing file: " + file.getName());
                        }
                    }
                }
                // Now that the songs are inserted into the database, let's display them in MusicPage
//        List<Song> songs = db.displaySongs(); // Assuming db is an instance of SongDatabase
//        Music musicPage = new Music(songs);
                Music musicPage = new Music();
                musicPage.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "No songs found in the selected folder");
            }
        }
        File f = chooser.getCurrentDirectory();
        String filename = f.getAbsolutePath();
        pathField.setText(filename);
    }

    private boolean isMP3File(File file) {
        // Check if the file has an ".mp3" extension
        return file.isFile() && file.getName().toLowerCase().endsWith(".mp3");

    }//GEN-LAST:event_ChooseButtonActionPerformed

    private void pathFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pathFieldActionPerformed

    private void ChooseButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChooseButtonMouseEntered
        // TODO add your handling code here:
        ChooseButton.setBackground(Color.green.darker());
        ChooseButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_ChooseButtonMouseEntered

    private void ChooseButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChooseButtonMouseExited
        // TODO add your handling code here:
        ChooseButton.setBackground(Color.red);
        ChooseButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_ChooseButtonMouseExited

    private void PreviousButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PreviousButtonMouseEntered
        // TODO add your handling code here:
        PreviousButton.setBackground(Color.green.darker());
        PreviousButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_PreviousButtonMouseEntered

    private void PreviousButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PreviousButtonMouseExited
        // TODO add your handling code here:
        PreviousButton.setBackground(Color.red);
        PreviousButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_PreviousButtonMouseExited

    private void PlayButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayButtonMouseEntered
        // TODO add your handling code here:
        PlayButton.setBackground(Color.green.darker());
        PlayButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_PlayButtonMouseEntered

    private void PlayButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayButtonMouseExited
        // TODO add your handling code here:
        PlayButton.setBackground(Color.red);
        PlayButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_PlayButtonMouseExited

    private void NextButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NextButtonMouseEntered
        // TODO add your handling code here:
        NextButton.setBackground(Color.green.darker());
        NextButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_NextButtonMouseEntered

    private void NextButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NextButtonMouseExited
        // TODO add your handling code here:
        NextButton.setBackground(Color.red);
        NextButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_NextButtonMouseExited

    private void repeatButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repeatButtonMouseClicked

    }//GEN-LAST:event_repeatButtonMouseClicked

    private void repeatButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repeatButtonMouseEntered
        // TODO add your handling code here:
        repeatButton.setBackground(Color.green.darker());
        repeatButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_repeatButtonMouseEntered

    private void repeatButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repeatButtonMouseExited
        // TODO add your handling code here:
        repeatButton.setBackground(Color.red);
        repeatButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_repeatButtonMouseExited

    private void repeatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repeatButtonActionPerformed
        if (repeat == true) {
            PausablePlayer.setRepeat(true);
            repeat = false;
            repeatButton.setText("Not Repeat");

        } else {
            System.out.println("by");
            repeatButton.setText("Repeat");
            PausablePlayer.setRepeat(false);
            repeat = true;

        }
    }//GEN-LAST:event_repeatButtonActionPerformed

    private void NextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextButtonActionPerformed

        play_pause_icon = "pause";
        PlayButton.setIcon(new ImageIcon("C:\\Users\\Hp\\Desktop\\Music_Player\\src\\Images\\pause.png"));

        try {
            int LastSong = SongDatabase.getFirstSongId() + 4;
            System.out.println(LastSong);
            if (id == LastSong) {
                id = SongDatabase.getFirstSongId();
            } else {
                id = id + 1;
            }
            System.out.println(id);
        } catch (SQLException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
        Song NextSong = db.getSongByIdFromRecentSongs(id);
        String path = NextSong.path;
        playSongAndUpdateUI(path);
    }//GEN-LAST:event_NextButtonActionPerformed

    private void volume_sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volume_sliderStateChanged
        // TODO add your handling code here:
        // TODO add your handling code here:
        JSlider source = (JSlider) evt.getSource();
        if (!source.getValueIsAdjusting()) {
            float volume = (int) source.getValue() / 100.0f;
            PausablePlayer.setVolume(volume);
        }
    }//GEN-LAST:event_volume_sliderStateChanged

    private void PlayButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_PlayButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BrowsePanel;
    private javax.swing.JButton ChooseButton;
    private javax.swing.JPanel ControlPanel;
    private javax.swing.JButton HomePageButton;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JButton MusicPageButton;
    private javax.swing.JSlider MusicSlider;
    private javax.swing.JPanel NavBar;
    private javax.swing.JButton NextButton;
    private javax.swing.JButton PlayButton;
    private javax.swing.JButton PlayListPageButton;
    private javax.swing.JButton PreviousButton;
    private javax.swing.JLabel RecentSongsLabel;
    private javax.swing.JScrollPane RecentSongsLoadPanel;
    private javax.swing.JLabel SongNameLabel;
    private javax.swing.JLabel SongPicLabel;
    private javax.swing.JButton VolumeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField pathField;
    private javax.swing.JButton repeatButton;
    private javax.swing.JSlider volume_slider;
    // End of variables declaration//GEN-END:variables
}
