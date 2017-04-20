package com.mygdx.game.mario;
/**
 * Constatnts for resources
 * @author maheshkurmi
 *
 */
public class Constants {
/** Visible game height in number of tiles (Here 20 times will be visible in y direction and number of times in x direction will 
automatically be calculated based on aspect ratio of screen*/
public static final float VIEWPORT_HEIGHT_TILES = 20f; 
/** Location of description file for texture atlas for mario*/
public static final String TEXTURE_ATLAS_MARIO ="assets-mario/images/mario.pack";     
/**Location of description file for texture atlas buddies/creatures*/
public static final String TEXTURE_ATLAS_CREATURES ="assets-mario/images/creatures.pack";
/**Location of description file for texture atlas game tiles and items*/
public static final String TEXTURE_ATLAS_GAMETILES ="assets-mario/images/gametiles.pack";
/**Location of description file for texture atlas for gui images in menu button, icon etc*/
public static final String TEXTURE_ATLAS_GUI ="assets-mario/images/gui.pack";
/**Location of description file for texture atlas for particles*/
public static final String TEXTURE_ATLAS_PARTICLES ="assets-mario/images/particles.pack";
/**Location of description file for texture atlas for icons*/
public static final String TEXTURE_ATLAS_ICONS ="assets-mario/images/icons.pack";

/**Location of default game background music*/
public static final String DEFAULT_GAME_MUSIC_PATH_1="assets-mario/music/smb-over.mp3";
public static final String DEFAULT_GAME_MUSIC_PATH_2="assets-mario/music/smb-over.mp3";
public static final String DEFAULT_GAME_MUSIC_PATH_3="assets-mario/music/mario_music.mp3";

/**Location of default main menu background music*/
public static final String DEFAULT_MENU_MUSIC_PATH="assets-mario/music/smb-map.mp3";

/**Location of default background image*/
public static final String DEFAULT_BACKGROUND_IMAGE_PATH="assets-mario/backgrounds/gui-background.png";

// Game preferences file
public static final String PREFERENCES = "mario.prefs";



}