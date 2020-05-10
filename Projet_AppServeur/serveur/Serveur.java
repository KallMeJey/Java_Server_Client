package serveur;

import services.ServiceEmprunt;
import services.ServiceReservation;
import services.ServiceRetour;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur implements Runnable {
    private ServerSocket serverSock;

    public Serveur(int noPort) throws IOException {
        this.serverSock = new ServerSocket(noPort);
    }

    /**
     * Lance le service adequat dans un nouveau Thread en fonction du numero de port donne
     */
    @Override
    public void run() {
        try {
            Socket client = null;
            while (true) {
                client = serverSock.accept();
                int portUtilise = serverSock.getLocalPort();

                switch (portUtilise) {
                    case 2500:
                        System.out.println("Lancement du service de réservation...");
                        new Thread(new ServiceReservation(client)).start();
                        break;
                    case 2600:
                        System.out.println("Lancement du service d'emprunt...");
                        new Thread(new ServiceEmprunt(client)).start();
                        break;
                    case 2700:
                        System.out.println("Lancement du service de retour...");
                        new Thread(new ServiceRetour(client)).start();
                        break;
                    default:
                        System.out.println("Lancement du service d'emprunt...");
                        new Thread(new ServiceEmprunt(client)).start();
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de lancement du service!");
            e.printStackTrace();
            try {
                // En cas d'erreurs on n'oublie pas de fermer la socket serveur.
                serverSock.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
