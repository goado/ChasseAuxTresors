package main;

import service.FichierService;
import service.FichierServiceImpl;
import model.Situation;
import service.SimulationService;
import service.SimulationServiceImpl;

/**
 * Classe Main qui permet le lancement de l'application.
 */
public class Main {

    private static final String FICHIER_ENTREE = "fichierEntree.txt";
    private static final String FICHIER_SORTIE = "fichierSortie.txt";

    private final FichierService fichierService = new FichierServiceImpl();
    private final SimulationService simulationService = new SimulationServiceImpl();

    /**
     * La méthode principale qui démarre l'application.
     * Cette méthode crée une instance de la classe Main et appelle sa méthode run().
     *
     * @param args les arguments de la ligne de commande (non utilisés dans cette application)
     */
    public static void main(String[] args) {
        Main programm = new Main();
        programm.run();
    }

    /**
     * Exécute le programme en lisant une situation initiale à partir d'un service de fichier,
     * simulant ensuite des mouvements avec un service de simulation, et enfin écrivant la situation finale dans un fichier.
     * Les étapes du programme sont les suivantes :
     * 1. Lecture de la situation initiale à partir du service de fichier.
     * 2. Simulation des mouvements à l'aide du service de simulation pour arriver d'une situation initiale à une situation finale.
     * 3. Écriture de la situation finale dans un fichier de sorte via le service de fichier.
     */
    public void run() {
        Situation situationInitale = this.fichierService.lecture(FICHIER_ENTREE);

        if (situationInitale.getCarte() != null) {
            Situation situationFinale = this.simulationService.simulerMouvements(situationInitale);
            this.fichierService.ecriture(situationFinale, FICHIER_SORTIE);
        } else {
            throw new NullPointerException("OBLIGATOIRE: Il manque la ligne décrivant la carte dans le fichier d'entrée.");
        }

    }

}