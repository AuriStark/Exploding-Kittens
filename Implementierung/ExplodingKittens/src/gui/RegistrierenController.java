package gui;

import client.Mensch;
import client.RemoteSpieler;
import client.Spieler;
import exceptions.DoppelterEintragException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import server.Verwaltung;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RegistrierenController {
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField benutzerNameField;
    @FXML
    Label meldung = new Label();

    Verwaltung verwaltung;

    @FXML
    public void spielerRegistrieren(ActionEvent event) throws IOException, DoppelterEintragException, InterruptedException {

        try {
            //Lese die ip-Einstellungen
            Scanner sc = new Scanner(new File(gui.Main.set_path + "ips.set"));
            String client_ip = sc.nextLine().replace("client", "").replace("=", "").replace(" ", "");
            String server_ip = sc.nextLine().replace("server", "").replace("=", "").replace(" ", "");
            sc.close();

            // Establish Connection to the Remote Server
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            System.out.println("RMI Verbindung wird aufgebaut");
            Registry reg = LocateRegistry.getRegistry(server_ip);
            System.out.println("Remote Klassen werden geladen");
            verwaltung = (Verwaltung) reg.lookup("Verwaltung");

            String benutzername = benutzerNameField.getText();
            String password = passwordField.getText();

            if (!benutzername.isEmpty() && !password.isEmpty()) {
                System.out.println(benutzername + ' ' + password);

                // Erstelle das Spielerobjekt für den Spieler und registriere es in RMI
                if (!client_ip.equals("127.0.0.1")){
                    System.setProperty("java.rmi.server.hostname", client_ip);
                }
                Spieler sp = new Mensch(benutzername);
                RemoteSpieler stub = (RemoteSpieler) UnicastRemoteObject.exportObject(sp, 0);

                reg = LocateRegistry.getRegistry(1099);
                try {
                    reg.bind(benutzername, stub);
                }
                // Passiert wenn man sich ab und wieder annmeldet oder con zwei Rechner, wir nehmen an zweiteres passiert nicht.
                catch (AlreadyBoundException e){}

                if (verwaltung.registrieren(benutzername, password)) {
                    getMeldung("Sie haben sich erfolgreich registriert!");
                    Thread.sleep(5000);
                    SceneManager.goToAnmelden(event);
                } else getMeldung("Dieser Benutzername ist bereits vergeben!");
            } else {
                if (benutzername.isEmpty() && !password.isEmpty()) getMeldung("Füllen Sie bitte Feld Benutzername aus!");
                else if (!benutzername.isEmpty()) getMeldung(" Füllen Sie bitte das Feld Password aus!");
                else getMeldung("Füllen Sie bitte alle Felder aus!");
            }
        }
        catch (FileNotFoundException e) {
            getMeldung("Konnte ips.set nicht finden, wurde sie gelöscht?");
        }
        catch (NotBoundException e){
            getMeldung("Fehler im RMI Server");
        }
    }

    @FXML
    public void sichAnmelden(ActionEvent event) throws IOException {
        SceneManager.goToAnmelden(event);
    }

    public void getMeldung(String message) {
        meldung.setText(message);
    }
}
