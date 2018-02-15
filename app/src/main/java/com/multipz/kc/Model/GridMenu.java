package com.multipz.kc.Model;

/**
 * Created by Admin on 08-08-2017.
 */

public class GridMenu {

    String name;
    int drawable;


    public GridMenu(String name, int drawable) {
        this.name = name;
        this.drawable = drawable;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
