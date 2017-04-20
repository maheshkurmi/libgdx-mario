package com.shikhar.mario.objects.creatures;



import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.particles.SmokeParticle;
import com.shikhar.mario.util.ImageUtilities;
import com.shikhar.mario.util.SpriteMap;


public class Virus extends Creature {
	
	private Animation waddle, flip;
	private int initY=0, span;
	public Virus(int x, int y,MarioSoundManager22050Hz soundManager) {
		
		super(x, y, soundManager);
		initY=y;
		BufferedImage[] v = new SpriteMap("baddies/Virus.png", 3, 1).getSprites();
		waddle = new Animation(200).addFrame(v[0]).addFrame(v[1]);
		flip = new Animation().addFrame(v[2]).addFrame(v[2]);
		setAnimation(waddle);
		dx=0;
	}
	
	@Override
	public void xCollide(Point p) {
		
	}
	
	@Override
	public void creatureXCollide() {
		
	}
	
	public void wakeUp(boolean isLeft) {
		super.wakeUp();
		dy =0.05f;//(float) (-0.5f+ Math.random()*0.9);
		span=20+(int) (Math.random()*20);
	}
	
	@Override
	public void updateCreature(TileMap map, int time) {
		if (isFlipped()){
			super.updateCreature(map, time);
		}else{
			y=y+dy*time;
			if (y>initY+span || y<initY-span)dy=-dy;
			super.update(time);
		}
	}
	
	public void jumpedOn() {
		//setAnimation(dead);
		//setIsCollidable(false);
		//dx = 0;
	}
	
	public void flip() {
		setAnimation(flip);
		setIsFlipped(true);
		setIsCollidable(false);
		dy = -.2f;
		dx = 0;
	}
	
	

}
