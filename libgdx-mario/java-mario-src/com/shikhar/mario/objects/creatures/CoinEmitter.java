package com.shikhar.mario.objects.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;

public class CoinEmitter extends Creature {


	private Animation steady;;
	private float t;
	public boolean readytoFire=false;
	private int initY;
    private Random random;
	public CoinEmitter(int x, int y, MarioSoundManager soundManager) {

		super(x, y);
		
		setAnimation(steady);
		setIsItem(true);
		setGravityFactor(0);
		//setIsCollidable(false);
		setIsAlwaysRelevant(true);
		setIsItem(true);
		initY=-2*getHeight();
		//this.y=initY-getHeight();
		//this.x=x;
		random=new Random();
		//setOffsetY(-getHeight());
		this.y=map.getHeight()*16+3*getHeight();
	}

	@Override
	public void xCollide(Point p) {

	}
	
	public void creatureXCollide() {
		
	}
	
	public void wakeUp(boolean isleft) {
		super.wakeUp();
		//x=map.getWidth()8*16;
		y=map.getHeight()*16+3*getHeight();
	}

	@Override
	public void update(int time) {
		super.update(time);
		t++;
		if (t>200){
			readytoFire=true;
			t=0;
		}
	}
	
	@Override
	public void updateCreature(TileMap map, int time) {
		//update(time);
		if(random.nextInt(700)==300){
				fireRandomCoins(map);
				readytoFire=false;
		}
	}
	
	public void jumpedOn() {

	}

	private void fireRandomCoins(TileMap map){
		float theta;
		int n=12+random.nextInt(5);
		Coin c;
		int x=(int) (map.getWidth()/2-8+Math.random()*(16))*16;
		for (int i = 0; i <= n; i++) {
			float v=(float) (0.32f+0.025f*Math.random());
			theta=(float) ((0.30+0.4*i/n)*Math.PI);
			//(float) (0.30f*v*Math.cos(theta));
			//(float) (-v*Math.sin(theta));
			c=new Coin(x,(int)getY());
			c.setIsAlwaysRelevant(true);
			map.creaturesToAdd().add(c);
		}
		
	}

}
