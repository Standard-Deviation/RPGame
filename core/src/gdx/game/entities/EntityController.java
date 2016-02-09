package gdx.game.entities;

import gdx.game.entities.components.AIComponent;
import gdx.game.entities.components.CollisionBoxComponent;
import gdx.game.entities.components.InteractableComponent;
import gdx.game.entities.components.InventoryComponent;
import gdx.game.entities.components.LightComponent;
import gdx.game.entities.components.MovementComponent;
import gdx.game.entities.components.PropertiesComponent;
import gdx.game.entities.components.RenderComponent;
import gdx.game.entities.components.TalkingComponent;
import gdx.game.entities.systems.AISystem;
import gdx.game.entities.systems.InteractSystem;
import gdx.game.entities.systems.LightSystem;
import gdx.game.entities.systems.MovementSystem;
import gdx.game.entities.systems.RenderSystem;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;

public class EntityController
{

    private Engine engine;
    public static Entity player;

    public static final ComponentMapper<PropertiesComponent> pm = ComponentMapper
            .getFor(PropertiesComponent.class);
    public static final ComponentMapper<MovementComponent> mm = ComponentMapper
            .getFor(MovementComponent.class);
    public static final ComponentMapper<RenderComponent> rm = ComponentMapper
            .getFor(RenderComponent.class);
    public static final ComponentMapper<AIComponent> am = ComponentMapper
            .getFor(AIComponent.class);
    public static final ComponentMapper<CollisionBoxComponent> cm = ComponentMapper
            .getFor(CollisionBoxComponent.class);
    public static final ComponentMapper<LightComponent> lm = ComponentMapper
            .getFor(LightComponent.class);
    public static final ComponentMapper<InteractableComponent> im = ComponentMapper
            .getFor(InteractableComponent.class);
    public static final ComponentMapper<TalkingComponent> tm = ComponentMapper
            .getFor(TalkingComponent.class);
    public static final ComponentMapper<InventoryComponent> invm = ComponentMapper
            .getFor(InventoryComponent.class);

    public EntityController(Batch batch)
    {
        engine = new Engine();

        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderSystem(batch));
        engine.addSystem(new LightSystem(batch));
        engine.addSystem(new AISystem());
        engine.addSystem(new InteractSystem());
    }

    private MyComponent getComponent(String info)
    {
        String[] type = info.split(":");
        MyComponent component = null;
        try
        {
            @SuppressWarnings("rawtypes")
            Class cls = Class.forName("gdx.game.entities.components."
                    + type[0] + "Component");
            component = (MyComponent) cls.newInstance();
            component.loadData(type[1]);
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return component;
    }

    public void update(float delta)
    {
        engine.update(delta);
    }

    public int loadEntity(String[] lines, int currentLine)
    {
        Entity entity = new Entity();
        int lineNum = currentLine;
        while (!lines[lineNum].equals("End"))
        {
            if (lines[lineNum].equals("Player"))
            {
                player = entity;
            }
            else
            {
                entity.add(getComponent(lines[lineNum]));
            }
            lineNum++;
        }
        engine.addEntity(entity);
        return lineNum;
    }

    public void resize(int width, int height)
    {
        engine.getSystem(LightSystem.class).resize(width, height);
    }
}
