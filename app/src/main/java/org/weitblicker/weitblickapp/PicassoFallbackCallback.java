package org.weitblicker.weitblickapp;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * Created by spuetz on 28.01.17.
 */

public class PicassoFallbackCallback implements Callback {

    Context context;
    ImageView imageView;
    String uri;


    public PicassoFallbackCallback(Context context, ImageView imageView, String uri){
        this.context = context;
        this.imageView = imageView;
        this.uri = uri;
    }

    @Override
    public void onSuccess() {}

    @Override
    public void onError() {
        //Try again online if cache failed
        Picasso.with(context)
            .load(uri)
            .fit()
            .centerCrop()
            .into(imageView, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Log.v("Picasso","Could not fetch image");
                }
            });
    }
}

