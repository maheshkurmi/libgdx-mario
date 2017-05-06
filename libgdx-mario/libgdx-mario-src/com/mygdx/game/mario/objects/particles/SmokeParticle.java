package com.mygdx.game.mario.objects.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.objects.Creature;

/**
 * effect for smashing goomba by jumping over it
 * @author maheshkurmi
 *
 */
public class SmokeParticle extends Creature
{
    private int life;
    private int xPic;
    private int xPicStart;
    private int delayTime;
    private Color color;
    
    public SmokeParticle(int x, int y, float dx, float dy,int delay){
    	this(x,y,dx,dy,delay,null);
    }
    
    public SmokeParticle(int x, int y, float dx, float dy,int delay,Color color)
    {
    	super(x, y);
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
        this.color=	color;

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
    
    public void draw(SpriteBatch g, int x, int y) {
    	delayTime--;
    	if (delayTime>0) return;
    	Color c=g.getColor();
    	if(color!=null)g.setColor(color);
    	g.draw(MarioResourceManager.instance.particles.Smoke_Particles[xPic], x, y);
    	g.setColor(c);
	}
    
 	public TextureRegion getImage() {
		return MarioResourceManager.instance.particles.Smoke_Particles[xPic];
	}
	
    public int getHeight() {
    	return 8;
    }
    
    public int getWidth() {
    	return 8;
    }
  
}
