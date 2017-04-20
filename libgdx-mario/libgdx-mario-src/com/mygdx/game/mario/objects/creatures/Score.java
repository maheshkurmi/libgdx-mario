package com.mygdx.game.mario.objects.creatures;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.GameRenderer;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;

/**
 * Non collidable short lived object which just displays animated score
 * @author maheshkurmi
 *
 */
public class Score extends Creature {
	
	public Animation oneHundred;
	private static TextureRegion one_hundred;
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
			one_hundred = MarioResourceManager.instance.creatures.Score_100_New6;
			break;
		case 1:
			one_hundred = MarioResourceManager.instance.creatures.Score_1_Up;
			dy = -.25f;
			break;
		case 2:
			one_hundred = MarioResourceManager.instance.creatures.Score_1000;
			dy = -.25f;
			break;
		case 3:
			one_hundred = MarioResourceManager.instance.creatures.Score_200;
			dy = -.1f;
			break;
				
		default:
			one_hundred = MarioResourceManager.instance.creatures.Score_100_New6;
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
	public void draw(SpriteBatch g, int x, int y) {
		if (type==-1){
			GameRenderer.drawStringDropShadow(g, msg, x, y,0);
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
