package org.weitblicker.weitblickapp;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Sebastian Pütz - spuetz@uos.de on 22.10.16.
 */

public class OpenSansTextView extends TextView {
    public OpenSansTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Regular.ttf"));
    }
}
