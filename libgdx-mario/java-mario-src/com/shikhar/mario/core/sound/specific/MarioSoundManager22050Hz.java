package com.shikhar.mario.core.sound.specific;
import javax.sound.sampled.AudioFormat;

import com.shikhar.mario.core.sound.Sound;
import com.shikhar.mario.core.sound.SoundManager;

public class MarioSoundManager22050Hz extends SoundManager {
	
	private static Sound bump, kick, coin, jump, pause, itemSprout,gulp,bonusPoints, healthUp, healthDown,brick_shatter,fireball,die,powerUp,powerDown,stage_clear;

	
	public MarioSoundManager22050Hz(AudioFormat format) {
		super(format);
 		bump = getSound("sounds/bump.wav");
 		kick = getSound("sounds/kick.wav");
 		coin = getSound("sounds/coin.wav");
 		jump = getSound("sounds/jump.wav");
 		pause = getSound("sounds/pause.wav");
 		itemSprout = getSound("sounds/item_sprout.wav");
 		bonusPoints = getSound("sounds/veggie_throw.wav");
 		healthUp = getSound("sounds/power_up.wav");
 		healthDown = getSound("sounds/power_down.wav");
 		brick_shatter=getSound("sounds/brick_shatter.wav");
 		fireball=getSound("sounds/fireball.wav");
 		die=getSound("sounds/fireball.wav");
 		powerUp=getSound("sounds/power_up.wav");
		powerDown=getSound("sounds/power_down.wav");
		stage_clear=getSound("sounds/win_stage.wav");

 	}
	
	public void playHealthUp() {
		play(healthUp);
	}
	
	public void playHealthDown() {
		play(healthDown);
	}
	
	public void playBonusPoints() {
		play(bonusPoints);
	}
	
	public void playItemSprout() {
		play(itemSprout);
	}
	
	public void playCoin() {
		play(coin);
	}
	
	public void playKick() {
		play(kick);
	}
	
	public void playBump() {
		play(bump);
	}
	
	public void playJump() {
		play(jump);
	}
	
	public void playPause() {
		play(pause);
	}
	
	public void playBrickShatter() {
		play(brick_shatter);
	}

	public void playFireBall() {
		play(fireball);
	}
	
	public void playDie() {
		play(die);
	}
	
	public void playPowerUp() {
		play(powerUp);
	}
	public void playPowerDown() {
		play(powerDown);
	}
	
	public void playStageClear() {
		play(stage_clear);
	}
	
	public void playGulp() {
		play(bump);
	}
	
}
