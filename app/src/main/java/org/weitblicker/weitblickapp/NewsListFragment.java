package org.weitblicker.weitblickapp;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsListFragment extends ListFragment {
    ArrayList<NewsArticle> news = new ArrayList<NewsArticle>();
    Context context;
    OnNewsArticleSelectListener onNewsArticleSelectInterface;
    NewsListAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnNewsArticleSelectListener) {
            onNewsArticleSelectInterface = (OnNewsArticleSelectListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewsArticleSelectListener");
        }
    }

    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        adapter = new NewsListAdapter(getActivity(), news);
        loadNews();

        setListAdapter(adapter);

        // disables the divider
        getListView().setDividerHeight(0);
    }

    public interface OnNewsArticleSelectListener {
        void onNewArticleSelect(NewsArticle newsArticle);
    }

    public void onListItemClick(ListView l, View v, int position, long id){
        onNewsArticleSelectInterface.onNewArticleSelect(news.get(position));
        Log.i("debug","Selected news at pos: " + position);
    }

    private void loadNews(){

        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.start();
        final String url = "https://weitblicker.org/news-rest-api";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(response.toString());
                            JSONArray articllist = response.getJSONArray("news-list");
                            for (int i = 0; i < articllist.length(); i++) {
                                JSONObject container = articllist.getJSONObject(i);
                                JSONObject article = container.getJSONObject("news-article");
                                String title = article.getString("article-title");
                                String idStr = article.getString("id");
                                int id = Integer.valueOf(idStr);
                                String abst = article.getString("conclusion");
                                JSONObject image = article.getJSONObject("teaserimage");
                                String imageUrl = image.getString("src");
                                String text = article.getString("article-text").trim();
                                text = text.trim();
                                text = text.replaceAll("\n{2,}", "\n\n");
                                String host = article.getString("wb-host");
                                String datetime = article.getString("datetime");

                                NewsArticle newsArticle = new NewsArticle();
                                newsArticle.setName(title);
                                newsArticle.setAbstract(abst);
                                newsArticle.setImageUrl(imageUrl);
                                newsArticle.setId(id);
                                newsArticle.setText(text);
                                newsArticle.setHost(host);
                                newsArticle.setDateTime(datetime);

                                Log.i("debug", "Added News Article: " + title);
                                System.out.println("Added News Article: " + title);
                                news.add(newsArticle);
                                adapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                                e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        error.printStackTrace();
                    }
                });

        queue.add(jsObjRequest);

    }
}
