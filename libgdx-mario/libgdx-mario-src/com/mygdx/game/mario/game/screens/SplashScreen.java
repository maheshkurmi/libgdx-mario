package com.mygdx.game.mario.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.game.GameRenderer;

/**
 * Splash screen shows progress % of asset loading
 * @author maheshkurmi
 *
 */
public class SplashScreen extends AbstractGameScreen {

  
    SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    AbstractGameScreen nextScreen;
	OrthographicCamera camera;

    public SplashScreen(AbstractGame game){
    	this(game,null);
    }
        
    
    public SplashScreen(AbstractGame game, AbstractGameScreen nextScreen ){
    	super(game);
     	this.nextScreen=nextScreen;
    	
    	this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
    	camera= new OrthographicCamera();//game.WIDTH, game.HEIGHT);//Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());
		camera.setToOrtho(true, game.WIDTH, game.HEIGHT);
		camera.update();

    }

    @Override
    public void render() {
       
        if(MarioResourceManager.instance.assetManager.update()){
        	MarioResourceManager.instance.createAssets();
        	Gdx.app.log("Splash Screen", "Assets are Loaded!");
    		// Start game at menu screen
     		//ScreenTransition transition = ScreenTransitionFade.init(1f);
           	if(nextScreen==null)nextScreen=new MenuScreen(game);
    		game.setScreen(nextScreen);
       }else {
    		Gdx.gl.glClearColor(0 / 255.0f, 0 / 255.0f, 0 / 255.0f, 0 / 255.0f);
    		// Clears the screen
    		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        	batch.setProjectionMatrix(camera.combined);
        	batch.enableBlending();
        	batch.begin();
        	GameRenderer.drawNormalString(batch, "LOADING GAME  ....",Color.ORANGE,game.WIDTH/2, game.HEIGHT/2-20,0 );
        	batch.end();

         	Gdx.gl20.glLineWidth(1f);
        	shapeRenderer.setProjectionMatrix(camera.combined);
        	shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        	shapeRenderer.setColor(Color.GREEN);
        	int w=(int) (game.WIDTH*0.9);
        	shapeRenderer.rect( game.WIDTH/2-w/2, game.HEIGHT/2, w , 10);
        	shapeRenderer.end();
        	
        	shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        	shapeRenderer.setColor(Color.GREEN);
        	shapeRenderer.rect( game.WIDTH/2-w/2, game.HEIGHT/2, w * MarioResourceManager.instance.assetManager.getProgress(), 10);
        	shapeRenderer.end();
       
     }
     
    }

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	 @Override
	  public  void resize (int width, int height){
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
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onBackPressed() {
		Gdx.app.exit();
		return false;
	}


	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
