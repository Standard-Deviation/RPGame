package gdx.game.desktop;

import gdx.game.MyGdxGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL30 = true;
        config.title = "RPGame";
        config.width = 720;
        config.height = 480;
        new LwjglApplication(new MyGdxGame(), config);
    }
}
