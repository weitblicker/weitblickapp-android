package org.weitblicker.weitblickapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

class ImageSlidePagerAdapter extends FragmentStatePagerAdapter {

    List<ProjectImage> images = null;
    Context context = null;

    public ImageSlidePagerAdapter(Context context, FragmentManager fm, List<ProjectImage> images) {
        super(fm);
        this.images = images;
        this.context = context;
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