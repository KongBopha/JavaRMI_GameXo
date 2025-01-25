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

        int win_player1 = 0;
        int win_player2 = 0;

        int row = 0;
        int column = 0;
        int straight_win = 0;
        int cross_win = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cross_win == 0) {
                    row = j;
                    column = j;
                } else if (cross_win == 1) {
                    row = j;
                    column = j == 0 ? 2 : j == 2 ? 0 : j;
                } else if (straight_win == 0) {
                    row = i;
                    column = j;
                } else if (straight_win == 1) {
                    row = j;
                    column = i;
                }
                if (board[row][column] == PLAYER1_MARKER) {
                    win_player1++;
                } else if (board[i][j] == PLAYER2_MARKER) {
                    win_player2++;
                }

                if (win_player1 == 3) {
                    System.out.println(win_player1);
                    return player1;
                } else if (win_player2 == 3) {
                    System.out.println(win_player1);
                    return player2;
                }

                if (cross_win == 0 && j == 2) {
                    cross_win = 1;
                    j = -1;
                }
            }

            win_player1 = 0;
            win_player2 = 0;

            if (straight_win == 0 && i == 2) {
                straight_win = 1;
                i = -1;
            }
        }

        return null;
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
