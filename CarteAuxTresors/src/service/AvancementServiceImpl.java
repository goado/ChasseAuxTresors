package service;

import enumeration.Orientation;
import model.*;

/**
 * Implémentation de l'interface {@code AvancementService} fournissant des méthodes pour avancer les aventuriers sur la carte.
 * Cette classe est responsable de l'implémentation des mécanismes d'avancement des aventuriers, en tenant compte des règles du jeu.
 */
public class AvancementServiceImpl implements AvancementService {

    /**
     * Avance l'aventurier dans la direction spécifiée par son orientation.
     *
     * @param aventurier L'aventurier à déplacer.
     * @param situation La situation actuelle comprenant la carte et les autres éléments du jeu.
     */
    public void avancer(Aventurier aventurier, Situation situation) {
        // Récupération de l'orientation de l'aventurier
        char orientation = aventurier.getOrientation();

        // Vérification des collisions avec les montagnes, les autres aventuriers et les frontières de la carte
        this.checkCollisionMontagne(aventurier, situation);
        this.checkCollisionAventurier(aventurier, situation);
        this.checkFrontieresCarte(aventurier, situation);

        // Vérification que l'aventurier n'est pas bloqué
        if ((!aventurier.isBloqueTemporairement()) && (!aventurier.isBloque())) {

            // Déplacement de l'aventurier selon son orientation
            if ((orientation == Orientation.NORD.getValue())) {
                aventurier.setPositionY(aventurier.getPositionY() - 1);
            }

            if (orientation == Orientation.OUEST.getValue()) {
                aventurier.setPositionX((aventurier.getPositionX() - 1));
            }

            if (orientation == Orientation.SUD.getValue()) {
                aventurier.setPositionY(aventurier.getPositionY() + 1);
            }

            if (orientation == Orientation.EST.getValue()) {
                aventurier.setPositionX((aventurier.getPositionX() + 1));
            }

            // Vérification de la présence de trésor sur la nouvelle position de l'aventurier
            this.checkPresenceTresor(aventurier, situation);
        }

        aventurier.setBloqueTemporairement(false);
    }

    /**
     * Vérifie s'il y a une collision entre l'aventurier donné et les montagnes dans la situation.
     * Met à jour l'état de l'aventurier en conséquence.
     *
     * @param aventurier L'aventurier à vérifier.
     * @param situation  La situation contenant les montagnes pour la vérification.
     */
    private void checkCollisionMontagne(Aventurier aventurier, Situation situation) {

        // Si des montagnes sont présentes sur la carte
        if (situation.getMontagnes() != null) {
            // Parcourir toutes les montagnes dans la situation
            for (Montagne montagne : situation.getMontagnes()) {
                int montagneX = montagne.getPositionX();
                int montagneY = montagne.getPositionY();

                // Vérifier la collision avec chaque montagne
                if (this.isCollision(aventurier, montagneX, montagneY)) {

                    // Si collision avec une montagne, marquer l'aventurier comme bloqué et ayant fini son parcours
                    aventurier.setBloque(true);
                    aventurier.setAFiniParcours(true);
                }
            }
        }
    }

    /**
     * Vérifie s'il y a une collision entre l'aventurier donné et les autres aventuriers dans la situation.
     * Met à jour l'état de l'aventurier en conséquence.
     *
     * @param aventurier L'aventurier à vérifier.
     * @param situation  La situation contenant les autres aventuriers pour la vérification.
     */
    private void checkCollisionAventurier(Aventurier aventurier, Situation situation) {

        if (situation.getAventuriers() != null) {
            // Parcourir tous les autres aventuriers dans la situation
            for (Aventurier aventurierElmt : situation.getAventuriers()) {

                // Vérifier la collision avec chaque autre aventurier sauf lui-même
                if (aventurier.getId() != aventurierElmt.getId()) {
                    int aventurierElmtX = aventurierElmt.getPositionX();
                    int aventurierElmtY = aventurierElmt.getPositionY();

                    // Vérifier la collision avec l'autre aventurier
                    if (this.isCollision(aventurier, aventurierElmtX, aventurierElmtY)) {

                        // Si l'autre aventurier a terminé son parcours ou est bloqué, marquer l'aventurier comme bloqué et ayant fini son parcours
                        if (aventurierElmt.isParcoursFini() || aventurierElmt.isBloque()) {
                            aventurier.setBloque(true);
                            aventurier.setAFiniParcours(true);
                            // Si les deux aventuriers ont des orientations en opposition, ils sont tous les deux définitevement bloqués
                        } else if (this.aventuriersEnOpposition(aventurier, aventurierElmt)) {
                            aventurier.setBloque(true);
                            aventurier.setAFiniParcours(true);
                            aventurierElmt.setBloque(true);
                            aventurierElmt.setAFiniParcours(true);
                        }
                        else {
                            // Sinon, marquer temporairement l'aventurier comme bloqué
                            aventurier.setBloqueTemporairement(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * Vérifie si deux aventuriers sont en opposition, c'est-à-dire s'ils sont orientés dans des directions opposées.
     *
     * @param aventurier1 Le premier aventurier à comparer.
     * @param aventurier2 Le deuxième aventurier à comparer.
     * @return true si les aventuriers sont en opposition, false sinon.
     */
    private boolean aventuriersEnOpposition(Aventurier aventurier1, Aventurier aventurier2) {

        // Récupération des orientations des aventuriers
        char orientation1 = aventurier1.getOrientation();
        char orientation2 = aventurier2.getOrientation();

        // Vérification des orientations pour détecter s'ils sont en opposition
        return ((orientation1 == Orientation.NORD.getValue()) && (orientation2 == Orientation.NORD.getValue()))
                || ((orientation1 == Orientation.OUEST.getValue()) && (orientation2 == Orientation.EST.getValue()))
                || ((orientation1 == Orientation.SUD.getValue()) && (orientation2 == Orientation.NORD.getValue()))
                || ((orientation1 == Orientation.EST.getValue()) && (orientation2 == Orientation.OUEST.getValue()));
    }

    /**
     * Vérifie s'il y a une collision entre l'aventurier et un élément aux coordonnées spécifiées.
     *
     * @param aventurier L'aventurier pour lequel on vérifie la collision.
     * @param elementX   La coordonnée X de l'élément avec lequel on vérifie la collision.
     * @param elementY   La coordonnée Y de l'élément avec lequel on vérifie la collision.
     * @return true si une collision est détectée, false sinon.
     */
    private boolean isCollision(Aventurier aventurier, int elementX, int elementY) {

        // Récupération de l'orientation et des coordonnées de l'aventurier
        char orientation = aventurier.getOrientation();
        int aventurierX = aventurier.getPositionX();
        int aventurierY = aventurier.getPositionY();

        // Vérification des conditions de collision en fonction de l'orientation de l'aventurier
        return (((orientation == Orientation.NORD.getValue()) && (aventurierX == elementX) && (aventurierY - 1 == elementY))
                || ((orientation == Orientation.OUEST.getValue()) && (aventurierX - 1 == elementX) && (aventurierY == elementY))
                || ((orientation == Orientation.SUD.getValue()) && (aventurierX == elementX) && (aventurierY == elementY - 1))
                || ((orientation == Orientation.EST.getValue()) && (aventurierX == elementX - 1) && (aventurierY == elementY)));
    }

    /**
     * Vérifie si l'aventurier est aux frontières de la carte.
     * Si c'est le cas, marque l'aventurier comme bloqué et ayant fini son parcours.
     *
     * @param aventurier L'aventurier à vérifier.
     * @param situation  La situation contenant la carte pour la vérification.
     */
    private void checkFrontieresCarte(Aventurier aventurier, Situation situation) {

        // Récupération de l'orientation et des coordonnées de l'aventurier
        char orientation = aventurier.getOrientation();
        int positionX = aventurier.getPositionX();
        int positionY = aventurier.getPositionY();
        Carte carte = situation.getCarte();

        // Vérification des frontières de la carte en fonction de l'orientation de l'aventurier
        if (((orientation == Orientation.NORD.getValue()) && (positionY == 0))
                || ((orientation == Orientation.OUEST.getValue()) && (positionX == 0))
                || ((orientation == Orientation.SUD.getValue()) && (positionY == carte.getHauteur() - 1))
                || ((orientation == Orientation.EST.getValue()) && (positionX == carte.getLargeur() - 1))) {

            // Marquer l'aventurier comme bloqué et ayant fini son parcours
            aventurier.setBloque(true);
            aventurier.setAFiniParcours(true);
        }
    }

    /**
     * Vérifie la présence d'un trésor à la position actuelle de l'aventurier.
     * Si un trésor est trouvé à cette position et qu'il reste des trésors à collecter, l'aventurier ramasse le trésor et le nombre de trésors restants est mis à jour.
     *
     * @param aventurier L'aventurier
     * @param situation  La situation actuelle comprenant les informations sur les trésors.
     */
    private void checkPresenceTresor(Aventurier aventurier, Situation situation) {
        // Récupération des coordonnées de l'aventurier
        int aventurierX = aventurier.getPositionX();
        int aventurierY = aventurier.getPositionY();

        // Si des trésors sont présents sur la carte
        if (situation.getTresors() != null) {
            // Parcours de la liste des trésors pour vérifier la présence d'un trésor à la position de l'aventurier
            for (Tresor tresor : situation.getTresors()) {
                int tresorX = tresor.getPositionX();
                int tresorY = tresor.getPositionY();

                // Vérification si l'aventurier est sur la même case qu'un trésor et s'il reste des trésors à collecter
                if ((aventurierX == tresorX) && (aventurierY == tresorY) && (tresor.getNombreTresorsRestant() > 0)) {

                    // Ramassage du trésor par l'aventurier et mise à jour du nombre de trésors restants
                    aventurier.setNombreTresorsRamasses(aventurier.getNombreTresorsRamasses() + 1);
                    tresor.setNombreTresorsRestant(tresor.getNombreTresorsRestant() - 1);
                }
            }
        }
    }
}
