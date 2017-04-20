package com.mygdx.game.mario.objects;



import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.game.TileMap;


/**
 * Static animated tiles
 * @author maheshkurmi
 *
 */
public class GameTile extends Animatible {
	
	// fields
	private int tileX;
	private int tileY;
	private int pixelX;
	private int pixelY;
	protected TextureRegion img;
	
	// fields
	private boolean isCollidable = true;
	private boolean isSloped = false;
	private List<Creature> collidingCreatures;
	
	
	/**
	 * Constructs a new GameTile at the pixel (x,y) position with the Animation anim
	 * and Image img.
	 */
	public GameTile(int pixelX, int pixelY, Animation anim, TextureRegion img) {
		setTileX(TileMap.pixelsToTiles(pixelX));
		setTileY(TileMap.pixelsToTiles(pixelY));
		this.pixelX = pixelX;
		this.pixelY = pixelY;
		this.img = img;
		setAnimation(anim);
		collidingCreatures = new LinkedList<Creature>();
	}
	
	/**
	 * Constructs a new GameTile at the pixel (x,y) position with no Animation
	 * and the constant Image img.
	 */
	public GameTile(int pixelX, int pixelY, TextureRegion img) {
		this(pixelX, pixelY, null, img);
	}
	
	
	public void draw(SpriteBatch g, int pixelX, int pixelY) {
		g.draw(getImage(), pixelX, pixelY);
	}
	
	public void draw(SpriteBatch g, int pixelX, int pixelY, int offsetX, int offsetY) {
		draw(g, pixelX + offsetX, pixelY + offsetY);
	}
	
	public TextureRegion getImage() {
		return (currentAnimation() == null) ? img : currentAnimation().getImage();
	}
	
	public int getPixelX() {
		return pixelX;
	}
	
	public int getPixelY() {
		return pixelY;
	}
	
	public int getWidth() {
		return getImage().getRegionWidth();
	}
	
	public int getHeight() {
		return getImage().getRegionHeight();
	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}
	/**
	 * Override to add action to this GameTile.
	 */
	public void doAction() { }
	
	/**
	 * @return true if this GameTile is collidable, else false.
	 */
	public boolean isCollidable() {
		return isCollidable;
	}
	
	/**
	 * @effects sets isCollidable to true or false.
	 */
	public void setIsCollidable(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}
	
	public boolean isSloped() {
		return isSloped;
	}
	
	public void setIsSloped(boolean isSloped) {
		this.isSloped = isSloped;
	}

	/**
	 * @return a list of Creatures who are currently colliding with this GameTile.
	 */
	public List<Creature> collidingCreatures() {
		return collidingCreatures;
	}
}
