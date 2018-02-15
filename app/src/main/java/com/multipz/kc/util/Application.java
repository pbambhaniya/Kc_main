package com.multipz.kc.util;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class Application extends android.app.Application {

    public static Shared shared;
    public static Typeface fontoxegenbold,fontoxegenlight,fontoxegenregular;
    public static Typeface FontCoral;
    public static android.graphics.Typeface fontOxygenRegular;


    @Override
    public void onCreate() {
        super.onCreate();
//        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "font/Oxygen-Bold.ttf");
        fontoxegenregular = Typeface.createFromAsset(getAssets(), "font/Oxygen-Regular.ttf");
        fontoxegenbold = Typeface.createFromAsset(getAssets(), "font/Oxygen-Bold.ttf");
        fontoxegenlight = Typeface.createFromAsset(getAssets(), "font/Oxygen-Light.ttf");
        shared = new Shared(this);
    }

    public static void setFontDefault(ViewGroup group) {
        setFont(group, Application.fontoxegenregular);
    }

    public static void setFont(ViewGroup group, Typeface font) {
        int count = group.getChildCount();
        View v;
        for(int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if(v instanceof TextView)
                ((TextView)v).setTypeface(font);
            else if(v instanceof ViewGroup)
                setFont((ViewGroup)v, font);
        }
    }
}
