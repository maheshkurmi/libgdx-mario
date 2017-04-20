package com.shikhar.mario.objects.creatures;


import java.awt.Point;
import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageUtilities;
import com.shikhar.mario.util.SpriteMap;






public class RedShell extends Creature {
	
	private Animation still;
	private Animation rotate;
	private Animation flip;
	
	private TileMap map;
	private boolean isMoving;
	private boolean isGreen=false;
	private  BufferedImage stay,rotate_1,rotate_2,rotate_3,rotate_4, flipped;
	private float dormantTime=0;
	public int creatureHitcount=0;
	public RedShell(int x, int y, TileMap map, MarioSoundManager22050Hz soundManager, boolean isStill, boolean isGreen) {
		
		super(x, y, soundManager);
		this.map = map;
		setIsAlwaysRelevant(true);
		this.isGreen=isGreen;
			if (!isGreen){
				stay = ImageUtilities.loadImage("baddies/Red_Shell_1.png");
				rotate_1 = ImageUtilities.loadImage("baddies/Red_Shell_2.png");
				rotate_2 = ImageUtilities.loadImage("baddies/Red_Shell_3.png");
				rotate_3 = ImageUtilities.loadImage("baddies/Red_Shell_4.png");
				flipped = ImageUtilities.loadImage("baddies/Red_Shell_Flip.png");
			}else{
				 BufferedImage[] images = new SpriteMap(
						"baddies/Green_Shell.png", 6, 1).getSprites();
				 stay =images[4];
				 rotate_1 = images[1];
				 rotate_2 = images[3];
				 rotate_3 = images[2];
			     rotate_4 = images[0];
				 flipped = images[5];
			}
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}
		
		still = new Animation();
		rotate = new Animation();
		flip = new DeadAfterAnimation();
		
		still.addFrame(stay, 150);
		rotate.addFrame(rotate_1, 30);
		rotate.addFrame(stay, 30);
		rotate.addFrame(rotate_2, 30);
		rotate.addFrame(rotate_3, 30);
		rotate.addFrame(rotate_1, 30);
		flip.addFrame(flipped, 1200);
		flip.addFrame(flipped, 1200);
		
		wakeUp();
		isMoving = false;
		setAnimation(still);
		dx = 0;
	}
	
	public boolean isMoving() {
		return isMoving;
	}
	
	public void xCollide(Point p) {
		super.xCollide(p);
		GameTile tile = map.getTile(p.x, p.y);
		if(this.isOnScreen()) {
			soundManager.playBump();
			if(tile != null) {
				tile.doAction();
			}
		}
	}
	
	public void flip() {
		setAnimation(flip);
		setIsFlipped(true);
		setIsCollidable(false);
		dy = -.2f;
		dx = 0;
	}
	
	// if you run or jump on the shell faster, the shell moves faster.
	public void jumpedOn(boolean fromRight, float attackerSpeed) {
		if(isMoving) {
			isMoving = false;
			setAnimation(still);
			dx = 0;
		} else {
			isMoving = true;
			setAnimation(rotate);
			if(fromRight) {
				if(attackerSpeed > .2f) {
					dx = .24f;
				} else if(attackerSpeed > .16) { 
					dx = .23f;
				} else {
					dx = .16f;
				}
			} else {
				if(attackerSpeed < -.2f) {
					dx = -.24f;
				} else if(attackerSpeed < -.16) {
					dx = -.23f;
				} else {
					dx = -.16f;
				}
			}
		}
	}
	
	@Override
	protected void useAI(TileMap map){
		if (currentAnimation()==still) {
			dormantTime++;
			if (dormantTime>=500){
				dormantTime=0;
				kill();
				map.creaturesToAdd().add(new RedKoopa(Math.round(getX()), 
						Math.round(getY()-13), soundManager, isGreen()));
				soundManager.playBump();
					return;
			}
		}else{
			dormantTime=0;
		}
	}
	
	public boolean isGreen() {
		return isGreen;
	}
}
