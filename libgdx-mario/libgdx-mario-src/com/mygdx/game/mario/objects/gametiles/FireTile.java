package com.mygdx.game.mario.objects.gametiles;


import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.GameTile;
import com.mygdx.game.mario.objects.particles.BubbleParticle;

/**
 * Kills all living objects in contact even mario
 * @author maheshkurmi
 *
 */
public class FireTile extends GameTile {

	private Animation active;
	private TileMap map;
	public FireTile(int pixelX, int pixelY,TileMap map) {
		super(pixelX, pixelY, null, null);
		setIsSloped(false);
		active = new Animation(400).addFrame(MarioResourceManager.instance.gameTiles.Fire_Tile[0]).addFrame(MarioResourceManager.instance.gameTiles.Fire_Tile[1]);
		setAnimation(active);
		this.map=map;
	}
	
	public void update(int time) {
		super.update(time);
		if (Math.random()>0.998){
			map.creaturesToAdd().add(new BubbleParticle((int)getPixelX(), (int)getPixelY(),Color.RED,Color.ORANGE));
		}

	}
	
}