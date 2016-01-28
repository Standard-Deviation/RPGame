package gdx.game.terrain.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlainTile extends Tile
{

    private TextureRegion texture;

    public PlainTile(TextureRegion texture, boolean solid)
    {
        super(solid);
        this.texture = texture;
    }

    @Override
    public void render(SpriteBatch batch, float x, float y)
    {
        batch.draw(texture, x, y);
    }

}
