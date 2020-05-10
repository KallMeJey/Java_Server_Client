package bibliotheque;

public class Abonne {
    private int numero;
    private String nom;
    private String prenom;
    private int age;

    private static int numAbonne = 0;

    public Abonne(String nom, String prenom) {
        this.numero = numAbonne++;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public Abonne(String nom, String prenom, int age) {
        this.numero = numAbonne++;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "N°" + numero + " " + prenom + ' ' + nom + ", " + age + " ans";
    }
}
