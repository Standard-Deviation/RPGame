package gdx.game.entities.components;

import gdx.game.entities.MyComponent;

import com.badlogic.gdx.math.Rectangle;

public class InteractableComponent extends MyComponent
{
    public int xOffset, yOffset;
    public Rectangle interactArea;

    @Override
    public void loadData(String data)
    {
        String[] info = data.split(",");

        this.xOffset = Integer.parseInt(info[0]);
        this.yOffset = Integer.parseInt(info[1]);
        interactArea = new Rectangle(xOffset, yOffset,
                Integer.parseInt(info[2]), Integer.parseInt(info[3]));
    }

    @Override
    public String getData()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
