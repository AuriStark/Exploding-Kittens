package common;

import server.Spielraum;

import java.io.Serializable;

/**
 *
 * @author SEP Gruppe-09
 */
public abstract class Karte implements Serializable {

    public int typ;
    /**
     * Es wird jeweils die spezielle Fähigkeit von der gespielten Karte ausgeführt
     */
    abstract public void action(Spielraum sp);

    public int getTyp(){
        return typ;
    }

    public void setTyp(int typ){
        this.typ = typ;
    }
}
