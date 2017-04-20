package com.mygdx.game.mario.game.screens;

import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.MarioSoundManager;
import com.mygdx.game.mario.Settings;
import com.mygdx.game.mario.game.GameLoader;
import com.mygdx.game.mario.game.GameRenderer;
import com.mygdx.game.mario.game.TileMap;
import com.mygdx.game.mario.game.screens.transition.ScreenTransition;
import com.mygdx.game.mario.game.screens.transition.ScreenTransitionFade;
import com.mygdx.game.mario.objects.creatures.CoinEmitter;
import com.mygdx.game.mario.objects.creatures.CreatureEmitter;
import com.mygdx.game.mario.objects.creatures.Latiku;
import com.mygdx.game.mario.objects.mario.Mario;

/**
 * Experimental screen not used in game
 * @author maheshkurmi
 *
 */
public class EditGameScreen extends AbstractGameScreen implements InputProcessor {
	// Location of description file for skins
				public static final String SKIN_UI = "assets-mario/skin/uiskin.json";
				public static final String SKIN_ATLAS = "assets-mario/skin/uiskin.atlas";

	private TileMap map;
	private TileMap backgroundMap;
	private TileMap foregroundMap;
	private GameRenderer renderer;
	private SpriteBatch batch;
	OrthographicCamera gameCamera;
	private String msg = "";
	private static Mario mario;
	protected Stage uiStage;
	protected Table uiTable;

	Skin skin;

	
	private final int MARGIN_LEFT=80;
	public EditGameScreen(AbstractGame game) {
		
		super(game);
		skin=new Skin(Gdx.files.internal(SKIN_UI),new TextureAtlas(SKIN_ATLAS));
		if (mario == null) {
			// Gdx.app.log("Mario","New Mario Created" +state);
			mario = new Mario();
		}
		batch = new SpriteBatch();
		renderer = new GameRenderer(game);
		renderer.drawHudEnabled=false;
		loadGame();
		// MarioSoundManager.instance.playStageClear();
		gameCamera = new OrthographicCamera();
		gameCamera.setToOrtho(true, game.WIDTH, game.HEIGHT );
		gameCamera.translate(-MARGIN_LEFT,0);
		gameCamera.update();
	
	    uiStage   = new Stage(new FitViewport(game.WIDTH, game.HEIGHT) );
	   // uiStage.getViewport().setCamera(uiCamera);
	    
		uiTable = new Table();
		//uiTable.setFillParent(true);
		uiStage.addActor(uiTable);
		//uiStage.getViewport().setCamera(camera);
		//initalize stage and all your buttons
		ButtonGroup buttonGroup = new ButtonGroup();
		//next set the max and min amount to be checked
		buttonGroup.setMaxCheckCount(1);
		buttonGroup.setMinCheckCount(0);
		//it may be useful to use this method:
		buttonGroup.setUncheckLast(true); //If true, when the maximum number of buttons are checked and an additional button is checked, the last button to be checked is unchecked so that the maximum is not exceeded.
	    Array<Button> tools=new Array<Button>();
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
	    tools.add(new Button(new Image(MarioResourceManager.instance.icons.NEW), skin, "toggle"));
		
	    buttonGroup.add(tools.toArray(Button.class));
	    
	
	    uiTable.pad(0);
	    uiTable.setFillParent(true);
        for (int i = 0; i < tools.size; i++){
           uiTable.add(tools.get(i));
           // uiTable.add(new Label( "B"+i, skin));
           uiTable.add().expandX();
           uiTable.row();
        }
        
        
	}

	@Override
	public void resize(int width, int height) {
		gameCamera.setToOrtho(true, game.WIDTH, game.HEIGHT );
		gameCamera.translate(-MARGIN_LEFT,0);
		
		uiStage.setViewport(new FitViewport(game.WIDTH, game.HEIGHT) );
		  
		//uiStage.getViewport().update(game.WIDTH, game.HEIGHT ,true) ;
			gameCamera.update();
	}

	@Override
	public void render() {
		// Do not update game world when paused.
		// if (!paused) {
		// Update game world by the time that has passed
		// since last rendered frame.
		// }
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x240 / 255.0f, 0x240 / 255.0f, 0x250/ 255.0f, 0xff / 255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		batch.setProjectionMatrix(gameCamera.combined);

		batch.begin();
		// First draw the game elements.
		renderer.draw(batch, map, backgroundMap, foregroundMap, game.WIDTH-MARGIN_LEFT, game.HEIGHT,true);
		// drawUI();
		batch.end();
		
		uiStage.draw();

	}

	@Override
	public InputProcessor getInputProcessor() {
		InputMultiplexer im = new InputMultiplexer(uiStage, this);
		return im;
	}

	public void loadGame() {
		try {

			Gdx.app.log("D", "assets-mario/maps/world" + Settings.world + "/map" + Settings.level + "/map3.txt");
			map = GameLoader.loadMap("assets-mario/maps/world" + Settings.world + "/map" + Settings.level + "/map3.txt",
					MarioSoundManager.instance); // use the ResourceManager
			int w = game.WIDTH - map.getWidth() * 16;
			if (w > 0) {
				w = w / 16 + 1;
				TileMap tempMap = new TileMap(map.getWidth() + w, map.getHeight());
				GameLoader.reLoadMap(tempMap,
						"assets-mario/maps/world" + Settings.world + "/map" + Settings.level + "/map3.txt",
						MarioSoundManager.instance, 0);
				for (int i = map.getWidth(); i < tempMap.getWidth(); i++) {
					for (int j = 0; j < map.getHeight(); j++) {
						tempMap.setTile(i, j, map.getTile(map.getWidth() - 1, j));
					}
				}
				for (int i = 0; i < map.creatures().size(); i++) {
					if ((map.creatures().get(i) instanceof CoinEmitter) || (map.creatures().get(i) instanceof Latiku)
							|| (map.creatures().get(i) instanceof CreatureEmitter)) {
						tempMap.creatures().add(map.creatures().get(i));
					}
				}
				map = tempMap;

			}
			backgroundMap = GameLoader
					.loadOtherMaps("assets-mario/maps/world" + Settings.world + "/map" + Settings.level + "/back3.txt");
			foregroundMap = GameLoader
					.loadOtherMaps("assets-mario/maps/world" + Settings.world + "/map" + Settings.level + "/fore3.txt");
			MarioResourceManager.loadBackground(GameLoader.backGroundImageIndex);
			renderer.setBackground(MarioResourceManager.gameBackground);
		
			map.setPlayer(mario); // set the games main player to mario
		} catch (IOException e) {
			System.out.println("Invalid Map.");
			Gdx.app.log("Errrr", "invalid map");
		}
	}

	@Override
	public void update(float deltaTime) {
		uiStage.act(deltaTime);
		updateRunning(deltaTime);
	}

	private void updateRunning(float deltaTime) {
		float x = mario.getX();
		float y = mario.getY();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			x += 16;
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			x -= 16;
		 if (Gdx.input.isKeyPressed(Keys.UP))
			y -= 16;
		 if (Gdx.input.isKeyPressed(Keys.DOWN))
			y += 16;
	
		// bound mario to layout
		y = MathUtils.clamp(y, game.HEIGHT / 2, map.getHeight()*16 - game.HEIGHT / 2);
		x = MathUtils.clamp(x, game.WIDTH / 2-MARGIN_LEFT, map.getWidth()*16 - game.WIDTH / 2);

		mario.setX(x);
		mario.setY(y);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	private boolean inBounds(Vector2 p, int x, int y, int width, int height) {

		if (p.x > x && p.x < x + width - 1 && p.y > y && p.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		super.keyDown(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	Vector2 prevPt = new Vector2();

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 v = new Vector3(screenX, screenY, 0);
		v = gameCamera.unproject(v);
		prevPt.set(v.x, v.y);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector3 v = new Vector3(screenX, screenY, 0);
		v = gameCamera.unproject(v);
		Vector2 p = new Vector2(v.x - prevPt.x, v.y - prevPt.y);
		prevPt.set(v.x, v.y);
		float x=mario.getX();
		float y=mario.getY();
		
		x -= p.x;
		y -= p.y;

		// bound camera to layout
		// bound mario to layout
		y = MathUtils.clamp(y, game.HEIGHT / 2, map.getHeight()*16 - game.HEIGHT / 2);
		x = MathUtils.clamp(x, game.WIDTH / 2-MARGIN_LEFT, map.getWidth()*16 - game.WIDTH / 2);

		
		mario.setX(x);
		mario.setY(y);
		// camera.update();

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
		// pause()
		goToMenu();

		return true;
	}

	private void goToMenu() {
		MenuScreen mainMenuScreen = new MenuScreen(game);
		ScreenTransition transition = ScreenTransitionFade.init(1.0f);
		game.setScreen(mainMenuScreen, transition);
		// mario=null;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

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