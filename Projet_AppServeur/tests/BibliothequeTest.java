package tests;

import bibliotheque.*;
import documents.DocumentBasique;
import documents.Livre;
import org.junit.Assert;
import org.junit.Test;

public class BibliothequeTest {
    Bibliotheque b;
    Abonne oscar = new Abonne("ouioui", "Oscar", 13);
    Abonne jey = new Abonne("nonnon", "Jeneifan", 18);
    Document doc2;

    // Verification que l'on ne peux pas ajouter deux fois le meme abonne
    @Test(expected = ExistDejaException.class)
    public void testerExistDejaException() throws ExistDejaException {
        b = new Bibliotheque();

        b.ajouterAbonne(oscar);
        b.ajouterAbonne(oscar);
    }

    // Verification que l'on ne peux pas ajouter deux fois le meme document
    @Test(expected = ExistDejaException.class)
    public void testerExistDejaException2() throws ExistDejaException {
        Document d = new Livre("titre", "auteur");
        b = new Bibliotheque();

        b.ajouterDocument(d);
        b.ajouterDocument(d);
    }

    // Verification des méthodes getDocumentByNumero et getAbonneByNumero
    @Test
    public void testerGetDocEtAbonne() throws ExistDejaException {
        b = new Bibliotheque();
        b.ajouterAbonne(oscar);
        DocumentBasique doc1 = new Livre("Becoming", "Michelle Obama");
        b.ajouterDocument(doc1);

        DocumentBasique livre = (DocumentBasique) b.getDocumentByNumero(doc1.numero());
        Assert.assertSame("Becoming", livre.getTitre());

        Abonne abonne = b.getAbonneByNumero(oscar.getNumero());
        Assert.assertSame(oscar.getNumero(), abonne.getNumero());

        livre = (DocumentBasique) b.getDocumentByNumero(-1);
        Assert.assertNull(livre);

        abonne = b.getAbonneByNumero(-1);
        Assert.assertNull(abonne);
    }


    // Verififcation que la saisie d'un document introuvable envoie une erreur
    @Test(expected = NotExistException.class)
    public void testerEmprunter() throws NotExistException, ExistDejaException, EmpruntException {
        b = new Bibliotheque();
        b.ajouterAbonne(oscar);
        b.ajouterDocument(new Livre("Becoming", "Michelle Obama"));

        b.emprunterDocument(0, 3);
    }

    // Verification que la saisie  d'un abonne invalide envoie une erreur
    @Test(expected = NotExistException.class)
    public void testerEmprunter2() throws NotExistException, ExistDejaException, EmpruntException {
        b = new Bibliotheque();
        b.ajouterAbonne(oscar);
        b.ajouterDocument(new Livre("Becoming", "Michelle Obama"));

        b.emprunterDocument(3, 0);
    }

    // Verification que la saisie d'un mauvais abonne et d'un mauvais document renvoie une erreur
    @Test(expected = NotExistException.class)
    public void testerEmprunter3() throws NotExistException, EmpruntException {
        b = new Bibliotheque();

        b.emprunterDocument(2, 1);
    }


    // Verification qu'un document déjà emprunté ne peux pas etre emprunté (abonnés différents)
    @Test(expected = EmpruntException.class)
    public void testerEmprunter4() throws NotExistException, ExistDejaException, EmpruntException {
        b = new Bibliotheque();
        DocumentBasique doc1 = new Livre("Becoming", "Michelle Obama");
        b.ajouterAbonne(oscar);
        b.ajouterAbonne(jey);

        b.ajouterDocument(doc1);

        b.emprunterDocument(oscar.getNumero(), doc1.numero());
        b.emprunterDocument(jey.getNumero(), doc1.numero());
    }

    // Verification qu'un document déjà emprunté ne peux pas etre emprunté (meme abonné)
    @Test(expected = EmpruntException.class)
    public void testerEmprunter5() throws NotExistException, ExistDejaException, EmpruntException {
        b = new Bibliotheque();
        DocumentBasique doc1 = new Livre("Becoming", "Michelle Obama");
        b.ajouterAbonne(oscar);
        b.ajouterDocument(doc1);

        b.emprunterDocument(oscar.getNumero(), doc1.numero());
        b.emprunterDocument(oscar.getNumero(), doc1.numero());
    }


    // Verification qu'un livre non-emprunté ne peux pas être retourné
    @Test(expected = RetourException.class)
    public void testerRetour() throws ExistDejaException, NotExistException, RetourException {
        b = new Bibliotheque();
        DocumentBasique doc1 = new Livre("Becoming", "Michelle Obama");
        b.ajouterAbonne(oscar);
        b.ajouterDocument(doc1);

        b.retournerDocument(doc1.numero());
    }


    // Verificication qu'un livre emprunté peux etre retourné
    @Test
    public void testerRetour2() throws NotExistException, ExistDejaException, EmpruntException, RetourException {
        b = new Bibliotheque();
        DocumentBasique doc1 = new Livre("Becoming", "Michelle Obama");
        b.ajouterAbonne(oscar);
        b.ajouterDocument(doc1);

        b.emprunterDocument(oscar.getNumero(), doc1.numero());
        b.retournerDocument(doc1.numero());
    }


    // Verification qu'un document déjà reservé ne peux pas etre reservé
    @Test(expected = ReservationException.class)
    public void testerReservation() throws ExistDejaException, NotExistException, ReservationException {
        b = new Bibliotheque();
        DocumentBasique doc1 = new Livre("Becoming", "Michelle Obama");
        b.ajouterAbonne(oscar);
        b.ajouterDocument(doc1);

        b.reserverDocument(oscar.getNumero(), doc1.numero());
        b.reserverDocument(oscar.getNumero(), doc1.numero());
    }


    // Verification qu'un livre peux etre réservé sans erreurs
    @Test
    public void testerReservation2() throws ExistDejaException, NotExistException, ReservationException {
        b = new Bibliotheque();
        DocumentBasique doc1 = new Livre("Becoming", "Michelle Obama");
        b.ajouterAbonne(oscar);
        b.ajouterDocument(doc1);

        b.reserverDocument(oscar.getNumero(), doc1.numero());
    }


    // Verification qu'un livre réservé ne peux pas etre emmprunté par qqn d'autre
    @Test(expected = EmpruntException.class)
    public void testerReservation3() throws ExistDejaException, NotExistException, ReservationException, EmpruntException {
        b = new Bibliotheque();
        DocumentBasique doc1 = new Livre("Becoming", "Michelle Obama");
        b.ajouterAbonne(oscar);
        b.ajouterAbonne(jey);
        b.ajouterDocument(doc1);

        b.reserverDocument(oscar.getNumero(), doc1.numero());
        b.emprunterDocument(jey.getNumero(), doc1.numero());
    }


    // Verification qu'un livre réservé peux etre emmprunté par le réserveur
    @Test
    public void testerReservation4() throws ExistDejaException, NotExistException, ReservationException, EmpruntException {
        b = new Bibliotheque();
        DocumentBasique doc1 = new Livre("Becoming", "Michelle Obama");
        b.ajouterAbonne(oscar);
        b.ajouterDocument(doc1);

        b.reserverDocument(oscar.getNumero(), doc1.numero());
        b.emprunterDocument(oscar.getNumero(), doc1.numero());
    }
}
