package gui;

import client.Mensch;
import client.RemoteSpieler;
import client.Spieler;
import common.ExplodingKitten;
import common.KartenStapel;
import exceptions.KarteZuruckLegenException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import server.Verwaltung;

import java.util.Random;

public class HideKitten {

    private String name;
    private Verwaltung verwaltung;
    private String raumName;
    private int anzahlSpieler;
    private Spieler sp;
    private KartenStapel kartenStapel;
    public SpielraumController sprc;

    @FXML
    public Button ersteStelle;
    @FXML
    public Button secondStelle;
    @FXML
    public Button funfteStelle;
    @FXML
    public Button vierteStelle;
    @FXML
    public Button dritteStelle;
    @FXML
    public Button randomStelle;
    @FXML
    public Button ganzUnten;

    // Platz wählen und Fenster schliesen
    @FXML
    public void explodingOnTop(ActionEvent event) {
        ((Mensch) sp).set_karte(0);
        ((Stage) ersteStelle.getScene().getWindow()).close();
    }

    @FXML
    public void explodingOnSecond(ActionEvent event) {
        ((Mensch) sp).set_karte(1);
        ((Stage) ersteStelle.getScene().getWindow()).close();
    }

    @FXML
    public void explodingOnThird(ActionEvent event) {
        ((Mensch) sp).set_karte(2);
        ((Stage) ersteStelle.getScene().getWindow()).close();
    }

    @FXML
    public void explodingOnFifth(ActionEvent event) {
        ((Mensch) sp).set_karte(4);
        ((Stage) ersteStelle.getScene().getWindow()).close();
    }

    @FXML
    public void explodingOnFourth(ActionEvent event) {
        ((Mensch) sp).set_karte(3);
        ((Stage) ersteStelle.getScene().getWindow()).close();
    }

    @FXML
    public void explodingRandom(ActionEvent event) {
        ((Mensch) sp).set_karte(getRandomNumber(0, kartenStapel.getAnzahlKartenImStapel()));
        ((Stage) ersteStelle.getScene().getWindow()).close();
    }

    @FXML
    public void explodingOnBottom(ActionEvent event) {
        ((Mensch) sp).set_karte(kartenStapel.getAnzahlKartenImStapel());
        ((Stage) ersteStelle.getScene().getWindow()).close();
    }

    public void setName(String name) {
        this.name = name ;
    }
    public void setSpieler(Spieler sp) { this.sp = sp; }
    public void setVerwaltung(Verwaltung verw) { this.verwaltung = verw;}

    public void setContent(String raumName, int anzahlSpieler) {
        this.raumName = raumName;
        this.anzahlSpieler = anzahlSpieler;
    }

    public int getRandomNumber(int min , int max){
        Random r = new Random();
        int result = r.nextInt(max-min) + min;
        return result;
    }
}
