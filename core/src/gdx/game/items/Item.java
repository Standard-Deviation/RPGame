package gdx.game.items;

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

    public void render(Batch batch, int x, int y)
    {
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }
}
