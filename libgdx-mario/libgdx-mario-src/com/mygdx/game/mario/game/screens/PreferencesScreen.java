
package com.mygdx.game.mario.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.mario.MarioResourceManager;
import com.mygdx.game.mario.MarioSoundManager;
import com.mygdx.game.mario.game.screens.transition.ScreenTransition;
import com.mygdx.game.mario.game.screens.transition.ScreenTransitionSlide;


/**
 * Experimental screen (not used in game)
 * @author maheshkurmi
 *
 */
public class PreferencesScreen extends AbstractGameScreen {
	// Location of description file for skins
	public static final String SKIN_UI = "assets-mario/skin/uiskin.json";
	public static final String SKIN_ATLAS = "assets-mario/skin/uiskin.atlas";

	protected Stage uiStage;
	protected Table uiTable;

	public final int viewWidth = 640;
	public final int viewHeight = 480;
	Skin skin;
	Label fpsLabel;
	public PreferencesScreen(AbstractGame g) {
		super(g);
		MarioSoundManager.instance.play(MarioResourceManager.instance.music.menuMusic);
		 skin = new Skin();
	}

	
	public void createOld() {
		skin=new Skin(Gdx.files.internal(SKIN_UI),new TextureAtlas(SKIN_ATLAS));
	     
	    uiStage   = new Stage( new StretchViewport(640,480) );
		uiTable = new Table();
		uiTable.setFillParent(true);
		uiStage.addActor(uiTable);
		uiTable.background( game.skin.getDrawable("backTex") );
		Table table = new Table();
		final ScrollPane scroll = new ScrollPane(table, skin);

		InputListener stopTouchDown = new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return false;
			}
		};

		table.pad(10).defaults().expandX().space(4);
		for (int i = 0; i < 100; i++) {
			table.row();
			table.add(new Label(i + "uno", skin)).expandX().fillX();

			TextButton button = new TextButton(i + "dos", skin);
			table.add(button);
			button.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					System.out.println("click " + x + ", " + y);
				}
			});

			Slider slider = new Slider(0, 100, 1, false, skin);
			slider.addListener(stopTouchDown); // Stops touchDown events from propagating to the FlickScrollPane.
			table.add(slider);

			table.add(new Label(i + "tres long0 long1 long2 long3 long4 long5 long6 long7 long8 long9 long10 long11 long12", skin));
		}

		final TextButton flickButton = new TextButton("Flick Scroll", skin.get("toggle", TextButtonStyle.class));
		flickButton.setChecked(true);
		flickButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				scroll.setFlickScroll(flickButton.isChecked());
			}
		});

		final TextButton fadeButton = new TextButton("Fade Scrollbars", skin.get("toggle", TextButtonStyle.class));
		fadeButton.setChecked(true);
		fadeButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				scroll.setFadeScrollBars(fadeButton.isChecked());
			}
		});

		final TextButton smoothButton = new TextButton("Smooth Scrolling", skin.get("toggle", TextButtonStyle.class));
		smoothButton.setChecked(true);
		smoothButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				scroll.setSmoothScrolling(smoothButton.isChecked());
			}
		});

		final TextButton onTopButton = new TextButton("Scrollbars On Top", skin.get("toggle", TextButtonStyle.class));
		onTopButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				scroll.setScrollbarsOnTop(onTopButton.isChecked());
			}
		});

		
		Label nameLabel = new Label("Name:", skin);
	    TextField nameText = new TextField("", skin);
	    Label addressLabel = new Label("Address:", skin);
	    TextField addressText = new TextField("", skin);
	    
	    uiTable.add(nameLabel);
	    uiTable.add(nameText).colspan(3);
	    uiTable.row();
	    uiTable.add(addressLabel);
	    uiTable.add(addressText).colspan(3);
		uiTable.row();
		uiTable.add(scroll).expand().fill().colspan(4);
		uiTable.row().space(10).padBottom(10);
		uiTable.add(flickButton).right().expandX();
		uiTable.add(onTopButton);
		uiTable.add(smoothButton);
		uiTable.add(fadeButton).left().expandX();
	}
	
	public void create(){
		skin=new Skin(Gdx.files.internal(SKIN_UI),new TextureAtlas(SKIN_ATLAS));
	     
	    uiStage   = new Stage( new StretchViewport(640,480) );
		uiTable = new Table();
		uiTable.setFillParent(true);
		uiStage.addActor(uiTable);
		uiTable.background( game.skin.getDrawable("backTex") );

		// Group.debug = true;
		ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
		style.imageUp = new TextureRegionDrawable(MarioResourceManager.instance.mario.Mario_Big_Change_Direction_Left);
		style.imageDown = new TextureRegionDrawable(MarioResourceManager.instance.mario.Mario_Big_Change_Direction_Right);
		ImageButton iconButton = new ImageButton(style);

		Button buttonMulti = new TextButton("Multi\nLine\nToggle", skin, "toggle");
		Button imgButton = new Button(new Image(MarioResourceManager.instance.gameTiles.Bush_1), skin);
		Button imgToggleButton = new Button(new Image(MarioResourceManager.instance.gameTiles.Bush_2), skin, "toggle");

		Label myLabel = new Label("this is some text.", skin);
		myLabel.setWrap(true);

		Table t = new Table();
		t.row();
		t.add(myLabel);

		t.layout();

		final CheckBox checkBox = new CheckBox(" Continuous rendering", skin);
		checkBox.setChecked(true);
		final Slider slider = new Slider(0, 10, 1, false, skin);
		slider.setAnimateDuration(0.3f);
		TextField textfield = new TextField("", skin);
		textfield.setMessageText("Click here!");
		textfield.setAlignment(Align.center);
		final SelectBox<String> selectBox = new SelectBox<String> (skin);
		selectBox.getStyle().listStyle.selection.setRightWidth(10);
		selectBox.getStyle().listStyle.selection.setLeftWidth(20);
		selectBox.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println(selectBox.getSelected());
			}
		});
		selectBox.setItems("Android1", "Windows1 long text in item", "Linux1", "OSX1", "Android2", "Windows2", "Linux2", "OSX2",
			"Android3", "Windows3", "Linux3", "OSX3", "Android4", "Windows4", "Linux4", "OSX4", "Android5", "Windows5", "Linux5",
			"OSX5", "Android6", "Windows6", "Linux6", "OSX6", "Android7", "Windows7", "Linux7", "OSX7");
		selectBox.setSelected("Linux6");
		Image imageActor = new Image(MarioResourceManager.instance.creatures.Fire_Flower);
		ScrollPane scrollPane = new ScrollPane(imageActor);
		
		Object[] listEntries = {"This is a list entry1", "And another one1", "The meaning of life1", "Is hard to come by1",
				"This is a list entry2", "And another one2", "The meaning of life2", "Is hard to come by2", "This is a list entry3",
				"And another one3", "The meaning of life3", "Is hard to come by3", "This is a list entry4", "And another one4",
				"The meaning of life4", "Is hard to come by4", "This is a list entry5", "And another one5", "The meaning of life5",
				"Is hard to come by5"};

		List list = new List(skin);
		list.setItems(listEntries);
		list.getSelection().setMultiple(true);
		list.getSelection().setRequired(false);
		// list.getSelection().setToggle(true);
		ScrollPane scrollPane2 = new ScrollPane(list, skin);
		scrollPane2.setFlickScroll(false);
		Label minSizeLabel = new Label("minWidth cell", skin); // demos SplitPane respecting widget's minWidth
		Table rightSideTable = new Table(skin);
		rightSideTable.add(minSizeLabel).growX().row();
		rightSideTable.add(scrollPane2).grow();
		SplitPane splitPane = new SplitPane(scrollPane, rightSideTable, false, skin, "default-horizontal");
		fpsLabel = new Label("fps:", skin);

		// configures an example of a TextField in password mode.
		final Label passwordLabel = new Label("Textfield in password mode: ", skin);
		final TextField passwordTextField = new TextField("", skin);
		passwordTextField.setMessageText("password");
		passwordTextField.setPasswordCharacter('*');
		passwordTextField.setPasswordMode(true);

		buttonMulti.addListener(new TextTooltip(
			"This is a tooltip! This is a tooltip! This is a tooltip! This is a tooltip! This is a tooltip! This is a tooltip!",
			skin));
		Table tooltipTable = new Table(skin);
		tooltipTable.pad(10).background("default-round");
		tooltipTable.add(new TextButton("Fancy tooltip!", skin));
		imgButton.addListener(new Tooltip(tooltipTable));

		// window.debug();
		Window window = new Window("Dialog", skin);
		window.getTitleTable().add(new TextButton("X", skin)).height(window.getPadTop());
		window.setPosition(0, 0);
		window.defaults().spaceBottom(10);
		window.row().fill().expandX();
		window.add(iconButton);
		window.add(buttonMulti);
		window.add(imgButton);
		window.add(imgToggleButton);
		window.row();
		window.add(checkBox);
		window.add(slider).minWidth(100).fillX().colspan(3);
		window.row();
		window.add(selectBox).maxWidth(100);
		window.add(textfield).minWidth(100).expandX().fillX().colspan(3);
		window.row();
		window.add(splitPane).fill().expand().colspan(4).maxHeight(200);
		window.row();
		window.add(passwordLabel).colspan(2);
		window.add(passwordTextField).minWidth(100).expandX().fillX().colspan(2);
		window.row();
		window.add(fpsLabel).colspan(4);
		window.setResizable(true);
		window.pack();

		// stage.addActor(new Button("Behind Window", skin));
		uiStage.addActor(window);

		textfield.setTextFieldListener(new TextFieldListener() {
			public void keyTyped (TextField textField, char key) {
				if (key == '\n') textField.getOnscreenKeyboard().show(false);
			}
		});

		slider.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Gdx.app.log("UITest", "slider: " + slider.getValue());
			}
		});

		iconButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				new Dialog("Some Dialog", skin, "dialog") {
					protected void result (Object object) {
						System.out.println("Chosen: " + object);
					}
				}.text("Are you enjoying this demo?").button("Yes", true).button("No", false).key(Keys.ENTER, true)
					.key(Keys.ESCAPE, false).show(uiStage);
			}
		});

		checkBox.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Gdx.graphics.setContinuousRendering(checkBox.isChecked());
			}
		});
	}

	@Override
	public void update(float deltaTime) {
		uiStage.act(deltaTime);
	}


	@Override
	public void render() {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		fpsLabel.setText("fps: " + Gdx.graphics.getFramesPerSecond());

		uiStage.draw();
		
		// Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		uiStage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		skin=new Skin(Gdx.files.internal(SKIN_UI),new TextureAtlas(SKIN_ATLAS));
 	}

	@Override
	public void hide() {
		skin.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public InputProcessor getInputProcessor() {
		return new InputMultiplexer(uiStage,this);
	}

	@Override
	public boolean onBackPressed() {
		MenuScreen menuScreen = new MenuScreen(game);
		//ScreenTransition transition = ScreenTransitionFade.init(1);
		ScreenTransition transition =ScreenTransitionSlide.init(2,ScreenTransitionSlide.LEFT,true,Interpolation.swing);
		game.setScreen(menuScreen,transition);
		return true;
	}



}
