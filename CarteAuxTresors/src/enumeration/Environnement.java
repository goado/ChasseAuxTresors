package enumeration;

/**
 * Enumération Environnement
 */
public enum Environnement {
    /**
     * Carte
     */
    CARTE("C"),
    /**
     * Montagne
     */
    MONTAGNE("M"),
    /**
     * Trésor
     */
    TRESOR("T"),
    /**
     * Aventurier
     */
    AVENTURIER("A");

    private final String value;

    Environnement(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
