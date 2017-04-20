package com.shikhar.mario.objects.creatures;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageUtilities;
import com.shikhar.mario.util.SpriteMap;



public class BoomRang extends Creature {


	private Animation dead;
	private Animation ball;
	private static BufferedImage fb_1, fb_2, fb_3, fb_4,fb_5,fb_6;
	private static boolean initialized = false;
	public static int fireballsCount=0;

	public BoomRang(int x, int y, float direction, MarioSoundManager22050Hz soundManager) {

		super(x, y, soundManager);
		fireballsCount++;
		if (!initialized) {
			BufferedImage[] images = new SpriteMap("baddies/BoomRang.png", 4, 1)
			.getSprites();
			fb_1 = images[0];
			fb_2 = images[1];
			fb_3 = images[2];
			fb_4 = images[3];
			fb_5 = ImageUtilities.loadImage("baddies/fireball_5.png");
			fb_6 = ImageUtilities.loadImage("baddies/fireball_6.png");
			
			initialized = true;
		}
		ball = new Animation(80).addFrame(fb_1).addFrame(fb_2).addFrame(fb_3).addFrame(fb_4);
		
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
				fireballsCount--;
			}
		}
		dead = new DeadAfterAnimation().setDAL(50).addFrame(fb_5).setDAL(100).addFrame(fb_6);
		//dead = new DeadAfterAnimation();
		// dead.addFrame(fb_5, 10);
		//dead.addFrame(fb_6, 10);
		setAnimation(ball);
		dx=direction*.09f;
		dy=-0.03f;
		setGravityFactor(0.1f);
		setIsCollidable(false);
	}

	@Override
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
	
	public void creatureXCollide() {
		
	}
	
	public void jumpedOn() {
		kill();
		setIsCollidable(false);
		dx = 0;
	}

}
