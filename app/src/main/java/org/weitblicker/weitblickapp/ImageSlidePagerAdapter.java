package org.weitblicker.weitblickapp;

import android.content.Context;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.List;

class ImageSlidePagerAdapter extends FragmentStatePagerAdapter {

    List<ImageInfo> images = null;

    public ImageSlidePagerAdapter(Context context, FragmentManager fm, List<ImageInfo> images) {
        super(fm);
        this.images = images;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageSlidePageFragment.create(images.get(position));
    }

    @Override
    public int getCount() {
        return images.size();
    }
}