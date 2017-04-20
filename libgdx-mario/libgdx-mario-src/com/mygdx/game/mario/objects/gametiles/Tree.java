package com.mygdx.game.mario.objects.gametiles;




import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.GameTile;
/**
 * background animated tree
 * @author maheshkurmi
 *
 */
public class Tree extends GameTile {
	
	private static TextureRegion[] c = {MarioResourceManager.instance.gameTiles.Tree_1, MarioResourceManager.instance.gameTiles.Tree_2};
	public static Animation swing = new Animation(1200).addFrame(c[0]).addFrame(c[1]);

	public Tree(int pixelX, int pixelY) {
		super(pixelX, pixelY,null,null);
		setIsCollidable(false);
		setAnimation(swing);
	}
	
}
