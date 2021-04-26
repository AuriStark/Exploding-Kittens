package tests;

import client.Mensch;
import client.RemoteSpieler;
import client.Spieler;
import exceptions.KartenStapelZuKleinException;
import exceptions.SpielraumVollException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Spielraum;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpielraumTest {

    Spielraum s;

    @BeforeEach
    void setUp(){
        Spielraum s = new Spielraum(1, "Testraum");
    }

    // Initialisierung
    @Test
    void initSpielraum() {
        assertEquals(null, s.getZustand());
    }

    // Spielerverwaltung
    @Test
    void spielerVerwaltung() throws SpielraumVollException, RemoteException {
        // Erstellen
        RemoteSpieler sp1 = new Mensch("Tester1");
        RemoteSpieler sp2 = new Mensch("Tester2");
        s.addSpieler(sp1);
        //Spielraum ist voll
        try {
            s.addSpieler(sp2);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof SpielraumVollException);
        }
        List<RemoteSpieler> spl = s.getSpielerList();
        assertEquals(1, spl.size());
        assertEquals(sp1, spl.get(0));

        // Löschen
        assertTrue(s.spielRaumVerlassen(sp1.getName()));
        assertEquals(0, spl.size());
    }

    // Spiel Starten uns stoppen
    @Test
    void startstop() throws SpielraumVollException, KartenStapelZuKleinException {
        Spieler sp1 = new Mensch("Tester1");
        s.addSpieler(sp1);
        s.spielStarten();
        assertNotEquals(null, s.getZustand());

        // beendet
        s.beenden();
        // assertFalse(s.isAlive())
    }

    // new Test :
    @Test
    void addSpieler_DerSpielerWirdHinzugefuegt() throws SpielraumVollException {
        Spielraum spielraum = new Spielraum(5, "testRaum");
        RemoteSpieler spieler = new Mensch("Test");
        assertEquals(true, spielraum.addSpieler(spieler));
    }

    @Test
    void addSpieler_DerSpielerWirdNichtHinzugefuegt() throws SpielraumVollException {
        Spielraum spielraum = new Spielraum(5, "testRaum");
        for(int i = 0; i < 5; i++){
            Spieler spieler = new Mensch("Test");
            spielraum.addSpieler(spieler);
        }

        assertEquals(false, spielraum.addSpieler(new Mensch("Test")));
    }
}

