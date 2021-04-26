package client;

import common.*;
import gui.SpielraumController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mensch extends Spieler {

    private SpielraumController cntrl;
    protected int amZug;
    private int kid;

    public Mensch(String name) {
        super(name);
        cntrl = null;
        kid = -2;
        amZug = -2;
    }

    public void start_updates(SpielraumController cntrl){
        this.cntrl = cntrl;
    }

    @Override
    public synchronized boolean updateSpiel(Zustand zustand, int amZug){
        //Tod
        if(zustand == null){
            cntrl.die();
            return true;
        }
        // Init amZug on first call
        if(this.amZug == -2){
            this.amZug = amZug;
        }
        this.zustand = zustand;
        cntrl.update(zustand, this.amZug, this.amZug == amZug, false);
        if(kid >= 0 && No.class.isInstance(zustand.handKarten.get(this.amZug).getHandKarte().get(kid))){
            kid = -2;
            return true;
        }
        return false;
    }

    @Override
    public synchronized List<String[]> updateChat(List<String[]> chat) {
        List<String[]> new_msg = super.updateChat(chat);
        if(cntrl != null) {
            cntrl.update_chat(getNachrichten());
        }
        return new_msg;
    }

    @Override
    public synchronized Karte zug(int i, boolean wunsch){
        // Exploding Kitten
        if(ExplodingKitten.class.isInstance(zustand.ausgespielt) && i == amZug){
            kid = -2;
            cntrl.update(zustand, this.amZug, true, true);
            while(kid == -2){
                try{
                    this.wait();
                }
                catch (InterruptedException e){}
            }
            ExplodingKitten out = (ExplodingKitten) zustand.ausgespielt;
            out.stelle = kid;
            kid = -2;
            return out;
        }
        if(wunsch){
            kid = -2;
        }
        System.out.printf("Du (%s) bist am Zug\n", name);
        while(kid == -2){
            try{
                this.wait();
                if(kid >= 0 && KatzenKarte.class.isInstance(zustand.handKarten.get(amZug).getHandKarte().get(kid))){
                    karte = existiertKarteKatzen();
                    if (karte == null){
                        System.out.println("Keine zwei Katzen");
                        kid = -2;
                    }
                }
            }
            catch (InterruptedException e){}
        }
        if(kid > -1){
            karte = zustand.handKarten.get(amZug).getHandKarte().get(kid);
        }
        Karte out = super.zug(i, wunsch);
        kid = -2;
        return out;
    }

    @Override
    public synchronized boolean isReady(){
        return cntrl != null;
    }

    /**
     * Setze die Karte die gespielt werden soll.
     * -1 keine Karte spielen / Karte ziehen
     * -2 noch nicht entschieden
     * @param id
     */
    public synchronized void set_karte(int id){
        kid = id;
        System.out.printf("Karte <%d> ausgewählt\n", kid);
        this.notifyAll();
    }

    /**
     * In den Handkarten wird nachgeguckt ob zwei Mal die gleiche Katzen Karte vorhanden ist
     * @return Gibt die Karte Mischen zurück wenn es eine gibt
     */
    public Karte existiertKarteKatzen(){
        List<Karte> hd = zustand.handKarten.get(amZug).getHandKarte();
        for(int i=0; i<hd.size();i++){
            int ersterTyp = hd.get(i).getTyp();
            for(int j=i+1 ; j<hd.size();j++){
                int zweiterTyp = hd.get(j).getTyp();
                //Kontrolle ob die KatzenKarte zwei MAl enthalten ist vom gleichen Typ
                if(KatzenKarte.class.isInstance(hd.get(i)) && KatzenKarte.class.isInstance(hd.get(j)) && ersterTyp==zweiterTyp) {
                    KatzenKarte katze = (KatzenKarte) hd.get(i);
                    katze.modus = 2;
                    katze.kartenliste = new ArrayList<>();
                    katze.kartenliste.add(hd.get(j));
                    katze.id =amZug > 0 ? amZug-1 : amZug+1;
                    return katze;
                }
            }

        }
        return null;
    }
}
