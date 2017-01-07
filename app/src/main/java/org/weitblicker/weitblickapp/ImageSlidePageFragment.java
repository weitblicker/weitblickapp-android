package org.weitblicker.weitblickapp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageSlidePageFragment extends Fragment {

    public static final String ARG_IMAGE = "IMAGE_PAGE";

    private ImageInfo image = null;

    public static ImageSlidePageFragment create(ImageInfo image) {
        ImageSlidePageFragment fragment = new ImageSlidePageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_IMAGE, image);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageSlidePageFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = (ImageInfo) getArguments().getSerializable(ARG_IMAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.image_pager_item, container, false);

        // Set the title view to show the page number.
        ImageView imageView = (ImageView) rootView.findViewById(R.id.image_pager_item_image);

        Picasso.with(getActivity())
                .load(image.url)
                .fit()
                .centerCrop()
                .into(imageView);

        return rootView;
    }

}
