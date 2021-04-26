package tests;

import datenbank.Datenbank;

import exceptions.DatenBankFehlerException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class DatenbankTest {

    // Initialisierung
    @Test
    void testDB() throws DatenBankFehlerException {
        // Init
        Datenbank db = new Datenbank("kittens", "localhost", "postgres", "root");

        // Write and Delete

        db.SpeicherNutzerDaten("Anna", "Kuchen");
        db.addSieg("Anna");
        db.aenderenutzerDaten("Anna","Kuchen", "Cake");
        db.loescheNutzerDaten("Anna", "Cake");

        // Read
        for(String[] s: db.getSiege()){
            System.out.println(Arrays.toString(s));
        }
    }
}
