package gdx.game.items;

import gdx.game.utils.ReasourceManager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item
{

    private String name;
    private int price;
    private int weight;
    private Sprite sprite;

    public Item(String name, int price, int weight, Sprite sprite)
    {
        super();
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.sprite = sprite;
    }

    public String getName()
    {
        return name;
    }

    public int getWeight()
    {
        return weight;
    }

    public int getPrice()
    {
        return price;
    }

    public void render(Batch batch, float f, float g)
    {
        sprite.setPosition(f, g);
        sprite.draw(batch);
    }

    public void draw(Batch batch, float x, float y)
    {
        render(batch, x, y);
        ReasourceManager.drawString(batch, name, x + 30, y, 1, 1);
        ReasourceManager.drawString(batch, price + "", x + 130, y, 1,
                1);
        ReasourceManager.drawString(batch, weight + "", x + 180, y,
                1, 1);
    }
}
