package tests;

import client.Mensch;
import client.Spieler;
import datenbank.Datenbank;
import exceptions.DatenBankFehlerException;
import exceptions.DoppelterEintragException;
import exceptions.NameFalschException;
import exceptions.PasswortFalschException;
import org.junit.jupiter.api.Test;
import server.RemoteSpielraum;
import server.Verwaltung;
import server.VerwaltungImpl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerwaltungTest {

    Datenbank db;
    Verwaltung v;

    // Initialisierung
    @Test
    void initVerwaltung() throws RemoteException{
        // Mockup einer Datenbank, die leer ist oder die Beispieldaten enthält.
        Datenbank db = new DatenbankSTUB();
        Verwaltung v = new VerwaltungImpl(db);

        assertEquals(0, v.getSpielRaume().size());
    }

    // Spielraumverwaltung
    @Test
    void spielraumVerwaltung() throws DatenBankFehlerException, RemoteException {
        // Mockup einer Datenbank, die leer ist oder die Beispieldaten enthält.
        Datenbank db = new DatenbankSTUB();
        Verwaltung v = new VerwaltungImpl(db);

        // Erstellen
        v.neuerSpielraum(1, "test");
        List<RemoteSpielraum> raume = v.getSpielRaume();
        assertEquals(1, raume.size());
        assertEquals("test", raume.get(0).getName());

        // Löschen
        v.deleteSpielraum("test");
        assertEquals(0, v.getSpielRaume().size());
    }

    // Loginfunktionen
    @Test
    void loginVerwaltung() throws DoppelterEintragException, NameFalschException, PasswortFalschException, RemoteException {
        // Mockup einer Datenbank, die leer ist oder die Beispieldaten enthält.
        Datenbank db = new DatenbankSTUB();
        Verwaltung v = new VerwaltungImpl(db);

        String name = "Tester200";
        String pwd = "password200";
        Spieler sp = new Mensch(name);

        // Registrieren
        v.registrieren(name, pwd);
        // Nicht doppelt
        try {
            v.registrieren(name, pwd);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof DoppelterEintragException);
        }

        // Anmelden
        assertTrue(v.anmelden(name, pwd, sp.getName()));
        assertTrue(v.getSpielerListe().contains(sp));
        // Falsches Anmelden führt zu Fehler
        try {
            v.anmelden(name, "password", "localhost");
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof NameFalschException);
        }
        try {
            v.anmelden("Tester", pwd, sp.getName());
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof PasswortFalschException);
        }

        // Abmelden
        v.abmelden(name);
        assertFalse(v.getSpielerListe().contains(sp));
    }

    // Konto Löschen
    @Test
    void loeschenVerwaltung() throws NameFalschException, PasswortFalschException, RemoteException {
        // Mockup einer Datenbank, die leer ist oder die Beispieldaten enthält.
        Datenbank db = new DatenbankSTUB();
        Verwaltung v = new VerwaltungImpl(db);

        String name = "Tester200";
        String pwd = "password200";
        Spieler sp = new Mensch(name);
        v.anmelden(name, pwd, sp.getName());

        // Konto löschen
        assertTrue(v.kontoLoeschen(name, pwd));

        // Erneut anmelden nicht möglich, da Daten gelöscht
        try{
            v.anmelden(name, pwd, sp.getName());
            fail();
        }
        catch (Exception e) {
            assertTrue(e instanceof NameFalschException);
        }
    }
}

// Mock einer Datenbank
class DatenbankSTUB extends Datenbank{

    private boolean saved;

    public DatenbankSTUB(){
        saved = false;
    }

    @Override
    public void SpeicherNutzerDaten(String name, String pwd){
        saved = true;
    }


    @Override
    public List<String[]> leseNutzerDaten(){
        List<String[]> out = new ArrayList<>();
        if(saved){
            out.add(new String[]{"Tester200", "password200"});
        }
        return out;
    }

    @Override
    public void loescheNutzerDaten(String name, String pwd){
        saved = false;
    }

}