package gdx.game.terrain;

import gdx.game.entities.EntityController;
import gdx.game.entities.components.PropertiesComponent;
import gdx.game.entities.systems.LightSystem;
import gdx.game.screen.Play;
import gdx.game.terrain.tiles.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TileMap
{
    private Tile[][] tileMap;
    private int[][][] terrainInfo;
    private EntityController controller;
    public int width, height;
    public static final int SIZE = 32;

    private Tileset tiles;

    private SpriteBatch batch;
    private Viewport viewport;
    public PropertiesComponent playerPosition;

    public TileMap(String path, SpriteBatch batch)
    {

        FileHandle file = Gdx.files.internal(path);
        String[] lines = file.readString().split("\\r?\\n");

        this.batch = batch;
        controller = new EntityController(batch);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        viewport = new FitViewport(w, h, Play.camera);

        int lineNum = 0;
        while (lineNum < lines.length)
        {
            if (lines[lineNum].equals("Tileset"))
            {
                lineNum = loadTileSet(lines, lineNum + 1);
            }
            else if (lines[lineNum].equals("Map"))
            {
                lineNum = loadMap(lines, lineNum + 1);
            }
            else if (lines[lineNum].equals("Entity"))
            {
                lineNum = controller.loadEntity(lines, lineNum + 1);
            }
            else if (lines[lineNum].equals("Globals"))
            {
                lineNum = loadGlobals(lines, lineNum + 1);
            }
            lineNum++;
        }

        playerPosition = EntityController.pm
                .get(EntityController.player);

    }

    private int loadGlobals(String[] lines, int currentLine)
    {
        int lineNum = currentLine;
        while (!lines[lineNum].equals("End"))
        {
            FileHandle file = Gdx.files.internal("NewGame/Globals/"
                    + lines[lineNum] + ".txt");
            String[] entityLines = file.readString().split("\\r?\\n");
            controller.loadEntity(entityLines, 1);
            lineNum++;
        }
        return lineNum;
    }

    public void resize(int width, int height)
    {
        viewport.update(width, height);
        controller.resize(width, height);
    }

    private int loadTileSet(String[] lines, int currentLine)
    {
        int lineNum = currentLine;
        tiles = new Tileset();
        int textureIndex = -1;
        while (!lines[lineNum].equals("End"))
        {
            if (lines[lineNum].startsWith("Texture"))
            {
                tiles.loadTexture(lines[lineNum]);
                textureIndex++;
            }
            else
            {
                tiles.add(lines[lineNum], textureIndex);
            }
            lineNum++;
        }
        return lineNum;
    }

    private int loadMap(String[] lines, int currentLine)
    {
        String[] lineOneParts = lines[currentLine].split(",");

        width = Integer.parseInt(lineOneParts[0]);
        height = Integer.parseInt(lineOneParts[1]);

        tileMap = new Tile[width][height];
        terrainInfo = new int[width][height][4];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                int index = Integer
                        .parseInt(lines[currentLine + 1].substring(x
                                * height + y, x * height + y + 1));
                tileMap[x][y] = tiles.getTile(index);
            }
        }
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (x > 0 && y > 0 && x < width - 1 && y < height - 1)
                {
                    setType(x, y);
                }
            }
        }
        return currentLine + 2;
    }

    private void setType(int x, int y)
    {
        boolean[] surroundingTiles =
            {
                tileMap[x - 1][y + 1] == tileMap[x][y],
                tileMap[x][y + 1] == tileMap[x][y],//
                tileMap[x + 1][y + 1] == tileMap[x][y],//
                tileMap[x - 1][y] == tileMap[x][y],
                tileMap[x][y] == tileMap[x][y],
                tileMap[x + 1][y] == tileMap[x][y],//
                        tileMap[x - 1][y - 1] == tileMap[x][y],
                tileMap[x][y - 1] == tileMap[x][y],
                tileMap[x + 1][y - 1] == tileMap[x][y] };//

        int bitA = (!surroundingTiles[3] ? 0 : 1)
                + (!surroundingTiles[0] ? 0 : 2)
                + (!surroundingTiles[1] ? 0 : 4);
        int bitB = (!surroundingTiles[5] ? 0 : 1)
                + (!surroundingTiles[2] ? 0 : 2)
                + (!surroundingTiles[1] ? 0 : 4);
        int bitC = (!surroundingTiles[3] ? 0 : 1)
                + (!surroundingTiles[6] ? 0 : 2)
                + (!surroundingTiles[7] ? 0 : 4);
        int bitD = (!surroundingTiles[5] ? 0 : 1)
                + (!surroundingTiles[8] ? 0 : 2)
                + (!surroundingTiles[7] ? 0 : 4);

        int[] infoNew =
            { evaluateBit(bitA), evaluateBit(bitB), evaluateBit(bitC),
                evaluateBit(bitD) };
        terrainInfo[x][y] = infoNew;
    }

    private int evaluateBit(int bit)
    {
        if (bit == 0 || bit == 2)
        {
            return 1;
        }
        else if (bit == 1 || bit == 3)
        {
            return 3;
        }
        else if (bit == 4 || bit == 6)
        {
            return 4;
        }
        else if (bit == 5)
        {
            return 0;
        }
        return 2;
    }

    public void render(OrthographicCamera camera, float delta)
    {
        batch.setProjectionMatrix(camera.combined);

        int xo = (int) playerPosition.x;
        int yo = (int) playerPosition.y;

        int x0 = Math.max(xo / SIZE - 14, 0);
        int y0 = Math.max(yo / SIZE - 7, 0);
        int x1 = Math.min(xo / SIZE + 17, width);
        int y1 = Math.min(yo / SIZE + 10, height);

        // begins batch.
        LightSystem.bindFbo();
        for (int x = x0; x < x1; x++)
        {
            for (int y = y0; y < y1; y++)
            {
                tileMap[x][y].render(batch, x * SIZE, y * SIZE,
                        terrainInfo[x][y]);
            }
        }
        batch.end();
        controller.update(delta);

        camera.position.set(playerPosition.x + 32,
                playerPosition.y + 32, 0);
        camera.update();

    }

    public void dispose()
    {
        tiles.dispose();
    }

    public boolean getCollision(float xMove, float yMove)
    {
        return tileMap[(int) (xMove / SIZE)][(int) (yMove / SIZE)].solid;
    }
}
