import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;


public class GamePanel extends JPanel implements ActionListener {

    private final int PanelX=0;
    private final int PanelY=0;
    private final int PanelWidth=405;
    private final int PanelHeight=700;
    Timer timer;
    private List<Ball> balls;
    private List<Block> blocks;
    private List<Wall> borders;
    private boolean newShoot;
    private boolean shootClicked;
    private Ball aim;
    private int aimX;
    private int aimY;
    private int ballD = 16;
    private float sumSpeed = 7;
    private int framesPerBall;
    private int actualFrame;


    public GamePanel()
    {
        balls = new ArrayList<Ball>();
        initializeBalls();

        blocks = new ArrayList<Block>();
        initializeBlocks();

        borders = new ArrayList<Wall>();
        initializeBorders();

        newShoot=true;
        shootClicked=false;
        Main.numberOfShoots=0;
        Main.points=0;

        aimX=0;
        aimY=0;
        aim = new Ball(aimX,aimY,0,0,8,Color.RED);

        ClickListener clickListener = new ClickListener();
        AimListener aimListener = new AimListener();

        this.addMouseListener(clickListener);
        this.addMouseMotionListener(aimListener);

        framesPerBall = (int)(ballD*sqrt(2)/sumSpeed) + 1;
        actualFrame=0;

        this.setPreferredSize(new Dimension(PanelWidth,PanelHeight));
        this.setBounds(PanelX,PanelY,PanelWidth,PanelHeight);
        this.setBackground(Color.BLACK);
        timer = new Timer(7,this);
        timer.start();
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        paintBorders(g2D);
        paintBlocks(g2D);
        paintBalls(g2D);
        paintAim(g2D);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(newShoot)
        {
            repaint();
        }
        else
        {
            updateBalls();
            repaint();
        }
    }

    private void initializeBalls()
    {
        for(int i=0;i<3;i++)
            balls.add(new Ball(190,650,5,-5,ballD,Color.WHITE));
        for(int i = 0; i< Main.points; i++)
            balls.add(new Ball(190,650,5,-5,ballD,Color.WHITE));
    }

    private void initializeBlocks()
    {
        Color color;
        for(int i=0;i<8;i++)
        {
            color=getColor(i);
            for(int j=0;j<10;j++)
            {
                blocks.add(new Block(5 + 50*i, 5 + 25*j, 45, 20, color,(i+1)*(j+1)));
            }
        }
    }

    private void initializeBorders()
    {
        borders.add(new Wall(0,PanelHeight-3,PanelWidth,3+(int)sumSpeed*2));
        borders.add(new Wall(0,-(int)sumSpeed*2,PanelWidth,3+(int)sumSpeed*2));
        borders.add(new Wall((int)-sumSpeed*2,0,3+(int)sumSpeed*2,PanelHeight));
        borders.add(new Wall(PanelWidth-3,0,3+(int)sumSpeed*2,PanelHeight));
    }

    private void updateBalls()
    {
        if(actualFrame==0)
        {
            actualFrame=framesPerBall;
            for(Ball ball : balls)
            {
                if(!ball.isShoot())
                {
                    ball.setShoot();
                    break;
                }
            }
        }
        actualFrame--;

        for(Ball ball : balls)
        {
            ball.updatePosition();
        }

        boolean ballRemoved=false;
        do{
            ballRemoved=false;
            Ball ballToRemove = null;
            for(Ball ball : balls)
            {
                if(ball.checkCollisionWithWall(borders.get(0)))
                    ballToRemove=ball;
            }
            if(ballToRemove!=null)
            {
                balls.remove(ballToRemove);
                ballRemoved=true;
            }
        }while(ballRemoved);

        if(balls.isEmpty())
        {
            newShoot=true;
            initializeBalls();
        }
        else
        {
            for(Ball ball : balls)
            {
                for(Wall wall : borders)
                {
                    ball.checkCollisionWithWall(wall);
                }

                Block blockToRemove = null;
                for(Block block : blocks)
                {
                    if(ball.checkCollisionWithWall(block.getWall()))
                    {
                        block.getHit();
                        if(block.getHitsLeft()==0)
                        {
                            blockToRemove=block;
                            Main.points++;
                            break;
                        }
                        break;
                    }
                }
                blocks.remove(blockToRemove);
            }
        }


    }

    private void paintBalls(Graphics2D g2D)
    {
        for(Ball ball : balls)
        {
            g2D.setColor(ball.getColor());
            g2D.fillOval(ball.getPosition().x,ball.getPosition().y,ball.getD(),ball.getD());
        }
    }

    private void paintBlocks(Graphics2D g2D)
    {
        for(Block block : blocks)
        {
            g2D.setColor(block.getColor());
            g2D.fillRect(
                    block.getPosition().x,
                    block.getPosition().y,
                    block.getWidth(),
                    block.getHeight()
            );
            g2D.setColor(Color.BLACK);
            if(block.getHitsLeft()>=1000)
                g2D.drawString(String.valueOf(block.getHitsLeft()),block.getPosition().x+8, block.getPosition().y+12);
            else if(block.getHitsLeft()>=100)
                g2D.drawString(String.valueOf(block.getHitsLeft()),block.getPosition().x+11, block.getPosition().y+12);
            else if(block.getHitsLeft()>=10)
                g2D.drawString(String.valueOf(block.getHitsLeft()),block.getPosition().x+17, block.getPosition().y+12);
            else if(block.getHitsLeft()>=1)
                g2D.drawString(String.valueOf(block.getHitsLeft()),block.getPosition().x+21, block.getPosition().y+12);
            g2D.setColor(Color.WHITE);
            if(block.getHitsLeft()>=1000)
                g2D.drawString(String.valueOf(block.getHitsLeft()),block.getPosition().x+7, block.getPosition().y+12);
            else if(block.getHitsLeft()>=100)
                g2D.drawString(String.valueOf(block.getHitsLeft()),block.getPosition().x+10, block.getPosition().y+12);
            else if(block.getHitsLeft()>=10)
                g2D.drawString(String.valueOf(block.getHitsLeft()),block.getPosition().x+16, block.getPosition().y+12);
            else if(block.getHitsLeft()>=1)
                g2D.drawString(String.valueOf(block.getHitsLeft()),block.getPosition().x+20, block.getPosition().y+12);
        }
    }

    private void paintBorders(Graphics2D g2D)
    {
        g2D.setColor(Color.LIGHT_GRAY);
        for(Wall wall : borders)
        {
            g2D.fillRect(wall.getPosition().x,wall.getPosition().y,wall.getWidth(),wall.getHeight());
        }
    }

    private void paintAim(Graphics2D g2D)
    {
        if(newShoot)
        {
            //Line
            g2D.setColor(Color.LIGHT_GRAY);
            g2D.setStroke(new BasicStroke(3));
            g2D.drawLine(
                    balls.get(0).getPosition().x+(balls.get(0).getD()/2),
                    balls.get(0).getPosition().y+(balls.get(0).getD()/2),
                    aim.getPosition().x+(aim.getD()/2),
                    aim.getPosition().y+(aim.getD()/2)
            );

            //Ball
            g2D.setColor(aim.getColor());
            g2D.fillOval(aim.getPosition().x,aim.getPosition().y,aim.getD(),aim.getD());
        }

    }

    private Color getColor(int x)
    {
        Color color;
        switch (x){
            case 1:
                color=Color.MAGENTA;
                break;
            case 2:
                color=Color.BLUE;
                break;
            case 3:
                color=Color.CYAN;
                break;
            case 4:
                color=Color.GREEN;
                break;
            case 5:
                color=Color.YELLOW;
                break;
            case 6:
                color=Color.ORANGE;
                break;
            case 7:
                color=Color.RED;
                break;
            default:
                color=Color.GRAY;
                break;
        }
        return color;
    }

    private void shoot()
    {
        newShoot=false;
        shootClicked=false;
        Main.numberOfShoots++;
        System.out.println(Main.numberOfShoots);
        actualFrame=0;

        int aimX = aim.getPosition().x+(aim.getD()/2);
        int aimY = aim.getPosition().y+(aim.getD()/2);

        for(Ball ball : balls)
        {
            int ballX = ball.getPosition().x+(ball.getD()/2);
            int ballY = ball.getPosition().y+(ball.getD()/2);
            int xDist = aimX-ballX;
            int yDist = aimY-ballY;
            int sum = abs(xDist)+abs(yDist);
            float xToSum = (float)xDist/(float)sum;
            float yToSum = (float)yDist/(float)sum;
            float xSpeed;
            float ySpeed;
            if(xToSum>yToSum)
            {
                xSpeed = sumSpeed*xToSum;
                if(yDist>0)
                    ySpeed = sumSpeed-abs(xSpeed);
                else
                    ySpeed = -(sumSpeed-abs(xSpeed));
            }
            else
            {
                ySpeed = (int)(sumSpeed*yToSum);
                if(xDist>0)
                    xSpeed = sumSpeed-abs(ySpeed);
                else
                    xSpeed = -(sumSpeed-abs(ySpeed));
            }

            ball.setXSpeed(xSpeed);
            ball.setYSpeed(ySpeed);
        }
    }

    private class ClickListener extends MouseAdapter{
        public void mouseClicked(MouseEvent e)
        {
            if(newShoot && !shootClicked)
            {
                shoot();
            }
        }
    }

    private class AimListener extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e)
        {
            Point newPos = e.getPoint();
            aim.setPosition(newPos.x-(aim.getD()/2), newPos.y-(aim.getD()/2));
        }
    }


}
