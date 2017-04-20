package com.mygdx.game.mario.objects.gametiles;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.objects.GameTile;

/**
 * Not used in game
 * @author maheshkurmi
 *
 */
public class SlopedTile extends GameTile {
	
	private Vector2 startingPoint;
	private Vector2 endingPoint;
	private boolean hasPositiveSlope;
		
	public SlopedTile(int pixelX, int pixelY, TextureRegion img, boolean hasPositiveSlope) {
		
		super(pixelX, pixelY, null, img);
		setIsCollidable(false);
		setIsCollidable(true);
		setIsSloped(true);
		
		this.hasPositiveSlope = hasPositiveSlope;
		
		if(hasPositiveSlope) {
			startingPoint = new Vector2(pixelX, pixelY + 15);
			endingPoint = new Vector2(pixelX + 16, pixelY - 1);
		} else {
			startingPoint = new Vector2(pixelX, pixelY);
			endingPoint = new Vector2(pixelX + 16, pixelY + 14);
		}
	}
	
	public Vector2 getStartingPoint() {
		return startingPoint;
	}
	
	public Vector2 getEndingPoint() {
		return endingPoint;
	}
	
	public boolean hasPositiveSlope() {
		return hasPositiveSlope;
	}
	
	private int correlateY(float pixelX) {
		if(pixelX >= endingPoint.x) {
			return (int) endingPoint.y;
		} else if(pixelX <= startingPoint.x) {
			return (int) startingPoint.y;
		} else {
			return (int) (-((int) pixelX) + startingPoint.x + startingPoint.y);
		}
	}
	
	private int correlateX(float pixelY) {
		if(pixelY >= startingPoint.y) {
			return (int) startingPoint.x;
		} else if(pixelY <= endingPoint.y) {
			return (int) endingPoint.x;
		} else {
			return (int) (((int) pixelY) - startingPoint.y + startingPoint.x);
		}
	}
	
	public Vector2 isWithin(float pixelX, float pixelY, float deltaX, float deltaY) {
		int xOnLine = correlateX(pixelY);
		int yOnLine = correlateY(pixelX);
//		System.out.println();
//		System.out.println("X on Line: " + xOnLine);
//		System.out.println("Starting Point X: " + startingPoint.x);
//		System.out.println("Mario bottom right X: " + pixelX);
//		System.out.println("Ending Point X: " + endingPoint.x);
//		System.out.println("Y on Line: " + yOnLine);
//		System.out.println("Starting Point Y: " + startingPoint.y);
//		System.out.println("Mario bottom right Y: " + pixelY);
//		System.out.println("Ending Point Y: " + endingPoint.y);
		if(pixelX >= xOnLine && pixelX >= startingPoint.x && (pixelX <= endingPoint.x + 5 || pixelX + deltaX <= endingPoint.x) && 
		   pixelY >= yOnLine && pixelY >= endingPoint.y && (pixelY <= startingPoint.y + 1 || pixelY - deltaY <= startingPoint.y)
		   ) {
			//System.out.println("SLOPED COLLISION.");
			return new Vector2(xOnLine, yOnLine);
		} else {
			return null;
		}
	}
}
