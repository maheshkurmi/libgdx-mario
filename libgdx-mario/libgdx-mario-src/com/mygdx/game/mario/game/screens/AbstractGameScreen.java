
package com.mygdx.game.mario.game.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;

public abstract class AbstractGameScreen implements Screen, InputProcessor {

	protected AbstractGame game;

	public AbstractGameScreen(AbstractGame game) {
		this.game = game;
		create();
	}

	public abstract void create();

	
	/**
	 * Screen need to process polling and updation of world 
	 * Called if screen is focus(brought to front) and is ready to receive inputs
	 * @param deltaTime
	 */
	public abstract void update (float deltaTime);

	@Override
	public  void render (float deltaTime){
		render();
	}
	
	/**
	 * Continuous rendering of screen as per vertical synchronisation
	 */
	public abstract void render();
	
	public abstract void resize(int width, int height);

	public abstract void show();

	public abstract void hide();

	public abstract void pause();

	public  InputProcessor getInputProcessor(){
		return this;
	}

	public void resume() {
		
		//MarioResourceManager.instance.init();
	}

	public void dispose() {
		//MarioResourceManager.instance.dispose();
	}



	// methods required by InputProcessor interface
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK||keycode==Keys.ESCAPE) {          
            return onBackPressed();
		 }
		return false;
	}

	public boolean keyUp(int keycode) {
		return false;
	}

	public boolean keyTyped(char c) {
		return false;
	}

	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	public boolean scrolled(int amount) {
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	

	/**
	 * Action performed on pressing back key when this screen is active (set as
	 * Current Screen of game) returns true if input is processed
	 */
	public abstract boolean onBackPressed();
}
