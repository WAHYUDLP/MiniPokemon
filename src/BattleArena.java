import java.util.Scanner;
import java.util.List;

public class BattleArena {
    private boolean healthItemUsed = false;

    BattleArena() {

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
                    useItem(scanner, currentMonster);
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

    private void useItem(Scanner scanner, PlayerMonster playerMonster) {
        System.out.println("Available Items: 1. Health Potion (+20 HP), 2. Elemental Potion (Change Element)");
        System.out.println("Select an item to use:");
        int itemChoice = scanner.nextInt();
        scanner.nextLine();

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
                    int elementChoice = scanner.nextInt();
                    scanner.nextLine();
                    applyElementalPotion(playerMonster, elementChoice);
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
        } else {
            System.out.println("Invalid element choice. Potion has no effect.");
        }
    }
}

