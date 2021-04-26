package gui;

import client.Spieler;
import exceptions.SpielraumVollException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.RemoteSpielraum;
import server.Verwaltung;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.Scanner;

public class SpielraumPwtModal {

    public String name;
    public String raumName;
    public int anzahlSpieler;

    private Spieler sp;
    private Verwaltung verwaltung;

    // Optional
    @FXML
    public Button neinAbrechen;

	@FXML
    public void jaBeitreten(ActionEvent event) throws IOException {
	    try {
            //Lese die ip-Einstellungen
            Scanner sc = new Scanner(new File("src/ips.set"));
            String client_ip = sc.nextLine().replace("client", "").replace("=", "").replace(" ", "");
            String server_ip = sc.nextLine().replace("server", "").replace("=", "").replace(" ", "");
            sc.close();
            Registry reg = LocateRegistry.getRegistry(server_ip);
            RemoteSpielraum spielraum = (RemoteSpielraum) reg.lookup(raumName);
            spielraum.addSpieler(sp);
            SceneManager.goToWarteraum(event, name, raumName, anzahlSpieler, sp, spielraum, verwaltung);
        }
        catch (NotBoundException e){
            e.printStackTrace();
        }
	    catch (SpielraumVollException e){
	        e.printStackTrace();
	        showAlertConfirmation("Spielraum ist voll, wähle einen anderen", "Voll", event);
	        // todo: wait?
        }
	    // Zurück zur Lobby
        SceneManager.goToLobby(event, name, sp, verwaltung);
    }
	@FXML
    public void neinAbrechen(ActionEvent event) throws IOException {
	    SceneManager.goToLobby(event,name, sp,verwaltung);
    }

    public void setName(String name) {
	    this.name = name;
    }
    public void setSpieler(Spieler sp) { this.sp = sp; }
    public void setVerwaltung(Verwaltung verw) { this.verwaltung = verw;}

    public void setContent(String raumName, int anzahlSpieler) {
        this.raumName = raumName;
        this.anzahlSpieler = anzahlSpieler;
    }

    public void showAlertConfirmation( String message,String title, ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setTitle(title);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Hauptmenu.class.getResource("img/explosion.png").toString()));

        Optional<ButtonType> result = alert.showAndWait();
        stage.close();
    }
}
