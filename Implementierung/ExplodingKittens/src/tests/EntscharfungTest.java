package tests;

import client.Mensch;
import client.Spieler;
import common.*;
import exceptions.SpielraumVollException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Spielraum;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntscharfungTest {

    @BeforeEach
    void setUp() {
        Spielraum spielraum = new Spielraum(5,"testRaum");
    }


    /**
     * Testet ob Entschärfung funktionniert,
     * wenn der Spieler gerade eine exploding Karte gezogen
     * (bzw  im played stapel gelegt hat)
     */
    @Test
    void actionTest() throws SpielraumVollException{

        Spielraum spielraum = new Spielraum(2,"Testraum");
        spielraum.zustand = new Zustand(2);

        spielraum.zustand.ausgespielt = new ExplodingKitten();

        Entscharfung entscharfung  = new Entscharfung();

        entscharfung.action(spielraum);

        assertEquals(null, spielraum.zustand.ausgespielt);
    }
}