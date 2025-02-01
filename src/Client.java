import java.rmi.Naming;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Client <server> <player_name>");
            System.exit(1);
        }

        try {
            ITicTacToe game = (ITicTacToe) Naming.lookup("//" + args[0] + "/TicTacToe");
            Scanner stdin = new Scanner(System.in);

            // Register player on the remote server
            int id = game.addPlayer(args[1]);

            if (id == -1) {
                System.err.println("Player name already in use!");
                System.exit(1);
            }

            if (id == -2) {
                System.err.println("Maximum number of players on the server exceeded!");
                System.exit(1);
            }

            int hasMatch = game.hasMatch(id);

            System.out.println("Looking for a match ...");

            // Check if there is an active match
            while (hasMatch != 1 && hasMatch != 2) {
                if (hasMatch == -2) {
                    System.err.println("Waiting time expired!");
                    System.exit(1);
                }

                if (hasMatch == -1) {
                    System.err.println("Server error!");
                    System.exit(1);
                }

                // Check every 1 second
                Thread.sleep(1000);
                hasMatch = game.hasMatch(id);
            }

            System.out.println("Second player " + game.getOpponent(id) + " joined ....");

            int isMyTurn;
            String message = null;

            // Game loop
            while (true) {

                // Check if it is this player's turn
                isMyTurn = game.isMyTurn(id);

                if (isMyTurn == -2) {
                    System.err.println("There are not two players in this match!");
                    game.endMatch(id);
                    System.exit(1);
                }

                if (isMyTurn == -1) {
                    System.err.println("Server error!");
                    game.endMatch(id);
                    System.exit(1);
                }

                switch (isMyTurn) {
                    case 2:
                        message = "You won!";
                        break;
                    case 3:
                        message = "You lost!";
                        break;
                    case 4:
                        message = "It's a draw!";
                        break;
                    case 5:
                        message = "You won by forfeit!";
                        break;
                    case 6:
                        message = "You lost by forfeit!";
                        break;
                    case 7:
                        message = "You lost by forfeit!";
                        break;
                }

                // End of game, display result and exit
                if (isMyTurn > 1 && isMyTurn < 7) {
                    System.out.println(message);

                    if (game.endMatch(id) == -1) {
                        System.err.println("Error ending the game!");
                        System.exit(1);
                    } else {
                        System.out.println("Match finished!");
                        System.exit(0);
                    }
                }

                // Only move pieces on the board
                int ret_movePiece = -1;

                while ((ret_movePiece != 1) && (ret_movePiece != -3) && (isMyTurn == 1)) {

                    System.out.println(game.getBoard(id));

                    System.out.println("Enter the position of the piece to be moved.");
                    System.out.print("Row: ");
                    int row = stdin.nextInt();

                    System.out.print("Column: ");
                    int column = stdin.nextInt();

                    ret_movePiece = game.move(id, row, column);

                    switch (ret_movePiece) {
                        case 2:
                            System.out.println("You lost by forfeit!");
                            game.endMatch(id);
                            System.exit(0);
                        case 1:
                            System.out.println("Move completed successfully");
                            System.out.println(game.getBoard(id));
                            break;
                        case 0:
                            System.out.println("Invalid position!");
                            break;
                        case -1:
                            System.out.println("Invalid parameters!");
                            break;
                        case -2:
                            System.err.println("There are not two players in this match!");
                            game.endMatch(id);
                            System.exit(1);
                        case -3:
                            System.out.println("Invalid parameters!");
                            break;
                        case -4:
                            System.out.println("It's not your turn to move!");
                            break;
                    }

                }
            }

        } catch (Exception e) {
            System.err.println("TicTacToe client failed!");
            System.err.println(e.toString());
        }
    }
}
