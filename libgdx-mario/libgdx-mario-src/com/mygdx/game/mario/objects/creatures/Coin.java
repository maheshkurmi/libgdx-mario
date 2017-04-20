package com.mygdx.game.mario.objects.creatures;



import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;


/**
 * Collected by mario on collision, and total 50 coins ==1 life
 * @author maheshkurmi
 *
 */
public class Coin extends Creature {
	
	private static TextureRegion[] c = { MarioResourceManager.instance.creatures.Coin_5,MarioResourceManager.instance.creatures.Coin_6,
		MarioResourceManager.instance.creatures.Coin_7, MarioResourceManager.instance.creatures.Coin_8};
	public static Animation turn = new Animation(150).addFrame(c[0]).addFrame(c[1]).addFrame(c[2]).addFrame(c[3]);
	// Alternate Animation;
	//private static TextureRegion[] c = { MarioResourceManager.instance.creatures.Coin_1,MarioResourceManager.instance.creatures.Coin_2,
	//	MarioResourceManager.instance.creatures.Coin_3, MarioResourceManager.instance.creatures.Coin_4};
	
	//private Animation turn;
	private Animation shoot;
	
	public Coin(int pixelX, int pixelY, float dx, float dy) {
		this(pixelX, pixelY);
		this.dx=dx;
		this.dy=dy;
	}

	public Coin(int pixelX, int pixelY) {
		
		super(pixelX, pixelY);
		setIsItem(true);
		dx=0; dy=0;
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
		}else if(dx!=0 || dy !=0){
			dy=dy+GRAVITY*time*0.25f;
			y = y + dy * time;
			x=x+dx*time;
		}
	}
	
	public void shoot() {
		setIsCollidable(false);
		setAnimation(shoot);
		dy = -.3f;
		dx = 0;
	}

}
