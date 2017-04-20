package com.shikhar.mario.objects.tiles;

import java.awt.image.BufferedImage;

import com.shikhar.mario.core.Settings;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.creatures.Coin;
import com.shikhar.mario.objects.creatures.Mushroom;
import com.shikhar.mario.objects.creatures.Score;
import com.shikhar.mario.objects.mario.Mario;
import com.shikhar.mario.particles.ParticleSystem;



public class Brick extends GameTile {

	private MarioSoundManager22050Hz soundManager;
	private TileMap map;
	
	private int numCoins;
	private boolean hasMushroom;
	
	public Brick(int pixelX, int pixelY, TileMap map, BufferedImage img,MarioSoundManager22050Hz soundManager,int numCoins,
			boolean hasMushroom) {
		super(pixelX, pixelY, null,img);
	
		setIsSloped(false);
		this.numCoins = numCoins;
		this.hasMushroom = hasMushroom;
		this.soundManager = soundManager;
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
			soundManager.playCoin();
			Coin newCoin = new Coin(getPixelX(), getPixelY());
			Score score = new Score(getPixelX(), getPixelY(),0);
			map.creaturesToAdd().add(newCoin);
			map.creaturesToAdd().add(score);
			newCoin.shoot();
		} else if (hasMushroom) {
			setOffsetY(-10);
			soundManager.playItemSprout();
			Mushroom shroom = new Mushroom(getPixelX(), getPixelY() - 26,((Mario)map.getPlayer()).isFireMan());
			map.creaturesToAdd().add(shroom);
		} else {
			if (((Mario)map.getPlayer()).isSmall()){
				setOffsetY(-10);
				soundManager.playKick();
			}else{
				soundManager.playBrickShatter();
				map.particleSystem = new ParticleSystem(getPixelX(), getPixelY(), 6);
				map.getTiles()[getPixelX() >> 4][getPixelY() >> 4] = null;
				Settings.addScore(100);
			}
			// (((Mario)map.getPlayer()).){
			//soundManager.playBrickShatter();
			//   for (int xx = 0; xx < 2; xx++)
             //      for (int yy = 0; yy < 2; yy++)
             //   	   map.creaturesToAdd().add(new BrickParticle(getPixelX()  + xx * 8 + 4, getPixelY()  + yy * 8 + 4,(xx * 2 - 1) * 3, ((yy * 2 - 1) * 3 - 6),10));
			//map.getTiles()[getPixelX() >> 4][getPixelY() >> 4] = null;
			//soundManager.playBrickShatter();
			
			//map.particleSystem = new ParticleSystem(getPixelX(), getPixelY(), 8);
			//map.getTiles()[getPixelX() >> 4][getPixelY() >> 4] = null;
			//Settings.addScore(100);
		}

	}
}