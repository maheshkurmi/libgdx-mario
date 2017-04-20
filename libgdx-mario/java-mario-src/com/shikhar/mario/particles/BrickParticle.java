package com.shikhar.mario.particles;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.SpriteMap;

public class BrickParticle extends Creature
{
    private int life;
    private static BufferedImage[] sheet;
    private int xPic;
    private static boolean initialized = false;
    private boolean doUpdate=false;
    
    /**
     * 
     * @param x Origin-X of particles
     * @param y Origin-Y of particles
     * @param vx Velocity-X of particles
     * @param vy Velocity-Y of particles
     * @param timeSpan
     */
    public BrickParticle(int x, int y, float vx, float vy, int timeSpan)
    {
    	super(x, y);
    	if (!initialized) {
			sheet = new SpriteMap("items/Brick_Particle.png", 8, 1)
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
		this.gravityEffect=0.0f;//2f;
        // timespan=5;
        life = 10;//+(int)(Math.random()*timeSpan);
    }

    public void update(int time){
      	doUpdate=!doUpdate;
    	if (doUpdate==true)return;
  
    	if (life--<0) kill();
         x+=dx;
         y+=dy;
         dy*=0.95f;
         dy+=2;
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
  
    public void xCollide(Point p) {}
    
    @Override
	public void creatureXCollide() {}
	
    public void updateCreature(TileMap map, int time){update( time);}
}
