package com.shikhar.mario.particles;

import java.awt.Image;

public class Sparkle extends ParticleSprite
{
    public int life;
    public int xPicStart;
    public static Image sheet;
    public ParticleSystem system;
   
    public Sparkle(int x, int y, float xa, float ya, ParticleSystem system,Image imgSheet)
    {
       this(x, y, xa, ya, (int)(Math.random()*2), 0, 5);
       if (sheet==null)sheet=imgSheet;//ImageManipulator.loadImage("items/particles");
    }

    public Sparkle(int x, int y, float xa, float ya, int xPic, int yPic, int timeSpan)
    {
        this.x = x;
        this.y = y;
        this.xa = xa;
        this.ya = ya;
        this.xPic = xPic;
        xPicStart = xPic;
        this.yPic = yPic;
        this.xPicO = 4;
        this.yPicO = 4;

        wPic = 8;
        hPic = 8;
        life = 10+(int)(Math.random()*timeSpan);
    }

    public void move()
    {
        if (life>10)
            xPic = 7;
        else
            xPic = xPicStart+(10-life)*4/10;

        if (life--<0) visible=false;//system.removeSprite(this);

        x+=xa;
        y+=ya;
    }
}
