package documents;

public class Livre extends DocumentBasique {
    private String auteur;

    public Livre(String titre, String auteur) {
        super(titre);
        this.auteur = auteur;
    }

    @Override
    public String toString() {
        return "[Livre] " + super.toString() + " de " + auteur;
    }
}
