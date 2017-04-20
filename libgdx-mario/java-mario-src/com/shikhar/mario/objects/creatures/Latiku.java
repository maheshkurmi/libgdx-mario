package com.shikhar.mario.objects.creatures;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.util.SpriteMap;


public class Latiku extends Creature {


	private Animation steady, fire, flip;
	private static BufferedImage fd_1, fd_2,fd_3,fd_4, fd_5;
	private static boolean initialized = false;
    private float t;
	public boolean readytoFire=false;
	private TileMap map;
	private int A=30;
	private int dormantTime=-1;
	private int initY;
	public Latiku(int x, int y, MarioSoundManager22050Hz soundManager,
			TileMap map) {

		super(x, y, soundManager);
		this.map = map;
		
		if (!initialized) {
			BufferedImage[] images = new SpriteMap("baddies/Latiku_Fire.png", 2, 1)
			.getSprites();
			fd_1 = images[0];
			fd_2 = images[1];
			images = new SpriteMap("baddies/Latiku.png", 3, 1)
					.getSprites();
			fd_3 = images[0];
			fd_4 = images[1];
			fd_5 = images[2];
			initialized = true;
		}
		steady = new Animation(250).addFrame(fd_3).addFrame(fd_4);

		final class FireAnimation extends Animation {
			public void endOfAnimationAction() {
				setAnimation(steady);
			}
		}
		fire = new FireAnimation().setDAL(250).addFrame(fd_3).addFrame(fd_2)
				.addFrame(fd_1).addFrame(fd_2).addFrame(fd_4);
	
		final class FlipAnimation extends Animation {
			public void endOfAnimationAction() {
				//dormantTime=200;
				//setIsInvisible(true);
				//setAnimation(steady);
			}
		}
		flip = new FlipAnimation();
		flip.addFrame(fd_5, 1200);
		flip.addFrame(fd_5, 1200);
		setAnimation(steady);
		setIsItem(true);
		setGravityFactor(0);
		//setIsCollidable(false);
		setIsAlwaysRelevant(true);
		setIsItem(true);
		initY=y+getHeight();
	}

	@Override
	public void xCollide(Point p) {

	}
	
	public void creatureXCollide() {
		
	}
	
	public void wakeUp() {
		super.wakeUp();
		x=map.getPlayer().getX()+A;
		y=initY-getHeight();
	}

	@Override
	public void update(int time) {
		super.update(time);
		t++;
		if (t>150){
			readytoFire=true;
			t=0;
		}
	}
	
	@Override
	public void updateCreature(TileMap map, int time) {
		update(time);
		if(this.currentAnimation()==flip && dormantTime<0){
			if (y>=map.getHeight()*16-16){
				dormantTime=400;
				setIsInvisible(true);
			}else{
				y=y+10*GRAVITY*time*time;
			}
			//if (y>mp.getHeight())
			return;
		}else if (dormantTime>0){
			dormantTime--;
			return;
		}else if (dormantTime==0){
			setAnimation(steady);
			soundManager.playItemSprout();
			setIsCollidable(true);
			setIsFlipped(false);
			setIsInvisible(false);
			x=map.getPlayer().getX()+A;
			y=initY-getHeight();
			dx=-2;
			wakeUp();
			dormantTime=-1;
			readytoFire=false;
			return;
		}
		double x1=x-map.getPlayer().getX();
		if(Math.abs(x1)<A){
			dx=Math.signum(dx)*100f;
		}else if (x1>A){
			dx=(float) (dx-(x1-A)/10.0);
		}else if (x1<-A){
			dx=(float) (dx+(-x1-A)/10.0);
		}
		//double v1=-x1/5;// -Math.signum(x1)*Math.abs(x1+100)/5;//-Math.signum(x1)*A*(x1*x1));
		//v1=Math.signum(v1)*(Math.abs(v1)+10);
		x=(float) (x+dx*time/600.0);
		if(x>=map.getWidth()*16-80){
			x=map.getWidth()*16-80;
			dx=-200;
		}
		y=initY-getHeight();
		//super.updateCreature(map,time);
		if (readytoFire && currentAnimation()!=fire){
			setAnimation(fire);
			//Latiku.this.map.creaturesToAdd().add(new Fire_Thorny((int)getX(),(int)getY()+15,Latiku.this.soundManager));
			//soundManager.playKick();
			///map.creaturesToAdd().add(new BoomRang((int) (dx<0?x:x+getWidth()),(int)(y+getHeight()*0.13f),dx<0?-2:2,soundManager));
			//readytoFire=false;
		}
		if(readytoFire && currentAnimation()==fire && !map.getPlayer().isLevelClear){
			if (currentAnimation().getImage()==fd_1){
				Latiku.this.map.creaturesToAdd().add(new Fire_Thorny((int)getX()+4,(int)getY(),Latiku.this.soundManager));
				soundManager.playKick();
				readytoFire=false;
			}
		}
	}
	
	public void jumpedOn() {
		setAnimation(flip);
		soundManager.playJump();
		setGravityFactor(0.7f);
		setIsCollidable(false);
		setIsFlipped(true);
		dx = 0;
		dy=0;
		
	}

}
