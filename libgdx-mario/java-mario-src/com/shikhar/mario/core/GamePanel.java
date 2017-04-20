package com.shikhar.mario.core;
/**
 * GamePanel extends Jpanel. Contains the main game loop.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.midi.Sequence;
import javax.sound.sampled.AudioFormat;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.shikhar.mario.core.animation.SpriteListener;
import com.shikhar.mario.core.sound.MidiPlayer;
import com.shikhar.mario.core.sound.specific.MarioSoundManager10512Hz;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.objects.creatures.Coin;
import com.shikhar.mario.objects.creatures.CoinEmitter;
import com.shikhar.mario.objects.creatures.CreatureEmitter;
import com.shikhar.mario.objects.creatures.Latiku;
import com.shikhar.mario.objects.mario.Mario;


public class GamePanel extends JPanel implements Runnable {

	private Graphics dbg;
	private Image dbImage = null;

	private boolean running = false; 
	private boolean gameOver = false;
	private boolean gameFreeze = false;
	
	  private boolean isSystemDriven=false;
	    /**time in seconds  */
	    private float timeRemaining=0;
		private boolean lockInputs=true;
		private String msg="";
		private boolean savedScores=false;

	private Thread animator;
	private int period = 20; 
	
	private Mario mario;
	private TileMap map;
	private TileMap backgroundMap;
	private TileMap foregroundMap;
	private GameRenderer renderer;
	private GameLoader gameloader;
	
	
	private MidiPlayer player;
	private MarioSoundManager22050Hz SM_22050_Hz;
	public static MarioSoundManager10512Hz SM_10512_Hz;
	final int numTilesX=32;
	final int numTilesY=16;
	public String mapDir="";
	public GamePanel(int w, int h, String mapDir) {
		
		SM_22050_Hz = new MarioSoundManager22050Hz(new AudioFormat(22050, 8, 1, true, true));
		SM_10512_Hz = new MarioSoundManager10512Hz(new AudioFormat(10512, 8, 1, true, true));
 		mario = new Mario(SM_22050_Hz);
		
 		if (mapDir==null || mapDir.isEmpty())mapDir="java-mario-src/"+"maps";
 		this.mapDir=mapDir;
		
 		loadGame();
 		/*
 		try {
			gameloader = new GameLoader();
			renderer = new GameRenderer();
			map = gameloader.loadMap(mapDir+File.separatorChar+"map3.txt", SM_22050_Hz); // use the ResourceManager to load the game map
			backgroundMap = gameloader.loadOtherMaps(mapDir+File.separatorChar+"back3.txt");
			foregroundMap = gameloader.loadOtherMaps(mapDir+File.separatorChar+"fore3.txt");
			renderer.setBackground(ImageIO.read(new File("backgrounds/background"+gameloader.getBackGroundImageIndex() +".png")));
			map.setPlayer(mario); // set the games main player to mario
		} catch (IOException e){
			System.out.println("Invalid Map.");
		}
		*/
		player = new MidiPlayer();
		Sequence sequence;
		Random r = new Random();
		int rNum = r.nextInt(4);
		if(rNum == 0) {
			sequence = player.getSequence("sounds/smwovr2.mid");
	       // player.play(sequence, true);
	       
		} else if(rNum == 1) {
			sequence = player.getSequence("sounds/smwovr2.mid");
	       // player.play(sequence, true);
		} else if(rNum == 2) {
			sequence = player.getSequence("music/smb_hammerbros.mid");
	       // player.play(sequence, true);
		} else if(rNum == 3) {
			sequence = player.getSequence("music/smrpg_nimbus1.mid");
	       // player.play(sequence, true);
		}
		
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		this.addKeyListener(new SpriteListener(mario));
		this.addKeyListener(new GameListener());
		this.setFocusable(true); 
	}
	
		
	/**
	 * Automatically called as GamePanel is being added to its enclosing GUI component,
	 * and so is a good place to initiate the animation thread.
	 */
	public void addNotify() {
		super.addNotify(); // creates the peer
		startGame(); // start the thread
	}
	
	/**
	 * Start the game thread.
	 */
	private void startGame() {
		if(animator == null || !running) {
			animator = new Thread(this, "The Animator V 3.0");
			animator.start();
		}
	}
	
	/**
	 * Stop the game.
	 */
	public void stopGame() { running = false; }
	
	/**
	 * Defines a single game loop.
	 */
	public void gameAction() {
		
		/*
		if (!mario.isAlive()){
			player = new MidiPlayer();
			Sequence sequence=player.getSequence("music/mario_dies.mid");
			player.play(sequence, true);
			gameUpdate(); // Update game state.
			gameRender(); // Draw to the double buffer.
			paintScreen(); // Draw double buffer to screen.
			stopGame();
		}
		*/
		if(!gameFreeze) gameUpdate(); // Update game state.
		gameRender(); // Draw to the double buffer.
		paintScreen(); // Draw double buffer to screen.
	}
	
	/**
	 * The main game loop - repeatedly update, repaint, sleep.
	 */
	public void run() {
		
		running = true;
		while(running) {
			gameAction();
						
			try {
				Thread.sleep(period);
			} catch(InterruptedException ex){}
		}
		//this.set // so enclosing JFrame/JApplet exits
	}
	 
	/**
 	 * Update the state of all game objects. In the future this game logic
	 * should probably be abstracted out of this class.
	 */
	private void gameUpdate() {
		//updateRunning(period/1000.0f);
		if(mario.isLevelClear){
			gameOver=true;
			msg="Level Completed !!";
			stopGame();
			return;
		}
		if (timeRemaining<0){
			mario.killMario();	
			msg="TIME OVER";
			gameOver=true;
			stopGame();
			//timeRemaining=map.getWidth()/2;
			return;
		}else{
			timeRemaining=timeRemaining-period/1000.0f;
			Settings.setTime((int) timeRemaining);
		}
		
			
		if (!mario.isAlive()){
			//player = new MidiPlayer();
			
			if(!isSystemDriven){
				//this.SM_10512_Hz.playDie();
				//SM_22050_Hz
				/*
				isSystemDriven=true;
				player=new MidiPlayer();
			    Sequence sequence=player.getSequence("music/mario_dies.mid");
				player.play(sequence, true);
				*/
			}
			
			
			//msg="Mario Died !";
			if (mario.getY() > map.getHeight() * 16 + mario.getHeight()) {
				msg="";
				if (mario.getHealth() >= 0 && timeRemaining >0) {
					Point p = map.getRecentbookMarkLocation();
					lockInputs=true;
					mario.Reset();
					reLoadGame(p.x);
					//eventID = 0;
					mario.setX(GameRenderer.tilesToPixels(p.x));
					mario.setY(GameRenderer.tilesToPixels(p.y)
							-mario.getHeight());
					//Log.i("Mario ","died and reset");
					//Settings.setLives(mario.getHealth())
					//mario.update(map, period, true);
					//return;
				} else {
					gameOver=true;
					msg="Game Over";
					stopGame();
					return;
				}
			}
			
			//stopGame();
		}else{
			isSystemDriven=false;
		}
		
		
		if (!gameOver ) {
			
			for(GameTile tile : map.animatedTiles()) {
	           tile.collidingCreatures().clear();  // clear the colliding sprites on the tile
	            tile.update(20);
			}
        
			for(GameTile tile : backgroundMap.animatedTiles()) {
	            tile.collidingCreatures().clear();  // clear the colliding sprites on the tile
	            tile.update(20);
			}
        
			for (GameTile tile : foregroundMap.animatedTiles()) {
				tile.collidingCreatures().clear(); // clear the colliding sprites on the tile
				tile.update(20);
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

			// Debugging information:
			// System.out.println("relevant creatures size: " +
			// map.relevantCreatures().size());
			// System.out.println("creatures size: " + map.creatures().size());
			// System.out.println(map.platforms().size());

			// Add creatures that need to be created. They are added here to
			// avoid concurrent modifcation errors.
			for (Creature c : map.creaturesToAdd()) {
				map.creatures().add(c);
			}
            
            map.creaturesToAdd().clear(); // This line MUST be called BEFORE mario.update(). Why?
            							  // If it is called after, all the creatures that are created
            							  // as a result of mario colliding are not added next update because
            							  // they are cleared immediately afterwards.

			mario.update(map, period);
			Coin.turn.update(period);
			map.relevantCreatures().clear();
			map.platforms().clear();
		}
	}
	
	/**
	 * Draws the game image to the buffer.
	 */
	private void gameRender() {
		if(dbImage == null) {
			dbImage = createImage(numTilesX*16, numTilesY*16);
			return;
		}
	    dbg = dbImage.getGraphics();    
		renderer.draw((Graphics2D) dbg, map, backgroundMap, foregroundMap, dbImage.getWidth(null), dbImage.getHeight(null),false);
		if(gameFreeze == true){
			dbg.setColor(new Color(0,0,0,120));
			dbg.fillRect(0, 0, dbImage.getWidth(null), dbImage.getHeight(null));
			dbg.setColor(Color.white);
			dbg.drawString("Paused: Press Z to Continue", dbImage.getWidth(null)/2-65, dbImage.getHeight(null)/2);
		}else if(gameOver){
			dbg.setColor(new Color(0,0,0,120));
			dbg.fillRect(0, 0, dbImage.getWidth(null), dbImage.getHeight(null));
			dbg.setColor(Color.white);
			dbg.drawString(msg+": Press R to Retry", dbImage.getWidth(null)/2-65, dbImage.getHeight(null)/2);

		}

	}
	
	/**
	 * Draws the game image to the screen by drawing the buffer.
	 */
	private void paintScreen() {	
		Graphics g;
		try {
			g = this.getGraphics();
			if ((g != null) && (dbImage != null))  {
				g.drawImage(dbImage, 0, 0,this.getWidth(),this.getHeight(), null);
				g.dispose();
			} 
		} catch (Exception e) { System.out.println("Graphics context error: " + e); }
	}
	
	/**
	 * Adds debugging features so it is possible to single step a game loop one by one.
	 * 'Z' pauses the game.
	 * 'X' resumes the game.
	 * '1' runs a single game loop if the game if paused.
	 * 'L' runs a single game loop if pressed and continously runs the game loop if held.
	 */
	class GameListener extends KeyAdapter {
		
	    public void keyReleased(KeyEvent e) {
	    	int key = e.getKeyCode();
			
	    	// 'Z' is pressed.
	        if (key == KeyEvent.VK_Z) { // pause
	        	gameFreeze = !gameFreeze;
	        	GamePanel.this.SM_22050_Hz.playPause();
	        
	        	/*
	        	if(GamePanel.this.gameFreeze == false) {
		        	GamePanel.this.gameFreeze = true;
		        	//GamePanel.this.player.setPaused(true);
		        	
	        	}else{
	        		GamePanel.this.gameFreeze = false;
	        		//if( GamePanel.this.player.isPaused())GamePanel.this.player.setPaused(false);
	        	}
	        	GamePanel.this.SM_22050_Hz.playPause();
	        	*/
	        }
	        
	        // '1' is pressed.
	        if (key == KeyEvent.VK_R) {
	        	gameOver=false;
	        	running=true;
	        	gameFreeze=false;
	        	Point p = map.getRecentbookMarkLocation();
				lockInputs=false;
				isSystemDriven=false;
				
				mario.Reset();
				mario.resetHealth();
			
				reLoadGame(p.x);
				
				mario.setX(GameRenderer.tilesToPixels(p.x));
				mario.setY(GameRenderer.tilesToPixels(p.y)
						-mario.getHeight());
				timeRemaining=((Settings.world==4)?25:0.5f)*map.getWidth();
				
				if(animator !=null){
					animator.stop();
					animator = new Thread(GamePanel.this, "The Animator V 3.0");
					animator.start();
				}
	        
	       }
	        
	        // '1' is pressed.
	        if (key == KeyEvent.VK_1) {
	        	if(GamePanel.this.gameFreeze == true) {
	        		System.out.println();
	        		System.out.println("Game Update (1) Starting...");
	        		GamePanel.this.gameAction();
	        		System.out.println();
	        		System.out.println("Game Update (1) Completed.");
	        	}
	        }

	    } 
	    
	    // 'L' is pressed or held.
	    public void keyPressed(KeyEvent e) {
	    	int key = e.getKeyCode();
	    	if (key == KeyEvent.VK_L) {
	    		GamePanel.this.gameAction();
	    	}

	    }
		
	}
	
	private void updateRunning( float deltaTime) {
		// if (state != GameState.Running)return;
		//period=(int) (1000*deltaTime);
		if (isSystemDriven){
			//mario.setY(mario.getY()+0.1f);
			//mario.setdY(0);
			mario.setX(32);
			if (mario.getY()>=140 || Math.abs(mario.getdY())<=0.001){
				isSystemDriven=false;
				lockInputs=false;
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
					Point p = map.getRecentbookMarkLocation();
					lockInputs=true;
					mario.Reset();
					reLoadGame(p.x);
					//eventID = 0;
					mario.setX(GameRenderer.tilesToPixels(p.x));
					mario.setY(GameRenderer.tilesToPixels(p.y)
							-mario.getHeight());
					//Log.i("Mario ","died and reset");
					//Settings.setLives(mario.getHealth())
					//mario.update(map, period, true);
					//return;
				} else {
					//gameOver=true;
					msg="Game Over";
					stopGame();
					/*
					renderer.draw(gameCanvas, map, backgroundMap,
							foregroundMap, frameBuffer.getWidth(),
							frameBuffer.getHeight());
					(game.getGraphics()).drawARGB(170, 0, 10, 0);
					//game.setScreen(new GuiLevelFailedScreen(game));
					 * 
					 */
					return;
				}
			}
		}
		
		if (mario.isLevelClear){
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
					if ((int)timeRemaining % 5==0)this.SM_22050_Hz.playCoin();
				}
			}else{
				//timeRemaining=0;
				Settings.setTime(0);
				msg="Level Cleared !";
				Settings.addHighScore(Settings.world, Settings.level, Settings.getScore());
				//((AndroidGame) game).setScreenWithFade(new LevelCompleteScreen(game));
				mario.isLevelClear=false;
				lockInputs=true;
			}
			if (timeRemaining<0)return;
		}
		
		if (timeRemaining<0){
			if (Settings.world==4){
				Settings.setTime(0);
				msg="Congratulations!! You have completed the Level";
				Settings.level=3;
				mario.killMario();
				//state = GameState.GameOver;
				msg="Game Over";
				//gameOver=true;
				//renderer.draw(gameCanvas, map, backgroundMap,
				//		foregroundMap, frameBuffer.getWidth(),
				//		frameBuffer.getHeight());
				//(game.getGraphics()).drawARGB(170, 0, 10, 0);
				//game.setScreen(new GuiLevelFailedScreen(game));
				return;
			}
			mario.killMario();	
			msg="TIME OVER";
			timeRemaining=map.getWidth()/2;
			return;
		}else if (!lockInputs){
			timeRemaining=timeRemaining-deltaTime;
			if(!lockInputs)Settings.setTime((int) timeRemaining);
		}
		
		/*
		if (switchTime>0){
			switchTime=0.99f*switchTime-deltaTime;
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
		*/
	}

    private void reLoadGame(int beginX) {
    	gameloader = new GameLoader();
    	try {
    		gameloader.reLoadMapFromFile(map,mapDir+File.separatorChar+"map3.txt", SM_22050_Hz,beginX); // use the ResourceManager to load the game map
    		
			//gameloader.reLoadMap(map,"maps/world"+Settings.world+"/map"+Settings.level+"/map3.txt",
			//		((MarioGame) game).soundManager,beginX);
	    	Settings.setLives(mario.getHealth());
	    	if (Settings.world!=4)timeRemaining=(map.getWidth()-beginX)/2+5;
	    	Settings.setTime((int) timeRemaining);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   // use the ResourceManager
    	msg="";
    	Settings.setLives(mario.getHealth());

    }

	
	public void loadGame() {
		lockInputs=true;
		//lockUpdates=true;
		isSystemDriven=true;
		try {
			gameloader = new GameLoader();
			renderer = new GameRenderer();
			map = gameloader.loadMapFromFile(mapDir+File.separatorChar+"map3.txt", SM_22050_Hz); // use the ResourceManager to load the game map
			
			int w= this.getWidth()-map.getWidth()*16;
			if (w>0){
				w=w/16+1;
				TileMap tempMap=new TileMap(map.getWidth()+w,map.getHeight());
				gameloader.reLoadMapFromFile(tempMap,mapDir+File.separatorChar+"map3.txt",
						SM_22050_Hz,0);
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
			backgroundMap = gameloader.loadOtherMaps(mapDir+File.separatorChar+"back3.txt");
			foregroundMap = gameloader.loadOtherMaps(mapDir+File.separatorChar+"fore3.txt");
			renderer.setBackground(ImageIO.read(new File("java-mario-src/backgrounds/background"+gameloader.getBackGroundImageIndex() +".png")));
				
			Settings.resetScores();
			if (mario!=null && mario.getHealth()>=0){
				mario.Reset();
			}else{
				
				mario.Reset();
				mario.resetHealth();
				
			}
			
			timeRemaining=((Settings.world==4)?25:0.5f)*map.getWidth();
			map.setPlayer(mario); // set the games main player to mario
			Settings.setPlayer(mario);
	    	Settings.setLives(mario.getHealth());
	    	Settings.setTime((int) timeRemaining);
			//state = GameState.Ready;
			//isSystemDriven=true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Invalid Map." +mapDir+File.separatorChar+"map3.txt");
		}
		msg="";
		
	}
}
