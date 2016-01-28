package gdx.game.entities.systems;

import gdx.game.entities.EntityController;
import gdx.game.entities.components.AIComponent;
import gdx.game.entities.components.CollisionBoxComponent;
import gdx.game.entities.components.MovementComponent;
import gdx.game.entities.components.PropertiesComponent;
import gdx.game.screen.Play;
import gdx.game.terrain.TileMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class MovementSystem extends IteratingSystem
{

    public MovementSystem()
    {
        super(Family.all(PropertiesComponent.class,
                MovementComponent.class, CollisionBoxComponent.class,
                AIComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime)
    {
        PropertiesComponent position = EntityController.pm
                .get(entity);
        MovementComponent movement = EntityController.mm.get(entity);
        CollisionBoxComponent collisionBox = EntityController.cm
                .get(entity);
        AIComponent ai = EntityController.am.get(entity);

        int xs = 0;
        int ys = 0;
        if (movement.up)
        {
            ys += movement.moveSpeed * deltaTime;
        }
        else if (movement.down)
        {
            ys -= movement.moveSpeed * deltaTime;
        }
        if (movement.left)
        {
            xs -= movement.moveSpeed * deltaTime;
        }
        else if (movement.right)
        {
            xs += movement.moveSpeed * deltaTime;
        }

        if (!getHardCollision(xs, 0, position, movement, collisionBox))
        {
            position.x += xs;
        }
        else
        {
            notifyCollision(ai, movement);
        }
        if (!getHardCollision(0, ys, position, movement, collisionBox))
        {
            position.y += ys;
        }
        else
        {
            notifyCollision(ai, movement);
        }
    }

    private boolean getHardCollision(int xs, int ys,
            PropertiesComponent position, MovementComponent movement,
            CollisionBoxComponent collisionBox)
    {

        float xMove = position.x + xs + collisionBox.xOffset;
        float yMove = position.y + ys + collisionBox.yOffset;

        if (xMove < 0
                || xMove + collisionBox.width > Play.tiles.width
                        * TileMap.SIZE)
        {
            return true;
        }
        else if (yMove < 0
                || yMove + collisionBox.height > Play.tiles.height
                        * TileMap.SIZE)
        {
            return true;
        }
        else if (Play.tiles.getCollision(xMove, yMove))
        {
            return true;
        }
        else if (Play.tiles.getCollision(xMove + collisionBox.width
                - 1, yMove))
        {
            return true;
        }
        else if (Play.tiles.getCollision(xMove, yMove
                + collisionBox.height - 1))
        {
            return true;
        }
        else if (Play.tiles.getCollision(xMove + collisionBox.width
                - 1, yMove + collisionBox.height - 1))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void notifyCollision(AIComponent ai,
            MovementComponent movement)
    {
        switch (ai.AItype)
        {
            case "Path":
                movement.stop();
                break;

            case "HumanControl":
                break;

            case "Wander":
                movement.stop();
                ai.info = new int[(int) (Math.random() * 4) + 1];
                int dir = (int) (Math.random() * 4);
                for (int i = 0; i < ai.info.length; i++)
                {
                    ai.info[i] = dir;
                }
                ai.counter = 0;
                break;

        }
    }
}