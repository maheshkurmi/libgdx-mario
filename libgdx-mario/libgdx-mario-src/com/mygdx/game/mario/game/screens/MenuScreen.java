
package com.mygdx.game.mario.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.MarioSoundManager;
import com.mygdx.game.mario.game.screens.transition.ScreenTransition;
import com.mygdx.game.mario.game.screens.transition.ScreenTransitionSlide;


public class MenuScreen extends AbstractGameScreen {

	protected Stage uiStage;
	protected Table uiTable;

	public final int viewWidth = 640;
	public final int viewHeight = 480;

	/**
	 * Game Menu screen
	 * @param g
	 */
	public MenuScreen(AbstractGame g) {
		super(g);
		MarioSoundManager.instance.play(MarioResourceManager.instance.music.menuMusic);

	}

	@Override
	public void create() {
	    uiStage   = new Stage( new StretchViewport(640,480) );
		uiTable = new Table();
		uiTable.setFillParent(true);
		uiStage.addActor(uiTable);
		 uiTable.background( game.skin.getDrawable("backTex") );
	       
	      Texture titleTex = new Texture(Gdx.files.internal("assets-mario/skin/title.png"), true);
	        titleTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	        Image titleImage = new Image( titleTex );
	        titleImage.setScale(1.5f);
	        titleImage.setSize(titleImage.getWidth(), titleImage.getHeight());
	        Texture logoTex = new Texture(Gdx.files.internal("assets-mario/skin/mario.png"), true);
	        logoTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	        Image logoImage = new Image( logoTex );
	        logoImage.setScale(1.5f);
	        logoImage.setSize(1.5f*logoImage.getWidth(), 1.5f*logoImage.getHeight());
		      
	        
	        TextButton startButton = new TextButton("Play", game.skin, "uiTextButtonStyle");
	        startButton.addListener(
	            new InputListener()
	            {
	                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
	                {  return true;  }  // continue processing?

	                public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
	                {  
	                	ScreenTransition transition =ScreenTransitionSlide.init(2,ScreenTransitionSlide.LEFT,true,Interpolation.swing);	                    
	                	game.setScreen( new WorldScreen(game) ,transition);
	                }
	            });

	        TextButton highScoreButton = new TextButton("HighScore", game.skin, "uiTextButtonStyle");
	        highScoreButton.addListener(
	            new InputListener()
	            {
	                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
	                {  return true;  }  // continue processing?

	                public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
	                {  
	                	HighScoreScreen highScoreScreen = new HighScoreScreen(game);
	            		//ScreenTransition transition = ScreenTransitionFade.init(1);
	            		ScreenTransition transition =ScreenTransitionSlide.init(2,ScreenTransitionSlide.LEFT,true,Interpolation.swing);
		                
	            		game.setScreen(highScoreScreen,transition);
	                }
	            });
	        TextButton helptButton = new TextButton("Help", game.skin, "uiTextButtonStyle");
	        helptButton.addListener(
	            new InputListener()
	            {
	                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
	                {  return true;  }  // continue processing?

	                public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
	                {  
	                	//ScreenTransition transition = ScreenTransitionFade.init(1);
	                	ScreenTransition transition =ScreenTransitionSlide.init(2,ScreenTransitionSlide.LEFT,true,Interpolation.swing);
	                	game.setScreen( new HelpScreen(game) ,transition);
	                }
	            });
	        
	        TextButton quitButton = new TextButton("Quit", game.skin, "uiTextButtonStyle");
	        quitButton.addListener(
	            new InputListener()
	            {
	                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
	                {  return true; 
	                }  // continue processing?

	                public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
	                {  
	                	//ScreenTransition transition =ScreenTransitionSlide.init(2,ScreenTransitionSlide.LEFT,true,Interpolation.swing);
	                	//game.setScreen( new PreferencesScreen(game) ,transition);
	                	Gdx.app.exit();
	                }
	            });

	        
	        float w = highScoreButton.getWidth();
	        
	        uiTable.add(startButton).width(w).left();
	        uiTable.add(titleImage);    
	        uiTable.row();
	        uiTable.add(highScoreButton).width(w).left();
	        uiTable.add().expandX();
	        uiTable.row();
	        uiTable.add(helptButton).width(w).left();
	        uiTable.add().expandX();
	        uiTable.row();
	        uiTable.add(quitButton).width(w).left();
	        uiTable.add(logoImage);
	      
	     
	        uiTable.setFillParent(true);
	        uiTable.padTop(20);
		        
	}
	

	@Override
	public void update(float deltaTime) {
		uiStage.act(deltaTime);
	}


	@Override
	public void render() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		uiStage.draw();
		// Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		uiStage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
		//MarioSoundManager.instance.stopMusic();

	}

	@Override
	public void pause() {
	}

	@Override
	public InputProcessor getInputProcessor() {
		return new InputMultiplexer(uiStage,this);
	}

	@Override
	public boolean onBackPressed() {
		Gdx.app.exit();
		return true;
	}



}
