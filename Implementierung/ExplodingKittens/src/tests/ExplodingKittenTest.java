package tests;

import client.Mensch;
import client.Spieler;
import common.ExplodingKitten;
import common.PlayedStapel;
import common.Zustand;
import exceptions.KartenStapelZuKleinException;
import exceptions.SpielraumVollException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Spielraum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExplodingKittenTest {

    @BeforeEach
    void setUp() {
        Spielraum spielraum = new Spielraum(5, "testRaum");
    }

    @Test
    void stelle_gut() throws KartenStapelZuKleinException {

        Spielraum spielraum = new Spielraum(2,"Testraum");
        spielraum.zustand = new Zustand(2);

        ExplodingKitten explodingKitten  = new ExplodingKitten();
        explodingKitten.stelle = 1;
        PlayedStapel playedStapel = new PlayedStapel();
        playedStapel.addkarte(explodingKitten);

        explodingKitten.action(spielraum);

        assertTrue(ExplodingKitten.class.isInstance(spielraum.zustand.ziehStapel.getkarte(1)));
    }
    
    @Test
    void  actionTest(){
        //TODO
         }
}