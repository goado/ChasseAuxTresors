package model;

/**
 * Représente la carte de la chasse aux trésors.
 */
public class Carte {

    // La largeur de la carte
    private int largeur;

    // La hauteur de la carte
    private int hauteur;

    /**
     * Constructeur de la classe Carte
     * @param largeur largeur de la carte
     * @param hauteur hauteur de la carte
     */
    public Carte(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    /**********************************
     * GETTERS AND SETTERS
     * ********************************
     */

    public int getLargeur() {
        return this.largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getHauteur() {
        return this.hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }
}
