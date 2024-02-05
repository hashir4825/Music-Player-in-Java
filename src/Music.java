
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public class Music extends javax.swing.JFrame {

    SongDatabase db;

    /**
     * Creates new form Music
     */
    private JPanel currentlyPlayingPanel;
    boolean toggle = false;
    boolean repeat = true;
    int id;
    int TotalSongs = 0;
    String play_pause_icon = "play";

    public Music() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        volume_slider.setVisible(false);
        db = new SongDatabase();
        displaySongsOnPanel();  // Call the method to display songs
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
            songNameLabel.setText(name);

            // Update the picture label
            ImageIcon icon = new ImageIcon(pic);
            Image scaledImage = icon.getImage().getScaledInstance(songPicLabel.getWidth(), songPicLabel.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            songPicLabel.setIcon(scaledIcon);

        } catch (Exception ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// Modify your displaySongsOnPanel method
    private void displaySongsOnPanel() {
        // Fetch songs from the database (Replace the following line with your database fetch logic)
        List<Song> songs = db.displaySongs();

        if (songs.isEmpty()) {
            System.out.println("No songs to display.");
        } else {
            // Create a new panel for storing the song panels vertically
            JPanel songListPanel = new JPanel();
            songListPanel.setLayout(new BoxLayout(songListPanel, BoxLayout.Y_AXIS));

            // Iterate through your data and create panels dynamically
            for (Song song : songs) {
                JPanel songPanel = new JPanel();
                songPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

                // Create and add 5 labels to the songPanel
                JLabel nameLabel = createFormattedLabel(song.name);
                JLabel singerLabel = createFormattedLabel(song.singer);
                JLabel durationLabel = createFormattedLabel(song.duration);
                JLabel genreLabel = createFormattedLabel(song.genre);
                JLabel pathLabel = createFormattedLabel(song.path);
                TotalSongs++;

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

                // Add the dynamically created panel to songListPanel
                songListPanel.add(songPanel);

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
            JScrollPane musicScrollPane = new JScrollPane(songListPanel);

            // Add the JScrollPane to the MusicLoadPanel viewport
            MusicLoadPanel.setViewportView(musicScrollPane);
        }

        // Repaint and revalidate to reflect changes
        MusicLoadPanel.revalidate();
        MusicLoadPanel.repaint();
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
        songNameLabel = new javax.swing.JLabel();
        songPicLabel = new javax.swing.JLabel();
        repeatButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        MusicLoadPanel = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        volume_slider.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        volume_slider.setMajorTickSpacing(20);
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

        songNameLabel.setBackground(new java.awt.Color(255, 0, 0));
        songNameLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        songNameLabel.setForeground(new java.awt.Color(255, 255, 255));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ControlPanelLayout.createSequentialGroup()
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ControlPanelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(MusicSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ControlPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(songPicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ControlPanelLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(songNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                                .addComponent(VolumeButton)
                                .addGap(52, 52, 52))
                            .addGroup(ControlPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(volume_slider, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ControlPanelLayout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(PreviousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(PlayButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(NextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(repeatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)))))
                .addGap(30, 30, 30))
        );
        ControlPanelLayout.setVerticalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MusicSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ControlPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(songPicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(ControlPanelLayout.createSequentialGroup()
                        .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PreviousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PlayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ControlPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(repeatButton)))
                        .addGap(36, 36, 36)
                        .addComponent(volume_slider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(songNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(VolumeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );

        jPanel1.setBackground(new java.awt.Color(255, 0, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Music");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        MusicLoadPanel.setBackground(new java.awt.Color(102, 153, 255));

        javax.swing.GroupLayout HomePanelLayout = new javax.swing.GroupLayout(HomePanel);
        HomePanel.setLayout(HomePanelLayout);
        HomePanelLayout.setHorizontalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ControlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(NavBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(MusicLoadPanel)))
                .addContainerGap())
            .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(HomePanelLayout.createSequentialGroup()
                    .addGap(202, 202, 202)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(202, 202, 202)))
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(MusicLoadPanel))
                    .addComponent(NavBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ControlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(HomePanelLayout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(409, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HomePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void HomePageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomePageButtonActionPerformed
        // TODO add your handling code here:
        HomePage h = new HomePage();
        h.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_HomePageButtonActionPerformed

    private void MusicPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MusicPageButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MusicPageButtonActionPerformed

    private void PreviousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviousButtonActionPerformed
        // TODO add your handling code here:

        // to avoid if else use the following line
        //id = (id - 2 + TotalSongs) % TotalSongs + 1;
        play_pause_icon = "pause";
        PlayButton.setIcon(new ImageIcon("C:\\Users\\Hp\\Desktop\\Music_Player\\src\\Images\\pause.png"));
        if (id == 1) {

            // if the id is 1 then it should move to the last song after pressing the previous button
            // so the id is set to the TotalSongs, total number of songs
            id = TotalSongs;
        } else {

            // if the id is not 1 then this line will work for every press of previous button and 
            // the previous song is played
            id = id - 1;

        }
        Song NextSong = db.getSongById(id);
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

    private void PlayListPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayListPageButtonActionPerformed
        // TODO add your handling code here:
        Playlist p = new Playlist();
        p.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_PlayListPageButtonActionPerformed

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

    private void PreviousButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PreviousButtonMouseExited
        // TODO add your handling code here:
        PreviousButton.setBackground(Color.red);
        PreviousButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_PreviousButtonMouseExited

    private void PreviousButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PreviousButtonMouseEntered
        // TODO add your handling code here:
        PreviousButton.setBackground(Color.green.darker());
        PreviousButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_PreviousButtonMouseEntered

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

    private void NextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextButtonActionPerformed
        // TODO add your handling code here:

        // to avoid if statement follow the following line 
        //id = (id % TotalSongs) + 1;
        play_pause_icon = "pause";
        PlayButton.setIcon(new ImageIcon("C:\\Users\\Hp\\Desktop\\Music_Player\\src\\Images\\pause.png"));
        if (id == TotalSongs) {
            id = 0;
        }
        id = id + 1;
        Song NextSong = db.getSongById(id);
        String path = NextSong.path;
        playSongAndUpdateUI(path);
    }//GEN-LAST:event_NextButtonActionPerformed

    private void repeatButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repeatButtonMouseClicked

    }//GEN-LAST:event_repeatButtonMouseClicked

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

    private void volume_sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volume_sliderStateChanged
        // TODO add your handling code here:
        JSlider source = (JSlider) evt.getSource();
        if (!source.getValueIsAdjusting()) {
            float volume = (int) source.getValue() / 100.0f;
            PausablePlayer.setVolume(volume);
        }
    }//GEN-LAST:event_volume_sliderStateChanged

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
            java.util.logging.Logger.getLogger(Music.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Music.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Music.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Music.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Music().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ControlPanel;
    private javax.swing.JButton HomePageButton;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JScrollPane MusicLoadPanel;
    private javax.swing.JButton MusicPageButton;
    private javax.swing.JSlider MusicSlider;
    private javax.swing.JPanel NavBar;
    private javax.swing.JButton NextButton;
    private javax.swing.JButton PlayButton;
    private javax.swing.JButton PlayListPageButton;
    private javax.swing.JButton PreviousButton;
    private javax.swing.JButton VolumeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton repeatButton;
    private javax.swing.JLabel songNameLabel;
    private javax.swing.JLabel songPicLabel;
    private javax.swing.JSlider volume_slider;
    // End of variables declaration//GEN-END:variables
}
