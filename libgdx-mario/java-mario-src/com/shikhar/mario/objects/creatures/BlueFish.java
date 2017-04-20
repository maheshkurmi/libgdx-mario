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


public class BlueFish extends Creature {
	
	private Animation leftSwim,rightSwim, dead, flip;
	private int initY;
	
	public BlueFish(int x, int y, MarioSoundManager22050Hz soundManager) {
		
		super(x, y,soundManager);
		inWater=true;
		initY=y;
		BufferedImage[] v = new SpriteMap("baddies/Blue_Fish.png", 5, 1).getSprites();
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
	}
	
	@Override
	public void xCollide(Point p) {
		super.xCollide(p);
		if(currentAnimation() == leftSwim) {
			setAnimation(rightSwim);
		} else {
			setAnimation(leftSwim);
		}
	}
	
	@Override
	public void creatureXCollide() {
		if(dx > 0) {
			x = x - 2;
			setAnimation(leftSwim);
		} else {
			setAnimation(rightSwim);
			x = x + 2;
		}
		dx = -dx;
	}
	
	@Override
	public void wakeUp(boolean isLeft) {
		super.wakeUp();
		super.wakeUp();
		if(isLeft) {
			dx = -.03f;
			setAnimation(leftSwim);
		} else {
			dx = .03f;
			setAnimation(rightSwim);
		}
	}
	
	@Override
	public void updateCreature(TileMap map, int time) {
		if (isFlipped() || !inWater){
			gravityFactor=1;
			super.updateCreature(map, time);
			
		}else{
			gravityFactor=0.00f;
			super.updateCreature(map, time);
			//x=x+dx;
			if (x<0 || x>map.getWidth()*16)xCollide(null);
			//super.update(time);
			y=y+dy;
			if (inWater){
				if (Math.random()>0.98){
	        	   map.creaturesToAdd().add(new BubbleParticle((int)getX(), (int)getY()));
				}
				y=(float) (initY+6*Math.sin(x/7));
			}
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
