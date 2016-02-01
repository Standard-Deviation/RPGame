package gdx.game.igmenus;

import gdx.game.entities.EntityController;
import gdx.game.entities.components.TalkingComponent;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TalkingMenu extends Menu
{

    private TextureRegion char1Render;
    private TalkingComponent char2Talk;
    private TextureRegion char2Render;

    private int selected;
    private int question;
    private int length;

    public TalkingMenu()
    {
        super(30, 20, 660, 150, Input.Keys.T);
    }

    @Override
    public void update(Entity e1, Entity e2)
    {
        char1Render = EntityController.rm.get(e1).textures.loadFace();
        char2Talk = EntityController.tm.get(e2);
        char2Render = EntityController.rm.get(e2).textures.loadFace();
        char2Render.flip(true, false);
        selected = 0;
        question = 0;
        length = char2Talk.text.get(question).size() - 1;
        nextCycle = true;
    }

    @Override
    protected void draw(Batch batch)
    {
        batch.draw(char1Render, 50, 170);
        batch.draw(char2Render, 570, 170);
        this.drawString(batch, char2Talk.questions.get(question),
                100, 80, 1);
        this.drawString(batch,
                char2Talk.text.get(question).get(selected), 500, 50,
                1);
        if (selected != 0)
        {
            this.drawString(batch,
                    char2Talk.text.get(question).get(selected - 1),
                    500, 90, .5f);
        }
        if (selected != length)
        {
            this.drawString(batch,
                    char2Talk.text.get(question).get(selected + 1),
                    500, 10, .5f);
        }
    }

    @Override
    protected void handleInputs()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && selected > 0)
        {
            selected--;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)
                && selected < length)
        {
            selected++;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            question = char2Talk.getNext(question, selected);
            if (question == -1)
            {
                nextCycle = true;
                question = 0;
            }
        }
    }
}
