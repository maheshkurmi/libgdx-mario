package com.mygdx.game.mario.game;



import java.io.IOException;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.MarioSoundManager;
import com.mygdx.game.mario.Settings;
import com.mygdx.game.mario.objects.Creature;
import com.mygdx.game.mario.objects.GameTile;
import com.mygdx.game.mario.objects.creatures.BlueFish;
import com.mygdx.game.mario.objects.creatures.Bowser;
import com.mygdx.game.mario.objects.creatures.Coin;
import com.mygdx.game.mario.objects.creatures.CoinEmitter;
import com.mygdx.game.mario.objects.creatures.CreatureEmitter;
import com.mygdx.game.mario.objects.creatures.FireDisc;
import com.mygdx.game.mario.objects.creatures.FlyGoomba;
import com.mygdx.game.mario.objects.creatures.FlyRedKoopa;
import com.mygdx.game.mario.objects.creatures.Goomba;
import com.mygdx.game.mario.objects.creatures.JumpingFish;
import com.mygdx.game.mario.objects.creatures.Latiku;
import com.mygdx.game.mario.objects.creatures.LevelComplete;
import com.mygdx.game.mario.objects.creatures.Piranha;
import com.mygdx.game.mario.objects.creatures.Platform;
import com.mygdx.game.mario.objects.creatures.RedFish;
import com.mygdx.game.mario.objects.creatures.RedKoopa;
import com.mygdx.game.mario.objects.creatures.RedShell;
import com.mygdx.game.mario.objects.creatures.Spring;
import com.mygdx.game.mario.objects.creatures.Thorny;
import com.mygdx.game.mario.objects.creatures.Virus;
import com.mygdx.game.mario.objects.gametiles.Brick;
import com.mygdx.game.mario.objects.gametiles.Bush;
import com.mygdx.game.mario.objects.gametiles.FireTile;
import com.mygdx.game.mario.objects.gametiles.InfoPanel;
import com.mygdx.game.mario.objects.gametiles.QuestionBlock;
import com.mygdx.game.mario.objects.gametiles.RotatingBlock;
import com.mygdx.game.mario.objects.gametiles.SlopedTile;
import com.mygdx.game.mario.objects.gametiles.Tree;


/**
 * map loader class
 * @author mahesh
 *
 */
public class GameLoader {
	
	/**flag to keep velocity of platforms random*/
	private static boolean togglePlatform_velocity=false;
	public static int backGroundImageIndex=0;
    private static ArrayList<String> infoPanels =new ArrayList<String>(); ;
    
    /**
     * Make it singleton and allow static access only
     */
	private GameLoader() {
		
	}
	

	/**loads a tile map, given a map to load..
     use this to load the background and foreground. Note: the status of the tiles (ie collide etc)
     is irrelevant. Why? I don't check collision on maps other than the main map. 
     **/
    public static TileMap loadOtherMaps(String filename) throws IOException {
		// lines is a list of strings, each element is a row of the map
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;
		int height = 0;
		
		// read in each line of the map into lines
		int infoCount=0;
		infoPanels.clear();
		FileHandle handle = Gdx.files.internal(filename);
	    String text = handle.readString();
	    String lineArray[] = text.split("\\r?\\n");
		//Scanner reader = new Scanner(gameActivity.getAssets().open(filename));
		//while(reader.hasNextLine()) {
	       //String line = reader.nextLine();
	    for (String line:lineArray){
			
			if(!line.startsWith("#")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}else{
				if (line.startsWith("#@")){
					infoPanels.add(line.substring(2).trim());
				}
			}
		}
		height = lines.size(); // number of elements in lines is the height
		int pipeOffsetIndex=(Settings.level==2)?30:96;
		TileMap newMap = new TileMap(width, height);
		for (int y=0; y < height; y++) {
			String line = lines.get(y);
			for (int x=0; x < line.length(); x++) {
				char ch = line.charAt(x);
				int pixelX = TileMap.tilesToPixels(x);
				int pixelY = TileMap.tilesToPixels(y);
				if (ch == 'n') {
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[92]);
				} else if (ch == 'm') {
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[93]);
				} else if (ch == 'a') {
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[90]);
				} else if (ch == 'b') {
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[91]);
				} else if (ch == 'q') { // rock left
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[48]);
				} else if (ch == 'r') { // rock right
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[49]);
				} else if (ch == 'z') { // tree stem
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[75]);
				} else if (ch=='T') {
					Tree t = new Tree(pixelX, pixelY);
					newMap.setTile(x, y, t);
					newMap.animatedTiles().add(t);
				} else if (ch=='}') {
					Bush t = new Bush(pixelX, pixelY);
					newMap.setTile(x, y, t);
					newMap.animatedTiles().add(t);
				} else if (ch == 'V') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[56]);
					newMap.setTile(x, y, t);
				} else if (ch == '5') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[3]);
					newMap.setTile(x, y, t);
				} else if (ch == '3') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[4]);
					newMap.setTile(x, y, t);
				} else if (ch == '6') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[5]);
					newMap.setTile(x, y, t);
				} else if (ch == '4') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[10]);
					newMap.setTile(x, y, t);
				} else if (ch == '2') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[86]);
					newMap.setTile(x, y, t);
				} else if (ch == '{') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[81]);
					newMap.setTile(x, y, t);
				}else if(ch == '9') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.mushroomTree);
					newMap.setTile(x, y, t);
					//newMap.slopedTiles().add(t);
				} else if(ch == '8') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.woodenBridge);
					newMap.setTile(x, y, t);
				} else if(ch == '7') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.waterRock);
					newMap.setTile(x, y, t);
				} else if(ch == 't') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex]); //pipe top left
					newMap.setTile(x, y, t);
				} else if(ch == 'u') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+1]); //pipe top middle
					newMap.setTile(x, y, t);
				} else if(ch == 'v') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+2]); //pipe top right
					newMap.setTile(x, y, t);
				} else if(ch == 'w') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+3]); //pipe base left
					newMap.setTile(x, y, t);
				} else if(ch == 'x') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+4]); //pipe base middle
					newMap.setTile(x, y, t);
				} else if(ch == 'y') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+5]); //pipe base right
					newMap.setTile(x, y, t);
				} else if(ch == 'z') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[75]); //tree stem
					newMap.setTile(x, y, t);
				}else if(ch == 'n') {
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[92]);
				} else if (ch == 'm') {
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[93]);
				} else if (ch == 'a') {
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[90]);
				} else if (ch == 'b') {
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[91]);
				} else if (ch == 'q') { // rock left
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[48]);
				} else if (ch == 'r') { // rock right
					newMap.setTile(x, y, MarioResourceManager.instance.gameTiles.Plain_Tiles[49]);
				} else if(ch == 'g') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[9]);
					newMap.setTile(x, y, t);
				} else if(ch == 'h') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[11]);
					newMap.setTile(x, y, t);
				}else if(ch == '*') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[78]); //pipe base right
					newMap.setTile(x, y, t);
				} else if(ch == '!') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[84]); //pipe base right
					newMap.setTile(x, y, t);    
				} else if(ch == '%') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[36]); //BlueRock 
					newMap.setTile(x, y, t);
				} else if(ch == '|') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[37]); //Yellowrock
					newMap.setTile(x, y, t);
				} else if(ch == '$') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[38]); //greyrock
					newMap.setTile(x, y, t);
				} else if(ch == '-') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[40]); // 
					newMap.setTile(x, y, t);
				} else if(ch == '+') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[46]); //
					newMap.setTile(x, y, t);
				} else if(ch == '~') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[89]); //watertile
					newMap.setTile(x, y, t);
				} else if(ch == '@') {
					InfoPanel t = new InfoPanel(pixelX, pixelY, "Mario"); //pipe base right
					newMap.setTile(x, y, t);
					if(infoCount<infoPanels.size()){
						t.setInfo(infoPanels.get(infoCount));
						infoCount++;
					}
				} else if (ch == '[') {
					FireTile r = new FireTile(pixelX, pixelY);
					newMap.setTile(x, y, r);
					newMap.animatedTiles().add(r);
				} else if (ch == ']') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[25]);
					newMap.setTile(x, y, t);
				} else if (ch == '_') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[28]);
					newMap.setTile(x, y, t);
				} else if (ch == ';') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[26]);
					newMap.setTile(x, y, t);
				} else if (ch == ':') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[27]);
					newMap.setTile(x, y, t);
				}
			}
		}
		return newMap;	
	}

 
    // Use this to load the main map
	public static TileMap loadMap(String filename, MarioSoundManager soundManager) throws IOException {
		// lines is a list of strings, each element is a row of the map
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

		int width = 0;
		int height = 0;
		
		int pipeOffsetIndex=(Settings.level==2)?30:96;
		// read in each line of the map into lines
		FileHandle handle = Gdx.files.internal(filename);
	    String text = handle.readString();
	    String lineArray[] = text.split("\\r?\\n");
		//Scanner reader = new Scanner(gameActivity.getAssets().open(filename));
		//while(reader.hasNextLine()) {
	       //String line = reader.nextLine();
	    for (String line:lineArray){
			line="."+line;
			line=line.trim();
			line=line.substring(1,line.length());
			if(!line.startsWith("#")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}else{
				line=line.substring(1).trim();
				if (line.startsWith("background")) {
					backGroundImageIndex=(Integer.parseInt(line
							.substring(line.length() - 1)));
				} else if (line.startsWith("waterzone")) {
					line=line.substring(10);
					String[] pts = line.split(",");
					{
						int[] x = new int[4];
						for (int i = 0; i <= 3; i++) {
							x[i] = Integer.parseInt(pts[i]);
						}
						rects.add(new Rectangle(x[0], x[1], x[2],  x[3]));
					}
				}
			}
		}
		height = lines.size(); // number of elements in lines is the height
		
		TileMap newMap = new TileMap(width, height);
		for (int y=0; y < height; y++) {
			String line = lines.get(y);
			for (int x=0; x < line.length(); x++) {
				char ch = line.charAt(x);
				
				int pixelX = TileMap.tilesToPixels(x);
				int pixelY = TileMap.tilesToPixels(y);
				// enumerate the possible tiles...
				if (ch == 'G') {
					newMap.creatures().add(new Goomba(pixelX, pixelY));
				} else if (ch == 'K') {
					newMap.creatures().add(new RedKoopa(pixelX, pixelY,false));
				} else if (ch == 'L') {
					newMap.creatures().add(new RedKoopa(pixelX, pixelY,true));
				} else if (ch == 'I') {
					FireDisc f = new FireDisc(pixelX, pixelY);
					newMap.creatures().add(f);
				} else if (ch == 'H') {
					newMap.creatures().add(new Thorny(pixelX, pixelY));
				} else if (ch == 'F') {
					newMap.creatures().add(new FlyRedKoopa(pixelX, pixelY));
				} else if (ch == 'O') {
					newMap.creatures().add(new FlyGoomba(pixelX, pixelY));
				} else if (ch == 'N') {
					newMap.creatures().add(new Bowser(pixelX, pixelY));
				} else if (ch == 'V') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[56]);
					newMap.setTile(x, y, t);
				} else if (ch == 'J') {
					Piranha t = new Piranha(pixelX, pixelY);
					newMap.creatures().add(t);
				} else if (ch == '&') {
					Latiku t = new Latiku(pixelX, pixelY);
					newMap.creatures().add(t);
				} else if (ch == '?') {
					Virus t = new Virus(pixelX, pixelY);
					newMap.creatures().add(t);
				} else if (ch == '(') {
					RedFish t = new RedFish(pixelX, pixelY);
					newMap.creatures().add(t);
				} else if (ch == ')') {
					BlueFish t = new BlueFish(pixelX, pixelY);
					newMap.creatures().add(t);
				} else if (ch == ',') {
					JumpingFish t = new JumpingFish(pixelX, pixelY,Math.random()>0.7f);
					newMap.creatures().add(t);
				} else if (ch == '.') {
					CreatureEmitter t = new CreatureEmitter(pixelX, pixelY);
					newMap.creatures().add(t);
				} else if (ch == '"') {
					CoinEmitter t = new CoinEmitter(pixelX, pixelY);
					newMap.creatures().add(t);
				} else if (ch == 'l') {
					LevelComplete c= new LevelComplete(pixelX, pixelY);
					newMap.creatures().add(c);
				} else if (ch=='B') {
					Brick b = new Brick(pixelX, pixelY,newMap, MarioResourceManager.instance.gameTiles.Plain_Tiles[77],10*(int)(Math.random()*1.1),false);
					newMap.setTile(x, y, b);
					newMap.animatedTiles().add(b);	
				} else if (ch == 'R') {
					RotatingBlock r = new RotatingBlock(pixelX, pixelY);
					newMap.setTile(x, y, r);
					newMap.animatedTiles().add(r);
				} else if (ch == '[') {
					FireTile r = new FireTile(pixelX, pixelY);
					newMap.setTile(x, y, r);
					newMap.animatedTiles().add(r);
				} else if (ch == ']') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[25]);
					newMap.setTile(x, y, t);
				} else if (ch == '_') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[28]);
					newMap.setTile(x, y, t);
				} else if (ch == ';') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[26]);
					newMap.setTile(x, y, t);
				} else if (ch == ':') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[27]);
					newMap.setTile(x, y, t);
				} else if (ch == '5') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[3]);
					newMap.setTile(x, y, t);
				} else if (ch == '3') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[4]);
					newMap.setTile(x, y, t);
				} else if (ch == '6') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[5]);
					newMap.setTile(x, y, t);
				} else if (ch == '4') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[10]);
					newMap.setTile(x, y, t);
				} else if (ch == '2') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[86]);
					newMap.setTile(x, y, t);
				} else if (ch == '{') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[81]);
					newMap.setTile(x, y, t);
				} else if (ch == 'Q') {
					QuestionBlock q = new QuestionBlock(pixelX, pixelY, newMap, true, false);
					newMap.setTile(x, y, q);
					newMap.animatedTiles().add(q);
				} else if (ch == 'W') {
					QuestionBlock q = new QuestionBlock(pixelX, pixelY, newMap, false, true);
					newMap.setTile(x, y, q);
					newMap.animatedTiles().add(q);
				} else if (ch == 'S') {
					newMap.creatures().add(new RedShell(pixelX, pixelY, newMap, true,true));
				} else if(ch == 'C') {
					newMap.creatures().add(new Coin(pixelX, pixelY));
				} else if(ch == 'P') {
					Platform p = new Platform(pixelX, pixelY);
					newMap.creatures().add(p);
				} else if(ch == '<') {
					Platform p = new Platform(pixelX, pixelY,togglePlatform_velocity?0.05f:-0.05f,0,false);
					newMap.creatures().add(p);
					togglePlatform_velocity=!togglePlatform_velocity;
				} else if(ch == '>') {
					Platform p = new Platform(pixelX, pixelY,0,0.05f,false);
					newMap.creatures().add(p);
					//togglePlatform_velocity=!togglePlatform_velocity;
				} else if(ch == '^') {
					Spring s = new Spring(pixelX, pixelY, newMap);
					newMap.creatures().add(s);
				} else if (ch=='T') {
					Tree t = new Tree(pixelX, pixelY);
					newMap.setTile(x, y, t);
					newMap.animatedTiles().add(t);
				} else if (ch=='}') {
					Bush t = new Bush(pixelX, pixelY);
					newMap.setTile(x, y, t);
					newMap.animatedTiles().add(t);
				} else if (ch == 'M') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[82]);
					t.setIsCollidable(false);
					newMap.setTile(x, y, t);
					newMap.addBookMark(new  Vector2(x,y));
				}else if(ch == '9') {
					SlopedTile t = new SlopedTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Sloped_Tile, true);
					newMap.setTile(x, y, t);
					newMap.slopedTiles().add(t);
				} else if(ch == '8') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Grass_Edge);
					newMap.setTile(x, y, t);
				} else if(ch == '7') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Grass_Center);
					newMap.setTile(x, y, t);
				} else if(ch == 't') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex]); //pipe top left
					newMap.setTile(x, y, t);
				} else if(ch == 'u') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+1]); //pipe top middle
					newMap.setTile(x, y, t);
				} else if(ch == 'v') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+2]); //pipe top right
					newMap.setTile(x, y, t);
				} else if(ch == 'w') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+3]); //pipe base left
					newMap.setTile(x, y, t);
				} else if(ch == 'x') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+4]); //pipe base middle
					newMap.setTile(x, y, t);
				} else if(ch == 'y') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+5]); //pipe base right
					newMap.setTile(x, y, t);
				} else if(ch == 'z') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[75]); //tree stem
					newMap.setTile(x, y, t);
				} else if(ch == 'i') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[42]); //
					newMap.setTile(x, y, t);
				} else if(ch == 'j') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[43]); //
					newMap.setTile(x, y, t);
				} else if(ch == 'k') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[44]); //tree stem
					newMap.setTile(x, y, t);
				} else if(ch == 'g') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[9]);
					newMap.setTile(x, y, t);
				} else if(ch == 'h') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[11]);
					newMap.setTile(x, y, t);
				} else if(ch == '%') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[36]); //BlueRock 
					newMap.setTile(x, y, t);
				} else if(ch == '|') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[37]); //Yellowrock
					newMap.setTile(x, y, t);
				} else if(ch == '$') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[38]); //greyrock
					newMap.setTile(x, y, t);
				} else if(ch == '-') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[40]); // 
					newMap.setTile(x, y, t);
				} else if(ch == '+') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[46]); //
					newMap.setTile(x, y, t);
				} else if(ch == '~') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[89]); //watertile
					newMap.setTile(x, y, t);
				}
			}
		}
		for (Rectangle r:rects){
			newMap.addWaterZone(r);
		}
		Creature.map=newMap;
		return newMap;	
	}

	/**
	 * ReLoads map from offset point
	 * @param map map to be reloaded
	 * @param filename path of file wrt asset folder
	 * @param soundManager
	 * @param beginX Starting x (benchmark of player)
	 * @throws IOException
	 */
	public static void reLoadMap(TileMap map,String filename, MarioSoundManager soundManager, int beginX) throws IOException {
		// lines is a list of strings, each element is a row of the map
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;
		int height = 0;
		int pipeOffsetIndex=(Settings.level==2)?30:96;
		// read in each line of the map into lines
		FileHandle handle = Gdx.files.internal(filename);
	    String text = handle.readString();
	    String lineArray[] = text.split("\\r?\\n");
		//Scanner reader = new Scanner(gameActivity.getAssets().open(filename));
		//while(reader.hasNextLine()) {
	       //String line = reader.nextLine();
	    for (String line:lineArray){
			if(!line.startsWith("#")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}
		height = lines.size(); // number of elements in lines is the height
		
		if (width<=beginX || beginX<0)return;
		width=Math.min(map.getWidth(),width);
		height=Math.min(map.getHeight(),height);
		map.creaturesToAdd().clear();
		map.platforms().clear();
		
		for(int i = 0; i < map.creatures().size(); i++) { 
			if (map.creatures().get(i).getX()>TileMap.tilesToPixels(beginX) ) {
				if(!(map.creatures().get(i) instanceof CoinEmitter) && !(map.creatures().get(i) instanceof Latiku) && !(map.creatures().get(i) instanceof CreatureEmitter)){
					map.creatures().remove(i);	 
					i--;
   				}else{
   					map.creatures().get(i).wakeUp(true);
   				}
            } 
		}
			
		for (int y=0; y < height; y++) {
			String line = lines.get(y);
			for (int x=beginX; x < line.length(); x++) {
				char ch = line.charAt(x);
				int pixelX = TileMap.tilesToPixels(x);
				int pixelY = TileMap.tilesToPixels(y);
				// enumerate the possible tiles...
				if (ch == 'G') {
					map.creatures().add(new Goomba(pixelX, pixelY));
				} else if (ch == 'K') {
					map.creatures().add(new RedKoopa(pixelX, pixelY,false));
				} else if (ch == 'L') {
					map.creatures().add(new RedKoopa(pixelX, pixelY,true));
				} else if (ch == 'I') {
					FireDisc f = new FireDisc(pixelX, pixelY);
					map.creatures().add(f);
				} else if (ch == 'H') {
					map.creatures().add(new Thorny(pixelX, pixelY));
				} else if (ch == 'F') {
					map.creatures().add(new FlyRedKoopa(pixelX, pixelY));
				} else if (ch == 'O') {
					map.creatures().add(new FlyGoomba(pixelX, pixelY));
				} else if (ch == 'N') {
					map.creatures().add(new Bowser(pixelX, pixelY));
				} else if (ch == 'V') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[56]);
					map.setTile(x, y, t);
				} else if (ch == 'J') {
					Piranha t = new Piranha(pixelX, pixelY);
					map.creatures().add(t);
				} else if (ch == '?') {
					Virus t = new Virus(pixelX, pixelY);
					map.creatures().add(t);
				} else if (ch == '&') {
					//no need to add latiku since it is always relevent
					//Latiku t = new Latiku(pixelX, pixelY,soundManager,map);
					//map.creatures().add(t);
				} else if (ch == '(') {
					RedFish t = new RedFish(pixelX, pixelY);
					map.creatures().add(t);
				} else if (ch == ')') {
					BlueFish t = new BlueFish(pixelX, pixelY);
					map.creatures().add(t);
				} else if (ch == ',') {
					JumpingFish t = new JumpingFish(pixelX, pixelY,Math.random()>0.7f);
					map.creatures().add(t);
				} else if (ch == 'l') {
					LevelComplete l= new LevelComplete(pixelX, pixelY);
					map.creatures().add(l);
				} else if (ch=='B') {
					Brick b = new Brick(pixelX, pixelY,map, MarioResourceManager.instance.gameTiles.Plain_Tiles[77],10*(int)(Math.random()*1.1),false);
					map.setTile(x, y, b);
					map.animatedTiles().add(b);	
				} else if (ch == 'R') {
					RotatingBlock r = new RotatingBlock(pixelX, pixelY);
					map.setTile(x, y, r);
					map.animatedTiles().add(r);
				} else if (ch == '[') {
					FireTile r = new FireTile(pixelX, pixelY);
					map.setTile(x, y, r);
					map.animatedTiles().add(r);
				} else if (ch == ']') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[25]);
					map.setTile(x, y, t);
				} else if (ch == '_') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[28]);
					map.setTile(x, y, t);
				} else if (ch == ';') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[26]);
					map.setTile(x, y, t);
				} else if (ch == ':') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[27]);
					map.setTile(x, y, t);
				} else if (ch == '5') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[3]);
					map.setTile(x, y, t);
				} else if (ch == '3') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[4]);
					map.setTile(x, y, t);
				} else if (ch == '6') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[5]);
					map.setTile(x, y, t);
				} else if (ch == '4') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[10]);
					map.setTile(x, y, t);
				} else if (ch == '2') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[86]);
					map.setTile(x, y, t);
				} else if (ch == '{') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[81]);
					map.setTile(x, y, t);
				} else if (ch == 'Q') {
					QuestionBlock q = new QuestionBlock(pixelX, pixelY, map, true, false);
					map.setTile(x, y, q);
					map.animatedTiles().add(q);
				} else if (ch == 'W') {
					QuestionBlock q = new QuestionBlock(pixelX, pixelY, map, false, true);
					map.setTile(x, y, q);
					map.animatedTiles().add(q);
				} else if (ch == 'S') {
					map.creatures().add(new RedShell(pixelX, pixelY, map, true,true));
				} else if(ch == 'C') {
					map.creatures().add(new Coin(pixelX, pixelY));
				} else if(ch == 'P') {
					Platform p = new Platform(pixelX, pixelY);
					map.creatures().add(p);
				} else if(ch == '<') {
					Platform p = new Platform(pixelX, pixelY,togglePlatform_velocity?0.05f:-0.05f,0,false);
					map.creatures().add(p);
					togglePlatform_velocity=!togglePlatform_velocity;
				} else if(ch == '>') {
					Platform p = new Platform(pixelX, pixelY,0,0.05f,false);
					map.creatures().add(p);
					//togglePlatform_velocity=!togglePlatform_velocity;
				} else if(ch == '^') {
					Spring s = new Spring(pixelX, pixelY,map);
					map.creatures().add(s);
				} else if (ch=='T') {
					Tree t = new Tree(pixelX, pixelY);
					map.setTile(x, y, t);
					map.animatedTiles().add(t);
				} else if (ch=='}') {
					Bush t = new Bush(pixelX, pixelY);
					map.setTile(x, y, t);
					map.animatedTiles().add(t);
				} else if (ch == 'M') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[82]);
					t.setIsCollidable(false);
					map.setTile(x, y, t);
					map.addBookMark(new  Vector2(x,y));
				}else if(ch == '9') {
					SlopedTile t = new SlopedTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Sloped_Tile, true);
					map.setTile(x, y, t);
					map.slopedTiles().add(t);
				} else if(ch == '8') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Grass_Edge);
					map.setTile(x, y, t);
				} else if(ch == '7') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Grass_Center);
					map.setTile(x, y, t);
				} else if(ch == 't') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex]); //pipe top left
					map.setTile(x, y, t);
				} else if(ch == 'u') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+1]); //pipe top middle
					map.setTile(x, y, t);
				} else if(ch == 'v') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+2]); //pipe top right
					map.setTile(x, y, t);
				} else if(ch == 'w') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+3]); //pipe base left
					map.setTile(x, y, t);
				} else if(ch == 'x') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+4]); //pipe base middle
					map.setTile(x, y, t);
				} else if(ch == 'y') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[pipeOffsetIndex+5]); //pipe base right
					map.setTile(x, y, t);
				} else if(ch == 'z') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[75]); //tree stem
					map.setTile(x, y, t);
				} else if(ch == 'i') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[42]); //
					map.setTile(x, y, t);
				} else if(ch == 'j') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[43]); //
					map.setTile(x, y, t);
				} else if(ch == 'k') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[44]); //tree stem
					map.setTile(x, y, t);
				} else if(ch == 'g') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[9]);
					map.setTile(x, y, t);
				} else if(ch == 'h') {
					GameTile t = new GameTile(pixelX, pixelY, MarioResourceManager.instance.gameTiles.Plain_Tiles[11]);
				    map.setTile(x, y, t);
				} else if(ch == '%') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[36]); //BlueRock 
					map.setTile(x, y, t);
				} else if(ch == '|') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[37]); //Yellowrock
					map.setTile(x, y, t);
				} else if(ch == '$') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[38]); //greyrock
					map.setTile(x, y, t);
				} else if(ch == '-') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[40]); // sandtop
					map.setTile(x, y, t);
				} else if(ch == '+') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[46]); //sandbottom
					map.setTile(x, y, t);
				} else if(ch == '~') {
					GameTile t = new GameTile(pixelX, pixelY,MarioResourceManager.instance.gameTiles.Plain_Tiles[89]); //watertile
					map.setTile(x, y, t);
				}
			}
		}
		Creature.map=map;
	}


	
}
