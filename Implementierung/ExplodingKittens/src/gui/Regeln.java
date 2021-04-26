
package gui;


import java.io.IOException;

import client.Spieler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import server.Verwaltung;


public class Regeln {
    public String name;
    private Spieler sp;
    private Verwaltung verwaltung;

    @FXML
    private Button zuruckBuuton;

    @FXML
    private void zuruck(ActionEvent event) throws IOException {
        SceneManager.goToHauptmenu(event, name, sp, verwaltung);
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSpieler(Spieler sp) { this.sp = sp; }
    public void setVerwaltung(Verwaltung verw) { this.verwaltung = verw;}
}
