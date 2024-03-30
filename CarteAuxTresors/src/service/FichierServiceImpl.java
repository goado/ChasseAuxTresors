package service;

import enumeration.Environnement;
import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface {@code FichierService} fournissant des méthodes pour lire-écrire des fichiers.
 */
public class FichierServiceImpl implements FichierService {

    List<Montagne> montagnes = new ArrayList<>();
    List<Tresor> tresors = new ArrayList<>();
    List<Aventurier> aventuriers = new ArrayList<>();
    int prioriteAventurier = 1;

    /**
     * Lit le contenu du fichier "fichierEntree.txt" et crée une situation à partir des données lues.
     *
     * @return La situation créée à partir des données lues dans le fichier.
     */
    public Situation lecture(String pathName) {
        Situation situation = new Situation();

        // LECTURE DE FICHIER
        try (BufferedReader br = new BufferedReader(new FileReader(pathName))) {

            String ligne;
            int numeroLigne = 1;

            // Lire chaque ligne du fichier
            while ((ligne = br.readLine()) != null) {
                // Si la ligne n'est pas un commentaire
                if (ligne.charAt(0) != '#') {
                    // Vérifier le format de la ligne
                    this.checkFormatLigne(ligne, numeroLigne);
                    // Traiter la ligne pour ajouter ses données à la situation
                    this.traitementLigne(ligne, situation);
                    numeroLigne++;
                }
            }

            // Définir les montagnes, trésors et aventuriers dans la situation
            situation.setMontagnes(montagnes);
            situation.setTresors(tresors);
            situation.setAventuriers(aventuriers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return situation;
    }

    /**
     * Cette méthode écrit le contenu de la situation dans un fichier "fichierSortie.txt".
     *
     * @param situation La situation à écrire dans le fichier.
     */
    public void ecriture(Situation situation, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Obtenir le contenu à écrire dans le fichier en appelant la méthode getContenuFichier
            String data = this.getContenuFichier(situation);
            // Écrire le contenu dans le fichier
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vérifie le format d'une ligne donnée en fonction de son type et lève une exception si le format est invalide.
     *
     * @param ligne       La ligne à vérifier.
     * @param numeroLigne Le numéro de la ligne pour lequel la vérification est effectuée.
     * @throws IllegalArgumentException Si le format de la ligne est invalide.
     */
    private void checkFormatLigne(String ligne, int numeroLigne) {
        // Séparation de la ligne en mots en utilisant le délimiteur " - "
        String[] words = ligne.split(" - ");
        String typeLigne = words[0];

        // Vérification du nombre de mots dans la ligne par rapport au nombre attendu pour ce type de ligne
        if (words.length != this.nombreMotsAttendus(typeLigne)) {
            // Si le nombre de mots ne correspond pas au nombre attendu, une exception est levée avec un message indiquant le numéro de la ligne
            throw new IllegalArgumentException("Format invalide pour la ligne : " + numeroLigne);
        }
    }

    /**
     * Retourne le nombre de mots attendus pour une ligne donnée en fonction de son type.
     *
     * @param typeLigne Le type de la ligne pour lequel on souhaite obtenir le nombre de mots attendus.
     * @return Le nombre de mots attendus pour le type de ligne spécifié.
     *         Si le type de ligne n'est pas reconnu, retourne -1.
     */
    private int nombreMotsAttendus(String typeLigne) {
        return switch (typeLigne) {
            case "C" -> 3; // Nombre attendu de mots pour la ligne de carte
            case "M" -> 3; // Nombre attendu de mots pour la ligne de montagne
            case "T" -> 4; // Nombre attendu de mots pour la ligne de trésor
            case "A" -> 6; // Nombre attendu de mots pour la ligne d'aventurier
            default -> -1; // pas de nombre précis attendu dans les autres cas
        };
    }

    /**
     * Traite une ligne donnée en extrayant ses informations et les ajoute à la situation en cours.
     *
     * @param ligne     La ligne à traiter.
     * @param situation La situation à laquelle ajouter les informations extraites de la ligne.
     */
    private void traitementLigne(String ligne, Situation situation) {
        String[] words = ligne.split(" - ");
        String typeLigne = words[0];

        // Traiter chaque type de ligne
        if (Environnement.CARTE.getValue().equals(typeLigne)) {
            // Si la ligne représente une carte, créer une carte à partir des informations et la définir dans la situation
            Carte carte = this.creationCarte(words);
            situation.setCarte(carte);
        }

        if (Environnement.MONTAGNE.getValue().equals(typeLigne)) {
            // Si la ligne représente une montagne, créer une montagne à partir des informations et l'ajouter à la liste des montagnes
            Montagne montagne = this.creationMontagne(words);
            this.montagnes.add(montagne);
        }

        if (Environnement.TRESOR.getValue().equals(typeLigne)) {
            // Si la ligne représente un trésor, créer un trésor à partir des informations et l'ajouter à la liste des trésors
            Tresor tresor = this.creationTresor(words);
            this.tresors.add(tresor);
        }

        if (Environnement.AVENTURIER.getValue().equals(typeLigne)) {
            // Si la ligne représente un aventurier, créer un aventurier à partir des informations et l'ajouter à la liste des aventuriers
            Aventurier aventurier = this.creationAventurier(words);
            this.aventuriers.add(aventurier);
            // Incrémenter la priorité de l'aventurier pour suivre l'ordre d'apparition dans le fichier
            this.prioriteAventurier = this.prioriteAventurier + 1;
        }
    }

    /**
     * Construit et retourne le contenu du fichier à partir de la situation donnée.
     *
     * @param situation La situation à partir de laquelle construire le contenu du fichier.
     * @return Le contenu du fichier construit à partir de la situation.
     */
    private String getContenuFichier(Situation situation) {

        // Initialisation d'un StringBuilder pour construire le contenu du fichier
        StringBuilder contenuBuilder = new StringBuilder();

        // Ajout de la ligne carte
        contenuBuilder.append("C - ")
                .append(situation.getCarte().getLargeur())
                .append(" - ")
                .append(situation.getCarte().getHauteur())
                .append("\n");

        // Ajout des lignes montagnes
        if (situation.getMontagnes() != null) {
            for (Montagne montagne : situation.getMontagnes()) {
                contenuBuilder.append("M - ")
                        .append(montagne.getPositionX())
                        .append(" - ")
                        .append(montagne.getPositionY())
                        .append("\n");
            }
        }

        // Ajout des lignes trésors
        if (situation.getTresors() != null) {
            for (Tresor tresor : situation.getTresors()) {
                // Ajouter seulement si le trésor a encore des trésors restants à collecter
                if (tresor.getNombreTresorsRestant() > 0) {
                    contenuBuilder.append("T - ")
                            .append(tresor.getPositionX())
                            .append(" - ")
                            .append(tresor.getPositionY())
                            .append(" - ")
                            .append(tresor.getNombreTresorsRestant())
                            .append("\n");
                }
            }
        }

        // Ajout des lignes aventuriers
        if (situation.getAventuriers() != null) {
            for (Aventurier aventurier : situation.getAventuriers()) {
                contenuBuilder.append("A - ")
                        .append(aventurier.getNom())
                        .append(" - ")
                        .append(aventurier.getPositionX())
                        .append(" - ")
                        .append(aventurier.getPositionY())
                        .append(" - ")
                        .append(aventurier.getOrientation())
                        .append(" - ")
                        .append(aventurier.getNombreTresorsRamasses())
                        .append("\n");
            }
        }

        return contenuBuilder.toString();
    }

    /**
     * Crée et retourne une instance de Carte à partir des informations extraites du fichier.
     *
     * @param words Les infos extraits de la ligne représentant la carte.
     *              L'index 1 contient la largeur de la carte, et l'index 2 contient la hauteur de la carte.
     * @return Une instance de Carte avec la largeur et la hauteur spécifiées.
     */
    private Carte creationCarte(String[] words) {
        int largeur = Integer.parseInt(words[1]);
        int hauteur = Integer.parseInt(words[2]);
        return new Carte(largeur, hauteur);
    }

    /**
     * Crée et retourne une instance de Montagne à partir des informations extraites.
     *
     * @param words Les infos extraites de la ligne représentant la montagne.
     *              L'index 1 contient la position X de la montagne, et l'index 2 contient la position Y de la montagne.
     * @return Une instance de Montagne avec la position X et la position Y spécifiées.
     */
    private Montagne creationMontagne(String[] words) {
        int positionX = Integer.parseInt(words[1]);
        int positionY = Integer.parseInt(words[2]);
        return new Montagne(positionX, positionY);
    }

    /**
     * Crée et retourne une instance de Tresor à partir des informations extraites des mots donnés.
     *
     * @param words Les infos extraits de la ligne représentant le trésor.
     *              L'index 1 contient la position X du trésor, l'index 2 contient la position Y du trésor,
     *              et l'index 3 contient le nombre de trésors restants à cette position.
     * @return Une instance de Tresor avec la position X, la position Y et le nombre de trésors restants spécifiés.
     */
    private Tresor creationTresor(String[] words) {
        int positionX = Integer.parseInt(words[1]);
        int positionY = Integer.parseInt(words[2]);
        int nombreTresorsRestant = Integer.parseInt(words[3]);
        return new Tresor(positionX, positionY, nombreTresorsRestant);
    }

    /**
     * Crée et retourne une instance d'Aventurier à partir des informations extraites des mots donnés.
     *
     * @param words Les infos extraites de la ligne représentant l'aventurier.
     *              L'index 1 contient le nom de l'aventurier, l'index 2 contient la position X de l'aventurier,
     *              l'index 3 contient la position Y de l'aventurier, l'index 4 contient l'orientation de l'aventurier,
     *              l'index 5 contient le chemin de déplacement de l'aventurier.
     * @return Une instance d'Aventurier avec les informations spécifiées.
     */
    private Aventurier creationAventurier(String[] words) {
        String nom = words[1];
        int positionX = Integer.parseInt(words[2]);
        int positionY = Integer.parseInt(words[3]);
        char orientation = words[4].charAt(0);
        String chemin = words[5];
        int nombreTresorsRamasses = 0;

        return new Aventurier(
                nom,
                positionX,
                positionY,
                orientation,
                chemin,
                nombreTresorsRamasses,
                this.prioriteAventurier);
    }
}
