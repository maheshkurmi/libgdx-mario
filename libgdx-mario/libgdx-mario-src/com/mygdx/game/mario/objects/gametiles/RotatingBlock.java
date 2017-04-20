package com.mygdx.game.mario.objects.gametiles;



import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.GameTile;

/**
 * Tiles which become non collidable on collision with mario
 * @author maheshkurmi
 *
 */
public class RotatingBlock extends GameTile {
	
	private Animation rotate;
	private Animation idle;
	
	public RotatingBlock(int pixelX, int pixelY) {
		
		// int pixelX, int pixelY, Animation anim, Image img, boolean isUpdateable
		super(pixelX, pixelY, null, null);
		setIsSloped(false);
		
		TextureRegion rotate_1 = MarioResourceManager.instance.gameTiles.Rotating_Block_Hit_1;
		TextureRegion rotate_2 = MarioResourceManager.instance.gameTiles.Rotating_Block_Hit_2;
		TextureRegion rotate_3 = MarioResourceManager.instance.gameTiles.Rotating_Block_Hit_3;
		TextureRegion still = MarioResourceManager.instance.gameTiles.Rotating_Block_Still;
		
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
