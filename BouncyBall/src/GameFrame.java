import javax.swing.*;

public class GameFrame extends JFrame {

    private GamePanel panel;

    public GameFrame()
    {
        panel = new GamePanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.setTitle("BouncyBall");
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
