package com.shikhar.mario.core;

import com.shikhar.mario.objects.mario.Mario;


public class Settings {
	public static boolean soundEnabled = true;
    public static boolean musicEnabled = true;
    
    public static int soundVolume = 50;
    public static int musicVolume = 50;
    public static int tiltSensitivity = 50;
    public static int movementSensitivity = 50;
    
   // public static int mLeftKeyCode = KeyEvent.KEY_LOCATION_LEFT;
    //public static int mRightKeyCode = KeyEvent.KEY_LOCATION_RIGHT;
   // public static int mJumpKeyCode = KeyEvent.ke;
    //public static int mAttackKeyCode = KeyEvent.KEYCODE_SHIFT_LEFT;
    
    public static  boolean mUseClickButtonForAttack = true;
    public static  boolean mUseOrientationForMovement = false;
    public static  boolean mUseOnScreenControls = false;

    public static int[] highScores = new int[] { 0, 0, 0, 0, 0 ,0,0,0,0,0};
    public  static int[] recodTimes =new int[] { 10000, 10000, 10000, 10000, 10000 ,10000,10000,10000,10000,10000};
    
    public final static String file = ".supermario";

    private static int score=0;
	private static int lives=0;
	private static int time=0;
	private static int coins=0;
	private static int levelsUnlocked=0;
	private static int worldsUnlocked=0;
	//private static Context context;
    private static Mario mario;
	
	
	public static int getWorldsUnlocked() {
		return worldsUnlocked;
	}

	public static void setWorldsUnlocked(int worldsUnlocked) {
		Settings.worldsUnlocked = worldsUnlocked;
	}

	public static int level = 1;
	   public static int world = 1;
	   
	public static final int COIN_BONUS=100;
	public static final int GOOMBA_BONUS=300;
	public static final int KOOPA_BONUS=500;
	public static final int LIFE_BONUS=1000;

	public static float accelerometerSenseFactor=1;
 

	public static boolean addHighScore(int world, int level,int newHighScore) {
		/*
		if (highScores[i]<newHighScore){
			highScores[i]=newHighScore;
			SharedPreferences sharedPref = context.getSharedPreferences(PreferenceConstants.PREFERENCE_SCORE_NAME, Context.MODE_PRIVATE);
			Editor editor = sharedPref.edit();
			editor.putInt("levelScore"+i, highScores[i]);
			editor.commit();
			return true;
		}
		*/
		return false;
	}
    
	public static boolean addRecordTime(int world, int level,int newTime) {
		int i=world*3+level-4;
		if (recodTimes[i]>newTime){
			recodTimes[i]=newTime;
			/*
			SharedPreferences sharedPref = context.getSharedPreferences(PreferenceConstants.PREFERENCE_SCORE_NAME, Context.MODE_PRIVATE);
			Editor editor = sharedPref.edit();
			editor.putInt("levelTime"+i, recodTimes[i]);
			editor.commit();
			*/
			return true;
		}
		return false;
	}
    
	
	public static void saveScores(){
		/*
		SharedPreferences sharedPref = context.getSharedPreferences("highScores", Context.MODE_PRIVATE);
		Editor editor = sharedPref.edit();
		 for (int i=0; i<highScores.length;i++){
			 editor.putInt("levelScore"+i, highScores[i]);
			 editor.putInt("levelTime"+i, recodTimes[i]);
	     }
		 editor.commit();
		 */
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
		
	
	
	public static Mario getPayer() {
		return mario;
	}

	public static void setPlayer(Mario mario) {
		Settings.mario = mario;
	}
}
