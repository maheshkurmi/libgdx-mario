package com.mygdx.game.mario.objects.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.objects.Creature;

/**
 * Effect for coin collection by mario
 * @author maheshkurmi
 *
 */
public class SparkleParticle extends Creature
{
    private int life;
    private int xPic;
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
    
    public void draw(SpriteBatch g, int x, int y) {
    	g.draw(MarioResourceManager.instance.particles.Sparkle_Particles[xPic], x, y);
	}
    
 	public TextureRegion getImage() {
		return MarioResourceManager.instance.particles.Sparkle_Particles[xPic];
	}
	
    public int getHeight() {
    	return 8;
    }
    
    public int getWidth() {
    	return 8;
    }
  
}
