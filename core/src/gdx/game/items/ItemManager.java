package gdx.game.items;

import gdx.game.utils.ReasourceManager;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemManager
{

    private static final String imagePath = "Textures/HF1_Remaster/IconSet.png";
    private static final String textPath = "NewGame/items.txt";
    private static final int size = 24;
    private TextureRegion[][] sprites;
    public ArrayList<Item> items = new ArrayList<Item>();

    public ItemManager()
    {
        loadItems();
    }

    private void loadItems()
    {
        ReasourceManager.loadTexture(imagePath);
        sprites = TextureRegion.split(
                ReasourceManager.getTexture(imagePath), size, size);

        FileHandle file = Gdx.files.internal(textPath);
        String[] lines = file.readString().split("\\r?\\n");
        for (String line : lines)
        {
            loadItem(line);
        }
    }

    private void loadItem(String data)
    {
        String[] parts = data.split(",");
        String name = parts[0];
        int price = Integer.parseInt(parts[1]);
        int weight = Integer.parseInt(parts[2]);
        int x = Integer.parseInt(parts[3]);
        int y = Integer.parseInt(parts[4]);
        Sprite sprite = new Sprite(sprites[y][x]);
        items.add(new Item(name, price, weight, sprite));
        System.out.println(name);
    }

}
