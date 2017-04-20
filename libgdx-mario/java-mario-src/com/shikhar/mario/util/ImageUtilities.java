package com.shikhar.mario.util;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

 /**
 * Utility class for loading, managing, and modifying images.
 * @author Mahesh kurmi
 * @version 1.0.0
 * @since 1.0.0
 */

public class ImageUtilities {
	
	/** Reads in a BufferedImage using the standard ImageIO.read() */
	public static BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("java-mario-src/"+filename));
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


	
	/**
	 * Returns a BufferedImage from the classpath given the path.
	 * @param path the path within the classpath
	 * @return BufferedImage
	 * @throws IOException thrown if an error occurs reading the file
	 */
	public static final BufferedImage getImageFromClassPath(String path) throws IOException {
		URL url = ImageUtilities.class.getResource(path);
		return ImageIO.read(url);
	}
	
	/**
	 * Returns an Icon from the classpath given the path.
	 * @param path the path within the classpath
	 * @return ImageIcon
	 * @throws IOException thrown if an error occurs reading the file
	 */
	public static final ImageIcon getIconFromClassPath(String path) throws IOException {
		BufferedImage bi = ImageUtilities.getImageFromClassPath(path);
		return new ImageIcon(bi);
	}
	
	/**
	 * Returns a BufferedImage from the classpath given the path.
	 * @param path the path within the classpath
	 * @return BufferedImage
	 */
	public static final BufferedImage getImageFromClassPathSuppressExceptions(String path) {
		try {
			return ImageUtilities.getImageFromClassPath(path);
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Returns an Icon from the classpath given the path.
	 * @param path the path within the classpath
	 * @return ImageIcon
	 */
	public static final ImageIcon getIconFromClassPathSuppressExceptions(String path) {
		try {
			BufferedImage bi = ImageUtilities.getImageFromClassPath(path);
			return new ImageIcon(bi);
		} catch (IOException e) {
			return null;
		}
	}
}
