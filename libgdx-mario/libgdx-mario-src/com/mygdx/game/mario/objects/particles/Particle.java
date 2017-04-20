package com.mygdx.game.mario.objects.particles;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


class Particle {
	protected float x;
    protected float y;
    protected float dx;
    protected float dy;
	
	private TextureRegion TextureRegion;
    protected static float GRAVITY = .0007f; //0.0008f
    protected static float gravityEffect = .20f; 
	
	public Particle(int x, int y, float dx, float dy, TextureRegion bmp) {
		this.dx=dx;
		this.dy=dy;
		this.x=x;
		this.y=y;
		TextureRegion = bmp;
	}


	public void updatePhysics(int time ) {
		if(dy < gravityEffect) { // apply gravity...this must be done first
			dy = dy*0.95f + GRAVITY * time;
		}
	
		x =  x + dx *time;
		y =  y + dy * time;
	}

	public void doDraw(SpriteBatch batch,int offsetX,int offsetY) {
		if(TextureRegion!=null)batch.draw(TextureRegion, offsetX+x,offsetY+y);
	}

}