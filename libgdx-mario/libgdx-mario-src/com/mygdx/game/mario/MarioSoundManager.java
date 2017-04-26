package com.mygdx.game.mario;


import java.util.Random;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


 /** Manages sound and musics for the game
 * @author mahesh
 *
 */
public class MarioSoundManager{
	
	private Music playingMusic;
	private  Sound playingSound;


	public static final MarioSoundManager instance = new MarioSoundManager();
	// singleton: prevent instantiation from other classes
	private MarioSoundManager () {
	}
	
	public void play (Sound sound) {
		if (!Settings.soundEnabled ||sound==null) return;
		if(playingSound!=null)playingSound.stop();
		sound.play(Settings.volSound );
		playingSound=sound;

	}

	public void play (Music music) {
		stopMusic();
		playingMusic = music;
		if (Settings.musicEnabled) {
			music.setLooping(true);
			music.setVolume(Settings.volMusic);
			music.play();
		}
	}

	public void stopMusic () {
		if (playingMusic != null) playingMusic.stop();
	}

	public Music getPlayingMusic () {
		return playingMusic;
	}

	public void onSettingsUpdated () {
		if (playingMusic == null) return;
		playingMusic.setVolume(Settings.volMusic);
		if (Settings.musicEnabled) {
			if (!playingMusic.isPlaying()) playingMusic.play();
		} else {
			playingMusic.pause();
		}
	}
	

	public void playHealthUp() {
		play(MarioResourceManager.instance.sounds.healthUp);
	}
	
	public void playHealthDown() {
		play(MarioResourceManager.instance.sounds.healthDown);
	}
	
	public void playBonusPoints() {
		play(MarioResourceManager.instance.sounds.bonusPoints);
	}
	
	public void playItemSprout() {
		play(MarioResourceManager.instance.sounds.itemSprout);
	}
	
	public void playCoin() {
		play(MarioResourceManager.instance.sounds.coin);
	}
	
	public void playKick() {
		play(MarioResourceManager.instance.sounds.kick);
	}
	
	public void playGulp() {
		play(MarioResourceManager.instance.sounds.gulp);
	}
	
	public void playStomp() {
		play(MarioResourceManager.instance.sounds.stomp);
	}

	public void playBump() {
		play(MarioResourceManager.instance.sounds.bump);
	}
	
	public void playJump() {
		play(MarioResourceManager.instance.sounds.jump);
	}
	
	public void playPause() {
		play(MarioResourceManager.instance.sounds.pause);
	}

	public void playBrickShatter() {
		play(MarioResourceManager.instance.sounds.brick_shatter);
	}

	public void playFireBall() {
		play(MarioResourceManager.instance.sounds.fireball);
	}
	
	public void playDie() {
		play(MarioResourceManager.instance.sounds.die);
	}
	
	public void playPowerUp() {
		play(MarioResourceManager.instance.sounds.powerUp);
	}
	
	public void playPowerDown() {
		play(MarioResourceManager.instance.sounds.powerDown);
	}
	
	public void playHurt() {
		Random r = new Random();
		int rNum = r.nextInt(2);
		if(rNum == 0) {
			play(MarioResourceManager.instance.sounds.hurt1);
		} else {
			play(MarioResourceManager.instance.sounds.hurt2);
		}
	}
	
	public void playCelebrate() {
		Random r = new Random();
		int rNum = r.nextInt(2);
		if(rNum == 0) {
			play(MarioResourceManager.instance.sounds.yahoo1);
		} else {
			play(MarioResourceManager.instance.sounds.yahoo2);
		}
	}
	
	public void playStageEnter() {
		play(MarioResourceManager.instance.sounds.stage_begin);
	}
	
	public void playStageClear() {
		play(MarioResourceManager.instance.sounds.stage_clear);
	}
	
	public void playswitchScreen() {
		play(MarioResourceManager.instance.sounds.switchScreen);
	}
	
	public void playClick() {
		play(MarioResourceManager.instance.sounds.click);
	}
	

	
	
}
