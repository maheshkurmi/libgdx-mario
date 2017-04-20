package com.mygdx.game.mario;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.mario.MarioGame;

/**
 * Launcher class for desktop
 * @author maheshkurmi
 *
 */
public class MarioGameLauncher
{
    public static void main (String[] args)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        
        // change configuration settings
        config.width = 720;
        config.height = 480;
        config.title = "LibGdx Super Mario";
        
        MarioGame myProgram = new MarioGame();
        LwjglApplication launcher = new LwjglApplication( myProgram, config );
    }
}