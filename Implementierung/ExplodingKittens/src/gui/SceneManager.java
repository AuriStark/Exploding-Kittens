package gui;

import client.RemoteSpieler;
import client.Spieler;
import common.Karte;
import common.KartenStapel;
import exceptions.DatenBankFehlerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import server.RemoteSpielraum;
import server.Spielraum;
import server.Verwaltung;

import java.io.IOException;
import java.util.List;

/**
 * enthält Funktionnen, für die bewegung  zwischen stages
 *
 * Alle Scenes außer Anmelden/Registrieren leiten Spieler und Verwaltung weiter.
 */
public class SceneManager {

    public static void goToAnmelden(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(SceneManager.class.getResource("sample/anmelden.fxml"));
        Scene scene = new Scene(root,600,700);
        scene.getStylesheets().add("gui/sample/style.css");
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Anmelden");
        stage.setScene(scene);
        scene.getStylesheets().add("gui/sample/style.css");
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToAnmelden(Scene sc) throws IOException {
        Parent root = FXMLLoader.load(SceneManager.class.getResource("sample/anmelden.fxml"));
        Scene scene = new Scene(root,600,700);
        scene.getStylesheets().add("gui/sample/style.css");
        Stage stage= (Stage) sc.getWindow();
        stage.setTitle("Exploding Kittens - Anmelden");
        stage.setScene(scene);
        scene.getStylesheets().add("gui/sample/style.css");
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public static void goToRegistrieren(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(SceneManager.class.getResource("sample/regiestrieren.fxml"));
        Scene scene = new Scene(root,600,700);
        scene.getStylesheets().add("gui/sample/style.css");
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - Registrieren");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
     public static void goToHauptmenu(ActionEvent event, String name, Spieler sp, Verwaltung verw) throws IOException {
         FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("sample/hauptmenu.fxml"));
         Parent root = loader.load();

         Hauptmenu ha = loader.getController();
         ha.setName(name);
         ha.setSpieler(sp);
         ha.setVerwaltung(verw);

         Scene scene = new Scene(root,600,700);
         scene.getStylesheets().add("gui/sample/style.css");
         Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.setTitle("Exploding Kittens - Hauptmenu");
         stage.setScene(scene);
         stage.setResizable(false);
         stage.show();
         stage.centerOnScreen();
     }
     public static void goToLobby(ActionEvent event, String name, Spieler sp, Verwaltung verw) throws IOException{
         FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("sample/lobby.fxml"));

         Parent root = loader.load();

         Lobby lo = loader.getController();
         lo.setName(name);
         lo.setSpieler(sp);
         lo.setVerwaltung(verw);

         Scene scene = new Scene(root,900,750);
         scene.getStylesheets().add("gui/sample/style.css");
         Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.setTitle("Exploding Kittens - Lobby");
         stage.setScene(scene);
         stage.setResizable(false);
         stage.show();
         stage.centerOnScreen();
     }
     public static void goToSpielraum(ActionEvent event, String name, String raumName, int anzahlSpieler, Spieler sp, RemoteSpielraum sprm, Verwaltung verw) throws IOException{
         FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("sample/spielraum.fxml"));
         Parent root = loader.load();

         SpielraumController spr = loader.getController();
         spr.setName(name);
         spr.setSpieler(sp);
         spr.setVerwaltung(verw);
         spr.setSpielraum(sprm);
         spr.setContent(raumName, anzahlSpieler);

         Scene scene = new Scene(root,931,756);
         scene.getStylesheets().add("gui/sample/style.css");
         Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.setTitle("Exploding Kittens - Spielraum");
         stage.setScene(scene);
         stage.setResizable(false);
         stage.show();
         stage.centerOnScreen();
     }

    public static void goToSpielraum(Scene sc, String name, String raumName, int anzahlSpieler, Spieler sp, RemoteSpielraum sprm, Verwaltung verw) throws IOException{
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("sample/spielraum.fxml"));
        Parent root = loader.load();

        SpielraumController spr = loader.getController();
        spr.setName(name);
        spr.setSpieler(sp);
        spr.setVerwaltung(verw);
        spr.setSpielraum(sprm);
        spr.setContent(raumName, anzahlSpieler);

        Scene scene = new Scene(root,931,756);
        scene.getStylesheets().add("gui/sample/style.css");
        Stage stage= (Stage) sc.getWindow();
        stage.setTitle("Exploding Kittens - Spielraum");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }
     public static void goToSpielraumErstellen(ActionEvent event, String name, Spieler sp, Verwaltung verw) throws IOException{
         FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("sample/spielraumErstellen.fxml"));
         Parent root = loader.load();

         SpielraumErstellen spre = loader.getController();
         spre.setName(name);
         spre.setSpieler(sp);
         spre.setVerwaltung(verw);

         Scene scene = new Scene(root,500,300);
         scene.getStylesheets().add("gui/sample/style.css");
         Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.setTitle("Exploding Kittens - SpielraumErstellen");
         stage.setScene(scene);
         stage.setResizable(false);
         stage.show();
         stage.centerOnScreen();
     }
     public static void goToWarteraum(ActionEvent event, String name, String raumName, int anzahlSpieler, Spieler sp, RemoteSpielraum spr, Verwaltung verw) throws IOException{
         FXMLLoader loader = new  FXMLLoader(SceneManager.class.getResource("sample/warteraum.fxml"));
         Parent root = loader.load();

         Warteraum wtr = loader.getController();
         wtr.setName(name);
         wtr.setSpieler(sp);
         wtr.setVerwaltung(verw);
         wtr.setSpielraum(spr);

         Scene scene = new Scene(root,518,300);
         scene.getStylesheets().add("gui/sample/style.css");

         wtr.setContent( raumName, anzahlSpieler);

         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.setTitle("Exploding Kittens - Warteraum");
         stage.setScene(scene);
         stage.setResizable(false);
         stage.show();
         stage.centerOnScreen();
     }

     public static void goToHideKitten(Scene sc, String name, String raumName, int anzahlSpieler, Spieler sp, Verwaltung verw) throws IOException{
         FXMLLoader loader = new  FXMLLoader(SceneManager.class.getResource("sample/hidekitten.fxml"));
         Parent root = loader.load();

         HideKitten hk = loader.getController();
         hk.setName(name);
         hk.setSpieler(sp);
         hk.setVerwaltung(verw);
         hk.setContent( raumName, anzahlSpieler);

         Scene scene = new Scene(root,340,500);
         scene.getStylesheets().add("gui/sample/style.css");
         Stage stage = new Stage();
         stage.setTitle("Exploding Kittens - Hide The kitten");
         stage.initStyle(StageStyle.UNDECORATED);
         stage.getIcons().add( new Image(SceneManager.class.getResourceAsStream("img/explosion.png")) );
         stage.setScene(scene);
         stage.initOwner(sc.getWindow());
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.setResizable(false);
         stage.centerOnScreen();
         stage.show();
     }

     public static void goToSieheZukunft(Scene sc, String name, String raumName, int anzahlSpieler, Spieler sp, Verwaltung verw, List<Karte> zukunft) throws IOException{
         FXMLLoader loader = new  FXMLLoader(SceneManager.class.getResource("sample/sieheDieZukunft.fxml"));
         Parent root = loader.load();

         SieheDieZukunft sz = loader.getController();
         sz.setName(name);
         sz.setSpieler(sp);
         sz.setVerwaltung(verw);
         sz.setContent(raumName, anzahlSpieler, zukunft);

         Scene scene = new Scene(root,500,350);
         scene.getStylesheets().add("gui/sample/style.css");
         Stage stage = new Stage();
         stage.setTitle("Exploding Kittens - Siehe Die zukunft");
         stage.getIcons().add( new Image(SceneManager.class.getResourceAsStream("img/explosion.png")) );
         stage.setScene(scene);
         stage.initOwner(sc.getWindow());
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.setResizable(false);
         stage.centerOnScreen();
         stage.showAndWait();
     }

     public static void goToYouHaveExploded(Scene sc, String name, String raumName, int anzahlSpieler, Spieler sp, Verwaltung verw, RemoteSpielraum spr) throws IOException{
         FXMLLoader loader = new  FXMLLoader(SceneManager.class.getResource("sample/youHaveExploded.fxml"));
         Parent root = loader.load();

         YouHaveExploded ye = loader.getController();
         ye.setName(name);
         ye.setSpieler(sp);
         ye.setVerwaltung(verw);
         ye.setSpielraum(spr);
         ye.setContent( raumName, anzahlSpieler);

         Scene scene = new Scene(root,568,350);
         scene.getStylesheets().add("gui/sample/style.css");
         Stage stage = (Stage) sc.getWindow();
         stage.setTitle("Exploding Kittens - You Have Exploded");
         stage.getIcons().add( new Image(SceneManager.class.getResourceAsStream("img/explosion.png")) );
         stage.setScene(scene);
         stage.setResizable(false);
         stage.centerOnScreen();
         stage.show();
     }

     public static void goToProfil(ActionEvent event, String name, Spieler sp, Verwaltung verw) throws IOException, DatenBankFehlerException {
         FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("sample/profil.fxml"));
         Parent root = loader.load();

         Profil p = loader.getController();
         p.setName(name);
         p.setSpieler(sp);
         p.setVerwaltung(verw);
         System.out.println(verw);
         p.setComponent();

         Scene scene = new Scene(root,600,700);
         scene.getStylesheets().add("gui/sample/style.css");
         Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.setTitle("Exploding Kittens - Profil");
         stage.setScene(scene);
         stage.setResizable(false);
         stage.show();
         stage.centerOnScreen();
     }

     public static void goToRegeln(ActionEvent event, String name, Spieler sp, Verwaltung verw) throws IOException{
         FXMLLoader loader = new  FXMLLoader(SceneManager.class.getResource("sample/regeln.fxml"));
         Parent root = loader.load();

         Regeln r = loader.getController();
         r.setName(name);
         r.setSpieler(sp);
         r.setVerwaltung(verw);

         Scene scene = new Scene(root,600,700);
         scene.getStylesheets().add("gui/sample/style.css");
         Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
         stage.setTitle("Exploding Kittens - Regeln");
         stage.setScene(scene);
         stage.setResizable(false);
         stage.show();
         stage.centerOnScreen();
     }

     public static void goToPasswortAndern(ActionEvent event, String name, Spieler sp, Verwaltung verw) throws IOException{
         FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("sample/pwtAndernModal.fxml"));
         Parent root = loader.load();

         PasswortAndern pwda = loader.getController();
         pwda.setName(name);
         pwda.setSpieler(sp);
         pwda.setVerwaltung(verw);

         Scene scene = new Scene(root,500,300);
         scene.getStylesheets().add("gui/sample/style.css");
         Stage stage = new Stage();
         stage.setTitle("Exploding Kittens - Passwort Ändern");
         stage.setScene(scene);
         stage.setResizable(false);
         stage.showAndWait();
         stage.centerOnScreen();
     }

     public static void goToPwtBestatigen(ActionEvent event, String name, Spieler sp, Verwaltung verw) throws IOException {
         FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("sample/pwtbestatigen.fxml"));
         Parent root = loader.load();

         Pwtbestatigen pwdb = loader.getController();
         pwdb.setName(name);
         pwdb.setSpieler(sp);
         pwdb.setVerwaltung(verw);

         Scene scene = new Scene(root,500,300);
         scene.getStylesheets().add("gui/sample/style.css");
         Stage stage = new Stage();
         stage.setTitle("Exploding Kittens - Passwort Bestätigen");
         stage.setScene(scene);
         stage.setResizable(false);
         stage.showAndWait();
         stage.centerOnScreen();
     }

    public static void goToSpielraumpwtModal(ActionEvent event, String name, String raumName, int anzahlSpieler, Spieler sp, Verwaltung verw) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("sample/spielraumPwtModal.fxml"));
        Parent root = loader.load();

        SpielraumPwtModal spm = loader.getController();
        spm.setName(name);
        spm.setSpieler(sp);
        spm.setVerwaltung(verw);
        spm.setContent(raumName,anzahlSpieler);

        Scene scene = new Scene(root,500,300);
        scene.getStylesheets().add("gui/sample/style.css");;
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Exploding Kittens - SpieraumModal");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public  static  void showAlertInfo(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Hauptmenu.class.getResource("img/explosion.png").toString()));
        alert.showAndWait();
    }

}
