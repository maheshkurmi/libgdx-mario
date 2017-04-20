package com.shikhar.mario.objects.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageUtilities;





public class FireFlower extends Creature {
	private static BufferedImage flower;
	private Animation fireFlower;
	private int updateNum;
	private static boolean initialized = false;

	float y1=0;
	float initY;
	public boolean isReady=false;;
	
	public FireFlower(int pixelX, int pixelY) {
		super(pixelX, pixelY);
		setIsItem(true);
		setIsAlwaysRelevant(true);
		if (!initialized){
			flower = ImageUtilities.loadImage("items/Fire_Flower.png");
			initialized=true;
		}
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
	public void draw(Graphics g, int x, int y) {
		g.drawImage(currentAnimation().getImage(),x+getOffsetX() , y+getOffsetY(),x +getWidth()+getOffsetX(),y+getOffsetY() +  getHeight()-(int)y1,0,0,getWidth(),getHeight()-(int)y1,null);
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

