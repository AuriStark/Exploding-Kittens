
package client;

import common.Karte;
import common.Zustand;
import server.Chat;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SEP Gruppe-09
 */
public abstract class Spieler implements RemoteSpieler {

    public String name;
    public boolean no_play;
    private List<String[]> nachrichten;
    public Karte karte;
    private Chat chat;
    protected Zustand zustand;

    public Spieler(String name) {
        this.name = name;
        this.nachrichten = new ArrayList<>();
        this.chat = new Chat();
    }

    public synchronized String getName(){
        return name;
    }

    /**
     * Leitet den aktuellen Zustand weiter an die Spieler
     *
     * @param zustand Der aktuelle Zustand vom Spiel
     * @return Wenn einer der Spieler ei Nö spielen will, gibt es true zurück ansonsten false
     */
    public abstract boolean updateSpiel(Zustand zustand, int amZug);

    /**
     * Guckt ob einer oder mehrere Spieler eine Nachricht geschrieben haben
     *
     * @param chat Der aktuelle Chat
     * @return Wenn der Spieler eine Nachricht geschrieben hat, wird sie an die Verwaltung weitergeleitet.
     */
    public synchronized List<String[]> updateChat(List<String[]> chat) {
        this.chat.nachrichten = chat;
        List<String[]> nt = nachrichten;
        nachrichten = new ArrayList<>();
        return nt;
    }

    /**
     * Die gespielte Karte wird weiter an die Verwaltung geleitet
     * Falls ein Explodding Kitten auf der Hand ist, muss dass gespielt werden
     *
     * @return Die gespielte Karte wird ausgegeben
     */
    public synchronized Karte zug(int x, boolean wunsch) {
        Karte out = karte;
        karte = null;
        return out;
    }

    /**
     * Es wird eine Nachricht vorgemerkt
     */
    public synchronized void addNachricht(String nt) {
        this.nachrichten.add(new String[]{name, nt});
    }

    /**
     * Baue Nachrichten und gebe sie zurück
     */
    public synchronized List<String> getNachrichten(){
        List<String> out = new ArrayList<>();
        for(String[] n: chat.nachrichten){
            out.add(n[0] + " - " + n[1]);
        }
        return out;
    }

    public synchronized boolean isReady(){
        return true;
    }
}
