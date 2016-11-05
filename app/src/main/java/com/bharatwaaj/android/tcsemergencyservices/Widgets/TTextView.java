package com.bharatwaaj.android.tcsemergencyservices.Widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Bharatwaaj on 04-11-2016.
 */

public class TTextView extends TextView {

    public TTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public TTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Light.ttf");
            setTypeface(tf);
        }
    }

}