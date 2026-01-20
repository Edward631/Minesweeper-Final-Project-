import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinesweeperFrame extends JFrame {

    private Board board;
    private CellButton[][] buttons;
    private GameHeader header;
    private Timer gameTimer;
    private int elapsedSeconds = 0;
    private boolean gameOver = false;
    private static int TOTAL_ROWS = 10;
    private static int TOTAL_COLS = 10; 

    public MinesweeperFrame(int rows, int cols, int mines) {
        super("Minesweeper");

        board = new Board(rows, cols, mines);
        buttons = new CellButton[rows][cols];

        setLayout(new BorderLayout());

        // Header with timer
        header = new GameHeader(e -> {
            gameTimer.stop();
            this.dispose ();
            new StartScreen();
        });
        add(header, BorderLayout.NORTH);

        // Timer updates every second
        gameTimer = new Timer(1000, e -> {
            elapsedSeconds++;
            header.updateTime(elapsedSeconds);
        });
        gameTimer.start();

        // Create grid
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        add(gridPanel, BorderLayout.CENTER);

        // Populate buttons
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                CellButton btn = new CellButton(r, c);
                buttons[r][c] = btn;

                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (gameOver) return;

                        if (SwingUtilities.isRightMouseButton(e)) {
                            handleRightClick(btn);
                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            handleLeftClick(btn);
                        }
                    }
                });

                gridPanel.add(btn);
            }
        }
        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack (); 
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // LEFT-CLICK: reveal
    private void handleLeftClick(CellButton btn) {
        Cell cell = board.getCell(btn.row, btn.col);

        if (cell.isFlagged() || cell.isRevealed()) return;

        if (cell.isMine()) {
            cell.reveal();
            btn.setBackground(Color.RED);
            gameOver = true;
            gameTimer.stop();
            animateMines();
            return;
        }else {
        fill (btn.row, btn.col);
        checkWin ();
        }
        
    

        //SafeCell safe = (SafeCell) cell;
        //btn.setEnabled(false);
        //btn.setText(String.valueOf(safe.getNeighborMines()));
    }

        // Optional: expand empty areas (not implemented here but easy to add)
            
            
        

    // RIGHT-CLICK: toggle flag
    private void handleRightClick(CellButton btn) {
        Cell cell = board.getCell(btn.row, btn.col);

        if (cell.isRevealed()) return;

        cell.toggleFlag();

        if (cell.isFlagged()) {
            btn.setText("X");
        } else {
            btn.setText("");
        }
    }

    // Animate mines turning red sequentially
    private void animateMines() {
        new Thread(() -> {
            try {
                for (int r = 0; r < buttons.length; r++) {
                    for (int c = 0; c < buttons[0].length; c++) {
                        Cell cell = board.getCell(r, c);

                        if (cell.isMine()) {
                            CellButton btn = buttons[r][c];
                            SwingUtilities.invokeLater(() -> {
                                btn.setBackground(Color.RED);
                                btn.setText("💣");
                            });
                            Thread.sleep(80); // small delay for animation
                        }
                    }
                }
            } catch (InterruptedException ignored) {}
        }).start();
    }
    
    public void fill(int row, int col) {
        if (row < 0 || row >= buttons.length || col < 0 || col >= buttons[0].length) { //if it is not in board then return
           return;
     }

        Cell cell = board.getCell(row, col);
        CellButton btn = buttons[row][col];

        
        if (cell.isRevealed() || cell.isFlagged()) { //if it is already cheacked then return
          return;
     }

        
        cell.reveal();

        
        SafeCell safe = (SafeCell) cell;
        int count = safe.getNeighborMines();

        btn.setBackground(Color.LIGHT_GRAY);
        btn.setForeground(Color.BLACK);

        if (count > 0) { // reveals all non zero cells
            
            btn.setText(String.valueOf(count));
            btn.setForeground (getNumberCol(count));
            return; 
        } else { // recurses if is zero
           
            btn.setText("");
        
         
          for (int i = -1; i <= 1; i++) { //looks in all directions
             for (int j = -1; j <= 1; j++) {
                 
                    if (i == 0 && j == 0) continue;
                    fill(row + i, col + j);
                }
            }
        }
    }
    public Color getNumberCol (int n){ // uses n to find the correct color for number
        switch (n) {
            case 1: return Color.BLUE;
            case 2: return new Color(0, 128, 0); // green
            case 3: return Color.RED;
            case 4: return new Color(0, 0, 128); // blue
            default: return Color.BLACK;
        }
    }
    
    public void checkWin() {
    int unrevealedSafeCells = 0;
    for (int r = 0; r < buttons.length; r++) {
        for (int c = 0; c < buttons[0].length; c++) {
            Cell cell = board.getCell(r, c);
            if (!cell.isMine() && !cell.isRevealed()) {
                unrevealedSafeCells++;
            }
        }
    }
    
    if (unrevealedSafeCells == 0 && !gameOver) {
        gameOver = true;
        gameTimer.stop();
        
        // 1. Ask for Name
        String name = JOptionPane.showInputDialog(this, 
            "You Won Time: " + elapsedSeconds + " seconds.\nEnter your name:");
        

        //  Save to scoreManager
        ScoreManager manager = new ScoreManager();
        manager.saveScore(name, elapsedSeconds);
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MinesweeperFrame(TOTAL_ROWS, TOTAL_COLS, 12));// 12
    }
}
