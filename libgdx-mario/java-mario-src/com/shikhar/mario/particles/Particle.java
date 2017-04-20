package com.shikhar.mario.particles;


import java.awt.Graphics;
import java.awt.Image;

class Particle {
	protected float x;
    protected float y;
    protected float dx;
    protected float dy;
	
	private Image bitmap;
    protected static float GRAVITY = .2f; //0.0008f
    protected static float gravityEffect = 3.90f; 
	
	public Particle(int x, int y, float dx, float dy, Image bmp) {
		this.dx=dx;
		this.dy=dy;
		this.x=x;
		this.y=y;
		//this.direction = Math.PI * new Random().nextFloat();//.nextInt(NO_OF_DIRECTION)/ NO_OF_DIRECTION;
		//this.speedX =(float) (speed*Math.cos(direction));
		//this.speedY = -(float) (speed*Math.sin(direction));
		//this.color = new Random().nextInt(3);
		bitmap = bmp;
	}

	public void updatePhysics(int time ) {
		//if(dy < gravityEffect) { // apply gravity...this must be done first
			dy = dy + GRAVITY * time;
		//}
	
		x =  x + dx *time;
		y =  y + dy * time;
	}

	public void doDraw(Graphics canvas,int offsetX,int offsetY) {
		canvas.drawImage(bitmap, offsetX+(int)x,offsetY+(int)y, null);
	}

}