package gdx.game.entities.components;

import gdx.game.entities.MyComponent;

public class MovementComponent extends MyComponent
{

    public boolean up, down, left, right;
    public float moveSpeed = 2f;

    @Override
    public void loadData(String data)
    {
        up = false;
        down = false;
        left = false;
        right = false;
        this.moveSpeed = Float.parseFloat(data);
    }

    @Override
    public String getData()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void stop()
    {
        up = false;
        down = false;
        left = false;
        right = false;
    }

}
