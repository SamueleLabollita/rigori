import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());

            // Ricevi il messaggio di benvenuto dal server
            String benvenuto = (String) inFromServer.readObject();
            System.out.println(benvenuto);

            // Richiedi la mossa del giocatore
            Scanner scanner = new Scanner(System.in);
            System.out.print("Scegli dove tirare (alto-sinistra, basso-sinistra, alto-centro, basso-centro, alto-destra, basso-destra): ");
            String mossaGiocatore = scanner.nextLine();

            // Invia la mossa del giocatore al server
            outToServer.writeObject(mossaGiocatore);

            // Ricevi il risultato dal server
            String risultato = (String) inFromServer.readObject();
            System.out.println("Risultato: " + risultato);

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
