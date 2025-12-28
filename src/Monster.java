import java.util.List;

public abstract class Monster implements BattleActions {

    protected String nama;
    protected int level;
    public int healthPoint;
    int expPoint;
    List<Element> element;
    protected String imgPath;

      // Abstract Methods
      public abstract void basicAttack(Monster target);

      public abstract void specialAttack(Monster target);
  
      public abstract void elementalAttack(Monster target);
  
      public abstract void useItem(Item item, Monster target);
  
      public abstract boolean flee();
  
      public abstract boolean isFainted();
  
      public abstract void gainExperiencePoints(int points);
  
      public abstract void evolve(Element newElement);
  
      protected abstract void performRandomAttack(Monster myMonster);
  
      protected abstract void incrementWins();

    public int getExpPoint() {
        return expPoint;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setExpPoint(int expPoint) {
        this.expPoint = expPoint;
    }


    public Monster(String nama, int level, List<Element> elements) {
        this.nama = nama;
        this.level = level;
        this.element = elements;
        this.healthPoint = 100; 
        this.expPoint = 0; 
    }

    public Monster(String nama, int level, List<Element> elements, String imgPath) {
        this.nama = nama;
        this.level = level;
        this.element = elements;
        this.healthPoint = 100; 
        this.expPoint = 0; 
        this.imgPath = imgPath;
    }

    // Getter for name
    public String getNama() {
        return nama;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) throws LevelOutOfBoundsException {
        this.level = level;
    }

    // Getter for element
    public List<Element> getElement() {
        return element;
    }

    // Setter for element
    public void setElement(List<Element> element2) {
        this.element = element2;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    // Setter for healthPoint
    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return this.imgPath;
    }
}
