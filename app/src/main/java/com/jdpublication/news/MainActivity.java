package com.jdpublication.news;

import static com.jdpublication.news.utils.CommonFunctions.getRequestURL;
import static com.jdpublication.news.utils.CommonFunctions.isConnectionAvailable;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jdpublication.news.Adapter.ListViewAdapter;
import com.jdpublication.news.Parse.NewsListParse;
import com.jdpublication.news.utils.MyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListViewAdapter adapter;
    ArrayList<NewsListParse> arrayList = new ArrayList<>();

    TextView bottomTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomTextView = findViewById(R.id.textView);
        adapter = new ListViewAdapter(this, R.layout.list_item_view, arrayList);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (arrayList.size() < 1) {
                    bottomTextView.setText("All news cleared.");
                }
            }
        });

        MyListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadNews();
    }

    private void LoadNews() {
        if (isConnectionAvailable(this)) {
            bottomTextView.setText("Loading...");
            StringRequest stringRequest = new StringRequest(Request.Method.GET, getRequestURL("us", "business"), response -> {
                try {
                    arrayList.clear();
                    JSONObject jsonObject = new JSONArray("[" + response + "]").getJSONObject(0);
                    JSONArray jsonArray = jsonObject.getJSONArray("articles");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectInner = jsonArray.getJSONObject(i);
                        String source = new JSONArray("[" + jsonObjectInner.getString("source") + "]").getJSONObject(0).getString("name");
                        String author = jsonObjectInner.getString("author");
                        String content = jsonObjectInner.getString("content");
                        String title = jsonObjectInner.getString("title");
                        String description = jsonObjectInner.getString("description");
                        String urlToImage = jsonObjectInner.getString("urlToImage");
                        String publishedAt = jsonObjectInner.getString("publishedAt");

                        arrayList.add(new NewsListParse(source, author, urlToImage, title, description, publishedAt, content));
                    }
                    adapter.notifyDataSetChanged();
                    bottomTextView.setText("That's all for Now");
                } catch (JSONException e) {
                    Log.d("TAG", "LoadNews: " + e);
                    String errorMessage = e.getMessage();
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }, error -> runOnUiThread(() -> {
                error.printStackTrace();
                Log.d("TAG", error.toString());
            })) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("User-Agent", "Mozilla/5.0");
                    return headers;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.getCache().clear();
            requestQueue.add(stringRequest);
        } else {
            bottomTextView.setText("Internet not available!");
            Toast.makeText(this, "Internet not available!", Toast.LENGTH_SHORT).show();
        }
    }
}