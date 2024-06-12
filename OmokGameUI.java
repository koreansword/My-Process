import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OmokGameUI extends JFrame {
    private static final int SIZE = 15;
    private static final char EMPTY = '.';
    private static final char BLACK = 'B';
    private static final char WHITE = 'W';

    private char[][] board = new char[SIZE][SIZE];
    private boolean blackTurn = true;
    private JButton[][] buttons = new JButton[SIZE][SIZE];

    public OmokGameUI() {
        // Initialize the board
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }

        // Set up the JFrame
        setTitle("Omok Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        // Create and add buttons to the frame
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int x, y;

        public ButtonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (placeStone(x, y)) {
                char stone = board[x][y];
                buttons[x][y].setText(String.valueOf(stone));
                if (stone == BLACK) {
                    buttons[x][y].setBackground(Color.BLACK);
                    buttons[x][y].setForeground(Color.WHITE);
                } else {
                    buttons[x][y].setBackground(Color.WHITE);
                    buttons[x][y].setForeground(Color.BLACK);
                }
                if (checkWin(x, y)) {
                    JOptionPane.showMessageDialog(null, (blackTurn ? "White" : "Black") + " wins!");
                    resetGame();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid move. Try again.");
            }
        }
    }

    private boolean placeStone(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || board[x][y] != EMPTY) {
            return false;
        }
        board[x][y] = blackTurn ? BLACK : WHITE;
        blackTurn = !blackTurn;
        return true;
    }

    private boolean checkWin(int x, int y) {
        char stone = board[x][y];
        if (stone == EMPTY) {
            return false;
        }
        return checkDirection(x, y, 1, 0, stone) || // Horizontal
                checkDirection(x, y, 0, 1, stone) || // Vertical
                checkDirection(x, y, 1, 1, stone) || // Diagonal /
                checkDirection(x, y, 1, -1, stone);  // Diagonal \
    }

    private boolean checkDirection(int x, int y, int dx, int dy, char stone) {
        int count = 0;
        for (int i = -4; i <= 4; i++) {
            int nx = x + i * dx;
            int ny = y + i * dy;
            if (nx >= 0 && nx < SIZE && ny >= 0 && ny < SIZE && board[nx][ny] == stone) {
                count++;
                if (count == 5) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    private void resetGame() {
        blackTurn = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
                buttons[i][j].setText("");
                buttons[i][j].setBackground(null);
                buttons[i][j].setForeground(Color.BLACK);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OmokGameUI().setVisible(true);
            }
        });
    }
}