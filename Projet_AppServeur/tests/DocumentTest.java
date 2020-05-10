package tests;

import bibliotheque.Abonne;
import bibliotheque.EmpruntException;
import bibliotheque.ReservationException;
import bibliotheque.RetourException;
import documents.DVD;
import documents.DocumentBasique;
import documents.Livre;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentTest {

    @Test
    public void testerAge() {
        Abonne ab = new Abonne("nomDeFamille", "Oscar", 13);
        DocumentBasique d1 = new DVD("Avatar", "realisateur connu", 16);

        assertThrows(EmpruntException.class, () -> {
            d1.emprunter(ab);
        });
    }

    @Test
    public void testerAge2() throws EmpruntException {
        Abonne ab = new Abonne("nomDeFamille", "Oscar", 22);
        DocumentBasique d1 = new DVD("Avatar", "realisateur connu", 16);

        d1.emprunter(ab);
        assertTrue(d1.estEmprunte());
    }

    @Test
    public void testerAge3() {
        Abonne ab = new Abonne("nomDeFamille", "Oscar", 13);
        DocumentBasique d1 = new DVD("Avatar", "realisateur connu", 16);

        assertThrows(ReservationException.class, () -> {
            d1.reserver(ab);
        });
    }

    @Test
    public void testerAge4() throws ReservationException {
        Abonne ab = new Abonne("nomDeFamille", "Oscar", 22);
        DocumentBasique d1 = new DVD("Avatar", "realisateur connu", 16);

        d1.reserver(ab);
        assertTrue(d1.estReserve());
    }

    @Test
    public void testerAge5() throws EmpruntException, RetourException {
        Abonne ab = new Abonne("nomDeFamille", "Oscar", 22);
        DocumentBasique d1 = new DVD("Avatar", "realisateur connu", 16);

        d1.emprunter(ab);
        assertTrue(d1.estEmprunte());
        d1.retour();
        assertFalse(d1.estEmprunte());
    }

    @Test
    public void testerLivre() throws EmpruntException, RetourException, ReservationException {
        Abonne ab = new Abonne("nomDeFamille", "Oscar", 13);
        DocumentBasique l1 = new Livre("Becoming", "Michelle Obama");
        DocumentBasique l2 = new Livre("The Overstory: A Novel", "Richard Powers");

        System.out.println(l1.toString());
        System.out.println(l2.toString());

        // On vérifie que les livres ont des numéros uniques.
        assertNotEquals(l1.numero(), l2.numero());

        // On vérifie que L1 n'est pas réservé et pas emprunté
        assertFalse(l1.estEmprunte());
        assertFalse(l1.estReserve());

        // *** Tests des services ***

        l1.emprunter(ab);

        // On vérifie que L1 est emprunté
        assertTrue(l1.estEmprunte());

        l2.emprunter(ab);

        // On vérifie que L1 est emprunté
        assertTrue(l2.estEmprunte());

        l2.retour();

        // On vérifie que le livre a bien été rendu
        assertFalse(l2.estEmprunte());

        l1.retour();
        l1.reserver(ab);

        // On vérifie que lel ivre est bien réserver
        assertTrue(l1.estReserve());

        l1.emprunter(ab);

        // On vérifie que le livre réservé par ab peut etre emprunter par lui
        assertTrue(l1.estEmprunte());
    }

    // On vérifie qu'un livre deja emprunté ne peut pas etre réumprunté
    @Test(expected = EmpruntException.class)
    public void testerEmprunt() throws EmpruntException {
        Abonne ab = new Abonne("dbkj", "Oscar");
        Abonne ab2 = new Abonne("kbsd", "Arthur");
        Livre l1 = new Livre("Becoming", "Michelle Obama");

        l1.emprunter(ab);
        l1.emprunter(ab2);
    }

    @Test(expected = EmpruntException.class)
    public void testerEmprunt2() throws EmpruntException, ReservationException {
        Abonne ab = new Abonne("dbkj", "Oscar");
        Abonne ab2 = new Abonne("kbsd", "Arthur");
        Livre l1 = new Livre("Becoming", "Michelle Obama");

        l1.reserver(ab);
        l1.emprunter(ab2);
    }

    // On vérifie qu'un livre non emprunté ne peux pas etre retourné
    @Test(expected = RetourException.class)
    public void testerRetour() throws RetourException {
        Livre l1 = new Livre("Becoming", "Michelle Obama");

        l1.retour();
    }

    // // On vérifie qu'un livre non emprunté mais reservé ne peux pas etre retourné
    @Test(expected = RetourException.class)
    public void testerRetour2() throws RetourException, ReservationException {
        Abonne ab = new Abonne("dbkj", "Oscar");
        Livre l1 = new Livre("Becoming", "Michelle Obama");

        l1.reserver(ab);
        l1.retour();
    }

    @Test(expected = ReservationException.class)
    public void testerReservation() throws ReservationException, EmpruntException {
        Abonne ab = new Abonne("dbkj", "Oscar");
        Livre l1 = new Livre("Becoming", "Michelle Obama");

        l1.emprunter(ab);
        l1.reserver(ab);
    }

    @Test(expected = ReservationException.class)
    public void testerReservation2() throws ReservationException, EmpruntException {
        Abonne ab = new Abonne("dbkj", "Oscar");
        Abonne ab2 = new Abonne("bvnoec", "jelckzcs");
        Livre l1 = new Livre("Becoming", "Michelle Obama");

        l1.emprunter(ab);
        l1.reserver(ab2);
    }

    @Test(expected = ReservationException.class)
    public void testerReservation3() throws ReservationException {
        Abonne ab2 = new Abonne("bvnoec", "jelckzcs");
        Livre l1 = new Livre("Becoming", "Michelle Obama");

        l1.reserver(ab2);
        l1.reserver(ab2);
    }

    @Test(expected = ReservationException.class)
    public void testerReservation4() throws ReservationException {
        Abonne ab = new Abonne("dbkj", "Oscar");
        Abonne ab2 = new Abonne("bvnoec", "jelckzcs");
        Livre l1 = new Livre("Becoming", "Michelle Obama");

        l1.reserver(ab2);
        l1.reserver(ab);
    }

    // Le test est valide si le document n'est plus réservé après la durée d'expiration de la réservation
    //@Test
    public void testerDureeMaxReservation() throws InterruptedException, ReservationException {
        Abonne ab = new Abonne("dbkj", "Oscar");
        Livre l1 = new Livre("Becoming", "Michelle Obama");

        l1.reserver(ab);
        TimeUnit.MINUTES.sleep((long) (Livre.DUREE_MAX_RESERVATION + 0.05));  // On rajoute 3s pour être sûr
        assertFalse(l1.estReserve());
    }


    @Test(expected = ReservationException.class)
    public void testerDureeMaxReservation2() throws ReservationException {
        Abonne ab = new Abonne("dbkj", "Oscar");
        Livre l1 = new Livre("Becoming", "Michelle Obama");

        l1.reserver(ab);
        // On n'attend pas la fin de la durée de réservation
        l1.reserver(ab);
    }

    //TODO : add test for time limit
}
