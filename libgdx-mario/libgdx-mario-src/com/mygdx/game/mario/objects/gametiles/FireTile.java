package com.mygdx.game.mario.objects.gametiles;


import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.GameTile;

/**
 * Kills all living objects in contact even mario
 * @author maheshkurmi
 *
 */
public class FireTile extends GameTile {

	private Animation active;
	
	public FireTile(int pixelX, int pixelY) {
		super(pixelX, pixelY, null, null);
		setIsSloped(false);
		active = new Animation(400).addFrame(MarioResourceManager.instance.gameTiles.Fire_Tile[0]).addFrame(MarioResourceManager.instance.gameTiles.Fire_Tile[1]);
		setAnimation(active);
	}
	
	public void update(int time) {
		super.update(time);
	}
	
}