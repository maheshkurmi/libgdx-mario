package com.shikhar.mario.objects.tiles;


import java.awt.Color;
import java.awt.Graphics;
import com.shikhar.mario.core.GameRenderer;
import com.shikhar.mario.core.tile.GameTile;


public class InfoPanel extends GameTile {
	
	private int width, height;
    private String info[];
     
	public InfoPanel(int pixelX, int pixelY, String info) {
		super(pixelX, pixelY, null, null);
		info = "Mahesh Kurmi;Android Mario V1.0";
		this.info = info.split(";");
		setIsCollidable(false);
		width=16;
		for (String s : this.info) {
			if (width < s.length() * 8)
				width = s.length() * 8;
		}
		width = width + 16;
		height = this.info.length * 12 + 8;

	}

	
	@Override
	public void update(int time) {
	
	}
	
	@Override
	public void draw(Graphics g, int x, int y) {
		}
	
	public void draw(Graphics g, int x, int y, int offsetX, int offsetY) {
		x+=offsetX;
		y+=offsetY;
			g.setColor(Color.ORANGE);
		g.fill3DRect(x, y, width, height, true);
		g.setColor(Color.white);
		g.draw3DRect(x, y, width, height, true);
	
		//	g.drawImage(currentAnimation().getImage(),x+offsetX , y+offsetY,x +getWidth()+offsetX,y+offsetY +  getHeight(),0,0,backPanel.getWidth(),backPanel.getHeight(),null);
		//GameRenderer.drawStringDropShadow(g, info, (x+offsetX+6)/8, (y+offsetY+4)/8);
		for (int i=0; i<info.length;i++){
			GameRenderer.drawStringDropShadow(g, info[i], (x+4)/8, (y+3+16*i)/8,-1);
		}
	}
	
	public String[] getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info=info.split(";");
		width=16;
		for (String s : this.info) {
			if (width < s.length() * 8)
				width = s.length() * 8;
		}
		width = width + 16;
		height = this.info.length * 12 + 8;

	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}

	
}
