package common;

import server.Spielraum;

import java.io.Serializable;

public class Angriff extends Karte implements Serializable {

    @Override
    public void action(Spielraum sp) {
        // Falls noch nicht initialisiert wurde
        int dt = 0;
        if(sp.getAnzahlZuegeNaechster() < 1){
            dt = 1;
        }
        System.out.println(sp.getAnzahlZuegeNaechster());
        sp.minimiereAnzahlZuege(1);
        sp.erhoeheAnzahlZuegeNaechster(1 + dt);

    }
}
