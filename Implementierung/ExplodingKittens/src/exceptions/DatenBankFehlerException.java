package exceptions;

/**
 *
 * @author SEP Gruppe-09
 */
public class DatenBankFehlerException extends Exception {

    /**
     *  Sobald ein Eintrag nicht in der Datenbank vorhanden ist werden die Exceptions 
     * NameFalsch, Passwort Falsch oder DoppelterEintrag geworfen.
     */
    public DatenBankFehlerException(){
        super();
    }

    /**
     *
     * @param message
     */
    public DatenBankFehlerException(String message) {
        super(message);
    }
}
