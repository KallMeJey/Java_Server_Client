package bibliotheque;

/**
 * La classe <strong>ExistDejaException</strong> est lancée lorsqu'on essaye d'ajouter un document ou un abonne
 * qui existe déjà dans la bibliothèque.
 */
public class ExistDejaException extends Throwable {

    @Override
    public String getMessage() {
        return "Erreur! L'abonne ou le document existe deja.";
    }
}
