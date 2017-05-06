package com.mygdx.game.mario.game;



import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.Settings;
import com.mygdx.game.mario.game.screens.AbstractGame;
import com.mygdx.game.mario.game.screens.LevelCompleteScreen;
import com.mygdx.game.mario.objects.Creature;
import com.mygdx.game.mario.objects.GameTile;
import com.mygdx.game.mario.objects.mario.Mario;



/**
 * The GameRenderer class is responsible for all the drawing onto the screen.
 Also contains useful static methods for converting tiles->pixels, pixels->tiles
 and a method for locating which tile a sprite has collided with.
 * @author maheshkurmi
 *
 */
public class GameRenderer {
	
	// AdjustYScroll is used to record the previous value of player.getY(). This way I can 
	// continue to draw on the same y level if there is no significant change in Y. I use 
	// the player jumping as a measure of significant change. Hides errors in my animations, 
	// keeping the screen from bobbing when there is a change in height of the player animation. 
	private int AdjustYScroll = 0;
	private  ArrayList<TileMap> maps = new ArrayList<TileMap>();
	public boolean drawHudEnabled=true;
    private  TextureRegion background;
    public static int xOffset=0;
	public static int yOffset=0;
	int waveOffset=0;
	AbstractGame game;
	private static ShapeRenderer shapeRenderer=new ShapeRenderer();
	 
	public GameRenderer (AbstractGame game) {
			this.game=game;
			shapeRenderer=new ShapeRenderer();
			
	}
    /**
     *  Sets the background to draw.
     * @param background
     */
    public void setBackground(TextureRegion background) {
        this.background = background;
    }
    
    public  void draw(SpriteBatch g,TileMap mainMap, TileMap backgroundMap,
			TileMap foregroundMap, int screenWidth, int screenHeight) {
    	draw(g,mainMap,backgroundMap,foregroundMap,screenWidth,screenHeight,false);
    }
	
	/**
	 * Draws all game elements. I did the best I can to separate all updating
	 * from drawing. However, it seems its much more efficient to do some
	 * updating here where I have all the information I need to make important
	 * decisions. So calling draw() DOES change the game state.
	 */
	public  void draw(SpriteBatch g,TileMap mainMap, TileMap backgroundMap,
			TileMap foregroundMap, int screenWidth, int screenHeight,boolean editMode) {
			// add the three maps to the list of maps to draw, only mainMap is
		// interactive
		if (backgroundMap != null)
			maps.add(backgroundMap);
		maps.add(mainMap);
		if (foregroundMap != null)
			maps.add(foregroundMap);
		Mario player = mainMap.getPlayer();
		int mapWidth = TileMap.tilesToPixels(mainMap.getWidth());
		int mapHeight = TileMap.tilesToPixels(mainMap.getHeight());

		// get the scrolling position of the map based on player's position...

		int offsetX = (screenWidth / 2 - Math.round(player.getX()) -TileMap.TILE_SIZE);
		offsetX = Math.min(offsetX, 0); // if this gets set to 0, player is
										// within a screen width
		offsetX = Math.max(offsetX, screenWidth - mapWidth);

		int round = Math.round(player.getY());

		// initialize AdjustYScroll
		if (AdjustYScroll == 0) {
			AdjustYScroll = round;
		}

		// if the player is jumping, change the level at which the screen is
		// drawn.
		if (player.isJumping() || player.isAbovePlatform()
				|| player.isOnSlopedTile()) {
			AdjustYScroll = round;
		}

		int offsetY = screenHeight / 2 - AdjustYScroll - TileMap.TILE_SIZE;
		offsetY = Math.min(offsetY, 0);
		offsetY = Math.max(offsetY, screenHeight - mapHeight);
		
		// draw parallax background image
		if (background != null && !editMode) {
			
			float w=mainMap.getWidth()*TileMap.TILE_SIZE;
			float bgW=background.getRegionWidth();
			float bgH=background.getRegionHeight();
			//float u1=3*player.getX()/mapWidth;
			float u1=w/(2*bgW)*player.getX()/mapWidth;
			g.draw(background.getTexture(),0, 0,bgW*(1-u1), bgH,u1,0,1,1);
			int i=0;
			while(bgW*(1-u1)+bgW*i<w){
				g.draw(background.getTexture(),bgW*(1-u1)+bgW*i, 0,bgW, bgH,0,0,1,1);
				i++;
			}
			/*
			 //Doest work in html, draws black background, dont know why
			if(background.getTexture().getUWrap()==TextureWrap.MirroredRepeat ||background.getTexture().getUWrap()==TextureWrap.Repeat){
				float u2=((float)screenWidth)  / background.getTexture().getWidth();
				float v2=((float)screenHeight)  / background.getTexture().getHeight();
				float u1=3*player.getX()/mapWidth;
				g.draw(background.getTexture(), 0, 0, screenWidth, screenHeight,u1, 0,u1+u2,v2);
			}
			*/
		}
		if (editMode) {
	        	drawGrids(screenHeight,screenWidth, mapHeight,mapWidth,g);
	        	offsetX=Math.round(offsetX/TileMap.TILE_SIZE)*TileMap.TILE_SIZE;
	    		offsetY=Math.round(offsetY/TileMap.TILE_SIZE)*TileMap.TILE_SIZE;
	    }

	
		//Store offsetValues
		GameRenderer.xOffset=offsetX;
		GameRenderer.yOffset=offsetY;

		int firstTileX = TileMap.pixelsToTiles(-offsetX-TileMap.TILE_SIZE); //draw extra tile to left
		int lastTileX = firstTileX + TileMap.pixelsToTiles(screenWidth) + 2; //draw extra tile to right
		int firstTileY = TileMap.pixelsToTiles(-offsetY);
		int lastTileY = firstTileY + TileMap.pixelsToTiles(screenHeight) + 1;

		if (lastTileX>= mainMap.getWidth())lastTileX= mainMap.getWidth()-1;
		for (TileMap map : maps) {
			// draw the visible tiles
			if (map != null && map.isVisible()) {
				for (int y = firstTileY; y <= lastTileY; y++) {
					for (int x = firstTileX; x <= lastTileX; x++) {
						GameTile tile = map.getTile(x, y);
						if (tile != null) {
							tile.draw(g, TileMap.tilesToPixels(x), TileMap.tilesToPixels(y),
									tile.getOffsetX() + offsetX,
									tile.getOffsetY() + offsetY);
						}
					}
				}
			}

			if (map.isVisible()) {

				for (int i = 0; i < map.creatures().size(); i++) {
					Creature c = map.creatures().get(i);
					int x = Math.round(c.getX()) + offsetX;
					int y = Math.round(c.getY()) + offsetY;
					int tileX = TileMap.pixelsToTiles(x);
					int tileY = TileMap.pixelsToTiles(y);

						if (Creature.WAKE_UP_VALUE_UP_LEFT <= tileX
								&& Creature.WAKE_UP_VALUE_DOWN_RIGHT >= tileX
								&& Creature.WAKE_UP_VALUE_UP_LEFT <= tileY
								&& Creature.WAKE_UP_VALUE_DOWN_RIGHT >= tileY) {
							c.setIsOnScreen(true);
							if (!c.isInvisible()) {
								c.draw(g, x, y); // draw the creature
							}
						} else {
							c.setIsOnScreen(false);
						}
					
				}
				// Draw the player.

				if (map == mainMap && !(((Mario) player).isInvisible())) {
					((Mario) player).draw(g, Math.round(player.getX())
							+ offsetX, Math.round(player.getY()) + offsetY,
							player.getOffsetX(), player.getOffsetY());
					if (map.particleSystem != null) {
						map.particleSystem.doDraw(g, offsetX, offsetY);
					}

				}
			}
		}

          if (drawHudEnabled){
        	
        	  drawStringDropShadow(g,"MARIO x "+Settings.getLives(),4,4,-1);
        	  drawStringDropShadow(g,Settings.getScore()+"",4,16,-1);
        	  g.draw(MarioResourceManager.instance.creatures.Coin_Icon, 68,2.5f);
        	  drawStringDropShadow(g,"x "+Settings.getCoins(),80,4,-1);
	    	  drawStringDropShadow(g,"WORLD  "+Settings.world + "-" + Settings.level,120,4,-1);
	    	  drawStringDropShadow(g,"TIME-"+Settings.getTime(),screenWidth-8,3,1);
        
        }	
        
      
     		waveOffset+=22;//for water wave animation
    		if (waveOffset>=1600)waveOffset=0;

    	
    		for(int i=0; i<mainMap.waterZones().size();i++){
    			Rectangle VisibleRect=new  Rectangle(firstTileX,firstTileY,lastTileX-firstTileX+1,lastTileY-firstTileY+1);
    			if (intersect(VisibleRect,mainMap.waterZones().get(i),VisibleRect)){
    				
    				float h=MarioResourceManager.instance.gameTiles.waterWaves[0].getRegionHeight();
    				float w=MarioResourceManager.instance.gameTiles.waterWaves[0].getRegionWidth();
    				Color col=	new Color( 72/255f, 120/255f,212/255f,62/255f);

    				drawRect(VisibleRect.x*16+offsetX,VisibleRect.y*16+16+offsetY,VisibleRect.width*16,VisibleRect.height*16-16,col,null,g);
    				drawRect(VisibleRect.x*16+offsetX,VisibleRect.y*16+16+offsetY,VisibleRect.width*16,VisibleRect.height*16-16,col,null,g);
         			
    				int n=waveOffset/200;
    				float x=VisibleRect.x*16+offsetX;
    				float y=VisibleRect.y*16+16+offsetY-h;
    				TextureRegion r=MarioResourceManager.instance.gameTiles.waterWaves[n];
    				//draw water waves
    				float dv=r.getV2()-r.getV();
					float du=r.getU2()-r.getU();
    				while(x<=VisibleRect.x*16+offsetX+VisibleRect.getWidth()*16-w){
    					g.draw(r.getTexture(),x , y+h*0.4f,w, 0.6f*h,r.getU(),r.getV2(),r.getU2(),r.getV()+dv*0.4f);
    					g.draw(r.getTexture(),x , y+h*0.4f,w, 0.6f*h,r.getU(),r.getV2(),r.getU2(),r.getV()+dv*0.4f);
    					x+=w;
    				}
					
					if((int)VisibleRect.width % 2 !=0){
						g.draw(r.getTexture(),x , y+h/3,w/2, 2*h/3,r.getU(),r.getV2(),(r.getU2()+r.getU())/2,r.getV()+dv/3);
						g.draw(r.getTexture(),x , y+h/3,w/2, 2*h/3,r.getU(),r.getV2(),(r.getU2()+r.getU())/2,r.getV()+dv/3);
					}
     	    	}
    		}
  
        //Draw Prev Next Buttons
        if (Settings.mUseOnScreenControls && !(game.getScreen() instanceof LevelCompleteScreen)){
    	  g.draw(MarioResourceManager.instance.gui.Btn_Prev,5,screenHeight-54);
       	  g.draw(MarioResourceManager.instance.gui.Btn_Next,screenWidth-53,screenHeight-54);
       	 
       }
       
        maps.clear(); 
    }
    
	/** Determines whether the supplied rectangles intersect and, if they do,
	 *  sets the supplied {@code intersection} rectangle to the area of overlap.
	 * 
	 * @return whether the rectangles intersect
	 */
	static public boolean intersect(Rectangle rectangle1, Rectangle rectangle2, Rectangle intersection) {
	    if (rectangle1.overlaps(rectangle2)) {
	        intersection.x = Math.max(rectangle1.x, rectangle2.x);
	        intersection.width = Math.min(rectangle1.x + rectangle1.width, rectangle2.x + rectangle2.width) - intersection.x;
	        intersection.y = Math.max(rectangle1.y, rectangle2.y);
	        intersection.height = Math.min(rectangle1.y + rectangle1.height, rectangle2.y + rectangle2.height) - intersection.y;
	        return true;
	    }
	    return false;
	}
	
	/**Fill Screen with Overlay color*/
	public void drawOverLay(Color color,SpriteBatch batch){
	   boolean wasDrawing=false;
		if(batch.isDrawing()){
			wasDrawing=true;
			batch.end();
		}
    	Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(0, 0, game.WIDTH, game.HEIGHT);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        if(wasDrawing) batch.begin();
	}

	private void drawGrids(int screenHeight, int screenWidth,int maxHeight,int maxWidth,SpriteBatch batch) {
		boolean wasDrawing=false;
		if(batch.isDrawing()){
			wasDrawing=true;
			batch.end();
		}
		screenHeight=Math.min(screenHeight,maxHeight);
		screenWidth=Math.min(screenWidth,maxWidth);
		Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.LIGHT_GRAY);
		for (int y=0;y<screenHeight ;y+=TileMap.TILE_SIZE){
			shapeRenderer.line(0, y, screenWidth,y);
		}
		for (int x=0;x<screenWidth ;x+=TileMap.TILE_SIZE){
			shapeRenderer.line(x, 0, x,screenHeight);
		}
		 shapeRenderer.end();
	        Gdx.gl.glDisable(GL20.GL_BLEND);
		 if(wasDrawing) batch.begin();
	}
	

	/**Fill Screen with Overlay color*/
	public static void drawCircle(float x, float y, float radius,Color fillColor,Color drawColor,SpriteBatch batch){
	   boolean wasDrawing=false;
		if(batch.isDrawing()){
			wasDrawing=true;
			batch.end();
		}
    	Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        if(fillColor!=null){
        	shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        	shapeRenderer.setColor(fillColor);
        	shapeRenderer.circle(x, y, radius);
        	shapeRenderer.end();
        }
        if(drawColor!=null){
        	shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        	shapeRenderer.setColor(drawColor);
        	shapeRenderer.circle(x, y, radius);
        	shapeRenderer.end();
        }
        
        Gdx.gl.glDisable(GL20.GL_BLEND);
        if(wasDrawing) batch.begin();
	}
	

	public static void drawRect(float x, float y, float width, float height,Color fillColor,Color drawColor,SpriteBatch batch){
	   boolean wasDrawing=false;
		if(batch.isDrawing()){
			wasDrawing=true;
			batch.end();
		}
    	Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        if(fillColor!=null){
        	shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        	shapeRenderer.setColor(fillColor);
        	 shapeRenderer.rect(x, y, width, height);
        	shapeRenderer.end();
        }
        if(drawColor!=null){
        	shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        	shapeRenderer.setColor(drawColor);
        	 shapeRenderer.rect(x, y, width, height);
        	shapeRenderer.end();
        }
        
        Gdx.gl.glDisable(GL20.GL_BLEND);
        if(wasDrawing) batch.begin();
	}
	
	public static void drawLine(float x1, float y1, float x2, float y2,Color drawColor,SpriteBatch batch){
		   boolean wasDrawing=false;
			if(batch.isDrawing()){
				wasDrawing=true;
				batch.end();
			}
	    	Gdx.gl.glEnable(GL20.GL_BLEND);
	        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
	        if(drawColor!=null){
	        	shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	        	shapeRenderer.setColor(drawColor);
	        	shapeRenderer.line(x1, y1, x2, y2);
	        	shapeRenderer.end();
	        }
	        
	        Gdx.gl.glDisable(GL20.GL_BLEND);
	        if(wasDrawing) batch.begin();
		}

    public void drawText(SpriteBatch g ,String line, int x, int y) {
		MarioResourceManager.defaultNormal.draw(g, line, x, y);//, start, end, targetWidth, halign, wrap, truncate);//
	}
	
	/**
	 * Draws small text
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 * @param alignment  -1==left, 0 ==centre, 1=right
	 */
	public static  void drawSmallString(SpriteBatch g,String text,Color color, float x, float y,int alignment) {
	
		if(alignment==-1){
			alignment=Align.left;
		}else if(alignment==0){
			alignment=Align.center;
		}else{
			alignment=Align.right;
		}
		MarioResourceManager.defaultSmall.setColor(color);
		MarioResourceManager.defaultSmall.draw(g, text, x, y, 0, text.length(),0, alignment, false);
	
	}
	/**
	 * Drawns normal text
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 * @param alignment  -1==left, 0 ==centre, 1=right
	 */
	public static void drawNormalString(SpriteBatch g,String text,Color color, int x, int y,int alignment) {
	
		if(alignment==-1){
			alignment=Align.left;
		}else if(alignment==0){
			alignment=Align.center;
		}else{
			alignment=Align.right;
		}
		MarioResourceManager.defaultNormal.setColor(color);
		MarioResourceManager.defaultNormal.draw(g, text, x, y, 0, text.length(),0, alignment, false);
	
	}
	
	public static  void drawStringDropShadow(SpriteBatch g, String text, int x,
			int y, int alignment) {
		drawSmallString(g, text,Color.GRAY, x+0.5f, y+0.5f,alignment);
		drawSmallString(g, text,Color.WHITE, x, y,alignment);
		
	}
	
	public static void drawStringDropShadowAsEntity(SpriteBatch g, String text, int x,
			int y, int alignment) {
		x+=xOffset;
		drawSmallString(g, text,Color.GRAY, x+0.5f, y+0.3f,alignment);
		drawSmallString(g, text,Color.WHITE, x, y,alignment);
	}
	/**
	 * Drawns large text
	 * @param g
	 * @param text
	 * @param x
	 * @param y
	 * @param alignment  -1==left, 0 ==centre, 1=right
	 */
	public  static void drawBigString(SpriteBatch g,String text,Color color ,int x, int y,int alignment) {
	
		if(alignment==-1){
			alignment=Align.left;
		}else if(alignment==0){
			alignment=Align.center;
		}else{
			alignment=Align.right;
		}
		
		MarioResourceManager.defaultBig.setColor(color);
		MarioResourceManager.defaultBig.draw(g, text, x, y, 0, text.length(), 0, alignment, false);
		
	}

	public boolean isDrawHudEnabled() {
		return drawHudEnabled;
	}

	public void setDrawHudEnabled(boolean drawHudEnabled) {
		this.drawHudEnabled = drawHudEnabled;
	}

}
