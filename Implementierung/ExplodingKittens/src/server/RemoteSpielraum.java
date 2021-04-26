package server;

import client.RemoteSpieler;
import common.Zustand;
import exceptions.KartenStapelZuKleinException;
import exceptions.SpielraumVollException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteSpielraum extends Remote{

    /**
     * Das Spiel wird gestartet, der Zustand initialisiert und die Karten ausgeteilt.
     * Fehlende Spieler werden mit Bots augefüllt.
     */
    public boolean spielStarten() throws RemoteException;

    /**
     *  Der Spieler wird in den Spielraum hinzugefügt
     * @param spieler Der Spieler der dem Spielraum beitreten will
     * @return Falls Spieler richtig in den Spielraum eingefügt wurde gibt es true zurück ansonsten wird
     * die Exception SpielraumVoll geworfen
     */
    public boolean addSpieler(RemoteSpieler spieler) throws RemoteException, SpielraumVollException;

    /**
     * Der Spieler wird aus dem Spielraum entfernt
     * @param name Der Name des Spielers, der entfernt werden soll
     * @return Falls der Spieler richtig aus dem Spileraum entfernt wurde gibt es true zurück ansonsten false
     */
    public boolean spielRaumVerlassen(String name) throws RemoteException;

    // Getter für Tests
    public  List<RemoteSpieler> getSpielerList() throws RemoteException;
    public  List<String> getSpielerNamen() throws RemoteException;
    public Zustand getZustand() throws RemoteException;
    public int getAnzahlSpieler() throws RemoteException;
    public String getName() throws RemoteException;
    public int getAmZug() throws RemoteException;
    public int getAnzahlZuege() throws RemoteException;
    public void setName(String name) throws RemoteException;
    public String kill() throws RemoteException;
}
