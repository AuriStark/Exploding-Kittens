package common;

import server.Spielraum;

import java.io.Serializable;

public class Hops extends Karte implements Serializable {

    @Override
    public void action(Spielraum sp) {
        // Falls noch nicht initialisiert wurde
        int dt = 0;
        if(sp.getAnzahlZuegeNaechster() < 1){
            dt = 1;
        }
        sp.minimiereAnzahlZuege(1 - dt);

    }

}
