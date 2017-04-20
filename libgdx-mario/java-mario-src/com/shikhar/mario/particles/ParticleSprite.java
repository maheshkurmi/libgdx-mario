package com.shikhar.mario.particles;

import java.awt.Graphics;
import java.awt.Image;

import com.shikhar.mario.objects.base.Creature;


public class ParticleSprite extends Creature
{
   
    public float xOld, yOld, x, y, xa, ya;

    public int xPic, yPic;
    public int wPic = 8;
    public int hPic = 8;
    public int xPicO, yPicO;
    public boolean xFlipPic = false;
    public boolean yFlipPic = false;
    public Image[] sheet;
    public boolean visible = true;

    public void move()
    {
        x+=xa;
        y+=ya;
    }

    public void render(Graphics og, float alpha)
    {
        if (!visible) return;

        int xPixel = (int)(xOld+(x-xOld)*alpha)-xPicO;
        int yPixel = (int)(yOld+(y-yOld)*alpha)-yPicO;

        og.drawImage(sheet[xPic], xPixel+(xFlipPic?wPic:0), yPixel+(yFlipPic?hPic:0), xFlipPic?-wPic:wPic, yFlipPic?-hPic:hPic, null);
    }

    public final void tick()
    {
        xOld = x;
        yOld = y;
        move();
    }

    public final void tickNoMove()
    {
        xOld = x;
        yOld = y;
    }

    public float getX(float alpha)
    {
        return (xOld+(x-xOld)*alpha)-xPicO;
    }

    public float getY(float alpha)
    {
        return (yOld+(y-yOld)*alpha)-yPicO;
    }


}
