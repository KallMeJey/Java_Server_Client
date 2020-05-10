package bibliotheque;

/**
 * La classe <strong>EmpruntException</strong> est lancée lorsqu'un document ne peut pas être emprunté.
 */
public class EmpruntException extends Throwable {

    @Override
    public String getMessage() {
        return "Erreur! Le document ne peux pas être emprunté.";
    }
}
