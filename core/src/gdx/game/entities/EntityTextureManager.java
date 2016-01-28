package gdx.game.entities;

import gdx.game.screen.Play;
import gdx.game.utils.ReasourceManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EntityTextureManager
{

    private String type;
    private String path;
    private String walking, walkingDiagonal, pose;
    public TextureRegion defaultIM;
    public TextureRegion[] laying, facing, facingDiagonal;
    public Animation upAni, downAni, rightAni, leftAni, upleftAni,
    uprightAni, downleftAni, downrightAni;

    public EntityTextureManager(String type, String path)
    {
        this.type = type;
        this.path = path;

        int texturesLoaded = 0;
        try
        {
            walking = ReasourceManager
                    .loadTexture("Textures/HF1_Remaster/" + type
                            + "/" + path + "/" + path
                            + "_walking.png");
            if (ReasourceManager.getTexture(walking).getWidth() / 3 >= 128)
            {
                loadWalking(8);
            }
            else
            {
                loadWalking(3);
            }
            facing = colTextures(walking, 0, 8, 4);
            texturesLoaded++;
        }
        catch (Exception e)
        {
        }

        try
        {
            walkingDiagonal = ReasourceManager
                    .loadTexture("Textures/HF1_Remaster/" + type
                            + "/" + path + "/" + path
                            + "_walkingDiagonal.png");
            if (ReasourceManager.getTexture(walkingDiagonal)
                    .getWidth() / 3 >= 128)
            {
                loadDiagonalWalking(8);
            }
            else
            {
                loadDiagonalWalking(3);
            }
            facingDiagonal = colTextures(walkingDiagonal, 0, 8, 4);
            texturesLoaded++;
        }
        catch (Exception e)
        {
        }

        try
        {
            pose = ReasourceManager
                    .loadTexture("Textures/HF1_Remaster/" + type
                            + "/" + path + "/" + path + "_pose.png");
            laying = colTextures(pose, 1, 3, 4);
            texturesLoaded++;
        }
        catch (Exception e)
        {
        }

        if (facing != null)
        {
            defaultIM = facing[0];
        }

        System.out.println(type + ", " + path
                + ", Number of Textures loaded: " + texturesLoaded);
    }

    public void render(Batch batch, boolean up, boolean down,
            boolean left, boolean right, float x, float y)
    {
        if (down != true && up != true && left != true
                && right != true)
        {
            batch.draw(defaultIM, x, y);
        }
        else if (down == true && left == true
                && facingDiagonal != null)
        {
            batch.draw(
                    downleftAni.getKeyFrame(Play.elapsedTime, true),
                    x, y);
            defaultIM = facingDiagonal[0];
        }
        else if (down == true && right == true
                && facingDiagonal != null)
        {
            batch.draw(
                    downrightAni.getKeyFrame(Play.elapsedTime, true),
                    x, y);
            defaultIM = facingDiagonal[2];
        }
        else if (up == true && left == true && facingDiagonal != null)
        {
            batch.draw(upleftAni.getKeyFrame(Play.elapsedTime, true),
                    x, y);
            defaultIM = facingDiagonal[1];
        }
        else if (up == true && right == true
                && facingDiagonal != null)
        {
            batch.draw(
                    uprightAni.getKeyFrame(Play.elapsedTime, true),
                    x, y);
            defaultIM = facingDiagonal[3];
        }
        else if (left == true)
        {
            batch.draw(leftAni.getKeyFrame(Play.elapsedTime, true),
                    x, y);
            defaultIM = facing[1];
        }
        else if (right == true)
        {
            batch.draw(rightAni.getKeyFrame(Play.elapsedTime, true),
                    x, y);
            defaultIM = facing[2];
        }
        else if (down == true)
        {
            batch.draw(downAni.getKeyFrame(Play.elapsedTime, true),
                    x, y);
            defaultIM = facing[0];
        }
        else if (up == true)
        {
            batch.draw(upAni.getKeyFrame(Play.elapsedTime, true), x,
                    y);
            defaultIM = facing[3];
        }
    }

    private Animation rowAni(String path, int row, int numWide,
            int numHigh)
    {
        Texture texture = ReasourceManager.getTexture(path);
        TextureRegion[][] region = TextureRegion.split(texture,
                texture.getWidth() / numWide, texture.getHeight()
                / numHigh);

        TextureRegion[] animationFrames = new TextureRegion[region[row].length];
        int index = 0;
        for (int j = 0; j < region[row].length; j++)
        {
            animationFrames[index++] = region[row][j];
        }
        texture = null;
        return new Animation(1f / 10f, animationFrames);
    }

    private TextureRegion[] colTextures(String path, int col,
            int numWide, int numHigh)
    {
        Texture texture = ReasourceManager.getTexture(path);
        TextureRegion[][] region = TextureRegion.split(texture,
                texture.getWidth() / numWide, texture.getHeight()
                / numHigh);

        TextureRegion[] frames = new TextureRegion[region.length];
        int index = 0;
        for (int j = 0; j < region.length; j++)
        {
            frames[index++] = region[j][col];
        }
        texture = null;
        return frames;
    }

    private void loadWalking(int length)
    {
        upAni = rowAni(walking, 3, length, 4);
        downAni = rowAni(walking, 0, length, 4);
        leftAni = rowAni(walking, 1, length, 4);
        rightAni = rowAni(walking, 2, length, 4);
    }

    private void loadDiagonalWalking(int length)
    {
        upleftAni = rowAni(walkingDiagonal, 1, length, 4);
        downrightAni = rowAni(walkingDiagonal, 2, length, 4);
        downleftAni = rowAni(walkingDiagonal, 0, length, 4);
        uprightAni = rowAni(walkingDiagonal, 3, length, 4);
    }

    public TextureRegion loadPortrait()
    {
        Texture texture = new Texture("Textures/HF1_Remaster/" + type
                + "/" + path + "/" + path + "_bust.png");
        TextureRegion region = TextureRegion.split(texture,
                texture.getWidth(), texture.getHeight())[0][0];
        return region;
    }

    public TextureRegion loadFace()
    {
        Texture texture = new Texture("Textures/HF1_Remaster/" + type
                + "/" + path + "/" + path + "_face.png");
        TextureRegion region = TextureRegion.split(texture,
                texture.getWidth(), texture.getHeight())[0][0];
        return region;
    }

    public void dispose()
    {
        laying = null;
        defaultIM = null;
        laying = null;
        facing = null;
        facingDiagonal = null;
        upAni = null;
        downAni = null;
        rightAni = null;
        leftAni = null;
        upleftAni = null;
        uprightAni = null;
        downleftAni = null;
        downrightAni = null;
    }
}
