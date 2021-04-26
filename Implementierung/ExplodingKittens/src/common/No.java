package common;

import server.Spielraum;

import java.io.Serializable;

public class No extends Karte implements Serializable {
    @Override
    public void action(Spielraum sp) {
        sp.zustand.number_of_nos += 1;
    }

}
