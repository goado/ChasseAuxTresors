package model;

/**
 * Représente une montagne dans la chasse aux trésors.
 */
public class Montagne {

    // La position X de la montagne sur la carte
    private int positionX;

    // La position Y de la montagne sur la carte
    private int positionY;

    /**
     * Constructeur de la classe Aventurier
     * @param positionX position X de la montagne
     * @param positionY position Y de la montagne
     */
    public Montagne(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**********************************
     * GETTERS AND SETTERS
     * ********************************
     */

    public int getPositionX() {
        return this.positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
