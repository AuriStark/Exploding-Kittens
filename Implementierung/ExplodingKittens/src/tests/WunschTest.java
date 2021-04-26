package tests;

import client.Mensch;
import client.Spieler;
import common.Mischen;
import common.PlayedStapel;
import common.Wunsch;
import exceptions.SpielraumVollException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Spielraum;

import static org.junit.jupiter.api.Assertions.*;

class WunschTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void nextSpielerDaran() throws SpielraumVollException {

        Spieler sp1 = new Mensch("Tester1");
        Spieler sp2 = new Mensch("Tester2");
        Spielraum spielraum = new Spielraum(2,"Testraum");
        spielraum.addSpieler(sp1);
        spielraum.addSpieler(sp2);

        Wunsch wunsch  = new Wunsch();
        PlayedStapel playedStapel = new PlayedStapel();
        playedStapel.addkarte(wunsch);

        wunsch.action(spielraum);

        assertEquals(2, spielraum.getAmZug()); // Tester  2 am Zug.
    }

    @Test
    void action() {
        //TODO
    }
}