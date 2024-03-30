package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.SimulationService;
import service.SimulationServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationServiceImplTest {

    private final SimulationService simulationService = new SimulationServiceImpl();
    private Situation situation;

    @BeforeEach
    void setUp() {
        // Création de la carte
        Carte carte = new Carte(5, 5);

        // Création des montagnes
        Montagne montagne = new Montagne(1, 3);
        List<Montagne> montagnes = new ArrayList<>();
        montagnes.add(montagne);

        // Création des trésors
        Tresor tresor = new Tresor(1, 1, 1);
        List<Tresor> tresors = new ArrayList<>();
        tresors.add(tresor);

        // Création de la situation
        this.situation = new Situation();
        situation.setCarte(carte);
        situation.setMontagnes(montagnes);
        situation.setTresors(tresors);
    }

    @Test
    void testSimulerMouvementsSansChangementDirection() {

        // Création des aventuriers
        Aventurier aventurier = new Aventurier("Lara", 1, 0, 'S', "AAAA", 0, 1);
        List<Aventurier> aventuriers = new ArrayList<>();
        aventuriers.add(aventurier);


        this.situation.setAventuriers(aventuriers);

        // Appel de la méthode simulerMouvements
        Situation situationFinale = this.simulationService.simulerMouvements(this.situation);

        // Vérification des résultats
        assertEquals(1, situationFinale.getAventuriers().get(0).getPositionX());
        assertEquals(2, situationFinale.getAventuriers().get(0).getPositionY());
        assertEquals(1, situationFinale.getAventuriers().get(0).getNombreTresorsRamasses());
        assertTrue(situationFinale.getAventuriers().get(0).isBloque());
        assertTrue(situationFinale.getAventuriers().get(0).isParcoursFini());
        assertEquals(0, situationFinale.getTresors().get(0).getNombreTresorsRestant());
    }

    @Test
    void testSimulerMouvementsAvecChangementDirection() {
        // Création d'un aventurier orienté au sud
        Aventurier aventurier = new Aventurier("Lara", 0, 0, 'S', "AGADA", 0, 1);
        List<Aventurier> aventuriers = new ArrayList<>();
        aventuriers.add(aventurier);
        this.situation.setAventuriers(aventuriers);

        // Appel de la méthode simulerMouvements
        Situation situationFinale = this.simulationService.simulerMouvements(situation);

        // Vérification de la nouvelle orientation
        assertEquals(1, situationFinale.getAventuriers().get(0).getPositionX());
        assertEquals(2, situationFinale.getAventuriers().get(0).getPositionY());
        assertEquals(1, situationFinale.getAventuriers().get(0).getNombreTresorsRamasses());
        assertFalse(situationFinale.getAventuriers().get(0).isBloque());
        assertTrue(situationFinale.getAventuriers().get(0).isParcoursFini());
        assertEquals(0, situationFinale.getTresors().get(0).getNombreTresorsRestant());
    }

}