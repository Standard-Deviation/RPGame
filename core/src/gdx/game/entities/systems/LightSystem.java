package gdx.game.entities.systems;

import gdx.game.entities.EntityController;
import gdx.game.entities.components.LightComponent;
import gdx.game.entities.components.PropertiesComponent;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class LightSystem extends EntitySystem
{

    private ImmutableArray<Entity> entities;

    private static Batch batch;
    private static FrameBuffer fbo;

    public static ShaderProgram finalShader;
    public static ShaderProgram defaultShader;
    public static ShaderProgram currentShader;

    private static Texture light;

    // values passed to the shader
    private static final float ambientIntensity = .7f;
    private static final Vector3 ambientColor = new Vector3(0.3f,
            0.2f, 0.7f);

    @SuppressWarnings("static-access")
    public LightSystem(Batch batch)
    {
        this.batch = batch;

        ShaderProgram.pedantic = false;

        finalShader = new ShaderProgram(
                Gdx.files
                        .internal("Textures/Shaders/vertexShader.glsl"),
                Gdx.files
                        .internal("Textures/Shaders/pixelShader.glsl"));
        defaultShader = new ShaderProgram(
                Gdx.files
                        .internal("Textures/Shaders/vertexShader.glsl"),
                Gdx.files
                        .internal("Textures/Shaders/defaultPixelShader.glsl"));

        finalShader.begin();
        finalShader.setUniformi("u_lightmap", 1);
        finalShader.setUniformf("ambientColor", ambientColor.x,
                ambientColor.y, ambientColor.z, ambientIntensity);
        finalShader.end();

        // declare all stuff we need to draw
        light = new Texture("Textures/Shaders/light.png");

        currentShader = defaultShader;
    }

    public void resize(int width, int height)
    {
        fbo = new FrameBuffer(Format.RGBA8888, width, height, false);

        finalShader.begin();
        finalShader.setUniformf("resolution", width, height);
        finalShader.end();
    }

    @Override
    public void update(float deltaTime)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.L))
        {
            toggleShader();
        }
        fbo.begin();
        batch.setShader(currentShader);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (int i = 0; i < entities.size(); ++i)
        {
            PropertiesComponent position = EntityController.pm
                    .get(entities.get(i));
            LightComponent lightComp = EntityController.lm
                    .get(entities.get(i));

            // draw the light to the FBO
            float lightSize = (lightComp.radius
                    + (lightComp.radius * .05f)
                    * (float) Math.sin(lightComp
                            .updateAngle(deltaTime)) + (lightComp.radius * .02f)
                            * MathUtils.random());
            batch.draw(light, position.x + 20 - lightSize * 0.5f
                    + 0.5f,
                    position.y + 20 + 0.5f - lightSize * 0.5f,
                    lightSize, lightSize);

        }
        batch.end();
        fbo.end();

    }

    private void toggleShader()
    {
        if (currentShader == finalShader)
        {
            currentShader = defaultShader;
        }
        else
        {
            currentShader = finalShader;
        }
    }

    public static void bindFbo()
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        fbo.getColorBufferTexture().bind(1);
        light.bind(0);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        entities = engine.getEntitiesFor(Family.all(
                LightComponent.class, PropertiesComponent.class)
                .get());
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
        entities = null;
        finalShader.dispose();
        defaultShader.dispose();
        light.dispose();
        fbo.dispose();
    }

}
