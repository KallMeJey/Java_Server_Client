package documents;

import bibliotheque.*;

import java.util.Timer;
import java.util.TimerTask;

public abstract class DocumentBasique implements Document {
    public static final int DUREE_MAX_RESERVATION = 120;    // en minutes
    private static int numDocument = 0;

    private int numero;
    private String titre;
    private Abonne emprunteParAbonne;
    private Abonne reserveParAbonne;
    private Timer limiteReservation;

    public DocumentBasique(String titre) {
        this.numero = numDocument++;
        this.titre = titre;
        this.emprunteParAbonne = null;
        this.reserveParAbonne = null;
        this.limiteReservation = null;
    }

    /**
     * Sert a instancier un nouveau Timer dans les classes filles.
     *
     * @param limiteReservation : objet Timer pour instancier
     */
    public void setLimiteReservation(Timer limiteReservation) {
        this.limiteReservation = limiteReservation;
    }

    /**
     * Sert a definir les actions du Timer dans les classes filles.
     *
     * @return le Timer du document.
     */
    public Timer getLimiteReservation() {
        return limiteReservation;
    }

    /**
     * Verifie si le document est emprunté ou non.
     *
     * @return true si le document est disponibe, false sinon
     */
    public boolean estEmprunte() {
        return emprunteParAbonne != null;
    }


    /**
     * Vérifie si le document est réservé ou non.
     *
     * @return true si le document est reservé, false sinon
     */
    public boolean estReserve() {
        return reserveParAbonne != null;
    }


    /**
     * Récupère le numéro unique du document
     *
     * @return le numéro unique du document
     */
    @Override
    public int numero() {
        return numero;
    }


    /**
     * Réserve le document si possible, sinon renvoie une erreur.
     *
     * @param ab : l'abonne qui souhaite réserver le document
     * @throws ReservationException : le document n'est pas disponible ou est deja reserver par qqn
     */
    @Override
    public void reserver(Abonne ab) throws ReservationException {
        // Si le document est deja emprunté par qqn on a pas le droit de le réserver
        if (estEmprunte()) {
            throw new ReservationException();
        } else {
            if (estReserve()) {
                throw new ReservationException();
            } else {
                reserveParAbonne = ab;  // valide pendant 2h, annuler la reservation après.
                limiteReservation = new Timer();
                limiteReservation.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        reserveParAbonne = null;
                        System.out.println("Temps écoulé! La réservation de " + this.toString() + " a été annulé.");
                    }
                }, DUREE_MAX_RESERVATION * 60 * 1000);
            }
        }
    }


    /**
     * Emprunte le document si possible, sinon renvoie une erreur.
     *
     * @param ab : l'abonné qui souhaite emprunter le document
     * @throws EmpruntException : le document est déjà emprunter ou réservé par qqn d'autre
     */
    @Override
    public void emprunter(Abonne ab) throws EmpruntException {
        // Si le document est deja emprunté on envoie une erreur
        if (estEmprunte()) {
            throw new EmpruntException();
        } else {
            // Si le document n'est pas emprunté mais qu'il est reservé
            if (estReserve()) {
                // On accepte l'emprunt ssi le demandeur est l'abonne qui a réservé le document
                if (reserveParAbonne == ab) {
                    emprunteParAbonne = ab;
                } else {
                    throw new EmpruntException();
                }
            } else {
                emprunteParAbonne = ab;
            }
        }
    }


    /**
     * Retourne le document si possible, sinon renvoie une erreur.
     *
     * @throws RetourException : le document n'est pas deja emprunté, impossible de le retourner
     */
    @Override
    public void retour() throws RetourException {
        if (emprunteParAbonne == null) {
            throw new RetourException();
        } else {
            emprunteParAbonne = null;
        }
    }


    /**
     * @return le titre du document.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return l'abonne qui a réservé ce document.
     */
    public Abonne getReserveur() {
        return reserveParAbonne;
    }

    /**
     * @param reserveParAbonne : l'abonne qui réserve le document
     */
    public void setReserveur(Abonne reserveParAbonne) {
        this.reserveParAbonne = reserveParAbonne;
    }

    /**
     * @param emprunteParAbonne : l'abonne qui emprunte le document
     */
    public void setEmprunteur(Abonne emprunteParAbonne) {
        this.emprunteParAbonne = emprunteParAbonne;
    }

    @Override
    public String toString() {
        return "#" + numero + " " + titre;
    }

}
