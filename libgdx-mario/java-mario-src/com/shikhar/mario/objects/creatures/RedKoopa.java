package com.shikhar.mario.objects.creatures;



import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.shikhar.mario.core.GameRenderer;
import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageUtilities;
import com.shikhar.mario.util.SpriteMap;




public class RedKoopa extends Creature {
	
	private Animation left;
	private Animation right;
	private Animation dead;
	private Animation flip;
	private Random r;
	private boolean isGreen=true;
	private  BufferedImage left_1,left_2,right_1,right_2,shell,flipped;
	private static boolean initialized=false;
	
	public RedKoopa(int x, int y, MarioSoundManager22050Hz soundManager,boolean isGreen) {
		
		super(x, y, soundManager);
		this.isGreen=isGreen;
		r = new Random();
	
			if (!isGreen) {
				left_1 = ImageUtilities.loadImage("baddies/Koopa_Red_Left_1.png");
				left_2 = ImageUtilities.loadImage("baddies/Koopa_Red_Left_2.png");
				right_1 = ImageUtilities.loadImage("baddies/Koopa_Red_Right_1.png");
				right_2 = ImageUtilities.loadImage("baddies/Koopa_Red_Right_2.png");
				shell = ImageUtilities.loadImage("baddies/Red_Shell_1.png");
				flipped = ImageUtilities.loadImage("baddies/Red_Shell_Flip.png");
			} else {
				BufferedImage[] images = new SpriteMap(
						"baddies/Green_Koopa.png", 4, 1).getSprites();
				left_1 = images[0];
				left_2 = images[1];
				right_1 = images[2];
				right_2 = images[3];
				images = new SpriteMap(
						"baddies/Green_Shell.png", 6, 1).getSprites();
				shell = images[4];
				flipped = images[5];
			}
		left = new Animation(150).addFrame(left_1).addFrame(left_2);
		right = new Animation(150).addFrame(right_1).addFrame(right_2);
		
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}
		
		dead = new DeadAfterAnimation();
		flip = new DeadAfterAnimation();
		dead.addFrame(shell, 10);
		dead.addFrame(shell, 10);
		flip.addFrame(flipped, 1200);
		flip.addFrame(flipped, 1200);
		setAnimation(left);
		this.y=y-getHeight()+16;
	}
	@Override
	public void xCollide(Point p) {
		super.xCollide(p);
		if(currentAnimation() == left) {
			setAnimation(right);
		} else {
			setAnimation(left);
		}
	}
	@Override
	public void creatureXCollide() {
		if(dx > 0) {
			x = x - 2;
			setAnimation(left);
		} else {
			setAnimation(right);
			x = x + 2;
		}
		dx = -dx;
	}
	@Override
	public void flip() {
		setAnimation(flip);
		setIsFlipped(true);
		setIsCollidable(false);
		dy = -.2f;
		dx = 0;
	}
	
	public void wakeUp(boolean isLeft) {
		super.wakeUp();
		if(isLeft) {
			dx = -.03f;
			setAnimation(left);
		} else {
			dx = .03f;
			setAnimation(right);
		}
	}
	
	public void jumpedOn() {
		setAnimation(dead);
		setIsCollidable(false);
		dx = 0;
	}
	
	@Override
	protected void useAI(TileMap map) {

		// don't let it fall
		int tileY = GameRenderer.pixelsToTiles(y + getHeight() - 1);
		if (dx > 0) {
			int tileX = GameRenderer.pixelsToTiles(x - 1);
			if (tileX + 1 >= map.getWidth()) {
				x = x - 2;
				setAnimation(left);
				dx = -dx;
				return;
			}
			if (map.getTile(tileX + 1, tileY + 1) == null
					&& (map.getTile(tileX + 1, tileY + 2) == null)) {
				x = x - 2;
				setAnimation(left);
				dx = -dx;
			}
		} else if (dx < 0) {
			int tileX = GameRenderer.pixelsToTiles(x + getWidth() - 1);
			if (tileX - 1 < 0) {
				x = x + 2;
				setAnimation(right);
				dx = -dx;
				return;
			}
			if (map.getTile(tileX - 1, tileY + 1) == null
					&& (map.getTile(tileX - 1, tileY + 2) == null)) {
				x = x + 2;
				setAnimation(right);
				dx = -dx;
			}
		}
	}
	public boolean isGreen() {
		return isGreen;
	}

}
