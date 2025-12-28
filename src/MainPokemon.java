import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Scanner;
 
public class MainPokemon {
    private static Scanner scanner = new Scanner(System.in);
    private static List<PlayerMonster> chosenMonsters = new ArrayList<>();

    public static void main(String[] args) throws LevelOutOfBoundsException {
        try {
            System.out.println("Welcome to Pokemon World!");

            List<PlayerMonster> playerMonsters = GameProgress.loadProgress();
            if (playerMonsters == null || playerMonsters.size() < 3) {
               
                System.out.print("Enter your player name: ");
                String playerName = scanner.nextLine();

                Player player = new Player(playerName);
                System.out.println("Player " + playerName + " starts the adventure!");

                playerMonsters = List.of(
                        new PlayerMonster("Pikachu", 1, List.of(Element.WIND), player),
                        new PlayerMonster("Charmander", 1, List.of(Element.FIRE), player),
                        new PlayerMonster("Squirtle", 1, List.of(Element.WATER), player)
                );
            }

            HomeBase homeBase = new HomeBase();
            BattleArena battleArena = new BattleArena(); 
            Dungeon dungeon = new Dungeon(battleArena);

            boolean gameRunning = true;
            while (gameRunning) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("1. Enter Home Base");
                System.out.println("2. Enter Dungeon");
                System.out.println("3. Exit Game");

                try {
                    int choice = getNextInt();
                    scanner.nextLine(); 
                    switch (choice) {
                        case 1:
                            homeBase.enterHomeBase(playerMonsters, chosenMonsters);
                            break;
                        case 2:
                            if (chosenMonsters.isEmpty()) {
                                System.out.println("You must select at least one monster from Home Base to enter the dungeon.");
                            } else {
                                System.out.println("Welcome to Dungeon");
                                dungeon.explore(chosenMonsters);
                            }
                            break;
                        case 3:
                            System.out.println("Do you want to save or delete progress?");
                            System.out.println("1. Save Progress");
                            System.out.println("2. Delete Account");
                            System.out.println("3. Cancel");

                            int exitChoice = getNextInt();
                            scanner.nextLine(); 

                            if (exitChoice == 1) {
                                GameProgress.saveProgress(playerMonsters);
                                System.out.println("Exiting game...");
                                gameRunning = false;
                            } else if (exitChoice == 2) {
                                GameProgress.deleteProgress();
                                System.out.println("Exiting game...");
                                gameRunning = false;
                            } else if (exitChoice == 3) {
                                System.out.println("Canceling exit...");
                            } else {
                                System.out.println("Invalid choice. Please choose again.");
                            }
                            break;
                        default:
                            System.out.println("Invalid choice. Please choose again.");
                            break;
                    }
                } catch (GameActionException e) {
                    System.err.println(e.getMessage());
                }
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static int getNextInt() throws GameActionException {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine(); 
            throw new GameActionException("Invalid input. Please enter a valid number.");
        } catch (NoSuchElementException e) {
            throw new GameActionException("Input stream closed unexpectedly.");
        }
    }

   
}


class GameActionException extends Exception {
    public GameActionException(String message) {
        super(message);
    }
}

class LevelOutOfBoundsException extends Exception {
    public LevelOutOfBoundsException(String message) {
        super(message);
    }
}
