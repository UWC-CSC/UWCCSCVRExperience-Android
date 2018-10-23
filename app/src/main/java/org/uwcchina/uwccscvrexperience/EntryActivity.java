package org.uwcchina.uwccscvrexperience;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class EntryActivity extends AppCompatActivity {

    private boolean animated = false;

    private RequestQueue mRequestQueue;
    private SharedPreferences prefs = null;

    private ImageView logoImageView;
    private ImageButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("com.uwcchina.uwccscvrexperience", MODE_PRIVATE);

        setContentView(R.layout.activity_entry);

        logoImageView = findViewById(R.id.logoImageView);
        nextButton = findViewById(R.id.nextButton);

        mRequestQueue = Volley.newRequestQueue(this);
        requestCVideos();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && !animated) {
            startEntryAnimation();
            animated = true;
        }
    }

    private void startEntryAnimation() {
        logoImageView.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.entry_animation));
    }

    public void nextButtonPressed(View view) {
        if (prefs.getBoolean("firstRun", true)) {
            Intent intent = new Intent(EntryActivity.this,
                    ConfigurationActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slidein_to_left_fast, R.anim.slideout_to_left_fast);
        }
    }

    /**
     * Creates a Volley.StringRequest for fetching the CVideos.
     */
    private void requestCVideos() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.cvideos_url), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            // TODO: Show the button, animated
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(EntryActivity.class.getName(), "returned an error: ", error.getCause());
            }
        });
        mRequestQueue.add(request);
    }
}
