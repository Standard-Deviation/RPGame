package gdx.game.entities.systems;

import gdx.game.entities.EntityController;
import gdx.game.entities.components.InteractableComponent;
import gdx.game.entities.components.MovementComponent;
import gdx.game.entities.components.PropertiesComponent;
import gdx.game.screen.Play;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;

public class InteractSystem extends IteratingSystem
{
    private Rectangle playerRectangle;
    private Entity target = null;

    public InteractSystem()
    {
        super(Family.all(PropertiesComponent.class,
                InteractableComponent.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        PropertiesComponent playerPosition = Play.tiles.playerPosition;
        playerRectangle = new Rectangle(playerPosition.x + 30,
                playerPosition.y + 20, 10, 10);
        target = null;
        super.update(deltaTime);
        MovementComponent movement = target != null
                && EntityController.mm.has(target) ? EntityController.mm
                        .get(target) : null;
        if (movement != null)
        {
            movement.stop();
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        PropertiesComponent position = EntityController.pm.get(entity);
        InteractableComponent interact = EntityController.im
                .get(entity);

        interact.interactArea.x = position.x + interact.xOffset;
        interact.interactArea.y = position.y + interact.yOffset;
        /*
         * TileMap.batch.begin(); TileMap.batch.draw(EntityController.player
         * .getComponent(RenderComponent.class).defaultIM,
         * interact.interactArea.x, interact.interactArea.y,
         * interact.interactArea.width, interact.interactArea.height);
         * TileMap.batch.draw(EntityController.player
         * .getComponent(RenderComponent.class).defaultIM, playerRectangle.x,
         * playerRectangle.y, playerRectangle.width, playerRectangle.height);
         * TileMap.batch.end();
         */
        if (interact.interactArea.overlaps(playerRectangle))
        {
            target = entity;
        }
    }

}
