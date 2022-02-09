import javax.swing.*;
import java.awt.*;

public class Block {
    private int x;
    private int y;
    private int width; //Always width>height
    private int height;
    private Color color;
    private Wall wall;
    private int hitsLeft;

    public Block(int X, int Y, int Width, int Height, Color coloR, int HitsLeft)
    {
        x=X;
        y=Y;
        width=Width;
        height=Height;
        color=coloR;
        wall = new Wall(x,y,width,height);
        hitsLeft=HitsLeft;
    }

    public Point getPosition()
    {
        return new Point(x,y);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Color getColor()
    {
        return color;
    }

    public Wall getWall()
    {
        return wall;
    }
    public int getHitsLeft()
    {
        return hitsLeft;
    }
    public void getHit()
    {
        hitsLeft--;
    }
}
