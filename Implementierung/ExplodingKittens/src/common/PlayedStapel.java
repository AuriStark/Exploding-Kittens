package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SEP Gruppe-09
 */
public class PlayedStapel implements Serializable {

    private List<Karte> listPlayedKarten;

    /**
     * Stellt die gespielten Karten in einem stapel dar.
     */
    public PlayedStapel(){
        this.listPlayedKarten = new ArrayList<Karte>();
    }

    /**
     * Stellt die in der List stehende Karten als Stapel vor.
     * @param listPlayedKarten
     */
    public PlayedStapel(List<Karte> listPlayedKarten){
        this.listPlayedKarten = new ArrayList<Karte>(listPlayedKarten);
    }

    /**
     * Gibt die Anzahl der gespielten Karten Im playedStapel.
     * @return Anzahl der  Karten im playedStapel.
     */
    public int getKartenCount(){
        return this.listPlayedKarten.size();
    }

    /**
     * Gibt eine Liste der gespielten Karten, die  Im playedStapel enthalten sind.
     * @return Liste der Karten im playedStapel.
     */
    public List<Karte> getKartenVomStapel(){
        return this.listPlayedKarten;
    }

    /**
     * Gibt eine Karte zurück, an eine bestimmte Stelle in einer Liste.
     * @param i  Die Stelle an der die Karte sich befindet.
     * @param listPlayedKarten die Liste in der die Karte gesucht wird.
     * @return Die Karte an der Stelle index in der Liste listPlayedkarten.
     */
    public Karte getkarte(int i, List<Karte> listPlayedKarten){
        return listPlayedKarten.get(i);
    }

    /**
     * Gibt den letzen Index von dem Stapel,
     * damit wird auf die letze gespielte Karte zugreifen können.
     * @return Letzter Index
     */
    public int getLastIndex(){
        return this.listPlayedKarten.size() - 1;
    }

    /**
     * Die gespielte Karte wird zu dem playedStapel  hinzugefügt
     * @param karte die gespielte  Karte
     */
    public boolean addkarte(Karte karte){
        try{
            this.listPlayedKarten.add(karte);
            return true;
        }catch (IndexOutOfBoundsException e){
            return false;
        }
    }
}
