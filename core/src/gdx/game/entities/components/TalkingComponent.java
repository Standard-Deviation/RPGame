package gdx.game.entities.components;

import gdx.game.entities.MyComponent;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class TalkingComponent extends MyComponent
{

    public List<String> questions = new ArrayList<String>();
    public List<ArrayList<String>> text = new ArrayList<ArrayList<String>>();
    // -1 means end convo
    public List<ArrayList<Integer>> textGoto = new ArrayList<ArrayList<Integer>>();

    private String path;

    @Override
    public void loadData(String data)
    {
        this.path = data;
        FileHandle file = Gdx.files.internal("NewGame/" + path
                + ".txt");

        String[] lines = file.readString().split("\\r?\\n");
        for (int i = 0; i < lines.length; i++)
        {
            addTextLine(lines[i]);
        }
    }

    private void addTextLine(String str)
    {

        String[] parts = str.split(";");
        String[] text1 = parts[1].split(",");
        String[] textGoto1 = parts[2].split(",");

        ArrayList<String> textArray = new ArrayList<String>();
        ArrayList<Integer> textGotoArray = new ArrayList<Integer>();
        for (int j = 0; j < text1.length; j++)
        {
            textArray.add(text1[j]);
            textGotoArray.add(Integer.parseInt(textGoto1[j]));
        }

        questions.add(parts[0]);
        text.add(textArray);
        textGoto.add(textGotoArray);

    }

    @Override
    public String getData()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public int getNext(int question, int selected)
    {
        return textGoto.get(question).get(selected);
    }

}
