package gdx.game.igmenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;

public class MainMenu extends Menu
{

    private String[] options =
        { "Quit", "Load", "Save" };
    private int selected = options.length - 1;

    public MainMenu()
    {
        super(100, 100, 200, 450, Input.Keys.ESCAPE);
    }

    @Override
    protected void draw(Batch batch)
    {
        for (int i = 0; i < options.length; i++)
        {
            drawString(batch, options[i], 75, 100 + i * 50, 1);
        }
        batch.draw(selector, getX() + 40, getY() + 100 + selected
                * 50);
    }

    @Override
    protected void handleInputs()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
        {
            selected = selected < options.length - 1 ? selected + 1
                    : selected;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
        {
            selected = selected > 0 ? selected - 1 : selected;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            switch (selected)
            {
                case 2:
                    FileHandle from = Gdx.files.internal("NewGame");
                    from.copyTo(Gdx.files.local("bin/savs"));
                    FileHandle newFile = Gdx.files.local("bin/savs");
                    System.out.println("saved " + newFile.path());
                    break;
                case 1:

                    break;
                case 0:
                    Gdx.app.exit();
                    break;
            }
        }
    }
}
