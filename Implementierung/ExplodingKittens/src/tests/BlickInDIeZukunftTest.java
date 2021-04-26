package tests;

import client.Mensch;
import client.Spieler;
import common.BlickInDIeZukunft;
import common.PlayedStapel;
import common.Zustand;
import exceptions.SpielraumVollException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Spielraum;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlickInDIeZukunftTest {

    @BeforeEach
    void setUp() {
        Spielraum spielraum = new Spielraum(5, "testRaum");
    }


    @Test
    void nextSpielerDaran() throws SpielraumVollException {

        Spielraum spielraum = new Spielraum(2,"Testraum");
        spielraum.zustand = new Zustand(2);

        BlickInDIeZukunft blickInDIeZukunft  = new BlickInDIeZukunft();

        blickInDIeZukunft.action(spielraum);

        // Zukunft korrekt gespeichert
        assertEquals(spielraum.zustand.zukunft, spielraum.zustand.ziehStapel.sieheZukunft());
    }

    @Test
    void getDreiErsteKartenImStapel() {
        //Genausowie KartenStapel.sieheDieZukunft
    }
}