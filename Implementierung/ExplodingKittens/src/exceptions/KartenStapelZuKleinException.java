package exceptions;

/**
 *
 * @author SEP Gruppe-09
 */
public class KartenStapelZuKleinException extends Exception {

    /**
     *  Falls der Spieler eine Karte zurück in den Stapel legen will und die Stelle die er angibt nicht im 
     *  Kartenstapel vorhanden ist, da der Stapel zu klein ist wird die Exception geworfen.
     */
    public KartenStapelZuKleinException(){
        super();
    }

    /**
     *
     * @param message
     */
    public KartenStapelZuKleinException(String message) {
        super(message);
    }

}
