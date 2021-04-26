package gui;

import client.Mensch;
import client.RemoteSpieler;
import client.Spieler;
import common.*;
import exceptions.DatenBankFehlerException;
import exceptions.KartenStapelZuKleinException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import server.RemoteSpielraum;
import server.Spielraum;
import server.Verwaltung;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class SpielraumController {

    public String name;
    public String raumName;
    public int anzahlSpieler;

    public Mensch sp;
    public RemoteSpielraum sprm;
    public HBox spielerListImg;
    public Button siehezft;
    public ImageView kartenStapelImg1;
    public ImageView kartenStapelImg;
    private Verwaltung verwaltung;

    public final int angriffId = 1;
    public final int blickInDieZukunftId = 2;
    public final int entscharfungId = 3;
    public final int explodingId = 4;
    public final int hopsID = 5;
    public final int katzenTacoId = 6;
    public final int mischenId = 7;
    public final int noId = 8;
    public final int wunschId = 9;
    public final int katzenMannId = 10;
    public final int katzenMeloneId = 11;
    public final int katzenRainbowId = 12;
    public final int katzenPotatoId = 13;


    public int angriffNummer;
    public int blickInDieZukunftNummer;
    public int entscharfungNummer;
    public int explodingNummer;
    public int hopsNummer;
    public int katzenNummer;
    public int mischenNummer;
    public int noNummer;
    public int wunschNummer;

    public final int angriffMax = 4;
    public final int blickInDieZukunftMax = 3;
    public final int entscharfungMax = 6;
    public final int explodingMax = 4;
    public final int hopsMax =  2;
    public final int katzenMax = 6;
    public final int mischenMax = 3;
    public final int noMax = 2;
    public final int wunschMax = 2;

    @FXML
    public Label raumNameLabel;
    @FXML
    public Label anzahlKartenImStapel;
    @FXML
    public Label labelSpieler1;
    @FXML
    public Image imgSpieler1;
    @FXML
    public ImageView playedStapel;
    @FXML
    public Button raumVerlassen;
    @FXML
    public TextArea nachri;
    @FXML
    public HBox karteObjektImg;
    @FXML
    public ListView chats;
    @FXML
    public Image playy;

    public int anzahlKarten;

    @FXML
    public void spielraumVerlassen(ActionEvent event) throws IOException, DatenBankFehlerException {
        // todo
    }

    @FXML
    public void nachrichtSchicken(ActionEvent event) {
        sp.addNachricht(nachri.getText());
        System.out.printf("Nachricht[%s]: %s", raumName, nachri.getText());
    }

    @FXML
    public void karteGezogen(MouseEvent mouseEvent) {
        // Spieler anzeigen, dass gezogen werden will
        sp.set_karte(-1);
    }

    public int getAnzahlSpieler(){
        return this.anzahlSpieler;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setSpieler(Spieler sp) { this.sp = (Mensch) sp; }
    public void setVerwaltung(Verwaltung verw) { this.verwaltung = verw;}
    public void setSpielraum(RemoteSpielraum sprm) { this.sprm = sprm;}

    private int ccc;
    private int ddd;

    // Update der GUI durch Mensch-Objekt
    public void update(Zustand zus, int amZug, boolean aktiv, boolean explo){
        if(explo){
            System.out.println("Du hast eine Exploding Kitten gezogen und musst Sie zurücklegen");
            Platform.runLater(() -> {
                try {
                    SceneManager.goToHideKitten(labelSpieler1.getScene(), name, raumName, anzahlSpieler, sp, verwaltung);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            });
        }

        // Update GUI
        Platform.runLater(() -> {
            anzahlKartenImStapel.setText(zus.ziehStapel.getAnzahlKartenImStapel() + " Karten");
            SetPlayedStapelImage(createPath(karte_to_kid(zus.ausgespielt), zus.ausgespielt == null ? 1 : zus.ausgespielt.getTyp()));
            // Zeige Zukunft
            if(zus.zukunft != null && aktiv){
                try {
                    SceneManager.goToSieheZukunft(labelSpieler1.getScene(), name, raumName, anzahlSpieler, sp, verwaltung, zus.zukunft);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        List<Karte> hand = zus.handKarten.get(amZug).getHandKarte();
        if(hand.size() != ddd) {
            Platform.runLater(() -> {
                // Lösche Karten vorm neu Anzeigen
                karteObjektImg.getChildren().clear();
            });
            for (int i = 0; i < hand.size(); i++) {
                karteAnzeigen(hand.get(i), i);
            }
            ddd = hand.size();
        }
        // System.out.printf("GUI up-to-date [%d]\n", amZug);
    }

    // Only update when new msg
    public void update_chat(List<String> msg){
        if(msg.size() > ccc) {
            Platform.runLater(() -> chats.setItems(FXCollections.observableArrayList(msg)));
            ccc = msg.size();
        }
    }

    public void setContent(String raumName,int anzahlSpieler){
        this.anzahlSpieler = anzahlSpieler;
        this.raumName = raumName;
        this.labelSpieler1.setText(name);
        this.raumNameLabel.setText(raumName);

        this.sp.start_updates(this);
        anzahlKartenImStapel.setText((51 - 4 * anzahlSpieler) + " Karten");

        try {
            try {
                List<String> spielerlst = sprm.getSpielerNamen();
                for (int i = 1; i < spielerlst.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("sample/spielerObjektImg.fxml"));
                    Parent root = fxmlLoader.load();
                    SpielerObjektImg spielerImg = fxmlLoader.getController();
                    spielerImg.set(spielerlst.get(i), "img/user_images/playericon" + String.valueOf(i + 1) + ".png");
                    Platform.runLater(() -> this.spielerListImg.getChildren().add(root));
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                SceneManager.goToAnmelden(labelSpieler1.getScene());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Spielraum beigetretten");
    }

    public void setAnzahlKartenImStapel(int anzahlKartenImStapel) {
        this.anzahlKarten = anzahlKartenImStapel;
    }

    public int karte_to_kid(Karte karte) {
        int kid;
        if (Angriff.class.isInstance(karte)) {
            kid = 1;
        } else if (BlickInDIeZukunft.class.isInstance(karte)) {
            kid = 2;
        } else if (Entscharfung.class.isInstance(karte)) {
            kid = 3;
        } else if (ExplodingKitten.class.isInstance(karte)) {
            kid = 4;
        } else if (Hops.class.isInstance(karte)) {
            kid = 5;
        } else if (KatzenKarte.class.isInstance(karte)) {
            if (karte.getTyp() == 0)
                kid = 6;
            else if (karte.getTyp() == 1)
                kid = 10;
            else if (karte.getTyp() == 2)
                kid = 11;
            else if (karte.getTyp() == 3)
                kid = 12;
            else
                kid = 13;
        } else if (Mischen.class.isInstance(karte)) {
            kid = 7;
        } else if (No.class.isInstance(karte)) {
            kid = 8;
        } else if (Wunsch.class.isInstance(karte)) {
            kid = 9;
        } else {
            kid = -3;
        }
        return kid;
    }

    public void karteAnzeigen(Karte karte, int id){
        int kid;
        if(Angriff.class.isInstance(karte)){
            kid = 1;
        }
        else if(BlickInDIeZukunft.class.isInstance(karte)){
            kid = 2;
        }
        else if(Entscharfung.class.isInstance(karte)){
            kid = 3;
        }
        else if(ExplodingKitten.class.isInstance(karte)){
            kid = 4;
        }
        else if(Hops.class.isInstance(karte)){
            kid = 5;
        }
        else if(KatzenKarte.class.isInstance(karte)){
            if(karte.getTyp()==0)
                kid = 6;
            else if(karte.getTyp()==1)
                kid = 10;
            else if(karte.getTyp()==2)
                kid = 11;
            else if(karte.getTyp()==3)
                kid = 12;
            else
                kid = 13;
        }
        else if(Mischen.class.isInstance(karte)){
            kid = 7;
        }
        else if(No.class.isInstance(karte)){
            kid = 8;
        }
        else if(Wunsch.class.isInstance(karte)){
            kid = 9;
        }
        else{
            System.out.println("Unbekannte Karte" + karte);
            kid = -3;
        }
        karteAnzeigen(kid, id, karte.getTyp());
    }

    public void karteAnzeigen(int karteId, int id, int typ){
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("sample/karteObjektImg.fxml"));
        try {
            Parent root = fxmlLoader.load();
            KarteObjektImg karteImg = fxmlLoader.getController();
            karteImg.set(createPath(karteId, typ), id, sp);
            Platform.runLater(() -> this.karteObjektImg.getChildren().add(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createPath(int karteId, int typ){
        String path;
        switch (karteId) {
            case angriffId:
                path = "img/game_cards/AttackCard" + (typ + 1) + ".png";
                return path;
            case blickInDieZukunftId:
                path = "img/game_cards/future-" + (typ + 1) + ".jpg";
                return path;
            case entscharfungId:
                path = "img/game_cards/DefuseCard" + (typ + 1) + ".jpg";
                return path;
            case explodingId:
                path = "img/game_cards/ExplodingKittenCard" + (typ + 1) + ".png";
                return path;
            case hopsID:
                path = "img/game_cards/skip-" + (typ + 1) + ".jpg";
                return path;
            case katzenTacoId:
                //katzenNummer = (katzenNummer) % katzenMax + 1;
                path = "img/game_cards/cat-" + 2 + ".jpg";
                return path;
            case katzenMannId:
                //katzenNummer = (katzenNummer) % katzenMax + 1;
                path = "img/game_cards/cat-" + 3 + ".jpg";
                return path;
            case katzenMeloneId:
                //katzenNummer = (katzenNummer) % katzenMax + 1;
                path = "img/game_cards/cat-" + 4 + ".jpg";
                return path;
            case katzenRainbowId:
                //katzenNummer = (katzenNummer) % katzenMax + 1;
                path = "img/game_cards/cat-" + 5 + ".jpg";
                return path;
            case katzenPotatoId:
                //katzenNummer = (katzenNummer) % katzenMax + 1;
                path = "img/game_cards/cat-" + 6 + ".jpg";
                return path;
            case mischenId:
                path = "img/game_cards/shuffle-" + (typ + 1) + ".jpg";
                return path;
            case noId:
                path = "img/game_cards/nope-" + (typ + 1) + ".jpg";
                return path;
            case wunschId:
                path = "img/game_cards/favor-" + (typ + 1) + ".jpg";
                return path;
            case -3:
                path = "img/game_cards/shuffle.jpg";
                return path;

            default:
                throw new IllegalStateException("Unexpected value: " + karteId);
        }
    }

    public  static  void showConfirmation(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Hauptmenu.class.getResource("img/explosion.png").toString()));

        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent()){
            // alert is exited, no button has been pressed.
        } else if(result.get() == ButtonType.OK){
            if(true){ // WURDE DER SPIELER IM SpIELRAUM GELÖSCHT???
                // todo gotoLobby
            }
        } else if(result.get() == ButtonType.CANCEL){
            stage.close();
        }
    }

    public void die() {
        Platform.runLater(() -> {
            try {
                SceneManager.goToYouHaveExploded(labelSpieler1.getScene(), name, raumName, anzahlSpieler, sp, verwaltung, sprm);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    public void SetPlayedStapelImage(String path){
        Platform.runLater(() -> playedStapel.setImage(new Image(this.getClass().getResource(path).toString())));
    }

    public int getAnzahlKarten() {
        return this.anzahlKarten;
    }
}
