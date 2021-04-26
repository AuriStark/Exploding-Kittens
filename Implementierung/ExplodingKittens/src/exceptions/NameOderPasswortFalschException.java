package exceptions;

public class NameOderPasswortFalschException extends DatenBankFehlerException {

    public NameOderPasswortFalschException() {
        super();
    }

    public NameOderPasswortFalschException(String message) {
        super(message);
    }
}
