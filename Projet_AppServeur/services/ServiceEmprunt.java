package services;

import bibliotheque.Document;
import bibliotheque.EmpruntException;
import bibliotheque.NotExistException;
import serveur.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceEmprunt implements Runnable {
    private Socket socketClient;

    public ServiceEmprunt(Socket socketClient) {
        this.socketClient = socketClient;
    }

    @Override
    public void run() {
        BufferedReader fromClient;
        PrintWriter toClient;

        try {
            // Pour déboguer
            //System.out.println("Service d'emprunt connecté au client sur " + socketClient.getInetAddress() + ":" + socketClient.getLocalPort());

            // Pour se connecter et communiquer avec le socketClient
            fromClient = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            toClient = new PrintWriter(socketClient.getOutputStream(), true);

            try {
                // Lecture des données envoyées par le socketClient
                int noAbo = Integer.parseInt(fromClient.readLine());
                int noDoc = Integer.parseInt(fromClient.readLine());

                System.out.println("Requete recue: Abonne " + noAbo + " --> document " + noDoc);

                // Traitement de la requete d'emprunt
                Main.getBibliotheqe().emprunterDocument(noAbo, noDoc);
                Document doc = Main.getBibliotheqe().getDocumentByNumero(noDoc);
                toClient.println(doc.toString() + " emprunté avec succès!");

            } catch (NumberFormatException e) {
                    toClient.println("Erreur! Saisie invalide.");
                    System.err.println("Erreur! Saisie invalide.");

                } catch (NotExistException e) {
                    toClient.println(e.getMessage());
                    System.err.println(e.getMessage());

                } catch (EmpruntException e) {
                    toClient.println(e.getMessage());
                    System.err.println(e.getMessage());
                }

                // On ferme les entrées et les sorties coté serveur
            socketClient.close();


        } catch (IOException e) {
            System.err.println("Erreur lors de la connexion au client.");
            try {
                // On ferme la socket lorsqu'il y a une erreur pour éviter d'avoir plein de sockets ouvertes sur le serveur
                socketClient.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
