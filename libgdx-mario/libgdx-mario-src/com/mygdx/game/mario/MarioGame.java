package com.mygdx.game.mario;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mygdx.game.mario.game.screens.AbstractGame;
import com.mygdx.game.mario.game.screens.SplashScreen;

/**
 * Game class to manage screens and initialize resources
 * @author maheshkurmi
 *
 */
public class MarioGame extends AbstractGame
{
	
	@Override
	public void create () {
		initSkin();
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		float h=Gdx.graphics.getHeight();
		float w=Gdx.graphics.getWidth();
		Gdx.app.log("mario"," window width & height (in pixels): " + w + ", " + h);
		float aspectRaio=w/h;
		//adjust width according to the aspect ratio, using this we can deal with any resolution.
		WIDTH=(int) (HEIGHT*aspectRaio);
		
		Gdx.input.setCatchBackKey(true);
		// Load assets
		MarioResourceManager.instance.init();
		setScreen(new SplashScreen(this));

		// Load preferences for audio settings and start playing music
		Settings.load();
	}
	
	private void initSkin(){
		          
        Texture backTex = new Texture(Gdx.files.internal("assets-mario/skin/background.png"), true);
        backTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        skin.add( "backTex", backTex );

		BitmapFont uiFont = new BitmapFont(Gdx.files.internal("assets-mario/skin/cooper.fnt"));
        uiFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        uiFont.getData().setScale(0.75f);
        skin.add("uiFont", uiFont);

     
        // Label style
        
        LabelStyle uiLabelStyle = new LabelStyle(uiFont, Color.WHITE);

        skin.add("uiLabelStyle", uiLabelStyle);

        // TextButton style

        TextButtonStyle uiTextButtonStyle = new TextButtonStyle();

        uiTextButtonStyle.font      = uiFont;
        uiTextButtonStyle.fontColor = Color.ORANGE;
        
        Texture upTex = new Texture(Gdx.files.internal("assets-mario/skin/ninepatch-1.png"));
        skin.add("buttonUp", new NinePatch(upTex, 26,26,16,20));
        uiTextButtonStyle.up = skin.getDrawable("buttonUp");
        
        Texture overTex = new Texture(Gdx.files.internal("assets-mario/skin/ninepatch-2.png"));
        skin.add("buttonOver", new NinePatch(overTex, 26,26,16,20) );
        uiTextButtonStyle.over = skin.getDrawable("buttonOver");
        uiTextButtonStyle.overFontColor = Color.GREEN;
        
        Texture downTex = new Texture(Gdx.files.internal("assets-mario/skin//ninepatch-3.png"));
        skin.add("buttonDown", new NinePatch(downTex, 26,26,16,20) );        
        uiTextButtonStyle.down = skin.getDrawable("buttonDown");
        uiTextButtonStyle.downFontColor = Color.GREEN;		

        skin.add("uiTextButtonStyle", uiTextButtonStyle);

 	}
}
