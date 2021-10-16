package com.example.graphic.Fragments.Graphics;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.graphic.R;
import com.example.graphic.views.lab1.ViewGraphic;
import com.example.graphic.views.lab1.Graphic1;
import com.example.graphic.views.lab1.Graphic2;


public class GraphicFragment1 extends Fragment {
    public static final int FIRST = 101;
    public static final int SECOND = 102;
    ViewGraphic root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = new ViewGraphic(getContext(), new Graphic1());
        registerForContextMenu(root);
        return root;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(Menu.NONE, FIRST, Menu.NONE, "Построить первый график");
        menu.add(Menu.NONE, SECOND, Menu.NONE, "Построить второй график");


    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        CharSequence message;
        switch (item.getItemId())
        {
            case FIRST:
                root.setGraphic(new Graphic1());
                break;
            case SECOND:
                root.setGraphic(new Graphic2());
                break;
            default:
                return super.onContextItemSelected(item);
        }
        root.invalidate();
        return true;
    }
}