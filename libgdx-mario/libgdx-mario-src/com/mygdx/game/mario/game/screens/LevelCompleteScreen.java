package com.mygdx.game.mario.game.screens;

import java.io.IOException;
import java.util.ArrayList;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.MarioSoundManager;
import com.mygdx.game.mario.Settings;
import com.mygdx.game.mario.game.GameLoader;
import com.mygdx.game.mario.game.GameRenderer;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.game.screens.transition.ScreenTransition;
import com.mygdx.game.mario.game.screens.transition.ScreenTransitionFade;
import com.mygdx.game.mario.game.screens.transition.ScreenTransitionSlide;
import com.mygdx.game.mario.objects.Creature;
import com.mygdx.game.mario.objects.mario.Mario;

public class LevelCompleteScreen extends AbstractGameScreen {

	private  Mario mario;
	private TileMap map;
	private TileMap backgroundMap;
	private TileMap foregroundMap;
	private GameRenderer renderer;
	public int period = 20;
	
	private SpriteBatch batch;
    private boolean lockInputs=true;
	private int blink=0;
	private Vector2 initialPt,finalPt;
	ArrayList<Vector2> pearls;
	private int pearlSize=10;
	OrthographicCamera camera;

	/**
	 * Screen which appears when level is completed or mario enters new level
	 * It shows overview of stage 
	 * @param game
	 */
	public LevelCompleteScreen(AbstractGame game) {
		super(game);
		batch=new SpriteBatch();
		renderer = new GameRenderer(game);
		Creature.WAKE_UP_VALUE_DOWN_RIGHT=game.WIDTH/8;
		//Settings.loadPreferences((((AndroidGame)game)).getSharedPreferences(PreferenceConstants.PREFERENCE_NAME, 0));
		pearls=new ArrayList<Vector2>();
		pearlSize=MarioResourceManager.instance.gui.pearl1.getRegionWidth();
		lockInputs=true;
		Settings.save();
		loadGame();
		camera= new OrthographicCamera();//game.WIDTH, game.HEIGHT);//Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());
		camera.setToOrtho(true, 2*game.WIDTH, 2*game.HEIGHT);
		camera.translate(0,-game.HEIGHT/2);
		camera.update();
	}

 	public void loadGame() {
  		try {
			renderer = new GameRenderer(game);
			renderer.setDrawHudEnabled(false);
			renderer.setBackground(null);//MarioResourceManager.loadImage("backgrounds/smb.png"));
			//bmpLevel=MarioResourceManager.loadImage("gui/"+Settings.world+".png");
			//Log.e("D","Loading LevelComplete screen ");
			map = GameLoader.loadMap("assets-mario/maps/world"+Settings.world+"/map.txt",MarioSoundManager.instance); 
			//renderer.setBackground(MarioResourceManager.guiBackground);
			mario = new Mario();
			mario.setX(32);
			mario.setY(32);
			map.setPlayer(mario); // set the games main player to mario
			if (Settings.level==0){
				lockInputs=false;
				Vector2 p=map.bookMarks().get(0);
				initialPt=p;
				mario.setX(TileMap.tilesToPixels(p.x));
				mario.setY(TileMap.tilesToPixels(p.y+1)-mario.getHeight());//);
				pearls.add(new Vector2((int)(mario.getX()),(int) mario.getY()));
				finalPt=p;
				return;
			}
			if (map.bookMarks().size()>=Settings.level){
				Vector2 p=map.bookMarks().get(Settings.level-1);
				initialPt=p;
				mario.setX(TileMap.tilesToPixels(p.x));
				mario.setY(TileMap.tilesToPixels(p.y+1)-mario.getHeight());//);
				finalPt=map.bookMarks().get(Settings.level);
				if (Settings.level >= 2) {
					Vector2 p1 = map.bookMarks().get(0);
					Vector2 p2 = map.bookMarks().get(1);

					for (int i = TileMap.tilesToPixels(p1.x); i < TileMap
							.tilesToPixels(p2.x); i += pearlSize) {
						pearls.add(new Vector2(i, TileMap
								.tilesToPixels(p1.y)));
					}
					for (int i = TileMap.tilesToPixels(p1.y); i < TileMap
							.tilesToPixels(p2.y); i += pearlSize) {
						pearls.add(new Vector2(TileMap.tilesToPixels(p2.x),
								i));
					}
				}if (Settings.level >= 3) {
					Vector2 p1 = map.bookMarks().get(1);
					Vector2 p2 = map.bookMarks().get(2);

					for (int i = TileMap.tilesToPixels(p1.x); i < TileMap
							.tilesToPixels(p2.x); i += pearlSize) {
						pearls.add(new Vector2(i, TileMap
								.tilesToPixels(p1.y)));
					}
					for (int i = TileMap.tilesToPixels(p1.y); i < TileMap
							.tilesToPixels(p2.y); i += pearlSize) {
						pearls.add(new Vector2(TileMap.tilesToPixels(p2.x),
								i));
					}
				}
				
				pearls.add(new Vector2((int)(mario.getX()),(int) mario.getY()));//+mario.getHeight()/2)));
			}else{
				finalPt=new Vector2(map.getWidth(),map.getHeight());
			}
			
		} catch (IOException e) {
			System.out.println("Invalid Map.");
			//Log.e("Errrr", "invalid map");
		}	
 	}

   	@Override
	public void update(float deltaTime) {
		updateRunning(deltaTime);
	}
	
	private void updateRunning(float deltaTime) {
		// if (state != GameState.Running)return;
		Vector2 pt=pearls.get(pearls.size()-1);
		if (mario.getX()<TileMap.tilesToPixels(finalPt.x)){
			mario.setX(mario.getX()+2);
			if (mario.getX()-pt.x>pearlSize){
				pearls.add(new Vector2((int) mario.getX(),pt.y));
			}
		}else if(mario.getY()<TileMap.tilesToPixels(finalPt.y+1)-mario.getHeight()){
			mario.setY(mario.getY()+1);
			if (Math.abs(mario.getY()-pt.y)>=pearlSize){
				pearls.add(new Vector2(pt.x,(int) mario.getY()));
			}
		}else{
			
		}

		if (mario.getX()>=TileMap.tilesToPixels(finalPt.x)){
			lockInputs=false;
		}
		

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawRunningUI();
		batch.end();
	}
	
	

	private void drawRunningUI() {

		batch.draw(MarioResourceManager.guiBackground, 0, -game.HEIGHT/2,2*game.WIDTH, 2*game.HEIGHT);
		renderer.draw(batch,map, backgroundMap, foregroundMap,2*game.WIDTH, game.HEIGHT);	

		//batch.draw(MarioResourceManager.guiBackground, 0, 0,game.HEIGHT/2, game.HEIGHT);
		
		//tmpCanvas.translate(0, frameBuffer.getHeight()/2);
		//renderer.draw(tmpCanvas, map, backgroundMap, foregroundMap,
			//	(int) (2*frameBuffer.getWidth()), frameBuffer.getHeight());
		//tmpCanvas.restore();
		float x, y;
		x=game.WIDTH-MarioResourceManager.instance.gui.logo.getRegionWidth()/2;
		y=15-game.HEIGHT/2;
		batch.draw(MarioResourceManager.instance.gui.logo, x, y);
		
		//tmpCanvas.drawBitmap(MarioResourceManager.logo, frameBuffer.getWidth()-MarioResourceManager.logo.getWidth()/2, 15, null);
		//tmpCanvas.drawBitmap(bmpLevel, frameBuffer.getWidth()+MarioResourceManager.logo.getWidth()/2-15, 10, null);
		
		blink++;
		if (blink>=30){
			x=game.WIDTH-MarioResourceManager.instance.gui.TapToStart.getRegionWidth()/2;
			y=2*game.HEIGHT-MarioResourceManager.instance.gui.TapToStart.getRegionHeight()-game.HEIGHT+20;
			batch.draw(MarioResourceManager.instance.gui.TapToStart, x, y);
			//   tmpCanvas.drawBitmap(MarioResourceManager.instance.gui.tTapToStart,x, 2*frameBuffer.getHeight()-2*MarioResourceManager.TapToStart.getHeight(), null);
		}
		
		if (blink==80)blink=0;
		TextureRegion bmp;
		if (blink % 20 <10){
			bmp=MarioResourceManager.instance.gui.pearl1;
		}else{
			bmp=MarioResourceManager.instance.gui.pearl2;
		}
		for (int i=0; i<pearls.size(); i++){
			batch.draw(bmp,pearls.get(i).x+GameRenderer.xOffset,pearls.get(i).y+GameRenderer.yOffset);
		}
		
		batch.draw(MarioResourceManager.instance.creatures.levelComplete, pearls.get(0).x-4+GameRenderer.xOffset,pearls.get(0).y-4);
		batch.draw(MarioResourceManager.instance.creatures.levelComplete, TileMap.tilesToPixels(initialPt.x)-5+GameRenderer.xOffset,TileMap.tilesToPixels(initialPt.y)-15);
	
		//tmpCanvas.drawBitmap(MarioResourceManager.levelComplete, pearls.get(0).x-4+GameRenderer.xOffset,pearls.get(0).y-4+frameBuffer.getHeight()/2, paint);
		//tmpCanvas.drawBitmap(MarioResourceManager.levelComplete, TileMap.tilesToPixels(initialPt.x)-5+GameRenderer.xOffset,TileMap.tilesToPixels(initialPt.y)+frameBuffer.getHeight()/2-15, paint);
	
		Vector2 p=map.bookMarks().get(map.bookMarks().size()-1);
		batch.draw(MarioResourceManager.instance.creatures.levelComplete, TileMap.tilesToPixels(p.x)+GameRenderer.xOffset-MarioResourceManager.instance.creatures.levelComplete.getRegionWidth()/2,TileMap.tilesToPixels(p.y+1)+GameRenderer.yOffset-MarioResourceManager.instance.creatures.levelComplete.getRegionHeight());
		if (Settings.level==3 && lockInputs==false ){
			if (blink>=30) GameRenderer.drawBigString(batch,"CONGRATUATIONS!! ",Color.GREEN,game.WIDTH, 2*game.HEIGHT-6*MarioResourceManager.instance.gui.TapToStart.getRegionHeight(),0);
			GameRenderer.drawNormalString(batch,"You Have Completed World " +Settings.world,Color.ORANGE, game.WIDTH,(int) (2*game.HEIGHT-4.5f*MarioResourceManager.instance.gui.TapToStart.getRegionHeight()-15),0);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		super.keyDown(keycode);
		 if(keycode==Keys.ESCAPE)return  true;
				 
		if(keycode==Keys.SPACE){
			touchDown(0,0,0,0);
			return true;
		}
		mario.keyPressed(keycode);
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		super.keyUp(keycode);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		//mario.keyReleased(keycode);
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (!lockInputs) {
				
			if (Settings.level >= 3) {
				Settings.level =0;
				ScreenTransition transition = ScreenTransitionFade.init(1.0f);
				//ScreenTransition transition =ScreenTransitionSlide.init(2,ScreenTransitionSlide.DOWN,true,Interpolation.fade);
				
				game.setScreen(new WorldScreen(game),transition);
			} else {
				Settings.level++;
				//ScreenTransition transition = ScreenTransitionFade.init(1.0f);
				ScreenTransition transition =ScreenTransitionSlide.init(1,ScreenTransitionSlide.UP,true,Interpolation.sine);
				game.setScreen(new GameScreen(game),transition);
			}
			lockInputs=true;
		}
	
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		//mario.keyReleased(Keys.LEFT);
		//mario.keyReleased(Keys.RIGHT);
		mario.keyReleased(Keys.SPACE);
		mario.keyReleased(Keys.CONTROL_LEFT);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pause() {
	}
	
	@Override
	public boolean onBackPressed() {
		//pause();
		goToMenu();
		return true;
	}
	
	private void goToMenu() {
		if (lockInputs || Settings.level==0)return;
		
		MenuScreen mainMenuScreen = new MenuScreen(game);
		//ScreenTransition transition = ScreenTransitionFade.init(1.0f);
		ScreenTransition transition =ScreenTransitionSlide.init(2,ScreenTransitionSlide.LEFT,true,Interpolation.swing);
	    
		game.setScreen(mainMenuScreen,transition);
		//mario=null;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(true, 2*game.WIDTH, 2*game.HEIGHT);
		camera.translate(0,-game.HEIGHT/2);
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

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return this;
	}
	
}