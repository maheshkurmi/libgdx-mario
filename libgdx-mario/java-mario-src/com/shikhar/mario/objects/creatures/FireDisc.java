package com.shikhar.mario.objects.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.SpriteMap;


public class FireDisc extends Creature {


	private Animation ball;
	private static BufferedImage fd_1, fd_2, fd_3;
	private static boolean initialized = false;
    private float radius=70;
    private double theta,omega=2*Math.PI/7;
    private int cx,cy;
	public FireDisc(int x, int y, MarioSoundManager22050Hz soundManager) {

		super(x, y, soundManager);
		cx=x;
		cy=y;
		theta=Math.random()*Math.PI*2;
		this.x=(float) (cx+radius*Math.cos(theta));
		this.y=(float) (cy+radius*Math.sin(theta));
	 
		if (!initialized) {
			 BufferedImage[] images = new SpriteMap(
						"baddies/Fire_Disc.png", 3, 1).getSprites();
			fd_1 =  images[0];
			fd_2 =  images[1];
			fd_3 =  images[2];
			initialized = true;
		}
		ball = new Animation(200).addFrame(fd_1).addFrame(fd_2).addFrame(fd_3);
		setAnimation(ball);
		setIsItem(true);
		setGravityFactor(0);
	}

	@Override
	public void xCollide(Point p) {

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
