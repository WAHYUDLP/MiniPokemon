import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.ArrayList;

public class GUIHomeBase {

    JRootPane rootPanel;
    List<PlayerMonster> playerMonsters;
    List<PlayerMonster> chosenMonsters;
    GUIHomeBaseListener listener;

    private EvolveLayout getEnvolveLayout(PlayerMonster playerMonster) {
        return new EvolveLayout(playerMonster, new EvolveListener() {
            @Override
            public void goBack() {
                showWhichMonster(3);
            }
    
            @Override
            public void doEnvolve(EvolveLayout layout, PlayerMonster selectedMonster, int code) {
                Element newElement = switch (code) {
                    case 1 -> Element.FIRE;
                    case 2 -> Element.ICE;
                    case 3 -> Element.WIND;
                    case 4 -> Element.EARTH;
                    case 5 -> Element.WATER;
                    default -> null;
                };
                if (newElement != null) {
                    evolveMonster(layout, selectedMonster, newElement);
                } else {
                    System.out.println("Invalid element choice.");
                    JOptionPane.showMessageDialog(null,
                            "Invalid element choice.",
                            "Invalid",
                            JOptionPane.ERROR_MESSAGE);
                }
    
            }
        });
    }
    

    private BuyItemLayout getBuyItemLayout() {
                                                   // item

        return new BuyItemLayout(new BuyItemListener() {
            @Override
            public void health() {
                if (playerMonsters.stream().anyMatch(monster -> monster.getExpPoint() < 20)) {
                    System.out.println("Not enough EP to buy Health Potion, you must have a minimum of 20 EP.");
                    JOptionPane.showMessageDialog(null,
                            "Not enough EP to buy Health Potion, you must have a minimum of 20 EP.",
                            "Not Enough",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    for (PlayerMonster monster : playerMonsters) {
                        buyItem(monster, new Item("Health Potion", 20, 0));
                    }
                }
            }

            @Override
            public void elemental(int code) {
                System.out.println("Choose an element to apply: 1. Fire, 2. Ice, 3. Wind, 4. Earth, 5. Water");
                Element newElement = switch (code) {
                    case 1 -> Element.FIRE;
                    case  4-> Element.ICE;
                    case 2 -> Element.WIND;
                    case 5-> Element.EARTH;
                    case 3 -> Element.WATER;
                    default -> null;
                };
                if (newElement != null) {
                    if (playerMonsters.stream().anyMatch(monster -> monster.getExpPoint() < 20)) {
                        System.out.println(
                                "Not enough EP to buy Elemental Potion, you must have a minimum of 20 EP.");
                        JOptionPane.showMessageDialog(null,
                                "Not enough EP to buy Elemental Potion, you must have a minimum of 20 EP.",
                                "Not Enough",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        for (PlayerMonster monster : playerMonsters) {
                            buyItem(monster, new Item("Elemental Potion", 0, 0));
                        }
                    }
                } else {
                    System.out.println("Invalid element choice. Potion has no effect.");
                    JOptionPane.showMessageDialog(null,
                            "Invalid element choice. Potion has no effect.",
                            "Invalid",
                            JOptionPane.ERROR_MESSAGE);
                }

            }

            @Override
            public void goBack() {

                rootPanel.setContentPane(getWelcomeBaseLayout().create());
                rootPanel.revalidate();
                rootPanel.repaint();
            } 
        }
        );
    }
    private JPanel createBuyItemPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1)); // Increase grid size to accommodate the back button
    
        JComboBox<String> monsterComboBox = new JComboBox<>();
        for (PlayerMonster monster : playerMonsters) {
            monsterComboBox.addItem(monster.getNama());
        }
        panel.add(new JLabel("Select a monster to buy an item for:"));
        panel.add(monsterComboBox);
    
        JButton healthPotionButton = new JButton("Buy Health Potion (+20 HP)");
        healthPotionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String monsterName = (String) monsterComboBox.getSelectedItem();
                PlayerMonster selectedMonster = null;
                for (PlayerMonster monster : playerMonsters) {
                    if (monster.getNama().equals(monsterName)) {
                        selectedMonster = monster;
                        break;
                    }
                }
                if (selectedMonster != null) {
                    if (selectedMonster.getExpPoint() >= 20) {
                        buyItem(selectedMonster, new Item("Health Potion", 20, 20));
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Not enough EP to buy Health Potion, you must have a minimum of 20 EP.",
                                "Not Enough EP",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(healthPotionButton);
    
        JButton elementalPotionButton = new JButton("Buy Elemental Potion (Change Element)");
        elementalPotionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String monsterName = (String) monsterComboBox.getSelectedItem();
                PlayerMonster selectedMonster = null;
                for (PlayerMonster monster : playerMonsters) {
                    if (monster.getNama().equals(monsterName)) {
                        selectedMonster = monster;
                        break;
                    }
                }
                if (selectedMonster != null) {
                    if (selectedMonster.getExpPoint() >= 20) {
                        Object[] options = { "Fire", "Ice", "Wind", "Earth", "Water" };
                        int choice = JOptionPane.showOptionDialog(null,
                                "Choose an element to apply:",
                                "Elemental Potion",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                        if (choice >= 0 && choice < options.length) {
                            // Panggil method buyItem untuk membeli Elemental Potion
                            buyItem(selectedMonster, new Item("Elemental Potion", 0, 0));
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Not enough EP to buy Elemental Potion, you must have a minimum of 20 EP.",
                                "Not Enough EP",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(elementalPotionButton);
    
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootPanel.setContentPane(getWelcomeBaseLayout().create());
                rootPanel.revalidate();
                rootPanel.repaint();
            }
        });
        panel.add(backButton);
    
        return panel;
    }
    
    private ChooseMonsterLayout getChooseMonsterLayout() {
        return new ChooseMonsterLayout(playerMonsters, chosenMonsters, new ChooseMonsterListener() {
            @Override
            public void goBack() {
                rootPanel.setContentPane(getWelcomeBaseLayout().create());
                rootPanel.revalidate();
                rootPanel.repaint();

            }
        });
    }

    private WelcomeBaseLayout getWelcomeBaseLayout() {
        return new WelcomeBaseLayout(playerMonsters, new WelcomeBaseListener() {
            @Override
            public void levelUp() {
                // TODO Auto-generated method stub
                showWhichMonster(1);
            }

            @Override
            public void healMonster() {
                showWhichMonster(2);

            }

            @Override
            public void envolve() {
                showWhichMonster(3);

            }

            @Override
            public void buyItem2() {
                rootPanel.setContentPane(getBuyItemLayout().create());
                rootPanel.revalidate();
                rootPanel.repaint();
            }

            @Override
            public void chooseMonster() {
                // TODO Auto-generated method stub
                rootPanel.setContentPane(getChooseMonsterLayout().create());
                rootPanel.revalidate();
                rootPanel.repaint();
            }

            @Override
            public void goBack() {
                GameProgress.saveProgress(playerMonsters);
                listener.back2();
            }
        });
    }

    public GUIHomeBase() {

    }

    public GUIHomeBase(List<PlayerMonster> playerMonsters, List<PlayerMonster> chosenMonsters,
            GUIHomeBaseListener listener) {
        rootPanel = new JRootPane();
        this.playerMonsters = playerMonsters;
        this.chosenMonsters = chosenMonsters;
        this.listener = listener;
    }

    public JRootPane GUIEnterHomeBase() {
        System.out.println("Welcome back to Home Base!");

        rootPanel.setContentPane(getWelcomeBaseLayout().create());
        rootPanel.revalidate();
        rootPanel.repaint();
        return rootPanel;
    }

    private void showWhichMonster(int actionCode) {
        String actionName = List.of("Level Up", "Heal", "Evolve").get(actionCode - 1);

        WhichMonsterLayout whichMonsterLayout = new WhichMonsterLayout(playerMonsters, actionName,
                new WhichMonsterListener() {
                    @Override
                    public void primaryAction(PlayerMonster playerMonster) {
                        if (actionCode == 3) {
                            EvolveLayout envolveLayout = getEnvolveLayout(playerMonster);
                            rootPanel.setContentPane(envolveLayout.create());
                            rootPanel.revalidate();
                            rootPanel.repaint();
                            return;
                        }
                        actionMonster(actionCode, playerMonster);
                    }

                    @Override
                    public void goBack() {
                        GUIEnterHomeBase();
                    }
                });
        rootPanel.setContentPane(whichMonsterLayout.create());
        rootPanel.revalidate();
        rootPanel.repaint();
    }

    private void actionMonster(int choice, PlayerMonster selectedMonster) {
        if (selectedMonster == null) {

        }

        switch (choice) {
            case 1:

                checkAndLevelUpMonster(selectedMonster);

                break;
            case 2:
                healMonster(selectedMonster);
                break;
            case 3:
                //
                break;
            case 4:
                // buyItemOption(scanner, playerMonsters);
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");
                break;
        }
    }

    public void checkAndLevelUpMonster(PlayerMonster monster) {
        int wins = monster.getWins();
        int expPoints = monster.getExpPoint();

        if (wins >= 5 && expPoints >= 50) {
            int levelsGained = wins / 5;
            int remainingWins = wins % 5;
            try {
                monster.setLevel(monster.getLevel() + levelsGained);
                monster.setWins(remainingWins); // Sisakan kemenangan yang tidak terpakai
                monster.setExpPoint(expPoints - 50); // Kurangi 50 EP untuk naik level
                monster.setHasEvolved(false);
                System.out.println(monster.getNama() + " has leveled up to " + monster.getLevel() + ".");
                JOptionPane.showMessageDialog(null,
                        monster.getNama() + " has leveled up to " + monster.getLevel() + ".",
                        monster.getNama(),
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (LevelOutOfBoundsException e) {
                System.out.println("Cannot level up: " + e.getMessage());
                JOptionPane.showMessageDialog(null,
                        "Cannot level up: " + e.getMessage(),
                        monster.getNama(),
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {

            System.out
                    .println(monster.getNama() + " needs to win every 5 battles and have at least 50 EP to level up.");
            JOptionPane.showMessageDialog(null,
                    monster.getNama() + " needs to win every 5 battles and have at least 50 EP to level up.",
                    monster.getNama(),
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void evolveMonster(EvolveLayout layout, PlayerMonster monster, Element newElement) {
        if (!monster.getElement().isEmpty() && !monster.hasEvolved()
                && monster.getElement().get(0).getEvolutionOptions().contains(newElement)) {
            monster.setElement(List.of(newElement));
            monster.setHasEvolved(true);
            System.out.println(monster.getNama() + " has evolved into " + newElement.getNama() + ".");
            layout.refreshMonster();
            JOptionPane.showMessageDialog(null,
                    monster.getNama() + " has evolved into " + newElement.getNama() + ".",
                    "Success!",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (monster.hasEvolved()) {
            System.out.println(monster.getNama() + " has already evolved this level.");
            JOptionPane.showMessageDialog(null,
                    monster.getNama() + " has already evolved this level.",
                    "Information",
                    JOptionPane.WARNING_MESSAGE);
        } else if (monster.getElement().isEmpty()) {
            System.out.println("The monster has no initial element.");
            JOptionPane.showMessageDialog(null,
                    "The monster has no initial element.",
                    "Failed!",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Evolution to " + newElement.getNama() + " is not allowed from "
                    + monster.getElement().get(0).getNama() + ".");
            JOptionPane.showMessageDialog(null,
                    "Evolution to " + newElement.getNama() + " is not allowed from "
                            + monster.getElement().get(0).getNama() + ".",
                    "Failed!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void healMonster(Monster monster) {
        monster.setHealthPoint(100);
        System.out.println(monster.getNama() + " has been fully healed.");
        JOptionPane.showMessageDialog(null,
                monster.getNama() + " has been fully healed.",
                monster.getNama(),
                JOptionPane.WARNING_MESSAGE);
    }

    public void buyItem(PlayerMonster monster, Item item) {
        if (monster.getExpPoint() >= 20) {
            monster.setExpPoint(monster.getExpPoint() - 20);
            monster.addItem(item);
            System.out.println("Bought " + item.getNama() + ".");
            JOptionPane.showMessageDialog(null,
                    "Bought " + item.getNama() + "!",
                    "Bought",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("Not enough EP to buy " + item.getNama() + ", you must have minimum 20 EP.");
            JOptionPane.showMessageDialog(null,
                    "Not enough EP to buy " + item.getNama() + ", you must have minimum 20 EP.",
                    "Not Enough",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void enterHomeBase(List<PlayerMonster> playerMonsters, List<PlayerMonster> chosenMonsters) {
        System.out.println("Welcome back to Home Base!");
        Scanner scanner = new Scanner(System.in);

        boolean done = false;

        while (!done) {
            // Display information for all monsters
            for (PlayerMonster monster : playerMonsters) {
                displayMonsterInfo(monster);
            }
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Level Up Monster");
            System.out.println("2. Heal Monster");
            System.out.println("3. Evolve Monster");
            System.out.println("4. Buy Item");
            System.out.println("5. Choose Monsters for Dungeon");
            System.out.println("6. Exit and Save Home Base");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    levelUpMonsterOption(scanner, playerMonsters);
                    break;
                case 2:
                    healMonsterOption(scanner, playerMonsters);
                    break;
                case 3:
                    evolveMonsterOption(scanner, playerMonsters);
                    break;
                case 4:
                    buyItemOption(scanner, playerMonsters);
                    break;
                case 5:
                    chosenMonsters.clear();
                    chosenMonsters.addAll(chooseMonstersForDungeon(playerMonsters));
                    System.out.println("Chosen monsters for dungeon: " + chosenMonsters);
                    break;
                case 6:
                    GameProgress.saveProgress(playerMonsters);
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
        }
    }

    private void levelUpMonsterOption(Scanner scanner, List<PlayerMonster> playerMonsters) {
        PlayerMonster selectedMonster = chooseMonster(scanner, playerMonsters, "Select a monster to level up:");
        if (selectedMonster != null) {
            checkAndLevelUpMonster(selectedMonster);
        }
    }

    private void healMonsterOption(Scanner scanner, List<PlayerMonster> playerMonsters) {
        PlayerMonster selectedMonster = chooseMonster(scanner, playerMonsters, "Select a monster to heal:");
        if (selectedMonster != null) {
            healMonster(selectedMonster);
        }
    }

    private void evolveMonsterOption(Scanner scanner, List<PlayerMonster> playerMonsters) {
        PlayerMonster selectedMonster = chooseMonster(scanner, playerMonsters, "Select a monster to evolve:");
        if (selectedMonster != null) {
            System.out.println("Select an element to evolve to: 1. Fire, 2. Wind, 3. Water, 4. Ice, 5. Earth");
            int elementChoice = scanner.nextInt();
            scanner.nextLine();
            Element newElement = switch (elementChoice) {
                case 1 -> Element.FIRE;
                case 2 -> Element.ICE;
                case 3 -> Element.WIND;
                case 4 -> Element.EARTH;
                case 5 -> Element.WATER;
                default -> null;
            };
            if (newElement != null) {
                // evolveMonster(selectedMonster, newElement);
            } else {
                System.out.println("Invalid element choice.");
            }
        }
    }

    private void buyItemOption(Scanner scanner, List<PlayerMonster> playerMonsters) {
        System.out.println("Select an item to buy:");
        System.out.println("1. Health Potion (+20 HP)");
        System.out.println("2. Elemental Potion (Change Element)");
        System.out.println("3. Exit");
    
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        switch (choice) {
            case 1:
                buyItemForAllMonsters(playerMonsters, new Item("Health Potion", 20, 0));
                break;
            case 2:
                buyItemForAllMonsters(playerMonsters, new Item("Elemental Potion", 0, 0));
                break;
            case 3:
                // Exit
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
    
    private void buyItemForAllMonsters(List<PlayerMonster> playerMonsters, Item item) {
        for (PlayerMonster monster : playerMonsters) {
            buyItem(monster, item);
        }
    }
    
    private List<PlayerMonster> chooseMonstersForDungeon(List<PlayerMonster> monsters) {
        Scanner scanner = new Scanner(System.in);
        List<PlayerMonster> chosenMonsters = new ArrayList<>();

        System.out.println("Choose up to 3 monsters to take into the dungeon (minimum 1):");
        for (int i = 0; i < monsters.size(); i++) {
            System.out.println((i + 1) + ". " + monsters.get(i).getNama());
        }

        int count = 0;
        while (count < 3) {
            System.out.print("Enter the number of the monster to choose (0 to finish): ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                if (count < 1) {
                    System.out.println("You must choose at least one monster.");
                } else {
                    break;
                }
            } else if (choice > 0 && choice <= monsters.size()) {
                PlayerMonster chosenMonster = monsters.get(choice - 1);
                if (!chosenMonsters.contains(chosenMonster)) {
                    chosenMonsters.add(chosenMonster);
                    count++;
                } else {
                    System.out.println("You have already chosen this monster.");
                }
            } else {
                System.out.println("Invalid choice. Please choose again.");
            }
        }

        if (chosenMonsters.isEmpty()) {
            System.out.println("You must choose at least one monster to enter the dungeon.");
        }

        return chosenMonsters;
    }

    private PlayerMonster chooseMonster(Scanner scanner, List<PlayerMonster> playerMonsters, String prompt) {
        System.out.println(prompt);
        for (int i = 0; i < playerMonsters.size(); i++) {
            System.out.println((i + 1) + ". " + playerMonsters.get(i).getNama());
        }

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (choice > 0 && choice <= playerMonsters.size()) {
            return playerMonsters.get(choice - 1);
        } else {
            System.out.println("Invalid choice. Please choose again.");
            return null;
        }
    }

    private void displayMonsterInfo(PlayerMonster monster) {
        System.out.println("\nMonster Information:");
        System.out.println("Name                \t: " + monster.getNama());
        System.out.println("Level               \t: " + monster.getLevel());
        System.out.println("Experience Points   \t: " + monster.getExpPoint());
        System.out.println("Health Points       \t: " + monster.getHealthPoint());
        System.out.println("Wins                \t: " + monster.getWins());
        System.out.println("Element             \t: "
                + (monster.getElement().isEmpty() ? "None" : monster.getElement().get(0).getNama()));
        System.out.println("Evolved             \t: " + (monster.hasEvolved() ? "Yes" : "No"));
    }
}