package fr.xebia.volleytuto;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONObject;

public final class VideoListActivity extends ListActivity {

    private static final String VIMEO_JSON_URL = "http://vimeo.com/api/v2/xebia/videos.json";
    private RequestQueue mRequestQueue;
    private VideoListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XebiaApplication app = (XebiaApplication) getApplication();
        mRequestQueue = app.getVolleyRequestQueue();
        ImageLoader imageLoader = app.getVolleyImageLoader();
        mAdapter = new VideoListAdapter(getApplicationContext(), imageLoader);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        JsonArrayRequest request = new JsonArrayRequest(VIMEO_JSON_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        mAdapter.updateVideos(jsonArray);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(VideoListActivity.this, "Error while getting videos: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        request.setTag(this);
        mRequestQueue.add(request);
    }

    @Override
    protected void onStop() {
        mRequestQueue.cancelAll(this);
        super.onStop();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        JSONObject item = mAdapter.getItem(position);
        String url = item.optString(VideoListAdapter.JSON_VIDEO_URL);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
