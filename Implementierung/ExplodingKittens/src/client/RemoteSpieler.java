package client;

import common.Karte;
import common.Zustand;
import server.Chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RemoteSpieler extends Remote{

    public String getName() throws RemoteException;

    /**
     * Leitet den aktuellen Zustand weiter an die Spieler
     *
     * @param zustand Der aktuelle Zustand vom Spiel
     * @return Wenn einer der Spieler ei Nö spielen will, gibt es true zurück ansonsten false
     */
    public boolean updateSpiel(Zustand zustand, int amZug) throws RemoteException;

    /**
     * Guckt ob einer oder mehrere Spieler eine Nachricht geschrieben haben
     *
     * @param chat Der aktuelle Chat
     * @return Wenn der Spieler eine Nachricht geschrieben hat, wird sie an die Verwaltung weitergeleitet.
     */
    public List<String[]> updateChat(List<String[]> chat) throws RemoteException;

    /**
     * Die gespielte Karte wird weiter an die Verwaltung geleitet
     * Falls ein Explodding Kitten auf der Hand ist, muss dass gespielt werden
     * @param x der spieler der grad dran ist
     * @param wunsch Angabe ob man eine Kare für den Gegenspieler aussuchen muss
     * @return Die gespielte Karte wird ausgegeben
     */
    public Karte zug(int x, boolean wunsch) throws RemoteException;

    /**
     * Zeig ob Spieler bereit ist (GUI geladen hat)
     */
    public boolean isReady() throws RemoteException;

}
