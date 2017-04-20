package com.mygdx.game.mario.objects.creatures;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;

/**
 * Rotating lethal and immortal disc
 * @author maheshkurmi
 *
 */
public class FireDisc extends Creature {


	private Animation ball;
	private static TextureRegion fd_1, fd_2, fd_3;
	private static boolean initialized = false;
    private float radius=55;
    private double theta,omega=2*Math.PI/6;
    private int cx,cy;
	public FireDisc(int x, int y) {

		super(x,y);
		cx=x;
		cy=y;
		theta=Math.random()*Math.PI*2;
		this.x=(float) (cx+radius*Math.cos(theta));
		this.y=(float) (cy+radius*Math.sin(theta));
		radius=(float) (radius+Math.random()*10);
		if (!initialized) {
			fd_1 =  MarioResourceManager.instance.creatures.FireDisc[0];
			fd_2 =  MarioResourceManager.instance.creatures.FireDisc[1];
			fd_3 =  MarioResourceManager.instance.creatures.FireDisc[2];
			initialized = true;
		}
		ball = new Animation(200).addFrame(fd_1).addFrame(fd_2).addFrame(fd_3);
		setAnimation(ball);
		setIsItem(true);
		setGravityFactor(0);
	}

	@Override
	public void xCollide(Vector2 p) {

	}
	
	public void creatureXCollide() {
		
	}
	
	@Override
	public void updateCreature(TileMap map, int time) {
		x=(float) (cx+radius*Math.cos(theta));
		y=(float) (cy+radius*Math.sin(theta));
		theta+=omega*time/1000.0;
		super.update(time);
	}
	
	public void jumpedOn() {

	}

}
