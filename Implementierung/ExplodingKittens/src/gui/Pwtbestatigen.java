package gui;

import client.Spieler;
import exceptions.PasswortFalschException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import server.Verwaltung;

import java.io.IOException;

public class Pwtbestatigen {

    public  String name;
    private Spieler sp;
    private Verwaltung verwaltung;

    @FXML
    public PasswordField pwdwiederholen;
    @FXML
    public Button abbrechenButton;
    @FXML
    public Button bestaetigenButton;

    public void bestätigen(ActionEvent event) throws IOException , PasswortFalschException {

        String pwt = pwdwiederholen.getText();

        try{

            if ( verwaltung.kontoLoeschen(name,  pwt)){
                SceneManager.showAlertInfo("Ihr Konto wurde gelöscht");
                SceneManager.goToAnmelden(event);
            } else {
                SceneManager.showAlertInfo("Prüfen Sie bitte die Richtigkeit Ihrem Passwort");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new PasswortFalschException();
        }
    }

    public void abbrechen(ActionEvent event) {
        Stage stage = (Stage) abbrechenButton.getScene().getWindow();
        stage.close();
    }

    public void setName(String n) {
        this.name = n;
    }
    public void setSpieler(Spieler sp) { this.sp = sp; }
    public void setVerwaltung(Verwaltung verw) { this.verwaltung = verw; }

}
