package com.shikhar.mario.objects.tiles;



import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.util.ImageUtilities;



public class Bush extends GameTile {
	
	private static BufferedImage[] c = {ImageUtilities.loadImage("items/Bush_1.png"),ImageUtilities.loadImage("items/Bush_2.png")};
	public static Animation swing = new Animation(600).addFrame(c[0]).addFrame(c[1]);

	public Bush(int pixelX, int pixelY) {
		super(pixelX, pixelY,null,null);
		setIsCollidable(false);
		setAnimation(swing);
	}
	
	
	
	
}
