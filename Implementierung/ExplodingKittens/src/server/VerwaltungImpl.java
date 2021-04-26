package server;

import client.RemoteSpieler;
import datenbank.Datenbank;
import exceptions.*;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author SEP Gruppe-09
 */
public class VerwaltungImpl implements Verwaltung {

    private List<RemoteSpielraum> listeSpielraum;
    private Map<String, RemoteSpieler> listeSpieler;
    private Datenbank datenbank;
    private Chat chat;
    private final ScheduledExecutorService scheduler;

    /**
     * Die Klasse Verwaltung verwaltet alles was mit der Datenbank zu tun hat, zum Beispiel das Anmelden,
     * das Registrieren, das Löschen vom Account, das Erschaffen von Spielräumen und die Rückgabe der Bestenliste.
     * Die GUI/main der Clients kann über RMI Aufrufe auf die Verwaltung machen.
     */
    public VerwaltungImpl(Datenbank db) {
        listeSpieler = new HashMap<>();
        listeSpielraum = new ArrayList<>();
        datenbank = db;
        chat = new Chat();

        // Erstelle Spielräume, die nicht zu Ende gespielt wurden
        try {
            Registry reg = LocateRegistry.getRegistry();

            for (String args[] : getSpielraumList()){

                Spielraum spraum = new Spielraum(Integer.valueOf(args[1]), args[0]);
                RemoteSpielraum stub = (RemoteSpielraum) UnicastRemoteObject.exportObject(spraum, 0);
                listeSpielraum.add(stub);
                reg.bind(args[0], stub);
                System.out.printf("Raum <%s> erstellt\n", args[0]);
            }
        }
        catch (DatenBankFehlerException e){e.printStackTrace();}
        catch (RemoteException e){e.printStackTrace();}
        catch (AlreadyBoundException e){e.printStackTrace();}

        // Chat
        scheduler = Executors.newScheduledThreadPool(1);
        final Runnable chatter = new Runnable() {
            public void run() {
                for (String name : listeSpieler.keySet()) {
                    try {
                        chat.nachrichten.addAll(listeSpieler.get(name).updateChat(chat.nachrichten));
                    } catch (RemoteException e) {
                        System.err.printf("RemoteSpieler %s konnte nicht erreicht werden.\n", name);
                    }
                }
            }
        };
        scheduler.scheduleAtFixedRate(chatter, 0, 500, TimeUnit.MILLISECONDS);
    }

    /**
     * Es wird ein neuer Account erzeugt
     *
     * @param name Name des anonymen Spielers
     * @param pwt  Passwort
     * @return Bei bereits vergebenen Namen wird die Exception DoppelterEintrag geworfen
     * andernfaslls wenn registrieren geklappt hat gibt es true zurück und fügt die Daten in die Datenbank ein.
     */
    public synchronized boolean registrieren(String name, String pwt) throws DoppelterEintragException, RemoteException {
        try {
            List<String[]> nutzerDaten = datenbank.leseNutzerDaten();
            for (String[] strings : nutzerDaten) {
                if (strings[0].equals(name)) throw new DoppelterEintragException("Benutzername existiert schon!");
            }

            // Name existiert noch nicht, dann wird er in der Datenbank gespeichert
            datenbank.SpeicherNutzerDaten(name, pwt);
            return true;

        } catch (DatenBankFehlerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Ein vorhandener Account wird aktiviert
     *
     * @param name Name des Spielers
     * @param pwt  Passwort
     * @return Wenn der Name nicht in der Datenbank vorhanden ist wird die Exception NameFalsch geworfen.
     * Wenn der Name in der Datenbank vorhanden ist allerdings das Passwort falsch ist, wird die Exception PasswortFalsch
     * geworfen. Wenn das anmelden erfolgreich war gibt es true zurück. Spieler wir gespeichert.
     */
    public synchronized boolean anmelden(String name, String pwt, String client_ip) throws NameFalschException, PasswortFalschException, RemoteException {
        try {
            List<String[]> nutzerDaten = datenbank.leseNutzerDaten();
            for (String[] strings : nutzerDaten) {
                if (strings[0].equals(name)) {
                    if (strings[1].equals(pwt)) {

                        // Erzeuge Remote Stub der IP
                        try {
                            // System.setProperty("java.security.policy", "Implementierung/ExplodingKittens/src/server/client.policy");
                            if (System.getSecurityManager() == null) {
                                System.setSecurityManager(new SecurityManager());
                            }

                            Registry reg = LocateRegistry.getRegistry(client_ip);
                            RemoteSpieler sp = (RemoteSpieler) reg.lookup(name);

                            listeSpieler.put(name, sp);
                            System.out.printf("Spieler <%s> in der Lobby\n", sp.getName());
                        }
                        catch(Exception e){
                            e.printStackTrace();
                            return false;
                        }

                        return true;
                    } else {
                        throw new NameOderPasswortFalschException("Entweder ist Ihr Name oder Ihr Passwort falsch!");
                    }
                }
            }
            for (String[] strings : nutzerDaten) {
                System.out.println(Arrays.toString(strings));
            }
            return false;

        } catch (DatenBankFehlerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Ein vorhandener Account wird deaktiviert
     *
     * @param name Name des Spielers
     * @return Der Spieler wird abgemeldet und Spieler aus der Liste von Spielern genommen.
     */

    public synchronized boolean abmelden(String name) throws RemoteException{
        //todo
        if (listeSpieler.get(name) == null) {
            return false;
        }
        else{
            listeSpieler.remove(name);
            return true;
        }
    }

    /**
     * Gibt eine Liste mit Spielern und deren Punktezahlen aus
     *
     * @return Gibt die gesamte Bestenliste aus
     */
    public synchronized List<String[]> getBestenListe() throws DatenBankFehlerException, RemoteException {
        List<String[]> siegeList = datenbank.getSiege();
        return siegeList;
    }

    /**
     * Ändern vom Passwort vom Konto.
     * @param name Name vom Spieler
     * @param neuespwd Das neue Passwort, das man benutzen möchte.
     * @param altespwd Aktueles Paawort vom Konto.
     * @return true odeer false falls, die Aktion erfolgreich durchgeführt wurde.
     * Am sonstens wird eine exeption geworfen.
     * Der Spieler muss sich dann erneut anmelden, damit die Liste der Spieler ( in verwaltung) aktualisiert werden kann.
     */
    public synchronized boolean passwortAendern(String name, String altespwd, String neuespwd) throws RemoteException{
        try{
            List<String[]> nutzerDaten = datenbank.leseNutzerDaten();
            for (String[] strings : nutzerDaten) {
                if (strings[0].equals(name)) {
                    if (strings[1].equals(altespwd)) {
                        datenbank.aenderenutzerDaten(name,altespwd,neuespwd);
                        return true;
                    } else {
                        throw new PasswortFalschException("Das Passwort ist falsch!");
                    }
                }
            }
            return false;
        } catch (DatenBankFehlerException e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Löschen vom eigenen Konto
     *
     * @param name Name des Kontoinhaber
     * @param pwt  Passwort vom Konto
     * @return Der Spieler wird abgemeldet, falls er das noch nicht ist.
     * Wenn der Name nicht in der Datenbank vorhanden ist wird die Exception NameFalsch geworfen.
     * Wenn der Name in deer Datenbank vorhanden ist allerdings das Passwort falsch ist, wird die Exception PasswortFalsch
     * geworfen. Wenn das löschen erfolgreich war gibt es true zurück.
     */
    public synchronized boolean kontoLoeschen(String name, String pwt) {
        try{
            List<String[]> nutzerDaten = datenbank.leseNutzerDaten();
            for (String[] strings : nutzerDaten) {
                if (strings[0].equals(name)) {
                    if (strings[1].equals(pwt)) {
                        datenbank.loescheNutzerDaten(name,pwt);
                        return true;
                    } else {
                        throw new PasswortFalschException("Das Passwort ist falsch!");
                    }
                }
            }
            return false;
        } catch (DatenBankFehlerException e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Erstellung eines neuen Spielraums, dafür benötigt man einen Namen für den Spielraum
     *
     * @param raumName Wie der Raum heißen soll den man erstellt
     * @return true oder False , falls der Spielraum erstellt wurde oder nicht!
     */
    public synchronized boolean neuerSpielraum(int anzahlSpieler, String raumName) {
        try {
            List<String[]> spielraume = datenbank.getSpielraum();
            for (String[] strings : spielraume) {
                if (strings[0].equals(raumName))
                    throw new DoppelterEintragException("Spielraum mit dem Namen existiert schon");
            }

            Registry reg = LocateRegistry.getRegistry();
            Spielraum spraum = new Spielraum(anzahlSpieler, raumName);
            RemoteSpielraum stub = (RemoteSpielraum) UnicastRemoteObject.exportObject(spraum, 0);

            reg.bind(raumName, stub);

            // Name existiert noch nicht, dann wird er in der Datenbank gespeichert
            datenbank.SpeicherSpielraum(raumName, anzahlSpieler);
            listeSpielraum.add(stub);

            System.out.printf("Spielraum %s erfolgreich gestartet\n", raumName);

            return true;

        } catch (DatenBankFehlerException e) {
            e.printStackTrace();
            return false;
        } catch (RemoteException e){
            e.printStackTrace();
            return false;
        } catch (AlreadyBoundException e){
            // Dann hat es wohl schon funktioniert
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Ermöglicht das löschen eines Spielraums
     *
     * @param raumName Name vom Spielraum den man löschen will
     *                 Wenn das Spiel im Spielraum schon beendet ist, trage den Sieger in die Datenbank ein.
     */
    public synchronized void deleteSpielraum(String raumName) {
        RemoteSpielraum spr = null;
        for(int i = 0; i < listeSpielraum.size(); i++){
            try {
                if (listeSpielraum.get(i).getName().equals(raumName)) {
                    spr = listeSpielraum.get(i);
                }
            }
            catch (RemoteException e){}
        }
        if(spr != null) {
            try {
                String sieger = spr.kill();
                datenbank.addSieg(sieger);
            }
            catch (RemoteException | DatenBankFehlerException e){}
        }
    }

    /**
     * Gibt eine Liste mit den Spielräumen zurück
     *
     * @return Liste von Spielräumen mit dem Anzahl an Spieler in jeweilligen Spielräume.
     */

   public synchronized List<String[]> getSpielraumList() throws DatenBankFehlerException {
        List<String[]> spielraumList = datenbank.getSpielraum();
        return spielraumList;
    }

    /**
     *
     * @return
     */
    public List<RemoteSpielraum> getSpielRaume(){
        return listeSpielraum;
    }

    /**
     * Gibt eine Liste mit angemeldeten Spielern zurück
     *
     * @return Liste mit angemeldeten Spielern
     */
    public synchronized List<RemoteSpieler> getSpielerListe() {
        List<RemoteSpieler> out = new ArrayList<>();
        out.addAll(listeSpieler.values());
        return out;
    }
}
