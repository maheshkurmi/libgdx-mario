package com.mygdx.game.mario.game.screens;

import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
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
import com.mygdx.game.mario.game.screens.transition.ScreenTransitionSlide;
import com.mygdx.game.mario.objects.Creature;
import com.mygdx.game.mario.objects.GameTile;
import com.mygdx.game.mario.objects.creatures.Coin;
import com.mygdx.game.mario.objects.creatures.Platform;
import com.mygdx.game.mario.objects.mario.Mario;

/**
 * World chooser screen
 * @author maheshkurmi
 *
 */
public class WorldScreen extends AbstractGameScreen {

	private  Mario mario;
	private TileMap map;
	private TileMap backgroundMap;
	private TileMap foregroundMap;
	private GameRenderer renderer;
	public GameLoader gameLoader;
	public int period = 20;
	
	/** to simulate key right key left using accelerometer */
	private int eventID = 0;
	private SpriteBatch batch;
    private boolean isSystemDriven=false;
    /**time in seconds  */
 	private boolean lockUpdates=true;
	private int blink=0;
	private int[] worldLocations;
	OrthographicCamera camera;
	
	public WorldScreen(AbstractGame game) {
		super(game);
		batch=new SpriteBatch();
		renderer = new GameRenderer(game);
		loadGame();
		lockUpdates=false;
		Settings.world=1;
		Settings.level=0;
		camera= new OrthographicCamera();//game.WIDTH, game.HEIGHT);//Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());
		camera.setToOrtho(true, game.WIDTH, game.HEIGHT);
		camera.update();
		//((MarioGame)game).soundManager.loadWorldMusic();
	}

 	public void loadGame() {
  		lockUpdates=true;
	
		try {
			renderer = new GameRenderer(game);
			renderer.setDrawHudEnabled(false);
			//renderer.setBackground(MarioResourceManager.loadImage("backgrounds/gray.png"));
			//MarioResourceManager.Background=MarioResourceManager.loadImage("backgrounds/gray.png");
				//Log.e("D","maps/map3.txt");
			map = GameLoader.loadMap("assets-mario/maps/map3.txt",MarioSoundManager.instance);
			MarioResourceManager.loadBackground(GameLoader.backGroundImageIndex);
			
			renderer.setBackground(MarioResourceManager.guiBackground);

			int w= game.WIDTH-map.getWidth()*16;
			if (w>0){
				w=Math.round(w/16.0f)+1;
				TileMap tempMap=new TileMap(map.getWidth()+w,map.getHeight());
				GameLoader.reLoadMap(tempMap,"assets-mario/maps/map3.txt",MarioSoundManager.instance,0);
				for (int i= map.getWidth();i<tempMap.getWidth();i++){
					for (int j=0;j<map.getHeight();j++){
					  tempMap.setTile(i, j, map.getTile(map.getWidth()-1, j));	
					}
				}
				map=tempMap;
			}
			backgroundMap = GameLoader.loadOtherMaps("assets-mario/maps/back3.txt");
			foregroundMap = GameLoader.loadOtherMaps("assets-mario/maps/fore3.txt");
			Settings.resetScores();
			mario = new Mario();
			mario.setX(48);
			mario.setY(0);
			eventID = 0;
			map.setPlayer(mario); // set the games main player to mario
			worldLocations=new int[map.creatures().size()];
			for (int i=0; i<map.creatures().size();i++){
				worldLocations[i]=(int) map.creatures().get(i).getX();
			}
			
		} catch (IOException e) {
			System.out.println("Invalid Map.");
			//Log.e("Errrr", "invalid map");
		}
		lockUpdates=false;
		//Settings.loadPreferences((((AndroidGame)game)).getSharedPreferences(PreferenceConstants.PREFERENCE_NAME, 0));
	}

   	
	@Override
	public void update(float deltaTime) {
		if (lockUpdates)return;
		updateRunning(deltaTime);
	}
	
	private void updateRunning(float deltaTime) {
		// if (state != GameState.Running)return;
	
		if (isSystemDriven){
			if (mario.getY()>=TileMap.tilesToPixels(map.getHeight())){
				isSystemDriven=false;
			}
			//Log.i("Time:", deltaTime+"s");
			mario.update(map, period,true);
			return;
		}
		
		if (mario.isLevelClear){
			for (int i=0; i<worldLocations.length;i++){
				if (mario.getX()==worldLocations[i]){
					Settings.world=i+1;
					Settings.level=0;
					break;
				}
			}
			LevelCompleteScreen gameScreen = new LevelCompleteScreen(game);
			ScreenTransition transition = ScreenTransitionFade.init(1.0f);
			//ScreenTransition transition =ScreenTransitionSlide.init(2,ScreenTransitionSlide.DOWN,true,Interpolation.sine);
			game.setScreen(gameScreen,transition);
			
			//game.setScreenWithFade(new LevelCompleteScreen(game));
			mario.isLevelClear=false;
			lockUpdates=true;
			return;
		}
		
		if ( Settings.mUseOrientationForMovement) {
			// 2. Handle accelerometer
			float screenX = Gdx.input.getAccelerometerY();
			// use screenX, screenY, as accelerometer values now!
			int iD = 1;
			if (screenX > 1) {
				iD = 1;
			} else if (screenX < -1) {
				iD = -1;
			} else {
				iD = 3;
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
		 renderer.draw(batch,map, backgroundMap, foregroundMap,game.WIDTH, game.HEIGHT);	
		
		GameRenderer.drawStringDropShadowAsEntity(batch, "WORLD-1",  worldLocations[0]+8, 100,0);
		GameRenderer.drawStringDropShadowAsEntity(batch, "WORLD-2",  worldLocations[1]+8, 100,0);
		GameRenderer.drawStringDropShadowAsEntity(batch, "WORLD-3",  worldLocations[2]+8, 100,0);
		GameRenderer.drawStringDropShadowAsEntity(batch, "WORLD-4",  worldLocations[3]+8, 100,0);
		
		blink++;
		if (blink>=30){
			GameRenderer.drawStringDropShadow(batch, "JUMP & GET STAR TO ENTER WORLD....... ",game.WIDTH/2, 16,0);
		}
		if (blink==80)blink=0;
	}


	@Override
	public boolean keyDown(int keycode) {
		super.keyDown(keycode);
		if(keycode==Keys.ESCAPE)return true;
		mario.keyPressed(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		super.keyUp(keycode);
		mario.keyReleased(keycode);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		//mario.keyReleased(keycode);
		// TODO Auto-generated method stub
		return false;
	}

	private int touchDownLeftPointer=-1, touchDownRightPointer=-1;

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 p=new Vector2(Gdx.input.getX(),Gdx.input.getY());
		
		p=new Vector2(p.x*game.HEIGHT/Gdx.graphics.getHeight(),p.y*game.WIDTH/Gdx.graphics.getWidth());
		if (Settings.mUseOnScreenControls) {
			if (inBounds(p, 5,game.HEIGHT-54, 48, 48)){
				mario.keyPressed(Keys.LEFT);
				touchDownLeftPointer=pointer;
				return true;
			}else if  (inBounds(p, game.WIDTH-53,game.HEIGHT-54, 48, 48)){
				mario.keyPressed(Keys.RIGHT);	
				touchDownRightPointer=pointer;
				return true;
			}
		}
	    p=new Vector2(Gdx.input.getX(),Gdx.input.getY());
			if(mario.isFireMan()&& p.y>Gdx.graphics.getHeight()-120){
				mario.keyPressed(Keys.CONTROL_LEFT);
			}else{
				mario.keyPressed(Keys.SPACE);
			}
	
		return true;
	}

	
	private boolean inBounds(Vector2 p, int x, int y, int width,
			int height) {

		if (p.x > x && p.x < x + width - 1 && p.y > y
				&& p.y < y + height - 1)
			return true;
		else
			return false;
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
		
	}
	
	@Override
	public boolean onBackPressed() {
		//pause();
		goToMenu();
		return true;
	}
	
	private void goToMenu() {
		MenuScreen mainMenuScreen = new MenuScreen(game);
		//ScreenTransition transition =ScreenTransitionSlide.init(2,ScreenTransitionSlide.UP,true,Interpolation.sine);
		ScreenTransition transition =ScreenTransitionSlide.init(2,ScreenTransitionSlide.LEFT,true,Interpolation.swing);
	    
		//ScreenTransition transition = ScreenTransitionFade.init(1.0f);
		game.setScreen(mainMenuScreen,transition);
			//mario=null;
	}

	@Override
	public void create() {
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

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return this;
	}

	
}