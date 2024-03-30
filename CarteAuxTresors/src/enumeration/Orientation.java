package enumeration;

/**
 * Enumération Orientation
 */
public enum Orientation {
    /**
     * Nord
     */
    NORD('N'),
    /**
     * Ouest
     */
    OUEST('O'),
    /**
     * Sud
     */
    SUD('S'),
    /**
     * Est
     */
    EST('E');

    private final char value;

    Orientation(char value) {
        this.value = value;
    }

    public char getValue() {
        return this.value;
    }
}
