package common;

import server.Spielraum;

import java.io.Serializable;

public class Entscharfung extends Karte implements Serializable {

    /**
     * Defuse darf man spielen,
     * nur wenn man eine Exploding Kitten gezogen hat
     */
    @Override
    public void action(Spielraum sp) {
        // Lösche Explodding Kitten
        if(ExplodingKitten.class.isInstance(sp.zustand.ausgespielt)){
            sp.zustand.ausgespielt = null;
        }
    }
}
