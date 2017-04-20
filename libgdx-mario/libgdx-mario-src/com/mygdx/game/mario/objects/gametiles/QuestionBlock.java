package com.mygdx.game.mario.objects.gametiles;

import java.util.Random;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.MarioSoundManager;
import com.mygdx.game.mario.Settings;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.Animation;
import com.mygdx.game.mario.objects.GameTile;
import com.mygdx.game.mario.objects.creatures.Coin;
import com.mygdx.game.mario.objects.creatures.FireFlower;
import com.mygdx.game.mario.objects.creatures.Mushroom;
import com.mygdx.game.mario.objects.creatures.Score;
import com.mygdx.game.mario.objects.mario.Mario;

/**
 * Secret tile that can contain coin or power ups
 * @author maheshkurmi
 *
 */
public class QuestionBlock extends GameTile {


	private TileMap map;
	
	private Animation active;
	private Animation dead;
	private boolean isActive;
	private boolean hasCoin;
	private boolean hasMushroom;
	
	public QuestionBlock(int pixelX, int pixelY, TileMap map, boolean hasCoin,
			boolean hasMushroom) {
		
		// int pixelX, int pixelY, Animation anim, Image img
		super(pixelX, pixelY, null, null);
		
		setIsSloped(false);
		isActive = true;
		this.hasCoin = hasCoin;
		this.hasMushroom = hasMushroom;
		this.map = map;

		TextureRegion q[] = { MarioResourceManager.instance.gameTiles.Question_Block_0, MarioResourceManager.instance.gameTiles.Question_Block_1,
		MarioResourceManager.instance.gameTiles.Question_Block_2, MarioResourceManager.instance.gameTiles.Question_Block_3,
		MarioResourceManager.instance.gameTiles.Question_Block_Dead};
		
		Random r = new Random();
		active = new Animation(r.nextInt(20) + 200).addFrame(q[0]).addFrame(q[1]).addFrame(q[2]).addFrame(q[3]);
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
				MarioSoundManager.instance.playCoin();
				Coin newCoin = new Coin(getPixelX(), getPixelY());
				Score score = new Score(getPixelX(), getPixelY(),0);
				map.creaturesToAdd().add(newCoin);
				map.creaturesToAdd().add(score);
				Settings.addScore(100);
				Settings.addCoins(1);
				newCoin.shoot();
			} else if(hasMushroom) {
				setOffsetY(-10);
				MarioSoundManager.instance.playItemSprout();
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