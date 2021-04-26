package tests;

import common.*;
import exceptions.KartenStapelZuKleinException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KartenStapelTest {

    @Test
    void getKartenTest1(){
        List<Karte> listKarten  = new ArrayList<>();
        KartenStapel kartenStapel = new KartenStapel(listKarten);

        assertEquals(listKarten,kartenStapel.getKarten());
    }

    @Test
    void getKartenTest2(){
        List<Karte> listKarten  = new ArrayList<>();
        Karte hops = new Hops();
        listKarten.add(hops);
        KartenStapel kartenStapel = new KartenStapel(listKarten);

        assertEquals(listKarten,kartenStapel.getKarten());
    }

    @Test
    void getAnzahlKartenImStapel() {
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
        assertEquals(4,  kartenStapel.getAnzahlKartenImStapel());
    }

    @Test
    void mischenTest() throws KartenStapelZuKleinException {
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

        while(kartenStapel.getkarte(0).equals(no)){
            kartenStapel.mischen();
        }

        assertTrue(!kartenStapel.getkarte(0).equals(no));
    }

    @Test
    void sieheZukunftTestmehrAls3Karten() throws KartenStapelZuKleinException {
        No no = new No();
        Hops hops = new Hops();
        Wunsch wunsch = new Wunsch();
        Entscharfung entscharfung = new Entscharfung() ;
        Angriff angrif = new Angriff();

        List<Karte> listKarten = new ArrayList<>();
        listKarten.add(no);
        listKarten.add(hops);
        listKarten.add(wunsch);
        listKarten.add(entscharfung);
        listKarten.add(angrif);

        KartenStapel kartenStapel = new KartenStapel(listKarten);
        assertTrue(kartenStapel.sieheZukunft().get(0) instanceof Angriff);
        assertTrue(kartenStapel.sieheZukunft().get(1) instanceof Entscharfung);
        assertTrue(kartenStapel.sieheZukunft().get(2) instanceof Wunsch);
        assertEquals(3,kartenStapel.sieheZukunft().size());
        assertEquals(5,kartenStapel.getAnzahlKartenImStapel());
    }
    @Test
    void sieheZukunftTestWenigerAls3karten() throws KartenStapelZuKleinException {
        No no = new No();
        Hops hops = new Hops();

        List<Karte> listKarten = new ArrayList<>();
        listKarten.add(no);
        listKarten.add(hops);

        KartenStapel kartenStapel = new KartenStapel(listKarten);
        assertTrue(kartenStapel.sieheZukunft().get(0) instanceof Hops);
        assertTrue(kartenStapel.sieheZukunft().get(1) instanceof No);
        assertEquals(kartenStapel.sieheZukunft().get(2), null);
        assertEquals(2, kartenStapel.getAnzahlKartenImStapel());
    }

    @Test
    void legenOnTop() throws KartenStapelZuKleinException {
        KartenStapel kartenstapel = new KartenStapel();
        List<Karte> listKarten = new ArrayList<>();
        Karte explodingKitten = new ExplodingKitten();
        Karte angriff = new Angriff();

        listKarten.add(angriff);
        kartenstapel.setKarten(listKarten);
        System.out.println(kartenstapel.getkarte(0));
        kartenstapel.legen(kartenstapel.getAnzahlKartenImStapel(),explodingKitten);
        System.out.println(kartenstapel.getkarte(1));

        assertEquals(2, kartenstapel.getAnzahlKartenImStapel());
        assertEquals(explodingKitten, kartenstapel.getKarten().get(0));
    }

    @Test
    void legenSecond() throws KartenStapelZuKleinException {
        ExplodingKitten explodingKitten = new ExplodingKitten();
        Angriff angriff = new Angriff();
        List<Karte> listKarten = new ArrayList<>();
        KartenStapel kartenStapel = new KartenStapel();

        listKarten.add(angriff);
        kartenStapel.setKarten(listKarten);
        kartenStapel.legen(1, explodingKitten);

        assertEquals(2, kartenStapel.getAnzahlKartenImStapel());
        assertEquals(kartenStapel.getAnzahlKartenImStapel()-2,kartenStapel.getLastIndex()-1);
        assertEquals(explodingKitten, kartenStapel.getkarte(1));
    }

    @Test
    void ziehen() throws  KartenStapelZuKleinException{

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

        assertTrue(kartenStapel.ziehen() instanceof Entscharfung);
        assertTrue(kartenStapel.getkarte(0) instanceof Wunsch);
        assertEquals(3,kartenStapel.getAnzahlKartenImStapel());
    }

    @Test
    void ziehenInLeeresStapel() {
        KartenStapel kartenStapel = new KartenStapel();
        try{
            kartenStapel.ziehen();
        }catch (Exception k){
            fail();
        }
    }

    @Test
    void initExplodeEntscharf(){
        KartenStapel kartenStapel = new KartenStapel();
        int anzahlSpieler = 5;

        kartenStapel.initializeStapel();
        kartenStapel.starten(anzahlSpieler);

        assertEquals(51,kartenStapel.getAnzahlKartenImStapel());
    }

    @Test
    void initExplodeEntscharf2(){
        KartenStapel kartenStapel = new KartenStapel();
        int anzahlSpieler = 2;

        kartenStapel.initializeStapel();
        kartenStapel.starten(anzahlSpieler);

        assertEquals(51,kartenStapel.getAnzahlKartenImStapel());
    }
}