package gdx.game.entities.components;

import gdx.game.entities.MyComponent;
import gdx.game.items.Item;

import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class InventoryComponent extends MyComponent
{

    public Hashtable<String, Item> inventory;
    public ArrayList<Integer> stack;
    public int gold;

    private String path;

    @Override
    public void loadData(String data)
    {
        inventory = new Hashtable<String, Item>();
        stack = new ArrayList<Integer>();

        this.path = data;
        FileHandle file = Gdx.files.internal("NewGame/" + path
                + ".txt");

        String[] lines = file.readString().split("\\r?\\n");
        gold = Integer.parseInt(lines[0]);
        for (int i = 1; i < lines.length; i++)
        {
            addItem(lines[i]);
        }
    }

    public void addItem(String item)
    {

    }

    @Override
    public String getData()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
