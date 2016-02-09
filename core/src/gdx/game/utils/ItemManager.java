package gdx.game.utils;

import gdx.game.items.Item;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemManager
{

    private static final String imagePath = "Textures/HF1_Remaster/IconSet.png";
    private static final String textPath = "NewGame/items.txt";
    private static final int size = 24;
    private static TextureRegion[][] sprites;

    public static HashMap<String, Item> items = new HashMap<String, Item>();

    static
    {
        loadItems();
    }

    public static void loadItems()
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

    private static void loadItem(String data)
    {
        String[] parts = data.split(",");
        String name = parts[0];
        int price = Integer.parseInt(parts[1]);
        int weight = Integer.parseInt(parts[2]);
        int x = Integer.parseInt(parts[3]);
        int y = Integer.parseInt(parts[4]);
        Sprite sprite = new Sprite(sprites[y][x]);
        items.put(name, new Item(name, price, weight, sprite));
        System.out.println(name);
    }

}
