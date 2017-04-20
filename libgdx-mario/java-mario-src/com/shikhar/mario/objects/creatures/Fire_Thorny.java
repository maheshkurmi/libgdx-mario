package com.shikhar.mario.objects.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.shikhar.mario.core.GameRenderer;
import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.SpriteMap;


public class Fire_Thorny extends Creature {


	private Animation ball;
	private static BufferedImage fd_1, fd_2;
	private static boolean initialized = false;
	public Fire_Thorny(int x, int y, MarioSoundManager22050Hz soundManager) {

		super(x, y, soundManager);
	 
		if (!initialized) {
			 BufferedImage[] images = new SpriteMap(
						"baddies/Thorny_Bomb.png", 2, 1).getSprites();
			fd_1 =  images[0];
			fd_2 =  images[1];
			initialized = true;
		}
		ball = new Animation(120).addFrame(fd_1).addFrame(fd_2);
		setAnimation(ball);
		setIsItem(true);
		setGravityFactor(0.8f);
	}

	@Override
	public void xCollide(Point p) {

	}
	
	public void creatureXCollide() {
		
	}
	
	public void wakeUp() {
		super.wakeUp();
		dy=-0.09f;
	}

	@Override
	public void updateCreature(TileMap map, int time) {
		//super.update(time);
		//super.updateCreature(map,time);
		if(dy < gravityEffect) { // apply gravity...this must be done first
			if (isFlipped()){
				dy = dy + gravityFactor*GRAVITY * time;
			}else{
				dy = dy +  (inWater?0.3f:1)*gravityFactor*GRAVITY * time;
			}
		}
		y= y+ dy * time; 
		
		int tileY = GameRenderer.pixelsToTiles(y + getHeight());
		int tileX = GameRenderer.pixelsToTiles(x + getWidth()/2);
		GameTile tile=map.getTile(tileX, tileY );
		if (tile!= null){
			if (!tile.isCollidable())return;
			kill();
			soundManager.playKick();
			map.creaturesToAdd().add(new Thorny(Math.round(getX()), 
					Math.round(getY()), soundManager));

		}
	}
	
	public void jumpedOn() {

	}

}
