package common;

import client.Mensch;
import server.Spielraum;

import java.io.Serializable;

public class BlickInDIeZukunft extends Karte implements Serializable {

    @Override
    public void action(Spielraum sp) {
        // Speichern in Zustand
        sp.zustand.zukunft = sp.zustand.ziehStapel.sieheZukunft();
    }
}
