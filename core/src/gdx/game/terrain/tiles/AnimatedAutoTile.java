package gdx.game.terrain.tiles;

import gdx.game.screen.Play;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedAutoTile extends Tile
{

    private Animation[] textureA, textureB, textureC, textureD;
    private int size = 16;

    public AnimatedAutoTile(TextureRegion[] texture, boolean solid)
    {
        super(solid);

        TextureRegion[][][] tempFrames = new TextureRegion[texture.length][][];
        for (int i = 0; i < texture.length; i++)
        {
            tempFrames[i] = texture[i].split(size, size);
        }
        TextureRegion[][][] frames = new TextureRegion[tempFrames[0].length][tempFrames[0][0].length][tempFrames.length];

        for (int i = 0; i < tempFrames.length; i++)
        {
            for (int x = 0; x < tempFrames[i].length; x++)
            {
                for (int y = 0; y < tempFrames[i][x].length; y++)
                {
                    frames[x][y][i] = tempFrames[i][x][y];
                }
            }
        }

        float time = frames[0][0].length;

        Animation[] textureA =
            { new Animation(1f / time, frames[0][2]),
                new Animation(1f / time, frames[2][0]),
                new Animation(1f / time, frames[4][2]),
                new Animation(1f / time, frames[2][2]),
                new Animation(1f / time, frames[4][0]) };
        this.textureA = textureA;
        Animation[] textureB =
            { new Animation(1f / time, frames[0][3]),
                new Animation(1f / time, frames[2][3]),
                new Animation(1f / time, frames[4][1]),
                new Animation(1f / time, frames[2][1]),
                new Animation(1f / time, frames[4][3]) };
        this.textureB = textureB;
        Animation[] textureC =
            { new Animation(1f / time, frames[1][2]),
                new Animation(1f / time, frames[5][0]),
                new Animation(1f / time, frames[3][2]),
                new Animation(1f / time, frames[5][2]),
                new Animation(1f / time, frames[3][0]) };
        this.textureC = textureC;
        Animation[] textureD =
            { new Animation(1f / time, frames[1][3]),
                new Animation(1f / time, frames[5][3]),
                new Animation(1f / time, frames[3][1]),
                new Animation(1f / time, frames[5][1]),
                new Animation(1f / time, frames[3][3]) };
        this.textureD = textureD;
    }

    @Override
    public void render(SpriteBatch batch, float x, float y, int[] info)
    {
        batch.draw(
                textureA[info[0]].getKeyFrame(Play.elapsedTime, true),
                x, y + size);
        batch.draw(
                textureB[info[1]].getKeyFrame(Play.elapsedTime, true),
                x + size, y + size);
        batch.draw(
                textureC[info[2]].getKeyFrame(Play.elapsedTime, true),
                x, y);
        batch.draw(
                textureD[info[3]].getKeyFrame(Play.elapsedTime, true),
                x + size, y);
    }

}
