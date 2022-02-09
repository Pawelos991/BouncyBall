import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InfoPanel extends JPanel {

    private final int PanelWidth = 250;
    private final int PanelHeight = 700;
    private final int PanelX = 405;
    private final int PanelY = 0;
    private Timer timer;
    private List<Wall> borders;

    public InfoPanel()
    {
        borders = new ArrayList<Wall>();
        borders.add(new Wall(PanelX,0,PanelWidth,3));
        borders.add(new Wall(PanelX,PanelHeight-3,PanelWidth,3));
        borders.add(new Wall(PanelX + PanelWidth-3,0,3,PanelHeight));

        this.setPreferredSize(new Dimension(PanelWidth,PanelHeight));
        this.setBounds(PanelX,PanelY,PanelWidth,PanelHeight);
        this.setBackground(Color.GRAY);
        //timer = new Timer(7,this);
        //timer.start();
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        paintBorders(g2D);
    }

    private void paintBorders(Graphics2D g2D)
    {
        g2D.setColor(Color.LIGHT_GRAY);
        for(Wall wall : borders)
        {
            g2D.fillRect(wall.getPosition().x,wall.getPosition().y,wall.getWidth(),wall.getHeight());
        }
    }

}
