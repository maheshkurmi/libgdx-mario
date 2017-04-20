
package com.shikhar.mario.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;




/**
 * Class storing all the icons used by the application.
 * @author Mahesh Kurmi
 * @version 1.0.2
 * @since 1.0.0
 */
public class Icons {
	/** Reads in a BufferedImage using the standard ImageIO.read() */
	public static BufferedImage loadImage(String filename) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(filename));
		} catch (IOException e) { 
			System.out.println(e.getMessage());
		}
		return img;
	} 
	
	/** New icon */
	public static final ImageIcon NEW = new ImageIcon(loadImage("java-mario-src/icons/new-icon.png"));
	
	/** Open icon */
	public static final ImageIcon OPEN = new ImageIcon("java-mario-src/icons/open-icon.png");
	
	/** Save icon */
	public static final ImageIcon SAVE = new ImageIcon("java-mario-src/icons/save-icon.png");

	/** Save As.. icon */
	public static final ImageIcon SAVEAS = new ImageIcon("java-mario-src/icons/saveas-icon.png");
	
	/** Export as Image icon */
	public static final ImageIcon EXPORT = new ImageIcon("java-mario-src/icons/export-icon.png");
	
	/** Print icon */
	public static final ImageIcon PRINT = new ImageIcon("java-mario-src/icons/print-icon.png");
	
	/** undo icon */
	public static final ImageIcon UNDO = new ImageIcon("java-mario-src/icons/undo-icon.png");
	
	/** redo icon */
	public static final ImageIcon REDO =  new ImageIcon("java-mario-src/icons/redo-icon.png");

	/** Check icon */
    public static final ImageIcon CHECK =  new ImageIcon("java-mario-src/icons/check-icon.png");

	/** Look and feel icon */
	public static final ImageIcon LOOKANDFEEL = new ImageIcon("java-mario-src/icons/lookandfeel-icon.png");
	
	/** Prefereneces icon */
	public static final ImageIcon PREFERENCES = new ImageIcon("java-mario-src/icons/preferences-icon.png");
	
	/**app icon */
	public static final ImageIcon ICON_G = new ImageIcon("java-mario-src/icons/G.png");
	public static final ImageIcon ICON_K = new ImageIcon("java-mario-src/icons/K_l.png");
	public static final ImageIcon ICON_L = new ImageIcon("java-mario-src/icons/K_r.png");
	public static final ImageIcon ICON_H = new ImageIcon("java-mario-src/icons/H_l.png");
	public static final ImageIcon ICON_J = new ImageIcon("java-mario-src/icons/J.png");      //red shell
	public static final ImageIcon ICON_B_L = new ImageIcon("java-mario-src/icons/B_l.png");
	public static final ImageIcon ICON_B_R = new ImageIcon("java-mario-src/icons/B_r.png");      //red shell
	public static final ImageIcon ICON_F_R = new ImageIcon("java-mario-src/icons/F_l.png");      //red shell
	public static final ImageIcon ICON_F_L = new ImageIcon("java-mario-src/icons/F_r.png");      //red shell
	public static final ImageIcon ICON_G_L = new ImageIcon("java-mario-src/icons/Fly_Goomba.png");      //red shell

	public static final ImageIcon ICON_I = new ImageIcon("java-mario-src/icons/firedisc_I.png");
	public static final ImageIcon ICON_S = new ImageIcon("java-mario-src/icons/S.png");      //red shell
	
	public static final ImageIcon ICON_Level = new ImageIcon("java-mario-src/icons/levelIcon.png");
	public static final ImageIcon ICON_P= new ImageIcon("java-mario-src/icons/brett_0.png");
	public static final ImageIcon ICON_Px= new ImageIcon("java-mario-src/icons/brett_x.png");
	public static final ImageIcon ICON_Py= new ImageIcon("java-mario-src/icons/brett_y.png");
	public static final ImageIcon ICON_BookMark= new ImageIcon("java-mario-src/icons/BookMark.png");


	public static final ImageIcon ICON_B = new ImageIcon("java-mario-src/icons/brick.png");
	public static final ImageIcon ICON_C = new ImageIcon("java-mario-src/icons/C.png");
	public static final ImageIcon ICON_Q = new ImageIcon("java-mario-src/icons/Q_S.png");  //Q Questiion bloack with coin
	public static final ImageIcon ICON_W = new ImageIcon("java-mario-src/icons/Q_L.png");  //W Questiion bloack with life
	//public static final ImageIcon ICON_P = new ImageIcon("java-mario-src/icons/P.png");
	public static final ImageIcon ICON_T = new ImageIcon("java-mario-src/icons/tree.png");  //tree
	public static final ImageIcon ICON_R = new ImageIcon("java-mario-src/icons/R_B.png");
	public static final ImageIcon ICON_V = new ImageIcon("java-mario-src/icons/V_T.png");
	
	
	public static final ImageIcon ICON_2 = new ImageIcon("java-mario-src/icons/2.png");  //
	public static final ImageIcon ICON_3 = new ImageIcon("java-mario-src/icons/3.png");  // green floor middle
	public static final ImageIcon ICON_g = new ImageIcon("java-mario-src/icons/left_g.png");  // 
	public static final ImageIcon ICON_4 = new ImageIcon("java-mario-src/icons/4.png");  // 
	public static final ImageIcon ICON_h = new ImageIcon("java-mario-src/icons/right_h.png");  // 
	
	public static final ImageIcon ICON_5 = new ImageIcon("java-mario-src/icons/5.png");  // grass floor left edge 
	public static final ImageIcon ICON_6 = new ImageIcon("java-mario-src/icons/6.png");  //grass floor right edge

	public static final ImageIcon ICON_7 = new ImageIcon("java-mario-src/icons/7.png");  //slope grass centre
	public static final ImageIcon ICON_8 = new ImageIcon("java-mario-src/icons/8.png");  //slope grass edge
	public static final ImageIcon ICON_9= new ImageIcon("java-mario-src/icons/9.png");   //slope
		
	public static final ImageIcon ICON_i = new ImageIcon("java-mario-src/icons/left_i.png");  //slope grass centre
	public static final ImageIcon ICON_j = new ImageIcon("java-mario-src/icons/mid_j.png");  //slope grass edge
	public static final ImageIcon ICON_k= new ImageIcon("java-mario-src/icons/right_k.png");   //slope

	
	public static final ImageIcon ICON_n = new ImageIcon("java-mario-src/icons/n.png"); //roundtree left
	public static final ImageIcon ICON_m= new ImageIcon("java-mario-src/icons/m.png");  //roundtree right
	
	public static final ImageIcon ICON_a = new ImageIcon("java-mario-src/icons/a.png"); //roundtree left
	public static final ImageIcon ICON_b= new ImageIcon("java-mario-src/icons/b.png");  //roundtree right
	public static final ImageIcon ICON_q = new ImageIcon("java-mario-src/icons/q.png"); //rock left
	public static final ImageIcon ICON_r = new ImageIcon("java-mario-src/icons/r.png"); //rock right
	public static final ImageIcon ICON_z = new ImageIcon("java-mario-src/icons/z.png"); //stem ttree
	public static final ImageIcon ICON_t = new ImageIcon("java-mario-src/icons/t.png"); //pipe top left
	public static final ImageIcon ICON_u = new ImageIcon("java-mario-src/icons/u.png");
	public static final ImageIcon ICON_v = new ImageIcon("java-mario-src/icons/v.png");
	public static final ImageIcon ICON_w = new ImageIcon("java-mario-src/icons/w.png");
	public static final ImageIcon ICON_x = new ImageIcon("java-mario-src/icons/x.png");
	public static final ImageIcon ICON_y = new ImageIcon("java-mario-src/icons/y.png");  // pipe base right
	public static final ImageIcon ICON_bulb = new ImageIcon("java-mario-src/icons/bulb.png");
	public static final ImageIcon ICON_bulbbase = new ImageIcon("java-mario-src/icons/bulbbase.png");  
	public static final ImageIcon ICON_info = new ImageIcon("java-mario-src/icons/info.png");  // Inf panel
	public static final ImageIcon ICON_spring= new ImageIcon("java-mario-src/icons/Spring.png");  // 
	public static final ImageIcon ICON_bluerock = new ImageIcon("java-mario-src/icons/blueRock.png");  
	public static final ImageIcon ICON_yellowrock= new ImageIcon("java-mario-src/icons/yellowRock.png");  //
	public static final ImageIcon ICON_grayrock= new ImageIcon("java-mario-src/icons/grayRock.png");  //
	
	public static final ImageIcon ICON_sandTop= new ImageIcon("java-mario-src/icons/sandM.png");  // 
	public static final ImageIcon ICON_sand = new ImageIcon("java-mario-src/icons/sand.png");  
	public static final ImageIcon ICON_waterTile = new ImageIcon("java-mario-src/icons/water_tile.png");  
	public static final ImageIcon ICON_waterPlant = new ImageIcon("java-mario-src/icons/water_plant.png");  
	public static final ImageIcon ICON_bush = new ImageIcon("java-mario-src/icons/bush.png");  

	public static final ImageIcon ICON_virus= new ImageIcon("java-mario-src/icons/virus.png");  // 
	public static final ImageIcon ICON_blueFish = new ImageIcon("java-mario-src/icons/blueFish.png");  
	public static final ImageIcon ICON_redFish = new ImageIcon("java-mario-src/icons/redFish.png");  
	public static final ImageIcon ICON_jumpingFish = new ImageIcon("java-mario-src/icons/jumpingFish.png");  
	public static final ImageIcon ICON_latiku = new ImageIcon("java-mario-src/icons/latiku.png");  

	public static final ImageIcon ICON_fireTop = new ImageIcon("java-mario-src/icons/FireTop.png");  
	public static final ImageIcon ICON_fireBase = new ImageIcon("java-mario-src/icons/FireBase.png");  
	public static final ImageIcon ICON_fireBrickLeft = new ImageIcon("java-mario-src/icons/FireBrickLeft.png");  
	public static final ImageIcon ICON_fireBrickRight = new ImageIcon("java-mario-src/icons/FireBrickRight.png");  
	public static final ImageIcon ICON_Girder = new ImageIcon("java-mario-src/icons/girder.png");  
	public static final ImageIcon ADD_WATER=  new ImageIcon("java-mario-src/icons/water.png");
	public static final ImageIcon REMOVE_WATER =  new ImageIcon("java-mario-src/icons/delete_water.png");

}
