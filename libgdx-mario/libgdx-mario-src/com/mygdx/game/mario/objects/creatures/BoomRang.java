package com.mygdx.game.mario.objects.creatures;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.MarioSoundManager;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;

public class BoomRang extends Creature {

	private Animation dead;
	private Animation ball_left,ball_right;
	private static TextureRegion fl_1, fl_2, fl_3, fr_1,fr_2,fr_3, fb_1,fb_2;
	private static boolean initialized = false;
	public static int fireballsCount=0;

	/**
	 * Fired by Bowser Kills mario on collision 
	 * @param x
	 * @param y
	 * @param direction
	 */
	public BoomRang(int x, int y, float direction) {

		super(x, y);
		fireballsCount++;
		if (!initialized) {
			fl_1 = MarioResourceManager.instance.creatures.BoomRang_left[0];
			fl_2 = MarioResourceManager.instance.creatures.BoomRang_left[1];
			fl_3 = MarioResourceManager.instance.creatures.BoomRang_left[2];

			fr_1 = MarioResourceManager.instance.creatures.BoomRang_right[0];
			fr_2 = MarioResourceManager.instance.creatures.BoomRang_right[1];
			fr_3 = MarioResourceManager.instance.creatures.BoomRang_right[2];
			fb_1 = MarioResourceManager.instance.creatures.fb_1;
			fb_2 = MarioResourceManager.instance.creatures.fb_2;
			
			initialized = true;
		}
		ball_left = new Animation(100).addFrame(fl_1).addFrame(fl_2).addFrame(fl_3);//.addFrame(fb_4);
		ball_right = new Animation(100).addFrame(fr_1).addFrame(fr_2).addFrame(fr_3);//.addFrame(fb_4);
		
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
				fireballsCount--;
			}
		}
		dead = new DeadAfterAnimation().setDAL(50).addFrame(fb_1).setDAL(100).addFrame(fb_2);
		//dead = new DeadAfterAnimation();
		// dead.addFrame(fb_5, 10);
		//dead.addFrame(fb_6, 10);
		dx=direction*.09f;
		dy=0.03f;
		setAnimation((dx<0)?ball_left:ball_right);
		setGravityFactor(0.1f);
		setIsCollidable(false);
	}

	@Override
	public void xCollide(Vector2 p) {
		dx=0;
		dy=0;
		//super.xCollide(p);
		setAnimation(dead);
		MarioSoundManager.instance.playKick();
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
