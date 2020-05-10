package bibliotheque;

import java.util.ArrayList;
import java.util.List;

public class Bibliotheque {
    private List<Abonne> abonnes;
    private List<Document> documents;


    public Bibliotheque() {
        this.abonnes = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    /**
     * Récupère un abonné grace a son numero unique
     *
     * @param num : numéro unique de l'abonné
     * @return l'abonné en question s'il existe dans la bibliotheque, null sinon.
     */
    public Abonne getAbonneByNumero(int num) {
        for (Abonne a : abonnes) {
            if (a.getNumero() == num) {
                return a;
            }
        }
        // l'abonne n'existe pas
        return null;
    }


    /**
     * Récupère un document par son numero unique
     *
     * @param num : le numero du document
     * @return le document en question s'il existe dans la bibliotheque, renvoie null sinon.
     */
    public Document getDocumentByNumero(int num) {
        for (Document d : documents) {
            if (d.numero() == num) {
                return d;
            }
        }
        // le document n'existe pas
        return null;
    }

    /**
     * Permet d'ajouter un abonne dans la bibliotheque
     *
     * @param ab : l'abonne a rajouter
     */
    public void ajouterAbonne(Abonne ab) throws ExistDejaException {
        if (getAbonneByNumero(ab.getNumero()) == ab) {
            throw new ExistDejaException();
        } else {
            abonnes.add(ab);
            System.out.println("L'abonné " + ab.toString() + " a été ajouté!");
        }
    }

    /**
     * Permet de rajouter un document dans la bilbiotheque
     *
     * @param doc : le document a rajouter
     */
    public void ajouterDocument(Document doc) throws ExistDejaException {
        if (getDocumentByNumero(doc.numero()) == doc) {
            throw new ExistDejaException();
        } else {
            documents.add(doc);
            System.out.println("Le document " + doc.toString() + " a été ajouté!");
        }
    }


    /**
     * Permet d'emprunter un document de la bibliothèque
     *
     * @param numAbonne   : le numéro d'abonne qui veut emprunter le document.
     * @param numDocument : le numéro du document que veut emprunter l'abonne.
     */
    public void emprunterDocument(int numAbonne, int numDocument) throws NotExistException, EmpruntException {
        Abonne ab = getAbonneByNumero(numAbonne);
        Document doc = getDocumentByNumero(numDocument);

        // Si l'abonne et le document existe
        if (doc != null && ab != null) {
            synchronized (doc) {
                doc.emprunter(ab);
                System.out.println(ab.toString() + " a emprunté " + doc.toString() + " avec succès!");
            }

        } else {
            throw new NotExistException();
        }
    }


    /**
     * Permet de réserver un document de la bibliothèque
     *
     * @param numAbonne   : le numéro d'abonne qui veut réserver le document.
     * @param numDocument : le numéro du document que veut réserver l'abonne.
     */
    public void reserverDocument(int numAbonne, int numDocument) throws NotExistException, ReservationException {
        Abonne ab = getAbonneByNumero(numAbonne);
        Document doc = getDocumentByNumero(numDocument);

        // Si l'abonne et le document existe
        if (ab != null && doc != null) {
            synchronized (doc) {
                doc.reserver(ab);
                System.out.println(ab.toString() + " a réservé " + doc.toString() + " avec succès!");
            }

        } else {
            throw new NotExistException();
        }
    }


    /**
     * Permet de retourner un document à la bibliothèque
     *
     * @param numDocument : le numéro du document à retourner.
     */
    public void retournerDocument(int numDocument) throws NotExistException, RetourException {
        Document d = getDocumentByNumero(numDocument);

        // Si le document existe
        if (d != null) {
            synchronized (d) {
                d.retour();
                System.out.println(d.toString() + " retourné avec succès!");
            }

        } else {
            throw new NotExistException();
        }
    }

}
