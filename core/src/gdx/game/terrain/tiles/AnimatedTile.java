package gdx.game.terrain.tiles;

import gdx.game.screen.Play;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedTile extends Tile
{

    private Animation animation;

    public AnimatedTile(TextureRegion[] textures, boolean solid)
    {
        super(solid);
        animation = new Animation(1f / textures.length, textures);
    }

    @Override
    public void render(SpriteBatch batch, float x, float y)
    {
        batch.draw(animation.getKeyFrame(Play.elapsedTime, true), x,
                y);
    }

}
