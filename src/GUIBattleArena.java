import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.io.File;

public class GUIBattleArena {
    private boolean healthItemUsed = false;
    private JRootPane rootPanel;
    // private JFrame root;
    private List<PlayerMonster> playerMonsters;
    private Monster wildMonster;
    private BattleArenaListener listener;

    public GUIBattleArena() {

    }

    public GUIBattleArena(List<PlayerMonster> playerMonsters, Monster wildMonster) {
        rootPanel = new JRootPane();
        this.playerMonsters = playerMonsters;
        this.wildMonster = wildMonster;
    }

    public GUIBattleArena(List<PlayerMonster> playerMonsters, Monster wildMonster, BattleArenaListener listener) {
        rootPanel = new JRootPane();
        this.playerMonsters = playerMonsters;
        this.wildMonster = wildMonster;
        this.listener = listener;
    }

    public JRootPane getRoot() {
        return rootPanel;
    }

    public JRootPane startBattle2() {

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon("assets/BattleArena.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 300);
            }
        };
        JPanel panel2 = new JPanel();
        JLabel labelWelcome = new JLabel("Welcome to the Battle Arena!");
        JLabel labelDesc = new JLabel("Battle started between your monsters and "
                + wildMonster.getNama() + ".");

        JButton btnBack = new JButton("Back");

        labelWelcome.setForeground(Color.YELLOW);
        labelWelcome.setFont(FontUtils.getPixelBoldFont().deriveFont(20f));
        labelWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        labelDesc.setForeground(Color.YELLOW);
        labelDesc.setHorizontalAlignment(SwingConstants.CENTER);
        panel2.setOpaque(false);

        btnBack.addActionListener(e -> {
            listener.goBack();
        });

        for (PlayerMonster playerMonster : playerMonsters) {
            JPanel panelMonster = new JPanel();

            ImageIcon icon = new ImageIcon(playerMonster.imgPath);
            ImageIcon icon2 = new ImageIcon(icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));

            JLabel labelImg = new JLabel(icon2);
            JTextArea teaInfo = new JTextArea(
                    "Name: " + playerMonster.getNama() +
                            "\n Level: " + playerMonster.getLevel() +
                            "\n Exp Point: " + playerMonster.getExpPoint() +
                            "\n HP Point: " + playerMonster.getHealthPoint() +
                            "\n Wins: " + playerMonster.getWins() +
                            "\n Elements: "
                            + (playerMonster.getElement().isEmpty() ? "None"
                                    : playerMonster.getElement().get(0).getNama())
                            +
                            "\n Envolved: " + (playerMonster.hasEvolved() ? "Yes" : "No"));
            JButton btn = new JButton("Select");

            panelMonster.setLayout(new BoxLayout(panelMonster, BoxLayout.Y_AXIS));
            panelMonster.setOpaque(false);

            labelImg.setAlignmentX(Component.CENTER_ALIGNMENT);

            btn.setOpaque(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);

            btn.setFocusable(false);
            btn.addActionListener(e -> {
                attackBegin(playerMonster, wildMonster);
                System.out.println("Clikced");
            });
            panelMonster.add(labelImg);
            panelMonster.add(teaInfo);
            panelMonster.add(btn);
            panel2.add(panelMonster);
        }
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        // gbc.weightx = 2;
        panel.add(labelWelcome, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelDesc, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipady = 32;
        panel.add(panel2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(btnBack, gbc);
        rootPanel.setContentPane(panel);
        rootPanel.revalidate();
        rootPanel.repaint();
        return rootPanel;
    }

    public void attackBegin(PlayerMonster currentMonster, Monster wildMonster) {
        BeginAttackLayout layout = new BeginAttackLayout(currentMonster, wildMonster, new BeginAttackListener() {
            @Override
            public String attack(BeginAttackLayout layout, int action) {
                return GUIAttack(action, layout, currentMonster, wildMonster);
            }

            @Override
            public void heal(BeginAttackLayout layout) {
                Item healthPotion = new Item("Health Potion", 20, 0);
                if (currentMonster.hasItem(healthPotion)) {
                    currentMonster.setHealthPoint(currentMonster.getHealthPoint() + 20); // Assuming +20 HP for health
                                                                                         // potion
                    currentMonster.useItem(healthPotion);
                    layout.showInfo(currentMonster.getNama() + " used Health Potion. Gained 20 HP.");
                    System.out.println(currentMonster.getNama() + " used Health Potion. Gained 20 HP.");
                    layout.refreshStatus();
                } else {
                    System.out.println("Health Potion not purchased.");
                    layout.showInfo("Health Potion not purchased.");
                }
            }

            @Override
            public void elemntalApply(BeginAttackLayout layout, int code) {
                Item elementalPotion = new Item("Elemental Potion", 0, 0);
                if (currentMonster.hasItem(elementalPotion)) {
                    // System.out.println("Select an element: 1. Fire, 2. Ice, 3. Wind, 4. Earth, 5.
                    // Water");
                    applyElementalPotion(currentMonster, code);
                    currentMonster.useItem(elementalPotion);
                    layout.refreshStatus();
                } else {
                    System.out.println("Elemental Potion not purchased.");
                    layout.showInfo("Elemental Potion not purchased.");
                }
            }

            @Override
            public void backToDungeon() {
                listener.goBack();
            }
        });
        rootPanel.setContentPane(layout.create());
        rootPanel.revalidate();
        rootPanel.repaint();
    }

    private String GUIAttack(int action, BeginAttackLayout layout, PlayerMonster currentMonster, Monster wildMonster) {
        System.out.println("Welcome to the Battle Arena!");
        System.out.println("Battle started between your monsters and " + wildMonster.getNama() + ".");

        if (currentMonster.getHealthPoint() <= 0) {
            System.out.println(currentMonster.getNama() + " has fainted!");
            return "lose";
        }

        switch (action) {
            case 1:
                currentMonster.basicAttack(wildMonster);
                break;
            case 2:
                currentMonster.specialAttack(wildMonster);
                break;
            case 3:
                currentMonster.elementalAttack(wildMonster);
                break;
            case 4:
                // useItem(scanner, currentMonster);
                break;
            case 5:
                if (currentMonster.flee()) {
                    // battleEnded = true;
                    layout.flee();
                    return "flee";
                } else {
                    layout.fleeInvalid();
                    System.out.println("Failed to flee!");
                }
            default:
                System.out.println("Invalid choice! Please choose again.");
        }

        // Check if wild monster fainted
        if (wildMonster.isFainted()) {
            System.out.println("You defeated the wild monster! Gaining experience...");
            for (PlayerMonster monster : playerMonsters) {
                monster.gainExperiencePoints(35);
                monster.incrementWins(); // Increment wins after winning a battle
            }
            layout.win();
            return "win";
        } else {
            // Wild monster attacks back
            wildMonster.performRandomAttack(currentMonster);
            if (currentMonster.isFainted()) {
                System.out.println(currentMonster.getNama() + " has fainted!");
                // End battle if current monster faints
                layout.lose();
                // battleEnded = true;
            }
        }

        // Check if all player monsters have fainted
        if (playerMonsters.stream().allMatch(PlayerMonster::isFainted)) {
            System.out.println("All your monsters have fainted. You lost the battle.");
            layout.lose();
            return "lost";
        }
        healthItemUsed = false;
        return "";
    }

    public void startBattle(List<PlayerMonster> playerMonsters, Monster wildMonster) {
        System.out.println("Welcome to the Battle Arena!");
        System.out.println("Battle started between your monsters and " + wildMonster.getNama() + ".");

        Scanner scanner = new Scanner(System.in);
        boolean battleEnded = false;

        PlayerMonster currentMonster = chooseMonsterForBattle(playerMonsters, scanner);

        if (currentMonster.getHealthPoint() <= 0) {
            System.out.println(currentMonster.getNama() + " has fainted!");
            return;
        }

        while (!battleEnded) {
            System.out.println("\n" + currentMonster.getNama() + "'s turn! Choose your action:");
            System.out.println("1. Basic Attack");
            System.out.println("2. Special Attack");
            System.out.println("3. Elemental Attack");
            System.out.println("4. Use Item");
            System.out.println("5. Flee");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    currentMonster.basicAttack(wildMonster);
                    break;
                case 2:
                    currentMonster.specialAttack(wildMonster);
                    break;
                case 3:
                    currentMonster.elementalAttack(wildMonster);
                    break;
                case 4:
                    // useItem(scanner, currentMonster);
                    break;
                case 5:
                    if (currentMonster.flee()) {
                        battleEnded = true;
                    } else {
                        System.out.println("Failed to flee!");
                    }
                    continue;
                default:
                    System.out.println("Invalid choice! Please choose again.");
                    continue;
            }

            // Check if wild monster fainted
            if (wildMonster.isFainted()) {
                System.out.println("You defeated the wild monster! Gaining experience...");
                for (PlayerMonster monster : playerMonsters) {
                    monster.gainExperiencePoints(35);
                    monster.incrementWins(); // Increment wins after winning a battle
                }
                battleEnded = true;
            } else {
                // Wild monster attacks back
                wildMonster.performRandomAttack(currentMonster);
                if (currentMonster.isFainted()) {
                    System.out.println(currentMonster.getNama() + " has fainted!");
                    // End battle if current monster faints
                    battleEnded = true;
                }
            }

            // Check if all player monsters have fainted
            if (playerMonsters.stream().allMatch(PlayerMonster::isFainted)) {
                System.out.println("All your monsters have fainted. You lost the battle.");
                battleEnded = true;
            }
        }

        healthItemUsed = false; // Reset the flag for the next battle
    }

    private PlayerMonster chooseMonsterForBattle(List<PlayerMonster> playerMonsters, Scanner scanner) {
        PlayerMonster chosenMonster = null;
        while (chosenMonster == null) {
            System.out.println("\nChoose a monster to attack with for this battle:");
            for (int i = 0; i < playerMonsters.size(); i++) {
                System.out.println((i + 1) + ". " + playerMonsters.get(i).getNama() + " ("
                        + playerMonsters.get(i).getHealthPoint() + " HP)");
            }
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice > 0 && choice <= playerMonsters.size()) {
                chosenMonster = playerMonsters.get(choice - 1);
            } else {
                System.out.println("Invalid choice! Please choose a valid monster.");
            }
        }
        return chosenMonster;
    }

    private void useItem(int itemChoice, PlayerMonster playerMonster) {
        System.out.println("Available Items: 1. Health Potion (+20 HP), 2. Elemental Potion (Change Element)");
        System.out.println("Select an item to use:");
        // int itemChoice = scanner.nextInt();
        // scanner.nextLine();

        switch (itemChoice) {
            case 1: // Health Potion
                Item healthPotion = new Item("Health Potion", 20, 0);
                if (playerMonster.hasItem(healthPotion)) {
                    playerMonster.setHealthPoint(playerMonster.getHealthPoint() + 20); // Assuming +20 HP for health
                                                                                       // potion
                    playerMonster.useItem(healthPotion);
                    System.out.println(playerMonster.getNama() + " used Health Potion. Gained 20 HP.");
                } else {
                    System.out.println("Health Potion not purchased.");
                }
                break;
            case 2: // Elemental Potion
                Item elementalPotion = new Item("Elemental Potion", 0, 0);
                if (playerMonster.hasItem(elementalPotion)) {
                    System.out.println("Select an element: 1. Fire, 2. Ice, 3. Wind, 4. Earth, 5. Water");
                    // int elementChoice = scanner.nextInt();
                    // scanner.nextLine();
                    // applyElementalPotion(playerMonster, elementChoice);
                    playerMonster.useItem(elementalPotion);
                } else {
                    System.out.println("Elemental Potion not purchased.");
                }
                break;
            default:
                System.out.println("Invalid item selection.");
                break;
        }
    }

    private void applyElementalPotion(PlayerMonster playerMonster, int elementChoice) {
        Element newElement = switch (elementChoice) {
            case 1 -> Element.FIRE;
            case 5 -> Element.WATER;
            case 3 -> Element.WIND;
            case 4 -> Element.EARTH;
            case 2 -> Element.ICE;
            default -> null;
        };
        if (newElement != null) {
            playerMonster.setElement(List.of(newElement));

            System.out.println(playerMonster.getNama() + " changed element to " + newElement.getNama() + ".");
            JOptionPane.showMessageDialog(null,
                    playerMonster.getNama() + " changed element to " + newElement.getNama() + "!",
                    "Yeayyy!",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("Invalid element choice. Potion has no effect.");
            JOptionPane.showMessageDialog(null,
            "Invalid element choice. Potion has no effect.",
                    "Invalid!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
