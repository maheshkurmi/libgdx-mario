package com.mygdx.game.mario.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.GameRenderer;
import com.mygdx.game.mario.game.screens.transition.ScreenTransition;
import com.mygdx.game.mario.game.screens.transition.ScreenTransitionSlide;

/**
 * Help Info
 * @author maheshkurmi
 *
 */
public class HelpScreen extends AbstractGameScreen {
	float offsetY = 0;
	float downY, Y;
	int offsetYMin, offsetYMax;
	private SpriteBatch batch;
	float prevX, prevY;
	OrthographicCamera camera;
	private boolean dragging = false;

	String[] help_Items;
	String[] info_Items;

	public HelpScreen(AbstractGame g) {
		super(g);
		batch = new SpriteBatch();
		offsetYMax = 32;
		offsetYMin = -32;
		camera = new OrthographicCamera();// game.WIDTH,
											// game.HEIGHT);//Gdx.graphics.getWidth(),
											// -Gdx.graphics.getHeight());
		camera.setToOrtho(true, game.WIDTH, game.HEIGHT);
		camera.update();
		help_Items = new String[5];
		help_Items[0] = "Use Arrow keys (or tilt phone screen) to move Mario";
		help_Items[1] = "Use Space (tap on screen) to jump";
		help_Items[2] = "Use Control (or tap on lower part of screen) to fire if you have";
		help_Items[3] = "Collect star at end of stage to complete stage";
		help_Items[4] = "To complete level, get star before time gets over";
		info_Items = new String[4];
		info_Items[0] = "Developer : Mahesh Kurmi";
		info_Items[1] = "Licence     : None (use anyway you want, at your own risk) ";
		info_Items[2] = "Credits     : Libgdx and many tutorials freely availbale on web";
		info_Items[3] = "Website    : https://simphy.com/apps-by-mahesh-kurmi";

	}

	@Override
	public InputProcessor getInputProcessor() {
		return this;
	}

	public void create() {

	}

	private void goToMenu() {
		MenuScreen mainMenuScreen = new MenuScreen(game);
		//ScreenTransition transition = ScreenTransitionFade.init(1.5f);
		ScreenTransition transition =ScreenTransitionSlide.init(2,ScreenTransitionSlide.LEFT,true,Interpolation.swing);
        
		game.setScreen(mainMenuScreen, transition);
	}

	@Override
	public void update(float deltaTime) {
		if (!dragging) {
			if (offsetY > 0) {
				offsetY--;
			} else if (offsetY < 0) {
				offsetY++;
			}
		}
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		prevY = screenY*game.HEIGHT/(Gdx.graphics.getHeight()+0.0f);
		dragging = true;
		return true;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		offsetY = offsetY +screenY*game.HEIGHT/(Gdx.graphics.getHeight()+0.0f) - prevY;
		if (offsetY < offsetYMin)
			offsetY = offsetYMin;
		if (offsetY > offsetYMax)
			offsetY = offsetYMax;
		prevY = screenY*game.HEIGHT/(Gdx.graphics.getHeight()+0.0f);
		dragging = true;
		return true;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		dragging = false;
		offsetY=(int)offsetY;
		return true;
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawHighscoreTable();
		batch.end();
	}

	private void drawHighscoreTable() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		int x, y;
		x = 16;
		y = (int) (offsetY + 8);
		batch.draw(MarioResourceManager.guiBackground, 0, 0, game.WIDTH, game.HEIGHT);
		GameRenderer.drawNormalString(batch, "HOW TO PLAY", Color.LIME, x, y, -1);
		y += 16;
		GameRenderer.drawStringDropShadow(batch,
				"------------------------------------------------------------------------------------------------------------------------------------------",
				x, y, -1);
		y += 12;
		for (int i = 0; i < help_Items.length; i++) {
			x = 2 * 16;
			GameRenderer.drawSmallString(batch, (i + 1) + ". " + help_Items[i], Color.WHITE, x, y, -1);
			y += 16;
		}
		y += 8;
		x = 16;
		GameRenderer.drawNormalString(batch, "ABOUT GAME", Color.LIME, x, y, -1);
		y += 16;
		GameRenderer.drawStringDropShadow(batch,
				"------------------------------------------------------------------------------------------------------------------------------------------",
				x, y, -1);
		y += 12;
		for (int i = 0; i < info_Items.length; i++) {
			x = 2 * 16;
			GameRenderer.drawSmallString(batch, info_Items[i], Color.WHITE, x, y, -1);
			y += 16;
		}

	}

	@Override
	public boolean onBackPressed() {
		goToMenu();
		return true;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(true, game.WIDTH, game.HEIGHT);
		camera.update();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}