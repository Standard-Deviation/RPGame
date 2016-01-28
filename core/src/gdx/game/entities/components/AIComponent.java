package gdx.game.entities.components;

import gdx.game.entities.MyComponent;

public class AIComponent extends MyComponent
{

    public String AItype;
    public int[] info;
    public int counter;
    public float elapsedTime;

    @Override
    public void loadData(String data)
    {
        String[] splitData = data.split(";");
        this.AItype = splitData[0];
        String[] tempInfo = splitData[1].split(",");
        info = new int[tempInfo.length];
        for (int i = 0; i < tempInfo.length; i++)
        {
            info[i] = Integer.parseInt(tempInfo[i]);
        }
        counter = 0;
        elapsedTime = 0;
    }

    @Override
    public String getData()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
