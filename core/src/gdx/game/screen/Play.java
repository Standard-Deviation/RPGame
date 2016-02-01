package gdx.game.screen;

import gdx.game.terrain.TileMap;
import gdx.game.utils.BlurUtils;
import gdx.game.utils.Hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Play implements Screen
{

    public static TileMap tiles;
    public static OrthographicCamera camera;

    private static SpriteBatch batch;

    public static Hud hud;

    public static float elapsedTime = 0;

    private static boolean paused = false;
    private static TextureRegion pause = null;
    private static Texture pauseTexture;

    @Override
    public void render(float delta)
    {
        if (!paused)
        {
            elapsedTime += delta;

            tiles.render(camera, delta);
        }
        else
        {
            batch.begin();
            batch.draw(pause, 0, 0);
            batch.end();
        }
        hud.render();
        handleInput();
    }

    private void handleInput()
    {
        hud.menuToggles();
    }

    public static void togglePause()
    {
        if (!paused)
        {
            updatePauseImage();
        }
        paused = !paused;
    }

    @Override
    public void show()
    {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

        batch = new SpriteBatch();
        hud = new Hud(batch);

        tiles = new TileMap(
                "NewGame/Tutorial_Island/Tutorial_Island.txt", batch);

        updatePauseImage();
    }

    @Override
    public void resize(int width, int height)
    {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        tiles.resize(width, height);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide()
    {
        dispose();
    }

    @Override
    public void dispose()
    {
        tiles.dispose();
        batch.dispose();
        hud.dispose();
        pauseTexture.dispose();
    }

    private static void updatePauseImage()
    {
        Pixmap blurred = BlurUtils.blur(BlurUtils.getScreenshot(0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                false), 3, 1, true);
        pauseTexture = new Texture(blurred);
        pause = TextureRegion.split(pauseTexture,
                pauseTexture.getWidth(), pauseTexture.getHeight())[0][0];
        pause.flip(false, true);
    }

}
