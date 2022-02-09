import javax.swing.*;

public class GameFrame extends JFrame {

    private GamePanel gamePanel;
    private InfoPanel infoPanel;

    public GameFrame()
    {
        gamePanel = new GamePanel();
        infoPanel = new InfoPanel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gamePanel);
        this.add(infoPanel);
        this.setTitle("BouncyBall");
        this.setSize(671, 739);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
}
