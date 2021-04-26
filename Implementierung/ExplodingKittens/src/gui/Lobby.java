package gui;

import client.RemoteSpieler;
import client.Spieler;
import exceptions.DatenBankFehlerException;
import exceptions.SpielraumVollException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import server.RemoteSpielraum;
import server.Spielraum;
import server.Verwaltung;

import java.io.IOException;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

public class Lobby implements Initializable{

    public String name;
    public String raumName;
    public int anzahlSpieler;
    Timer t;

    @FXML
    private TableView<SpielraumListe> spielraumeTable;
    @FXML
    private TableColumn<SpielraumListe,String> spielraumNameCol;
    @FXML
    private TableColumn<SpielraumListe,Integer> anzahlSpielerCol;
    @FXML
    private TableColumn<SpielraumListe,String> spielerCol;
    @FXML
    private TextArea nachrichtZumSenden;
    @FXML
    private TableColumn<?, ?> spielerInRaum;
    @FXML
    private TextArea nachricht;
    @FXML
    private ListView chats;

    private Spieler sp;
    private Verwaltung verwaltung;
    private ScheduledExecutorService scheduler = null;

    @FXML
    public void zuruckZurHauptmenu(ActionEvent event) throws IOException {
        t.cancel();
        SceneManager.goToHauptmenu(event, name, sp, verwaltung);
    }

    @FXML
    public void spielraumErstellen(ActionEvent event) throws IOException {
        t.cancel();
        SceneManager.goToSpielraumErstellen(event, name, sp, verwaltung);
    }

    @FXML
    public void nachrichtSchicken(ActionEvent event) throws IOException {
        sp.addNachricht(nachricht.getText());
        System.out.println("Nachricht[Lobby]: " + nachricht.getText());
    }


    /**
     *  spielraumbeitreten ist die Funktion die auf alleElemente der Listview der Spielräume aufgerufen wird.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spielraumNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        anzahlSpielerCol.setCellValueFactory(new PropertyValueFactory<SpielraumListe,Integer>("anzahlspieler"));
        // anzahlspieler.setCellValueFactory(new PropertyValueFactory<>("spielerPunkt"));
    }

    @FXML
    public void spielraumBeitreten(ActionEvent event) throws IOException{
        if(spielraumeTable.getSelectionModel().getSelectedItem() != null) {
            raumName = spielraumeTable.getSelectionModel().getSelectedItem().getName();

            RemoteSpielraum joined = null;
            for (RemoteSpielraum rspr : verwaltung.getSpielRaume()) {
                if (rspr.getName().equals(raumName)) {
                    try {
                        rspr.addSpieler(sp);
                    }
                    catch (SpielraumVollException e){}
                    joined = rspr;
                }
            }
            if(joined != null) {
                t.cancel();
                SceneManager.goToWarteraum(event, name, raumName, anzahlSpieler, sp, joined, verwaltung);
            }
        }
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setSpieler(Spieler sp) {
        this.sp = sp;
        // Update Chat regelmäßig
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        chats.setItems(FXCollections.observableArrayList(sp.getNachrichten()));
                        try {
                            ObservableList<SpielraumListe> rms = getSpielraum();
                            if(rms.size() > spielraumeTable.getItems().size()){
                                spielraumeTable.setItems(rms);
                            }
                        }
                        catch (DatenBankFehlerException e){
                            e.printStackTrace();
                        }
                        catch (RemoteException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, 0, 500);
    }

    public void setVerwaltung(Verwaltung verw) {
        this.verwaltung = verw;
        try {
            this.spielraumeTable.setItems(getSpielraum());
        }
        catch (DatenBankFehlerException e){
            e.printStackTrace();
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
    }

    private ObservableList<SpielraumListe> getSpielraum() throws DatenBankFehlerException, RemoteException {

        List<String[]> spielraumliste = verwaltung.getSpielraumList();

        ObservableList<SpielraumListe> raume = FXCollections.observableArrayList();
        for( String[] spielraum : spielraumliste) {
            raume.add(new SpielraumListe(spielraum[0],Integer.parseInt(spielraum[1])));
        }
        return raume;
    }

}
