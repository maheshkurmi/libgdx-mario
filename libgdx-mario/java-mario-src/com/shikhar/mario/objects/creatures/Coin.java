package com.shikhar.mario.objects.creatures;


import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.ImageUtilities;




public class Coin extends Creature {
	
	private static BufferedImage[] c = { ImageUtilities.loadImage("items/Coin_5.png"), ImageUtilities.loadImage("items/Coin_6.png"),
		ImageUtilities.loadImage("items/Coin_7.png"), ImageUtilities.loadImage("items/Coin_8.png") };
	public static Animation turn = new Animation(150).addFrame(c[0]).addFrame(c[1]).addFrame(c[2]).addFrame(c[3]);
	// Alternate Animation;
    //private static BufferedImage[] c = { loadImage("items/Coin_1.png"), loadImage("items/Coin_2.png"),
	//	loadImage("items/Coin_3.png"), loadImage("items/Coin_4.png") };
	
	//private Animation turn;
	private Animation shoot;
	
	public Coin(int pixelX, int pixelY) {
		
		super(pixelX, pixelY);
		setIsItem(true);
		
		final class DeadAfterAnimation extends Animation {
			public void endOfAnimationAction() {
				kill();
			}
		}
		
		//turn = new Animation(1000).addFrame(c[0]).addFrame(c[1]).addFrame(c[2]).addFrame(c[3]);
		shoot = new DeadAfterAnimation().setDAL(120).addFrame(c[0]).addFrame(c[1]).addFrame(c[2]).addFrame(c[3]);
		setAnimation(turn);
	}
	
//	public void draw(Graphics g, int pixelX, int pixelY) {
//		float[] scales = { .6f, .6f, .6f, 8f};
//		float[] offsets = new float[4];
//		RescaleOp rop = new RescaleOp(scales, offsets, null);
//		
//		//RescaleOp rop = new RescaleOp(1.0f, 1f, null);
//		
//		BufferedImage source= currentAnimation().getImage();
//		BufferedImage adjusted=new BufferedImage(source.getWidth(),source.getHeight(),BufferedImage.TYPE_INT_BGR);
//		Graphics2D ug=adjusted.createGraphics();
//		ug.drawImage(source,0,0,null);
//		ug.dispose();
//		BufferedImageOp filter=new RescaleOp(1f, 100f,null);
//
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.drawImage(adjusted, filter, pixelX, pixelY);
//	}
//	
//	public void draw(Graphics g, int pixelX, int pixelY, int offsetX, int offsetY) {
//		draw(g, pixelX + offsetX, pixelY + offsetY);	
//	}
	
	public void updateCreature(TileMap map, int time) {
		if(currentAnimation() == shoot) {
			super.update(time);
			y = y + dy * time;
			if(dy < 0) {
				dy = dy + .018f;
			} 
		}
	}
	
	public void shoot() {
		setIsCollidable(false);
		setAnimation(shoot);
		dy = -.3f;
	}

}
