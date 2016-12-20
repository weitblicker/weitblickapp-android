package org.weitblicker.weitblickapp;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class NewsArticleFragment extends Fragment {

    private NewsArticle article = null;
    private Context context = null;
    static Gson gson = new Gson();

    final static String NEWS_ARTICLE_BUNDLE_KEY = "JSON_PROJECT";

    public NewsArticleFragment() {
        super();
    }

    public static NewsArticleFragment newInstance(NewsArticle article) {
        String articleJson = gson.toJson(article);
        NewsArticleFragment fragment = new NewsArticleFragment();

        Bundle bundle = new Bundle();
        bundle.putString(NEWS_ARTICLE_BUNDLE_KEY, articleJson);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String articleJson = bundle.getString(NEWS_ARTICLE_BUNDLE_KEY);
            if(articleJson != null){
                this.article = gson.fromJson(articleJson, NewsArticle.class);
                Log.i("debug", articleJson);
            }else{
                throw new RuntimeException("Bundle Arguments for "+ NEWS_ARTICLE_BUNDLE_KEY + " are null!");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        TextView titleView = (TextView) view.findViewById(R.id.fragment_news_title);
        titleView.setText(article.getName());

        TextView abstView = (TextView) view.findViewById(R.id.fragment_news_abstract);
        abstView.setText(article.getAbstract());

        TextView descriptionView = (TextView) view.findViewById(R.id.fragment_news_description);
        descriptionView.setText(article.getText());

        ImageView imageView = (ImageView) view.findViewById(R.id.fragment_news_image);

        Picasso.with(context)
            .load(article.getImageUrl())
            .fit()
            .centerCrop()
            .into(imageView);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }
}
