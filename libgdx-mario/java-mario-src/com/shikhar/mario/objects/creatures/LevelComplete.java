package com.shikhar.mario.objects.creatures;

import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageUtilities;





public class LevelComplete extends Creature {
	
	private Animation level;

	
	public LevelComplete(int pixelX, int pixelY) {
		super(pixelX, pixelY);
		setIsItem(true);
		setIsAlwaysRelevant(true);
		BufferedImage shroom = ImageUtilities.loadImage("baddies/level.png");
		level = new Animation();
		level.addFrame(shroom, 1000);
		level.addFrame(shroom, 1000);
		setAnimation(level);
	}
	
	@Override
	public void updateCreature(TileMap map, int time) {
	
	}
}

