package com.shikhar.mario.objects.tiles;

import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.util.SpriteMap;


public class FireTile extends GameTile {

	private Animation active;
	private static BufferedImage q[]=null;
	public FireTile(int pixelX, int pixelY) {
		super(pixelX, pixelY, null, null);
		if (q==null){
			q=new SpriteMap("items/FireTile.png",2,1).getSprites();
		}
		setIsSloped(false);
		active = new Animation(400).addFrame(q[0]).addFrame(q[1]);
		setAnimation(active);
	}
	
	public void update(int time) {
		super.update(time);
	}
	
}