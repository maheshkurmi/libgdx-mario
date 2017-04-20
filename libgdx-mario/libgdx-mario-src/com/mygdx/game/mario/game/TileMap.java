package com.mygdx.game.mario.game;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.objects.CollidableObject;
import com.mygdx.game.mario.objects.Creature;
import com.mygdx.game.mario.objects.GameTile;
import com.mygdx.game.mario.objects.creatures.Platform;
import com.mygdx.game.mario.objects.gametiles.SlopedTile;
import com.mygdx.game.mario.objects.mario.Mario;
import com.mygdx.game.mario.objects.particles.ParticleSystem;



/**
 * The TileMap class contains all data for a tile-based map. 
 * @author maheshkurmi
 *
 */
public class TileMap {
	
	// fields
	private GameTile[][] tiles; 
	private List<Platform> platforms; // List of Platforms on the current screen.
	private List<Creature> creatures; // Starts containing every Creature and decreases as they die.
	private List<Creature> relevantCreatures; // List of relevant Creatures to the current frame.
											  // This is a subset of creatures.
	private List<Creature> creaturesToAdd; // List of Creatures to be added inbetween frames.
	private List<GameTile> animatedTiles;
	private List<SlopedTile> slopedTiles;
	private Mario player; 
	private boolean visible =true;
	public ParticleSystem particleSystem;
	private ArrayList<Vector2> bookMarks;
	private List<Rectangle> waterZones; // List of Water Zones.

	// the size in bits of the tile
    public static final int TILE_SIZE = 16;
    // Math.pow(2, TILE_SIZE_BITS) == TILE_SIZE
    private static final int TILE_SIZE_BITS = 4;

	/**
	 * Constructs a new TileMap with the specified width and height (in number of tiles)
	 * of the map.
	 */
	public TileMap(int width, int height) {
		tiles = new GameTile[width][height];
		creatures = new LinkedList<Creature>();
		relevantCreatures = new ArrayList<Creature>();
		creaturesToAdd = new ArrayList<Creature>();
		platforms = new ArrayList<Platform>();
		animatedTiles = new ArrayList<GameTile>();
		slopedTiles = new ArrayList<SlopedTile>();
		bookMarks=new ArrayList<Vector2>();
		waterZones=new ArrayList<Rectangle>();
		
	}
	
	public GameTile[][] getTiles() {
		return tiles;
	}
	
	/**
	 * @return the width of this TileMap in GameTiles.
	 */
	public int getWidth() {
		return tiles.length;
	}
	
	/**
	 * @return the height of this TileMap in GameTiles.
	 */
	public int getHeight() {
		return tiles[0].length;
	}
	
	
	public GameTile getTile(float x, float y) {
		return getTile((int) x, (int) y) ;
	}

	/**
	 * @return the GameTiles at tiles[x][y]. If x or y is out of bounds
	 * or if tiles[x][y] == null, null is returned.
	 */
	public GameTile getTile(int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			return null;
		} else {
			if(tiles[x][y] != null) {
				return tiles[x][y];
			} else {
				return null;
			}
		}
	}
	
	/**
	 * @return the image of the GameTiles at tiles[x][y]. If x or y is out of bounds
	 * or if tiles[x][y] == null, null is returned.
	 */
	public TextureRegion getImage(int x, int y) {
		if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
			return null;
		} else {
			if(tiles[x][y] != null) {
			     return tiles[x][y].getImage();
			} else {
				return null;
			}
		}
	}
	
	/**
	 * Sets tiles[x][y] equal to parameter tile.
	 * This is used to set animated GameTiles.
	 */
	public void setTile(int x, int y, GameTile tile) {
		tiles[x][y] = tile;
	}
	
	/**
	 * Sets tiles[x][y] equal to a new Tile with no animation and the constant Image img.
	 * This is used to set non-animated GameTiles.
	 */
	public void setTile(int x, int y, TextureRegion img) {
		tiles[x][y] = new GameTile(x, y, null, img);
	}
	
	/**
	 * @return the player sprite.
	 */
	public Mario getPlayer() {
		return player;
	}
	
	public void clearBookMarks(){
		bookMarks.clear();
	}
	
	public void addBookMark(Vector2 pt){
		bookMarks.add(pt);
	}
	
	public Vector2 getRecentbookMarkLocation(){
		Vector2 p=new Vector2(2,10);
		//p.x=GameRenderer.pixelsToTiles(player.getX());
		for (Vector2 pt: bookMarks){
			if (pt.x<TileMap.pixelsToTiles(player.getX())){
				if (pt.x>p.x)p=pt;
			}
		}
		return p;
	}
	public Vector2 getbookMarkLocation(int x , int Y){
		Vector2 p=new Vector2(2,2);
		for (Vector2 pt: bookMarks){
			if (pt.x>x) p=pt;
		}
		return p;
	}
	/**
	 * Sets the player sprite for this map.
	 */
	public void setPlayer(Mario player) {
		this.player = player;
		player.map=this;
	}
	
	 
	/**
	 * @return a List containing every Platform in this map.
	 */
	public List<Platform> platforms() {
		return platforms;
	}
	
	/**
	 * @return a List containing every Creature in this map.
	 */
	public List<Creature> creatures() {
		return creatures;
	}
	
	/**
	 * @return a List containing Creatures to add to this map after the next game update.
	 */
	public List<Creature> creaturesToAdd() {
		return creaturesToAdd;
	}
	
	/**
	 * @return a List containing animated Tile in this map.
	 */
	public List<GameTile> animatedTiles() {
		return animatedTiles;
	}
	
	/**
	 * @return a List containing every SlopedTile in this map.
	 */
	public List<SlopedTile> slopedTiles() {
		return slopedTiles;
	}
	
	/**
	 * @return a List containing every relevant Creature in this map. 
	 * 
	 * A 'relevant Creature' is a Creature that the current frame cares about. 
	 * This is generally creatures on screen or creatures that need to be updated globally. 
	 */
	public List<Creature> relevantCreatures() {
		return relevantCreatures;
	}

	/**
	 * @return a List containing bookmarks in this map.
	 */
	public List<Vector2> bookMarks() {
		return bookMarks;
	}
	
	public void addWaterZone(Rectangle zone) {
		waterZones.add(zone);
	}

	public List<Rectangle> waterZones() {
		return waterZones;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
	  /**
     *  Converts a pixel position to a tile position.
     */
    public static int pixelsToTiles(float pixels) {
        return pixelsToTiles(Math.round(pixels));
    }

	  /**
     * 
     *  Converts a pixel position to a tile position.
     * @param pixels
     * @return
     */
    public static int pixelsToTiles(int pixels) {
        // use shifting to get correct values for negative pixels
        return pixels >> TILE_SIZE_BITS;
        // or, for tile sizes that aren't a power of two,
        // use the floor function: return (int)Math.floor((float)pixels / TILE_SIZE);
    }

    public static int tilesToPixels(float numTiles) {
    	return tilesToPixels((int)numTiles);
    }	  
    	
    /**
     * Converts a tile position to a pixel position.
     * @param numTiles
     * @return
     */
    public static int tilesToPixels(int numTiles) {
        // no real reason to use shifting here. it's slighty faster, but doesn't add up to much
        // on modern processors.
        return numTiles << TILE_SIZE_BITS;
        // use this if the tile size isn't a power of 2:
        //return numTiles * TILE_SIZE;
    }
    
	/**
	 *  Returns the tile that a CREATURE has collided with. Returns null if no collision was detected. The last parameter, right, is used to check if multiple blocks
	// are hit when a sprite jumps.
	 * @param map
	 * @param sprite
	 * @param currX
	 * @param currY
	 * @param newX
	 * @param newY
	 * @return
	 */
	// 
	public static Vector2 getTileCollision(TileMap map, CollidableObject obj, float currX, float currY, float newX, float newY) {

	    float fromX = Math.min(currX, newX);
	    float fromY = Math.min(currY, newY);
	    float toX = Math.max(currX, newX);
	    float toY = Math.max(currY, newY);
	
	    // get the tile locations
	    int fromTileX = pixelsToTiles(fromX);
	    int fromTileY = pixelsToTiles(fromY);
	    int toTileX = pixelsToTiles(toX + obj.getWidth() - 1);
	    int toTileY = pixelsToTiles(toY + obj.getHeight() - 1);
	
	    // check each tile for a collision
	    for (int x=fromTileX; x<=toTileX; x++) {
	        for (int y=fromTileY; y<=toTileY; y++) {
	            if (x < 0 || x >= map.getWidth() || map.getImage(x, y) != null) {
	            	GameTile tile = map.getTile(x,y);
	            	if(tile != null && map.getTile(x, y).isCollidable()) {
	                // collision found and the tile is collidable, return the tile
	            		return new Vector2(x,y);
	            	} 
	            }
	        }
	    }
	    // no collision found, return null
	    return null;
	}
	
	/**
	 * @return A List of Points, where each Point corresponds to the location of a tile the sprite is 
	 * colliding with in map.tiles().
	 */
	public static ArrayList<Vector2> getTileCollisionAll(TileMap map, CollidableObject obj, float currX, float currY, float newX, float newY) {
		
		ArrayList<Vector2> collisionPoints = new ArrayList<Vector2>(); 
	    float fromX = Math.min(currX, newX);
	    float fromY = Math.min(currY, newY);
	    float toX = Math.max(currX, newX);
	    float toY = Math.max(currY, newY);
	
	    // get the tile locations
	    int fromTileX = pixelsToTiles(fromX);
	    int fromTileY = pixelsToTiles(fromY);
	    int toTileX = pixelsToTiles(toX + obj.getWidth() - 1);
	    int toTileY = pixelsToTiles(toY + obj.getHeight() - 1);
	
	    // check each tile for a collision
	    for (int x=fromTileX; x<=toTileX; x++) {
	        for (int y=fromTileY; y<=toTileY; y++) {
	            if (x < 0 || x >= map.getWidth() || map.getImage(x, y) != null) {
	            	GameTile tile = map.getTile(x,y);
	            	if(tile != null && map.getTile(x, y).isCollidable()) {
	                // collision found and the tile is collidable, return the tile
	            		collisionPoints.add(new Vector2(x,y));
	            	} 
	            }
	        }
	    }
	    // no collision found, return null
	    return collisionPoints;
	}
    

}
