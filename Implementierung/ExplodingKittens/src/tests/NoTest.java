package tests;

import client.Mensch;
import client.Spieler;
import common.Mischen;
import common.No;
import common.PlayedStapel;
import common.Zustand;
import exceptions.SpielraumVollException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Spielraum;

import static org.junit.jupiter.api.Assertions.*;

class NoTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void number_of_no() throws SpielraumVollException {

        Spieler sp1 = new Mensch("Tester1");
        Spieler sp2 = new Mensch("Tester2");
        Spielraum spielraum = new Spielraum(2,"Testraum");
        spielraum.zustand = new Zustand(2);
        spielraum.addSpieler(sp1);
        spielraum.addSpieler(sp2);

        No no  = new No();
        PlayedStapel playedStapel = new PlayedStapel();
        playedStapel.addkarte(no);

        no.action(spielraum);

        assertEquals(1, spielraum.zustand.number_of_nos); // 1 No gespielt
    }

    @Test
    void action() {
        //TODO
    }
}