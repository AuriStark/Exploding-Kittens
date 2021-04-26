package tests;

import common.Entscharfung;
import common.ExplodingKitten;
import common.Karte;
import common.PlayedStapel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayedStapelTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getKartenCountTest() {
        Entscharfung entscharfung = new Entscharfung();
        ExplodingKitten exploding = new ExplodingKitten();

        List<Karte> listPlayedKarten = new ArrayList<Karte>();
        listPlayedKarten.add(exploding);
        listPlayedKarten.add(entscharfung);

        PlayedStapel playedKarten = new PlayedStapel(listPlayedKarten);

        assertEquals(2, playedKarten.getKartenCount());
    }

    @Test
    void getkarteTest() {
        Entscharfung entscharfung = new Entscharfung();
        ExplodingKitten exploding = new ExplodingKitten();

        List<Karte> listPlayedKarten = new ArrayList<Karte>();
        listPlayedKarten.add(exploding);
        listPlayedKarten.add(entscharfung);

        PlayedStapel playedKarten = new PlayedStapel(listPlayedKarten);

        assertEquals(exploding, playedKarten.getkarte(0,listPlayedKarten));
        assertEquals(entscharfung, playedKarten.getkarte(playedKarten.getLastIndex(),listPlayedKarten));
    }

    @Test
    void getLastIndexTest() {
        Entscharfung entscharfung = new Entscharfung();
        ExplodingKitten exploding = new ExplodingKitten();

        List<Karte> listPlayedKarten = new ArrayList<Karte>();
        listPlayedKarten.add(exploding);
        listPlayedKarten.add(entscharfung);

        PlayedStapel playedKarten = new PlayedStapel(listPlayedKarten);

        assertEquals(1, playedKarten.getLastIndex());
    }

    @Test
    void addkarteTest() {
        Entscharfung entscharfung = new Entscharfung();
        ExplodingKitten exploding = new ExplodingKitten();

        List<Karte> listPlayedKarten = new ArrayList<Karte>();
        listPlayedKarten.add(exploding);
        listPlayedKarten.add(entscharfung);

        PlayedStapel playedKarten = new PlayedStapel(listPlayedKarten);
        playedKarten.addkarte(exploding);

        assertEquals(true, playedKarten.addkarte(entscharfung));
    }
}