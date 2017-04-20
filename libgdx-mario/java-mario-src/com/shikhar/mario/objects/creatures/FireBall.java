package com.shikhar.mario.objects.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageUtilities;
import com.shikhar.mario.util.SpriteMap;


public class FireBall extends Creature {


	private Animation dead;
	private Animation ball;

	private static BufferedImage fb_1, fb_2, fb_3, fb_4, fb_5,fb_6;
	private static boolean initialized = false;
  	/**
	 * controls max number of fireballs at an instant in map (maximum=6)
	 */
	public static int fireballsCount=0;
	/**
	 * controls min. time interval of firing between 2 successive fireballs
	 */
	public static int aliveTime=800;

	public FireBall(int x, int y, float dir, MarioSoundManager22050Hz soundManager) {

		super(x, y, soundManager);
		fireballsCount++;
		aliveTime=0;
		if (!initialized) {
			fb_1 =  ImageUtilities.loadImage("baddies/fireball_1.png");
			fb_2 =  ImageUtilities.loadImage("baddies/fireball_2.png");
			fb_3 =  ImageUtilities.loadImage("baddies/fireball_3.png");
			fb_4 =  ImageUtilities.loadImage("baddies/fireball_4.png");
			fb_5 =  ImageUtilities.loadImage("baddies/fireball_5.png");
			fb_6 =  ImageUtilities.loadImage("baddies/fireball_6.png");
			initialized = true;
		}
		ball = new Animation(150).addFrame(fb_1).addFrame(fb_2).addFrame(fb_3).addFrame(fb_4);
		
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}
		dead = new DeadAfterAnimation().setDAL(50).addFrame(fb_5).setDAL(100).addFrame(fb_6);
		//dead = new DeadAfterAnimation();
		// dead.addFrame(fb_5, 10);
		//dead.addFrame(fb_6, 10);
		setAnimation(ball);
		dx=dir*.12f;
		//dy=0.3f;
	}

	public void xCollide(Point p) {
		dx=0;
		dy=0;
		//super.xCollide(p);
		setAnimation(dead);
		soundManager.playKick();
		//setIsCollidable(false);
		//dx = 0;
		//dy=0;
	}
	

	@Override
	public void updateCreature(TileMap map, int time) {
		super.updateCreature(map, time);
		if (aliveTime<800)aliveTime+=time;
	}

	/**
	 * used to check that prev fireball has spent sufficient time  
	 * @return true when fireball throw is allowed
	 */
    public static boolean isReady(){
    	return (fireballsCount==0 || (aliveTime>=800 && fireballsCount<=10));
    }
	   @Override
	    public void kill(){
	    	super.kill();
	    	aliveTime=800;
	    	fireballsCount--;
	    }
}
