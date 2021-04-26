package exceptions;

import java.rmi.RemoteException;

/**
 *
 * @author SEP Gruppe-09
 */
public class KeineAntwortException extends RemoteException {

    /**
     * Wenn der Spieler der gerade dran ist nach einer gweissen Zeit keine Aktion 
     * durchgeführt hat wird diese Exception geworfen.
     */
    public KeineAntwortException(){
        super();
    }

    /**
     *
     * @param message
     */
    public KeineAntwortException(String message) {
        super(message);
    }

}
