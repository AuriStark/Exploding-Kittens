package gui;

import client.RemoteSpieler;
import client.Spieler;
import exceptions.DatenBankFehlerException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import server.RemoteSpielraum;
import server.Spielraum;
import server.Verwaltung;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Warteraum {

    public String name;
    public String raumName;
    public int anzahlSpieler;
    public HBox spielerListImg;

    Timer t;

    private Spieler sp;
    private Verwaltung verwaltung;
    private RemoteSpielraum spr;

    @FXML
    public Button zuruckZurLobby;
    @FXML
    public Button botHinzufugen;
    @FXML
    public Label raumNameLabel;

    @FXML
    public void spielStarten(ActionEvent event) throws IOException {
        t.cancel();
        spr.spielStarten();
        SceneManager.goToSpielraum(event,name,raumName,anzahlSpieler, sp, spr, verwaltung);
    }

    public void zuruckZurLobby(ActionEvent event) throws IOException {
        // todo: der Spieler muss von der Liste entfernt werden.
        SceneManager.goToLobby(event,name, sp,verwaltung);
    }

    public void setName(String name) {
        this.name = name ;
    }
    public void setSpieler(Spieler sp) { this.sp = sp; }
    public void setVerwaltung(Verwaltung verw) { this.verwaltung = verw;}
    public void setSpielraum(RemoteSpielraum spr) { this.spr = spr;}

    public void setContent(String raumName, int anzahlSpieler){
        this.raumName = raumName;
        this.anzahlSpieler = anzahlSpieler;
        this.raumNameLabel.setText(raumName);

        try {
            try {
                // For each Spieler in der List wird den label und das Bild gesetzt
                List<RemoteSpieler> spielerlst = spr.getSpielerList();
                System.out.printf("Spielraum hat insgesamt %d Mitglied(er)\n", spielerlst.size());
                for (int i = 0; i < spielerlst.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("sample/spielerObjektImg.fxml"));
                    Parent root = fxmlLoader.load();
                    SpielerObjektImg spielerImg = fxmlLoader.getController();
                    // Der erste Parameter sollte der Name der Spieler sein
                    spielerImg.set(spielerlst.get(i).getName(), "img/user_images/playericon" + String.valueOf(i + 1) + ".png");
                    Platform.runLater(() -> this.spielerListImg.getChildren().add(root));
                }
            } catch (RemoteException e) {
                SceneManager.goToAnmelden(new ActionEvent());
            }
        }
        catch (IOException e) {
        e.printStackTrace();
        }

        // Warte bis es losgeht
        Scene scene = zuruckZurLobby.getScene();
        t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                try {
                    if(spr.getAmZug() > -1){
                        Platform.runLater(() -> {
                            try {
                                SceneManager.goToSpielraum(scene, name, raumName, anzahlSpieler, sp, spr, verwaltung);
                            }
                            catch (IOException e){
                                e.printStackTrace();
                            }
                        });
                        t.cancel();
                    }
                }
                catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        };
        t.schedule(tt, 0, 200);
    }
    
}
