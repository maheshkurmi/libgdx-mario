package com.mygdx.game.mario.objects.creatures;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;



/**
 * Generally placed over pipe, so that it appears to come out of it, can be killed only by fireball
 * @author maheshkurmi
 *
 */
public class FireFlower extends Creature {
	private static TextureRegion flower;
	private Animation fireFlower;
	private int updateNum;
	float y1=0;
	float initY;
	public boolean isReady=false;;
	
	public FireFlower(int pixelX, int pixelY) {
		super(pixelX, pixelY);
		setIsItem(true);
		setIsAlwaysRelevant(true);
		flower = MarioResourceManager.instance.creatures.Fire_Flower;
		fireFlower = new Animation();

		fireFlower.addFrame(flower, 1000);
		fireFlower.addFrame(flower, 1000);
		
		setAnimation(fireFlower);
		dy=0.5f;
		dx=0;
		updateNum = 0;
		initY=pixelY;
		//setOffsetX(-3);
		y1=getHeight();
	}
	
	@Override
	public void draw(SpriteBatch g, int x, int y) {
		TextureRegion r=new TextureRegion(currentAnimation().getImage());//,0,0,getWidth(),getHeight()-(int)y1);
		//g.drawBitmap(currentAnimation().getImage(),new Rect(0,0,getWidth(),getHeight()-(int)y1),new Rect(x+getOffsetX() , y+getOffsetY(),x +getWidth()+getOffsetX(),y+getOffsetY() +  getHeight()-(int)y1),null);

		g.draw(r,x+getOffsetX() , y+getOffsetY(),getWidth(), getHeight()-(int)y1);
	}
	
	public void updateCreature(TileMap map, int time) {
		super.update(time);

		if (y1 >0){
			y1=y1-dy;
			if (y1<0)y1=0;
			y=initY-getHeight()+y1;
		}
		
		//setOffsetY(getOffsetY()+1); 
		if(updateNum>500 && updateNum < 600) {
			if(updateNum % 6 == 0 || updateNum % 6 == 1 || updateNum % 6==2) {
				setIsInvisible(true);
			} else {
				setIsInvisible(false);
			}
		} else if (updateNum> 600){
			kill();
		}
		updateNum += 1;
	}
}

