package com.shikhar.mario.particles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.SpriteMap;

public class SparkleParticle extends Creature
{
  
    private int life;
    private static BufferedImage[] sheet;
    private int xPic;
    private static boolean initialized = false;
    private int xPicStart;
    private boolean doUpdate=false;
    /**
     * 
     * @param x Origin-X of particles
     * @param y Origin-Y of particles
     * @param vx Velocity-X of particles
     * @param vy Velocity-Y of particles
     * @param timeSpan
     */
    public SparkleParticle(int x, int y, float vx, float vy, int timeSpan)
    {
    	super(x, y);
    	if (!initialized) {
    		sheet = new SpriteMap("items/Sparkle_Particle.png", 8, 1)
    						.getSprites();
    		initialized = true;
    	}
    
        this.x = x;
        this.y = y;
        this.dx = vx;
        this.dy = vy;
        this.xPic = (int)(Math.random()*2);
        setIsItem(true);
		setIsAlwaysRelevant(true);
		setIsCollidable(false);
		this.gravityEffect=0.02f;
        // timespan=5;
        life = 10+(int)(Math.random()*timeSpan);
    }

    public void update(int time){
    	doUpdate=!doUpdate;
    	if (doUpdate==true)return;
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
  		g.drawImage(sheet[xPic], x, y, null);
  	}
      
      public void draw(Graphics g, int x, int y, int offsetX, int offsetY) {
  		draw(g, x + offsetX, y + offsetY);
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
