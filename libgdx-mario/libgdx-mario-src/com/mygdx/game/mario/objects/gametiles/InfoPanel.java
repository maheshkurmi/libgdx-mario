package com.mygdx.game.mario.objects.gametiles;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.mario.game.GameRenderer;
import com.mygdx.game.mario.objects.GameTile;

/**
 * Displays info/message box
 * @author maheshkurmi
 *
 */
public class InfoPanel extends GameTile {
	protected Color borderColor = Color.BLACK, bgColorNormal = Color.LIGHT_GRAY;
	protected Color bgColorFocused = Color.DARK_GRAY, foreColor = Color.BLACK;
	protected int borderWidth = 2;

    private String info[];
    private int width=0, height;
	
    public InfoPanel(int pixelX, int pixelY, String info) {
		super(pixelX, pixelY,null,null);
    	if (info ==null)info="Mahesh Kurmi;Android Mario V1.0";
    	this.info=info.split(";");
		setIsCollidable(false);
		height=24;//backPanel.getHeight();
		for (String s:this.info){
			if (width<s.length()*8)width=s.length()*8;
		}
		width=width+16;
		height=this.info.length*12+8;
		
	}
	
	@Override
	public void update(int time) {
	
	}
	
	@Override
	public void draw(SpriteBatch g, int x, int y) {
		//g.drawBitmap(currentAnimation().getImage(),x+getOffsetX() , y+getOffsetY(),x +getWidth()+getOffsetX(),y+getOffsetY() +  getHeight(),0,0,getWidth(),getHeight(),null);
		//GameRenderer.drawStringDropShadow(g, info, x+getOffsetX()+6, y+getOffsetY()+5);
		draw( g,  x,  y, 0, 0);
	}
	
	public void draw(SpriteBatch g, int x, int y, int offsetX, int offsetY) {
		x+=offsetX;
		y+=offsetY;
		
		//paint.setAntiAlias(true);
		bgColorNormal = Color.LIGHT_GRAY;//new Color(0.4f, 0.8f, 0,0.5f);
		bgColorNormal.a=0.5f;
		GameRenderer.drawCircle(x+5, y-10, 2, bgColorNormal, Color.DARK_GRAY, g);
		GameRenderer.drawCircle(x+width-5, y-10, 2, bgColorNormal, Color.DARK_GRAY, g);
		GameRenderer.drawRect(x, y, width, height,bgColorNormal,Color.DARK_GRAY,g);
		GameRenderer.drawLine(x+5, y, x+5, y-8, Color.DARK_GRAY,g);
		GameRenderer.drawLine(x+width-5, y, x+width-5, y-8, Color.DARK_GRAY,g);
	
		for (int i=0; i<info.length;i++){
			GameRenderer.drawSmallString(g, info[i], Color.WHITE,x+width/2, y+6+12*i,0);
		}
	
	}
	
	public String[] getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info=info.split(";");
		width=16;
		for (String s : this.info) {
			if (width < s.length() * 5)
				width = s.length() * 5;
		}
		width = width+8;
		height = this.info.length * 12 + 6;

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
