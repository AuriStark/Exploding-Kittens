package tests;

import client.Mensch;
import client.Spieler;
import common.Angriff;
import common.PlayedStapel;
import common.Zustand;
import exceptions.SpielraumVollException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Spielraum;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AngriffTest {

    Spielraum spielraum ;
    PlayedStapel playedStapel;
    Spieler sp1 ;
    Spieler sp2 ;
    Angriff angriff;

    @BeforeEach
    void setUp() {
        spielraum = new Spielraum(2, "Testraum");
        spielraum.zustand = new Zustand(2);
    }

    @Test
    void nextSpieltZeiMal() throws SpielraumVollException {

        angriff = new Angriff();

        angriff.action(spielraum);

        assertEquals(2, spielraum.getAnzahlZuegeNaechster()); // 2-Mal spielen für nächsten Spieler
    }
}