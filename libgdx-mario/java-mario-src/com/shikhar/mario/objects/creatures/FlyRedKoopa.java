package com.shikhar.mario.objects.creatures;




import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.shikhar.mario.core.GameRenderer;
import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageUtilities;
import com.shikhar.mario.util.SpriteMap;




public class FlyRedKoopa extends Creature {
	
	private Animation left;
	private Animation right;
	private Animation left_fly;
	private Animation right_fly;
	private Animation left_walk;
	private Animation right_walk;
	
	private Animation dead;
	private Animation flip;
	private Random r;
	
	private static BufferedImage left_1,left_2,left_3,right_1,right_2,right_3;
	private static boolean initialized=false;
	private static BufferedImage flipped,shell;
	public boolean canFly=true;
	private float dormantTime=0;
	public FlyRedKoopa(int x, int y, MarioSoundManager22050Hz soundManager) {
		
		super(x, y, soundManager);
		r = new Random();
		if (!initialized){
			BufferedImage[] l= new SpriteMap("baddies/Fly_Koopa_Red_Left.png",3,1).getSprites();
			BufferedImage[] r= new SpriteMap( "baddies/Fly_Koopa_Red_Right.png",3,1).getSprites();
			left_1=l[0];
			left_2=l[1];
			left_3=l[2];
			right_1=r[2];
			right_2=r[1];
			right_3=r[0];
			shell = ImageUtilities.loadImage("baddies/Red_Shell_1.png");
			flipped = ImageUtilities.loadImage("baddies/Red_Shell_Flip.png");
			initialized=true;
		}
		left_fly = new Animation(300).addFrame(left_1).setDAL(200).addFrame(left_2).setDAL(200).addFrame(left_3);
		right_fly = new Animation(300).addFrame(right_1).setDAL(200).addFrame(right_2).setDAL(200).addFrame(right_3);
		left_walk = new Animation(300).addFrame(left_2).setDAL(200).addFrame(left_3);
		right_walk = new Animation(300).addFrame(right_2).setDAL(200).addFrame(right_3);
		
		left=left_fly;
		right=right_fly;
		
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}
		
		dead = new DeadAfterAnimation();
		flip = new DeadAfterAnimation();
		dead.addFrame(shell, 10);
		dead.addFrame(shell, 10);
		flip.addFrame(flipped, 1200);
		flip.addFrame(flipped, 1200);
		setAnimation(left);
		setGravityFactor(0.4f);
	}
	
	public void xCollide(Point p) {
		super.xCollide(p);
		/*
		if(currentAnimation() == left) {
			setAnimation(right);
		} else {
			setAnimation(left);
		}
		*/
		if(dx>0) {
			setAnimation(right);
		} else if(dx<0) {
			setAnimation(left);
		}
	}
	
	@Override
	public void creatureXCollide() {
		if(dx > 0) {
			x = x - 2;
			setAnimation(left);
		} else {
			setAnimation(right);
			x = x + 2;
		}
		dx = -dx;
	}
	
	public void flip() {
		setAnimation(flip);
		setIsFlipped(true);
		setIsCollidable(false);
		setGravityFactor(0.7f);
		canFly=false;
		dy = -.2f;
		dx = 0;
	}
	
	public void wakeUp(boolean isLeft) {
		super.wakeUp();
	
			if(isLeft) {
				dx = -.035f;
				setAnimation(left);
			} else {
				dx = .035f;
				setAnimation(right);
			}
		//dy=-0.15f;
	}
	
	public void jumpedOn() {
		if (canFly){
			dormantTime=0;
			left=left_walk;
			right=right_walk;
			canFly=false;
			setGravityFactor(0.7f);
			dy=0;
			dormantTime=0;
		}else{
			setAnimation(dead);
			canFly=false;
			setIsCollidable(false);
			dx = 0;
		}
	}
	
	@Override
	protected void useAI(TileMap map){
		if (!canFly) {
			dormantTime++;
			if (dormantTime>=800){
				dormantTime=0;
				left=left_fly;
				right=right_fly;
				canFly=true;
				setGravityFactor(0.4f);
				dy=-0.2f;
				return;
			}
			
			//don't let it fall
			int tileY = GameRenderer.pixelsToTiles(y+getHeight()-1);
			if (dx>0){
				int tileX = GameRenderer.pixelsToTiles(x-1);
				if (tileX+1>=map.getWidth()){
					x = x - 2;
					setAnimation(left);
					dx=-dx;
					return;
				}
				if (map.getTile(tileX+1, tileY+1)==null &&  (map.getTile(tileX+1, tileY+2)==null)){
					x = x - 2;
					setAnimation(left);
					dx=-dx;
				}
			}else if(dx<0){
				int tileX = GameRenderer.pixelsToTiles(x+getWidth()-1);
				if (tileX-1<0){
					x = x + 2;
					setAnimation(right);
					dx=-dx;
					return;
				}
				if (map.getTile(tileX-1, tileY+1)==null &&  (map.getTile(tileX-1, tileY+2)==null)){
					x = x + 2;
					setAnimation(right);
					dx=-dx;
				}
			}
		}
		
	}
	
}
