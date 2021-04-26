package common;

import exceptions.KartenStapelZuKleinException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Zustand implements Serializable {

    public Karte ausgespielt;
    public List<HandKarten> handKarten;
    public KartenStapel ziehStapel;
    public PlayedStapel ablegeStapel;
    public String dran;
    public int number_of_nos;
    public List<Karte> zukunft;

    /**
     * Erstelle und mische die Karten, teile aus und füge Entschärfung und Exploding Kittens hinzu
     */
    public Zustand(int anzSpieler){
        ziehStapel = new KartenStapel();

        handKarten = new ArrayList<>();
        for(int i = 0; i < anzSpieler; i++){

            // / Handkarten + 1 Defuse
            HandKarten hand = new HandKarten();
            for(int j = 0; j < 4; j++){
                try {
                    hand.addkarte(ziehStapel.ziehen());
                }
                catch (KartenStapelZuKleinException e){
                    e.printStackTrace();
                    continue;
                }
            }
            hand.addkarte(new Entscharfung());

            handKarten.add(hand);
        }

        ziehStapel.starten(anzSpieler);
        ablegeStapel = new PlayedStapel();
        number_of_nos = 0;
    }



}
