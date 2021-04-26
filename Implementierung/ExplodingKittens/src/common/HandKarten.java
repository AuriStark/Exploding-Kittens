package common;

import exceptions.ZuWenigKartenException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SEP Gruppe-09
 */
public class HandKarten implements Serializable {

    List<Karte> firstHandKarten = new ArrayList<Karte>();
    private List<Karte> listKartenInhand;

    /**
     * Stellt die Handkarten des Spielers dar und dessen Aktionen wie legen und ziehen
     */
    public HandKarten(){
        this.listKartenInhand = new  ArrayList<Karte>();
    }

    /**
     * Am Anfang des spiels wird  eine Hand für jede Spieler erzeugt,
     * mit einer bestimmte liste von Karten.
     * @param listKartenInHand
     */
    public HandKarten(List<Karte> listKartenInHand){
        this.listKartenInhand = listKartenInHand;
    }

    /**
     * Zeigt die aktuellen Handkarten des Spielers an
     * @return Liste der Handkarten
     */
    public List<Karte> getHandKarte(){
        return listKartenInhand;
    }

    /**
     * Gibt die Anzahl der Handkarten des Spielers an
     * @return Anzahl der Handkarten
     */
    public int getHandKarteCount(){
      return getHandKarte().size();
    }


    /**
     * Die gezogene Karte wird zu den Handkarten hinzugefügt
     * @param karte die gezogene Karte
     */
    public boolean addkarte(Karte karte){
        listKartenInhand.add(karte);
        return true;
    }

    /**
     * Eine Karte wird von der Hand entfernt, entweder durch legen einer Karte oder durch eine Aktion einer 
     * anderen Karte, entfernt die erst Karte der passenden Klasse
     */
    public Karte removeKarte( Karte karte) throws ZuWenigKartenException {
        Karte found = null;
        for(Karte k: listKartenInhand){
            if(karte.getClass().isInstance(k)){
                found = k;
                break;
            }
        }
        if(karte != null){
            listKartenInhand.remove(found);
            return found;
        }
        throw new ZuWenigKartenException("keine No D:");
    }
}
