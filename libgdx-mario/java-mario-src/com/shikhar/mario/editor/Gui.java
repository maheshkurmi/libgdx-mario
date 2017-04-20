package com.shikhar.mario.editor;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.shikhar.mario.core.GameFrame;
import com.shikhar.mario.core.GameLoader;
import com.shikhar.mario.core.GameRenderer;
import com.shikhar.mario.core.sound.specific.MarioSoundManager22050Hz;
import com.shikhar.mario.core.tile.GameTile;
import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;
import com.shikhar.mario.objects.creatures.BlueFish;
import com.shikhar.mario.objects.creatures.Bowser;
import com.shikhar.mario.objects.creatures.Coin;
import com.shikhar.mario.objects.creatures.FireDisc;
import com.shikhar.mario.objects.creatures.FlyGoomba;
import com.shikhar.mario.objects.creatures.FlyRedKoopa;
import com.shikhar.mario.objects.creatures.Goomba;
import com.shikhar.mario.objects.creatures.JumpingFish;
import com.shikhar.mario.objects.creatures.Latiku;
import com.shikhar.mario.objects.creatures.LevelComplete;
import com.shikhar.mario.objects.creatures.Piranha;
import com.shikhar.mario.objects.creatures.Platform;
import com.shikhar.mario.objects.creatures.RedFish;
import com.shikhar.mario.objects.creatures.RedKoopa;
import com.shikhar.mario.objects.creatures.RedShell;
import com.shikhar.mario.objects.creatures.Spring;
import com.shikhar.mario.objects.creatures.Thorny;
import com.shikhar.mario.objects.creatures.Virus;
import com.shikhar.mario.objects.mario.Mario;
import com.shikhar.mario.objects.tiles.Brick;
import com.shikhar.mario.objects.tiles.Bush;
import com.shikhar.mario.objects.tiles.FireTile;
import com.shikhar.mario.objects.tiles.InfoPanel;
import com.shikhar.mario.objects.tiles.QuestionBlock;
import com.shikhar.mario.objects.tiles.RotatingBlock;
import com.shikhar.mario.objects.tiles.Tree;
import com.shikhar.mario.util.ImageUtilities;
import com.shikhar.mario.util.SpriteMap;




public class Gui extends JFrame implements ActionListener,  MouseListener, MouseMotionListener{
	/** The app version */
	public static final String VERSION = "1.0.0";
	/** The main menu bar */
	private JMenuBar barMenu;
	
	/** The file menu */
	private JMenu mnuFile;
	
	/** The File_New menu option */
	private JMenuItem mnuNew;
	
	/** The File_Open menu option */
	private JMenuItem mnuOpen;
	
	/** The File_Save As... menu option */
	private JMenuItem mnuSave;
	
	/** The File_Save As... menu option */
	private JMenuItem mnuSaveAs;
	
	/** The File_Export to Image.. menu option */
	private JMenuItem mnuExport;
	
	/** The File_Print menu option */
	private JMenuItem mnuPrint;
	
	/** The File_Exit menu option */
	private JMenuItem mnuExit;
	
	/** The window menu */
	private JMenu mnuWindow;
	
	/** The window_Preferneces menu */
	private JMenuItem mnuPreferences;
	
	/** The Window_look and feel menu */
	private JMenu mnuLookAndFeel;
	
	/** The help menu */
	private JMenuItem mnuRun;

	/** The File_New toolbar button  */
	private JButton btnNew;
	
	/** The File_Open toolbar button */
	private JButton btnOpen;
	
	/** The File_Save toolbar button */
	private JButton btnSave;
	
	/** The File_Print toolbar button */
	private JButton btnPrint;
	
	/** The File_Export toolbar button */
	private JButton btnExport;
	
	/** The File_Undo toolbar button */
	private JButton btnUndo;
	
	/** The File_Redo toolbar button */
	private JButton btnRedo;
	
	/** The Light Enable/disable toggle button */
	private JToggleButton tglG;
	private JToggleButton tglK;
	private JToggleButton tglL;
	private JToggleButton tglH;
	private JToggleButton tglI;
	private JToggleButton tglJ;
	private JToggleButton tglS;
	private JToggleButton tglC;
	private JToggleButton tglQ;
	private JToggleButton tglW;
	private JToggleButton tglR;
	private JToggleButton tglB;
	private JToggleButton tglT;
	private JToggleButton tglV;
	private JToggleButton tglLevel;
	private JToggleButton tglF_G;
	private JToggleButton tglF_l;
	private JToggleButton tglF_r;
	private JToggleButton tglN_l;
	private JToggleButton tglN_r;
	private JToggleButton tglVirus;
	private JToggleButton tglRedFish;
	private JToggleButton tglBlueFish;
	private JToggleButton tglJumpingFish;
	private JToggleButton tglLatiku;

	private JToggleButton tgl2;
	private JToggleButton tgl3;
	private JToggleButton tglg;
	private JToggleButton tgl4;
	private JToggleButton tglh;
	private JToggleButton tgl5;
	private JToggleButton tgl6;
	private JToggleButton tgl7;
	private JToggleButton tgl8;
	private JToggleButton tgl9;
	private JToggleButton tglBlueRock;
	private JToggleButton tglYellowRock;
	private JToggleButton tglGrayRock;
	private JToggleButton tglWaterTile;
	private JToggleButton tglWaterPlant;
	private JToggleButton tglFireTileTop;
	private JToggleButton tglFireTileBase;
	private JToggleButton tglFireBrickLeft;
	private JToggleButton tglFireBrickRight;
	private JToggleButton tglGirder;

	private JToggleButton tglSandTop;
	private JToggleButton tglSandNormal;

	private JToggleButton tgli;
	private JToggleButton tglj;
	private JToggleButton tglk;

	private JToggleButton tgln;
	private JToggleButton tglm;
	private JToggleButton tgla;
	private JToggleButton tglb;
	private JToggleButton tglq;
	private JToggleButton tglr;
	private JToggleButton tglt;
	private JToggleButton tglu;
	private JToggleButton tglv;
	private JToggleButton tglw;
	private JToggleButton tglx;
	private JToggleButton tgly;
	private JToggleButton tglz;
	private JToggleButton tglbulb;
	private JToggleButton tglbulbBase;
	private JToggleButton tglInfo;
	private JToggleButton tglBush;

	private JToggleButton tglBackMap;
	private JToggleButton tglMainMap;
	private JToggleButton tglForeMap;
	private JToggleButton tglBookMark;
	private JToggleButton tglP;
	private JToggleButton tglPx;
	private JToggleButton tglPy;
	private JToggleButton tglSpring;
	private JToggleButton tglWaterZone;
	private JToggleButton tglRemoveWaterZone;

	
	/** editMap=0 backMap, =1 MainMap, 2=ForeMap*/
	private int editMap=1; 
	private boolean dirty;
	/** Upper toolbar*/
	JToolBar fileToolbar ;
	
	/** Lower toolbar*/
	JToolBar editToolbar ;
	
	/** Lower toolbar*/
	JToolBar backGroundToolbar ;
	
	
	/** toolbars containers*/
	JPanel pnlToolBar;
	/** The Edit_addPoint toolbar button */
	
	/**Drawing Canvas*/
	private BufferedPanel canvas;
	private Mario mario;
	private TileMap map;
	private TileMap backgroundMap;
	private TileMap foregroundMap;
	private GameRenderer renderer;
	private GameLoader loader;
	private Graphics dbg;
	private Image dbImage = null;
	private int panelWidth=600;
	private int panelHeight=300;
	private int mapWidth,mapHeight;

	/**Status bar*/
	private StatusBarPanel statusBar = new StatusBarPanel();

	private String toolID="";
	private ArrayList<BufferedImage> plain;
	private BufferedImage[] plainTiles;
	
	private BufferedImage sloped_image;
	private BufferedImage grass_edge;
	private BufferedImage grass_center;
	private BufferedImage bush;
	private BufferedImage mushroomTree;
	private BufferedImage woodenBridge;
	private BufferedImage waterRock;
	

	private char[][] mapMainData;
	private char[][] mapBackData;
	private char[][] mapForeData;
	
    /**stacks for redo undo actions**/
    Vector<String> undoStack, redoStack;
    final static  int MAX_STACK_SIZE=10;
    
    //coordinates to store initial drag value
	private int initMouseX,initMouseY;
	
	private boolean isMapChanged=false;
	
	public Gui(){
	    super();
		this.setTitle("Mario-Editor");	
		// make sure tooltips and menus show up on top of the heavy weight canvas
		ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		plain = new ArrayList<BufferedImage>();
		plainTiles = (new SpriteMap("tiles/Plain_Tiles.png", 6, 17)).getSprites();
        bush=ImageUtilities.loadImage("items/water_plant.png");
    	mushroomTree=ImageUtilities.loadImage("items/mushRoom_tree.png");
		woodenBridge=ImageUtilities.loadImage("items/wooden_bridge.png");
		waterRock=ImageUtilities.loadImage("items/water_rock.png");
	
		for (BufferedImage bImage : plainTiles) {
				plain.add(bImage);
		}
			
		sloped_image = loadImage("items/Sloped_Tile.png");
		grass_edge = loadImage("items/Grass_Edge.png");
		grass_center = loadImage("items/Grass_Center.png");
		this.barMenu = new JMenuBar();
		
		// file menu
		this.mnuFile = new JMenu("File");
		
		this.mnuNew = new JMenuItem("New");
		this.mnuNew.setIcon(Icons.NEW);
		this.mnuNew.setActionCommand("new");
		this.mnuNew.addActionListener(this);
		
		this.mnuSave = new JMenuItem("Save..");
		this.mnuSave.setIcon(Icons.SAVE);
		this.mnuSave.setActionCommand("save");
		this.mnuSave.addActionListener(this);
		
		this.mnuSaveAs = new JMenuItem("Save As..");
		this.mnuSaveAs.setIcon(Icons.SAVEAS);
		this.mnuSaveAs.setActionCommand("saveas");
		this.mnuSaveAs.addActionListener(this);
		
		this.mnuOpen = new JMenuItem("Open..");
		this.mnuOpen.setIcon(Icons.OPEN);
		this.mnuOpen.setActionCommand("open");
		this.mnuOpen.addActionListener(this);
		
		this.mnuExport = new JMenuItem("Take ScreenShot..");
		this.mnuExport.setIcon(Icons.EXPORT);
		this.mnuExport.setActionCommand("export");
		this.mnuExport.addActionListener(this);

		this.mnuPrint = new JMenuItem("Print");
		this.mnuPrint.setIcon(Icons.PRINT);
		this.mnuPrint.setActionCommand("print");
		this.mnuPrint.addActionListener(this);
		
		this.mnuExit = new JMenuItem("Exit");
		this.mnuExit.setActionCommand("exit");
		this.mnuExit.addActionListener(this);
		
		this.mnuFile.add(this.mnuNew);
		this.mnuFile.add(this.mnuOpen);
		this.mnuFile.addSeparator();
		this.mnuFile.add(this.mnuSave);
		this.mnuFile.add(this.mnuSaveAs);
		this.mnuFile.addSeparator();
		//this.mnuFile.add(this.mnuPrint);
		this.mnuFile.add(this.mnuExport);
		
		this.mnuFile.addSeparator();
		this.mnuFile.add(mnuExit);
		
		// window menu
		this.mnuWindow = new JMenu("Window");

		this.mnuPreferences = new JMenuItem("Preferences");
		this.mnuPreferences.setIcon(Icons.PREFERENCES);
		this.mnuPreferences.setActionCommand("preferences");
		this.mnuPreferences.addActionListener(this);

		this.mnuLookAndFeel = new JMenu("Theme");
		this.mnuLookAndFeel.setIcon(Icons.LOOKANDFEEL);
		this.createLookAndFeelMenuItems(this.mnuLookAndFeel);
			
		this.mnuRun = new JMenuItem("run");
		this.mnuRun.setActionCommand("run");
		this.mnuRun.addActionListener(this);
		
		this.mnuWindow.add(mnuPreferences);
		this.mnuWindow.addSeparator();
		this.mnuWindow.add(this.mnuLookAndFeel);
		//this.mnuWindow.addSeparator();
		//this.mnuWindow.add(this.mnuRun);
		
		//Add main menu to menu bar
		this.barMenu.add(this.mnuFile);
		this.barMenu.add(this.mnuWindow);	
		this.barMenu.add(this.mnuRun);	
		
		this.setJMenuBar(this.barMenu);
				
		this.fileToolbar = new JToolBar("File Toolbar", JToolBar.HORIZONTAL);
		this.fileToolbar.setFloatable(false);
		
		this.btnNew = new JButton(Icons.NEW);
		this.btnNew.addActionListener(this);
		this.btnNew.setActionCommand("new");
		this.btnNew.setToolTipText("New Map");
		
		this.btnOpen = new JButton(Icons.OPEN);
		this.btnOpen.addActionListener(this);
		this.btnOpen.setActionCommand("open");
		this.btnOpen.setToolTipText("Open Map");
		
		this.btnSave = new JButton(Icons.SAVE);
		this.btnSave.addActionListener(this);
		this.btnSave.setActionCommand("save");
		this.btnSave.setToolTipText("Save Map");
		
		this.btnPrint = new JButton(Icons.PRINT);
		this.btnPrint.addActionListener(this);
		this.btnPrint.setActionCommand("print");
		//this.btnPrint.setToolTipText(Messages.getString("toolbar.file.print"));
		
		this.btnExport = new JButton(Icons.EXPORT);
		this.btnExport.addActionListener(this);
		this.btnExport.setActionCommand("export");
		this.btnExport.setToolTipText("Take Screen-Shot");				
		
		this.btnUndo = new JButton(Icons.UNDO);
		this.btnUndo.addActionListener(this);
		this.btnUndo.setActionCommand("undo");
		this.btnUndo.setToolTipText("Undo");				
		
		this.btnRedo = new JButton(Icons.REDO);
		this.btnRedo.addActionListener(this);
		this.btnRedo.setActionCommand("redo");
		this.btnRedo.setToolTipText("Redo");				
		
		this.editToolbar = new JToolBar("toolbar.edit", JToolBar.HORIZONTAL);
		this.editToolbar.setFloatable(false);
		this.tglG = new JToggleButton(Icons.ICON_G);
		this.tglG.setActionCommand("G");
		this.tglG.addActionListener(this);
		this.tglG.setSelected(false);
		
		this.tglK = new JToggleButton(Icons.ICON_K);
		this.tglK.setToolTipText("K");
		this.tglK.setActionCommand("K");
		this.tglK.addActionListener(this);
		this.tglK.setSelected(false);
		
		this.tglL = new JToggleButton(Icons.ICON_L);
		this.tglL.setToolTipText("L");
		this.tglL.setActionCommand("L");
		this.tglL.addActionListener(this);
		this.tglL.setSelected(false);
				
		this.tglH = new JToggleButton(Icons.ICON_H);
		this.tglH.setToolTipText("H");
		this.tglH.setActionCommand("H");
		this.tglH.addActionListener(this);
		this.tglH.setSelected(false);
			
		this.tglI = new JToggleButton(Icons.ICON_I);
		this.tglI.setToolTipText("I");
		this.tglI.setActionCommand("I");
		this.tglI.addActionListener(this);
		this.tglI.setSelected(false);
	
		this.tglJ = new JToggleButton(Icons.ICON_J);
		this.tglJ.setToolTipText("J");
		this.tglJ.setActionCommand("J");
		this.tglJ.addActionListener(this);
		this.tglJ.setSelected(false);
		
		this.tglF_G = new JToggleButton(Icons.ICON_G_L);
		this.tglF_G.setToolTipText("O");
		this.tglF_G.setActionCommand("O");
		this.tglF_G.addActionListener(this);
		this.tglF_G.setSelected(false);
	
		this.tglF_l = new JToggleButton(Icons.ICON_F_L);
		this.tglF_l.setToolTipText("F");
		this.tglF_l.setActionCommand("F");
		this.tglF_l.addActionListener(this);
		this.tglF_l.setSelected(false);
	
		this.tglF_r = new JToggleButton(Icons.ICON_F_R);
		this.tglF_r.setToolTipText("F");
		this.tglF_r.setActionCommand("F");
		this.tglF_r.addActionListener(this);
		this.tglF_r.setSelected(false);
	
		this.tglN_l = new JToggleButton(Icons.ICON_B_L);
		this.tglN_l.setToolTipText("N");
		this.tglN_l.setActionCommand("N");
		this.tglN_l.addActionListener(this);
		this.tglN_l.setSelected(false);
	
		this.tglN_r = new JToggleButton(Icons.ICON_B_R);
		this.tglN_r.setToolTipText("N");
		this.tglN_r.setActionCommand("N");
		this.tglN_r.addActionListener(this);
		this.tglN_r.setSelected(false);
	
		this.tglVirus = new JToggleButton(Icons.ICON_virus);
		this.tglVirus.setToolTipText("?");
		this.tglVirus.setActionCommand("?");
		this.tglVirus.addActionListener(this);
		this.tglVirus.setSelected(false);
	
		this.tglBlueFish= new JToggleButton(Icons.ICON_blueFish);
		this.tglBlueFish.setToolTipText(")");
		this.tglBlueFish.setActionCommand(")");
		this.tglBlueFish.addActionListener(this);
		this.tglBlueFish.setSelected(false);
	
		this.tglRedFish = new JToggleButton(Icons.ICON_redFish);
		this.tglRedFish.setToolTipText("(");
		this.tglRedFish.setActionCommand("(");
		this.tglRedFish.addActionListener(this);
		this.tglRedFish.setSelected(false);
	
		this.tglJumpingFish = new JToggleButton(Icons.ICON_jumpingFish);
		this.tglJumpingFish.setToolTipText(",");
		this.tglJumpingFish.setActionCommand(",");
		this.tglJumpingFish.addActionListener(this);
		this.tglJumpingFish.setSelected(false);
	
		this.tglLatiku = new JToggleButton(Icons.ICON_latiku);
		this.tglLatiku.setToolTipText("&");
		this.tglLatiku.setActionCommand("&");
		this.tglLatiku.addActionListener(this);
		this.tglLatiku.setSelected(false);
	
		this.tglJ = new JToggleButton(Icons.ICON_J);
		this.tglJ.setToolTipText("J");
		this.tglJ.setActionCommand("J");
		this.tglJ.addActionListener(this);
		this.tglJ.setSelected(false);
	
		this.tglS = new JToggleButton(Icons.ICON_S);
		this.tglS.setToolTipText("S");
		this.tglS.setActionCommand("S");
		this.tglS.addActionListener(this);
		this.tglS.setSelected(false);
		
		this.tglC = new JToggleButton(Icons.ICON_C);
		this.tglC.setToolTipText("C");
		this.tglC.setActionCommand("C");
		this.tglC.addActionListener(this);
		this.tglC.setSelected(false);
		
		this.tglB = new JToggleButton(Icons.ICON_B);
		this.tglB.setToolTipText("B");
		this.tglB.setActionCommand("B");
		this.tglB.addActionListener(this);
		this.tglB.setSelected(false);
		
		this.tglQ = new JToggleButton(Icons.ICON_Q);
		this.tglQ.setToolTipText("Q");
		this.tglQ.setActionCommand("Q");
		this.tglQ.addActionListener(this);
		this.tglQ.setSelected(false);
		
		this.tglFireTileTop = new JToggleButton(Icons.ICON_fireTop);
		this.tglFireTileTop.setToolTipText("[");
		this.tglFireTileTop.setActionCommand("[");
		this.tglFireTileTop.addActionListener(this);
		this.tglFireTileTop.setSelected(false);
		
		this.tglFireTileBase = new JToggleButton(Icons.ICON_fireBase);
		this.tglFireTileBase.setToolTipText("]");
		this.tglFireTileBase.setActionCommand("]");
		this.tglFireTileBase.addActionListener(this);
		this.tglFireTileBase.setSelected(false);
		
		this.tglFireBrickLeft = new JToggleButton(Icons.ICON_fireBrickLeft);
		this.tglFireBrickLeft.setToolTipText(";");
		this.tglFireBrickLeft.setActionCommand(";");
		this.tglFireBrickLeft.addActionListener(this);
		this.tglFireBrickLeft.setSelected(false);
		
		this.tglFireBrickRight = new JToggleButton(Icons.ICON_fireBrickRight);
		this.tglFireBrickRight.setToolTipText(":");
		this.tglFireBrickRight.setActionCommand(":");
		this.tglFireBrickRight.addActionListener(this);
		this.tglFireBrickRight.setSelected(false);
	
		this.tglW = new JToggleButton(Icons.ICON_W);
		this.tglW.setToolTipText("W");
		this.tglW.setActionCommand("W");
		this.tglW.addActionListener(this);
		this.tglW.setSelected(false);
		
		this.tglR = new JToggleButton(Icons.ICON_R);
		this.tglR.setToolTipText("R");
		this.tglR.setActionCommand("R");
		this.tglR.addActionListener(this);
		this.tglR.setSelected(false);
	
		this.tglT = new JToggleButton(Icons.ICON_T);
		this.tglT.setToolTipText("T");
		this.tglT.setActionCommand("T");
		this.tglT.addActionListener(this);
		this.tglT.setSelected(false);
		
		this.tglV = new JToggleButton(Icons.ICON_V);
		this.tglV.setToolTipText("V");
		this.tglV.setActionCommand("V");
		this.tglV.addActionListener(this);
		this.tglV.setSelected(false);
		

		this.tglz = new JToggleButton(Icons.ICON_z);
		this.tglz.setToolTipText("z");
		this.tglz.setActionCommand("z");
		this.tglz.addActionListener(this);
		this.tglz.setSelected(false);
	
		this.tgl2 = new JToggleButton(Icons.ICON_2);
		this.tgl2.setToolTipText("2");
		this.tgl2.setActionCommand("2");
		this.tgl2.addActionListener(this);
		this.tgl2.setSelected(false);
		
		this.tgl3 = new JToggleButton(Icons.ICON_3);
		this.tgl3.setToolTipText("3");
		this.tgl3.setActionCommand("3");
		this.tgl3.addActionListener(this);
		this.tgl3.setSelected(false);
		
		
		this.tglGirder = new JToggleButton(Icons.ICON_Girder);
		this.tglGirder.setToolTipText("_");
		this.tglGirder.setActionCommand("_");
		this.tglGirder.addActionListener(this);
		this.tglGirder.setSelected(false);
		
		this.tgl4 = new JToggleButton(Icons.ICON_4);
		this.tgl4.setToolTipText("4");
		this.tgl4.setActionCommand("4");
		this.tgl4.addActionListener(this);
		this.tgl4.setSelected(false);
		
		this.tglBlueRock = new JToggleButton(Icons.ICON_yellowrock);
		this.tglBlueRock.setToolTipText("%");
		this.tglBlueRock.setActionCommand("%");
		this.tglBlueRock.addActionListener(this);
		this.tglBlueRock.setSelected(false);
	
		this.tglYellowRock = new JToggleButton(Icons.ICON_bluerock);
		this.tglYellowRock.setToolTipText("\\");
		this.tglYellowRock.setActionCommand("\\");
		this.tglYellowRock.addActionListener(this);
		this.tglYellowRock.setSelected(false);
	
		this.tglGrayRock = new JToggleButton(Icons.ICON_grayrock);
		this.tglGrayRock.setToolTipText("$");
		this.tglGrayRock.setActionCommand("$");
		this.tglGrayRock.addActionListener(this);
		this.tglGrayRock.setSelected(false);
	
		this.tglSandTop = new JToggleButton(Icons.ICON_sandTop);
		this.tglSandTop.setToolTipText("-");
		this.tglSandTop.setActionCommand("-");
		this.tglSandTop.addActionListener(this);
		this.tglSandTop.setSelected(false);

		this.tglWaterTile = new JToggleButton(Icons.ICON_waterTile);
		this.tglWaterTile.setToolTipText("~");
		this.tglWaterTile.setActionCommand("~");
		this.tglWaterTile.addActionListener(this);
		this.tglWaterTile.setSelected(false);

		this.tglWaterPlant = new JToggleButton(Icons.ICON_waterPlant);
		this.tglWaterPlant.setToolTipText("{");
		this.tglWaterPlant.setActionCommand("{");
		this.tglWaterPlant.addActionListener(this);
		this.tglWaterPlant.setSelected(false);

		this.tglSandNormal = new JToggleButton(Icons.ICON_sand);
		this.tglSandNormal.setToolTipText("+");
		this.tglSandNormal.setActionCommand("+");
		this.tglSandNormal.addActionListener(this);
		this.tglSandNormal.setSelected(false);

		this.tglg = new JToggleButton(Icons.ICON_g);
		this.tglg.setToolTipText("g");
		this.tglg.setActionCommand("g");
		this.tglg.addActionListener(this);
		this.tglg.setSelected(false);
		
		this.tglh = new JToggleButton(Icons.ICON_h);
		this.tglh.setToolTipText("h");
		this.tglh.setActionCommand("h");
		this.tglh.addActionListener(this);
		this.tglh.setSelected(false);
		
		this.tgl5 = new JToggleButton(Icons.ICON_5);
		this.tgl5.setToolTipText("5");
		this.tgl5.setActionCommand("5");
		this.tgl5.addActionListener(this);
		this.tgl5.setSelected(false);
	
		this.tgl6 = new JToggleButton(Icons.ICON_6);
		this.tgl6.setToolTipText("6");
		this.tgl6.setActionCommand("6");
		this.tgl6.addActionListener(this);
		this.tgl6.setSelected(false);
		
		this.tgl7 = new JToggleButton(Icons.ICON_7);
		this.tgl7.setToolTipText("7");
		this.tgl7.setActionCommand("7");
		this.tgl7.addActionListener(this);
		this.tgl7.setSelected(false);
		
		this.tgl8 = new JToggleButton(Icons.ICON_8);
		this.tgl8.setToolTipText("8");
		this.tgl8.setActionCommand("8");
		this.tgl8.addActionListener(this);
		this.tgl8.setSelected(false);
		
		this.tgl9 = new JToggleButton(Icons.ICON_9);
		this.tgl9.setToolTipText("9");
		this.tgl9.setActionCommand("9");
		this.tgl9.addActionListener(this);
		this.tgl9.setSelected(false);

		this.tgli = new JToggleButton(Icons.ICON_i);
		this.tgli.setToolTipText("i");
		this.tgli.setActionCommand("i");
		this.tgli.addActionListener(this);
		this.tgli.setSelected(false);
		
		this.tglj = new JToggleButton(Icons.ICON_j);
		this.tglj.setToolTipText("j");
		this.tglj.setActionCommand("j");
		this.tglj.addActionListener(this);
		this.tglj.setSelected(false);
		
		this.tglk = new JToggleButton(Icons.ICON_k);
		this.tglk.setToolTipText("k");
		this.tglk.setActionCommand("k");
		this.tglk.addActionListener(this);
		this.tglk.setSelected(false);
		
		this.tgln = new JToggleButton(Icons.ICON_n);
		this.tgln.setToolTipText("n");
		this.tgln.setActionCommand("n");
		this.tgln.addActionListener(this);
		this.tgln.setSelected(false);
	
		this.tglm = new JToggleButton(Icons.ICON_m);
		this.tglm.setToolTipText("m");
		this.tglm.setActionCommand("m");
		this.tglm.addActionListener(this);
		this.tglm.setSelected(false);
	
		this.tgla = new JToggleButton(Icons.ICON_a);
		this.tgla.setToolTipText("a");
		this.tgla.setActionCommand("a");
		this.tgla.addActionListener(this);
		this.tgla.setSelected(false);
		
		this.tglb = new JToggleButton(Icons.ICON_b);
		this.tglb.setToolTipText("b");
		this.tglb.setActionCommand("b");
		this.tglb.addActionListener(this);
		this.tglb.setSelected(false);
		
		this.tglq = new JToggleButton(Icons.ICON_q);
		this.tglq.setToolTipText("q");
		this.tglq.setActionCommand("q");
		this.tglq.addActionListener(this);
		this.tglq.setSelected(false);
		
		this.tglr = new JToggleButton(Icons.ICON_r);
		this.tglr.setToolTipText("r");
		this.tglr.setActionCommand("r");
		this.tglr.addActionListener(this);
		this.tglr.setSelected(false);
		
		this.tglt = new JToggleButton(Icons.ICON_t);
		this.tglt.setToolTipText("t");
		this.tglt.setActionCommand("t");
		this.tglt.addActionListener(this);
		this.tglt.setSelected(false);
		
		this.tglu = new JToggleButton(Icons.ICON_u);
		this.tglu.setToolTipText("u");
		this.tglu.setActionCommand("u");
		this.tglu.addActionListener(this);
		this.tglu.setSelected(false);
		
		this.tglv = new JToggleButton(Icons.ICON_v);
		this.tglv.setToolTipText("v");
		this.tglv.setActionCommand("v");
		this.tglv.addActionListener(this);
		this.tglv.setSelected(false);
		
		this.tglw = new JToggleButton(Icons.ICON_w);
		this.tglw.setToolTipText("w");
		this.tglw.setActionCommand("w");
		this.tglw.addActionListener(this);
		this.tglw.setSelected(false);
		
		this.tglx = new JToggleButton(Icons.ICON_x);
		this.tglx.setToolTipText("x");
		this.tglx.setActionCommand("x");
		this.tglx.addActionListener(this);
		this.tglx.setSelected(false);
		
		this.tgly = new JToggleButton(Icons.ICON_y);
		this.tgly.setToolTipText("y");
		this.tgly.setActionCommand("y");
		this.tgly.addActionListener(this);
		this.tgly.setSelected(false);
		
		this.tglbulb = new JToggleButton(Icons.ICON_bulb);
		this.tglbulb.setToolTipText("*");
		this.tglbulb.setActionCommand("*");
		this.tglbulb.addActionListener(this);
		this.tglbulb.setSelected(false);
	
		this.tglbulbBase = new JToggleButton(Icons.ICON_bulbbase);
		this.tglbulbBase.setToolTipText("!");
		this.tglbulbBase.setActionCommand("!");
		this.tglbulbBase.addActionListener(this);
		this.tglbulbBase.setSelected(false);
	
		this.tglInfo = new JToggleButton(Icons.ICON_info);
		this.tglInfo.setToolTipText("@");
		this.tglInfo.setActionCommand("@");
		this.tglInfo.addActionListener(this);
		this.tglInfo.setSelected(false);
	
		this.tglBackMap = new JToggleButton("BM");
		this.tglBackMap.setToolTipText("Background map");
		this.tglBackMap.setActionCommand("backmap");
		this.tglBackMap.addActionListener(this);
		this.tglBackMap.setSelected(false);
		
		this.tglMainMap = new JToggleButton("MM");
		this.tglMainMap.setToolTipText("Main map");
		this.tglMainMap.setActionCommand("mainmap");
		this.tglMainMap.addActionListener(this);
		this.tglMainMap.setSelected(false);
		
		this.tglForeMap = new JToggleButton("FM");
		this.tglForeMap.setToolTipText("Forwground mae");
		this.tglForeMap.setActionCommand("foremap");
		this.tglForeMap.addActionListener(this);
		this.tglForeMap.setSelected(false);
		
		this.tglLevel = new JToggleButton(Icons.ICON_Level);
		this.tglLevel.setToolTipText("levelComplete");
		this.tglLevel.setActionCommand("l");
		this.tglLevel.addActionListener(this);
		this.tglLevel.setSelected(false);
		
		this.tglP = new JToggleButton(Icons.ICON_P);
		this.tglP.setToolTipText("P");
		this.tglP.setActionCommand("P");
		this.tglP.addActionListener(this);
		this.tglP.setSelected(false);

		this.tglPx = new JToggleButton(Icons.ICON_Px);
		this.tglPx.setToolTipText("Horizontal Platform (<)");
		this.tglPx.setActionCommand("<");
		this.tglPx.addActionListener(this);
		this.tglPx.setSelected(false);

		this.tglPy = new JToggleButton(Icons.ICON_Py);
		this.tglPy.setToolTipText("vertical Platform (>)");
		this.tglPy.setActionCommand(">");
		this.tglPy.addActionListener(this);
		this.tglPy.setSelected(false);
		
		this.tglSpring = new JToggleButton(Icons.ICON_spring);
		this.tglSpring.setToolTipText("^");
		this.tglSpring.setActionCommand("^");
		this.tglSpring.addActionListener(this);
		this.tglSpring.setSelected(false);

		this.tglBush = new JToggleButton(Icons.ICON_bush);
		this.tglBush.setToolTipText("}");
		this.tglBush.setActionCommand("}");
		this.tglBush.addActionListener(this);
		this.tglBush.setSelected(false);

		this.tglBookMark = new JToggleButton(Icons.ICON_BookMark);
		this.tglBookMark.setToolTipText("BookMark");
		this.tglBookMark.setActionCommand("M");
		this.tglBookMark.addActionListener(this);
		this.tglBookMark.setSelected(false);

		this.tglWaterZone = new JToggleButton(Icons.ADD_WATER);
		this.tglWaterZone.setToolTipText("Add WaterZone");
		this.tglWaterZone.setActionCommand("WaterZone");
		this.tglWaterZone.addActionListener(this);
		this.tglWaterZone.setSelected(false);

		this.tglRemoveWaterZone = new JToggleButton(Icons.REMOVE_WATER);
		this.tglRemoveWaterZone.setToolTipText("Remove WaterZone");
		this.tglRemoveWaterZone.setActionCommand("RemoveWaterZone");
		this.tglRemoveWaterZone.addActionListener(this);
		this.tglRemoveWaterZone.setSelected(false);

		this.backGroundToolbar = new JToolBar("BackGround Toolbar", JToolBar.HORIZONTAL);

		fileToolbar.add(this.btnNew);
		fileToolbar.add(this.btnOpen);
		fileToolbar.add(this.btnSave);
		fileToolbar.add(this.btnPrint);
		fileToolbar.add(this.btnExport);
		fileToolbar.addSeparator();
		fileToolbar.add(this.btnUndo);
		fileToolbar.add(this.btnRedo);
		fileToolbar.addSeparator();
		
		fileToolbar.add(this.tglBackMap);
		fileToolbar.add(this.tglMainMap);
		fileToolbar.add(this.tglForeMap);
		fileToolbar.addSeparator();
		fileToolbar.add(this.tglBookMark);
		fileToolbar.add(this.tglP);
		fileToolbar.add(this.tglPx);
		fileToolbar.add(this.tglPy);

		fileToolbar.addSeparator();
		fileToolbar.add(this.tglG);
		fileToolbar.add(this.tglK);
		fileToolbar.add(this.tglL);
		fileToolbar.add(this.tglH);
		fileToolbar.add(this.tglI);
		fileToolbar.add(this.tglS);
		fileToolbar.addSeparator();
		fileToolbar.add(this.tglF_G);
		fileToolbar.add(this.tglF_l);
		fileToolbar.add(this.tglN_l);
		fileToolbar.add(this.tglLatiku);
		fileToolbar.addSeparator();
		fileToolbar.add(this.tglVirus);
		fileToolbar.add(this.tglRedFish);
		fileToolbar.add(this.tglBlueFish);
		fileToolbar.add(this.tglJumpingFish);
		fileToolbar.addSeparator();
		
		fileToolbar.add(this.tglJ);
		fileToolbar.add(this.tglSpring);
		fileToolbar.add(this.tglLevel);
		fileToolbar.addSeparator();
		
		editToolbar.add(this.tglB);
		editToolbar.add(this.tglR);
		editToolbar.add(this.tglQ);
		editToolbar.add(this.tglW);
		editToolbar.add(this.tglC);
		
		editToolbar.addSeparator();
	
		editToolbar.add(this.tgl5);
		editToolbar.add(this.tgl3);
		editToolbar.add(this.tgl6);
		editToolbar.addSeparator();
		editToolbar.add(this.tglg);
		editToolbar.add(this.tgl4);
		editToolbar.add(this.tglh);
		editToolbar.addSeparator();
		editToolbar.add(this.tglWaterTile);
		editToolbar.add(this.tgl2);
		editToolbar.add(this.tglV);
		editToolbar.add(this.tglBlueRock);
		editToolbar.add(this.tglYellowRock);
		editToolbar.add(this.tglGrayRock);
		
		editToolbar.addSeparator();
		editToolbar.add(this.tglSandTop);
		editToolbar.add(this.tglSandNormal);
		editToolbar.add(this.tglWaterPlant);
		
		editToolbar.addSeparator();

		editToolbar.add(this.tgli);
		editToolbar.add(this.tglj);
		editToolbar.add(this.tglk);
		editToolbar.addSeparator();
		
		editToolbar.add(this.tglt);
		editToolbar.add(this.tglu);
		editToolbar.add(this.tglv);
		editToolbar.add(this.tglw);
		editToolbar.add(this.tglx);
		editToolbar.add(this.tgly);
		editToolbar.addSeparator();
		backGroundToolbar.add(this.tglBush);
		backGroundToolbar.add(this.tglT);
		backGroundToolbar.add(this.tglz);
		backGroundToolbar.addSeparator();
		
		backGroundToolbar.add(this.tgln);
		backGroundToolbar.add(this.tglm);
		backGroundToolbar.add(this.tgla);
		backGroundToolbar.add(this.tglb);
		backGroundToolbar.addSeparator();
		backGroundToolbar.add(this.tglq);
		backGroundToolbar.add(this.tglr);
		backGroundToolbar.add(this.tglbulb);
		backGroundToolbar.add(this.tglbulbBase);
		backGroundToolbar.addSeparator();
		backGroundToolbar.add(this.tgl7);
		backGroundToolbar.add(this.tgl8);
		backGroundToolbar.add(this.tgl9);
		backGroundToolbar.addSeparator();
		backGroundToolbar.add(this.tglInfo);
		backGroundToolbar.addSeparator();
		backGroundToolbar.add(this.tglFireTileTop);
		backGroundToolbar.add(this.tglFireTileBase);
		backGroundToolbar.add(this.tglFireBrickLeft);
		backGroundToolbar.add(this.tglFireBrickRight);
		backGroundToolbar.add(this.tglGirder);
		backGroundToolbar.add(this.tglWaterZone);
		backGroundToolbar.add(this.tglRemoveWaterZone);
		
		//Add toolbars
		pnlToolBar = new JPanel();
		pnlToolBar.setLayout(new GridLayout(3, 1));
		pnlToolBar.add(fileToolbar);
		pnlToolBar.add(editToolbar);
		pnlToolBar.add(backGroundToolbar);
		
		this.add(pnlToolBar,BorderLayout.NORTH);
		
		canvas=new BufferedPanel();
		canvas.setPreferredSize(new Dimension(panelWidth, panelHeight));
		canvas.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(canvas);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
	            public void windowClosing(WindowEvent e) {
	                close();
	            }
	    });
		
		pack();
	
		canvas.addComponentListener(new ComponentListener() 
		{  
		        public void componentResized(ComponentEvent evt) {
		            Component c = (Component)evt.getSource();
		            panelWidth=c.getWidth();
				    panelHeight=c.getHeight();
				    
		            int w=GameRenderer.pixelsToTiles(panelWidth);
		            int h=GameRenderer.pixelsToTiles(panelHeight);
				    Creature.WAKE_UP_VALUE_DOWN_RIGHT=w;
				    
				    canvas.hsbar.setMaximum(Math.max(0, mapWidth-w));
				    canvas.vsbar.setMaximum(Math.max(0, mapHeight-h));
				    canvas.vsbar.invalidate();
				    System.out.println(mapHeight+","+h);
				    dbImage=null;
				    gameRender();
				    canvas.repaint();
		        }

				@Override
				public void componentHidden(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void componentMoved(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void componentShown(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}
		});
		
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		
		//initialize stacks
		undoStack = new Vector<String>();
		redoStack = new Vector<String>();

		loadDefaultMap();
		
		//let's show begin
		this.setVisible(true);
	}

	
	/**
	 * Adds menu items to the given menu for each look and feel
	 * installed in the running vm.
	 * @param menu the menu to add the items to
	 */
	private void createLookAndFeelMenuItems(JMenu menu) {
		LookAndFeel current = UIManager.getLookAndFeel();
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			JMenuItem mnuLaF = new JMenuItem(info.getName());
			if (current.getClass().getName().equals(info.getClassName())) {
				mnuLaF.setIcon(Icons.CHECK);
			}
			mnuLaF.setActionCommand("laf+" + info.getClassName());
			mnuLaF.addActionListener(this);
			menu.add(mnuLaF);
		}
	}
	
	/*
	@Override
	public void paint(Graphics g){
		super.paint(g);
		paintScreen();
	}
    */
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command=arg0.getActionCommand();
		if (command.startsWith("laf+")) {
			// make sure they are sure
				// parse out the LAF class name
				String className = command.replace("laf+", "");
				try {
					// attempt to set the look and feel
					UIManager.setLookAndFeel(className);
					//Globalsettings.lookandFeel=UIManager.getLookAndFeel().getName();
					// get the current windows open by this application
					Window windows[] = Frame.getWindows();
					// update the ui
			        for(Window window : windows) {
			            SwingUtilities.updateComponentTreeUI(window);
			        }
			        // we need to pack since certain look and feels may have different component
			        // gaps which can cause stuff not to be shown
			        this.pack();
			        // find the item in the menu to set the current one
			        for (Component component : this.mnuLookAndFeel.getPopupMenu().getComponents()) {
			        	JMenuItem item = (JMenuItem)component;
			        	// set the newly selected LAF to have a checked icon
			        	// and the rest to have no icon
			        	if (item.getActionCommand().equalsIgnoreCase(command)) {
			        		item.setIcon(Icons.CHECK);
			        	} else {
			        		item.setIcon(null);
			        	}
			        }
				} catch (Exception e) {
					System.out.println(e);
				}
		}else if (command=="new"){	  
			newFile();
		}else if(command=="open"){
			loadFromFile();
		}else if(command=="run"){
			runGame();	
		}else if(command=="save"){
			loader.saveMaptoFile(mapDir+File.separatorChar+"map3.txt",mapMainData,mapHeight,mapWidth);
			loader.saveMaptoFile(mapDir+File.separatorChar+"back3.txt",mapBackData,mapHeight,mapWidth);
			loader.saveMaptoFile(mapDir+File.separatorChar+"fore3.txt",mapForeData,mapHeight,mapWidth);
		}else if(command=="saveas"){
	    	saveToFile(true);	
		}else if(command=="print"){
			printGraphics();	
		}else if (command=="exit"){
		    close();	
		}else if (command=="export"){
			  exportPNG();
		} else if ("undo".equals(command)) {
			doUndo();
		} else if ("redo".equals(command)) {
			doRedo();
		}else if (command=="backmap"){
			backgroundMap.setVisible(tglBackMap.isSelected());
			setEditMap();
		}else if (command =="mainmap"){
			map.setVisible(tglMainMap.isSelected());
			setEditMap();
		}else if (command=="foremap"){
			foregroundMap.setVisible(tglForeMap.isSelected());
			setEditMap();
		}else if(arg0.getSource() instanceof JToggleButton){
			toolID=command;
			System.out.println(toolID);
			deselectAll();
			((JToggleButton)arg0.getSource()).setSelected(true);
		}
	}
	
	private void setEditMap(){
		if (tglBackMap.isSelected()&& !tglMainMap.isSelected() && !tglForeMap.isSelected()){
			editMap=0;
			toggleCreatures(false);
		}else if(!tglBackMap.isSelected()&& !tglMainMap.isSelected() && tglForeMap.isSelected()){
			editMap=2;
			toggleCreatures(false);
		}else {
			editMap=1;
			toggleCreatures(true);
		}
		gameRender();
		canvas.repaint();
	}
	
	/**
	 * Enables/disabled creatureToggleButtons
	 * @param enabled
	 */
	private void toggleCreatures(boolean enabled){
		this.tglG.setEnabled(enabled);
		this.tglK.setEnabled(enabled);
		this.tglL.setEnabled(enabled);
		this.tglH.setEnabled(enabled);
		this.tglI.setEnabled(enabled);
		this.tglJ.setEnabled(enabled);
		this.tglS.setEnabled(enabled);
		this.tglR.setEnabled(enabled);
		this.tglQ.setEnabled(enabled);
		this.tglW.setEnabled(enabled);
		this.tglC.setEnabled(enabled);
		this.tglF_l.setEnabled(enabled);
		this.tglF_G.setEnabled(enabled);
		this.tglN_l.setEnabled(enabled);
		this.tglVirus.setEnabled(enabled);
		this.tglRedFish.setEnabled(enabled);
		this.tglBlueFish.setEnabled(enabled);
		this.tglSpring.setEnabled(enabled);
	
		
		this.tglT.setEnabled(!enabled);
		this.tglz.setEnabled(!enabled);
		this.tglm.setEnabled(!enabled);
		this.tgln.setEnabled(!enabled);
		this.tglq.setEnabled(!enabled);
		this.tglr.setEnabled(!enabled);
		this.tgla.setEnabled(!enabled);
		this.tglb.setEnabled(!enabled);
		this.tglbulb.setEnabled(!enabled);
		this.tglbulbBase.setEnabled(!enabled);
		this.tglInfo.setEnabled(!enabled);
		this.tgl7.setEnabled(!enabled);
		this.tgl8.setEnabled(!enabled);
		this.tgl9.setEnabled(!enabled);
		this.tglBush.setEnabled(!enabled);
		
	}
	
	public void deselectAll(){
		this.tglG.setSelected(false);
		this.tglK.setSelected(false);
		this.tglL.setSelected(false);
		this.tglH.setSelected(false);
		this.tglI.setSelected(false);
		this.tglJ.setSelected(false);
		this.tglF_l.setSelected(false);
		this.tglF_G.setSelected(false);
		this.tglN_l.setSelected(false);
		this.tglVirus.setSelected(false);
		this.tglRedFish.setSelected(false);
		this.tglBlueFish.setSelected(false);
	
		this.tglS.setSelected(false);
		this.tglR.setSelected(false);
		this.tglQ.setSelected(false);
		this.tglW.setSelected(false);
		this.tglC.setSelected(false);
		this.tgl2.setSelected(false);
		this.tgl3.setSelected(false);
		this.tgl4.setSelected(false);
		this.tglV.setSelected(false);
		this.tgl7.setSelected(false);
		this.tgl8.setSelected(false);
		this.tgl9.setSelected(false);
		this.tglT.setSelected(false);
		this.tgla.setSelected(false);
		this.tglb.setSelected(false);
		this.tglm.setSelected(false);
		this.tgln.setSelected(false);
		this.tglq.setSelected(false);
		this.tglr.setSelected(false);
			
		this.tglt.setSelected(false);
		this.tglu.setSelected(false);
		this.tglv.setSelected(false);
		this.tglw.setSelected(false);
		this.tglx.setSelected(false);
		this.tgly.setSelected(false);
		this.tglz.setSelected(false);
		this.tgl5.setSelected(false);
		this.tgl6.setSelected(false);
		this.tglYellowRock.setSelected(false);
		this.tglBlueRock.setSelected(false);
		this.tglGrayRock.setSelected(false);
		this.tglSandTop.setSelected(false);
		this.tglSandNormal.setSelected(false);
		this.tglWaterTile.setSelected(false);
		this.tglWaterPlant.setSelected(false);
		
		this.tgli.setSelected(false);
		this.tglj.setSelected(false);
		this.tglk.setSelected(false);
		this.tglg.setSelected(false);
		this.tglh.setSelected(false);
		this.tglP.setSelected(false);
		this.tglPx.setSelected(false);
		this.tglPy.setSelected(false);
		this.tglbulb.setSelected(false);
		this.tglbulbBase.setSelected(false);
		this.tglInfo.setSelected(false);
		
		this.tglLevel.setSelected(false);
		this.tglSpring.setSelected(false);
		this.tglBookMark.setSelected(false);
		this.tglFireBrickLeft.setSelected(false);
		this.tglFireBrickRight.setSelected(false);
		this.tglFireTileBase.setSelected(false);
		this.tglFireTileTop.setSelected(false);
		this.tglBookMark.setSelected(false);
		this.tglP.setSelected(false);
		this.tglPx.setSelected(false);
		this.tglPy.setSelected(false);
		this.tglSpring.setSelected(false);
		this.tglWaterZone.setSelected(false);
		this.tglRemoveWaterZone.setSelected(false);
		this.tglBookMark.setSelected(false);
		this.tglGirder.setSelected(false);
		
		
		
	}
	
	/**
	 * Draws the game image to the buffer.
	 */
	private void gameRender() {
		mario.setX(panelWidth/2+GameRenderer.tilesToPixels(canvas.hsbar.getValue()-1));
		mario.setIsJumping(true);
		//if (canvas.vsbar.getMaximum()!=0)  mario.setY(GameRenderer.tilesToPixels((int) (mapHeight*canvas.vsbar.getValue()/(float)canvas.vsbar.getMaximum())));
		mario.setY(GameRenderer.tilesToPixels(canvas.vsbar.getValue()));
		if(dbImage == null) {
			dbImage = createImage(this.panelWidth, this.panelHeight); 
			dbg = dbImage.getGraphics();  
			//return;
		}
    
		renderer.draw((Graphics2D) dbg, map,backgroundMap,foregroundMap,panelWidth, panelHeight,true);
	}
	
	/**
	 * Prints the graphics on Canvas
	 */
	private void printGraphics() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		//pj.setPrintable(canvas3D);
		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * returns true if dirty (file is changed and need to be saved) and if necessary shows a dialog box
	 * @param saveMessage
	 * @return
	 */
    public boolean isDirty(String saveMessage) {
        if (!dirty) return false;
        int result=JOptionPane.showConfirmDialog(this,saveMessage,"File Modified",
            JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
        switch (result) {
            case JOptionPane.YES_OPTION:
                if (saveToFile(false)) return false;
                break;
            case JOptionPane.NO_OPTION:
                return false;
        }
        return true;
    }
	
	
	/**
	 * Last Working directories/files
	 */
	private String lastDirectory=null;
	private String lastFileName=null;
	private String mapDir="java-mario-src/maps";
	
	  
    // Just in case I decide to put filename on title bar
    private void setLastFileName(String string) {
        lastFileName=string;
        if (string==null)string="Untitled Document";
        this.setTitle("Mario Editor -"+string.substring(string.lastIndexOf(File.separatorChar)+1));

        /*
        final String title="3D Graph Explorer";
        if (parentFrame!=null) {
            if (string==null) parentFrame.setTitle(title+" v1.01");
            else parentFrame.setTitle(title+" - "+string.substring(string.lastIndexOf(File.separatorChar)+1));
        }
        */
    }
    
    /**
     * Exit
     */
    private void close() {
        if (!isDirty("Do you want to save before exit?")) System.exit(0);
    }
    
    /**
     *  erases everything
     */
    private void newFile() {
    	if (!isDirty("Do you want to save before creating a new file?")) {
             canvas.invalidate();
         	loader = new GameLoader();
			renderer = new GameRenderer();
			mapWidth=400;
			mapHeight=18;
			map=new TileMap(mapWidth,mapHeight);
			backgroundMap=new TileMap(mapWidth,mapHeight);
			foregroundMap=new TileMap(mapWidth,mapHeight);
			mapBackData=new char[mapWidth][mapHeight];
			mapForeData=new char[mapWidth][mapHeight];
			mapMainData=new char[mapWidth][mapHeight];
			//backgroundMap = loader.loadOtherMaps("maps/backgroundMap.txt");
			//foregroundMap = loader.loadOtherMaps("maps/foregroundMap.txt");
			map.setPlayer(mario); // set the games main player to mario
			tglBackMap.setSelected(true);
			tglMainMap.setSelected(true);
			tglForeMap.setSelected(true);
			setEditMap();
			panelWidth=canvas.getWidth();
			panelHeight=canvas.getHeight();
		    int w=GameRenderer.pixelsToTiles(panelWidth);
		    int h=GameRenderer.pixelsToTiles(panelHeight);
		    Creature.WAKE_UP_VALUE_DOWN_RIGHT=w;
		    canvas.hsbar.setMaximum(Math.max(0, mapWidth-w-1));
		    canvas.vsbar.setMaximum(Math.max(0, mapHeight-h));
		    System.out.println(canvas.vsbar.getMaximum());
		    dbImage=null;
		    gameRender();
             
             dirty=false;
             lastFileName=null;
             setLastFileName(null);
             clearRedoUndostack();
            
    	}
    }
    
    
	/**
	 * returns filename by showing Open/Save dialogue box
	 * @param save flag to select opn/save dialogue
	 * @param ext extention of file  (ext (like ".ser") is ignored for save=false)
	 * @return
	 */
	private String getFileName(boolean save,String ext,String title) {
	    final JFileChooser fileChooser=new JFileChooser();
	    fileChooser.setDialogTitle(title);
	    fileChooser.setAcceptAllFileFilterUsed(false);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(ext.equals("txt")?"TEXT FILE":"IMAGE FILE", ext);
	    fileChooser.setFileFilter(filter);
	    
	    fileChooser.setCurrentDirectory(new File(lastDirectory==null?System.getProperty("user.dir"):lastDirectory));
	    int result;
	    if (save) result=fileChooser.showSaveDialog(this);
	    else result=fileChooser.showOpenDialog(this);
	    if (result==JFileChooser.APPROVE_OPTION) {
	        String fileName=fileChooser.getSelectedFile().getPath();
	        lastDirectory=fileName;
	        // it also works if the next line is commented out!
	        lastDirectory=lastDirectory.substring(0,lastDirectory.lastIndexOf(File.separatorChar));
	        if (save && fileName.indexOf('.')==-1) fileName+=ext;
	        if (save && (new File(fileName)).exists())
	            if (JOptionPane.showConfirmDialog(this,"File "+fileName+" already exists. Overwrite?","Warning",
	                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)==JOptionPane.NO_OPTION)
	                return null;
	        return fileName;
	    }
	    else return null;
	}
	

	/**
	 * Shows load dialog and loads file if dialogue is not canceled
	 */
	private void loadFromFile() {
	        loadFromFile(getFileName(false,"txt","Open new file..."));
	}
	    
	private void loadDefaultMap(){ 
		try {
			mapDir="java-mario-src/maps";
			MarioSoundManager22050Hz SM_22050_Hz = new MarioSoundManager22050Hz(new AudioFormat(22050, 8, 1, true, true));
	 		mario = new Mario(SM_22050_Hz);
	 		
			loader = new GameLoader();
			renderer = new GameRenderer();
			renderer.setBackground(ImageIO.read(new File("java-mario-src/backgrounds/background2.png")));
			map = loader.loadMapFromFile(mapDir+File.separatorChar+"map3.txt", null); // use the ResourceManager to load the game map
			
			mapWidth=map.getWidth();
			mapHeight=map.getHeight();
			backgroundMap=new TileMap(mapWidth,mapHeight);
			foregroundMap=new TileMap(mapWidth,mapHeight);
			loader.fillMap(mapDir+File.separatorChar+"back3.txt", backgroundMap);
			loader.fillMap(mapDir+File.separatorChar+"fore3.txt", foregroundMap);
			//mapMainData=loader.getMap("maps/map7.txt");
			mapBackData=new char[mapWidth][mapHeight];
			mapForeData=new char[mapWidth][mapHeight];
			mapMainData=new char[mapWidth][mapHeight];
			
			loader.fillMapFromFile(mapDir+File.separatorChar+"map3.txt",mapMainData,mapWidth,mapHeight);
			loader.fillMapFromFile(mapDir+File.separatorChar+"back3.txt",mapBackData,mapWidth,mapHeight);
			loader.fillMapFromFile(mapDir+File.separatorChar+"fore3.txt",mapForeData,mapWidth,mapHeight);

			//backgroundMap = loader.loadOtherMaps("maps/backgroundMap.txt");
			//foregroundMap = loader.loadOtherMaps("maps/foregroundMap.txt");
			map.setPlayer(mario); // set the games main player to mario
			tglBackMap.setSelected(true);
			tglMainMap.setSelected(true);
			tglForeMap.setSelected(true);
			setEditMap();
			panelWidth=canvas.getWidth();
			panelHeight=canvas.getHeight();
		    int w=GameRenderer.pixelsToTiles(panelWidth);
		    int h=GameRenderer.pixelsToTiles(panelHeight);
		    Creature.WAKE_UP_VALUE_DOWN_RIGHT=w;
		    canvas.hsbar.setMaximum(Math.max(0, mapWidth-w-1));
		    canvas.vsbar.setMaximum(Math.max(0, mapHeight-h));
		    System.out.println(canvas.vsbar.getMaximum());
		    dbImage=null;
		    gameRender();
		    canvas.repaint();
		    setLastFileName("map3.txt");
	        dirty=false;
		} catch (IOException e){
			System.out.println("Invalid Map.");
		}
		clearRedoUndostack();

	}
	 /**
	  * Loads file if exits and valid
	  * @param fileName file to be loaded
	  */
	 public void loadFromFile(String fileName) {
	        if (fileName==null) return;
	        File file=new File(fileName);
	        String mapDir=file.getParent();
	        try {
				MarioSoundManager22050Hz SM_22050_Hz = new MarioSoundManager22050Hz(new AudioFormat(22050, 8, 1, true, true));
		 		mario = new Mario(SM_22050_Hz);
		 		
				loader = new GameLoader();
				renderer = new GameRenderer();
				renderer.setBackground(ImageIO.read(new File("java-mario-src/backgrounds/background2.png")));
				map = loader.loadMapFromFile(mapDir+File.separatorChar+"map3.txt", null); // use the ResourceManager to load the game map
				loader.getBackGroundImageIndex();
				mapWidth=map.getWidth();
				mapHeight=map.getHeight();
				backgroundMap=new TileMap(mapWidth,mapHeight);
				foregroundMap=new TileMap(mapWidth,mapHeight);
				loader.fillMap(mapDir+File.separatorChar+"back3.txt", backgroundMap);
				loader.fillMap(mapDir+File.separatorChar+"fore3.txt", foregroundMap);
				//mapMainData=loader.getMap("maps/map7.txt");
				mapBackData=new char[mapWidth][mapHeight];
				mapForeData=new char[mapWidth][mapHeight];
				mapMainData=new char[mapWidth][mapHeight];
				
				loader.fillMapFromFile(mapDir+File.separatorChar+"map3.txt",mapMainData,mapWidth,mapHeight);
				loader.fillMapFromFile(mapDir+File.separatorChar+"back3.txt",mapBackData,mapWidth,mapHeight);
				loader.fillMapFromFile(mapDir+File.separatorChar+"fore3.txt",mapForeData,mapWidth,mapHeight);

				this.mapDir=mapDir;
				//backgroundMap = loader.loadOtherMaps("maps/backgroundMap.txt");
				//foregroundMap = loader.loadOtherMaps("maps/foregroundMap.txt");
				map.setPlayer(mario); // set the games main player to mario
				tglBackMap.setSelected(true);
				tglMainMap.setSelected(true);
				tglForeMap.setSelected(true);
				setEditMap();
				panelWidth=canvas.getWidth();
				panelHeight=canvas.getHeight();
			    int w=GameRenderer.pixelsToTiles(panelWidth);
			    int h=GameRenderer.pixelsToTiles(panelHeight);
			    Creature.WAKE_UP_VALUE_DOWN_RIGHT=w;
			    canvas.hsbar.setMaximum(Math.max(0, mapWidth-w-1));
			    canvas.vsbar.setMaximum(Math.max(0, mapHeight-h));
			    System.out.println(canvas.vsbar.getMaximum());
			    dbImage=null;
			    gameRender();
			    canvas.repaint();
			    setLastFileName(fileName);
	 	        dirty=false;
	 	       clearRedoUndostack();

			} catch (IOException e){
				System.out.println("Invalid Map.");
			}
	        
	        try {
	    		//  ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
	    		//  ArrayList<Element3D> list=(ArrayList<Element3D>) is.readObject();
	    		 // sceneManager.getElement3DList().clear();
	    		//  sceneManager.getElement3DList().addAll(list);
	    		 // Preferences preferences=(Preferences) is.readObject();
	    		 /// preferences.setLookandFeel(Globalsettings.lookandFeel);
	    		 // applySettings(preferences,true,true);
	    		  //canvas3D.setScene(sceneManager.createScene(true));
	 	          //canvas3D.refresh();
	 	    
	 	          setLastFileName(fileName);
	 	          dirty=false;
			 } catch (Exception e) {
				  JOptionPane.showMessageDialog(this,e.getMessage()+fileName,"Error",JOptionPane.ERROR_MESSAGE);
			 }
	 }
	 
	 
	 /**
	 * Shows save dialog optionally (for save as ... or when file is being saved for firsttime), and saves, returns true if succesful
	 * @param askName if new filename is to be chosen to save
	 * @return
	 */
	 private boolean saveToFile(boolean askName) {
        String fileName;
        if (askName || lastFileName==null) fileName=getFileName(true,"txt","Save file as ...");
        else fileName="java-mario-src/map/map3.txt";
        if (fileName==null) return false;
      
        File file=new File(fileName);
        String mapDir=file.getParent();

   	    try {
   		      new ObjectOutputStream(new FileOutputStream(fileName));
	    	//  os.writeObject(sceneManager.getElement3DList());
	    	//  os.writeObject(Globalsettings.getSettings());  
   		      loader.saveMaptoFile(mapDir+File.separatorChar+"map3.txt",mapMainData,mapHeight,mapWidth);
   		      loader.saveMaptoFile(mapDir+File.separatorChar+"back3.txt",mapBackData,mapHeight,mapWidth);
   		      loader.saveMaptoFile(mapDir+File.separatorChar+"fore3.txt",mapForeData,mapHeight,mapWidth);
	    	  dirty=false;
	    	  setLastFileName(fileName);
	 	} catch (IOException e) {
			  JOptionPane.showMessageDialog(this,"Could not save to file "+fileName,"Warning",JOptionPane.WARNING_MESSAGE);
		      return false;  
		}
        return true;
    }
	 
	 
	private void runGame(){
		if (lastFileName==null){
			JOptionPane.showMessageDialog(this, "Please Import Map first then run the Game.", "No map loaded!!!", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		File file=new File(lastFileName);
	    String mapDir=file.getParent();

		new GameFrame(mapDir);
	}
	
	
	
	 
	 
	
	//utility methods
	//********************************************************
	
	public BufferedImage loadImage(String filename) {
			BufferedImage img = null;
			try {
			    img = ImageIO.read(new File(filename));
			} catch (IOException e) { }
			return img;
	}
		
    
    /**
     * Saves current Canvas Drawing in PNG Format
     */
    private void exportPNG() {
        String fileName=getFileName(true,"png","Export to image as....");
        if (fileName==null) return;
      
        try {
          
   		ImageIO.write((RenderedImage) dbImage, "png", new File(fileName));
           
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"Could not export to file "+fileName,"Warning",JOptionPane.WARNING_MESSAGE);
        }
        
    }
	
  
	
	/**
	 * The main method; uses zero arguments in the args array.
	 * @param args the command line arguments
	 */
	public static final void main(String[] args) {
		// attempt to use the nimbus look and feel
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
			        break;
		        }
		    }
		} catch (Exception e) {
			// completely ignore the error and just use the default look and feel
		}
		
		
		// show the GUI on the EDT
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Gui();
			}
		});
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}


	@Override
	public void mousePressed(MouseEvent e) {
		int x=e.getX();
		int y=e.getY();
		initMouseX=renderer.firstTileX+GameRenderer.pixelsToTiles(x);
		initMouseY=renderer.firstTileY+GameRenderer.pixelsToTiles(y);
		if (e.getButton()==MouseEvent.BUTTON3){
			if (editMap==0){
				isMapChanged=setTileat(backgroundMap, x,  y, "delete");
			}else if (editMap==2){
				isMapChanged=setTileat(foregroundMap, x,  y, "delete");
			}else{
				if (!deleteCreature(x,y)){
					isMapChanged=setTileat(map, x,  y, "delete");
				}else{
					isMapChanged=true;
				}
			}
		}else if(toolID=="RemoveWaterZone"){
			isMapChanged=removeWaterZones(initMouseX,initMouseY);
		}else{	
			if (toolID=="?"|| toolID=="("|| toolID==")"||toolID=="<" ||toolID==">" ||toolID=="^" ||toolID=="P" ||toolID=="G" || toolID=="K" ||toolID=="L"||toolID=="C"||toolID=="H"||toolID=="I" ||toolID=="J" || toolID=="Q"||toolID=="W" ){
				int n= map.creatures().size();
			 	x=GameRenderer.tilesToPixels(renderer.firstTileX)+x;
			    y=GameRenderer.tilesToPixels(renderer.firstTileY)+y;

				for (int i=0;i<n;i++){
				    Creature c =map.creatures().get(i);
				    // check if the two sprites' boundaries intersect
				    if (x < c.getX() + c.getWidth() && x > c.getX()  && 
				    		y < c.getY() + c.getHeight() && y > c.getY()){
				    	map.creatures().remove(i);
				    	isMapChanged=true;
			    	    //gameRender();
						//canvas.repaint();
				    	break;
				    }
				}
				isMapChanged=setTileat(map,x-GameRenderer.tilesToPixels(renderer.firstTileX), y-GameRenderer.tilesToPixels(renderer.firstTileY), toolID);
			}else{
				if (editMap==0){
					isMapChanged=setTileat(backgroundMap, x,  y, toolID);
				}else if (editMap==2){
					isMapChanged=setTileat(foregroundMap, x,  y, toolID);
				}else{
					if (!deleteCreature(x,y)){
						isMapChanged=setTileat(map, x,  y, toolID);
					}else{
						isMapChanged=true;
					}
				}
			}
		}
	 	//initMouseX=GameRenderer.tilesToPixels(renderer.firstTileX)+x;
	 	//initMouseY=GameRenderer.tilesToPixels(renderer.firstTileY)+y;
	 	
		//canvas.invalidate();
		if (isMapChanged){
			gameRender();
		}
		canvas.repaint();

	}

	/**
	 * 
	 * @param x1
	 * @param y1
	 * @return true if create is removed
	 */
	private boolean deleteCreature(int x1,int y1){
	 	int x=GameRenderer.tilesToPixels(renderer.firstTileX)+x1;
	    int y=GameRenderer.tilesToPixels(renderer.firstTileY)+y1;

		int n= map.creatures().size();
		for (int i=0;i<n;i++){
		    Creature c =map.creatures().get(i);
		    // check if the two sprites' boundaries intersect
		    if (x < c.getX() + c.getWidth() && x > c.getX()  && 
		    		y < c.getY() + c.getHeight() && y > c.getY()){
		       	mapMainData[GameRenderer.pixelsToTiles(c.getX())][GameRenderer.pixelsToTiles(c.getY())]=' ';
		    	map.creatures().remove(i);
		        //mapMainData[renderer.firstTileX+renderer.pixelsToTiles(x1)][renderer.firstTileY+renderer.pixelsToTiles(y1)]=' ';
		    	return true;
		    }
		}
		return false;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		isMapChanged=false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
   	int x=e.getX();
		int y=e.getY();
		
		 if(toolID=="WaterZone" || toolID=="RemoveWaterZone" ){
		    if (x<0 || x>panelWidth || y<0 || y>panelWidth) return ;
		    x=renderer.firstTileX+GameRenderer.pixelsToTiles(x);
			y=renderer.firstTileY+GameRenderer.pixelsToTiles(y);
		    if (SwingUtilities.isRightMouseButton(e)|| toolID=="RemoveWaterZone" ){
		    	isMapChanged=removeWaterZones(x,y);
		    }else{
		    	isMapChanged=updateWaterZones(x,y);
		    }
		 
		 }else if (SwingUtilities.isRightMouseButton(e)){
			if (editMap==0){
				isMapChanged=setTileat(backgroundMap, x,  y, "delete");
			}else if (editMap==2){
				isMapChanged=setTileat(foregroundMap, x,  y, "delete");
			}else{
				isMapChanged=setTileat(map, x,  y, "delete");
			}
			//setTileat( x,  y, "delete");
		}else{
			if (toolID=="?"|| toolID=="("|| toolID==")"||  toolID=="<" ||toolID==">" ||toolID=="^" ||toolID=="P" || toolID=="G" || toolID=="K" ||toolID=="L"||toolID=="C"||toolID=="H"||toolID=="I" || toolID=="J" ||toolID=="T"||toolID=="Q"||toolID=="W")return;
			
			if (editMap==0){
				isMapChanged=setTileat(backgroundMap, x,  y, toolID);
			}else if (editMap==2){
				isMapChanged=setTileat(foregroundMap, x,  y, toolID);
			}else{
				isMapChanged=setTileat( map,x,  y, (SwingUtilities.isRightMouseButton(e))?"delete":toolID);
			}
			
			
		}
		 
		//canvas.invalidate();
		gameRender();
		canvas.repaint();
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	 
	/**
	 * 
	 * @param x
	 * @param y
	 * @return true is zone is actually  added
	 */
	public boolean updateWaterZones(int x, int y){
		Rectangle rect=new Rectangle(Math.min(x, initMouseX),Math.min(y, initMouseY),Math.abs(x-initMouseX),Math.abs(y-initMouseY));
		if(rect.getWidth()==0 || rect.getHeight()==0)return false;
		int n=map.waterZones().size();
		if(n==0){
			if(isMapChanged==false)pushUndo();
			map.waterZones().add(rect);
		}else{
			Rectangle rect1=map.waterZones().get(n-1);
			if (rect.intersects(rect1)){
				if(isMapChanged==false)pushUndo();
				map.waterZones().set(n-1, rect);
			}else{
				if(isMapChanged==false)pushUndo();
				map.waterZones().add(rect);
			}
		}
	
		return true;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return true is zone is removed
	 */
	public boolean removeWaterZones(int x, int y){
		Rectangle rect=new Rectangle(Math.min(x, initMouseX),Math.min(y, initMouseY),Math.abs(x-initMouseX),Math.abs(y-initMouseY));
		//if(rect.getWidth()==0 || rect.getHeight()==0)return false;
		int n=map.waterZones().size();
		if(n==0) return false;
		
		for (int i=0;i<n;i++){
			Rectangle rect1=map.waterZones().get(i);
			if (rect.intersects(rect1) ||rect1.contains(x,y)){
				if(isMapChanged==false)pushUndo();
				map.waterZones().remove(i);
				return true;
			}
		}
	
		return false;
	}
	
	
	
	/**
	 * 
	 * @param pMap
	 * @param x
	 * @param y
	 * @param toolID
	 * @return true if tile successfully set
	 */
    public boolean setTileat(TileMap pMap,int x, int y,String toolID){
    	if (toolID=="")return false;
    	if (x<0 || x>panelWidth || y<0 || y>panelWidth) return false;
    	x=renderer.firstTileX+GameRenderer.pixelsToTiles(x);
	    y=renderer.firstTileY+GameRenderer.pixelsToTiles(y);
	    
	    if (x<0|| x>=mapWidth || y<0|| y>=mapHeight) return false;		
	    
	    //push undo
		if(isMapChanged==false)pushUndo();

	    if (pMap==foregroundMap){
	    	mapForeData[x][y]=toolID.charAt(0);
	    }else if(pMap==backgroundMap){
	    	mapBackData[x][y]=toolID.charAt(0);
	    }else {
	    	mapMainData[x][y]=toolID.charAt(0);
	    }
	    int pixelX = GameRenderer.tilesToPixels(x);
		int pixelY = GameRenderer.tilesToPixels(y);
		// enumerate the possible creatures/tiles...
		if (toolID == "G") {
			pMap.creatures().add(new Goomba(pixelX, pixelY, null));
		} else if (toolID == "K") {
			pMap.creatures().add(new RedKoopa(pixelX, pixelY, null,false));
		} else if (toolID == "L") {
			RedKoopa rk=new  RedKoopa(pixelX, pixelY, null,true);
			pMap.creatures().add(rk);
		} else if (toolID == "H") {
			pMap.creatures().add(new Thorny(pixelX, pixelY, null));
		} else if (toolID == "I") {
			FireDisc th= new FireDisc(pixelX, pixelY, null);
			th.xCollide(new Point(0,0));
			pMap.creatures().add(th);
		} else if (toolID == "N") {
			Bowser th= new Bowser(pixelX, pixelY, null);
			th.xCollide(new Point(0,0));
			pMap.creatures().add(th);
		} else if (toolID == "&") {
			Latiku th= new Latiku(pixelX, pixelY, null,map);
			th.xCollide(new Point(0,0));
			pMap.creatures().add(th);
		} else if (toolID == "F") {
			FlyRedKoopa th= new FlyRedKoopa(pixelX, pixelY, null);
			th.xCollide(new Point(0,0));
			pMap.creatures().add(th);
		} else if (toolID == "O") {
			FlyGoomba th= new FlyGoomba(pixelX, pixelY, null);
			th.xCollide(new Point(0,0));
			pMap.creatures().add(th);
		} else if (toolID == "?") {
			Virus t = new Virus(pixelX, pixelY,null);
			pMap.creatures().add(t);
		} else if (toolID=="(") {
			RedFish t = new RedFish(pixelX, pixelY,null);
			pMap.creatures().add(t);
		} else if (toolID == ")") {
			BlueFish t = new BlueFish(pixelX, pixelY,null);
			pMap.creatures().add(t);
		} else if (toolID == ",") {
			JumpingFish t = new JumpingFish(pixelX, pixelY,Math.random()>0.6f,null);
			pMap.creatures().add(t);
		} else if (toolID == "l") {
			LevelComplete l= new LevelComplete(pixelX, pixelY);
			pMap.creatures().add(l);
		} else if (toolID == "J") {
			pMap.creatures().add(new Piranha(pixelX, pixelY,null));
		} else if (toolID == "V") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(56));
			pMap.setTile(x, y, t);
		} else if (toolID == "R") {
			RotatingBlock r = new RotatingBlock(pixelX, pixelY);
			pMap.setTile(x, y, r);
			pMap.animatedTiles().add(r);
		} else if (toolID=="B") {
			Brick b = new Brick(pixelX, pixelY,pMap, plain.get(77),null,3,false);
			pMap.setTile(x, y, b);
			pMap.animatedTiles().add(b);
		} else if (toolID == "3") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(4));
			pMap.setTile(x, y, t);
		} else if (toolID == "4") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(10));
			pMap.setTile(x, y, t);
		} else if (toolID == "5") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(3));
			pMap.setTile(x, y, t);
		} else if (toolID == "6") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(5));
			pMap.setTile(x, y, t);
		} else if (toolID == "2") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(86));
			pMap.setTile(x, y, t);
		} else if (toolID == "{") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(81));
			pMap.setTile(x, y, t);
		} else if (toolID == "Q") {
			QuestionBlock q = new QuestionBlock(pixelX, pixelY, pMap, null, true, false);
			pMap.setTile(x, y, q);
			pMap.animatedTiles().add(q);
		} else if (toolID == "W") {
			QuestionBlock q = new QuestionBlock(pixelX, pixelY, pMap, null, false, true);
			pMap.setTile(x, y, q);
			pMap.animatedTiles().add(q);
		} else if (toolID == "S") {
			pMap.creatures().add(new RedShell(pixelX, pixelY, pMap, null, true,false));
		} else if(toolID == "C") {
			pMap.creatures().add(new Coin(pixelX, pixelY));
		} else if(toolID == "P") {
			Platform p = new Platform(pixelX, pixelY);
			pMap.creatures().add(p);
		} else if(toolID == "<") {
			Platform p = new Platform(pixelX, pixelY);
			pMap.creatures().add(p);
		} else if(toolID == ">") {
			Platform p = new Platform(pixelX, pixelY);
			pMap.creatures().add(p);
		} else if(toolID == "^") {
			Spring s = new Spring(pixelX, pixelY,map);
			pMap.creatures().add(s);
		}else if(toolID == "9") {
			GameTile t = new GameTile(pixelX, pixelY, mushroomTree);
			pMap.setTile(x, y, t);
			//newMap.slopedTiles().add(t);
		} else if(toolID == "8") {
			GameTile t = new GameTile(pixelX, pixelY, woodenBridge);
			pMap.setTile(x, y, t);
		} else if(toolID == "7") {
			GameTile t = new GameTile(pixelX, pixelY, waterRock);
			pMap.setTile(x, y, t);
		} else if(toolID == "i") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(42));
			pMap.setTile(x, y, t);
		} else if(toolID == "j") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(43));
			pMap.setTile(x, y, t);
		} else if(toolID == "k") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(44));
			pMap.setTile(x, y, t);
		} else if(toolID == "g") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(9));
			pMap.setTile(x, y, t);
		} else if(toolID == "h") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(11));
			pMap.setTile(x, y, t);
		}else if (toolID == "n") {
			pMap.setTile(x, y, plain.get(92));
		} else if (toolID == "m") {
			pMap.setTile(x, y, plain.get(93));
		} else if (toolID == "a") {
			pMap.setTile(x, y, plain.get(90));
		} else if (toolID == "b") {
			pMap.setTile(x, y, plain.get(91));
		} else if (toolID == "q") { // rock left
			pMap.setTile(x, y, plain.get(48));
		} else if (toolID == "r") { // rock right
			pMap.setTile(x, y, plain.get(49));
		} else if (toolID == "z") { // tree stem
			pMap.setTile(x, y, plain.get(75));
		} else if (toolID=="T") {
			Tree t = new Tree(pixelX, pixelY);
			pMap.setTile(x, y, t);
			pMap.animatedTiles().add(t);
		} else if (toolID=="}") {
			Bush t = new Bush(pixelX, pixelY);
			pMap.setTile(x, y, t);
			pMap.animatedTiles().add(t);
		} else if(toolID == "t") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(96)); //pipe top left
			pMap.setTile(x, y, t);
		} else if(toolID == "u") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(97)); //pipe top middle
			pMap.setTile(x, y, t);
		} else if(toolID == "v") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(98)); //pipe top right
			pMap.setTile(x, y, t);
		} else if(toolID == "w") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(99)); //pipe base left
			pMap.setTile(x, y, t);
		} else if(toolID == "x") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(100)); //pipe base middle
			pMap.setTile(x, y, t);
		} else if(toolID == "y") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(101)); //pipe base right
			pMap.setTile(x, y, t);
		} else if(toolID == "*") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(78)); //pipe base right
			pMap.setTile(x, y, t);
		} else if(toolID == "!") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(84)); //pipe base right
			pMap.setTile(x, y, t);
		} else if(toolID == "@") {
			GameTile t = new InfoPanel(pixelX, pixelY, "This is"); //pipe base right
			pMap.setTile(x, y, t);
		} else if(toolID == "%") {
			GameTile t = new GameTile(pixelX, pixelY,plain.get(36)); //BlueRock 
			pMap.setTile(x, y, t);
		} else if(toolID == "\\") {
			GameTile t = new GameTile(pixelX, pixelY,plain.get(37)); //Yellowrock
			pMap.setTile(x, y, t);
		} else if(toolID == "$") {
			GameTile t = new GameTile(pixelX, pixelY,plain.get(38)); //greyrock
			pMap.setTile(x, y, t);
		} else if(toolID == "-") {
			GameTile t = new GameTile(pixelX, pixelY,plain.get(40)); // 
			pMap.setTile(x, y, t);
		} else if(toolID == "+") {
			GameTile t = new GameTile(pixelX, pixelY,plain.get(46)); //
			pMap.setTile(x, y, t);
		} else if(toolID == "~") {
			GameTile t = new GameTile(pixelX, pixelY,plain.get(89)); //watertile
			pMap.setTile(x, y, t);
		} else if(toolID == "M"){
			pMap.addBookMark(new Point(x,y));
			GameTile t = new GameTile(pixelX, pixelY, plain.get(82));
			t.setIsCollidable(false);
			pMap.setTile(x, y, t);
		} else if (toolID == "[") {
			FireTile r = new FireTile(pixelX, pixelY);
			pMap.setTile(x, y, r);
			pMap.animatedTiles().add(r);
		} else if (toolID == "]") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(25));
			pMap.setTile(x, y, t);
		} else if (toolID == "_") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(28));
			pMap.setTile(x, y, t);
		} else if (toolID == ";") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(26));
			pMap.setTile(x, y, t);
		} else if (toolID == ":") {
			GameTile t = new GameTile(pixelX, pixelY, plain.get(27));
			pMap.setTile(x, y, t);
		} else if (toolID=="delete"){
			if (pMap==foregroundMap){
			    mapForeData[x][y]=' ';
			}else if(pMap==backgroundMap){
			    mapBackData[x][y]=' ';
			   // System.out.println("delete");
			}else {
			    mapMainData[x][y]=' ';
			}	
			pMap.getTiles()[x][y]= null;
			System.out.println("delete");
		}else{
			return false;
		}
		return true;
   }
	
  class BufferedPanel extends JPanel implements AdjustmentListener{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JScrollBar hsbar, vsbar;
	  
	  public BufferedPanel(){
		  super();
		  hsbar=new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,0);
		  vsbar=new JScrollBar(JScrollBar.VERTICAL,0,0,0,0);
		  hsbar.addAdjustmentListener(this);
		  vsbar.addAdjustmentListener(this);
		  setLayout(new BorderLayout());
		  add(hsbar,BorderLayout.SOUTH);
		  add(vsbar,BorderLayout.EAST);
		  
	  }
	  
	  
	  @Override
	  public void paintComponent(Graphics g){
		  super.paintComponent(g);
		  if  (dbImage != null)  {
				g.drawImage(dbImage, 0, 0, null);
				
			} 
	  }


	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		gameRender();
		//invalidate();
		repaint();
	}
  }

  
	/**
	 * saves simulation and return saved xml as string	
	 * @return
	 */
	private String dumpMapData(){
		String s =loader.saveMapToString(mapMainData,mapHeight,mapWidth);
	  	//System.out.print(s);
		return s;
	}
	
	void doUndo() {
		
		if (undoStack.size() == 0)
			return;
		String s=dumpMapData();
		//if (redoStack.size() == 0 ||! (s.compareTo(redoStack.lastElement()) == 0))
		redoStack.add(s);
		
		s = undoStack.remove(undoStack.size() - 1);
		loadFromString(s);
		enableUndoRedo();
		gameRender();
		canvas.repaint();
		System.out.println("undo Called: " +"undo stack size= " +undoStack.size() +"; redo stack size= " +redoStack.size() );

	}

	void doRedo() {
		if (redoStack.size() == 0)
			return;
		String s=dumpMapData();
		//if (undoStack.size() == 0 ||! (s.compareTo(undoStack.lastElement()) == 0))
		undoStack.add(s);
		
		s = redoStack.remove(redoStack.size() - 1);
		loadFromString(s);
		enableUndoRedo();
		gameRender();
		canvas.repaint();
		System.out.println("Redo Called: " +"undo stack size= " +undoStack.size() +"; redo stack size= " +redoStack.size() );

	}
	
	
	//enable/disable undo redo button according to stack size
	void enableUndoRedo() {
		btnRedo.setEnabled(redoStack.size() > 0);
		btnUndo.setEnabled(undoStack.size() > 0);
	}

	/**
	 * Insert in undo stack
	 */
	void pushUndo() {
		isMapChanged=true;
		redoStack.removeAllElements();
		String s=dumpMapData();   
		if (undoStack.size() > 0 && s.compareTo(undoStack.lastElement()) == 0)
			return;
		undoStack.add(s);
		enableUndoRedo();
		System.out.println("undo pushed: " +"undo stack size= " +undoStack.size() +"; redo stack size= " +redoStack.size() );
		
	}

	/**
	 * clears redo undostacks
	 */
	private void clearRedoUndostack() {
		redoStack.removeAllElements();
		undoStack.removeAllElements();
		enableUndoRedo();
	}
	 
	
	 /**
	  * Loads file if exits and valid
	  * @param fileName file to be loaded
	  */
	 public void loadFromString(String data) {
	    map = loader.loadMapfromString(data, null);
	    
	    loader.fillMapFromString(data,mapMainData,mapWidth,mapHeight);
		loader.getBackGroundImageIndex();
		mapWidth=map.getWidth();
		mapHeight=map.getHeight();
		map.setPlayer(mario); // set the games main player to mario
		tglMainMap.setSelected(true);
		tglForeMap.setSelected(true);
		setEditMap();
		panelWidth=canvas.getWidth();
		panelHeight=canvas.getHeight();
		int w=GameRenderer.pixelsToTiles(panelWidth);
		int h=GameRenderer.pixelsToTiles(panelHeight);
		Creature.WAKE_UP_VALUE_DOWN_RIGHT=w;
		canvas.hsbar.setMaximum(Math.max(0, mapWidth-w-1));
		canvas.vsbar.setMaximum(Math.max(0, mapHeight-h));
		System.out.println(canvas.vsbar.getMaximum());
		dbImage=null;
		gameRender();
		canvas.repaint();
	 	dirty=false;
	 }
	 
	 
}
