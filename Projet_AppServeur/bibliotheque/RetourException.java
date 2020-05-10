package bibliotheque;

/**
 * La classe <strong>RetourException</strong> est lancée lorsqu'un document ne peut pas être retourné.
 */
public class RetourException extends Exception {

    @Override
    public String getMessage() {
        return "Erreur! Le document ne peux pas être retourné.";
    }
}
