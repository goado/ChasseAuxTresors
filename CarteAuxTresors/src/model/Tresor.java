package model;

public class Tresor {

    // La position X du trésor sur la carte
    private int positionX;

    // La position Y du trésor sur la carte
    private int positionY;

    // Le nombre de trésors restants
    private int nombreTresorsRestant;

    /**
     * Constructeur de la classe Tresor
     * @param positionX La position X du trésor sur la carte
     * @param positionY La position Y du trésor sur la carte
     * @param nombreTresorsRestant Le nombre de trésors restants
     */
    public Tresor(int positionX, int positionY, int nombreTresorsRestant) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.nombreTresorsRestant = nombreTresorsRestant;
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

    public int getNombreTresorsRestant() {
        return this.nombreTresorsRestant;
    }

    public void setNombreTresorsRestant(int nombreTresorsRestant) {
        this.nombreTresorsRestant = nombreTresorsRestant;
    }
}
