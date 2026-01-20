import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {
    public final int row;
    public final int col;

    public CellButton(int row, int col) {
        this.row = row;
        this.col = col;
        
        setPreferredSize(new Dimension(43, 43));
        setFont(new Font("Arial", Font.BOLD, 18));
        setMargin(new Insets(0, 0, 0, 0));
        setForeground(Color.BLACK);
    }
}
