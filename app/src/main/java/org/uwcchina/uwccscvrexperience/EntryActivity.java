package org.uwcchina.uwccscvrexperience;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private ImageView entryServerErrorImageView;
    private ImageButton entryNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("com.uwcchina.uwccscvrexperience", MODE_PRIVATE);

        setContentView(R.layout.activity_entry);

        logoImageView = findViewById(R.id.logoImageView);
        entryNextButton = findViewById(R.id.entryNextButton);
        entryServerErrorImageView = findViewById(R.id.entryServerErrorImageView);

        mRequestQueue = Volley.newRequestQueue(this);
        tryServer();
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
    private void tryServer() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.api_base_url) + getString(R.string.api_port),
                null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            entryServerErrorImageView.setVisibility(View.INVISIBLE);
                            entryNextButton.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                entryNextButton.setVisibility(View.INVISIBLE);
                entryServerErrorImageView.setVisibility(View.VISIBLE);
            }
        });

        mRequestQueue.add(request);
    }

    /**
     * Wrapper method for the button
     */
    public void tryServer(View view) {
        tryServer();
    }
}
