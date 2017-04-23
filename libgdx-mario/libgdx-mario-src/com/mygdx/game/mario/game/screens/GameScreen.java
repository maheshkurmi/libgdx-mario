package com.mygdx.game.mario.game.screens;

import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.mygdx.game.mario.objects.Creature;
import com.mygdx.game.mario.objects.GameTile;
import com.mygdx.game.mario.objects.creatures.Coin;
import com.mygdx.game.mario.objects.creatures.CoinEmitter;
import com.mygdx.game.mario.objects.creatures.CreatureEmitter;
import com.mygdx.game.mario.objects.creatures.Latiku;
import com.mygdx.game.mario.objects.creatures.Platform;
import com.mygdx.game.mario.objects.mario.Mario;

/**
 * Screen which actually holds game data and updates and renders games
 * @author maheshkurmi
 *
 */
public class GameScreen extends AbstractGameScreen implements InputProcessor {
	enum GameState {
		Ready, Running, SwitchLevel,Paused,GameOver
	}

	GameState state = GameState.Ready;

	// Variable Setup
	int livesLeft = 1;
	private static Mario mario;
	private TileMap map;
	private TileMap backgroundMap;
	private TileMap foregroundMap;
	private GameRenderer renderer;
 
	public int period = 20;
	boolean pauseDrawn = false;
	boolean readyDrawn = false;
	private boolean isSystemDriven=false;
	/** to simulate key right key left using accelerometer */
	private int eventID = 0;
    /**time in seconds  */
    private float timeRemaining=0;
	private float switchTime=0;
	private float switchTimeInitial=1;
	private boolean lockInputs=true;
	private boolean savedScores=false;
	private SpriteBatch batch;
	OrthographicCamera camera;
	private String msg="";

   public GameScreen(AbstractGame game) {
		super(game);
		Settings.load();
		if (mario==null){
			Gdx.app.log("Mario","New Mario Created" +state);
			mario = new Mario();
		}
		batch=new SpriteBatch();
		renderer = new GameRenderer(game);
		loadGame();	
		Settings.setPlayer(mario);
		lockInputs=false;
		state = GameState.Running;
		camera= new OrthographicCamera();
		camera.setToOrtho(true, game.WIDTH, game.HEIGHT);
		camera.update();
	}

    @Override
    public  void resize (int width, int height){
    	camera.setToOrtho(true, game.WIDTH, game.HEIGHT);
		camera.update();
    }

	@Override
	public void render () {
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.SwitchLevel)
			drawSwitchGameUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();
		batch.end();
	}


	private void drawSwitchGameUI(){
		Color c=batch.getColor();
		batch.setColor(0,0,0,switchTime/switchTimeInitial);
		renderer.draw(batch, map, backgroundMap, foregroundMap,
				game.WIDTH, game.HEIGHT);
		batch.setColor(c);
	}
	@Override
	public void show () {
		Settings.load();
		resume();
		float r=(float) Math.random();
		if(r>0.5f){
			MarioSoundManager.instance.play(MarioResourceManager.instance.music.gameMusic_1);
		}else{
			MarioSoundManager.instance.play(MarioResourceManager.instance.music.gameMusic_2);
		}
	}

	@Override
	public void hide () {
		MarioSoundManager.instance.stopMusic();
		pause();
	}



	@Override
	public InputProcessor getInputProcessor () {
		return this;
	}

	private void loadNextGame() {
		MarioSoundManager.instance.playswitchScreen();

		Settings.setLives(3);
		mario.resetHealth();
		if(Settings.level==3){
			Settings.world++;
			if(Settings.world>3){
				Settings.world=3;
			}else{
				Settings.level=1;
			}
		}else{
			Settings.level++;
		}
		
    	loadGame();
		switchTime=10;
		switchTimeInitial=12;

    }
	
	private void loadPrevGame() {
		MarioSoundManager.instance.playswitchScreen();

		Settings.setLives(3);
		mario.resetHealth();
		if(Settings.level==1){

			Settings.world--;
			if(Settings.world<1){
				Settings.world=1;
			}else{
				Settings.level=3;
			}
		}else{
			Settings.level--;
		}
		
    	loadGame();
		switchTime=8;
		switchTimeInitial=10;

    }
    private void reLoadGame(int beginX) {
    	MarioSoundManager.instance.playswitchScreen();

    	msg="";
    	
    	try {
    		GameLoader.reLoadMap(map,"assets-mario/maps/world"+Settings.world+"/map"+Settings.level+"/map3.txt",
					MarioSoundManager.instance,beginX);
	    	Settings.setLives(mario.getHealth());
	    	if (Settings.world!=4)timeRemaining=(map.getWidth()-beginX)/2+5;
	    	Settings.setTime((int) timeRemaining);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   

    	Settings.setLives(mario.getHealth());
    	switchTime=10;
		switchTimeInitial=12;

    }

	public void loadGame() {
		readyDrawn = false;
		lockInputs=true;
		isSystemDriven=true;
		
		try {
	
			Gdx.app.log("D","assets-mario/maps/world"+Settings.world+"/map"+Settings.level+"/map3.txt");
			map=GameLoader.loadMap("assets-mario/maps/world"+Settings.world+"/map"+Settings.level+"/map3.txt",
					MarioSoundManager.instance); // use the ResourceManager
			int w= game.WIDTH-map.getWidth()*16;
			if (w>0){
				w=w/16+1;
				TileMap tempMap=new TileMap(map.getWidth()+w,map.getHeight());
				GameLoader.reLoadMap(tempMap,"assets-mario/maps/world"+Settings.world+"/map"+Settings.level+"/map3.txt",
						MarioSoundManager.instance,0);
				for (int i= map.getWidth();i<tempMap.getWidth();i++){
					for (int j=0;j<map.getHeight();j++){
					  tempMap.setTile(i, j, map.getTile(map.getWidth()-1, j));	
					}
				}
				for(int i = 0; i < map.creatures().size(); i++) { 
					if((map.creatures().get(i) instanceof CoinEmitter) ||(map.creatures().get(i) instanceof Latiku) ||(map.creatures().get(i) instanceof CreatureEmitter)){
						tempMap.creatures().add(map.creatures().get(i));	 
			         } 
				}
				map=tempMap;
				
			}
			backgroundMap = GameLoader.loadOtherMaps("assets-mario/maps/world"+Settings.world+"/map"+Settings.level+"/back3.txt");
			foregroundMap = GameLoader.loadOtherMaps("assets-mario/maps/world"+Settings.world+"/map"+Settings.level+"/fore3.txt");
			MarioResourceManager.loadBackground(GameLoader.backGroundImageIndex);
			renderer.setBackground(MarioResourceManager.gameBackground);
				
			Settings.resetScores();
			
			Gdx.app.log("Mario","begin new stage" +state);
			if (mario!=null && mario.getHealth()>=0){
				mario.Reset();
				Gdx.app.log("Mario","Reset Mario" +state);
			}else{
				mario.Reset();
				mario.resetHealth();
			}
			
			timeRemaining=((Settings.world==4)?25:0.5f)*map.getWidth();
			eventID = 0;
			map.setPlayer(mario); // set the games main player to mario
			Settings.setPlayer(mario);
	    	Settings.setLives(mario.getHealth());
	    	Settings.setTime((int) timeRemaining);
			//state = GameState.Ready;
			//isSystemDriven=true;
		} catch (IOException e) {
			System.out.println("Invalid Map.");
			Gdx.app.log("Errrr", "invalid map");
		}
		
	}


	@Override
	public void update(float deltaTime) {
	    // We have four separate update methods
		// Depending on the state of the game, we call different update methods.
		if (state == GameState.Ready){
			updateReady(deltaTime);
		}else if (state == GameState.Running){
			updateRunning(deltaTime);
		}else if (state == GameState.Paused){
			updatePaused();
		}else if (state == GameState.GameOver){
			updateGameOver();
		}
	}

	private void updateReady(float deltaTime) {
		lockInputs=true;
		//updateRunning(0);
	}

	private void updateRunning(float deltaTime) {
		// if (state != GameState.Running)return;
		period=(int) (1000*deltaTime);
		if (isSystemDriven){
			mario.setX(32);
			if (mario.getY()>=140 || Math.abs(mario.getdY())<=0.001){
				isSystemDriven=false;
				lockInputs=false;
				eventID=0;
				switchTime=0;
			}else{
				//Log.i("Time:", deltaTime+"s");
				mario.update(map, period,true);
				//return;
			}
		}
		
		if (!mario.isAlive()) {
			//msg="Mario Died !";
			if (mario.getY() > map.getHeight() * 16 + mario.getHeight()) {
				msg="";
				if (mario.getHealth() >= 0 && timeRemaining >0) {
					Vector2 p = map.getRecentbookMarkLocation();
					switchTime=6;	
					switchTimeInitial =7;
					lockInputs=true;
					mario.Reset();
					reLoadGame((int) p.x);
					eventID=0;
					mario.setX(TileMap.tilesToPixels(p.x));
					mario.setY(TileMap.tilesToPixels(p.y)
							-mario.getHeight());
					Gdx.app.log("Mario ","died and reset");
				} else {
					state = GameState.GameOver;
					batch.begin();
					renderer.draw(batch,map, backgroundMap,
							foregroundMap, game.WIDTH,
							game.HEIGHT);
					batch.end();
					return;
				}
			}
		}
		
		if (mario.isLevelClear){
			msg="Level Cleared !";

			//save Time
			if (!savedScores){
				Settings.addRecordTime(Settings.world, Settings.level, map.getWidth()/2-(int) timeRemaining);
				savedScores=true;                      
			}
			if (timeRemaining>=-40){
				timeRemaining-=1;              
				if (timeRemaining>=0){ 
					Settings.setTime((int) timeRemaining);
					Settings.addScore(20);
					if ((int)timeRemaining % 5==0)MarioSoundManager.instance.playCoin();
				}
			}else{
				//timeRemaining=0;
				Settings.setTime(0);
				Settings.addHighScore(Settings.world, Settings.level, Settings.getScore());
				game.setScreen(new LevelCompleteScreen(game));//loadNextGame();
				mario.isLevelClear=false;
				lockInputs=true;
				
			}
			if (timeRemaining<0)return;
		}
		
		if (timeRemaining<0){
			if (Settings.world==4){
				Settings.setTime(0);
				
				Settings.level=3;
				mario.killMario();
				state = GameState.GameOver;
				msg="Game Over";
				renderer.draw(batch, map, backgroundMap,
						foregroundMap, game.WIDTH,
						game.HEIGHT);
				GameOver();
				return;
			}
			
			msg="TIME OVER";
			mario.killMario();
			timeRemaining=map.getWidth()/2;
			return;
		}else if (!lockInputs){
			timeRemaining=timeRemaining-deltaTime;
			Settings.setTime((int) timeRemaining);
		}
		
		
		if (switchTime>0){
			switchTime=0.99f*switchTime-deltaTime;
		}

		
		if ( Settings.mUseOrientationForMovement) {
			// 2. Handle accelerometer
			float screenX = Gdx.input.getAccelerometerY();
			// use screenX, screenY, as accelerometer values now!
			int iD = 1;
			if (screenX > 1f) {
				iD = 1; //move right
			} else if (screenX < -1f) {
				iD = -1; //move left
			} else {
				iD = 3; //keep still
			}
			if (eventID != iD) {
				eventID = iD;
				mario.processEvent(eventID,true);
			}

		}
		
		
		for (GameTile tile : map.animatedTiles()) {
			tile.collidingCreatures().clear(); // clear the colliding sprites
			// on the tile
			tile.update(20);
		}

		for (GameTile tile : backgroundMap.animatedTiles()) {
			tile.collidingCreatures().clear(); // clear the colliding sprites on
												// the tile
			tile.update(20);
		}

		for (GameTile tile : foregroundMap.animatedTiles()) {
			tile.collidingCreatures().clear(); // clear the colliding sprites on
												// the tile
			tile.update(20);
		}


		for (int i = 0; i < map.creatures().size(); i++) {

			Creature c = map.creatures().get(i);
			if (!c.isAlive()) {
				map.creatures().remove(i);
				i--;
			} else {
				if (c.isOnScreen() ||c.isAlwaysRelevant()) {
					// Only want to deal with platforms that are awake.
					if (c instanceof Platform) {
						map.platforms().add((Platform) c);
					}
					// Wake up the creature the first time the sprite is
					// in view.
					if (c.isSleeping()) {
						c.wakeUp(c.getX()>mario.getX());
					}
					map.relevantCreatures().add(c);
				} 
			}
		}
		// Update all relevant Creatures.
		for (int i = 0; i < map.relevantCreatures().size(); i++) {
			Creature c = map.relevantCreatures().get(i);
			if (!(c instanceof Coin)) {
				c.updateCreature(map, period);
				mario.playerCollision(map, c);
				for (Creature other : map.relevantCreatures()) {
					c.creatureCollision(other);
				}
				
			} else {
				c.updateCreature(map, period);
				mario.playerCollision(map, c);
			}
		}

		 if (map.particleSystem != null)map.particleSystem.updatePhysics(period);
		// Debugging information:
		// System.out.println("relevant creatures size: " +
		// map.relevantCreatures().size());
		// System.out.println("creatures size: " + map.creatures().size());
		// System.out.println(map.platforms().size());

		// Add creatures that need to be created. They are added here to avoid
		// concurrent modifcation errors.
		for (Creature c : map.creaturesToAdd()) {
			map.creatures().add(c);
		}

		map.creaturesToAdd().clear(); // This line MUST be called BEFORE
										// mario.update(). Why?
										// If it is called after, all the
										// creatures that are created
										// as a result of mario colliding are
										// not added next update because
										// they are cleared immediately
										// afterwards.

		mario.update(map, period);
		Coin.turn.update(period);
		map.relevantCreatures().clear();
		map.platforms().clear();
	}

	
	private void updatePaused() {
		//if(Gdx.input.isTouched()){
			//resume();
		//}
	}

	
	private void updateGameOver() {
		lockInputs=true;
		updateRunning(0);
        render();
	}


	private void drawReadyUI() {
		readyDrawn = true;

		renderer.draw(batch, map, backgroundMap, foregroundMap, game.WIDTH, game.HEIGHT);
		renderer.drawOverLay(new Color(0, 0, 0, 0.5f),batch);
		GameRenderer.drawSmallString(batch, "TAP TO BEGIN THE GAME ..",Color.WHITE, game.WIDTH / 2, game.HEIGHT / 2-16, 0);
	}

	private void drawRunningUI() {
	    renderer.draw(batch,map, backgroundMap, foregroundMap,game.WIDTH, game.HEIGHT);	
	
		if (switchTime<2){
			lockInputs=false;
	    }
		if(switchTime>0) {
			float alpha = Interpolation.fade.apply(switchTime/switchTimeInitial);
			renderer.drawOverLay(new Color(0, 0, 0, alpha),batch);
	    }	
			
		if (msg.length()>0)GameRenderer.drawStringDropShadow(batch, msg,game.WIDTH/2,game.HEIGHT/2-16 ,0);
		
	}

	private void drawPausedUI() {
		renderer.draw(batch,map, backgroundMap, foregroundMap,
				game.WIDTH, game.HEIGHT);
		//g.drawARGB((int) (42*switchTime), 0, 0, 0);
		renderer.drawOverLay(new Color(0, 0, 0, 0.5f),batch);
		GameRenderer.drawNormalString(batch,"PAUSED!",Color.YELLOW,game.WIDTH/2,game.HEIGHT/2-16,0);
		GameRenderer.drawSmallString(batch,"TAP TO RESUME, PRESS BACK TO QUIT",Color.WHITE,game.WIDTH/2,game.HEIGHT/2+10,0);
	}

	private void drawGameOverUI() {
		renderer.draw(batch,map, backgroundMap, foregroundMap,
				game.WIDTH, game.HEIGHT);
		//g.drawARGB((int) (42*switchTime), 0, 0, 0);
		renderer.drawOverLay(new Color(0, 0, 0, 0.5f),batch);
		GameRenderer.drawBigString(batch,"Game Over!",Color.YELLOW,game.WIDTH/2,game.HEIGHT/2-16,0);
		GameRenderer.drawNormalString(batch,"TAP TO RETURN TO MAIN MENU",Color.WHITE,game.WIDTH/2,game.HEIGHT/2+10,0);
	}

	private void GameOver() {
		if (Settings.world==4){
			Settings.addHighScore(4, 1, Settings.getScore());
			Settings.addRecordTime(4, 1, 10000);
		}
		state = GameState.Running;
		isSystemDriven=false;
		lockInputs=false;
		mario.resetHealth();
		goToMenu();
		return;
   	}	
	
	
	@Override
	public void resume() {
		//game begins when user taps the screen
		if (state == GameState.Paused){
			//state = GameState.Ready;
			//lockInputs=false;
		}
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	private boolean inBounds(Vector2 p, int x, int y, int width,
			int height) {

		if (p.x > x && p.x < x + width - 1 && p.y > y
				&& p.y < y + height - 1)
			return true;
		else
			return false;
	}
	
	
	public static Mario getMario(){
		return mario;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		super.keyDown(keycode);
	
		if(keycode==Keys.ESCAPE)return true;
		if(state==GameState.Running){
			mario.keyPressed(keycode);
			if(keycode==Keys.R)reLoadGame((int) (mario.getdX()/16));
			if(keycode==Keys.N)loadNextGame();
			if(keycode==Keys.P)loadPrevGame();
		} if(state==GameState.Paused && keycode==Keys.SPACE){
			resume();
		}else if(state==GameState.GameOver && keycode==Keys.SPACE){
			GameOver();
		}else if(state==GameState.Ready && keycode==Keys.SPACE){
			state = GameState.Running;
			isSystemDriven=false;
			updateRunning(0);
			render();
			lockInputs=false;
		}
			
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		super.keyUp(keycode);
		if(state==GameState.Running)mario.keyReleased(keycode);
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
		
		if(state==GameState.GameOver){
			GameOver();
		}else if(state==GameState.Running){
			processTouchEvent(screenX,screenY,pointer);
		}else if(state==GameState.Paused||state==GameState.Ready){
			state = GameState.Running;
			isSystemDriven=false;
			updateRunning(0);
			render();
			lockInputs=false;
			MarioSoundManager.instance.playPause();
			
		}
	
		return true;
	}

	private int touchDownLeftPointer=-1, touchDownRightPointer=-1;

	private void processTouchEvent(int screenX, int screenY, int pointer){
		Vector2 p=new Vector2(screenX,screenY);//Gdx.input.getX(),Gdx.input.getY());
		//assign some shortcuts to skip screen
		if (inBounds(p, 0,0, 60, 60)){
			loadPrevGame();
		}else if  (inBounds(p, Gdx.graphics.getWidth()-60,0, 60, 60)){
			loadNextGame();
		}
		
		//process on screen movement button clicks
		p=new Vector2(p.x*game.HEIGHT/Gdx.graphics.getHeight(),p.y*game.WIDTH/Gdx.graphics.getWidth());
		if (Settings.mUseOnScreenControls) {
			if (inBounds(p, 5,game.HEIGHT-54, 48, 48)){
				mario.keyPressed(Keys.LEFT);
				touchDownLeftPointer=pointer;
				return;
			}else if  (inBounds(p, game.WIDTH-53,game.HEIGHT-54, 48, 48)){
				mario.keyPressed(Keys.RIGHT);	
				touchDownRightPointer=pointer;
				return;
			}
		}
		//check for jump or fire
		p=new Vector2(screenX,screenY);
		if(mario.isFireMan()&& p.y>Gdx.graphics.getHeight()-120){
			mario.keyPressed(Keys.CONTROL_LEFT);
		}else{
			mario.keyPressed(Keys.SPACE);
		}
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (Settings.mUseOnScreenControls) {
			if(pointer==touchDownLeftPointer)mario.keyReleased(Keys.LEFT);
			if(pointer==touchDownRightPointer)mario.keyReleased(Keys.RIGHT);
		}
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
		if (state == GameState.Running) {
			state=GameState.Paused;
			lockInputs=true;
		}
	}
	
	@Override
	public boolean onBackPressed() {
		//pause();
		if (switchTime>0||isSystemDriven||mario.isSystemDriven())return false;
		if (state == GameState.Running) {
			pause();
			MarioSoundManager.instance.playPause();
				//showPauseDialog();
		} else  {
			goToMenu();
		}
		return true;
	}
	
	private void goToMenu() {
		MenuScreen mainMenuScreen = new MenuScreen(game);
		ScreenTransition transition = ScreenTransitionFade.init(1.0f);
		game.setScreen(mainMenuScreen,transition);
			//mario=null;
	}
}