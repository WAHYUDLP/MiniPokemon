public class Item {
    private String nama;
    private int healthPoint;
    private int energyPoint;

    public Item(String name, int healthPoint, int energyPoint) {
        this.nama = name;
        this.healthPoint = healthPoint;
        this.energyPoint = energyPoint;
    }

    public String getNama() {
        return nama;
    }
    


    public int getHealthPoint() {
        return healthPoint;
    }

    public int getEnergyPoint() {
        return energyPoint;
    }
}