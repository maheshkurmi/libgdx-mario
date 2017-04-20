package com.shikhar.mario.objects.creatures;



import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.particles.BubbleParticle;
import com.shikhar.mario.particles.SmokeParticle;
import com.shikhar.mario.util.ImageUtilities;
import com.shikhar.mario.util.SpriteMap;


public class JumpingFish extends Creature {
	
	private Animation leftSwim,rightSwim, dead, flip;
    private int initY;
    private int wait=0;
    private boolean isBlue;
	public JumpingFish(int x, int y, boolean isblue, MarioSoundManager22050Hz soundManager) {
		super(x, y,soundManager);
		this.isBlue=isblue;
	    initY=y+80;
		//this.y=initY;
		//setIsItem(true);
		BufferedImage[] v = new SpriteMap(isBlue?"baddies/blue_Fish.png":"baddies/Red_Fish.png", 5, 1).getSprites();
		leftSwim = new Animation(200).addFrame(v[0]).addFrame(v[1]);
		rightSwim = new Animation(200).addFrame(v[2]).addFrame(v[3]);
		flip = new Animation().addFrame(v[4]).addFrame(v[4]);
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}
		dead = new DeadAfterAnimation().setDAL(100).addFrame(v[4]).setDAL(20).addFrame(v[4]);
		setAnimation(leftSwim);
		dx=(float) (-0.4f -Math.random()*0.3f);
	}
	
	@Override
	public void xCollide(Point p) {
		
	}
	
	@Override
	public void creatureXCollide() {
		
	}
	
	@Override
	public void wakeUp(boolean isLeft) {
		super.wakeUp();
		setRandomSpeed();
		if(isLeft) {
			setAnimation(leftSwim);
		} else {
			setAnimation(rightSwim);
		}
		dx=(isLeft)?-Math.abs(dx):Math.abs(dx);
	}
	
	@Override
	public void updateCreature(TileMap map, int time) {
		if (wait>0)wait--;
		
		if (wait==0) {
			x=x+dx*time;
			dy=dy+GRAVITY*time;
			y=y+dy*time;
			if(y>initY){ y=initY;setRandomSpeed();}
		}else{
			x=x+0.6f*dx*time;
		}
		
		//if (x<-16 || x>map.getWidth()*16)kill();
		super.update(time);
		if (inWater){
			if (Math.random()>0.97){
	       	   map.creaturesToAdd().add(new BubbleParticle((int)getX(), (int)getY()));
			}
		}
	}
	
	private void setRandomSpeed(){
		dx=(float) (-Math.signum(dx)*(0.07+Math.random()*0.03f));
		dy=(float) (-0.45f-Math.random()*0.20f);
		wait=0;
		if(dx<0) {
			setAnimation(leftSwim);
		} else {
			setAnimation(rightSwim);
		}

	}
	@Override
	public void jumpedOn() {
		setAnimation(dead);
		setIsCollidable(false);
		dx = 0;
		dy = 0;
	}
	
	@Override
	public void flip() {
		setAnimation(flip);
		setIsFlipped(true);
		setIsCollidable(false);
		dy = -.2f;
		dx = 0;
	}
	
	

}
