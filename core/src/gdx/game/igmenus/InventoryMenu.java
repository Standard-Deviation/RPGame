package gdx.game.igmenus;

import gdx.game.entities.EntityController;
import gdx.game.entities.components.InventoryComponent;
import gdx.game.utils.ReasourceManager;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;

public class InventoryMenu extends Menu
{

    private InventoryComponent inventory;
    private float selectedY;

    public InventoryMenu()
    {
        super(100, 100, 500, 300, Input.Keys.I);
        inventory = EntityController.invm
                .get(EntityController.player);
        selectedY = 0;
    }

    @Override
    public void update(Entity e1, Entity e2)
    {
        inventory.inventory.update();
    }

    @Override
    protected void draw(Batch batch)
    {
        selectedY = inventory.inventory.render(batch, getX() + 40,
                getY() + 250);
        batch.draw(selector, getX(), selectedY);

        ReasourceManager.drawString(batch, "Name: ", getX() + 70,
                getY() + 270, 1, 1);
        ReasourceManager.drawString(batch, "Value: ", getX() + 170,
                getY() + 270, 1, 1);
        ReasourceManager.drawString(batch, "Weight:", getX() + 220,
                getY() + 270, 1, 1);
    }

    @Override
    protected void handleInputs()
    {
        inventory.inventory.handleInput();
    }

}
