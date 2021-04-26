package server;

import client.RemoteSpieler;
import exceptions.DatenBankFehlerException;
import exceptions.DoppelterEintragException;
import exceptions.NameFalschException;
import exceptions.PasswortFalschException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Verwaltung extends Remote {

    /**
     * Es wird ein neuer Account erzeugt
     *
     * @param name Name des anonymen Spielers
     * @param pwt  Passwort
     * @return Bei bereits vergebenen Namen wird die Exception DoppelterEintrag geworfen
     * andernfaslls wenn registrieren geklappt hat gibt es true zurück und fügt die Daten in die Datenbank ein.
     */
    public boolean registrieren(String name, String pwt) throws RemoteException, DoppelterEintragException;

    /**
     * Ein vorhandener Account wird aktiviert
     *
     * @param name Name des Spielers
     * @param pwt  Passwort
     * @param client_ip IP Adresse des Spielers
     * @return Wenn der Name nicht in der Datenbank vorhanden ist wird die Exception NameFalsch geworfen.
     * Wenn der Name in der Datenbank vorhanden ist allerdings das Passwort falsch ist, wird die Exception PasswortFalsch
     * geworfen. Wenn das anmelden erfolgreich war gibt es true zurück. Spieler wir gespeichert.
     */
    public boolean anmelden(String name, String pwt, String client_ip) throws RemoteException, NameFalschException, PasswortFalschException;

    /**
     * Ein vorhandener Account wird deaktiviert
     *
     * @param name Name des Spielers
     * @return Der Spieler wird abgemeldet und Spieler aus der Liste von Spielern genommen.
     */

    public boolean abmelden(String name) throws RemoteException;
    /**
     * Gibt eine Liste mit Spielern und deren Punktezahlen aus
     *
     * @return Gibt die gesamte Bestenliste aus
     */

    public List<String[]> getBestenListe() throws RemoteException, DatenBankFehlerException;

    /**
     * Ändern vom Passwort vom Konto.
     * @param name Name vom Spieler
     * @param neuespwd Das neue Passwort, das man benutzen möchte.
     * @param altespwd Aktueles Paawort vom Konto.
     * @return true odeer false falls, die Aktion erfolgreich durchgeführt wurde.
     * Am sonstens wird eine exeption geworfen.
     * Der Spieler muss sich dann erneut anmelden, damit die Liste der Spieler ( in verwaltung) aktualisiert werden kann.
     */
    public boolean passwortAendern(String name, String altespwd, String neuespwd) throws RemoteException;

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
    public boolean kontoLoeschen(String name, String pwt) throws RemoteException;

    /**
     * Erstellung eines neuen Spielraums, dafür benötigt man einen Namen für den Spielraum
     * Registriert den Spielraum mit in RMI mit dem raumNamen.
     *
     * @param raumName Wie der Raum heißen soll den man erstellt
     * @return true oder False , falls der Spielraum erstellt wurde oder nicht!
     */
    public boolean neuerSpielraum(int anzahlSpieler, String raumName) throws RemoteException;


    /**
     * Ermöglicht das löschen eines Spielraums
     *
     * @param raumName Name vom Spielraum den man löschen will
     *                 Wenn das Spiel im Spielraum schon beendet ist, trage den Sieger in die Datenbank ein.
     */
    public void deleteSpielraum(String raumName) throws RemoteException;

    /**
     * Gibt eine Liste mit den Spielräumen zurück
     *
     * @return Liste von Spielräumen mit dem Anzahl an Spieler in jeweilligen Spielräume.
     */

    public List<String[]> getSpielraumList() throws RemoteException, DatenBankFehlerException;

    /**
     *
     * @return
     */
    public List<RemoteSpielraum> getSpielRaume() throws RemoteException;

    /**
     * Gibt eine Liste mit angemeldeten Spielern zurück
     *
     * @return Liste mit angemeldeten Spielern
     */
    public List<RemoteSpieler> getSpielerListe() throws RemoteException;
}
