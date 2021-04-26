package exceptions;

import java.io.IOException;

/**
 *
 * @author SEP Gruppe-09
 */
public class FalscherSpielerException extends IOException {

    /**
     * Wenn der Spieler eine Karten spielen will ohne dass er an der Reihe ist wird die Exception geworfen.
     */
    public FalscherSpielerException(){
        super();
    }

    /**
     *
     * @param message
     */
    public FalscherSpielerException(String message) {
        super(message);
    }

}

