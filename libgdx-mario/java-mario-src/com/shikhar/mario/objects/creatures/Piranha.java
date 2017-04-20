package com.shikhar.mario.objects.creatures;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageUtilities;


public class Piranha extends Creature {
	
	private static BufferedImage p1 = ImageUtilities.loadImage("baddies/Piranha_1.png");
	private static BufferedImage p2 =ImageUtilities.loadImage("baddies/Piranha_2.png");
	public static Animation turn ;
	float dy=0.3f;
	float y1=0;
	float initY;
	private int wait=0;
	public Piranha(int pixelX, int pixelY, MarioSoundManager22050Hz soundManager) {
		super(pixelX, pixelY, soundManager);
		turn = new Animation(200).addFrame(p1).addFrame(p2);
		setAnimation(turn);
		dy= 0.5f;
		initY=pixelY;
		setOffsetX(-3);
	}
	
	@Override
	// for creature collisions
	public void creatureXCollide() {
		
	}
	
	@Override	
	// for tile collisions
	public void xCollide(Point p) {
	
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawImage(currentAnimation().getImage(), x+getOffsetX() , y+getOffsetY(),x +getWidth()+getOffsetX(),y+getOffsetY() +  getHeight()-(int)y1,0,0,getWidth(),getHeight()-(int)y1,null);
	}
	
	@Override
	public void updateCreature(TileMap map, int time) {
		// make sure piranha waits for some time inside pipe
		if (wait > 0) {
				if (map.getPlayer().getX() < x) {
					if (x - map.getPlayer().getX() <= map.getPlayer().getWidth() + 16){
						wait = 75;
						return;
					}
				} else {
					if (map.getPlayer().getX() - x <= 16 + getWidth() - 8){
						wait = 75;
						return;
					}
				}
			wait--;
			return;
		}
		
		super.update(time);
		
		
	    y1=y1+dy;
		if (y1 >getHeight()){
			y1=getHeight();
			dy=-Math.abs(dy);
			wait=70;
		}else if (y1<0){
			y1=0;
			dy=Math.abs(dy);
		}
		y=initY+y1;
		//setOffsetY(getOffsetY()+1); 
	}
	
	public void flip() {
		kill();
	}
}
