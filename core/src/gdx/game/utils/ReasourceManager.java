package gdx.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class ReasourceManager
{

    public static AssetManager manager = new AssetManager();

    public static void load()
    {
        while (!manager.update())
        {
            System.out.println("Loaded: " + manager.getProgress()
                    * 100 + "%");
        }
    }

    public static String loadTexture(String path)
    {
        manager.load(path, Texture.class);
        // manager.finishLoading();
        return path;
    }

    public static Texture getTexture(String path)
    {
        if (!manager.isLoaded(path))
        {
            manager.finishLoadingAsset(path);
        }
        return manager.get(path, Texture.class);
    }

    public static Boolean isLoaded()
    {
        if (manager.getProgress() >= 1)
            return true;
        return false;
    }

    public static void dispose()
    {
        manager.dispose();
        manager = null;
    }

}
