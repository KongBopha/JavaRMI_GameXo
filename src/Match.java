public class Match {

    private Player player1, player2;
    private char[][] board = new char[3][3];
    private boolean ready;
    private Player currentPlayer;

    private static final char EMPTY = '.';
    private static final char PLAYER1_MARKER = 'X';
    private static final char PLAYER2_MARKER = 'O';

    public Match(Player player1) {
        this.player1 = player1;
        this.ready = false;
        this.currentPlayer = this.player1;

        // Initialize the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = EMPTY;
            }
        }
    }

    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
        this.ready = true;
    }

    public boolean canDelete() {
        return this.player1 == null && this.player2 == null;
    }

    public Player getWinner() {
        // Check rows
        if ((board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != ' '))
            return board[0][0] == PLAYER1_MARKER ? player1 : player2;
        if ((board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != ' '))
            return board[1][0] == PLAYER1_MARKER ? player1 : player2;
        if ((board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != ' '))
            return board[2][0] == PLAYER1_MARKER ? player1 : player2;

        // Check columns
        if ((board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != ' '))
            return board[0][0] == PLAYER1_MARKER ? player1 : player2;
        if ((board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != ' '))
            return board[0][1] == PLAYER1_MARKER ? player1 : player2;
        if ((board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != ' '))
            return board[0][2] == PLAYER1_MARKER ? player1 : player2;

        // Check diagonals
        if ((board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' '))
            return board[0][0] == PLAYER1_MARKER ? player1 : player2;
        if ((board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' '))
            return board[0][2] == PLAYER1_MARKER ? player1 : player2;

        return null; // No winner
    }

    public boolean isReady() {
        return this.ready;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public char[][] getBoard() {
        return this.board;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    private void changeTurn() {
        this.currentPlayer = (this.currentPlayer == this.player1 ? this.player2 : this.player1);
    }

    public int move(int row, int column) {
        if ((row < 0) || (row > 2) || (column < 0) || (column > 2)) {
            return 0; // Invalid move
        }

        if (board[row][column] != EMPTY) {
            return 0; // Cell already occupied
        }

        if (currentPlayer == player1) {
            board[row][column] = PLAYER1_MARKER;
        } else if (currentPlayer == player2) {
            board[row][column] = PLAYER2_MARKER;
        } else {
            return 0; // Invalid player
        }

        currentPlayer.updateTimestamp();
        this.changeTurn();
        return 1; // Move successful
    }

    public boolean hasTimedOut() {
        return ((player1 == null && player2 == null) ||
                (player1 == null && player2.hasTimedOut()) ||
                (player1.hasTimedOut() && player2 == null) ||
                (player1.hasTimedOut() && player2.hasTimedOut()));
    }
}
