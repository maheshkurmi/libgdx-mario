package com.shikhar.mario.objects.tiles;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.shikhar.mario.core.Settings;
import com.shikhar.mario.core.animation.Animation;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.creatures.Coin;
import com.shikhar.mario.objects.creatures.FireFlower;
import com.shikhar.mario.objects.creatures.Mushroom;
import com.shikhar.mario.objects.creatures.Score;
import com.shikhar.mario.objects.mario.Mario;
import com.shikhar.mario.util.ImageUtilities;


public class QuestionBlock extends GameTile {

	private MarioSoundManager22050Hz soundManager;
	private TileMap map;
	
	private Animation active;
	private Animation dead;
	private boolean isActive;
	private boolean hasCoin;
	private boolean hasMushroom;
	private static BufferedImage q[] ;
	public QuestionBlock(int pixelX, int pixelY, TileMap map, MarioSoundManager22050Hz soundManager, boolean hasCoin,
			boolean hasMushroom) {
		
		// int pixelX, int pixelY, Animation anim, Image img
		super(pixelX, pixelY, null, null);
		
		setIsSloped(false);
		isActive = true;
		this.hasCoin = hasCoin;
		this.hasMushroom = hasMushroom;
		this.soundManager = soundManager;
		this.map = map;
		if (q==null){
			q = new BufferedImage[5];
			q[0] = ImageUtilities.loadImage("items/Question_Block_0.png");
			q[1] = ImageUtilities.loadImage("items/Question_Block_1.png");
			q[2] = ImageUtilities.loadImage("items/Question_Block_2.png");
			q[3] = ImageUtilities.loadImage("items/Question_Block_3.png");
			q[4] = ImageUtilities.loadImage("items/Question_Block_Dead.png");
		}
		Random r = new Random();
		active = new Animation(r.nextInt(20) + 140).addFrame(q[0]).addFrame(q[1]).addFrame(q[2]).addFrame(q[3]);
		dead = new Animation(2000).addFrame(q[4]);
		setAnimation(active);
	}
	
	public void update(int time) {
		super.update(time);
		if(getOffsetY() != 0) { setOffsetY(getOffsetY() + 2); }
	}
	
	public void doAction() {
		if(isActive) {
			if(hasCoin) {
				setOffsetY(-10);
				soundManager.playCoin();
				Coin newCoin = new Coin(getPixelX(), getPixelY());
				Score score = new Score(getPixelX(), getPixelY(),0);
				map.creaturesToAdd().add(newCoin);
				map.creaturesToAdd().add(score);
				Settings.addScore(100);
				Settings.addCoins(1);
				newCoin.shoot();
			} else if(hasMushroom) {
				setOffsetY(-10);
				soundManager.playItemSprout();
				if (!((Mario)map.getPlayer()).isSmall() && (!((Mario)map.getPlayer()).isFireMan())){
					FireFlower flower=new FireFlower(getPixelX(), getPixelY());
					map.creaturesToAdd().add(flower);
				}else{
					Mushroom shroom = new Mushroom(getPixelX(), getPixelY()-16,((Mario)map.getPlayer()).isFireMan());
					map.creaturesToAdd().add(shroom);
				}
				Settings.addScore(100);
			}
			setAnimation(dead);
			isActive = false;
		}
	}
}