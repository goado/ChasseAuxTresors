package enumeration;

/**
 * Enumération Mouvement
 */
public enum Mouvement {
    /**
     * Avancer tout droit
     */
    AVANCER('A'),
    /**
     * Tourner à gauche
     */
    GAUCHE('G'),
    /**
     * Tourner à droite
     */
    DROITE('D');

    private final char value;

    Mouvement(char value) {
        this.value = value;
    }

    public char getValue() {
        return this.value;
    }

}
