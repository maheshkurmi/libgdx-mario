package com.mygdx.game.mario.objects.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.game.GameRenderer;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Creature;

/**
 * For breathing effect in water
 * @author maheshkurmi
 *
 */
public class BubbleParticle extends Creature
{
	private float r=1.5f;
	private Color borderColor= new Color( 150/255f,160/255f,240/255f,170/255f);
	private Color fillColor=new Color( 200/255f,200/255f,245/255f,60/255f);
	
    /**
     * 
     * @param x Origin-X of particles
     * @param y Origin-Y of particles
     * @param vx Velocity-X of particles
     * @param vy Velocity-Y of particles
     * @param timeSpan
     */
    public BubbleParticle(int x, int y)
    {
    	super(x, y);
        this.x = x;
        this.y = y;
        dy=-0.1f;
        setIsItem(true);
		setIsAlwaysRelevant(true);
		setIsCollidable(false);
		this.setGravityFactor(-1f);
		//paint=new Paint(fillColor);
		//paint.setAntiAlias(true);
    }

    public void update(int time){
    	if (y<0) kill();
    	 dx=-1f+(float) (2*Math.random());
       	 r+=time/1800.0f;
     	 if(dy > -3f) { // apply gravity...this must be done first
			dy = dy + getGravityFactor()*GRAVITY * time;
		}
     	x+=dx;
     	y+=dy;
    	// super.update(time);
 
    }
   
       
    public void draw(SpriteBatch g, int x, int y) {
      /*
    	paint.setStyle(Style.FILL);
    	paint.setColor(fillColor);
    	//g.drawCircle(x, y, r,paint);
    	//paint.setColor(borderColor);
    	paint.setStyle(Style.STROKE);
    	g.drawCircle(x, y, r,paint);
    	
    	*/
    	GameRenderer.drawCircle(x, y, r, fillColor, borderColor, g);
	}
    
    public void draw(SpriteBatch g, int x, int y, int offsetX, int offsetY) {
		draw(g, x + offsetX, y + offsetY);
	}

	public TextureRegion getImage() {
		return null;
	}
	
    public int getHeight() {
    	return 8;
    }
    
    public int getWidth() {
    	return 8;
    }
  
    public void xCollide(Vector2 p) {}
    
    @Override
	public void creatureXCollide() {}
	
    public void updateCreature(TileMap map, int time){
    	update( time);
    }
}
