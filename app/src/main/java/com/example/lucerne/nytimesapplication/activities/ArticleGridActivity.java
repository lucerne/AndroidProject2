package com.example.lucerne.nytimesapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lucerne.nytimesapplication.R;
import com.example.lucerne.nytimesapplication.adapters.ArticleArrayAdapter;
import com.example.lucerne.nytimesapplication.models.Article;
import com.example.lucerne.nytimesapplication.models.Filter;
import com.example.lucerne.nytimesapplication.net.EndlessScrollListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ArticleGridActivity extends AppCompatActivity {

    GridView gvResults;
    ArrayList<Article> articles;
    ArticleArrayAdapter articleAdapter;
    Filter filter = new Filter();
    private final int REQUEST_CODE = 1;
    String currQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();

//        filter = new Filter();
    }

    public void setupViews() {
        gvResults = (GridView) findViewById(R.id.gvResults);
        articles = new ArrayList<>();
        articleAdapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(articleAdapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);

                Article article = articles.get(position);

                i.putExtra("article", article);
                startActivity(i);
            }
        });

        // Attach the listener to the AdapterView onCreate
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        OnArticleSearch(currQuery, offset);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                currQuery = query;
                OnArticleSearch(query, 0);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Expand the search view and request focus
        searchItem.expandActionView();
        searchView.requestFocus();

        // Use a custom search icon for the SearchView in AppBar
        int searchImgId = android.support.v7.appcompat.R.id.search_button;
        ImageView v = (ImageView) searchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.yellowstarfull);

        // Customize searchview text and hint colors
        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText et = (EditText) searchView.findViewById(searchEditId);
        et.setTextColor(Color.BLACK);
        et.setHintTextColor(Color.BLACK);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.miSettings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnArticleSearch(String query, int page) {
        if (!isNetworkAvailable()){
            Toast.makeText(getApplicationContext(), "Internet is not available",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "7c3bb249c8aa4b7c8861b39cc84300cf");
        params.put("q", query);

        if (filter.getNewsValues() != null) {
            String begin_fq = "news_desk:(";
            String fq = begin_fq;

            for (int i = 0; i < filter.getNewsValues().size(); ++i) {
                if (i == filter.getNewsValues().size() - 1) {
                    fq += "\"" + filter.getNewsValues().get(i) + "\")";
                } else {
                    fq += "\"" + filter.getNewsValues().get(i) + "\" ";
                }
            }

            if (!fq.equals(begin_fq)) {
                params.put("fq", fq);
            }
        }
        params.put("sort", filter.getSortOrder());
        params.put("page", page);
        if (filter.getCalendar() != null) {
            String date = new SimpleDateFormat("yyyyMMdd").format(filter.getCalendar().getTime());
            params.put("begin_date", date);
        }

        final int new_page = page;

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");

                    if (new_page == 0) {
                        articleAdapter.clear();
                    }
                    articleAdapter.addAll(Article.fromJSONArray(articleJsonResults));
                    articleAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", articles.toString());
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void onSetting(MenuItem mi) {
        Intent i = new Intent(this, SettingActivity.class);
        i.putExtra("filter", filter);
//        startActivity(i);
        startActivityForResult(i,REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // deep copy?
            filter = (Filter) data.getSerializableExtra("filter");
            int a = 1;
        }
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
