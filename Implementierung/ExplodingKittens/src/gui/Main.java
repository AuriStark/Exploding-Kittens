package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;

public class Main extends Application {

    // Ort von ips.set/rmi.policy
    public static String set_path;
    public static String pol_path;

    @Override
    public void start(Stage primaryStage) throws IOException{
        System.setProperty("java.security.policy", pol_path + "rmi.policy");
        try {
            LocateRegistry.createRegistry(1099);
        }
        catch (ExportException e){}

        System.out.println(getClass().getResource("sample/anmelden.fxml"));
        FXMLLoader l = new FXMLLoader(getClass().getResource("sample/anmelden.fxml"));
        Parent root = l.load();
        Scene scene = new Scene(root,600,700);
        scene.getStylesheets().add("gui/sample/style.css");

        primaryStage.setTitle("Exploding Kittens - Anmelden");
        primaryStage.getIcons().add( new Image(getClass().getResourceAsStream("img/explosion.png")) );
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        // Settings
        if(args.length > 0){
            // Erstes arg ist der Pfad zu ips.set
            set_path = args[0];
            if (args.length > 1) {
                // Zweites arg ist Pfad zu rmi.policy
                pol_path = args[1];
            }
            else{
                pol_path = "src/server/";
            }
        }
        else{
            set_path = "src/";
        }

        launch(args);
    }
}
