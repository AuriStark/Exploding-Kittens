package common;

import server.Spielraum;

import java.io.Serializable;

public class ExplodingKitten extends Karte implements Serializable {

    public int stelle;

    @Override
    public void action(Spielraum sp) {
        sp.zustand.ziehStapel.legen(stelle, this);
    }
}
