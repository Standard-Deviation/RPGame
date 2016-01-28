package gdx.game.terrain.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Tile
{

    public final boolean solid;

    public Tile(boolean solid)
    {
        this.solid = solid;
    }

    public void render(SpriteBatch batch, float x, float y)
    {
        render(batch, x, y, null);
    }

    public void render(SpriteBatch batch, float x, float y, int[] info)
    {
        render(batch, x, y);
    }
}
