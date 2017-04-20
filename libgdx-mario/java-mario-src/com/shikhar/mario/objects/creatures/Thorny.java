package com.shikhar.mario.objects.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.SpriteMap;


public class Thorny extends Creature {

	private Animation left;
	private Animation right;
	private Animation dead;
	private Animation flip;
	private Random r;

	private static BufferedImage left_1, left_2, right_1, right_2, flipped;
	private static boolean initialized = false;

	public Thorny(int x, int y, MarioSoundManager22050Hz soundManager) {

		super(x, y, soundManager);
		r = new Random();
		if (!initialized) {
			BufferedImage[] images = new SpriteMap("baddies/thorny.png", 5, 1)
					.getSprites();
			left_1 = images[0];
			left_2 = images[1];
			right_1 = images[2];
			right_2 = images[3];
			flipped = images[4];
			initialized = true;
		}
		left = new Animation(150).addFrame(left_1).addFrame(left_2);
		right = new Animation(150).addFrame(right_1).addFrame(right_2);

		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}

		// dead = new DeadAfterAnimation();
		flip = new DeadAfterAnimation();
		// dead.addFrame(shell, 10);
		// dead.addFrame(shell, 10);
		flip.addFrame(flipped, 1200);
		flip.addFrame(flipped, 1200);
		setAnimation(left);
		//align base of creature to tile
		this.y=y-getHeight()+16;
	}

	public void xCollide(Point p) {
		super.xCollide(p);
		if (currentAnimation() == left) {
			setAnimation(right);
		} else {
			setAnimation(left);
		}
	}

	public void creatureXCollide() {
		if (dx > 0) {
			x = x - 2;
			setAnimation(left);
		} else {
			setAnimation(right);
			x = x + 2;
		}
		dx = -dx;
	}

	public void flip() {
		setAnimation(flip);
		setIsFlipped(true);
		setIsCollidable(false);
		dy = -.2f;
		dx = 0;
	}

	public void wakeUp(boolean isLeft) {
		super.wakeUp();
		if (isLeft) {
			dx = -.03f;
			setAnimation(left);
		} else {
			dx = .03f;
			setAnimation(right);
		}
	}


}
