import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {

    public StartScreen() {
        super("Minesweeper - Welcome");
        
        setLayout(new BorderLayout(10, 10));
        
        JTextArea instruct = new JTextArea(); 
        instruct.setText(
            "Welcome to Minesweeper!\n\n" +
            "HOW TO PLAY:\n" +
            "- Left Click: Reveal a cell. If it's a mine, you lose!\n" +
            "- Right Click: Flag a cell you suspect contains a mine.\n" +
            "- Numbers: Indicate how many mines are next to that cell.\n" +
            "- Goal: Reveal all cells without hitting a mine."
        );
        
        add(instruct, BorderLayout.CENTER);
        instruct.setMargin(new Insets(20, 20, 20, 20));
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        //creates buttons
        JButton easyBtn = new JButton("Easy (8x8, 10 Mines)");
        JButton medBtn = new JButton("Medium (16x16, 40 Mines)");
        JButton hardBtn = new JButton("Hard (20x20, 99 Mines)");
        // changes layout based on the button the player clicks (rows, cols, mines)
        easyBtn.addActionListener(e -> startGame(8, 8, 10)); 
        medBtn.addActionListener(e -> startGame(16, 16, 40));
        hardBtn.addActionListener(e -> startGame(20, 20, 99));
        buttonPanel.add(easyBtn);
        buttonPanel.add(medBtn);
        buttonPanel.add(hardBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void startGame(int rows, int cols, int mines) {
        this.dispose(); // Close the start screen
        SwingUtilities.invokeLater(() -> new MinesweeperFrame(rows, cols, mines));
    }
    public static void main(String[] args) {
        new StartScreen();
    }
}
