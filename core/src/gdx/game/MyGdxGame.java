package gdx.game;

import gdx.game.screen.Play;
import gdx.game.utils.ReasourceManager;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game
{

    @Override
    public void create()
    {
        setScreen(new Play());
    }

    @Override
    public void dispose()
    {
        super.dispose();
        ReasourceManager.dispose();
    }

    @Override
    public void render()
    {
        super.render();
    }

    @Override
    public void resize(int width, int height)
    {
        super.resize(width, height);
    }

    @Override
    public void pause()
    {
        super.pause();
    }

    @Override
    public void resume()
    {
        super.resume();
    }

}
