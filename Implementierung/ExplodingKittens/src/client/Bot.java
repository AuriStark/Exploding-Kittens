package client;

import common.*;
import exceptions.KartenStapelZuKleinException;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Spieler {

    int zeit;
    int zustandBot; //aktueller zustand vom Bot; Zustand normal beim Beginn vom Spiel
    Zustand zustand;
    int wievielterSpieler;
    boolean explo;


    public Bot(String name) {
        super(name);
        zeit = 0;
        zustandBot = 1;
        wievielterSpieler=-1;
        explo=false;
    }

    /**
     * In den Handkarten wird nachgeguckt ob eine Hops Karte enthalten ist
     * @return Gibt die Karte Hops zurück wenn es eine gibt
     */
    public Karte existiertKarteHops(){     //kontrollieren ob die Karte Hopps exisiert
        for(Karte k :zustand.handKarten.get(wievielterSpieler).getHandKarte()){
            if(Hops.class.isInstance(k))
                return k;
        }
        return null;
    }

    /**
     * In den Handkarten wird nachgeguckt ob eine Mischen Karte enthalten ist
     * @return Gibt die Karte Mischen zurück wenn es eine gibt
     */
    public Karte existiertKarteMischen(){      //kontrollieren ob die Karte Mischen exisiert
        for(Karte k :zustand.handKarten.get(wievielterSpieler).getHandKarte()){
            if(Mischen.class.isInstance(k))
                return k;
        }
        return null;
    }

    /**
     * In den Handkarten wird nachgeguckt ob eine Blick in die Zukunft Karte enthalten ist
     * @return Gibt die Karte Blick in die Zukunft zurück wenn es eine gibt
     */
    public Karte existiertKarteZukunft(){      //kontrollieren ob die Karte Blick in die Zukunft exisiert
        for(Karte k :zustand.handKarten.get(wievielterSpieler).getHandKarte()){
            if(BlickInDIeZukunft.class.isInstance(k)) {
                return k;
            }
        }
        return null;
    }

    /**
     * In den Handkarten wird nachgeguckt ob eine Angriff Karte enthalten ist
     * @return Gibt die Karte Angriff zurück wenn es eine gibt
     */
    public Karte existiertKarteAngriff(){     //kontrollieren ob die Karte Angriff exisiert
        for(Karte k :zustand.handKarten.get(wievielterSpieler).getHandKarte()){
            if(Angriff.class.isInstance(k)) {
                return k;
            }
        }
        return null;
    }

    /**
     * In den Handkarten wird nachgeguckt ob zwei Mal die gleiche Katzen Karte vorhanden ist
     * @return Gibt die Karte Mischen zurück wenn es eine gibt
     */
    public Karte existiertKarteKatzen(){
        int anzahlHandkarten = zustand.handKarten.get(wievielterSpieler).getHandKarte().size();
        for(int i=0; i<anzahlHandkarten;i++){
            int ersterTyp = zustand.handKarten.get(wievielterSpieler).getHandKarte().get(i).getTyp();
            for(int j=i+1 ; j<anzahlHandkarten;j++){
                int zweiterTyp = zustand.handKarten.get(wievielterSpieler).getHandKarte().get(j).getTyp();
                //Kontrolle ob die KatzenKarte zwei MAl enthalten ist vom gleichen Typ
                if(KatzenKarte.class.isInstance(i) && KatzenKarte.class.isInstance(j) && ersterTyp==zweiterTyp) {
                    KatzenKarte katze = (KatzenKarte) zustand.handKarten.get(wievielterSpieler).getHandKarte().get(i);
                    katze.modus = 2;
                    katze.kartenliste = new ArrayList<>();
                    katze.kartenliste.add(zustand.handKarten.get(wievielterSpieler).getHandKarte().get(j));
                    katze.id = wievielterSpieler > 0 ? wievielterSpieler-1 : wievielterSpieler+1;
                    return katze;
                }
            }

        }
        return null;
    }

    // Zustände:
    // normal = 1
    // unsicher = 2
    // sicher = 3
    // ziehen = 4
    // angriff = 5
    // alles spielen = 6

    //prioritäten der Karten : Blick-in-die-Zukunft > Mischen > Hops > Kombination > Ziehen
    @Override
    public synchronized Karte zug(int x, boolean wunsch) {
        // Falls Explosion
        if(explo){
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){}
            ExplodingKitten out = new ExplodingKitten();
            // Immer nach oben
            out.stelle = 0;
            return out;
        }
        //wenn der Bot spieler soll, weil ein Spieler Wunsch gespielt hat
        if(wunsch){
            Random zufallsZahl = new Random(); // neues Random Objekt, namens zufall
            int anzahlKarten = zustand.handKarten.get(wievielterSpieler).getHandKarte().size()-1;
            int zufallsKarte = zufallsZahl.nextInt(anzahlKarten);
            Karte gegeben = zustand.handKarten.get(wievielterSpieler).getHandKarte().get(zufallsKarte);
            return gegeben;
        }
        // Wenn der Bot am Zug ist
        else {
            if (zustandBot == 1) { //wenn der Bot sich im Zustand normal befindet,keine Karte spielen sondern nur ziehen
                if (zeit == 2) {
                    this.zustandBot = 2;
                    zeit = 0;
                }
                zeit++;
                return null;
            } else if (zustandBot == 2) {    //wenn der Bot sich im Zustand unsicher befindet
                if (existiertKarteZukunft() != null) {
                    return existiertKarteZukunft(); //gucken (ob x enthalten)
                }
                if (existiertKarteMischen() != null) {
                    zustandBot = 1;
                    return existiertKarteMischen();
                }
                return null;
            } else if (zustandBot == 3) {    //wenn der Bot sich im Zustand sicher befindet, nur Karte ziehen
                if (zeit == 2) {
                    this.zustandBot = 1;
                    zeit = 0;
                }
                zeit++;
                return null;
            } else if (zustandBot == 4) {    //wenn der Bot sich im Zustand ziehen vermeiden befindet
                if (existiertKarteMischen() != null) {
                    zustandBot = 1;
                    return existiertKarteMischen();
                }
                if (existiertKarteHops() != null)
                    return existiertKarteHops();
                if (existiertKarteKatzen() != null) //anders, da 2 mal gliche katzenkarte geben muss
                    return existiertKarteKatzen();
                return null;
            } else if (zustandBot == 5) {    //wenn der Bot sich im Zustand Angriff befindet
                if (existiertKarteAngriff() != null)
                    return existiertKarteAngriff();
                return null;
            } else if (zustandBot == 6) {        //wenn der Bot alles spielen soll, auser No und Entschärfung
                if (existiertKarteKatzen() != null) //anders, da 2 mal gliche katzenkarte geben muss
                    return existiertKarteKatzen();
                for (Karte k : zustand.handKarten.get(x).getHandKarte()) {
                    if (!No.class.isInstance(k) && !Entscharfung.class.isInstance(k) && !KatzenKarte.class.isInstance(k)) {
                        return k;
                    }
                }
            }
            return null;
        }
    }

    @Override
    public synchronized boolean updateSpiel(Zustand zustand, int zug) {  //jedes Mal wenn Zustand gewechselt wird setze zeit auf 0
        // Init beim ersten Mal
        if(this.wievielterSpieler == -1){
            this.wievielterSpieler = zug;
        }

        // Merken ob Expo gezogen
        explo = ExplodingKitten.class.isInstance(zustand.ausgespielt) && zug == wievielterSpieler;
        this.zustand = zustand;

        // Nur bei fremden Zügen
        if(zug != this.wievielterSpieler) {

            if (zustand.ziehStapel.getAnzahlKartenImStapel() <= 3) {
                zustandBot = 6;
                zeit = 0;
            } else if (Mischen.class.isInstance(zustand.ausgespielt)) {//wenn als letztes mischen gespielt wurde
                zustandBot = 5;
                zeit = 0;
            }
            //jemand nahes hat ein exploding gezogen
            else if (ExplodingKitten.class.isInstance(zustand.ausgespielt) && zug == (wievielterSpieler - 2))
                zustandBot = 2;
                //jemand weit entferntes hat ein Exploding Kitten gezogen
            else if (ExplodingKitten.class.isInstance(zustand.ausgespielt) && zug != (wievielterSpieler - 2)) {
                zustandBot = 5;
                zeit = 0;
            }
            //wenn der Bot eine Exploding Kitten gezogen hat wechselt er in den Zustand 6
            else if (name.equals(zustand.dran) && ExplodingKitten.class.isInstance(zustand.ausgespielt)) {
                zustandBot = 6;
            }
        }
        else{
            // Auser bei Zukunft
            if (BlickInDIeZukunft.class.isInstance(zustand.ausgespielt) && zustand.zukunft != null) {
                int counter = 0;
                for (Karte k : zustand.zukunft) { //nachgucken ob beim Blick in die Zukunft ein Exploding gesehen wurde
                    if (ExplodingKitten.class.isInstance(k))
                        counter++;
                }
                if (counter > 1)  //wenn mindestens ein Exploding gesehen wurde gehe in Zustand ziehen vermeiden
                    zustandBot = 4;
                else            //wenn kein Exploding gesehen wurde gehe in Zustand sicher
                    zustandBot = 3;
            }
        }
        return false;   //der Bot spielt kein Nö
    }
}