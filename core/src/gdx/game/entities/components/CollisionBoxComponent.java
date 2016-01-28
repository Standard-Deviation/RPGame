package gdx.game.entities.components;

import gdx.game.entities.MyComponent;

public class CollisionBoxComponent extends MyComponent
{
    public int xOffset, yOffset;
    public int width, height;

    @Override
    public void loadData(String data)
    {
        String[] info = data.split(",");

        this.xOffset = Integer.parseInt(info[0]);
        this.yOffset = Integer.parseInt(info[1]);
        this.width = Integer.parseInt(info[2]);
        this.height = Integer.parseInt(info[3]);
    }

    @Override
    public String getData()
    {
        return null;
    }

}
