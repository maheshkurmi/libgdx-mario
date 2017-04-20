package com.mygdx.game.mario.objects.creatures;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.MarioSoundManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;
import com.mygdx.game.mario.objects.GameTile;


public class Fire_Thorny extends Creature {


	private Animation ball;
	private static TextureRegion fd_1, fd_2;
	private static boolean initialized = false;
	public Fire_Thorny(int x, int y) {

		super(x, y);
	 
		if (!initialized) {
			 TextureRegion[] images = MarioResourceManager.instance.creatures.Fire_Thorny;
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
	public void xCollide(Vector2 p) {

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
		
		int tileY = TileMap.pixelsToTiles(y + getHeight());
		int tileX = TileMap.pixelsToTiles(x + getWidth()/2);
		GameTile tile=map.getTile(tileX, tileY );
		if (tile!= null){
			if (!tile.isCollidable())return;
			kill();
			MarioSoundManager.instance.playKick();
			map.creaturesToAdd().add(new Thorny(Math.round(getX()), 
					Math.round(getY())));

		}
	}
	
	public void jumpedOn() {

	}

}
