package com.mygdx.game.mario.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CollidableObject extends Animatible {
	
	private boolean isCollidable;
	private boolean isOnScreen;
	protected float x;
    protected float y;
    protected float dx;
    protected float dy;
	

	
	public CollidableObject(int pixelX, int pixelY) {
		//super(pixelX, pixelY);
		this.x = pixelX;
		this.y = pixelY;
		dx = 0;
		dy = 0;
		this.isCollidable = true;
		setIsOnScreen(false);
	}
	

	public boolean isCollidable() {
		return isCollidable;
	}
	
	public void setIsCollidable(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}
	
	public boolean isOnScreen() {
		return isOnScreen;
	}
	
	public void setIsOnScreen(boolean isOnScreen) {
		this.isOnScreen = isOnScreen;
	}
	
	public void draw(SpriteBatch batch, int x, int y) {
		batch.draw(currentAnimation().getImage(), x, y);
	}
	
	public void draw(SpriteBatch batch, int x, int y, int offsetX, int offsetY) {
		batch.draw(currentAnimation().getImage(), x + offsetX, y + offsetY);
		
	}
	public TextureRegion getImage() {
		return (currentAnimation() == null) ? null : currentAnimation().getImage();
	}
	
    public float getX() {
        return x;
    }
    
    public void setX(float x) {
    	this.x = x;
    }
    
    public float getY() {
        return y;
    }
    
    public void setY(float y) {
    	this.y = y;
    }
    
    public float getdX() {
    	return dx;
    }
    
    public void setdX(float dx) {
    	this.dx = dx;	
    }
    
    public void setdY(float dy) {
    	this.dy = dy;
    }
    
    public float getdY() {
    	return dy;
    }
    
    public int getHeight() {
    	return currentAnimation().getHeight();
    }
    
    public int getWidth() {
    	return currentAnimation().getWidth();
    }
    
     
	// Checks simple collision between sprites.
	// Checks if two Sprites collide with one another. Returns false if the two Sprites 
	// are the same. Returns false if one of the Sprites is a Creature that is not alive.
	public static boolean isCollision(CollidableObject s1, CollidableObject s2) {
	    // if the Sprites are the same, return false
	    if (s1 == s2) {
	        return false;     
	    }
	
	    // get the pixel location of the Sprites
	    int s1x = Math.round(s1.getX());
	    int s1y = Math.round(s1.getY());
	    int s2x = Math.round(s2.getX());
	    int s2y = Math.round(s2.getY());
	
	    // check if the two sprites' boundaries intersect
	    return (s1x < s2x + s2.getWidth() && s2x < s1x + s1.getWidth() && 
	    		s1y < s2y + s2.getHeight() && s2y < s1y + s1.getHeight());
 
	}
}
