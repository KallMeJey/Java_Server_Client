package bibliotheque;

/**
 * La classe <strong>NotExistException</strong> est lancée lorsqu'un numéro de document ou d'abonne donné n'existe pas.
 */
public class NotExistException extends Throwable {

    @Override
    public String getMessage() {
        return "Erreur! Le document ou l'abonne spécifié n'existe pas dans la bibliothèque.";
    }
}
