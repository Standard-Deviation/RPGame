package gdx.game.entities.components;

import gdx.game.entities.MyComponent;
import gdx.game.items.Item;
import gdx.game.utils.ItemManager;
import gdx.game.utils.ScrollList;

public class InventoryComponent extends MyComponent
{

    public ScrollList<Item> inventory;
    public int gold;

    @Override
    public void loadData(String data)
    {
        inventory = new ScrollList<Item>(10);

        String[] info = data.split(",");
        gold = Integer.parseInt(info[0]);
        for (int i = 1; i < info.length; i++)
        {
            addItem(info[i]);
        }
    }

    public void addItem(String item)
    {
        Item i = ItemManager.items.get(item);
        inventory.add(i);
    }

    @Override
    public String getData()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
