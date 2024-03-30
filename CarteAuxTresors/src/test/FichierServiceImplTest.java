package test;

import model.*;
import org.junit.jupiter.api.Test;
import service.FichierServiceImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FichierServiceImplTest {

    private static final String FICHIER_ENTREE_TEST = "fichierEntreeTest.txt";
    private static final String FICHIER_SORTIE_TEST = "fichierSortieTest.txt";

    @Test
    void testLecture() {
        // Création d'une instance de FichierServiceImpl
        FichierServiceImpl fichierService = new FichierServiceImpl();

        // Appel de la méthode lecture pour lire les données du fichier fictif
        Situation situation = fichierService.lecture(FICHIER_ENTREE_TEST);

        // Vérification que la situation retournée n'est pas nulle
        assertNotNull(situation);

        // Vérification de la carte
        Carte carte = situation.getCarte();
        assertNotNull(carte);
        assertEquals(3, carte.getLargeur());
        assertEquals(4, carte.getHauteur());

        // Vérification des montagnes
        List<Montagne> montagnes = situation.getMontagnes();
        assertNotNull(montagnes);
        assertEquals(2, montagnes.size());
        assertEquals(1, montagnes.get(0).getPositionX());
        assertEquals(0, montagnes.get(0).getPositionY());
        assertEquals(2, montagnes.get(1).getPositionX());
        assertEquals(1, montagnes.get(1).getPositionY());

        // Vérification des trésors
        List<Tresor> tresors = situation.getTresors();
        assertNotNull(tresors);
        assertEquals(2, tresors.size());
        assertEquals(0, tresors.get(0).getPositionX());
        assertEquals(3, tresors.get(0).getPositionY());
        assertEquals(2, tresors.get(0).getNombreTresorsRestant());
        assertEquals(1, tresors.get(1).getPositionX());
        assertEquals(3, tresors.get(1).getPositionY());
        assertEquals(3, tresors.get(1).getNombreTresorsRestant());

        // Vérification des aventuriers
        List<Aventurier> aventuriers = situation.getAventuriers();
        assertNotNull(aventuriers);
        assertEquals(1, aventuriers.size());
        assertEquals("Lara", aventuriers.get(0).getNom());
        assertEquals(1, aventuriers.get(0).getPositionX());
        assertEquals(1, aventuriers.get(0).getPositionY());
        assertEquals('S', aventuriers.get(0).getOrientation());
        assertEquals("AADADAGGA", aventuriers.get(0).getChemin());
    }

    @Test
    void testEcriture() throws FileNotFoundException {
        // Création d'une situation fictive
        Situation situation = new Situation();
        Carte carte = new Carte(5, 5);
        situation.setCarte(carte);
        List<Montagne> montagnes = new ArrayList<>();
        montagnes.add(new Montagne(1, 1));
        situation.setMontagnes(montagnes);
        List<Tresor> tresors = new ArrayList<>();
        tresors.add(new Tresor(1, 2, 1));
        situation.setTresors(tresors);
        List<Aventurier> aventuriers = new ArrayList<>();
        aventuriers.add(new Aventurier("Lara", 1, 1, 'S', "AA", 0, 1));
        situation.setAventuriers(aventuriers);

        // Création d'une instance de FichierServiceImpl
        FichierServiceImpl fichierService = new FichierServiceImpl();

        // Appel de la méthode ecriture pour écrire la situation dans un fichier fictif
        fichierService.ecriture(situation, FICHIER_SORTIE_TEST);

        // Lecture du contenu du fichier écrit
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_SORTIE_TEST))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                switch (lineNumber) {
                    case 0: // Première ligne : carte
                        assertEquals("C - 5 - 5", line);
                        break;
                    case 1: // Deuxième ligne : montagne
                        assertEquals("M - 1 - 1", line);
                        break;
                    case 2: // Troisième ligne : trésor
                        assertEquals("T - 1 - 2 - 1", line);
                        break;
                    case 3: // Quatrième ligne : aventurier
                        assertEquals("A - Lara - 1 - 1 - S - 0", line);
                        break;
                    default:
                        fail("Fichier de sortie contient trop de lignes");
                }
                lineNumber++;
            }
            // Vérification du nombre de lignes
            assertEquals(4, lineNumber);
        } catch (IOException e) {
            fail("Erreur lors de la lecture du fichier de sortie");
        }
    }

}