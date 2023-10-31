import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeGUI());
    }
}

class TicTacToeGUI {
    private JButton[][] buttons;
    private TicTacToeGame game;

    public TicTacToeGUI() {
        game = new TicTacToeGame();

        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        JPanel panel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                panel.add(buttons[i][j]);
            }
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (game.isValidMove(row, col)) {
                buttons[row][col].setText(game.getCurrentPlayerSymbol());
                game.makeMove(row, col);

                if (game.isGameOver()) {
                    String result = game.getGameResult();
                    if (result.equals("Draw")) {
                        JOptionPane.showMessageDialog(null, "It's a draw!");
                    } else {
                        JOptionPane.showMessageDialog(null, result + " wins!");
                    }
                    System.exit(0);
                }
            }
        }
    }
}

class TicTacToeGame {
    private TicTacToeBoard board;
    private Player currentPlayer;

    public TicTacToeGame() {
        board = new TicTacToeBoard();
        currentPlayer = new Player("X");
    }

    public boolean isValidMove(int row, int col) {
        return board.isCellEmpty(row, col);
    }

    public String getCurrentPlayerSymbol() {
        return currentPlayer.getSymbol();
    }

    public void makeMove(int row, int col) {
        board.setCell(row, col, currentPlayer.getSymbol());
        currentPlayer = (currentPlayer.getSymbol().equals("X")) ? new Player("O") : new Player("X");
    }

    public boolean isGameOver() {
        return board.isBoardFull() || board.checkWin();
    }

    public String getWinner() {
        if (board.checkWin()) {
            return currentPlayer.getSymbol().equals("X") ? "O" : "X";
        }
        return "Draw";
    }

    public String getGameResult() {
        if (board.checkWin()) {
            return currentPlayer.getSymbol().equals("X") ? "O" : "X";
        }
        return "Draw";
    }
}

class TicTacToeBoard {
    private String[][] board;

    public TicTacToeBoard() {
        board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
    }

    public boolean isCellEmpty(int row, int col) {
        return board[row][col].isEmpty();
    }

    public void setCell(int row, int col, String symbol) {
        board[row][col] = symbol;
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].isEmpty()) {
                return true;
            }
            // Check columns
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].isEmpty()) {
                return true;
            }
        }

        // Check diagonals
        if ((board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].isEmpty()) ||
                (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].isEmpty())) {
            return true;
        }

        return false;
    }

}

class Player {
    private String symbol;

    public Player(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
