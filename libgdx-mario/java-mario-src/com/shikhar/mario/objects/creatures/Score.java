package com.shikhar.mario.objects.creatures;



import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.shikhar.mario.core.GameRenderer;
import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageUtilities;




public class Score extends Creature {
	
	public Animation oneHundred;
	private static BufferedImage one_hundred;
	/**type=0 score100, 2=+1up, 3=200*/
	private int type;
	private float t;
	private int initY;
	private String msg="";
	
	public Score(int x, int y, String msg) {
		this(x, y,-1);
		setIsItem(true);
		initY=y-5;
		type=-1;
		this.msg=msg;
	}
	
	public Score(int x, int y, int type) {
			
		super(x, y);
		setIsItem(true);
		initY=y-5;
		this.type=type;
		dy = -.45f;
		switch(type){
		case 0:
			one_hundred = ImageUtilities.loadImage("items/Score_100_New6.png");
			break;
		
		case 1:
			one_hundred = ImageUtilities.loadImage("items/Score_1_Up.png");
			dy = -.25f;
			break;
		case 2:
			one_hundred = ImageUtilities.loadImage("items/Score_1000.png");
			dy = -.25f;
			break;
		case 3:
			one_hundred = ImageUtilities.loadImage("items/Score_200.png");
			dy = -.1f;
			break;
				
		default:
			one_hundred = ImageUtilities.loadImage("items/Score_100_New6.png");
			break;
		}
		
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}
		
		oneHundred = new DeadAfterAnimation();
		oneHundred.addFrame(one_hundred, type==0?380:600);
		oneHundred.addFrame(one_hundred, type==0?380:400);	
		setAnimation(oneHundred);
		
		
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		if (type==-1){
			GameRenderer.drawStringSmall(g, msg, x, y);
		}else{
			super.draw(g, x+getWidth()/2, y);
		}
	}
	
	public void updateCreature(TileMap map, int time) {
		this.update((int) time);
		if(type!=0){
			if (t<0.8)t+=time/1000.0f;
			if (type>0)
				y= (float) (initY-15+15*Math.cos(4*t));
			else{
				y= (float) (initY-25+25*Math.cos(4*t));
			}
			return;
		}
		y = y + dy * time;
		if(dy < 0) {
			if(type!=3)dy = dy + .032f;
			else
				dy=0.8f*dy;
		} else {
			dy = 0;
		}
	}

}
