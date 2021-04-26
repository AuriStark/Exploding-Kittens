package exceptions;

/**
 *
 * @author SEP Gruppe-09
 */
public class DoppelterEintragException extends DatenBankFehlerException {

    /**
     *  Wenn der Name schon in der Datenbank vorhanden ist wird die Exception geworfen.
     */
    public DoppelterEintragException(){
        super();
    }

    /**
     *  
     * @param message
     */
    public DoppelterEintragException(String message) {
        super(message);
    }

}
