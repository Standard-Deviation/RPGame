package gdx.game.terrain.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AutoTile extends Tile
{

    private TextureRegion[] textureA, textureB, textureC, textureD;
    private int size = 16;

    public AutoTile(TextureRegion texture, boolean solid)
    {
        super(solid);
        TextureRegion[][] textures = texture.split(size, size);
        TextureRegion[] textureA =
        { textures[0][2], textures[2][0], textures[4][2],
                textures[2][2], textures[4][0] };
        this.textureA = textureA;
        TextureRegion[] textureB =
        { textures[0][3], textures[2][3], textures[4][1],
                textures[2][1], textures[4][3] };
        this.textureB = textureB;
        TextureRegion[] textureC =
        { textures[1][2], textures[5][0], textures[3][2],
                textures[5][2], textures[3][0] };
        this.textureC = textureC;
        TextureRegion[] textureD =
        { textures[1][3], textures[5][3], textures[3][1],
                textures[5][1], textures[3][3] };
        this.textureD = textureD;
    }

    @Override
    public void render(SpriteBatch batch, float x, float y, int[] info)
    {
        batch.draw(textureA[info[0]], x, y + size);
        batch.draw(textureB[info[1]], x + size, y + size);
        batch.draw(textureC[info[2]], x, y);
        batch.draw(textureD[info[3]], x + size, y);
    }

}
