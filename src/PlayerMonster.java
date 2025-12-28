import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerMonster extends Monster {
    private static final long serialVersionUID = 1L;

    private Player owner;
    private int wins;
    private boolean hasEvolved;
    private List<Item> boughtItems; // Daftar item yang dibeli
    private List<Element> elements;

    public PlayerMonster() {
        super("", 1, new ArrayList<>()); 
        this.elements = new ArrayList<>();
        this.boughtItems = new ArrayList<>(); 
    }

  
    
    public PlayerMonster(String nama, int level, List<Element> elements, Player owner) {
        super(nama, level, elements);
        this.owner = owner;
        this.wins = 0;
        this.hasEvolved = false;
        this.elements = elements != null ? elements : new ArrayList<>();
        this.boughtItems = new ArrayList<>(); // Inisialisasi daftar boughtItems
    }

    public PlayerMonster(String nama, int level, List<Element> elements, Player owner, String imgPath) {
        super(nama, level, elements, imgPath);
        this.owner = owner;
        this.wins = 0;
        this.hasEvolved = false;
        this.elements = elements != null ? elements : new ArrayList<>();
        this.boughtItems = new ArrayList<>(); // Inisialisasi daftar boughtItems
    }

    public void addItem(Item item) {
        this.boughtItems.add(item);
    }

    public boolean hasItem(Item item) {
        for (Item invItem : boughtItems) {
            if (invItem.getNama().equals(item.getNama())) {
                return true;
            }
        }
        return false;
    }

    public void useItem(Item item) {
        for (int i = 0; i < boughtItems.size(); i++) {
            if (boughtItems.get(i).getNama().equals(item.getNama())) {
                boughtItems.remove(i);
                break;
            }
        }
    }

    public List<Item> getBoughtItems() {
        return boughtItems;
    }

    public String getNama() {
        return super.getNama();
    }

    public void setNama(String nama) {
        super.setNama(nama);
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public int getWins() {
        return wins;
    }

    public void incrementWins() {
        this.wins++;
    }

    public boolean hasEvolved() {
        return hasEvolved;
    }

    public void setElement(List<Element> elements) {
        this.elements = elements != null ? elements : new ArrayList<>();
    }

    public List<Element> getElement() {
        return elements != null ? elements : new ArrayList<>();
    }

    public void setHasEvolved(boolean hasEvolved) {
        this.hasEvolved = hasEvolved;
    }

    @Override
    public void basicAttack(Monster target) {
        int damage = level * 12; 
    
        target.healthPoint -= damage;
    
        System.out.println("\n"+nama + " performed a basic attack on " + target.nama + "!");
        System.out.println(target.nama + "'s HP decreased by " + damage + ". " + target.nama + " has " + target.healthPoint + " HP.");
        System.out.println(nama + " has " + healthPoint + " HP.");
    }
    
    @Override
    public void specialAttack(Monster target) {
        int actualDamage = level * 15; 
    
        boolean miss = Math.random() < 0.1; 
    
        if (miss) {
            System.out.println(nama + "'s special attack missed!");
        } else {
            target.healthPoint -= actualDamage;
            int sacrificeHP = (int) (healthPoint * 0.2);
            healthPoint -= sacrificeHP;
    
            System.out.println("\n"+nama + " performed a special attack on " + target.nama + "!");
            System.out.println(target.nama + "'s HP decreased by " + actualDamage + ". " + target.nama + " has " + target.healthPoint + " HP.");
            System.out.println(nama + " sacrificed " + sacrificeHP + " HP and now has " + healthPoint + " HP.");
        }
    }
    
    @Override
    public void elementalAttack(Monster target) {
        if (elements != null && !elements.isEmpty() && target.getElement() != null && !target.getElement().isEmpty()) {
            Element playerElement = elements.get(0); // Use current element of PlayerMonster
    
            int effectiveness = determineElementalEffectiveness(playerElement, target.getElement().get(0));
            int baseDamage = level * 20; // Example: elemental attack reduces 20 HP per level
            int actualDamage = baseDamage * effectiveness;
    
            target.healthPoint -= actualDamage;
            String attackMessage = getElementalAttackMessage(playerElement);
            System.out.println();

            System.out.println(attackMessage);
    
            System.out.println(nama + " performed an elemental attack on " + target.nama + "!");
            System.out.println(target.nama + "'s HP decreased by " + actualDamage + ". " + target.nama + " has " + target.healthPoint + " HP.");
            System.out.println(nama + " has " + healthPoint + " HP .");
        } else {
            System.out.println("Cannot perform elemental attack. Invalid elements.");
        }
    }
    
    
    private int determineElementalEffectiveness(Element playerElement, Element targetElement) {
        switch (playerElement.getNama().toUpperCase()) {
            case "FIRE":
                return targetElement.getNama().equalsIgnoreCase("ICE") ? 2 : 1;
            case "WATER":
                return targetElement.getNama().equalsIgnoreCase("FIRE") ? 2 : 1;
            case "EARTH":
                return targetElement.getNama().equalsIgnoreCase("WATER") ? 2 : 1;
            case "WIND":
                return targetElement.getNama().equalsIgnoreCase("EARTH") ? 2 : 1;
            case "ICE":
                return targetElement.getNama().equalsIgnoreCase("WIND") ? 2 : 1;
            default:
                return 1; // Default effectiveness if elements don't match
        }
    }
    
    private String getElementalAttackMessage(Element element) {
        switch (element.getNama().toUpperCase()) {
            case "FIRE":
                return "Blazing flames!";
            case "WIND":
                return "The wind roars!";
            case "WATER":
                return "Aqua splash!";
            case "ICE":
                return "Freezing cold!";
            case "EARTH":
                return "Earthquake shakes!";
            default:
                return "Elemental attack!";
        }
    }
    
    @Override
    public void setLevel(int level) throws LevelOutOfBoundsException {
        super.setLevel(level);
        if (level < 1 || level > 99) {
            throw new LevelOutOfBoundsException("Level must be between 1 and 99.");
        }

    }



    @Override
    public void useItem(Item item, Monster target) {
        Scanner scanner = new Scanner(System.in);

        if ("ElementalPotion".equals(item.getNama())) {
            System.out.println("Choose an element to apply: FIRE, WATER, EARTH, AIR, ICE");
            String input = scanner.nextLine().toUpperCase(); // Ambil input dan ubah ke huruf besar

            switch (input) {
                case "FIRE":
                case "WATER":
                case "EARTH":
                case "AIR":
                case "ICE":
                    List<Element> elementList = new ArrayList<>();
                    elementList.add(new Element(input)); // Buat objek Element sesuai pilihan pengguna
                    target.setElement(elementList);

                    int baseDamage = level * 10; // Example base damage
                    int actualDamage = baseDamage;
                    target.setHealthPoint(target.getHealthPoint() - actualDamage);

                    System.out.println("Used elemental potion. Target's element changed to " + input + ".");
                    System.out.println("Target loses " + actualDamage + " HP.");
                    break;
                default:
                    System.out.println("Invalid element choice. Potion has no effect.");
                    break;
            }
        } else {
            System.out.println("Cannot use this item. Invalid item type.");
        }

        scanner.close(); // Jangan lupa untuk menutup scanner setelah penggunaan
    }

    @Override
    public boolean flee() {
        boolean success = Math.random() < 0.3; // 30% chance of success

        if (success) {
            System.out.println("Successfully fled from the battle.");
            System.out.println("You Lose!");
        } else {
            System.out.println("Failed to flee. The battle continues.");
        }

        return success;
    }

    @Override
    public boolean isFainted() {
        return healthPoint <= 0;
    }

    @Override
    public void gainExperiencePoints(int points) {
        expPoint += points;
    }

    @Override
    public void evolve(Element newElement) {
        if (!hasEvolved && element.get(0).getEvolutionOptions().contains(newElement)) {
            element.set(0, newElement);
            hasEvolved = true;
            System.out.println(getNama() + " has evolved. New element: " + newElement.getNama());
        } else if (hasEvolved) {
            System.out.println(getNama() + " has already evolved this level.");
        } else {
            System.out.println("Cannot evolve. Evolution to " + newElement.getNama() + " is not allowed.");
        }
    }

    @Override
    protected void performRandomAttack(Monster myMonster) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'performRandomAttack'");
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    
}
