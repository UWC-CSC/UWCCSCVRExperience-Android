package org.uwcchina.uwccscvrexperience;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 *
 */
public class EntryActivity extends AppCompatActivity {

    private boolean animated = false;

    private ImageView loadingLogoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entry);

        loadingLogoImageView = findViewById(R.id.loadingLogoImageView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && !animated) {
            startEntryAnimation();
            animated = true;
        }
    }

    private void startEntryAnimation() {
        loadingLogoImageView.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.entry_animation));
    }
}
