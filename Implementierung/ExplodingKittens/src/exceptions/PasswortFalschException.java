package exceptions;

/**
 *
 * @author SEP Gruppe-09
 */
public class PasswortFalschException extends DatenBankFehlerException {

    /**
     * Wenn das angegebene Passwort nicht mit dem Namen und dem Passwort aus der Datenbank
     * übereinstimmt wird die Exception geworfen.
     */
    public PasswortFalschException(){
        super();
    }

    /**
     *
     * @param message
     */
    public PasswortFalschException(String message) {
        super(message);
    }

}
