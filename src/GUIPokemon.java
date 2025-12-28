import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.*;
import java.awt.*;

public class GUIPokemon extends JFrame {
    private Player player;

    private List<PlayerMonster> playerMonsters;
    private List<PlayerMonster> chosenMonsters = new ArrayList<>();

    private Clip musicClip;

    public GUIPokemon() {
        setTitle("Pokemon Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Play background music
        try {
            File musicFile = new File("assets/PokemonSong2.wav"); // Ganti dengan path file musik Anda
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        WelcomeLayout welcomeLayout = new WelcomeLayout(new WelcomeListener() {
            @Override
            public void login(String name) {
                initMainGame(name);
            }
        });

        setContentPane(welcomeLayout.create());
    }

    public void showMainMenuLayout() {
    JRootPane rootPane = new JRootPane();

    JPanel mainPanel = new JPanel(new GridBagLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(new ImageIcon("assets/OpeningPokemon.gif").getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 300);
        }
    };

    // Create label for welcome message
    JLabel welcomeLabel = new JLabel("Welcome to Pokemon World!");
    welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
    welcomeLabel.setFont(new Font("ALGERIAN", Font.BOLD, 30));

    // Create label for action message
    JLabel actionLabel = new JLabel("What do you want to do?");
    actionLabel.setHorizontalAlignment(SwingConstants.CENTER);
    actionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
    actionLabel.setForeground(Color.BLACK);

    JButton btnHomebase = new JButton("Homebase");
    JButton btnDungeon = new JButton("Dungeon");
    JButton btnSave = new JButton("Save Progress");
    JButton btnDelAccount = new JButton("Delete Account");
    JButton btnExit = new JButton("Exit");

    btnHomebase.addActionListener(e -> {
        GUIHomeBase guiHomeBase = new GUIHomeBase(playerMonsters, chosenMonsters, new GUIHomeBaseListener() {
            public void back2() {
                showMainMenuLayout();
            }
        });
        setContentPane(guiHomeBase.GUIEnterHomeBase());
        revalidate();
        repaint();
    });
    btnDungeon.addActionListener(e -> {
        GUIDungeon guiDungeon = new GUIDungeon(this, new GUIBattleArena(), new DungeonListener() {
            @Override
            public void startBattle(GUIBattleArena battleArena) {
                // TODO Auto-generated method stub

            }

            @Override
            public void goBack() {
                // TODO Auto-generated method stub
                showMainMenuLayout();
            }
        });
        setContentPane(guiDungeon.GUIExplore(chosenMonsters));
        // setContentPane(new GUIDungeon(this, new
        // GUIBattleArena()).GUIExplore(playerMonsters));
        revalidate();
        repaint();
    });

    btnSave.addActionListener(e -> {
            GameProgress.saveProgress(playerMonsters);
            JOptionPane.showMessageDialog(null,
            "Saved Successfully!",
            "Success!",
            JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Exiting game...");
            dispose();
    });

    btnDelAccount.addActionListener(e -> {
            GameProgress.deleteProgress();
            JOptionPane.showMessageDialog(null,
            "Your save has been deleted!",
            "Success!",
            JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Exiting game...");
            dispose();
    });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        mainPanel.add(welcomeLabel, gbc);
        mainPanel.add(actionLabel, gbc);
        mainPanel.add(btnHomebase, gbc);
        mainPanel.add(btnDungeon, gbc);
        mainPanel.add(btnSave, gbc);
        mainPanel.add(btnDelAccount, gbc);

        rootPane.setContentPane(mainPanel);

        setContentPane(rootPane);
        revalidate();
        repaint();
    }


    public void initMainGame(String name) {
        String playerName = name;
        if (playerName != null && !playerName.trim().isEmpty()) {
            player = new Player(playerName);
        } else {
            player = new Player("Ash"); // Default name if no input
        }

        try {
            playerMonsters = GameProgress.loadProgress();

            if (playerMonsters == null || playerMonsters.size() < 3) {
                // If no previous game progress is found or fewer than 3 monsters, create new
                // player and player monsters

                Player player = new Player(playerName);
                playerMonsters = List.of(
                        new PlayerMonster("Pikachu", 1, List.of(Element.WIND), player,
                                "assets/monsters-kita/PikachuBertarung.png"),
                        new PlayerMonster("Charmander", 1, List.of(Element.FIRE), player,
                                "assets/monsters-kita/CharmanderBertarung.png"),
                        new PlayerMonster("Squirtle", 1, List.of(Element.WATER), player,
                                "assets/monsters-kita/SquirtleBertarung.png"));
            }

        } catch (LevelOutOfBoundsException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        showMainMenuLayout();
    }

    public static void main(String[] args) throws Exception {
        UIManager.put("Button.font", FontUtils.getPixelBoldFont().deriveFont(16f));
        UIManager.put("Label.font", FontUtils.getPixelBoldFont().deriveFont(14f));
        SwingUtilities.invokeLater(() -> new GUIPokemon().setVisible(true));
    }
}
