import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dungeon {
    private List<Monster> monsters;
    private Random random;
    private BattleArena battleArena;

    public Dungeon(BattleArena battleArena) {
        this.monsters = new ArrayList<>();
        this.random = new Random();
        this.battleArena = battleArena;
        generateMonster();
    }

    private void generateMonster() {
        monsters.add(new WildMonster("Aqua Spirit", 4, List.of(new Element("WATER")), true));
        monsters.add(new WildMonster("Drake", 5, List.of(new Element("FIRE")), false));
        monsters.add(new WildMonster("Terra Beast", 3, List.of(new Element("EARTH")), false));
        monsters.add(new WildMonster("Flame Phoenix", 6, List.of(new Element("FIRE")), true));
        monsters.add(new WildMonster("Frost Wyvern", 7, List.of(new Element("ICE")), true));
        monsters.add(new WildMonster("Thunderbird", 5, List.of(new Element("WIND")), true));
        monsters.add(new WildMonster("Rock Golem", 4, List.of(new Element("EARTH")), false));
        monsters.add(new WildMonster("Ocean Leviathan", 8, List.of(new Element("WATER")), true));
        monsters.add(new WildMonster("Blizzard Yeti", 7, List.of(new Element("ICE")), true));
        monsters.add(new WildMonster("Storm Elemental", 6, List.of(new Element("WIND")), true));
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
