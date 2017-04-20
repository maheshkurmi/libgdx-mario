package com.mygdx.game.mario.objects.creatures;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;

/**
 * Used n world 4 only ,emits random coin
 * @author maheshkurmi
 *
 */
public class CoinEmitter extends Creature {


	private Animation steady;
	private static TextureRegion fd_3,fd_4;;
	private static boolean initialized = false;
    private float t;
	public boolean readytoFire=false;

    private Random random;
	public CoinEmitter(int x, int y) {

		super(x, y);
		if (!initialized) {
			TextureRegion[] images = MarioResourceManager.instance.creatures.Latiku_Fire;
			images = MarioResourceManager.instance.creatures.Latiku;
			fd_3 = images[0];
			fd_4 = images[1];
			initialized = true;
		}
		steady = new Animation(250).addFrame(fd_3).addFrame(fd_4);

		setAnimation(steady);
		setIsItem(true);
		setGravityFactor(0);
		//setIsCollidable(false);
		setIsAlwaysRelevant(true);
		setIsItem(true);
		random=new Random();
		this.y=map.getHeight()*16+3*getHeight();
	}

	@Override
	public void xCollide(Vector2 p) {

	}
	
	public void creatureXCollide() {
		
	}
	
	public void wakeUp(boolean isleft) {
		super.wakeUp();
		//x=map.getWidth()8*16;
		y=map.getHeight()*16+3*getHeight();
	}

	@Override
	public void update(int time) {
		super.update(time);
		t++;
		if (t>200){
			readytoFire=true;
			t=0;
		}
	}
	
	@Override
	public void updateCreature(TileMap map, int time) {
		//update(time);
		if(random.nextInt(700)==300){
				fireRandomCoins(map);
				readytoFire=false;
		}
	}
	
	public void jumpedOn() {

	}

	private void fireRandomCoins(TileMap map){
		float theta;
		int n=12+random.nextInt(5);
		float dx,dy;
		Coin c;
		int x=(int) (map.getWidth()/2-8+Math.random()*(16))*16;
		for (int i = 0; i <= n; i++) {
			float v=(float) (0.32f+0.025f*Math.random());
			theta=(float) ((0.30+0.4*i/n)*Math.PI);
			dx=(float) (0.30f*v*Math.cos(theta));
			dy=(float) (-v*Math.sin(theta));
			c=new Coin(x,(int)getY(),dx,dy);
			c.setIsAlwaysRelevant(true);
			map.creaturesToAdd().add(c);
		}
		
	}

}
