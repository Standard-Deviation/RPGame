package gdx.game.entities.components;

import gdx.game.entities.EntityTextureManager;
import gdx.game.entities.MyComponent;

public class RenderComponent extends MyComponent
{

    private String type;
    private String path;
    public EntityTextureManager textures;

    @Override
    public void loadData(String data)
    {
        String[] info = data.split(",");
        this.type = info[0];
        this.path = info[1];
        textures = new EntityTextureManager(info[0], info[1]);
    }

    @Override
    public String getData()
    {
        return "Render:" + type + "," + path;
    }

}
