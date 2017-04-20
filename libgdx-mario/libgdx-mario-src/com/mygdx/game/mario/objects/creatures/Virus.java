package com.mygdx.game.mario.objects.creatures;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;

/**
 * Water living lethal creatures, can be killed by fireballs only
 * @author maheshkurmi
 *
 */
public class Virus extends Creature {
	
	private Animation waddle, flip;
	private int initY=0, span;
	private static TextureRegion[]v;
	private static boolean initialized=false;

	public Virus(int x, int y) {
		
		super(x, y);
		if (!initialized){
			v = MarioResourceManager.instance.creatures.Virus;
			initialized=true;
		}
		initY=y;
		waddle = new Animation(200).addFrame(v[0]).addFrame(v[1]);
		flip = new Animation().addFrame(v[2]).addFrame(v[2]);
		setAnimation(waddle);
		dx=0;
	}
	
	@Override
	public void xCollide(Vector2 p) {
		
	}
	
	@Override
	public void creatureXCollide() {
		
	}
	
	public void wakeUp(boolean isLeft) {
		super.wakeUp();
		dy =0.05f;//(float) (-0.5f+ Math.random()*0.9);
		span=20+(int) (Math.random()*20);
	}
	
	@Override
	public void updateCreature(TileMap map, int time) {
		if (isFlipped()){
			super.updateCreature(map, time);
		}else{
			y=y+dy*time;
			if (y>initY+span || y<initY-span)dy=-dy;
			super.update(time);
		}
	}
	
	public void jumpedOn() {
		//setAnimation(dead);
		//setIsCollidable(false);
		//dx = 0;
	}
	
	public void flip() {
		setAnimation(flip);
		setIsFlipped(true);
		setIsCollidable(false);
		dy = -.1f;
		dx = 0;
	}
	
	

}
