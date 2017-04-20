package com.mygdx.game.mario.objects.creatures;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;

/**
 * can be killed by jumping over it
 * @author maheshkurmi
 *
 */
public class Goomba extends Creature {
	
	private Animation waddle, dead, flip;
	private static TextureRegion w1,w2,smashed,flipped;
	private static boolean initialised=false;
	public Goomba(int x, int y) {
		
		super(x, y);
		if (!initialised){
			 w1 = MarioResourceManager.instance.creatures.Goomba_Normal_1;
			 w2 = MarioResourceManager.instance.creatures.Goomba_Normal_2;
			 smashed = MarioResourceManager.instance.creatures.Goomba_Dead;
			 flipped = MarioResourceManager.instance.creatures.Goomba_Flip;
			 initialised=true;
		}

		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}

		waddle = new Animation(150).addFrame(w1).addFrame(w2);
		dead = new DeadAfterAnimation().setDAL(100).addFrame(smashed).setDAL(20).addFrame(smashed);
		flip = new Animation().addFrame(flipped).addFrame(flipped);
		setAnimation(waddle);
	}
	
	public void wakeUp(boolean isLeft) {
		super.wakeUp();
		dx=isLeft?-0.03f:0.03f;
		//dx = (r.nextInt(25) == 0) ? .03f : -.03f;
	}
	
	public void jumpedOn() {
		setAnimation(dead);
		setIsCollidable(false);
		dx = 0;
	}
	
	public void flip() {
		setAnimation(flip);
		setIsFlipped(true);
		setIsCollidable(false);
		dy = -.2f;
		dx = 0;
	}
}
