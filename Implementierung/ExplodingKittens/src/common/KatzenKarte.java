package common;

import exceptions.ZuWenigKartenException;
import server.Spielraum;

import java.io.Serializable;
import java.util.Random;
import java.util.List;

public class KatzenKarte extends Karte implements Serializable {

    public int id;
    public int modus;
    public List<Karte> kartenliste;

    @Override
    public void action(Spielraum sp) {
        try {
            Random zufallsZahl = new Random(); // neues Random Objekt, namens zufall
            int anzahlKarten = sp.zustand.handKarten.get(id).getHandKarte().size() - 1;
            int zufallsKarte = zufallsZahl.nextInt(anzahlKarten);
            Karte ausgetauscht = sp.zustand.handKarten.get(id).getHandKarte().get(zufallsKarte);
            sp.zustand.handKarten.get(sp.getAmZug()).addkarte(ausgetauscht);    //füge die Karte beim aktuellen Spieler hinzu
            sp.zustand.handKarten.get(id).removeKarte(ausgetauscht);   //entferne die Karte beim Spieler dem sie entnommen wurde
            for(Karte k: kartenliste){
                sp.zustand.handKarten.get(id).removeKarte(k);   //entferne ALLE
            }
        }
        catch (ZuWenigKartenException e){
            System.out.println("nicht genug Karten");
        }

    }
}
