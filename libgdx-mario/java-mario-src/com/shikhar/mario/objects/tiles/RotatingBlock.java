package com.shikhar.mario.objects.tiles;


import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.util.ImageUtilities;




public class RotatingBlock extends GameTile {
	
	private Animation rotate;
	private Animation idle;
	
	public RotatingBlock(int pixelX, int pixelY) {
		
		// int pixelX, int pixelY, Animation anim, Image img, boolean isUpdateable
		super(pixelX, pixelY, null, null);
		setIsSloped(false);
		
		BufferedImage rotate_1 = ImageUtilities.loadImage("items/Rotating_Block_Hit_1.png");
		BufferedImage rotate_2 = ImageUtilities.loadImage("items/Rotating_Block_Hit_2.png");
		BufferedImage rotate_3 = ImageUtilities.loadImage("items/Rotating_Block_Hit_3.png");
		BufferedImage still = ImageUtilities.loadImage("items/Rotating_Block_Still.png");
		
     	final class RotateAnimation extends Animation {
     		public void endOfAnimationAction() {
     			setAnimation(idle);
     			setIsCollidable(true);
     		}
		}
		
		idle = new Animation(10000).addFrame(still);
		rotate = new RotateAnimation();
		
		int rotateTime = 90;
		for(int i = 1; i <= 3; i++) {
			for(int j = 1; j <= 3; j++) {
				rotate.addFrame(rotate_1, rotateTime);
				rotate.addFrame(rotate_2, rotateTime);
				rotate.addFrame(rotate_3, rotateTime);
			}
			rotateTime += 90;
		}
		setAnimation(idle);
	}
	
	public void doAction() {
		setAnimation(rotate);
		setIsCollidable(false);
	}
}
