package com.mygdx.game.mario.objects.creatures;




import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;

/**
 * Converts to shell on jumping over it, which can be kicked sideways to kill other animals
 * Very handy in world 4
 * @author maheshkurmi
 *
 */
public class RedKoopa extends Creature {
	
	private Animation left;
	private Animation right;
	private Animation dead;
	private Animation flip;

	private boolean isGreen=true;
	private  TextureRegion left_1,left_2,right_1,right_2,shell,flipped;
	public RedKoopa(int x, int y,boolean isGreen) {
		
		super(x, y);
		this.isGreen=isGreen;
	
			if (!isGreen){
			 left_1 = MarioResourceManager.instance.creatures.Koopa_Red_Left_1;
			 left_2 = MarioResourceManager.instance.creatures.Koopa_Red_Left_2;
			 right_1 = MarioResourceManager.instance.creatures.Koopa_Red_Right_1;
			 right_2 = MarioResourceManager.instance.creatures.Koopa_Red_Right_2;
			 shell = MarioResourceManager.instance.creatures.Red_Shell_1;
			 flipped = MarioResourceManager.instance.creatures.Red_Shell_Flip;
			}else{
			 left_1 = MarioResourceManager.instance.creatures.Green_Koopa[0];
			 left_2 = MarioResourceManager.instance.creatures.Green_Koopa[1];
			 right_1 = MarioResourceManager.instance.creatures.Green_Koopa[2];
			 right_2 = MarioResourceManager.instance.creatures.Green_Koopa[3];
			 shell = MarioResourceManager.instance.creatures.Green_Shell[4];
			 flipped = MarioResourceManager.instance.creatures.Green_Shell[5];
		}
		left = new Animation(150).addFrame(left_1).addFrame(left_2);
		right = new Animation(150).addFrame(right_1).addFrame(right_2);
		
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
		//align base of creature to tile
		this.y=y-getHeight()+16;
	}
	
	@Override
	public void xCollide(Vector2 p) {
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
		dy = -.2f;
		dx = 0;
	}
	
	public void wakeUp(boolean isLeft) {
		super.wakeUp();
			if(isLeft) {
				dx = -.03f;
				setAnimation(left);
			} else {
				dx = .03f;
				setAnimation(right);
			}
	}
	
	@Override
	protected void useAI(TileMap map){
		int tileY = TileMap.pixelsToTiles(y+getHeight()-1);
		if (dx>0){
			int tileX = TileMap.pixelsToTiles(x-1);
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
			int tileX = TileMap.pixelsToTiles(x+getWidth()-1);
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
	
	public void jumpedOn() {
		setAnimation(dead);
		setIsCollidable(false);
		dx = 0;
	}

	public boolean isGreen() {
		return isGreen;
	}


}
