package com.mygdx.game.mario.objects.creatures;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.MarioSoundManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;

/**
 * Mario can fire fireballs (if loaded) to kill enemies
 * @author mahesh
 *
 */
public class FireBall extends Creature {


	private Animation dead;
	private Animation ball;
	private static TextureRegion fb_1, fb_2, fb_3, fb_4, fb_5,fb_6;
	private static boolean initialized = false;
	/**
	 * controls max number of fireballs at an instant in map (maximum=6)
	 */
	public static int fireballsCount=0;
	/**
	 * controls min. time interval of firing between 2 successive fireballs
	 */
	public static int aliveTime=800;
		
	public FireBall(int x, int y, float direction) {

		super(x, y);
		fireballsCount++;
		aliveTime=0;
		if (!initialized) {
			fb_1 =  MarioResourceManager.instance.creatures.fb_1;//loadImage("baddies/fireball_1.png");
			fb_2 =  MarioResourceManager.instance.creatures.fb_2;//loadImage("baddies/fireball_2.png");
			fb_3 =  MarioResourceManager.instance.creatures.fb_3;//loadImage("baddies/fireball_3.png");
			fb_4 =  MarioResourceManager.instance.creatures.fb_4;//loadImage("baddies/fireball_4.png");
			fb_5 =  MarioResourceManager.instance.creatures.fb_5;//loadImage("baddies/fireball_5.png");
			fb_6 =  MarioResourceManager.instance.creatures.fb_6;//loadImage("baddies/fireball_6.png");
			
			initialized = true;
		}
		ball = new Animation(150).addFrame(fb_1).addFrame(fb_2).addFrame(fb_3).addFrame(fb_4);
		
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}
		dead = new DeadAfterAnimation().setDAL(50).addFrame(fb_5).setDAL(100).addFrame(fb_6);
		//dead = new DeadAfterAnimation();
		// dead.addFrame(fb_5, 10);
		//dead.addFrame(fb_6, 10);
		setAnimation(ball);
		dx=direction*.12f;
		setIsAlwaysRelevant(true);
		//dy=0.3f;
	}

	@Override
	public void updateCreature(TileMap map, int time) {
		super.updateCreature(map, time);
		if (aliveTime<800)aliveTime+=time;
	}
	
	@Override
	public void xCollide(Vector2 p) {
		dx=0;
		dy=0;
		//super.xCollide(p);
		setIsCollidable(false);
		setAnimation(dead);
		MarioSoundManager.instance.playKick();
	}

	/**
	 * used to check that prev fireball has spent sufficient time  
	 * @return true when fireball throw is allowed
	 */
    public static boolean isReady(){
    	return (fireballsCount==0 || (aliveTime>=800 && fireballsCount<=10));
    }
    
    @Override
    public void kill(){
    	super.kill();
    	aliveTime=800;
    	fireballsCount--;
    }
}
