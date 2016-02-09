package gdx.game.utils;

import gdx.game.items.Item;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;

public class ScrollList<T extends Item> extends ArrayList<T>
{

    private static final long serialVersionUID = 1L;

    private int selected;
    private int top;
    private int length;

    public ScrollList(int length)
    {
        super();
        this.length = length;
        top = 0;
    }

    public void update()
    {
        selected = 0;
    }

    public float render(Batch batch, float x, float y)
    {
        for (int i = top; i < top + length && i < size(); i++)
        {
            get(i).draw(batch, x, y - i * 20);
        }
        return y - selected * 20;
    }

    public void handleInput()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && selected > 0)
        {
            selected--;
            if (selected == top + length)
            {
                top--;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)
                && selected < length && selected < size() - 1)
        {
            selected++;
            if (selected > length)
            {
                top++;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            System.out.println(selected + " " + top);
        }
    }

}
