package com.shikhar.mario.util;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ImageManipulator.java
 * @author Forrest
 * 
 * Provides a set of methods used to modify a BufferedImage.
 * 
 */
public class ImageManipulator {
	
	/** Reads in a BufferedImage using the standard ImageIO.read() */
	public static BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(filename));
		} catch (IOException e) { }
		return img;
	} 

	/** Horizontally flips img. */
	public static BufferedImage horizontalFlip(BufferedImage img) {   
        int w = img.getWidth();   
        int h = img.getHeight();   
        BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());     
        Graphics2D g = dimg.createGraphics();   
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);   
        g.dispose();   
        return dimg;   
    }  
	
	/** Vertically flips img. */
	public static BufferedImage verticalFlip(BufferedImage img) {   
        int w = img.getWidth();   
        int h = img.getHeight();   
        BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());   
        Graphics2D g = dimg.createGraphics();   
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);   
        g.dispose();   
        return dimg;   
    }  

}
