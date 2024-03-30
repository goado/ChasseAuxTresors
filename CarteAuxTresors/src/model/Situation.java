package model;

import java.util.List;

/**
 * Représente la synthèse de l'écosystème chasse aux trésors
 */
public class Situation {

    // La carte de la chasse aux trésors
    private Carte carte;

    // Les montagnes présentes sur la carte
    private List<Montagne> montagnes;

    // Les trésors présents sur la carte
    private List<Tresor> tresors;

    // Les aventuriers participants à la chasse aux trésors
    private List<Aventurier> aventuriers;

    /**
     * Constructeur vide
     */
    public Situation() {

    }

    /**
     * Constructeur de la classe Situation
     * @param carte La carte de la chasse aux trésors
     * @param montagnes Les montagnes présentes sur la carte
     * @param tresors Les trésors présents sur la carte
     * @param aventuriers Les aventuriers participants à la chasse aux trésors
     */
    public Situation(Carte carte, List<Montagne> montagnes, List<Tresor> tresors, List<Aventurier> aventuriers) {
        this.carte = carte;
        this.montagnes = montagnes;
        this.tresors = tresors;
        this.aventuriers = aventuriers;
    }

    /**********************************
     * GETTERS AND SETTERS
     * ********************************
     */

    public Carte getCarte() {
        return this.carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public List<Montagne> getMontagnes() {
        return this.montagnes;
    }

    public void setMontagnes(List<Montagne> montagnes) {
        this.montagnes = montagnes;
    }

    public List<Tresor> getTresors() {
        return this.tresors;
    }

    public void setTresors(List<Tresor> tresors) {
        this.tresors = tresors;
    }

    public List<Aventurier> getAventuriers() {
        return this.aventuriers;
    }

    public void setAventuriers(List<Aventurier> aventuriers) {
        this.aventuriers = aventuriers;
    }
}
