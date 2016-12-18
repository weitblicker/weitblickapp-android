package org.weitblicker.weitblickapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<NewsArticle> mDataSource;

    public NewsListAdapter(Context context, ArrayList<NewsArticle> items){
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_news, parent, false);

        // title
        TextView captionTextView =
                (TextView) rowView.findViewById(R.id.list_item_news_title);

        // abstract
        TextView abstractTextView =
                (TextView) rowView.findViewById(R.id.list_item_news_abstract);

        // host
        TextView hostTextView =
                (TextView) rowView.findViewById(R.id.list_item_news_host);

        // datetime
        TextView datetimeTextView =
                (TextView) rowView.findViewById(R.id.list_item_news_datetime);

        // image
        ImageView imageView =
                (ImageView) rowView.findViewById(R.id.list_item_news_image);

        NewsArticle article = (NewsArticle) getItem(position);

        captionTextView.setText(article.getName());
        abstractTextView.setText(article.getAbstract());
        hostTextView.setText(article.getHost());
        datetimeTextView.setText(article.getDate());

        Picasso.with(mContext)
            .load(article.getImageUrl())
            .fit()
            .centerCrop()
            .into(imageView);

        return rowView;
    }

}
