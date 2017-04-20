package com.shikhar.mario.particles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.SpriteMap;

public class SmokeParticle extends Creature
{
    private int life;
    private int xPic;
    private int xPicStart;
    private int delayTime;
    private static BufferedImage[] sheet;
    private static boolean initialized = false;
    public SmokeParticle(int x, int y, float dx, float dy,int delay)
    {
    	super(x, y);
    	if (!initialized) {
			sheet = new SpriteMap("items/Smoke_Particle.png", 8, 1)
					.getSprites();
			initialized = true;
    	}
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.xPic = (int)(Math.random()*2);
        this.delayTime=delay;
        setIsItem(true);
		setIsAlwaysRelevant(true);
		setIsCollidable(false);
		this.gravityEffect=0.02f;
        int timespan=5;
        life = 10+(int)(Math.random()*timespan);
    }

    public void update(int time){
    	delayTime--;
    	if (delayTime>0) return;
    	if (life>10)
             xPic = 7;
         else
             xPic = xPicStart+(10-life)*4/10;

         if (life--<0) kill();//system.removeSprite(this);

         x+=dx;
         y+=dy;
    }
   
    public void updateCreature()
    {
       
    }
    
    public void draw(Graphics g, int x, int y) {
    	delayTime--;
    	if (delayTime>0) return;
    	g.drawImage(sheet[xPic], x, y, null);
	}
    
	public BufferedImage getImage() {
		return sheet[xPic];
	}
	
    public int getHeight() {
    	return 8;
    }
    
    public int getWidth() {
    	return 8;
    }
    
    
  
}
