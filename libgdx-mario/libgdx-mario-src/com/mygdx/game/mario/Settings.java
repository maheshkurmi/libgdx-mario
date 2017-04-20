

package com.mygdx.game.mario;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.mario.objects.mario.Mario;

/**
 * Saves and loads Setings
 * @author maheshkurmi
 *
 */
public class Settings{

	public static final String TAG = Settings.class.getName();

	public static float volSound;
	public static float volMusic;
	public static boolean showFpsCounter;
    public static boolean soundEnabled = true;
    public static boolean musicEnabled = true;
    
    public static int soundVolume = 50;
    public static int musicVolume = 50;
    public static int tiltSensitivity = 50;//not implemneted
    public static int movementSensitivity = 50; //not implemneted
    
       
    public static  boolean mUseClickButtonForAttack = true;
    public static  boolean mUseOrientationForMovement = false;
    public static  boolean mUseOnScreenControls = false;

    public static int[] highScores = new int[] { 0, 0, 0, 0, 0 ,0,0,0,0,0};
    public  static int[] recodTimes =new int[] { 10000, 10000, 10000, 10000, 10000 ,10000,10000,10000,10000,10000};
    
   
    private static int score=0;
	private static int lives=0;
	private static int time=0;
	private static int coins=0;
	private static int levelsUnlocked=0;
	private static int worldsUnlocked=0;

    private static Mario mario;

	private static Preferences prefs;

	// singleton: prevent instantiation from other classes
	private Settings () {
		load();
	}

	public static void load () {
		
		//choose controllar based on platform/device
		mUseOrientationForMovement = false;
		mUseOnScreenControls = false;
		if(Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)){
			mUseOrientationForMovement=true;
		}else if(Gdx.app.getType()==ApplicationType.Desktop){
			//mUseOnScreenControls = true;
			//mUseOrientationForMovement = false;
		}else{
			mUseOnScreenControls = true;
		}
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);		
		soundEnabled = prefs.getBoolean("sound", true);
		musicEnabled = prefs.getBoolean("music", true);
		volSound = MathUtils.clamp(prefs.getFloat("volSound", 0.5f), 0.0f, 1.0f);
		volMusic = MathUtils.clamp(prefs.getFloat("volMusic", 0.5f), 0.0f, 1.0f);
		showFpsCounter = prefs.getBoolean("showFpsCounter", false);
		  //SharedPreferences scorePrefs=context.getSharedPreferences(PreferenceConstants.PREFERENCE_SCORE_NAME, Context.MODE_PRIVATE);
		for (int i=0; i<highScores.length;i++){
	       	 highScores[i]=prefs.getInteger("levelScore"+i, 0);
	       	 recodTimes[i]=prefs.getInteger("levelTime"+i, 10000);
	    }
	}

	public static void save () {
		prefs.putBoolean("sound", soundEnabled);
		prefs.putBoolean("music", musicEnabled);
		prefs.putFloat("volSound", volSound);
		prefs.putFloat("volMusic", volMusic);
		prefs.putBoolean("showFpsCounter", showFpsCounter);
		for (int i=0; i<highScores.length;i++){
			prefs.putInteger("levelScore"+i, highScores[i]);
			prefs.putInteger("levelTime"+i, recodTimes[i]);
	     }
		prefs.flush();
	}

	public static int getWorldsUnlocked() {
		return worldsUnlocked;
	}

	public static void setWorldsUnlocked(int worldsUnlocked) {
		Settings.worldsUnlocked = worldsUnlocked;
	}

	public static int level = 1;
	public static int world = 2;
	   
	public static final int COIN_BONUS=100;
	public static final int GOOMBA_BONUS=300;
	public static final int KOOPA_BONUS=500;
	public static final int LIFE_BONUS=1000;

	public static float accelerometerSenseFactor=1;
 

	public static boolean addHighScore(int world, int level,int newHighScore) {
		int i=world*3+level-4;
		if (highScores[i]<newHighScore){
			highScores[i]=newHighScore;
			prefs.putInteger("levelScore"+i, highScores[i]);
			prefs.flush();
			return true;
		}
		return false;
	}
    
	public static boolean addRecordTime(int world, int level,int newTime) {
		int i=world*3+level-4;
		if (recodTimes[i]>newTime){
			recodTimes[i]=newTime;
			prefs.putInteger("levelTime"+i, recodTimes[i]);
			prefs.flush();
			return true;
		}
		return false;
	}
    
	
	
	
	public static void setSensitivity(int value){
		tiltSensitivity=value;
		accelerometerSenseFactor=0.1f+tiltSensitivity/55.0f;
	}

	public static int getHighScore(int world, int level) {
		return highScores[world*3+level-4];
	}
	public static int getRecordTime(int world, int level) {
		return recodTimes[world*3+level-4];
	}
	
	public static int getTime() {
		return time;
	}

	public static void setTime(int time) {
		Settings.time = time;
	}

	public static int getCoins() {
		return coins;
	}

	public static void addCoins(int coins) {
		Settings.coins+= coins;
		if (Settings.coins>=50 && mario!=null){
			if (mario.getHealth()<3){
				Settings.coins-=50;
				mario.setHealth(mario.getHealth()+1);
				Settings.setLives(mario.getHealth());
			}
		}
	}
   
	public static int getScore() {
		return score;
	}

	public static void addScore(int newScore) {
		Settings.score += newScore;
	}

	public static void setLives(int n) {
		if (lives>=0)lives=n;
	}

	public static int getLives() {
		return Math.max(0, lives);
	}

	public static void resetScores() {
		Settings.score = 0;
		//Settings.coins = 0;
	}

	public static int getLevelsUnlocked() {
		return levelsUnlocked;
	}

	public static void setLevelsUnlocked(int levelsUnlocked) {
		Settings.levelsUnlocked = levelsUnlocked;
	}
		
	
	public static boolean hasAccelerometer() {
		if (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)) {
			return true;
		}
		return false;
	}

	public static Mario getPayer() {
		return mario;
	}

	public static void setPlayer(Mario mario) {
		Settings.mario = mario;
	}
	
}
