package com.shikhar.mario.editor;

import java.awt.Image;
import java.awt.Rectangle;

public class Tool {
	private Rectangle rect;
	private Image image;
	
	public Tool(int x, int y, int Width, int Height, Image image) {
		setRect(new Rectangle(x, y, Width, Height));
		this.setImage(image);
	}

	public Tool(Image image) {
		rect=new Rectangle(0,0, image.getWidth(null),image.getHeight(null));
		this.setImage(image);
	}
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public boolean contains(int x, int y) {
		return rect.contains(x, y);
	}
}
