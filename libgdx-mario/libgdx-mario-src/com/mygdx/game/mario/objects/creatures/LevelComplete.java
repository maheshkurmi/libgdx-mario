package com.mygdx.game.mario.objects.creatures;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;



/**
 * Star that mario must collect to clear level
 * @author maheshkurmi
 *
 */
public class LevelComplete extends Creature {
	
	private Animation level;

	
	public LevelComplete(int pixelX, int pixelY) {
		super(pixelX, pixelY);
		setIsItem(true);
		setIsAlwaysRelevant(true);
		TextureRegion shroom = MarioResourceManager.instance.creatures.levelComplete;
		level = new Animation();
		level.addFrame(shroom, 1000);
		level.addFrame(shroom, 1000);
		setAnimation(level);
	}
	
	@Override
	public void updateCreature(TileMap map, int time) {
	
	}
}

