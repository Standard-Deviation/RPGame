package gdx.game.terrain;

import gdx.game.terrain.tiles.AnimatedAutoTile;
import gdx.game.terrain.tiles.AnimatedTile;
import gdx.game.terrain.tiles.AutoTile;
import gdx.game.terrain.tiles.PlainTile;
import gdx.game.terrain.tiles.Tile;
import gdx.game.utils.ReasourceManager;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tileset
{

    private List<Tile> tiles = new ArrayList<Tile>();
    private List<String> tileTextures = new ArrayList<String>();
    private List<TextureRegion[][]> splitTiles = new ArrayList<TextureRegion[][]>();

    public void loadTexture(String data)
    {
        String info = data.substring(data.indexOf(":") + 1);
        String[] parts = info.split(",");
        String path = parts[0];
        int width = Integer.parseInt(parts[1]);
        int height = Integer.parseInt(parts[2]);

        tileTextures.add(ReasourceManager
                .loadTexture("Textures/HF1_Remaster/Tiles/" + path
                        + ".png"));

        String currentPath = tileTextures
                .get(tileTextures.size() - 1);

        splitTiles.add(TextureRegion.split(
                ReasourceManager.getTexture(currentPath), width,
                height));

        System.out.println(info);
    }

    public void add(String tile, int textureIndex)
    {
        String[] type = tile.split(":");
        String[] parts = type[1].split(";");
        boolean collision = Integer.parseInt(parts[0]) == 1;
        if (type[0].equals("Plain"))
        {
            String[] coords = parts[1].split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            tiles.add(new PlainTile(
                    splitTiles.get(textureIndex)[y][x], collision));
        }
        else if (type[0].equals("Animated"))
        {
            TextureRegion[] frames = new TextureRegion[parts.length - 1];
            for (int j = 1; j < parts.length; j++)
            {
                String[] coords = parts[j].split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                frames[j - 1] = splitTiles.get(textureIndex)[y][x];
            }
            tiles.add(new AnimatedTile(frames, collision));
        }
        else if (type[0].equals("Auto"))
        {
            String[] coords = parts[1].split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            tiles.add(new AutoTile(
                    splitTiles.get(textureIndex)[y][x], collision));
        }
        else if (type[0].equals("AnimatedAuto"))
        {
            TextureRegion[] frames = new TextureRegion[parts.length - 1];
            for (int j = 1; j < parts.length; j++)
            {
                String[] coords = parts[j].split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                frames[j - 1] = splitTiles.get(textureIndex)[y][x];
            }
            tiles.add(new AnimatedAutoTile(frames, collision));
        }
    }

    public void dispose()
    {
        tileTextures = null;
        splitTiles = null;
    }

    public Tile getTile(int tileIndex)
    {
        return tiles.get(tileIndex);
    }

}
