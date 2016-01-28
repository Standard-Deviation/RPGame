package gdx.game.entities;

import com.badlogic.ashley.core.Component;

public abstract class MyComponent implements Component
{
    public MyComponent()
    {

    }

    public abstract void loadData(String data);

    public abstract String getData();
}
