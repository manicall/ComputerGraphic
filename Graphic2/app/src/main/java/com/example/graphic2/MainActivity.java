package com.example.graphic2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    MyView myView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = new MyView(this);
        setContentView(myView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, myView.SIMPLE_DDA__STRING_FILL_WITH_SEED,
                Menu.NONE, "построчная заливка");
        menu.add(Menu.NONE, myView.BRESENHAM__WITH_STORING_POINTS_OF_BORDER_IN_STACK,
                Menu.NONE,"заливка с границей в стеке");
        menu.add(Menu.NONE, myView.BRESENHAM__SIMPLE_FILL_WITH_SEED_WITH_RECURSION,
                Menu.NONE,"рекурсивная заливка");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        myView.setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

}