package gdx.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;

public class ReasourceManager
{

    public static AssetManager manager = new AssetManager();

    public static BitmapFont font = new BitmapFont();

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

    public static void drawString(Batch batch, String str, float x,
            float y, float alpha, float scale)
    {
        font.getData().setScale(scale);
        BitmapFontCache cache = font.getCache();
        cache.clear();
        cache.addText(str, x, font.getLineHeight() + y);

        // This is the useful bit!
        cache.setAlphas(alpha);

        cache.draw(batch);
    }

    public static void dispose()
    {
        manager.dispose();
        font.dispose();
        font = null;
        manager = null;
    }

}
