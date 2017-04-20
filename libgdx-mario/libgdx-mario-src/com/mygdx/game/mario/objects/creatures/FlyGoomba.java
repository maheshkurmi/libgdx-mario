package com.mygdx.game.mario.objects.creatures;



import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;


/**
 * Flying Goomba, looses flying power when mario jumps on it, it starts behaving like normal goomba then
 * @author maheshkurmi
 *
 */
public class FlyGoomba extends Creature {
	
	private Animation waddle;
	private Animation fly;
	private Animation walk;

	private Animation dead;
	private Animation flip;

	
	private static TextureRegion img1,img2,img3,smashed,flipped;;
	
	private static boolean initialized=false;
	public boolean canFly=true;
	private float dormantTime=0;
	public FlyGoomba(int x, int y) {
		
		super(x, y);

		if (!initialized){
			TextureRegion[] l=  MarioResourceManager.instance.creatures.Fly_Goomba;
			img1=l[0];
			img2=l[1];
			img3=l[2];
			smashed = MarioResourceManager.instance.creatures.Goomba_Dead;
			flipped = MarioResourceManager.instance.creatures.Goomba_Flip;
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
