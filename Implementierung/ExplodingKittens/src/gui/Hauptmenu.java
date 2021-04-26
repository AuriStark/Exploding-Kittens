package gui;

import client.Spieler;
import exceptions.DatenBankFehlerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.Spielraum;
import server.Verwaltung;

import java.io.IOException;
import java.util.Optional;

public class Hauptmenu {

    public String name;
    private Spieler sp;
    private Verwaltung verwaltung;

    @FXML
    public void spielGegenFreunde(ActionEvent event) throws IOException {
        SceneManager.goToLobby(event, name, sp, verwaltung);
    }
    @FXML
    public void spielGegenComputer(ActionEvent event) throws IOException  {
        // Geht über Lobby
        SceneManager.goToLobby(event, name, sp, verwaltung);
    }
    @FXML
    public void sieheRegeln(ActionEvent event) throws IOException {
        SceneManager.goToRegeln(event, name, sp, verwaltung);
    }
    @FXML
    public void sieheProfil(ActionEvent event) throws IOException, DatenBankFehlerException {
        SceneManager.goToProfil(event, name, sp, verwaltung);
    }
    @FXML
    public void spielerAbmelden(ActionEvent event) throws IOException {
       showAlertConfirmation("Möchten Sie sich wirklich abmelden ?", "Abmelden", event);
    }

    public void setName(String n){
        this.name = n;
    }

    public void setSpieler(Spieler sp) { this.sp = sp; }

    public void setVerwaltung(Verwaltung verw) { this.verwaltung = verw;}

    public void showAlertConfirmation( String message,String title, ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setTitle(title);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Hauptmenu.class.getResource("img/explosion.png").toString()));

        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent()){
            // alert is exited, no button has been pressed.
        } else if(result.get() == ButtonType.OK){
            if (verwaltung.abmelden(name)){
                System.out.println( name  + " Spieler entfernt");
                SceneManager.goToAnmelden(event);
            }else{
                System.out.println( name  + " Spieler nicht entfernt");
            }
        } else if(result.get() == ButtonType.CANCEL){
            stage.close();
        }
    }
}
