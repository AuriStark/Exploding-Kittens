package tests;

import client.Mensch;
import client.Spieler;
import common.Hops;
import common.PlayedStapel;
import exceptions.SpielraumVollException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Spielraum;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HopsTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void nextSpielerDaran(){

        Spielraum spielraum = new Spielraum(2,"Testraum");

        Hops hops  = new Hops();

        hops.action(spielraum);

        assertEquals(0, spielraum.getAnzahlZuege()); // Tester  2 am Zug.
    }
}