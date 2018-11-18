package org.uwcchina.uwccscvrexperience;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.widget.FrameLayout;

public class CVideoActivity extends Activity {

    private ParallaxView parallaxView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvideo);

        parallaxView = findViewById(R.id.video_selection_background);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) parallaxView.getLayoutParams();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int sWidth = size.x;
        int sHeight = size.y;
        params.width = sWidth + 100;
        params.height = sHeight + 100;
        parallaxView.setLayoutParams(params);
        parallaxView.init();
    }

    @Override
    protected void onResume() {
        parallaxView.registerSensorListener();
        super.onResume();
    }

    @Override
    protected void onPause() {
        parallaxView.unregisterSensorListener();
        super.onPause();
    }
}
