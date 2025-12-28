import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class GUIDungeon {
    private List<Monster> monsters;
    private Random random;
    private GUIBattleArena battleArena;
    private JRootPane rootPanel;
    private DungeonExploreLayout dungeonExploreLayout;
    private JFrame frame;
    private DungeonListener listener;

    public GUIDungeon(GUIBattleArena battleArena) {
        rootPanel = new JRootPane();
        this.monsters = new ArrayList<>();
        this.random = new Random();
        this.battleArena = battleArena;
        generateMonster();
    }

    public GUIDungeon(JFrame frame, GUIBattleArena battleArena, DungeonListener listener) {
        rootPanel = new JRootPane();
        this.monsters = new ArrayList<>();
        this.random = new Random();
        this.battleArena = battleArena;
        this.frame = frame;
        this.listener = listener;
        generateMonster();
    }

    public JRootPane GUIExplore(List<PlayerMonster> playerMonsters) {
        dungeonExploreLayout = new DungeonExploreLayout(new DungeonExploreListener() {
            @Override
            public void battle(Monster wildMonster) {
                if (monsters.isEmpty()) {
                    System.out.println("No monsters left to encounter in the dungeon.");
                    return;
                }

                System.out.println("A wild " + wildMonster.getNama() + " appears!");
                GUIBattleArena battleArena = new GUIBattleArena(playerMonsters, wildMonster, new BattleArenaListener() {
                    @Override
                    public void goBack() {
                        listener.goBack();
                    }
                });
                frame.setContentPane(battleArena.startBattle2());
                frame.revalidate();
                frame.repaint();

            }

            @Override
            public Monster getMonsterRnd() {
                return getMonsterRandom();
            }

            @Override
            public void back() {
                listener.goBack();

            }
        });
        rootPanel.setContentPane(dungeonExploreLayout.create());
        rootPanel.revalidate();
        rootPanel.repaint();
        return rootPanel;
    }

    private void generateMonster() {
        monsters.add(new WildMonster("Aqua Spirit", 4, List.of(new Element("WATER")), true,
                "assets/monsters/AquaS - Air.png"));
        monsters.add(new WildMonster("Drake", 5, List.of(new Element("FIRE")), false, "assets/monsters/drake-api.png"));
        monsters.add(new WildMonster("Terra Beast", 3, List.of(new Element("EARTH")), false,
                "assets/monsters/Terra Beast - Tanah.png"));
        monsters.add(new WildMonster("Flame Phoenix", 6, List.of(new Element("FIRE")), true,
                "assets/monsters/Flame Phoenix - api.png"));
        monsters.add(new WildMonster("Frost Wyvern", 7, List.of(new Element("ICE")), true,
                "assets/monsters/Frost Wyvern - Es.png"));
        monsters.add(new WildMonster("Thunderbird", 5, List.of(new Element("WIND")), true,
                "assets/monsters/Thunderbird - Angin.png"));
        monsters.add(new WildMonster("Rock Golem", 4, List.of(new Element("EARTH")), false,
                "assets/monsters/Rock Golem - Tanah.png"));
        monsters.add(new WildMonster("Ocean Leviathan", 8, List.of(new Element("WATER")), true,
                "assets/monsters/Ocean Leviathan - Air.png"));
        monsters.add(new WildMonster("Blizzard Yeti", 7, List.of(new Element("ICE")), true,
                "assets/monsters/Blizzard Yeti -Es.png"));
        monsters.add(new WildMonster("Storm Elemental", 6, List.of(new Element("WIND")), true,
                "assets/monsters/Storm Elemental - Angin.png"));
    }

    public void explore(List<PlayerMonster> playerMonsters) {
        if (monsters.isEmpty()) {
            System.out.println("No monsters left to encounter in the dungeon.");
            return;
        }

        int encounterChance = random.nextInt(100); // Random chance generation
        if (encounterChance < 50) { // 50% chance to encounter a monster
            Monster wildMonster = getMonsterRandom();
            System.out.println("A wild " + wildMonster.getNama() + " appears!");
            battleArena.startBattle(playerMonsters, wildMonster);
        } else {
            System.out.println("No monsters encountered this time.");
        }
    }

    private Monster getMonsterRandom() {
        if (!monsters.isEmpty()) {
            int index = random.nextInt(monsters.size());
            return monsters.remove(index);
        }
        return null;
    }
}
