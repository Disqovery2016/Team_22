package com.bharatwaaj.android.tcsemergencyservices.Widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Bharatwaaj on 04-11-2016.
 */

public class TButton extends Button {

    public TButton(Context context) {
        super(context);
        init();
    }

    public TButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Light.ttf");
            setTypeface(tf);
        }
    }
}
