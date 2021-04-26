
package gui;

import client.Spieler;
import exceptions.DatenBankFehlerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.Verwaltung;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class Profil implements Initializable {

    public String name;
    private Spieler sp;
    private Verwaltung verwaltung;

    @FXML
    private  Label benutzerName;
    @FXML
    private  Label anzahlGewonnen;
    @FXML
    private TableView<BestenListe> bestenListeTabelle;
    @FXML
    public TableColumn<BestenListe,String> spielerNameCol;
    @FXML
    public TableColumn<BestenListe, String> spielerPunktCol;

    @FXML
    private void pwdändern(ActionEvent event) throws IOException {
        SceneManager.goToPasswortAndern(event, name, sp, verwaltung);
    }

    @FXML
    public void back( ActionEvent event) throws IOException {
        SceneManager.goToHauptmenu(event, name, sp, verwaltung);
    }

    @FXML
    private void profilLöschen(ActionEvent event) throws IOException, DatenBankFehlerException {
        SceneManager.goToPwtBestatigen(event, name, sp, verwaltung);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        spielerNameCol.setCellValueFactory(new PropertyValueFactory<BestenListe,String>("spielerName"));
        spielerPunktCol.setCellValueFactory(new PropertyValueFactory<BestenListe, String>("spielerPunkt"));
    }

    public void setName(String n) {
        this.name = n;
    }
    public void setSpieler(Spieler sp) { this.sp = sp; }
    public void setVerwaltung(Verwaltung verw) throws DatenBankFehlerException, RemoteException {
        this.verwaltung = verw;
        // todo show bestenliste
        //this.bestenListeTabelle.setItems(FXCollections.observableArrayList(verwaltung.getBestenListe()));
        this.bestenListeTabelle.setItems(setBestenListe());
        // System.out.println(verwaltung.getBestenListe());
    }

    public void setComponent() throws RemoteException {
        this.benutzerName.setText(name);
        this.anzahlGewonnen.setText(anzahlGewonnen(name));
    }

    private ObservableList<BestenListe> setBestenListe() throws DatenBankFehlerException, RemoteException {

        ObservableList<BestenListe> siege = FXCollections.observableArrayList();
        try{
            if (verwaltung.getBestenListe().isEmpty()){
                throw new DatenBankFehlerException();
            } else{
                for( String[] spieler : verwaltung.getBestenListe()) {
                /*
                Todo: Fehler handle: Punktezahl wird nicht anzezeigt.
                 javafx.scene.control.cell.PropertyValueFactory getCellDataReflectively
                 WARNING: Can not retrieve property 'spielerPunkt' in PropertyValueFactory:
                 javafx.scene.control.cell.PropertyValueFactory@697053a3 with provided class type: class gui.BestenListe
                 java.lang.IllegalStateException: Cannot read from unreadable property spielerPunkt
                 */
                    siege.add(new BestenListe(spieler[0],String.valueOf(spieler[1])));
                    System.out.println("Bestenliste: " + Arrays.asList(spieler).toString());
                }
            }
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return siege;
    }

    public String anzahlGewonnen(String name) throws RemoteException{
        try {

            List<String[]> siegeList = verwaltung.getBestenListe();
            for (String[] siege : siegeList){
                if (siege[0].equals(name)){
                    return Integer.toString(Integer.parseInt(siege[1]));
                }
            }
        } catch (DatenBankFehlerException e) {
            e.printStackTrace();
        }

        return Integer.toString(0);
    }
}

