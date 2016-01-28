package gdx.game.entities.components;

import gdx.game.entities.MyComponent;
import gdx.game.terrain.TileMap;

public class PropertiesComponent extends MyComponent
{

    public float x = 0.0f;
    public float y = 0.0f;
    public String name = "item";
    public boolean global = false;
    public String worldLocation = null;

    @Override
    public void loadData(String data)
    {
        String[] info = data.split(",");
        if (info.length >= 2)
        {
            this.x = Float.parseFloat(info[0]) * TileMap.SIZE;
            this.y = Float.parseFloat(info[1]) * TileMap.SIZE;
        }
        if (info.length >= 3)
        {
            this.name = info[2];
        }
        if (info.length >= 4)
        {
            global = Boolean.parseBoolean(info[3]);
            worldLocation = info[4];
        }
    }

    @Override
    public String getData()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
