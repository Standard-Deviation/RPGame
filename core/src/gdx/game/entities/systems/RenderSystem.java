package gdx.game.entities.systems;

import gdx.game.entities.EntityController;
import gdx.game.entities.components.MovementComponent;
import gdx.game.entities.components.PropertiesComponent;
import gdx.game.entities.components.RenderComponent;

import java.util.Comparator;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;

public class RenderSystem extends SortedIteratingSystem
{

    private Batch batch;

    public RenderSystem(Batch batch)
    {
        super(Family.all(RenderComponent.class,
                PropertiesComponent.class).get(), new YComparator());
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime)
    {
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        this.forceSort();

        RenderComponent render = EntityController.rm.get(entity);
        PropertiesComponent position = EntityController.pm
                .get(entity);
        MovementComponent movement = EntityController.mm.get(entity);

        if (movement == null)
        {
            batch.draw(render.textures.defaultIM, position.x,
                    position.y);
            return;
        }

        render.textures
        .render(batch, movement.up, movement.down,
                movement.left, movement.right, position.x,
                position.y);
    }

    private static class YComparator implements Comparator<Entity>
    {
        @Override
        public int compare(Entity e1, Entity e2)
        {
            return (int) Math.signum(EntityController.pm.get(e2).y
                    - EntityController.pm.get(e1).y);
        }
    }

}
