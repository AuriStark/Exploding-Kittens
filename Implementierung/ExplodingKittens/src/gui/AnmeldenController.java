package gui;

import client.Mensch;
import client.RemoteSpieler;
import client.Spieler;
import exceptions.NameFalschException;
import exceptions.PasswortFalschException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

public class AnmeldenController {

    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField benutzernameField;
    @FXML
    public Button anmeldenBtn;
    @FXML
    Label fehlerMeldung = new Label();

    Verwaltung verwaltung;

    public String getAngemeldeteSpielerName() {
        return benutzernameField.getText();
    }

    public String getAngemeldeteSpielerPass() {
        return passwordField.getText();
    }

    @FXML
    public void spielerAnmelden(ActionEvent event) throws IOException, NameFalschException, PasswortFalschException {

        String benutzername = benutzernameField.getText();
        String password = passwordField.getText();

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

            if (!benutzername.isEmpty() && !password.isEmpty()) {

                System.out.printf("Login: %s [pwd: %s]\n", benutzername, password);

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

                if (verwaltung.anmelden(benutzername, password, client_ip)) {

                    getMeldung("Willkommen");
                    SceneManager.goToHauptmenu(event, benutzername, sp, verwaltung); // name

                }
                else getMeldung("Überprüfen Sie Ihren Namen oder Ihr Passwort");


            }
            else {
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
    public void sichRegistrieren(ActionEvent event) throws IOException {
        SceneManager.goToRegistrieren(event);
    }

    public void getMeldung(String message) {
        fehlerMeldung.setText(message);
    }
}
