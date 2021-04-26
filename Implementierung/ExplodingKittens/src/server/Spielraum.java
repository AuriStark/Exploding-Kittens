package server;


import client.Bot;
import client.Mensch;
import client.RemoteSpieler;
import common.*;
import exceptions.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author SEP Gruppe-09
 */
public class Spielraum implements RemoteSpielraum {

    private String raumName;
    private int anzahlSpieler;
    private String passwort;
    private boolean beendet;
    private int amZug;
    private Verwaltung verwaltung;
    public String sieger;
    public List<RemoteSpieler> spielerList = new ArrayList<>();
    private int anzahlZuege;
    private int anzahlZuegeNaechster;
    public Zustand zustand;
    public boolean[] imSpiel;
    public Chat chat;

    private ScheduledExecutorService scheduler;

    // Spielverwaltung
    private final Runnable spielmanager = () -> {
        // Spiellogik
        if (!beendet && imSpiel[amZug]) {
            // aktueller Spieler
            RemoteSpieler currentSpieler = spielerList.get(amZug);
            try {
                zustand.dran = currentSpieler.getName();
                chat.nachrichten.add(new String[]{raumName, zustand.dran + " ist am Zug"});
                System.out.printf("[%s]: %s ist dran\n", raumName, zustand.dran);
                while (anzahlZuege > 0) {
                    Karte gespielt = currentSpieler.zug(amZug, false);

                    // null heißt Karte ziehen
                    if (gespielt == null) {
                        // Ziehen
                        System.out.printf("[%s]: %s hat Karte gezogen\n", raumName, zustand.dran);
                        Karte newKarte = zustand.ziehStapel.ziehen();

                        // Wenn Exploding Kitten gezogen muss Spieler sagen wo er sie zurücklegt
                        if (ExplodingKitten.class.isInstance(newKarte)) {
                            System.out.printf("[%s]: %s hat Exploding Kitten gezogen\n", raumName, zustand.dran);

                            // Anzeigen
                            zustand.ausgespielt = newKarte;

                            update_all();

                            // Ausscheiden
                            boolean defuse = false;
                            for (Karte k : zustand.handKarten.get(amZug).getHandKarte()) {
                                if (Entscharfung.class.isInstance(k)) {
                                    defuse = true;
                                }
                            }
                            if (!defuse) {
                                // Ausscheiden
                                System.out.printf("[%s]: %s ist explodiert\n", raumName, zustand.dran);
                                imSpiel[amZug] = false;
                                spielerList.get(amZug).updateSpiel(null, amZug);

                                // Zähle die noch drin sind
                                int cnt1 = 0;
                                int last = -1;
                                for (int i = 0; i < imSpiel.length; i++) {
                                    if (imSpiel[i] == true) {
                                        cnt1 += 1;
                                        last = i;
                                    }
                                }

                                // Gewinner ermitteln
                                if (cnt1 <= 1) {
                                    System.out.printf("[%s]: %s ist der Gewinnert\n", raumName, spielerList.get(last).getName());
                                    chat.nachrichten.add(new String[]{raumName, spielerList.get(last).getName() + " ist der Gewinner"});
                                    sieger = spielerList.get(last).getName();
                                    beendet = true;
                                    break;
                                }
                            }
                            else {
                                // Wo zurücklegen?
                                Karte xplod = currentSpieler.zug(amZug, false);
                                // Darf nur Explodding Kitten sein
                                while (!ExplodingKitten.class.isInstance(xplod)) {
                                    xplod = currentSpieler.zug(amZug, false);
                                    System.out.println("Fehler X Kit");
                                }
                                // Entschärfung weg
                                Entscharfung ent = (Entscharfung) zustand.handKarten.get(amZug).removeKarte(new Entscharfung());
                                zustand.ablegeStapel.addkarte(ent);
                                xplod.action(Spielraum.this);
                                // update others
                                System.out.printf("[%s]: %s hat Exploding Kitten versteckt \n", raumName, zustand.dran);
                                update_all();
                            }
                        }
                        else{
                            // Auf die Hand nehmen
                            zustand.handKarten.get(amZug).addkarte(newKarte);
                        }

                        // Gezogen heißt ein Zug erledigt
                        anzahlZuege -= 1;
                        zustand.number_of_nos = 0;
                    } else {
                        // Spielzug anzeigen
                        System.out.printf("[%s]: %s hat %s gespielt\n", raumName, zustand.dran, gespielt.getClass().toString().substring(gespielt.getClass().toString().lastIndexOf('.')+1));
                        zustand.ausgespielt = gespielt;
                        // Ist weg
                        zustand.handKarten.get(amZug).removeKarte(gespielt);

                        // Alle 300 ms überprüfen ob jemand No spielt, bis 2 Sekunden um sind.
                        int rate = 300;
                        int limit = 2000;
                        int t = 0;
                        while (t < limit) {
                            for (int i = 0; i < spielerList.size(); i++) {
                                RemoteSpieler sp = spielerList.get(i);
                                boolean no = sp.updateSpiel(zustand, amZug);

                                // Nö spielen
                                if (no) {
                                    System.out.println(i);
                                    Karte noKarte = zustand.handKarten.get(amZug).removeKarte(new No());
                                    noKarte.action(Spielraum.this);
                                    zustand.ablegeStapel.addkarte(noKarte);
                                    if(zustand.number_of_nos % 2 == 0) {
                                        zustand.ausgespielt = gespielt;
                                    }
                                    else{
                                        zustand.ausgespielt = noKarte;
                                    }
                                    t = 0;
                                }
                            }

                            // Warte kurz
                            try {
                                Thread.sleep(rate);
                            } catch (InterruptedException e) {
                            }

                            t += rate;
                        }

                        // Karte spielen, falls kein No
                        if (zustand.number_of_nos % 2 == 0) {
                            System.out.println("preplay");
                            gespielt.action(Spielraum.this);
                            System.out.println("played");
                            zustand.ablegeStapel.addkarte(gespielt);
                            // Ausspielen fertig
                            zustand.ausgespielt = null;
                        }
                        // Zug machen
                        spielerList.get(amZug).updateSpiel(zustand, amZug);
                        zustand.zukunft = null;
                        zustand.ausgespielt = null;
                        update_all();
                    }
                }
                if(!beendet) {
                    System.out.printf("[%s]: %s ist fertig \n", raumName, zustand.dran);

                    // nächster ist dran
                    amZug = (amZug + 1) % anzahlSpieler;
                    anzahlZuege = anzahlZuegeNaechster;
                    anzahlZuegeNaechster = 1;
                    zustand.zukunft = null;

                    update_all();
                }

            } catch (RemoteException e) {
                // Ersetzen durch einen Bot, damit die anderen weiter Spielen können
                spielerList.set(amZug, new Bot("*Bot_" + amZug + 5));
            } catch (KartenStapelZuKleinException | ZuWenigKartenException e) {
                // Sollte niemals passieren
                System.out.println("Fatal Error in Game");
                // todo Lobby
            }
        }
    };


    /**
     * Hier wird das Eintreten in die Spielräume oder das Verlassen verwaltet,
     * desweiteren wird hier das Spiel gestartet.
     * SpielraumThread wird beendet wenn "beendet" auf True gesetzt wird.
     */
    public Spielraum(int anzahlSpieler, String raumName){
        super();
        this.raumName = raumName;
        this.anzahlSpieler = anzahlSpieler;
        // Am Anfang ist jeder im Spiel
        this.imSpiel = new boolean[anzahlSpieler];

        // Markiere als noch nicht gestartet
        this.amZug = -1;
        this.beendet = false;
        this.anzahlZuege = 1;
        this.anzahlZuegeNaechster = 1;

        this.chat = new Chat();
        Arrays.fill(imSpiel, true);
    }

    /**
     * Das Spiel wird gestartet, der Zustand initialisiert und die Karten ausgeteilt.
     * Fehlende Spieler werden mit Bots augefüllt.
     */
    public synchronized boolean spielStarten(){
        // Erster Spieler ist dran
        amZug = 0;

        // Karten vorbereiten
        zustand = new Zustand(anzahlSpieler);

        // Bots hinzufügen
        int cnt = 1;
        while(spielerList.size() < anzahlSpieler){
            spielerList.add(new Bot("Bot_" + cnt));
            cnt += 1;
        }

        // Chat
        scheduler = Executors.newScheduledThreadPool(3);
        final Runnable chatter = () -> {
            for (RemoteSpieler rs : spielerList) {
                try {
                    chat.nachrichten.addAll(rs.updateChat(chat.nachrichten));
                } catch (RemoteException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        };

        // Chat Update alle 100 ms
        scheduler.scheduleAtFixedRate(chatter, 0, 100, TimeUnit.MILLISECONDS);

        // Auf Spieler warten
        final Runnable waiter = () -> {
            for (int i = 0; i < spielerList.size(); i++) {
                RemoteSpieler rs = spielerList.get(i);
                try {
                    while (!rs.isReady()) {
                        try {
                            // 100 ms warten
                            System.out.printf("[%s]: warten auf Spieler mit id %d \n", raumName, i);
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                        }
                    }
                    rs.updateSpiel(zustand, i);
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            System.out.printf("[%s]: Spiel gestartet\n", raumName);

            // Spiel starten, Update alle 500 ms
            scheduler.scheduleWithFixedDelay(spielmanager, 0, 500, TimeUnit.MILLISECONDS);
        };

        // Spiel starten sobald alle bereit
        scheduler.schedule(waiter, 0, TimeUnit.MILLISECONDS);

        return true;
    }

    /**
     *  Der Spieler wird in den Spielraum hinzugefügt
     * @param spieler Der Spieler der dem Spielraum beitreten will
     * @return Falls Spieler richtig in den Spielraum eingefügt wurde gibt es true zurück ansonsten wird
     * die Exception SpielraumVoll geworfen
     */
    public synchronized boolean addSpieler(RemoteSpieler spieler) throws SpielraumVollException{
        if(spielerList.size() >= anzahlSpieler){
            throw new SpielraumVollException();
        }
        else {
            try {
                String name = spieler.getName();
                System.out.printf("Spieler <%s> ist <%s> beigetreten\n", name, raumName);
                spielerList.add(spieler);
                return true;
            }
            catch (RemoteException e) {
                return false;
            }
        }
    }

    /**
     * Der Spieler wird aus dem Spielraum entfernt
     * @param name Der Name des Spielers, der entfernt werden soll
     * @return Falls der Spieler richtig aus dem Spileraum entfernt wurde gibt es true zurück ansonsten false
     */
    public synchronized boolean spielRaumVerlassen(String name){
        for(int i=0;i<spielerList.size();i++){
            RemoteSpieler sp = spielerList.get(i);
            try {
                if (sp.getName().equals(name)) {
                    if(!beendet) {
                        spielerList.set(i, new Bot(name + "_Bot"));
                        spielerList.get(i).updateSpiel(zustand, i);
                    }
                    else{
                        spielerList.set(i, null);
                        // Kill Spielraum, wenn es der letzte war
                        boolean alive = false;
                        for(RemoteSpieler rs: spielerList){
                            try {
                                rs.getName();
                                if (Mensch.class.isInstance(rs)) {
                                    alive = true;
                                }
                            }
                            catch (RemoteException e){}
                        }
                        if(!alive){
                            verwaltung.deleteSpielraum(raumName);
                        }

                    }
                    return true;
                }
            }
            catch(RemoteException e){
                continue;
            }
        }
        return false;
    }

    /**
     * Beende den Thread, der die Spielverwaltung steuert. Sollte nur von der Verwaltung ausgeführt werden,
     * nach dem die Ergebnisse gespeichert werden
     */
    public void beenden(){
        beendet = true;
        return;
    }

    /**
     * Die Anzahl der Züge wird verringert
     * @param x Um wieviel die Züge verringert werden sollen
     */
    public void minimiereAnzahlZuege(int x){
        anzahlZuege=anzahlZuege-x;
    }


    /**
     * Die Anzahl der Züge vom nächsten Spieler wird erhöht
     * @param x Um wieviel die Züge erhöht werden soll
     */
    public void erhoeheAnzahlZuegeNaechster(int x){
        anzahlZuegeNaechster=anzahlZuegeNaechster+x;
    }


    // Getter für Tests
    public synchronized List<RemoteSpieler> getSpielerList(){
        return spielerList;
    }
    public  List<String> getSpielerNamen(){

        List<String> names = new ArrayList<>();
        for(RemoteSpieler rs: spielerList){
            try {
                names.add(rs.getName());
            }
            catch(RemoteException e){
                names.add("Connection Lost");
            }
        }
        return names;
    }

    public void update_all() throws  RemoteException{
        for(RemoteSpieler rs: spielerList){ //amZug ist beim Update nur wichtig für den Bot
            rs.updateSpiel(zustand,amZug);
        }
    }

    public Zustand getZustand(){
        return zustand;
    }

    public int getAnzahlSpieler() {
        return anzahlSpieler;
    }

    public String getName() {

        return raumName;
    }

    public int getAmZug(){
        return amZug;
    }

    public int getAnzahlZuege(){
        return anzahlZuege;
    }

    public int getAnzahlZuegeNaechster(){
        return anzahlZuegeNaechster;
    }

    public void setName(String name) {
        this.raumName = name;
    }

    public String kill(){
        scheduler.shutdownNow();
        return sieger;
    }

}