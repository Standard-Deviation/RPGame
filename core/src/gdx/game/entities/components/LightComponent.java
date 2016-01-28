package gdx.game.entities.components;

import gdx.game.entities.MyComponent;

public class LightComponent extends MyComponent
{

    public float radius;
    // used to make the light flicker
    public float zAngle;
    public static final float PI2 = 3.1415926535897932384626433832795f * 2.0f;

    @Override
    public void loadData(String data)
    {
        radius = Float.parseFloat(data);
    }

    @Override
    public String getData()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public float updateAngle(float deltaTime)
    {
        zAngle += deltaTime * 10.0f;
        while (zAngle > PI2)
            zAngle -= PI2;
        return zAngle;
    }

}
