package bibliotheque;

/**
 * La classe <strong>ReservationException</strong> est lancée lorsqu'un document ne peut pas être réservé.
 */
public class ReservationException extends Exception {

    @Override
    public String getMessage() {
        return "Erreur! Le document ne peut pas être réservé.";
    }
}
