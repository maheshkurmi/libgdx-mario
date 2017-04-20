

package com.mygdx.game.mario.game.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.screens.transition.ScreenTransition;


public abstract class AbstractGame implements ApplicationListener {
	public Skin skin;
	private boolean init;
	private AbstractGameScreen currScreen;
	private AbstractGameScreen nextScreen;
	private FrameBuffer currFbo;
	private FrameBuffer nextFbo;
	private SpriteBatch batch;
	private float t;
	private ScreenTransition screenTransition;
	public int WIDTH;
	protected int HEIGHT=240;
	
	public AbstractGame(){
	        skin = new Skin();
	  }
	 
	public void setScreen (AbstractGameScreen screen) {
		
		setScreen(screen, null);
	}

	public AbstractGameScreen getScreen () {
		return currScreen;
	}
	
	public void setScreen (AbstractGameScreen screen, ScreenTransition screenTransition) {
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		if (!init) {
			currFbo = new FrameBuffer(Format.RGB888, w, h, false);
			nextFbo = new FrameBuffer(Format.RGB888, w, h, false);
			batch = new SpriteBatch();
			init = true;
		}
		// start new transition
		nextScreen = screen;
		nextScreen.show(); // activate next screen
		nextScreen.resize(w, h);
		nextScreen.render(0); // let next screen update() once
		//if (currScreen != null) currScreen.pause();
		//nextScreen.pause();
		Gdx.input.setInputProcessor(null); // disable input
		this.screenTransition = screenTransition;
		t = 0;
	}

	@Override
	public void render () {
        // get delta time and ensure an upper limit of one 60th second
		float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);
		if (nextScreen == null) {
			// no ongoing transition
			if (currScreen != null) {
				currScreen.update(deltaTime);
				currScreen.render();
			}
		} else {
			// ongoing transition
			float duration = 0;
			if (screenTransition != null) duration = screenTransition.getDuration();
			t = Math.min(t + deltaTime, duration);
			if (screenTransition == null || t >= duration) {
				// no transition effect set or transition has just finished
				if (currScreen != null) currScreen.hide();
				nextScreen.resume();
				// enable input for next screen
				Gdx.input.setInputProcessor(nextScreen.getInputProcessor());
				// switch screens
				currScreen = nextScreen;
				nextScreen = null;
				screenTransition = null;
			} else {
				// render screens to FBOs
				currFbo.begin();
				if (currScreen != null) currScreen.render();
				currFbo.end();
				nextFbo.begin();
				nextScreen.render();
				nextFbo.end();
				// render transition effect to screen
				float alpha = t / duration;
				screenTransition.render(batch, currFbo.getColorBufferTexture(), nextFbo.getColorBufferTexture(), alpha);
			}
		}
	}

	@Override
	public void resize (int width, int height) {
		float h=Gdx.graphics.getHeight();
		float w=Gdx.graphics.getWidth();
		float aspectRaio=w/h;
		//adjust width according to the aspect ratio, using this we can deal with any resolution.
		WIDTH=(int) (HEIGHT*aspectRaio);
		Gdx.app.log("mario"," window width & height (in pixels): " + w + ", " + h);

		Gdx.app.log("mario"," window width & height (in tiles): " + WIDTH + ", " + HEIGHT);
		
		if (currScreen != null) currScreen.resize(width, height);
		if (nextScreen != null) nextScreen.resize(width, height);
	}

	@Override
	public void pause () {
		if (currScreen != null) currScreen.pause();
	}

	@Override
	public void resume () {
		//setScreen(new SplashScreen(this,currScreen));
		MarioResourceManager.instance.init();
		if (currScreen != null) currScreen.resume();
	}

	@Override
	public void dispose () {
		if (currScreen != null) currScreen.hide();
		if (nextScreen != null) nextScreen.hide();
		if (init) {
			currFbo.dispose();
			currScreen = null;
			nextFbo.dispose();
			nextScreen = null;
			batch.dispose();
			init = false;
		}
		MarioResourceManager.instance.dispose();
	}


}
