package tests;

import common.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HandKartenTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void initialiseHandKarteTest(){
        List<Karte> listKartenInHand = new ArrayList<Karte>();

        BlickInDIeZukunft  blickInDIeZukunft= new BlickInDIeZukunft();
        Entscharfung entscharfung = new Entscharfung();
        Angriff angriff = new Angriff();
        No no = new No();
        Hops hops = new Hops();

        listKartenInHand.add(entscharfung);
        listKartenInHand.add(angriff);
        listKartenInHand.add(blickInDIeZukunft);
        listKartenInHand.add(hops);
        listKartenInHand.add(no);

        assertEquals(5, listKartenInHand.size());
        assertTrue(listKartenInHand.size() != 0);
    }

    @Test
    void getHandKarteTest() {
        List<Karte> listKartenInHand = new ArrayList<Karte>();

        Entscharfung entscharfung = new Entscharfung();
        Angriff angriff = new Angriff();

        listKartenInHand.add(entscharfung);
        listKartenInHand.add(angriff);

        HandKarten handkarten = new HandKarten(listKartenInHand);

        assertEquals(listKartenInHand, handkarten.getHandKarte());
    }

    @Test
    void getHandKarteCountTest() {
        List<Karte> listKartenInHand = new ArrayList<Karte>();

        Entscharfung entscharfung = new Entscharfung();
        Angriff angriff = new Angriff();

        listKartenInHand.add(entscharfung);
        listKartenInHand.add(angriff);

        HandKarten handkarten = new HandKarten(listKartenInHand);

        int size = handkarten.getHandKarteCount();

        assertTrue(size != 0);
        assertTrue(size == 2);
        assertEquals(listKartenInHand.size(), size);

    }

    @Test
    void addKarteTest() {
        List<Karte> listKartenInHand = new ArrayList<Karte>();
        HandKarten handkarten = new HandKarten();

        Entscharfung entscharfung = new Entscharfung();
        Angriff angriff = new Angriff();

        listKartenInHand.add(entscharfung);
        listKartenInHand.add(angriff);

        handkarten.addkarte(entscharfung);
        handkarten.addkarte(angriff);

        assertEquals(listKartenInHand, handkarten.getHandKarte());
        assertTrue(handkarten.getHandKarteCount() == 2);
        assertTrue(handkarten.getHandKarteCount() != 0);
    }

    @Test
    void addKartetest2(){
        List<Karte> listKartenInHand = new ArrayList<Karte>();
        HandKarten handkarten = new HandKarten();

        Entscharfung entscharfung = new Entscharfung();
        Angriff angriff = new Angriff();

        listKartenInHand.add(entscharfung);
        listKartenInHand.add(angriff);

        boolean added1 = handkarten.addkarte(entscharfung);

        assertTrue(added1);
    }

    @Test
    void addKarteTest3(){
        List<Karte> listKartenInHand = new ArrayList<Karte>();
        HandKarten handkarten = new HandKarten();

        Entscharfung entscharfung = new Entscharfung();
        Angriff angriff = new Angriff();

        listKartenInHand.add(entscharfung);
        listKartenInHand.add(angriff);

        boolean added1 = handkarten.addkarte(entscharfung);
        boolean added2 = handkarten.addkarte(angriff);

        assertTrue(added1);
        assertTrue(added2);
    }

    @Test
    void removeKarteTest() {
        List<Karte> listKartenInHand = new ArrayList<Karte>();

        Entscharfung entscharfung = new Entscharfung();
        Angriff angriff = new Angriff();

        listKartenInHand.add(entscharfung);
        listKartenInHand.add(angriff);
        listKartenInHand.remove(angriff);

        HandKarten handkarten = new HandKarten(listKartenInHand);

        assertEquals(listKartenInHand.size(),handkarten.getHandKarteCount());
        assertEquals(entscharfung, handkarten.getHandKarte().get(0));
        assertEquals(listKartenInHand, handkarten.getHandKarte());
        assertTrue(handkarten != null);
    }
}