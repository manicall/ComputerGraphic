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
import com.example.graphic.views.lab3.Flag;
import com.example.graphic.views.lab3.Rhombus;

public class GraphicFragment3 extends Fragment {
    public static final int FILL_B = 101;
    public static final int FILL_L = 102;
    public static final int FILL_V = 103;
    public static final int INCREMENT = 104;
    private int numOfVertexes = 5;
    MyGraphic root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        return new Flag(getContext());
    }

}