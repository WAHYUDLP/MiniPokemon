import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Element {
    private String nama;
    private List<Element> weakness;
    private List<Element> evolutionOptions;
    private static final long serialVersionUID = 1L;

    // Definisi elemen-elemen
    public static final Element FIRE = new Element("FIRE");
    public static final Element WIND = new Element("WIND");
    public static final Element WATER = new Element("WATER");
    public static final Element ICE = new Element("ICE");
    public static final Element EARTH = new Element("EARTH");

    private static final Map<String, Element> ELEMENTS = new HashMap<>();

    public Element(){

    }
    
    static {
        // Mengatur kelemahan setiap elemen
        FIRE.weakness = Arrays.asList(WATER);
        ICE.weakness = Arrays.asList(FIRE);
        WIND.weakness = Arrays.asList(ICE);
        EARTH.weakness = Arrays.asList(WIND);
        WATER.weakness = Arrays.asList(EARTH);

        // Mengatur pilihan evolusi setiap elemen
        FIRE.evolutionOptions = Arrays.asList(WIND, EARTH);
        WIND.evolutionOptions = Arrays.asList(FIRE, WATER);
        WATER.evolutionOptions = Arrays.asList(ICE, WIND);
        ICE.evolutionOptions = Arrays.asList(WATER, EARTH);
        EARTH.evolutionOptions = Arrays.asList(FIRE, ICE);

        // Mengisi peta elemen
        ELEMENTS.put(FIRE.getNama(), FIRE);
        ELEMENTS.put(WIND.getNama(), WIND);
        ELEMENTS.put(WATER.getNama(), WATER);
        ELEMENTS.put(ICE.getNama(), ICE);
        ELEMENTS.put(EARTH.getNama(), EARTH);
    }

    Element(String nama) {
        this.nama = nama;
    }

    public boolean isEffectiveAgainst(Element element) {
        return weakness.contains(element);
    }

    public List<Element> getWeakness() {
        return weakness;
    }

    public String getNama() {
        return nama;
    }

    public List<Element> getEvolutionOptions() {
        return evolutionOptions;
    }

    public static Element fromString(String elementName) {
        return ELEMENTS.get(elementName.toUpperCase());
 
    }
    public void evolutionOptions(Element element) {
        if (!evolutionOptions.contains(element)) {
            evolutionOptions.add(element);
        }
    }
}
