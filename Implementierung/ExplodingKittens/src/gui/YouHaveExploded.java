package gui;

import client.RemoteSpieler;
import client.Spieler;
import exceptions.DatenBankFehlerException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import server.RemoteSpielraum;
import server.Spielraum;
import server.Verwaltung;

import java.io.IOException;
import java.rmi.RemoteException;

public class YouHaveExploded {

    private String name;
    private Verwaltung verwaltung;
    private String raumName;
    private int anzahlSpieler;
    private Spieler sp;

    @FXML
    public Button spielVerlassen;
    private RemoteSpielraum spr;

    public void setName(String name) {
        this.name = name ;
    }
    public void setSpieler(Spieler sp) { this.sp = sp; }
    public void setVerwaltung(Verwaltung verw) { this.verwaltung = verw;}
    public void setSpielraum(RemoteSpielraum spr){this.spr = spr;}

    public void setContent(String raumName, int anzahlSpieler) {
        this.raumName = raumName;
        this.anzahlSpieler = anzahlSpieler;
    }

    @FXML
    public void spielVerlassen(ActionEvent event) {
        try {
            spr.spielRaumVerlassen(sp.getName());
        }
        catch (RemoteException e){}
        Platform.runLater(() ->{
            try {
                SceneManager.goToHauptmenu(event, name, sp, verwaltung);
            }
            catch (IOException e){}
        });
    }
}
