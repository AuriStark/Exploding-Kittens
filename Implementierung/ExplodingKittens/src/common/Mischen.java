package common;

import server.Spielraum;

import java.io.Serializable;

public class Mischen extends Karte implements Serializable {
    @Override
    public void action(Spielraum sp) {
        sp.zustand.ziehStapel.mischen();
    }
}
