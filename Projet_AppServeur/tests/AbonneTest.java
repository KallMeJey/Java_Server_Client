package tests;

import bibliotheque.Abonne;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class AbonneTest {

    @Test
    public void testerAbonne() {
        Abonne a1 = new Abonne("nomDeFamille", "Oscar", 32);
        Abonne a2 = new Abonne("nomDeFamille2", "Pierre", 13);


        // On vérifie que les numéros des abonnés sont uniques
        assertNotEquals(a1.getNumero(), a2.getNumero());
    }
}
