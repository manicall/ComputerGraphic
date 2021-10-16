package com.example.graphic.Fragments.Graphics;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.graphic.views.lab2.MyGraphic;

public class GraphicFragment2 extends Fragment {
    public static final int FILL_B = 101;
    public static final int FILL_L = 102;
    public static final int FILL_V = 103;
    public static final int INCREMENT = 104;
    private int numOfVertexes = 5;
    MyGraphic root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = new MyGraphic(getContext(), numOfVertexes, MyGraphic.V);
        root.setFilling(MyGraphic.B);
        registerForContextMenu(root);
        return root;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(Menu.NONE, FILL_B, Menu.NONE, "Заполнить B");
        menu.add(Menu.NONE, FILL_L, Menu.NONE, "Заполнить L");
        menu.add(Menu.NONE, FILL_V, Menu.NONE, "Заполнить V");
        menu.add(Menu.NONE, INCREMENT, Menu.NONE, "добавить точку многоугольника");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        CharSequence message;
        switch (item.getItemId())
        {
            case FILL_B:
                root.setFilling(MyGraphic.B);
                break;
            case FILL_L:
                root.setFilling(MyGraphic.L);
                break;
            case FILL_V:
                root.setFilling(MyGraphic.V);
                break;
            case INCREMENT:
                root.setNumOfVertexes(++numOfVertexes);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        root.invalidate();
        return true;
    }
}