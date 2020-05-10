package serveur;

import bibliotheque.Abonne;
import bibliotheque.Bibliotheque;
import bibliotheque.ExistDejaException;
import documents.DVD;
import documents.Livre;

import java.io.IOException;

public class Main {
    private static Bibliotheque bibliotheque;

    /**
     * Permet au services et au serveur d'intéragir avec la bibliothèque directement.
     *
     * @return la bilbiothèque
     */
    public static Bibliotheque getBibliotheqe() {
        return bibliotheque;
    }

    public static void main(String[] args) {
        bibliotheque = new Bibliotheque();
        try {
            bibliotheque.ajouterDocument(new Livre("Becoming", "Michelle Obama"));
            bibliotheque.ajouterDocument(new Livre("Harry Potter", "J.K. Rowling"));
            bibliotheque.ajouterDocument(new DVD("Avatar", "James Cameron", 16));
            bibliotheque.ajouterDocument(new DVD("Alien", "Ridley Scott", 12));
            bibliotheque.ajouterAbonne(new Abonne("ouioui", "Oscar", 22));
            bibliotheque.ajouterAbonne(new Abonne("nonnon", "Jeneifan", 11));
        } catch (ExistDejaException e) {
            e.printStackTrace();
        }

        // On lance les 3 services
        try {
            new Thread(new Serveur(2500)).start();
            new Thread(new Serveur(2600)).start();
            new Thread(new Serveur(2700)).start();
        } catch (IOException e) {
            System.err.println("Erreur! Le serveur n'a pas pu etre lancé.");
            e.printStackTrace();
        }
    }
}
