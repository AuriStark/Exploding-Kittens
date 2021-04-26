package server;

import datenbank.Datenbank;
import exceptions.DatenBankFehlerException;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class main {

    // Ort von ips.set/rmi.policy
    public static String set_path;
    public static String pol_path;

    public static void main (String[] args){

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

        // Try to connect to db
        try {
            Datenbank db = new Datenbank("kittens", "localhost", "postgres", "root");

            //Lese die ip-Einstellungen
            Scanner sc = new Scanner(new File(set_path + "ips.set"));
            sc.nextLine();
            String server_ip = sc.nextLine().replace("server", "").replace("=", "").replace(" ", "");
            sc.close();

            System.setProperty("java.rmi.server.hostname", server_ip);
            System.setProperty("java.security.policy", pol_path + "rmi.policy");

            Registry reg = LocateRegistry.createRegistry(1099);

            VerwaltungImpl v = new VerwaltungImpl(db);
            Verwaltung stub = (Verwaltung) UnicastRemoteObject.exportObject(v, 0);

            reg.bind("Verwaltung", stub);

            System.out.println("Verwaltung erfolgreich gestartet");

        }
        catch (DatenBankFehlerException e){
            e.printStackTrace();
            System.exit(0);
        }
        catch (RemoteException e){
            e.printStackTrace();
            System.exit(0);
        }
        catch (AlreadyBoundException e){
            e.printStackTrace();
            System.exit(0);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(0);
        }
    }
}
