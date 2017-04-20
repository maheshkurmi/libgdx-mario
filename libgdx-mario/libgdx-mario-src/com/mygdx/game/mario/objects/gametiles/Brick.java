package com.mygdx.game.mario.objects.gametiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioSoundManager;
import com.mygdx.game.mario.Settings;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.objects.GameTile;
import com.mygdx.game.mario.objects.creatures.Coin;
import com.mygdx.game.mario.objects.creatures.Mushroom;
import com.mygdx.game.mario.objects.creatures.Score;
import com.mygdx.game.mario.objects.mario.Mario;
import com.mygdx.game.mario.objects.particles.ParticleSystem;

/**
 * Breakable bricks
 * @author maheshkurmi
 *
 */
public class Brick extends GameTile {

	private TileMap map;
	
	private int numCoins;
	private boolean hasMushroom;
	
	public Brick(int pixelX, int pixelY, TileMap map, TextureRegion img,int numCoins,
			boolean hasMushroom) {
		// int pixelX, int pixelY, Animation anim, Image img
		super(pixelX, pixelY, null,img);
	
		setIsSloped(false);
		this.numCoins = numCoins;
		this.hasMushroom = hasMushroom;
		this.map = map;
	}
	
	@Override
	public void update(int time) {
		//super.update(time);
		if(getOffsetY() != 0) { setOffsetY(getOffsetY() + 2); }
	}
	
	@Override
	public void doAction() {

		if (numCoins > 0) {
			numCoins--;
			setOffsetY(-10);
			MarioSoundManager.instance.playCoin();
			Coin newCoin = new Coin(getPixelX(), getPixelY());
			Score score = new Score(getPixelX(), getPixelY(),0);
			map.creaturesToAdd().add(newCoin);
			map.creaturesToAdd().add(score);
			Settings.addScore(100);
			Settings.addCoins(1);
			newCoin.shoot();
		} else if (hasMushroom) {
			setOffsetY(-10);
			MarioSoundManager.instance.playItemSprout();
			Mushroom shroom = new Mushroom(getPixelX(), getPixelY() - 26,((Mario)map.getPlayer()).isFireMan());
			map.creaturesToAdd().add(shroom);
			Settings.addScore(100);
		} else {
			if (((Mario)map.getPlayer()).isSmall()){
				setOffsetY(-10);
				MarioSoundManager.instance.playKick();
			}else{
				MarioSoundManager.instance.playBrickShatter();
				map.particleSystem = new ParticleSystem(getPixelX(), getPixelY(), 6);
				map.getTiles()[getPixelX() >> 4][getPixelY() >> 4] = null;
				Settings.addScore(100);
			}
		}

	}
}