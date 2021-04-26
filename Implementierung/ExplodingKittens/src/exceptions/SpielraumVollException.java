package exceptions;

/**
 *
 * @author SEP Gruppe-09
 */
public class SpielraumVollException  extends Exception{

    /**
     * Die Exception wird geworfen wenn ein Spieler in einen Spielraum beitreten 
     * will welcher bereits voll ist.
     */
    public SpielraumVollException(){
        super();
    }

    /**
     *
     * @param message
     */
    public SpielraumVollException(String message) {
        super(message);
    }
}

