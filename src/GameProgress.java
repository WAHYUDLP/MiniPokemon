import java.io.*;
import java.util.*;

public class GameProgress {
    private static final String SAVE_FILE_PATH = "game_progress.txt";

    public GameProgress(){

    }
    
    public static void saveProgress(List<PlayerMonster> playerMonsters) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE_PATH))) {
            writer.write("========== GAME PROGRESS ==========\n");
            for (PlayerMonster playerMonster : playerMonsters) {
                writer.write("Name              \t: " + playerMonster.getNama() + "\n");
                writer.write("Level             \t: " + playerMonster.getLevel() + "\n");
                writer.write("Experience Points \t: " + playerMonster.getExpPoint() + "\n");
                writer.write("Health Points     \t: " + playerMonster.getHealthPoint() + "\n");
                writer.write("Wins              \t: " + playerMonster.getWins() + "\n");

                // Save the element
                if (playerMonster.getElement().isEmpty() || playerMonster.getElement().get(0) == null) {
                    writer.write("Element        \t: None\n");
                } else {
                    writer.write("Element            \t: " + playerMonster.getElement().get(0).getNama() + "\n");
                }

                // Save the evolved status
                writer.write("Evolved           \t: " + playerMonster.hasEvolved() + "\n");
                writer.write("===================================\n");
            }
            System.out.println("Game progress saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving game progress: " + e.getMessage());
        }
    }

    public static List<PlayerMonster> loadProgress() throws LevelOutOfBoundsException {
        File saveFile = new File(SAVE_FILE_PATH);
        if (!saveFile.exists()) {
            System.out.println("No saved game progress found.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE_PATH))) {
            List<PlayerMonster> playerMonsters = new ArrayList<>();
            String line;
            String name = null;
            int level = 0;
            int expPoints = 0;
            int healthPoints = 0;
            int wins = 0;
            String elementName = null;
            boolean evolved = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name")) {
                    name = line.split(":")[1].trim();
                } else if (line.startsWith("Level")) {
                    level = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Experience Points")) {
                    expPoints = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Health Points")) {
                    healthPoints = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Wins")) {
                    wins = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Element")) {
                    elementName = line.split(":")[1].trim();
                } else if (line.startsWith("Evolved")) {
                    evolved = Boolean.parseBoolean(line.split(":")[1].trim());
                } else if (line.startsWith("===")) {
                    if (name != null) {
                        Element element = null;
                        if (elementName != null && !elementName.equals("None")) {
                            switch (elementName.toUpperCase()) {
                                case "FIRE":
                                    element = Element.FIRE;
                                    break;
                                case "WATER":
                                    element = Element.WATER;
                                    break;
                                case "EARTH":
                                    element = Element.EARTH;
                                    break;
                                case "WIND":
                                    element = Element.WIND;
                                    break;
                                case "ICE":
                                    element = Element.ICE;
                                    break;
                                default:
                                    element = new Element(elementName); // Initialize with new Element
                            }
                        }

                        List<Element> elements = element == null ? new ArrayList<>() : List.of(element);

                        Player player = new Player("LoadedPlayer"); // Set player with default or required name

                        Map<String, String> map = new HashMap<>();
                        map.put("Pikachu", "assets/monsters-kita/PikachuBertarung.png");
                        map.put("Charmander", "assets/monsters-kita/CharmanderBertarung.png");
                        map.put("Squirtle", "assets/monsters-kita/SquirtleBertarung.png");
                        String imgPath = map.get(name);

                        PlayerMonster playerMonster = new PlayerMonster(name, level, elements, player, imgPath);
                        playerMonster.setExpPoint(expPoints);
                        playerMonster.setHealthPoint(healthPoints);
                        playerMonster.setWins(wins);
                        playerMonster.setHasEvolved(evolved);

                        playerMonsters.add(playerMonster);
                    }

                    // Reset attributes for next monster
                    name = null;
                    level = 0;
                    expPoints = 0;
                    healthPoints = 0;
                    wins = 0;
                    elementName = null;
                    evolved = false;
                }
            }

            System.out.println("Game progress loaded successfully.");
            return playerMonsters;
        } catch (IOException e) {
            System.out.println("Error loading game progress: " + e.getMessage());
            return null;
        }
    }

    public static void deleteProgress() {
        File saveFile = new File(SAVE_FILE_PATH);
        if (saveFile.exists()) {
            if (saveFile.delete()) {
                System.out.println("Game progress deleted successfully.");
            } else {
                System.out.println("Failed to delete game progress.");
            }
        } else {
            System.out.println("No saved game progress found to delete.");
        }
    }
}

