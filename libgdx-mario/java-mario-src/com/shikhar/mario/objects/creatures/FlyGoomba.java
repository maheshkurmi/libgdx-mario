package com.shikhar.mario.objects.creatures;




import java.awt.image.BufferedImage;
import java.util.Random;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageUtilities;
import com.shikhar.mario.util.SpriteMap;





public class FlyGoomba extends Creature {
	
	private Animation waddle;
	private Animation fly;
	private Animation walk;

	private Animation dead;
	private Animation flip;
	private Random r;
	
	private static BufferedImage img1,img2,img3,smashed,flipped;;
	
	private static boolean initialized=false;
	public boolean canFly=true;
	private float dormantTime=0;
	public FlyGoomba(int x, int y, MarioSoundManager22050Hz soundManager) {
		
		super(x, y, soundManager);
		r = new Random();
		if (!initialized){
			BufferedImage[] l= new SpriteMap("baddies/Fly_Goomba.png", 3, 1).getSprites();
			img1=l[0];
			img2=l[1];
			img3=l[2];
			smashed = ImageUtilities.loadImage("baddies/Goomba_Dead.png");
			flipped = ImageUtilities.loadImage("baddies/Goomba_Flip.png");
			initialized=true;
		}
		fly = new Animation(300).addFrame(img1).setDAL(200).addFrame(img2).setDAL(200).addFrame(img3);
		walk = new Animation(300).addFrame(img2).setDAL(200).addFrame(img3);
	
		waddle=fly;
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}
		dead = new DeadAfterAnimation().setDAL(100).addFrame(smashed).setDAL(20).addFrame(smashed);
		flip = new Animation().addFrame(flipped).addFrame(flipped);

		setAnimation(waddle);
		setGravityFactor(0.4f);
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
		} else {
				dx = .035f;
		}
		//dy=-0.15f;
	}
	
	public void jumpedOn() {
		if (canFly){
			dormantTime=0;
			waddle=walk;
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
				waddle=fly;
				canFly=true;
				setGravityFactor(0.4f);
				dy=-0.2f;
				return;
			}
		}
		
	}
	
}
