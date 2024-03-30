package service;

import enumeration.Mouvement;
import model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation de l'interface {@code SimulationService} qui fournit des méthodes pour simuler une chasse aux trésors.
 * Cette classe est responsable de la simulation des mouvements des aventuriers sur la carte.
 */
public class SimulationServiceImpl implements SimulationService {

    private final AvancementService avancementService = new AvancementServiceImpl();

    /**
     * Simule les mouvements des aventuriers sur la carte en fonction des chemins définis pour chacun d'eux.
     * Les aventuriers avancent ou changent d'orientation selon les mouvements définis dans leur chemin.
     * Une fois tous les aventuriers ayant terminé leur parcours, la situation mise à jour est renvoyée.
     *
     * @param situation La situation actuelle comprenant la carte, les aventuriers et les trésors.
     * @return La situation mise à jour après la simulation des mouvements des aventuriers.
     */
    public Situation simulerMouvements(Situation situation) {

        boolean isFinDesParcours = false;

        // Boucle tant que tous les aventuriers n'ont pas terminé leur parcours
        while (!isFinDesParcours) {

            // Parcours de tous les aventuriers dans la situation
            for (Aventurier aventurier : situation.getAventuriers()) {
                String chemin = aventurier.getChemin();
                if (!chemin.isEmpty()) {
                    if (chemin.charAt(0) == Mouvement.AVANCER.getValue()) {
                        // Avancement de l'aventurier
                        this.avancementService.avancer(aventurier, situation);
                    } else if ((chemin.charAt(0) == Mouvement.GAUCHE.getValue()) || (chemin.charAt(0) == Mouvement.DROITE.getValue())) {
                        // Changement d'orientation de l'aventurier
                        char nouvelleOrientation = this.changerDirection(aventurier, chemin.charAt(0));
                        aventurier.setOrientation(nouvelleOrientation);
                    }
                    // Mise à jour du chemin de l'aventurier après avoir traité le premier mouvement
                    aventurier.setChemin(chemin.substring(1));
                } else {
                    // Marquage de l'aventurier comme ayant terminé son parcours si son chemin est vide
                    aventurier.setAFiniParcours(true);
                }
            }
            // Vérification si tous les aventuriers ont terminé leur parcours
            isFinDesParcours = this.isParcoursFiniPourToutAventurier(situation.getAventuriers());
        }
        // Renvoi de la situation mise à jour après la simulation des mouvements
        return situation;
    }

    /**
     * Vérifie si le parcours est terminé pour tous les aventuriers.
     *
     * @param aventuriers La liste des aventuriers à vérifier.
     * @return true si le parcours est terminé pour tous les aventuriers, sinon false.
     */
    private boolean isParcoursFiniPourToutAventurier(List<Aventurier> aventuriers) {
        // Utilise un flux pour vérifier si tous les aventuriers ont leur parcours terminé
        return aventuriers.stream().allMatch(Aventurier::isParcoursFini);
    }

    /**
     * Change l'orientation de l'aventurier en fonction de la direction spécifiée.
     *
     * @param aventurier L'aventurier dont l'orientation doit être modifiée.
     * @param direction  La direction dans laquelle l'aventurier doit tourner ('G' pour gauche, 'D' pour droite).
     * @return La nouvelle orientation de l'aventurier après avoir tourné dans la direction spécifiée.
     */
    private char changerDirection(Aventurier aventurier, char direction) {

        char orientation = aventurier.getOrientation();

        // Création d'une map pour mapper les changements d'orientation en fonction de l'orientation actuelle et de la direction
        Map<Character, Map<Character, Character>> changementsOrientation = new HashMap<>();
        changementsOrientation.put('N', Map.of('G', 'O', 'D', 'E'));
        changementsOrientation.put('O', Map.of('G', 'S', 'D', 'N'));
        changementsOrientation.put('S', Map.of('G', 'E', 'D', 'O'));
        changementsOrientation.put('E', Map.of('G', 'N', 'D', 'S'));

        // Vérifier si l'orientation actuelle et la direction sont présentes dans la map
        if (changementsOrientation.containsKey(orientation) && changementsOrientation.get(orientation).containsKey(direction)) {
            // Récupérer la nouvelle orientation à partir de la map
            return changementsOrientation.get(orientation).get(direction);
        }

        // Retourner l'orientation inchangée si les paramètres ne correspondent à aucun cas dans la map
        return orientation;
    }

}
