package org.uwcchina.uwccscvrexperience;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoSelectionActivity extends Activity {

    private List<CVideo> cVideos;
    private List<Bitmap> thumbs = new ArrayList<Bitmap>();
    private RequestQueue mRequestQueue;

    private CVideoAdapter videoAdapter;
    private ListView videoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_selection);

        videoListView = findViewById(R.id.videoListView);

        cVideos = new ArrayList<CVideo>();
        videoAdapter = new CVideoAdapter(this, cVideos, thumbs);
        videoListView.setAdapter(videoAdapter);

        mRequestQueue = Volley.newRequestQueue(this);
        requestCVideos();
    }

    /**
     * Creates a Volley.StringRequest for fetching the CVideos.
     */
    private void requestCVideos() {
        String uri = getString(R.string.api_base_url) + getString(R.string.api_port) +
                getString(R.string.api_cvideos_url);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                uri, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            Gson gson = new Gson();
                            cVideos = Arrays.asList(gson.fromJson(response.toString(),
                                    CVideo[].class));
                            requestThumbs();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Replace this with a more descriptive image.
            }
        });
        mRequestQueue.add(request);
    }

    private void requestThumbs() {
        for (int i = 0; i < cVideos.size(); i++) {
            ImageRequest thumbnailRequest = new ImageRequest(cVideos.get(i).getThumbnailUri(),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            thumbs.add(response);
                            videoAdapter.refreshCVideos(cVideos);
                        }
                    }, 1920, 1080, ImageView.ScaleType.CENTER,
                    Bitmap.Config.RGB_565,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            thumbs.add(null);
                        }
                    });
            mRequestQueue.add(thumbnailRequest);
        }
    }
}
