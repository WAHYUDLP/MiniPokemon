import java.util.ArrayList;
import java.util.List;

public class Player {
    String nama;
    List<Monster> monsters;
    HomeBase homeBase;
    Dungeon currentDungeon;
    BattleArena battleArena;
 
    public Player(String nama) {
        this.nama = nama;
        this.monsters = new ArrayList<>();
        this.homeBase = new HomeBase();
        this.battleArena = new BattleArena();
        this.currentDungeon = new Dungeon(battleArena);
    }

    // Getter for name 
    public String getNama() {
        return nama;
    }
    public void enterDungeon() {
        System.out.println(nama + " has entered a dungeon.");
        if (monsters.isEmpty()) {
            System.out.println("No monsters to enter the dungeon.");
            return;
        }
    }

 public HomeBase getHomeBase() {
        return homeBase;
    }
 
    public void battleMonster(Monster monster) {
        if (!monsters.isEmpty()) {
            List<PlayerMonster> myMonster = (List<PlayerMonster>) monsters.get(0); // Contoh sederhana, memilih monster pertama
            battleArena.startBattle(myMonster, monster);
        } else {
            System.out.println("No monster is available for battle.");
        }
    }

    public void addMonster(Monster monster) {
        if (monsters.size() < 3) {
            monsters.add(monster);
            System.out.println("Added " + monster.getNama() + " to " + nama + "'s team.");
        } else {
            System.out.println("Cannot add more monsters, team is full.");
        }
    }

    public void useItem(Item elementalPotion, Monster monster) {
        // TODO Auto-generated method stub
    }
}