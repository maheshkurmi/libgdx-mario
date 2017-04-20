package com.shikhar.mario.util;
// Object responsible for dealing with and cutting up a BufferedImage into individual pieces
// - Contains a BufferedImage spriteMap which is the image to be cut 
// - Contains an array of BufferedImages, sprites[], which store the cut images

// To use this object, create a SpriteMap with parameters image to be cut,
// the number of columns, and the number of rows. Then call getSprites() on
// the object to retrieve the array of cut images.

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class SpriteMap {
	
    private BufferedImage spriteMap;
    private BufferedImage[] sprites; 
    
    public SpriteMap(String filename, int c, int r) {
        this.loadSprites("java-mario-src/"+filename, c, r);
    }
    
    // returns the sprites array
	public BufferedImage[] getSprites() {
		return sprites;
	}
    
    // loads a BufferedImage into spriteSheet and then cuts the image
    // into individual sprites based on amount of columns and rows
    private void loadSprites(String filename, int c, int r) {
    	spriteMap = loadImage(filename);
    	sprites = splitSprites(c, r);
    }
    
	// loads a BufferedImage and returns it
	private BufferedImage loadImage(String ref) {   
        BufferedImage bimg = null;   
        try {   
            bimg = ImageIO.read(new File(ref));   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        return bimg;   
    }
	
	// Splits a given sprite sheet into it's individual sprites and
	// returns the array containing each sprite from left to right, top to bottom.
	// This is accomplished by drawing a portion of the larger image onto a new BufferedImage
	// by calling the graphics of each new BufferedImage.
	private BufferedImage[] splitSprites(int c, int r) {
		int pWidth = spriteMap.getWidth() / c; // width of each sprite
		int pHeight = spriteMap.getHeight() / r; // height of each sprite
		BufferedImage[] sprites = new BufferedImage[c*r];
		int n = 0; // used to count sprites
		
		//int xOff = 0; if needed to adjust cutting precision
		int yOff = 0;
	
		for(int y=0; y < r; y++) {
			for(int x = 0; x < c; x++) {
				sprites[n] = new BufferedImage(pWidth, pHeight, 2);
                Graphics2D g = sprites[n].createGraphics(); // retrieve graphics to draw onto the BufferedImage
                // draws a portion of the spriteMap into sprites by directly drawing on the BufferedImage
                g.drawImage(spriteMap, 0, 0, pWidth, pHeight, pWidth*x, pHeight*y, pWidth*x+pWidth, pHeight*y+pHeight-yOff, null); 
                g.dispose();
                n++; // next sprite
			}
		}
		return sprites;
	} 
}
