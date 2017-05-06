
package com.mygdx.game.mario;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;



/**
 * 
 * This class will be responsible for loading/unloading our game resources (art, fonts, audio) 
 * It is defined as A singleton class where the instance variable holds the actual instance of the class. It uses the 
 * public static final modifiers that allows read-only access and is the one and only way to access this class. 
 * The static-ness of this class allows us to access it from virtually anywhere in the game code without having 
 * to pass over its reference to every method where we will use it.
 * E.g to get menu music use <br>
 * <pre>
 *  Music music = Assets.instance.music.menuMusic
 *  </pre>

 * @author maheshkurmi
 */
public class MarioResourceManager  implements Disposable, AssetErrorListener
{
	public static final String TAG = MarioResourceManager.class.getName();

	public static final MarioResourceManager instance = new MarioResourceManager();

	public AssetManager assetManager=new AssetManager();
	//images

	public AssetMario mario;
	public AssetCreatures creatures;
	public AssetGameTiles gameTiles;
	public AssetGui gui;
	public AssetParticles particles;
	public AssetIcon icons;

	//sounds
	public AssetSounds sounds;
	public AssetMusic music;

	public static  BitmapFont defaultSmall;
	public static  BitmapFont defaultNormal;
	public static  BitmapFont defaultBig;

	//Background
	public static TextureRegion gameBackground=null;
	public static TextureRegion guiBackground=null;
	
	
	private MarioResourceManager(){
		loadfonts();
     }

	private static void loadfonts(){
		defaultSmall = new BitmapFont(Gdx.files.internal("assets-mario/images/arial-15.fnt"), true);
		defaultNormal = new BitmapFont(Gdx.files.internal("assets-mario/images/arial-15.fnt"), true);
		defaultBig = new BitmapFont(Gdx.files.internal("assets-mario/images/arial-15.fnt"), true);
		// set font sizes
		defaultSmall.getData().setScale(0.6f);
		//defaultNormal.getData().setScale(1f);
		defaultBig.getData().setScale(1.5f);
		// enable linear texture filtering for smooth fonts
		defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
	}

	public class AssetMario {
		//left mario
		public final  TextureRegion Mario_Big_Left_Still, Mario_Big_Left_1,Mario_Big_Left_2,
				Mario_Big_Left_Run_1,Mario_Big_Left_Run_2, Mario_Big_Crouch_Left,
				Mario_Big_Jump_Left, Mario_Big_Change_Direction_Left;
		//Right mario
		public final  TextureRegion Mario_Big_Right_Still, Mario_Big_Right_1,Mario_Big_Right_2,
				Mario_Big_Right_Run_1,Mario_Big_Right_Run_2, Mario_Big_Crouch_Right,
				Mario_Big_Jump_Right, Mario_Big_Change_Direction_Right;
		//Fire Left mario
		public final  TextureRegion Mario_Big_Left_Still_Fire, Mario_Big_Left_1_Fire,Mario_Big_Left_2_Fire,
					Mario_Big_Left_Run_1_Fire,Mario_Big_Left_Run_2_Fire, Mario_Big_Crouch_Left_Fire,
					Mario_Big_Jump_Left_Fire, Mario_Big_Change_Direction_Left_Fire;
		//Fire Right mario
		public final  TextureRegion Mario_Big_Right_Still_Fire, Mario_Big_Right_1_Fire,Mario_Big_Right_2_Fire,
					Mario_Big_Right_Run_1_Fire,Mario_Big_Right_Run_2_Fire, Mario_Big_Crouch_Right_Fire,
					Mario_Big_Jump_Right_Fire, Mario_Big_Change_Direction_Right_Fire;
			
		public final  TextureRegion[] Mario_Small_Left;
		public final  TextureRegion[] Mario_Small_Right;
		public final  TextureRegion Mario_Flip;

		public final   TextureRegion[] Mario_swim_left_small ,Mario_swim_left_big,Mario_swim_left_fire;
		public final   TextureRegion[] Mario_swim_right_small, Mario_swim_right_big,Mario_swim_right_fire;
		
		public AssetMario (TextureAtlas atlas) {
			Mario_Big_Left_Still = atlas.findRegion("Mario_Big_Left_Still");;		
			Mario_Big_Left_1 = atlas.findRegion("Mario_Big_Left_1");
			Mario_Big_Left_2 = atlas.findRegion("Mario_Big_Left_2");
			Mario_Big_Left_Run_1 = atlas.findRegion("Mario_Big_Left_Run_1");
			Mario_Big_Left_Run_2 = atlas.findRegion("Mario_Big_Left_Run_2");
			Mario_Big_Crouch_Left = atlas.findRegion("Mario_Big_Crouch_Left");
			Mario_Big_Jump_Left = atlas.findRegion("Mario_Big_Jump_Left");
			Mario_Big_Change_Direction_Left = atlas.findRegion("Mario_Big_Change_Direction_Left");
			// Right mario
			Mario_Big_Right_Still = atlas.findRegion("Mario_Big_Right_Still");
			Mario_Big_Right_1 = atlas.findRegion("Mario_Big_Right_1");
			Mario_Big_Right_2 = atlas.findRegion("Mario_Big_Right_2");
			Mario_Big_Right_Run_1 = atlas.findRegion("Mario_Big_Right_Run_1");
			Mario_Big_Right_Run_2 = atlas.findRegion("Mario_Big_Right_Run_2");
			Mario_Big_Crouch_Right = atlas.findRegion("Mario_Big_Crouch_Right");
			Mario_Big_Jump_Right = atlas.findRegion("Mario_Big_Jump_Right");
			Mario_Big_Change_Direction_Right = atlas.findRegion("Mario_Big_Change_Direction_Right");
			//Fire Left Mario
			Mario_Big_Left_Still_Fire = atlas.findRegion("Mario_Big_Left_Still_Fire");
			Mario_Big_Left_1_Fire = atlas.findRegion("Mario_Big_Left_1_Fire");
			Mario_Big_Left_2_Fire = atlas.findRegion("Mario_Big_Left_2_Fire");
			Mario_Big_Left_Run_1_Fire = atlas.findRegion("Mario_Big_Left_Run_1_Fire");
			Mario_Big_Left_Run_2_Fire = atlas.findRegion("Mario_Big_Left_Run_2_Fire");
			Mario_Big_Crouch_Left_Fire = atlas.findRegion("Mario_Big_Crouch_Left_Fire");
			Mario_Big_Jump_Left_Fire = atlas.findRegion("Mario_Big_Jump_Left_Fire");
			Mario_Big_Change_Direction_Left_Fire = atlas.findRegion("Mario_Big_Change_Direction_Left_Fire");
			
			//Fire Right Mario
			Mario_Big_Right_Still_Fire = atlas.findRegion("Mario_Big_Right_Still_Fire");
			Mario_Big_Right_1_Fire = atlas.findRegion("Mario_Big_Right_1_Fire");
			Mario_Big_Right_2_Fire = atlas.findRegion("Mario_Big_Right_2_Fire");
			Mario_Big_Right_Run_1_Fire = atlas.findRegion("Mario_Big_Right_Run_1_Fire");
			Mario_Big_Right_Run_2_Fire = atlas.findRegion("Mario_Big_Right_Run_2_Fire");
			Mario_Big_Crouch_Right_Fire = atlas.findRegion("Mario_Big_Crouch_Right_Fire");
			Mario_Big_Jump_Right_Fire = atlas.findRegion("Mario_Big_Jump_Right_Fire");
			Mario_Big_Change_Direction_Right_Fire = atlas.findRegion("Mario_Big_Change_Direction_Right_Fire");
			
			Mario_Small_Left=splitSprites(atlas.findRegion("Mario_Small_Left"),6,1);
			Mario_Small_Right=splitSprites(atlas.findRegion("Mario_Small_Right"),6,1);
			
			Mario_swim_left_small=splitSprites(atlas.findRegion("Mario_Small_Swim_Left"), 4, 1);
			Mario_swim_right_small=splitSprites(atlas.findRegion("Mario_Small_Swim_Right"), 4, 1);
			Mario_swim_left_big=splitSprites(atlas.findRegion("Mario_Big_Swim_Left"), 4, 1);
			Mario_swim_right_big=splitSprites(atlas.findRegion("Mario_Big_Swim_Right"), 4, 1);
			Mario_swim_left_fire=splitSprites(atlas.findRegion("Mario_Fire_Swim_Left"), 4, 1);
			Mario_swim_right_fire=splitSprites(atlas.findRegion("Mario_Fire_Swim_Right"), 4, 1);
			Mario_Flip=atlas.findRegion("Mario_Flip");
		
		}
	};

	public class AssetCreatures {
		//coin
		public final  TextureRegion Coin_Icon;
		public final  TextureRegion Coin_1, Coin_2,Coin_3,Coin_4;
		public final  TextureRegion Coin_5, Coin_6,Coin_7,Coin_8;
		//goomba
		public final  TextureRegion Goomba_Normal_1,Goomba_Normal_2,Goomba_Dead,Goomba_Flip;
		public final  TextureRegion[] Fly_Goomba;

		//mushroom
		public final  TextureRegion Mushroom;
		public final  TextureRegion LifeUp;
		public final  TextureRegion Fire_Flower;

		//platform
		public final  TextureRegion Red_Platform_2;
		//FlyredKoopa
		public final  TextureRegion[]  Fly_Koopa_Red_Left,Fly_Koopa_Red_Right;
		//redKoopa
		public final  TextureRegion  Koopa_Red_Left_1,Koopa_Red_Left_2,Koopa_Red_Right_1,Koopa_Red_Right_2;
	  	//redShell
		public final  TextureRegion  Red_Shell_1,Red_Shell_2,Red_Shell_3,Red_Shell_4,Red_Shell_Still,Red_Shell_Flip;
		//greenKoopa
		public final  TextureRegion[]  Green_Koopa;
		
		
		
		//greenShell
		public final  TextureRegion[]  Green_Shell;
		//Thorny
		public final  TextureRegion[] Thorny;	
		//ThornyBomb
		public final  TextureRegion[] Fire_Thorny;
		//Thorny
		public final  TextureRegion[] Latiku;
		public final  TextureRegion[] Latiku_Fire;


		//Bowser
		public final  TextureRegion[] Bowser_Left;
		public final  TextureRegion[] Bowser_Right;
		public final  TextureRegion[] BoomRang_left;
		public final  TextureRegion[] BoomRang_right;
		
		//Score
		public final  TextureRegion  Score_100_New6;
		public final  TextureRegion  Score_1000;
		public final  TextureRegion  Score_1_Up;
		public final  TextureRegion  Score_200;
		public final TextureRegion levelComplete;
		
		//fireball
		public final  TextureRegion fb_1,fb_2,fb_3,fb_4,fb_5,fb_6;
		//firedisc
		public final  TextureRegion[] FireDisc;
		//piranha
		public final  TextureRegion Piranha_1,Piranha_2;
		//Spring
		public final  TextureRegion Spring_1,Spring_2,Spring_3;
		public final  TextureRegion[] Virus;
		public final  TextureRegion[] RedFish;
		public final  TextureRegion[] BlueFish;

		public AssetCreatures (TextureAtlas atlas) {
			Coin_Icon = atlas.findRegion("Coin_Icon");
			Coin_1 = atlas.findRegion("Coin_1");
			Coin_2 = atlas.findRegion("Coin_2");
			Coin_3 = atlas.findRegion("Coin_3");
			Coin_4 = atlas.findRegion("Coin_4");
			Coin_5 = atlas.findRegion("Coin_5");
			Coin_6 = atlas.findRegion("Coin_6");
			Coin_7 = atlas.findRegion("Coin_7");
			Coin_8 = atlas.findRegion("Coin_8");
			// goomba
			Goomba_Normal_1 = atlas.findRegion("Goomba_Normal_1");
			Goomba_Normal_2 = atlas.findRegion("Goomba_Normal_2");
			Goomba_Dead = atlas.findRegion("Goomba_Dead");
			Goomba_Flip = atlas.findRegion("Goomba_Flip");
			Fly_Goomba = splitSprites(atlas.findRegion("Fly_Goomba"),3,1);

			// mushroom
			Mushroom = atlas.findRegion("Mushroom");
			LifeUp = atlas.findRegion("1_Up");
			Fire_Flower= atlas.findRegion("Fire_Flower");
			// Platform
			Red_Platform_2 = atlas.findRegion("brett");
			
			// Fly redKoopa
			Fly_Koopa_Red_Left = splitSprites(atlas.findRegion("Fly_Koopa_Red_Left"),3,1);
			Fly_Koopa_Red_Right = splitSprites(atlas.findRegion("Fly_Koopa_Red_Right"),3,1);
					
			// redKoopa
			Koopa_Red_Left_1 = atlas.findRegion("Koopa_Red_Left_1");
			Koopa_Red_Left_2 = atlas.findRegion("Koopa_Red_Left_2");
			Koopa_Red_Right_1 = atlas.findRegion("Koopa_Red_Right_1");
			Koopa_Red_Right_2 = atlas.findRegion("Koopa_Red_Right_2");
			// redshell
			Red_Shell_1 = atlas.findRegion("Red_Shell_1");
			Red_Shell_2 = atlas.findRegion("Red_Shell_2");
			Red_Shell_3 = atlas.findRegion("Red_Shell_3");
			Red_Shell_4 = atlas.findRegion("Red_Shell_4");
			Red_Shell_Still = atlas.findRegion("Red_Shell_Still");
			Red_Shell_Flip = atlas.findRegion("Red_Shell_Flip");
			//Green_Koopa
			Green_Koopa = splitSprites(atlas.findRegion("Green_Koopa"), 4, 1);
			//GreenShell
			Green_Shell = splitSprites(atlas.findRegion("Green_Shell"), 6, 1);
			//Thorny
			Thorny = splitSprites(atlas.findRegion("thorny"), 5, 1);
			Fire_Thorny = splitSprites(atlas.findRegion("Thorny_Bomb"), 2, 1);
			Latiku = splitSprites(atlas.findRegion("Latiku"), 3, 1);
			Latiku_Fire = splitSprites(atlas.findRegion("Latiku_Fire"), 2, 1);
			
			Bowser_Left=splitSprites(atlas.findRegion("Bowser_Left"), 5, 1);
			Bowser_Right=splitSprites(atlas.findRegion("Bowser_Right"), 5, 1);
			
			BoomRang_left=splitSprites(atlas.findRegion("BoomRang_left"), 4, 1);
			BoomRang_right=splitSprites(atlas.findRegion("BoomRang_right"), 4, 1);
			
			// Score
			Score_100_New6 = atlas.findRegion("Score_100_New6");
			Score_1000 = atlas.findRegion("Score_1000");
			Score_200 = atlas.findRegion("Score_200");
			Score_1_Up=atlas.findRegion("Score_1_Up");
			RedFish=splitSprites(atlas.findRegion("Red_Fish"), 5, 1);
			BlueFish=splitSprites(atlas.findRegion("Blue_Fish"), 5, 1);
			Virus=splitSprites(atlas.findRegion("Virus"), 3, 1);
			
			levelComplete = atlas.findRegion("level");
			// fireball
			fb_1 = atlas.findRegion("Fireball_1");
			fb_2 = atlas.findRegion("Fireball_2");
			fb_3 = atlas.findRegion("Fireball_3");
			fb_4 = atlas.findRegion("Fireball_4");
			fb_5 = atlas.findRegion("Fireball_5");
			fb_6 = atlas.findRegion("Fireball_6");
			
			FireDisc=splitSprites(atlas.findRegion("Fire_Disc"),3,1);
			//Piranha
			Piranha_1=atlas.findRegion("Piranha_1");
			Piranha_2=atlas.findRegion("Piranha_2");
			
			//Spring
			Spring_1=atlas.findRegion("Spring_1");
			Spring_2=atlas.findRegion("Spring_2");
			Spring_3=atlas.findRegion("Spring_3");
		}
	}
	
	public class AssetGameTiles {
		//tree
		public final TextureRegion  Tree_1,Tree_2;
		//bush
		public final TextureRegion  Bush_1,Bush_2;
		//FireTile
		public final TextureRegion[] Fire_Tile;
		//QuestionBlock
		public final TextureRegion  Question_Block_0,Question_Block_1,Question_Block_2,Question_Block_3,Question_Block_Dead;
		//RotatingBlock
		public final TextureRegion  Rotating_Block_Hit_1,Rotating_Block_Hit_2,Rotating_Block_Hit_3,Rotating_Block_Still;
		//PlainTileMap
		public final TextureRegion[] Plain_Tiles;
		public final TextureRegion mushroomTree;
		public final TextureRegion woodenBridge;
		public final TextureRegion waterRock;
		public final TextureRegion[] waterWaves;
		//grass
		public final TextureRegion Sloped_Tile;
		public final TextureRegion Grass_Edge; 
		public final TextureRegion Grass_Center; 
		public final TextureRegion Castle;
		
		public AssetGameTiles(TextureAtlas atlas){
			// Tree
			Tree_1 = atlas.findRegion("Tree_1");
			Tree_2 = atlas.findRegion("Tree_2");
			// Bush
			Bush_1 = atlas.findRegion("Bush_1");
			Bush_2 = atlas.findRegion("Bush_2");
			Gdx.app.log("bush1=",""+ Tree_1+Bush_1);
			//Fire_Tile
			Fire_Tile=splitSprites(atlas.findRegion("FireTile"), 2, 1);
			// QuwestionBlock
			Question_Block_0 = atlas.findRegion("Question_Block_0");
			Question_Block_1 = atlas.findRegion("Question_Block_1");
			Question_Block_2 = atlas.findRegion("Question_Block_2");
			Question_Block_3 = atlas.findRegion("Question_Block_3");
			Question_Block_Dead = atlas.findRegion("Question_Block_Dead");
			//rotatingBlock
			Rotating_Block_Hit_1 = atlas.findRegion("Rotating_Block_Hit_1");
			Rotating_Block_Hit_2 = atlas.findRegion("Rotating_Block_Hit_2");
			Rotating_Block_Hit_3 = atlas.findRegion("Rotating_Block_Hit_3");
			Rotating_Block_Still = atlas.findRegion("Rotating_Block_Still");
			//options.inPreferredConfig = TextureRegion.Config.RGB_565;

			//plainTiles
			Plain_Tiles = splitSprites(atlas.findRegion("Plain_Tiles"), 6, 17);
			mushroomTree=atlas.findRegion("tree");
			woodenBridge=atlas.findRegion("wooden_bridge");
			waterRock=atlas.findRegion("water_rock");

			//grass
			Sloped_Tile = atlas.findRegion("Sloped_Tile");
			Grass_Edge = atlas.findRegion("Grass_Edge");
			Grass_Center = atlas.findRegion("Grass_Center");
			waterWaves=splitSprites(atlas.findRegion("water-waves"),1,8);
			Castle=atlas.findRegion("Castle");

		}
	}
	
	public class AssetParticles {
		//bricks shatter
		public final TextureRegion brick_particle;
		public final TextureRegion[] Sparkle_Particles;
		public final TextureRegion[] Smoke_Particles;
		public final TextureRegion[] Bomb_Particles;
		
		public AssetParticles(TextureAtlas atlas){
			brick_particle=atlas.findRegion("particle_brick");
			Smoke_Particles = splitSprites(atlas.findRegion("Smoke_Particle"), 8, 1);
			Sparkle_Particles = splitSprites(atlas.findRegion("Sparkle_Particle"), 8, 1);
			Bomb_Particles = splitSprites(atlas.findRegion("Bomb_Particle"), 8, 1);
		}
		
	}
		
	public class AssetGui {
		//3stars
		public  TextureRegion star3;
		// mario pic for main menu
		public  TextureRegion marioPic;
		public final TextureRegion pearl1,pearl2;
		public final TextureRegion level;
		public final TextureRegion level_1star;
		public final TextureRegion level_2star;
		public final TextureRegion level_3star;
		public final TextureRegion level_locked;
	

		public final TextureRegion Btn_Next;
		public final TextureRegion Btn_Prev;
		public final TextureRegion Btn_Fire;
		public final TextureRegion Btn_Jump;
		public final TextureRegion logo;

		public final TextureRegion TapToStart;
		
		public AssetGui(TextureAtlas atlas){
			marioPic = atlas.findRegion("marioPic");
			level = atlas.findRegion("level");
			level_1star = atlas.findRegion("level_1star");
			level_2star = atlas.findRegion("level_2star");
			level_3star = atlas.findRegion("level_3star");
			level_locked = atlas.findRegion("level_locked");
			star3 = atlas.findRegion("star3");
			
			Btn_Next= atlas.findRegion("Button_Next");
			Btn_Prev= atlas.findRegion("Button_Prev");
			Btn_Fire= atlas.findRegion("Button_Fire");
			Btn_Jump= atlas.findRegion("Button_Jump");
			logo=atlas.findRegion("Title");
			pearl1=atlas.findRegion("pearl1");
			pearl2=atlas.findRegion("pearl2");
			TapToStart=atlas.findRegion("TaptoStart");
		}
	}
	
	public class AssetIcon {
		
		/** icon */
		public final TextureRegion NEW,OPEN,SAVE,SAVEAS ,EXPORT ,PRINT ,UNDO,REDO ;
		
		/**app icon */
		public final TextureRegion ICON_G ;
		public final TextureRegion ICON_K ;
		public final TextureRegion ICON_L;
		public final TextureRegion ICON_H ;;
		public final TextureRegion ICON_J ;   ;
		public final TextureRegion ICON_B_L ;
		public final TextureRegion ICON_B_R ;
		public final TextureRegion ICON_F_R ;
		public final TextureRegion ICON_F_L ;
		public final TextureRegion ICON_G_L;

		public final TextureRegion ICON_I;
		public final TextureRegion ICON_S ;
		
		public final TextureRegion ICON_Level;
		public final TextureRegion ICON_P;
		public final TextureRegion ICON_Px;
		public final TextureRegion ICON_Py;
		public final TextureRegion ICON_BookMark;


		public final TextureRegion ICON_B;
		public final TextureRegion ICON_C;
		public final TextureRegion ICON_Q;
		public final TextureRegion ICON_W;  //W Questiion bloack with life
		//public final TextureRegion ICON_P = ImageIcon("icons/P.png");
		public final TextureRegion ICON_T ;  //tree
		public final TextureRegion ICON_R ;
		public final TextureRegion ICON_V ;
		
		
		public final TextureRegion ICON_2 ;
		public final TextureRegion ICON_3;// green floor middle
		public final TextureRegion ICON_g ;  // 
		public final TextureRegion ICON_4 ; // 
		public final TextureRegion ICON_h ; // 
		
		public final TextureRegion ICON_5 ;// grass floor left edge 
		public final TextureRegion ICON_6 ;  //grass floor right edge

		public final TextureRegion ICON_7 ;///slope grass centre
		public final TextureRegion ICON_8 ; //slope grass edge
		public final TextureRegion ICON_9;//slope
			
		public final TextureRegion ICON_i; //slope grass centre
		public final TextureRegion ICON_j ;;  //slope grass edge
		public final TextureRegion ICON_k;  //slope

		
		public final TextureRegion ICON_n; //roundtree left
		public final TextureRegion ICON_m; //roundtree right
		
		public final TextureRegion ICON_a; //roundtree left
		public final TextureRegion ICON_b;  //roundtree right
		public final TextureRegion ICON_q;//rock left
		public final TextureRegion ICON_r ;; //rock right
		public final TextureRegion ICON_z ; //stem ttree
		public final TextureRegion ICON_t ; //pipe top left
		public final TextureRegion ICON_u ;
		public final TextureRegion ICON_v ;
		public final TextureRegion ICON_w ;
		public final TextureRegion ICON_x;
		public final TextureRegion ICON_y;;  // pipe base right
		public final TextureRegion ICON_bulb ;
		public final TextureRegion ICON_bulbbase ;
		public final TextureRegion ICON_info ;  // Inf panel
		public final TextureRegion ICON_spring;
		public final TextureRegion ICON_bluerock;
		public final TextureRegion ICON_yellowrock;
		public final TextureRegion ICON_grayrock;
		
		public final TextureRegion ICON_sandTop;
		public final TextureRegion ICON_sand ;
		public final TextureRegion ICON_waterTile ;
		public final TextureRegion ICON_waterPlant ;
		public final TextureRegion ICON_bush ;

		public final TextureRegion ICON_virus;
		public final TextureRegion ICON_blueFish;
		public final TextureRegion ICON_redFish;
		public final TextureRegion ICON_jumpingFish ;
		public final TextureRegion ICON_latiku ;

		public final TextureRegion ICON_fireTop ;
		public final TextureRegion ICON_fireBase ;
		public final TextureRegion ICON_fireBrickLeft ;  
		public final TextureRegion ICON_fireBrickRight ;
		public final TextureRegion ICON_Girder;
		public final TextureRegion ADD_WATER;
		public final TextureRegion REMOVE_WATER ;
		public AssetIcon(TextureAtlas atlas){
			
			/** New icon */
			 NEW = atlas.findRegion("new-icon");
			
			/** Open icon */
			 OPEN = atlas.findRegion("open-icon");
			
			/** Save icon */
			 SAVE = atlas.findRegion("save-icon");

			/** Save As.. icon */
			 SAVEAS = atlas.findRegion("saveas-icon");
			
			/** Export as Image icon */
			 EXPORT = atlas.findRegion("export-icon");
			
			/** Print icon */
			 PRINT = atlas.findRegion("print-icon");
			
			/** undo icon */
			 UNDO = atlas.findRegion("undo-icon");
			
			/** redo icon */
			 REDO =  atlas.findRegion("redo-icon");

		
			/**app icon */
			 ICON_G = atlas.findRegion("G");
			 ICON_K = atlas.findRegion("K_l");
			 ICON_L = atlas.findRegion("K_r");
			 ICON_H = atlas.findRegion("H_l");
			 ICON_J = atlas.findRegion("J");      //red shell
			 ICON_B_L = atlas.findRegion("B_l");
			 ICON_B_R = atlas.findRegion("B_r");      //red shell
			 ICON_F_R = atlas.findRegion("F_l");      //red shell
			 ICON_F_L = atlas.findRegion("F_r");      //red shell
			 ICON_G_L = atlas.findRegion("Fly_Goomba");      //red shell

			 ICON_I = atlas.findRegion("firedisc_I");
			 ICON_S = atlas.findRegion("S");      //red shell
			
			 ICON_Level = atlas.findRegion("levelIcon");
			 ICON_P= atlas.findRegion("brett_0");
			 ICON_Px= atlas.findRegion("brett_x");
			 ICON_Py= atlas.findRegion("brett_y");
			 ICON_BookMark= atlas.findRegion("BookMark");


			 ICON_B = atlas.findRegion("brick");
			 ICON_C = atlas.findRegion("C");
			 ICON_Q = atlas.findRegion("Q_S");  //Q Questiion bloack with coin
			 ICON_W = atlas.findRegion("Q_L");  //W Questiion bloack with life
			// ICON_P = atlas.findRegion("P");
			 ICON_T = atlas.findRegion("tree");  //tree
			 ICON_R = atlas.findRegion("R_B");
			 ICON_V = atlas.findRegion("V_T");
			
			
			 ICON_2 = atlas.findRegion("2");  //
			 ICON_3 = atlas.findRegion("3");  // green floor middle
			 ICON_g = atlas.findRegion("left_g");  // 
			 ICON_4 = atlas.findRegion("4");  // 
			 ICON_h = atlas.findRegion("right_h");  // 
			
			 ICON_5 = atlas.findRegion("5");  // grass floor left edge 
			 ICON_6 = atlas.findRegion("6");  //grass floor right edge

			 ICON_7 = atlas.findRegion("7");  //slope grass centre
			 ICON_8 = atlas.findRegion("8");  //slope grass edge
			 ICON_9= atlas.findRegion("9");   //slope
				
			 ICON_i = atlas.findRegion("left_i");  //slope grass centre
			 ICON_j = atlas.findRegion("mid_j");  //slope grass edge
			 ICON_k= atlas.findRegion("right_k");   //slope

			
			 ICON_n = atlas.findRegion("n"); //roundtree left
			 ICON_m= atlas.findRegion("m");  //roundtree right
			
			 ICON_a = atlas.findRegion("a"); //roundtree left
			 ICON_b= atlas.findRegion("b");  //roundtree right
			 ICON_q = atlas.findRegion("q"); //rock left
			 ICON_r = atlas.findRegion("r"); //rock right
			 ICON_z = atlas.findRegion("z"); //stem ttree
			 ICON_t = atlas.findRegion("t"); //pipe top left
			 ICON_u = atlas.findRegion("u");
			 ICON_v = atlas.findRegion("v");
			 ICON_w = atlas.findRegion("w");
			 ICON_x = atlas.findRegion("x");
			 ICON_y = atlas.findRegion("y");  // pipe base right
			 ICON_bulb = atlas.findRegion("bulb");
			 ICON_bulbbase = atlas.findRegion("bulbbase");  
			 ICON_info = atlas.findRegion("info");  // Inf panel
			 ICON_spring= atlas.findRegion("Spring");  // 
			 ICON_bluerock = atlas.findRegion("blueRock");  
			 ICON_yellowrock= atlas.findRegion("yellowRock");  //
			 ICON_grayrock= atlas.findRegion("grayRock");  //
			
			 ICON_sandTop= atlas.findRegion("sandM");  // 
			 ICON_sand = atlas.findRegion("sand");  
			 ICON_waterTile = atlas.findRegion("water_tile");  
			 ICON_waterPlant = atlas.findRegion("water_plant");  
			 ICON_bush = atlas.findRegion("bush");  

			 ICON_virus= atlas.findRegion("virus");  // 
			 ICON_blueFish = atlas.findRegion("blueFish");  
			 ICON_redFish = atlas.findRegion("redFish");  
			 ICON_jumpingFish = atlas.findRegion("jumpingFish");  
			 ICON_latiku = atlas.findRegion("latiku");  

			 ICON_fireTop = atlas.findRegion("FireTop");  
			 ICON_fireBase = atlas.findRegion("FireBase");  
			 ICON_fireBrickLeft = atlas.findRegion("FireBrickLeft");  
			 ICON_fireBrickRight = atlas.findRegion("FireBrickRight");  
			 ICON_Girder = atlas.findRegion("girder");  
			 ADD_WATER=  atlas.findRegion("water");
			 REMOVE_WATER =  atlas.findRegion("delete_water");
		}
	}
	
	public class AssetSounds {

		//Sound and Music
		public final Sound bump, kick, coin, jump, pause, itemSprout, bonusPoints, healthUp, healthDown; 
		public final Sound brick_shatter,fireball,die,powerUp,powerDown,stomp;
		public final Sound hurt1, hurt2, yahoo1, yahoo2,stage_clear,stage_begin;
		public final Sound click, gulp,switchScreen;

		public AssetSounds (AssetManager am) {
			bump = assetManager.get("assets-mario/sounds/bump_converted.wav", Sound.class);
	 		kick = assetManager.get("assets-mario/sounds/kick_converted.wav", Sound.class);
	 		coin = assetManager.get("assets-mario/sounds/coin_converted.wav", Sound.class);
	 		jump = assetManager.get("assets-mario/sounds/jump_converted.wav", Sound.class);
	 		pause = assetManager.get("assets-mario/sounds/pause_converted.wav", Sound.class);
	 		itemSprout = assetManager.get("assets-mario/sounds/item_sprout_converted.wav", Sound.class);
	 		bonusPoints = assetManager.get("assets-mario/sounds/veggie_throw_converted.wav", Sound.class);
	 		healthUp = assetManager.get("assets-mario/sounds/power_up_converted.wav", Sound.class);
	 		healthDown = assetManager.get("assets-mario/sounds/power_down_converted.wav", Sound.class);
	 		
	 		hurt1 = assetManager.get("assets-mario/sounds/mario_ooh_converted.wav", Sound.class);
	 		hurt2 = assetManager.get("assets-mario/sounds/mario_oh_converted.wav", Sound.class);
	 		yahoo1 = assetManager.get("assets-mario/sounds/mario_waha_converted.wav", Sound.class);
	 		yahoo2 = assetManager.get("assets-mario/sounds/mario_woohoo_converted.wav", Sound.class);
	 		brick_shatter=assetManager.get("assets-mario/sounds/crack_converted.wav", Sound.class);
	 		fireball=assetManager.get("assets-mario/sounds/fireball_converted.wav", Sound.class);
	 		die=assetManager.get("assets-mario/sounds/die_converted.wav", Sound.class);
	 		powerUp=assetManager.get("assets-mario/sounds/power_up_converted.wav", Sound.class);
			powerDown=assetManager.get("assets-mario/sounds/power_down_converted.wav", Sound.class);
			stage_clear=assetManager.get("assets-mario/sounds/win_stage_converted.wav", Sound.class);
			stage_begin=assetManager.get("assets-mario/sounds/level_enter_converted.wav", Sound.class);
			stomp=assetManager.get("assets-mario/sounds/stomp_converted.wav", Sound.class);
			gulp=assetManager.get("assets-mario/sounds/gulp_converted.wav", Sound.class);
			switchScreen=assetManager.get("assets-mario/sounds/level_enter_converted.wav", Sound.class);
			click=assetManager.get("assets-mario/sounds/coin_converted.wav", Sound.class);	;
		}
	}
	
	public class AssetMusic {
		public final Music gameMusic_1;
		public final Music gameMusic_2;
		public final Music menuMusic;

		public AssetMusic (AssetManager am) {
			gameMusic_1=am.get(Constants.DEFAULT_GAME_MUSIC_PATH_1,Music.class);
			gameMusic_1.setLooping(true);
			gameMusic_2=am.get(Constants.DEFAULT_GAME_MUSIC_PATH_1,Music.class);
			gameMusic_2.setLooping(true);
			menuMusic=am.get(Constants.DEFAULT_MENU_MUSIC_PATH,Music.class);
			menuMusic.setLooping(true);
		}
	}
		
	  /**
	 * loads resources in memory if resources are not already loaded
	 */
	public void init () {
		assetManager=new AssetManager();
		if(defaultSmall==null)loadfonts();
		// set asset manager error handler
		assetManager.setErrorListener(this);
		
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_MARIO,TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_CREATURES,TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_GAMETILES,TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_GUI,TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_PARTICLES,TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_ICONS,TextureAtlas.class);
		
		// load background
		assetManager.load(Constants.DEFAULT_BACKGROUND_IMAGE_PATH,Texture.class);
		
		//load Sounds
		assetManager.load("assets-mario/sounds/bump_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/kick_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/coin_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/jump_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/pause_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/item_sprout_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/veggie_throw_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/power_up_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/power_down_converted.wav", Sound.class);
	    assetManager.load("assets-mario/sounds/mario_ooh_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/mario_oh_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/mario_waha_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/mario_woohoo_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/crack_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/fireball_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/die_converted.wav", Sound.class);
	 	assetManager.load("assets-mario/sounds/power_up_converted.wav", Sound.class);
		assetManager.load("assets-mario/sounds/power_down_converted.wav", Sound.class);
		assetManager.load("assets-mario/sounds/win_stage_converted.wav", Sound.class);
		assetManager.load("assets-mario/sounds/level_enter_converted.wav", Sound.class);
		assetManager.load("assets-mario/sounds/stomp_converted.wav", Sound.class);
		assetManager.load("assets-mario/sounds/gulp_converted.wav", Sound.class);
		assetManager.load("assets-mario/sounds/level_enter_converted.wav", Sound.class);
		assetManager.load("assets-mario/sounds/coin_converted.wav", Sound.class);	
		
		//load music
		assetManager.load(Constants.DEFAULT_GAME_MUSIC_PATH_1, Music.class);
		assetManager.load(Constants.DEFAULT_GAME_MUSIC_PATH_2, Music.class);
		assetManager.load(Constants.DEFAULT_MENU_MUSIC_PATH, Music.class);
		
		// start loading assets and wait until finished
		//assetManager.finishLoading();

		Texture.setAssetManager(assetManager);

	}
	
	public void createAssets(){
		/*
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames()) {
			Gdx.app.log(TAG, a+": " + assetManager.getAssetType(a));
		}
		*/
		
		TextureAtlas atlasMario = assetManager.get(Constants.TEXTURE_ATLAS_MARIO);
		TextureAtlas atlasCreatures = assetManager.get(Constants.TEXTURE_ATLAS_CREATURES);
		TextureAtlas atlasGameTiles = assetManager.get(Constants.TEXTURE_ATLAS_GAMETILES);
		TextureAtlas atlasGui = assetManager.get(Constants.TEXTURE_ATLAS_GUI);
		TextureAtlas atlasParticles = assetManager.get(Constants.TEXTURE_ATLAS_PARTICLES);
		TextureAtlas atlasIcons= assetManager.get(Constants.TEXTURE_ATLAS_ICONS);
		// enable texture filtering for pixel smoothing
		
		for (Texture t : atlasMario.getTextures()) t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//for (Texture t : atlasCreatures.getTextures()) t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//for (Texture t : atlasGameTiles.getTextures()) t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		for (Texture t : atlasGui.getTextures()) t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//for (Texture t : atlasParticles.getTextures()) t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		Array<AtlasRegion> tr;
		// Let's flip all the regions.  Required for y=0 is TOP
		 tr= atlasMario.getRegions();      
		for (int i = 0; i < tr.size; i++) {
		  TextureRegion t = tr.get(i);
		  t.flip(false, true);
		}
		
		
		tr = atlasCreatures.getRegions();      
		for (int i = 0; i < tr.size; i++) {
		  TextureRegion t = tr.get(i);
		  t.flip(false, true);
		}
		
		tr = atlasGameTiles.getRegions();      
		for (int i = 0; i < tr.size; i++) {
		  TextureRegion t = tr.get(i);
		  t.flip(false, true);
		}
		tr = atlasGui.getRegions();      
		for (int i = 0; i < tr.size; i++) {
		  TextureRegion t = tr.get(i);
		  t.flip(false, true);
		}
		tr = atlasParticles.getRegions();      
		for (int i = 0; i < tr.size; i++) {
		  TextureRegion t = tr.get(i);
		  t.flip(false, true);
		}
		
		// create game resource objects
			mario = new AssetMario(atlasMario);
		creatures = new AssetCreatures(atlasCreatures);
		gameTiles = new AssetGameTiles(atlasGameTiles);
		//Gdx.app.log("",""+atlasGameTiles.getTextures());
		gui = new AssetGui(atlasGui);
		particles = new AssetParticles(atlasParticles);
		sounds = new AssetSounds(assetManager);
		music = new AssetMusic(assetManager);
		icons =new AssetIcon(atlasIcons);
		guiBackground=new TextureRegion((Texture) assetManager.get(Constants.DEFAULT_BACKGROUND_IMAGE_PATH));
	}

    
	
	
 // Splits a given sprite sheet into it's individual sprites and
  	// returns the array containing each sprite from left to right, top to bottom..
  	private TextureRegion[] splitSprites(TextureRegion textureRegion,int frameCols, int frameRows) {
  		int width = textureRegion.getRegionWidth()/frameCols;
		int height = textureRegion.getRegionHeight()/frameRows;
		textureRegion.flip(false,true);
		
  	    TextureRegion[][] temp = textureRegion.split( width, height);
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];

        int index = 0;
        for (int i = 0; i < frameRows; i++) 
        {
            for (int j = 0; j < frameCols; j++) 
            {
            	//frames[index].
                frames[index] = temp[i][j];
                frames[index].flip(false, true);
                index++;
            }
        }
  		return frames;
  	} 
     

	@Override
	public void dispose () {
	
		defaultSmall.dispose();
		defaultNormal.dispose();
		defaultBig.dispose();
		//if(gameBackground!=null)gameBackground.getTexture().dispose();
		assetManager.dispose();
		defaultSmall=null;
		defaultNormal=null;
		defaultBig=null;
		gameBackground=null;
		guiBackground=null;
		gameBackground=null;
		
	}

	@Override
	public void error(@SuppressWarnings("rawtypes") AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);		
	}
	
	private static String background_Path="";
	//loads random background
	public static void loadBackground(int index){
		if(!background_Path.isEmpty())instance.assetManager.unload(background_Path);
		//Texture t=new Texture(Gdx.files.internal("assets-mario/backgrounds/background"+index+".png"));
		//t.setWrap( TextureWrap.MirroredRepeat,TextureWrap.MirroredRepeat);
		//gameBackground=new TextureRegion(t);
		instance.assetManager.load("assets-mario/backgrounds/background"+index+".png", Texture.class);
		instance.assetManager.finishLoading();
		gameBackground=new TextureRegion((Texture)instance.assetManager.get("assets-mario/backgrounds/background"+index+".png"));
		gameBackground.flip(false,true);
		//gameBackground.getTexture().setWrap( TextureWrap.MirroredRepeat,TextureWrap.MirroredRepeat);
	}
 
}