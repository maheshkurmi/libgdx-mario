package com.mygdx.game.mario.objects.creatures;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.Creature;

/**
 * Killer plant
 * @author maheshkurmi
 *
 */
public class Piranha extends Creature {
	
	private static TextureRegion p1;
	private static TextureRegion p2;
	public static Animation turn ;
	private static boolean initialized = false;

	float dy=0.3f;
	float y1=0;
	float initY;
	private int wait=0;
	public Piranha(int pixelX, int pixelY) {
		super(pixelX, pixelY);
		if (!initialized) {
			p1 =  MarioResourceManager.instance.creatures.Piranha_1;
			p2 =  MarioResourceManager.instance.creatures.Piranha_2;
			initialized = true;
		}
		
		turn = new Animation(200).addFrame(p1).addFrame(p2);
		setAnimation(turn);
		dy= 0.5f;
		dx=0;
		initY=pixelY;
		setOffsetX(-3);
		y1=getHeight();
	}
	
	@Override
	public void draw(SpriteBatch g, int x, int y) {
		TextureRegion r=new TextureRegion(currentAnimation().getImage());//,0,0,getWidth(),getHeight()-(int)y1);
		r.flip(false, true);
		g.draw(r.getTexture(),x+getOffsetX() , y+getOffsetY(),getWidth(), getHeight()-(int)y1,r.getU(),r.getV(),r.getU2(),r.getV2()-(r.getV2()-r.getV())*((int)y1)/getHeight());
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
			wait=75;
		}else if (y1<0){
			y1=0;
			dy=Math.abs(dy);
		}
		y=initY+y1;
		//setOffsetY(getOffsetY()+1); 
	}
	
	//override for creature collisions
	public void creatureXCollide() {}
		
	public void flip() {
		kill();
	}
}
