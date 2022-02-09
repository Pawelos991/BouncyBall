import java.awt.*;

import static java.lang.Math.abs;

public class Ball {
    private float x;
    private float y;
    private float xSpeed;
    private float ySpeed;
    private int d; //diameter
    private Color color;
    private boolean isShoot;

    public Ball(int X, int Y, float XSpeed, float YSpeed, int D, Color coloR)
    {
        x=(float)X;
        y=(float)Y;
        xSpeed=XSpeed;
        ySpeed=YSpeed;
        d=D;
        color=coloR;
        isShoot=false;
    }

    public Point getPosition()
    {
        return new Point((int)x,(int)y);
    }

    public void setPosition(int X, int Y)
    {
        x=X;
        y=Y;
    }

    public void updatePosition()
    {
        if(isShoot)
        {
            x=x+xSpeed;
            y=y+ySpeed;
        }
    }

    public void changeXSpeed()
    {
        xSpeed=xSpeed*(-1);
    }

    public void changeYSpeed()
    {
        ySpeed=ySpeed*(-1);
    }

    public int getD()
    {
        return d;
    }

    public Color getColor()
    {
        return color;
    }

    public void setXSpeed(float newSpeed)
    {
        xSpeed=newSpeed;
    }

    public void setYSpeed(float newSpeed)
    {
        ySpeed=newSpeed;
    }

    public void setShoot()
    {
        isShoot=true;
    }

    public boolean isShoot()
    {
        return isShoot;
    }

    private boolean checkCollisionWithWallFromLeft(Wall wall)
    {
        if( //Collision from left
                (y+d>wall.getPosition().y && y<wall.getPosition().y+wall.getHeight())
                        && (x+d>=wall.getPosition().x && x+d <= wall.getPosition().x+abs(xSpeed))
        )
        {
            changeXSpeed();
            return true;
        }
        return false;
    }

    private boolean checkCollisionWithWallFromRight(Wall wall)
    {
        if( //Collision from right
                (y+d>wall.getPosition().y && y<wall.getPosition().y+wall.getHeight())
                        && (x<=wall.getPosition().x+wall.getWidth()
                                && x >= wall.getPosition().x+wall.getWidth()-abs(xSpeed))
        )
        {
            changeXSpeed();
            return true;
        }
        return false;
    }

    private boolean checkCollisionWithWallFromBottom(Wall wall)
    {
        if( //Collision from bottom
                (x+d>=wall.getPosition().x && x<= wall.getPosition().x+wall.getWidth())
                        && (y<=wall.getPosition().y+wall.getHeight()
                                && y>=wall.getPosition().y+wall.getHeight()-abs(ySpeed))
        )
        {
            changeYSpeed();
            return true;
        }
        return false;
    }

    private boolean checkCollisionWithWallFromTop(Wall wall)
    {
        if( //Collision from top
                (x+d>=wall.getPosition().x && x<= wall.getPosition().x+wall.getWidth())
                        && (y+d>=wall.getPosition().y && y+d<=wall.getPosition().y+abs(ySpeed))
        )
        {
            changeYSpeed();
            return true;
        }
        return false;
    }

    public boolean checkCollisionWithWall(Wall wall)
    {
        if(checkCollisionWithWallFromLeft(wall))
            return true;
        else if(checkCollisionWithWallFromRight(wall))
            return true;
        if(checkCollisionWithWallFromBottom(wall))
            return true;
        else if(checkCollisionWithWallFromTop(wall))
            return true;
        return false;
    }

}
