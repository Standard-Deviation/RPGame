package gdx.game.utils;

import gdx.game.entities.systems.LightSystem;
import gdx.game.igmenus.InventoryMenu;
import gdx.game.igmenus.MainMenu;
import gdx.game.igmenus.Menu;
import gdx.game.igmenus.TalkingMenu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud
{
    private Stage stage;
    private Viewport viewport;

    Label debugLabel;
    public static List<Menu> menus = new ArrayList<Menu>();

    public Hud(SpriteBatch batch)
    {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        viewport = new FitViewport(w, h, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        menus.add(new MainMenu());
        menus.add(new TalkingMenu());
        menus.add(new InventoryMenu());

        debugLabel = new Label("FPS: "
                + Gdx.graphics.getFramesPerSecond() + " Delta: "
                + Gdx.graphics.getDeltaTime(), new Label.LabelStyle(
                new BitmapFont(), Color.YELLOW));

        stage.addActor(debugLabel);
        for (Menu menu : menus)
        {
            stage.addActor(menu);
        }
    }

    public void render()
    {
        Batch batch = stage.getBatch();
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.setShader(LightSystem.defaultShader);

        debugLabel.setText("FPS: "
                + Gdx.graphics.getFramesPerSecond() + " Delta: "
                + Gdx.graphics.getDeltaTime());
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        batch.setShader(LightSystem.currentShader);
    }

    public void dispose()
    {
        stage.dispose();
        for (Menu menu : menus)
        {
            menu.dispose();
        }
    }

    public void menuToggles()
    {
        boolean visible = false;
        for (Menu menu : menus)
        {
            if (menu.visible)
            {
                visible = true;
            }
        }
        for (Menu menu : menus)
        {
            if (visible && menu.visible)
            {
                menu.toggleVisible();
            }
            else if (!visible)
            {
                menu.toggleVisible();
            }
        }
    }

    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }
}
