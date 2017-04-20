package com.shikhar.mario.core.animation;

import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;



public class CollidableObject extends Sprite {
	
	protected MarioSoundManager22050Hz soundManager;
	private boolean isCollidable;
	private boolean isOnScreen;
	
	public CollidableObject(int pixelX, int pixelY, MarioSoundManager22050Hz soundManager) {
		super(pixelX, pixelY);
		this.isCollidable = true;
		setIsOnScreen(false);
		this.soundManager = soundManager;
	}
	
	public CollidableObject(int pixelX, int pixelY) {
		this(pixelX, pixelY, null);
	}
	
	public boolean isCollidable() {
		return isCollidable;
	}
	
	public void setIsCollidable(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}
	
	public boolean isOnScreen() {
		return isOnScreen;
	}
	
	public void setIsOnScreen(boolean isOnScreen) {
		this.isOnScreen = isOnScreen;
	}
}
