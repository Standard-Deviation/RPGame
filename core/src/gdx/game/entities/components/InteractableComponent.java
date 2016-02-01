package gdx.game.entities.components;

import gdx.game.entities.EntityController;
import gdx.game.entities.MyComponent;
import gdx.game.screen.Play;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;

public class InteractableComponent extends MyComponent
{
    public int xOffset, yOffset;
    public Rectangle interactArea;
    private int menuToggle;

    @Override
    public void loadData(String data)
    {
        String[] info = data.split(",");

        this.xOffset = Integer.parseInt(info[0]);
        this.yOffset = Integer.parseInt(info[1]);
        interactArea = new Rectangle(xOffset, yOffset,
                Integer.parseInt(info[2]), Integer.parseInt(info[3]));
        menuToggle = Integer.parseInt(info[4]);
    }

    @Override
    public String getData()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void interact(Entity e)
    {
        Play.hud.menus.get(menuToggle).update(
                EntityController.player, e);
    }

}
