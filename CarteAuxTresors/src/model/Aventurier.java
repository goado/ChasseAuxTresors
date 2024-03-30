package model;

/**
 * Représente un aventurier dans la chasse aux trésors.
 */
public class Aventurier {

    // Le nom de l'aventurier
    private String nom;

    // La position X actuelle de l'aventurier sur la carte
    private int positionX;

    // La position Y actuelle de l'aventurier sur la carte
    private int positionY;

    // L'orientation actuelle de l'aventurier (N, S, E ou O)
    private char orientation;

    // Le chemin à parcourir par l'aventurier
    private String chemin;

    // Le nombre de trésors ramassés par l'aventurier
    private int nombreTresorsRamasses;

    // L'ID de l'aventurier (permet d'établir une priorité également)
    private int id;

    // Indique si l'aventurier est bloqué
    private boolean isBloque;

    // Indique si l'aventurier est bloqué temporairement
    private boolean isBloqueTemporairement;

    // Indique si l'aventurier a fini son parcours
    private boolean aFiniParcours;

    /**
     * Constructeur vide
     */
    public Aventurier() {
    }

    /**
     * Constructeur de la classe Aventurier
     * @param nom nom de l'aventurier
     * @param positionX position X de l'aventurier
     * @param positionY position Y de l'aventurier
     * @param orientation orientation de l'aventurier
     * @param chemin chemin à parcourir
     * @param nombreTresorsRamasses nombre de trésors à ramasser
     * @param id ID de l'aventurier
     */
    public Aventurier(String nom, int positionX, int positionY, char orientation, String chemin, int nombreTresorsRamasses, int id) {
        this.nom = nom;
        this.positionX = positionX;
        this.positionY = positionY;
        this.orientation = orientation;
        this.chemin = chemin;
        this.nombreTresorsRamasses = nombreTresorsRamasses;
        this.id = id;
        this.isBloque = false;
        this.isBloqueTemporairement = false;
        this.aFiniParcours = false;
    }

    /**********************************
     * GETTERS AND SETTERS
     * ********************************
     */

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

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

    public char getOrientation() {
        return this.orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }

    public String getChemin() {
        return this.chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public int getNombreTresorsRamasses() {
        return this.nombreTresorsRamasses;
    }

    public void setNombreTresorsRamasses(int nombreTresorsRamasses) {
        this.nombreTresorsRamasses = nombreTresorsRamasses;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBloque() {
        return this.isBloque;
    }

    public void setBloque(boolean bloque) {
        isBloque = bloque;
    }

    public boolean isBloqueTemporairement() {
        return this.isBloqueTemporairement;
    }

    public void setBloqueTemporairement(boolean bloqueTemporairement) {
        isBloqueTemporairement = bloqueTemporairement;
    }

    public boolean isParcoursFini() {
        return this.aFiniParcours;
    }

    public void setAFiniParcours(boolean aFiniParcours) {
        this.aFiniParcours = aFiniParcours;
    }
}
