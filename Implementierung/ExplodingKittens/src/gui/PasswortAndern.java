
package gui;

import java.io.IOException;

import client.Spieler;
import exceptions.DatenBankFehlerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import server.Verwaltung;

public class PasswortAndern {

    public String name;
    private Spieler sp;
    private Verwaltung verwaltung;

    @FXML
    PasswordField neuespwd = new PasswordField();
    @FXML
    PasswordField altespwd = new PasswordField();
    @FXML
    PasswordField pwdwiederholen  = new PasswordField();
    @FXML
    Button abbrechenButton = new Button();
    @FXML
    Button bestaetigenButton = new Button();

    @FXML
    private void abbrechen(ActionEvent event) throws IOException {//dann ändere passwort
        Stage stage = (Stage) abbrechenButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void bestätigen(ActionEvent event) throws IOException, DatenBankFehlerException {

         String wiederP = pwdwiederholen.getText();
         String neuesP = neuespwd.getText();
         String  altesP = altespwd.getText();

        if (!wiederP.isEmpty() && !neuesP.isEmpty() && !altesP.isEmpty()){

            System.out.println(neuesP + ' ' + wiederP +' ' + altesP);

           if(verwaltung.passwortAendern(name,altesP,neuesP)){

                SceneManager.showAlertInfo("Sie haben Ihr passwort erfolgreich geändert. melden sie sich erneut mit dem Neuen Passwort an.");
                SceneManager.goToAnmelden(event);
           } else{
                SceneManager.showAlertInfo("Ihr passwort ist entweder falsch, oder das wiederholte Passwort it dem neuen Passwort stimmt nicht überein.");
            }
        } else{
            if (altesP.isEmpty() || neuesP.isEmpty() || wiederP.isEmpty()){
                SceneManager.showAlertInfo("Bitte füllen Sie alle Felder aus.");
            }
        }
    }

    public void setName(String n){
        this.name = n;
    }
    public void setSpieler(Spieler sp) { this.sp = sp; }
    public void setVerwaltung(Verwaltung verw) { this.verwaltung = verw; }
}
