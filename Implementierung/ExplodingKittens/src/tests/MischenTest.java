package tests;

import client.Mensch;
import client.Spieler;
import common.*;
import exceptions.KartenStapelZuKleinException;
import exceptions.SpielraumVollException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Spielraum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MischenTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void mischen(){

        Spielraum spielraum = new Spielraum(2,"Testraum");
        spielraum.zustand = new Zustand(2);
        KartenStapel st = spielraum.zustand.ziehStapel;

        Mischen mischen  = new Mischen();
        PlayedStapel playedStapel = new PlayedStapel();
        playedStapel.addkarte(mischen);

        mischen.action(spielraum);

        // Mischen ändert die Anzahl nicht
        assertEquals(st.getAnzahlKartenImStapel(), spielraum.zustand.ziehStapel.getAnzahlKartenImStapel());
    }

    /**
     * Test skip nach attack gespielt.
     */
    @Test
    void kleiner_Stapel() throws KartenStapelZuKleinException {
        Spielraum sp = new Spielraum(5, "testRaum");
        sp.zustand = new Zustand(2);

        No no = new No();
        Hops hops = new Hops();
        Wunsch wunsch = new Wunsch();
        Entscharfung entscharfung = new Entscharfung() ;
        List<Karte> listKarten = new ArrayList<>();
        listKarten.add(no);
        listKarten.add(hops);
        listKarten.add(wunsch);
        listKarten.add(entscharfung);
        KartenStapel kartenStapel = new KartenStapel(listKarten);

        sp.zustand.ziehStapel = kartenStapel;

        Mischen mischen = new Mischen();
        PlayedStapel playedStapel = new PlayedStapel();

        playedStapel.addkarte(mischen);
        mischen.action(sp);

        assertEquals(4, kartenStapel.getAnzahlKartenImStapel());

    }
}