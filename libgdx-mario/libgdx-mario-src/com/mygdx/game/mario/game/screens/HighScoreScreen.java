package com.mygdx.game.mario.game.screens;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.Settings;
import com.mygdx.game.mario.game.GameRenderer;
import com.mygdx.game.mario.game.screens.transition.ScreenTransition;
import com.mygdx.game.mario.game.screens.transition.ScreenTransitionSlide;

/**
 * High Score rendering screen
 * @author maheshkurmi
 *
 */
public class HighScoreScreen extends AbstractGameScreen {
		float offsetY=0;
	float downY,Y;
	int offsetYMin,offsetYMax;
	private SpriteBatch batch;
	float prevX, prevY;
	OrthographicCamera camera;
	  private boolean dragging=false;
	  
   public HighScoreScreen(AbstractGame g) {
		super(g);
		batch = new SpriteBatch();
		offsetYMax=32;
		offsetYMin=game.HEIGHT-336;
		camera= new OrthographicCamera();//game.WIDTH, game.HEIGHT);//Gdx.graphics.getWidth(), -Gdx.graphics.getHeight());
		camera.setToOrtho(true, game.WIDTH, game.HEIGHT);
		camera.update();
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
        
		game.setScreen(mainMenuScreen,transition);
	}

	@Override
	public void update(float deltaTime) {
		if (!dragging){
			if (offsetY>0){
				offsetY--;
			}else if (offsetY<0){
				offsetY++;
			}
		}
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(screenX<60 && screenY<60){
			goToMenu();
			return true;
		}
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
		int x,y;
		batch.draw(MarioResourceManager.guiBackground, 0,0,game.WIDTH,game.HEIGHT );		
		GameRenderer.drawSmallString(batch,"HIGHSCORE",Color.LIME,16*8,(int) (offsetY+8),-1);
		GameRenderer.drawSmallString(batch,"RECORD TIME (s)",Color.ORANGE,29*8,(int) (offsetY+8),-1);
		
		for (int i=1; i<=4;i++){
			y=(int) (offsetY+8*(10*(i-1)+2));
			x=2*8;
			GameRenderer.drawStringDropShadow(batch,"WORLD-"+i,x,y,-1);
			GameRenderer.drawStringDropShadow(batch,"------------------------------------------------------------------------------------------------------------------------------------------",x,y+8,-1);
			
			for (int j=1; j<=3;j++){
				if(i==4 &&j>1)continue;
				x=4*8;
				y=(int) (offsetY+8*(10*(i-1)+3+2*j));
				GameRenderer.drawSmallString(batch,"Level-"+j,Color.WHITE,x,y,-1);
				x=18*8;
				GameRenderer.drawSmallString(batch,Settings.getHighScore(i, j)==0?"-":Settings.getHighScore(i, j)+"",Color.LIME,x,y,-1);
				x=32*8;
				GameRenderer.drawSmallString(batch,Settings.getRecordTime(i, j)==10000 ?"-":Settings.getRecordTime(i, j)+"",Color.ORANGE,x,y,-1);
			}
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