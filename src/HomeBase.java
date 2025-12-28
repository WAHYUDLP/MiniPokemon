import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class HomeBase {

    public HomeBase() {

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
            } catch (LevelOutOfBoundsException e) {
                System.out.println("Cannot level up: " + e.getMessage());
            }
        } else {
            System.out.println(monster.getNama() + " needs to win every 5 battles and have at least 50 EP to level up.");
        }
    }

    public void evolveMonster(PlayerMonster monster, Element newElement) {
        if (!monster.getElement().isEmpty() && !monster.hasEvolved()
                && monster.getElement().get(0).getEvolutionOptions().contains(newElement)) {
            monster.setElement(List.of(newElement));
            monster.setHasEvolved(true);
            System.out.println(monster.getNama() + " has evolved into " + newElement.getNama() + ".");
        } else if (monster.hasEvolved()) {
            System.out.println(monster.getNama() + " has already evolved this level.");
        } else if (monster.getElement().isEmpty()) {
            System.out.println("The monster has no initial element.");
        } else {
            System.out.println("Evolution to " + newElement.getNama() + " is not allowed from "
                    + monster.getElement().get(0).getNama() + ".");
        }
    }

    public void healMonster(Monster monster) {
        monster.setHealthPoint(100);
        System.out.println(monster.getNama() + " has been fully healed.");
    }

    public void buyItem(PlayerMonster monster, Item item) {
        if (monster.getExpPoint() >= 20) {
            monster.setExpPoint(monster.getExpPoint() - 20);
            monster.addItem(item);
            System.out.println("Bought " + item.getNama() + ".");
        } else {
            System.out.println("Not enough EP to buy " + item.getNama() + ", you must have minimum 20 EP.");
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
                case 3 -> Element.WATER;
                case 2 -> Element.WIND;
                case 5 -> Element.EARTH;
                case 4 -> Element.ICE;
                default -> null;
            };
            if (newElement != null) {
                evolveMonster(selectedMonster, newElement);
            } else {
                System.out.println("Invalid element choice.");
            }
        }
    }

    private void buyItemOption(Scanner scanner, List<PlayerMonster> playerMonsters) {
      
        PlayerMonster selectedMonster = chooseMonster(scanner, playerMonsters, "Select a monster to buy an item for:");

        System.out.println("Available Items: ");
        System.out.println("1. Health Potion (+20 HP)");
        System.out.println("2. Elemental Potion (Change Element)");
        System.out.println("3. Exit");
        
        boolean buyRunning = true;
        while (buyRunning) {
            if (selectedMonster != null) {
                System.out.print("Choose an item to buy: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        buyItem(selectedMonster, new Item("Health Potion", 20, 20));
                        break;
                    case 2:
                        System.out.println("Choose an element to apply: 1. Fire, 2. Ice, 3. Wind, 4. Earth, 5. Water");
                        int elementChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        Element newElement = switch (elementChoice) {
                            case 1 -> Element.FIRE;
                            case 2 -> Element.ICE;
                            case 3 -> Element.WIND;
                            case 4 -> Element.EARTH;
                            case 5 -> Element.WATER;
                            default -> null;
                        };
                        if (newElement != null) {
                            buyItem(selectedMonster, new Item("Elemental Potion", 20, 0));
                        } else {
                            System.out.println("Invalid element choice. Potion has no effect.");
                        }
                        break;
                    case 3:
                        buyRunning = false;
                        break;
                    default:
                        System.out.println("Invalid item choice. No item bought.");
                        break;
                }
            } else {
                buyRunning = false;
            }
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
        System.out.println("Element             \t: " + (monster.getElement().isEmpty() ? "None" : monster.getElement().get(0).getNama()));
        System.out.println("Evolved             \t: " + (monster.hasEvolved() ? "Yes" : "No"));
    }
}