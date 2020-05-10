package documents;

import bibliotheque.Abonne;
import bibliotheque.EmpruntException;
import bibliotheque.ReservationException;

import java.util.Timer;
import java.util.TimerTask;

public class DVD extends DocumentBasique {
    private String realisateur;
    private int restrictionAge;

    public DVD(String titre, String realisateur, int ageMin) {
        super(titre);
        this.realisateur = realisateur;
        this.restrictionAge = ageMin;
    }

    /**
     * Verifie qua l'abonne a l'age minimum requis pour emprunter le document.
     *
     * @param ab : l'abonne
     * @return true si l'abonne a l'age requis, sinon false
     */
    public boolean ageMinOk(Abonne ab) {
        return ab.getAge() >= restrictionAge;
    }

    /**
     * @param ab : l'abonné qui souhaite emprunter le document
     * @throws EmpruntException : age minimal requis n'est pas respecté
     * @see DocumentBasique
     */
    @Override
    public void emprunter(Abonne ab) throws EmpruntException {
        // Si le document est deja emprunté on envoie une erreur
        if (estEmprunte() || !ageMinOk(ab)) {
            throw new EmpruntException();
        } else {
            // Si le document n'est pas emprunté mais qu'il est reservé
            if (estReserve()) {
                // On accepte l'emprunt ssi le demandeur est l'abonne qui a réservé le document
                if (getReserveur() == ab) {
                    setEmprunteur(ab);
                } else {
                    throw new EmpruntException();
                }
            } else {
                setEmprunteur(ab);
            }
        }
    }

    /**
     * @param ab : l'abonne qui souhaite réserver le document
     * @throws ReservationException : age minimal requis n'est pas respecté
     * @see DocumentBasique
     */
    @Override
    public void reserver(Abonne ab) throws ReservationException {
        // Si le document est deja emprunté par qqn on a pas le droit de le réserver
        if (estEmprunte() || !ageMinOk(ab)) {
            throw new ReservationException();
        } else {
            if (estReserve()) {
                throw new ReservationException();
            } else {
                setReserveur(ab);   // valide pendant 2h, annuler la reservation après.
                setLimiteReservation(new Timer());
                getLimiteReservation().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setReserveur(null);
                        System.out.println("Temps écoulé! La réservation de " + super.toString() + " a été annulé.");
                    }
                }, DUREE_MAX_RESERVATION * 60 * 1000);
            }
        }
    }

    @Override
    public String toString() {
        return "[DVD] " + super.toString() + " de " + realisateur;
    }
}
