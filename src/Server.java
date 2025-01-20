import java.rmi.Naming;
import java.rmi.RemoteException;

public class Server {

    public static void main(String[] args) {

        // Step 1: Start the RMI registry
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099); // Default RMI port
            System.out.println("RMI Registry ready!");
        } catch (RemoteException e) {
            System.err.println("RMI registry already running!");
        }

        // Step 2: Bind the TicTacToe game to the RMI registry
        try {
            TicTacToe game = new TicTacToe(); // Create an instance of the TicTacToe game
            Naming.rebind("TicTacToe", game); // Bind the game instance to the name "TicTacToe"
            System.out.println("TicTacToe server ready!");

            // Optional: Call the garbage collector for cleaning up unused matches or resources
            game.garbageCollector();
        } catch (Exception e) {
            System.err.println("TicTacToe server failed!");
            e.printStackTrace(); // Log the exception stack trace for debugging
        }
    }
}
