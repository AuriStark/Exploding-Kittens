package exceptions;

/**
 *
 * @author SEP Gruppe-09
 */
public class NameFalschException extends DatenBankFehlerException {

    /**
     * Wenn der Name mit dem man sich in sein Accoiunt einlogen will falsch ist (nicht in der Datenbank 
     * vorhabnden ist) wird die Exception geworfen.
     */
    public NameFalschException(){
        super();
    }

    /**
     *
     * @param message
     */
    public NameFalschException(String message) {
        super(message);
    }

}
