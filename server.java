import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server in attesa di connessioni...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connesso: " + clientSocket.getInetAddress());

                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());

                // Genera una mossa del portiere iniziale
                String mossaPortiere = generaMossaPortiere();

                outToClient.writeObject("Benvenuto! Il portiere ha scelto: " + mossaPortiere);

                // Ricevi la mossa del giocatore
                String mossaGiocatore = (String) inFromClient.readObject();

                // Determina il risultato
                String risultato = determinaRisultato(mossaGiocatore, mossaPortiere);
                outToClient.writeObject(risultato);

                clientSocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String generaMossaPortiere() {
        String[] opzioni = {"alto-sinistra", "basso-sinistra", "alto-centro", "basso-centro", "alto-destra", "basso-destra"};
        Random random = new Random();
        int index = random.nextInt(opzioni.length);
        return opzioni[index];
    }

    private static String determinaRisultato(String mossaGiocatore, String mossaPortiere) {
        if (mossaGiocatore.equals(mossaPortiere)) {
            return "Rigore parato!";
        } else {
            return "Goal!";
        }
    }
}
