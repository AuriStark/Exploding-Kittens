package tests;

import client.Mensch;
import client.Spieler;
import common.Entscharfung;
import common.KatzenKarte;
import common.PlayedStapel;
import common.Zustand;
import exceptions.SpielraumVollException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Spielraum;

import static org.junit.jupiter.api.Assertions.*;

class KatzenKarteTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void nextSpielerDaran() throws SpielraumVollException {

        Spielraum spielraum = new Spielraum(2,"Testraum");
        spielraum.zustand = new Zustand(2);

        KatzenKarte katzenKarte  = new KatzenKarte();
        PlayedStapel playedStapel = new PlayedStapel();
        playedStapel.addkarte(katzenKarte);

        try {
            katzenKarte.action(spielraum);
        }
        catch (Exception e){}

        assertEquals(0, spielraum.getSpielerNamen().size()); // Tester  2 am Zug.
    }
}