package gui;

import client.Spieler;
import exceptions.DatenBankFehlerException;
import exceptions.SpielraumVollException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.RemoteSpielraum;
import server.Verwaltung;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class SpielraumErstellen{

    public String name;
    private Spieler sp;
    private Verwaltung verwaltung;

    @FXML
    public Button abrechenButton;
    @FXML
    private ChoiceBox<String> anzahlSpielerBox;
    @FXML
    private Button erstellenButton;
    @FXML
    public TextField raumNameField;
    @FXML
    public PasswordField pwdField;

    public void initialize() {
        anzahlSpielerBox.getItems().addAll("2", "3", "4", "5");
    }

    @FXML
    public void neienAbrechen(ActionEvent event) throws IOException {
        Stage stage = (Stage) abrechenButton.getScene().getWindow();
        stage.close();
        SceneManager.goToLobby(event, name, sp, verwaltung);
    }

    @FXML
    public void jaErstellen(ActionEvent event) throws IOException, SpielraumVollException, DatenBankFehlerException {
        String raumName = raumNameField.getText();
        String  anzahl = anzahlSpielerBox.getValue();

        if (raumName.isEmpty()) {
            SceneManager.showAlertInfo("Bitte einen Namen für den Spielraum angeben");
        }
        else if (anzahl.isEmpty()){
            SceneManager.showAlertInfo("Bitte einen Namen für den Spielraum angeben");
        }
        else {
            System.out.println(anzahl);
            int anzahlSpieler = Integer.valueOf(anzahl);

            // Lese die ip-Einstellungen
            Scanner sc = new Scanner(new File(gui.Main.set_path + "ips.set"));
            String client_ip = sc.nextLine().replace("client", "").replace("=", "").replace(" ", "");
            String server_ip = sc.nextLine().replace("server", "").replace("=", "").replace(" ", "");
            sc.close();
            Registry reg = LocateRegistry.getRegistry(server_ip);

            verwaltung.neuerSpielraum(anzahlSpieler, raumName);
            try {
                RemoteSpielraum spielraum = (RemoteSpielraum) reg.lookup(raumName);
                spielraum.addSpieler(sp);
                SceneManager.goToWarteraum(event, name, raumName, anzahlSpieler, sp, spielraum, verwaltung);
            }
            catch (NotBoundException e){
                e.printStackTrace();
                throw new DatenBankFehlerException();
            }
        }
    }

    public void setName(String name){
        this.name = name;
    }
    public void setSpieler(Spieler sp) { this.sp = sp; }
    public void setVerwaltung(Verwaltung verw) { this.verwaltung = verw;}
}
