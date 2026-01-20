import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameHeader extends JPanel {

    private JLabel timerLabel;
    private JButton replay;

    public GameHeader(ActionListener replayAction) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        timerLabel = new JLabel("Time: 0");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(timerLabel);
        
        replay = new JButton("Replay?");
        replay.setFocusable(false);
        replay.addActionListener(replayAction);

        add(replay);
    }

    public void updateTime(int seconds) {
        timerLabel.setText("Time: " + seconds);
    }
}
