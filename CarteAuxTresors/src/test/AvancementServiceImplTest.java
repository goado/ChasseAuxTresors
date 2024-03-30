package test;

import enumeration.Orientation;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AvancementService;
import service.AvancementServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AvancementServiceImplTest {

    private AvancementService avancementService;
    private Aventurier aventurier;
    private Situation situation;

    @BeforeEach
    void setUp() {
        this.avancementService = new AvancementServiceImpl();

        this.aventurier = new Aventurier();
        this.aventurier.setPositionX(0);
        this.aventurier.setPositionY(0);
        this.aventurier.setOrientation(Orientation.SUD.getValue());
        this.aventurier.setId(1);
        this.aventurier.setNom("Lara");

        this.situation = new Situation();
        this.situation.setCarte(new Carte(4, 4));
    }

    @Test
    void testAvancerVersSudZeroCollision() {
        // Given

        // When
        this.avancementService.avancer(this.aventurier, this.situation);

        // Then
        assertEquals(0, this.aventurier.getPositionX());
        assertEquals(1, this.aventurier.getPositionY());
        assertFalse(this.aventurier.isBloque());
        assertFalse(this.aventurier.isParcoursFini());
    }

    @Test
    void testAvancerVersEstZeroCollision() {
        // Given
        this.aventurier.setOrientation(Orientation.EST.getValue());

        // When
        this.avancementService.avancer(this.aventurier, this.situation);

        // Then
        assertEquals(1, this.aventurier.getPositionX());
        assertEquals(0, this.aventurier.getPositionY());
        assertFalse(this.aventurier.isBloque());
        assertFalse(this.aventurier.isParcoursFini());
    }

    @Test
    void testAvancerVersOuestZeroCollision() {
        // Given
        this.aventurier.setPositionX(1);
        this.aventurier.setOrientation(Orientation.OUEST.getValue());

        // When
        this.avancementService.avancer(this.aventurier, this.situation);

        // Then
        assertEquals(0, this.aventurier.getPositionX());
        assertEquals(0, this.aventurier.getPositionY());
        assertFalse(this.aventurier.isBloque());
        assertFalse(this.aventurier.isParcoursFini());
    }

    @Test
    void testAvancerVersNordZeroCollision() {
        // Given
        this.aventurier.setPositionY(1);
        this.aventurier.setOrientation(Orientation.NORD.getValue());

        // When
        this.avancementService.avancer(this.aventurier, this.situation);

        // Then
        assertEquals(0, this.aventurier.getPositionX());
        assertEquals(0, this.aventurier.getPositionY());
        assertFalse(this.aventurier.isBloque());
        assertFalse(this.aventurier.isParcoursFini());
    }

    @Test
    void testAvancerCollisionMontagne() {
        // Given
        this.situation.setMontagnes(Collections.singletonList(new Montagne(0, 1)));

        // When
        this.avancementService.avancer(this.aventurier, this.situation);

        // Then
        assertEquals(0, this.aventurier.getPositionX());
        assertEquals(0, this.aventurier.getPositionY());
        assertTrue(this.aventurier.isBloque());
        assertTrue(this.aventurier.isParcoursFini());
    }

    @Test
    void testAvancerRecuperationTresor() {
        // Given
        Tresor tresor = new Tresor(0, 1, 1);
        this.situation.setTresors(Collections.singletonList(tresor));

        // When
        this.avancementService.avancer(this.aventurier, this.situation);

        // Then
        assertEquals(0, this.aventurier.getPositionX());
        assertEquals(1, this.aventurier.getPositionY());
        assertEquals(1, this.aventurier.getNombreTresorsRamasses());
        assertEquals(0, tresor.getNombreTresorsRestant());
        assertFalse(this.aventurier.isBloque());
        assertFalse(this.aventurier.isParcoursFini());
    }

    @Test
    void testAvancerCollisionFrontiere() {
        // Given
        this.aventurier.setOrientation(Orientation.NORD.getValue());

        // When
        this.avancementService.avancer(this.aventurier, this.situation);

        // Then
        assertEquals(0, this.aventurier.getPositionX());
        assertEquals(0, this.aventurier.getPositionY());
        assertTrue(this.aventurier.isBloque());
        assertTrue(this.aventurier.isParcoursFini());
    }

    // TU collision de face avec aventurier
    @Test
    void testAvancerCollisionFrontaleAvecAventurier() {
        // Given
        Aventurier aventurierEnOpposition = new Aventurier();
        aventurierEnOpposition.setPositionX(0);
        aventurierEnOpposition.setPositionY(1);
        aventurierEnOpposition.setOrientation(Orientation.NORD.getValue());
        aventurierEnOpposition.setNom("John");
        aventurierEnOpposition.setId(2);

        List<Aventurier> aventurierList = new ArrayList<>();
        aventurierList.add(this.aventurier);
        aventurierList.add(aventurierEnOpposition);
        this.situation.setAventuriers(aventurierList);


        // When
        this.avancementService.avancer(this.aventurier, this.situation);

        // Then
        assertEquals(0, this.aventurier.getPositionX());
        assertEquals(0, this.aventurier.getPositionY());
        assertEquals(0, aventurierEnOpposition.getPositionX());
        assertEquals(1, aventurierEnOpposition.getPositionY());
        assertTrue(this.aventurier.isBloque());
        assertTrue(this.aventurier.isParcoursFini());
        assertTrue(aventurierEnOpposition.isBloque());
        assertTrue(aventurierEnOpposition.isParcoursFini());
    }

    @Test
    void testAvancerCollisionSimpleAvecAventurier() {
        // Given
        Aventurier aventurierEnOpposition = new Aventurier();
        aventurierEnOpposition.setPositionX(0);
        aventurierEnOpposition.setPositionY(1);
        aventurierEnOpposition.setOrientation(Orientation.EST.getValue());
        aventurierEnOpposition.setNom("John");
        aventurierEnOpposition.setId(2);
        aventurierEnOpposition.setAFiniParcours(false);
        aventurierEnOpposition.setBloqueTemporairement(false);
        aventurierEnOpposition.setBloque(false);

        List<Aventurier> aventurierList = new ArrayList<>();
        aventurierList.add(this.aventurier);
        aventurierList.add(aventurierEnOpposition);
        this.situation.setAventuriers(aventurierList);

        // When
        this.avancementService.avancer(this.aventurier, this.situation);

        // Then
        assertEquals(0, this.aventurier.getPositionX());
        assertEquals(0, this.aventurier.getPositionY());
        assertEquals(0, aventurierEnOpposition.getPositionX());
        assertEquals(1, aventurierEnOpposition.getPositionY());
        assertFalse(this.aventurier.isParcoursFini());
        assertFalse(this.aventurier.isBloque());
    }

    @Test
    void testAvancerCollisionSimpleAvecAventurierBloque() {
        // Given
        Aventurier aventurierEnOpposition = new Aventurier();
        aventurierEnOpposition.setPositionX(0);
        aventurierEnOpposition.setPositionY(1);
        aventurierEnOpposition.setOrientation(Orientation.EST.getValue());
        aventurierEnOpposition.setNom("John");
        aventurierEnOpposition.setId(2);
        aventurierEnOpposition.setAFiniParcours(true);
        aventurierEnOpposition.setBloque(true);

        List<Aventurier> aventurierList = new ArrayList<>();
        aventurierList.add(this.aventurier);
        aventurierList.add(aventurierEnOpposition);
        this.situation.setAventuriers(aventurierList);

        // When
        this.avancementService.avancer(this.aventurier, this.situation);

        // Then
        assertEquals(0, this.aventurier.getPositionX());
        assertEquals(0, this.aventurier.getPositionY());
        assertEquals(0, aventurierEnOpposition.getPositionX());
        assertEquals(1, aventurierEnOpposition.getPositionY());
        assertTrue(this.aventurier.isParcoursFini());
        assertTrue(this.aventurier.isBloque());
    }
}