package gui;

import client.Spieler;
import common.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import server.Verwaltung;

import java.io.IOException;
import java.util.List;

public class SieheDieZukunft{

    private String name;
    private Verwaltung verwaltung;
    private String raumName;
    private int anzahlSpieler;
    private Spieler sp;
    public KartenStapel kartenStapel;
    public SpielraumController sprc = new SpielraumController();
    
    @FXML
    Button okayZukunft;
    @FXML
    HBox zukunftHbox ;

    @FXML
    public void zukunftGesehen(ActionEvent event) {
        Stage stage = (Stage) okayZukunft.getScene().getWindow();
        stage.close();
    }

    public void setName(String name) {
        this.name = name ;
    }

    public void setSpieler(Spieler sp) { this.sp = sp; }
    public void setVerwaltung(Verwaltung verw) { this.verwaltung = verw;}

    public void setContent(String raumName, int anzahlSpieler, List<Karte> zukunft) {
        this.raumName = raumName;
        this.anzahlSpieler = anzahlSpieler;

        System.out.println("Du siehst die Zukunft");

        for (Karte k: zukunft){
            if (Angriff.class.isInstance(k)){
                karteAnzeigen(sprc.angriffId, k.getTyp());
            }
            if (BlickInDIeZukunft.class.isInstance(k)){
                karteAnzeigen(sprc.blickInDieZukunftId, k.getTyp());
            }if (Entscharfung.class.isInstance(k)){
                karteAnzeigen(sprc.entscharfungId, k.getTyp());
            }if (ExplodingKitten.class.isInstance(k)){
                karteAnzeigen(sprc.explodingId, k.getTyp());
            }if (Hops.class.isInstance(k)){
                karteAnzeigen(sprc.hopsID, k.getTyp());
            }if (KatzenKarte.class.isInstance(k)){
                if(k.getTyp()==0)
                    karteAnzeigen(sprc.katzenTacoId, k.getTyp());
                if(k.getTyp()==1)
                    karteAnzeigen(sprc.katzenMannId, k.getTyp());
                if(k.getTyp()==2)
                    karteAnzeigen(sprc.katzenMeloneId, k.getTyp());
                if(k.getTyp()==3)
                    karteAnzeigen(sprc.katzenRainbowId, k.getTyp());
                if(k.getTyp()==4)
                    karteAnzeigen(sprc.katzenPotatoId, k.getTyp());
            }if (Mischen.class.isInstance(k)){
                karteAnzeigen(sprc.mischenId, k.getTyp());
            }if (No.class.isInstance(k)){
                karteAnzeigen(sprc.noId, k.getTyp());
            }if (Wunsch.class.isInstance(k)){
                karteAnzeigen(sprc.wunschId, k.getTyp());
            }
        }

    }

    public void karteAnzeigen(int karteId, int typ){
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("sample/karteObjektImg.fxml"));
        try {
            Parent root = fxmlLoader.load();
            KarteObjektImg karteImg = fxmlLoader.getController();
            //todo
            karteImg.set(sprc.createPath(karteId, typ), -1, null);
            Platform.runLater(() -> this.zukunftHbox.getChildren().add(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
