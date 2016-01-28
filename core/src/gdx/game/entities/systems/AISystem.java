package gdx.game.entities.systems;

import gdx.game.entities.EntityController;
import gdx.game.entities.components.AIComponent;
import gdx.game.entities.components.MovementComponent;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class AISystem extends IteratingSystem
{

    public AISystem()
    {
        super(Family.all(AIComponent.class, MovementComponent.class)
                .get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        AIComponent ai = EntityController.am.get(entity);
        MovementComponent movement = EntityController.mm.get(entity);

        ai.elapsedTime += deltaTime;
        switch (ai.AItype)
        {
            case "Path":
                movement.stop();

                if (ai.elapsedTime >= (2))
                {
                    ai.elapsedTime = 0;
                    ai.counter++;
                }
                if (ai.counter == ai.info.length)
                {
                    ai.counter = 0;
                }
                int aiCounter = ai.info[ai.counter];
                if (aiCounter == 2)
                {
                    movement.down = true;
                }
                if (aiCounter == 0)
                {
                    movement.up = true;
                }
                if (aiCounter == 3)
                {
                    movement.left = true;
                }
                if (aiCounter == 1)
                {
                    movement.right = true;
                }
                break;

            case "HumanControl":
                movement.down = Gdx.input
                        .isKeyPressed(Input.Keys.DOWN);
                movement.up = Gdx.input.isKeyPressed(Input.Keys.UP);
                movement.left = Gdx.input
                        .isKeyPressed(Input.Keys.LEFT);
                movement.right = Gdx.input
                        .isKeyPressed(Input.Keys.RIGHT);
                break;

            case "Wander":
                movement.stop();
                if (ai.elapsedTime >= (2))
                {
                    ai.elapsedTime = 0;
                    ai.counter++;
                }
                if (ai.counter == ai.info.length)
                {
                    ai.info = new int[(int) (Math.random() * 4) + 1];
                    int dir = (int) (Math.random() * 4);
                    for (int i = 0; i < ai.info.length; i++)
                    {
                        ai.info[i] = dir;
                    }
                    ai.counter = 0;
                }
                int aiCounter2 = ai.info[ai.counter];
                if (aiCounter2 == 2)
                {
                    movement.down = true;
                }
                if (aiCounter2 == 0)
                {
                    movement.up = true;
                }
                if (aiCounter2 == 3)
                {
                    movement.left = true;
                }
                if (aiCounter2 == 1)
                {
                    movement.right = true;
                }
                break;

        }
    }
}
