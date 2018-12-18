package org.uwcchina.uwccscvrexperience;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

public class CVideoActivity extends Activity {
    private static final String TAG = CVideoActivity.class.getSimpleName();

    /**
     * Tracks the file to be loaded across the lifetime of this activity.
     **/
    private Uri fileUri;

    /**
     * Configuration information for the video.
     **/
    private VrVideoView.Options videoOptions = new VrVideoView.Options();

    private VideoLoaderTask backgroundVideoLoaderTask;

    private VrVideoView cVideoView;

    /**
     * Arbitrary constants and variable to track load status. In this example, this variable should
     * only be accessed on the UI thread. In a real app, this variable would be code that performs
     * some UI actions when the video is fully loaded.
     */
    public static final int LOAD_VIDEO_STATUS_UNKNOWN = 0;
    public static final int LOAD_VIDEO_STATUS_SUCCESS = 1;
    public static final int LOAD_VIDEO_STATUS_ERROR = 2;

    private int loadVideoStatus = LOAD_VIDEO_STATUS_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvideo);

        cVideoView = findViewById(R.id.cvideo_view);

        loadVideoStatus = LOAD_VIDEO_STATUS_UNKNOWN;

        handleIntent(getIntent());
    }

    /**
     * Load custom videos based on the Intent or load the default video. See the Javadoc for this
     * class for information on generating a custom intent via adb.
     */
    private void handleIntent(Intent intent) {
        // Determine if the Intent contains a file to load.
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Log.i(TAG, "ACTION_VIEW Intent received");

            fileUri = intent.getData();
            if (fileUri == null) {
                Log.w(TAG, "No data uri specified. Use \"-d /path/filename\".");
            } else {
                Log.i(TAG, "Using file " + fileUri.toString());
            }

            videoOptions.inputFormat = intent.getIntExtra("inputFormat", VrVideoView.Options.FORMAT_DEFAULT);
            videoOptions.inputType = intent.getIntExtra("inputType", VrVideoView.Options.TYPE_MONO);
        } else {
            Log.i(TAG, "Intent is not ACTION_VIEW. Using the default video.");
            fileUri = null;
        }

        // Load the bitmap in a background thread to avoid blocking the UI thread. This operation can
        // take 100s of milliseconds.
        if (backgroundVideoLoaderTask != null) {
            // Cancel any task from a previous intent sent to this activity.
            backgroundVideoLoaderTask.cancel(true);
        }
        backgroundVideoLoaderTask = new VideoLoaderTask();
        backgroundVideoLoaderTask.execute(Pair.create(fileUri, videoOptions));
    }

    /**
     * Helper class to manage threading.
     */
    class VideoLoaderTask extends AsyncTask<Pair<Uri, VrVideoView.Options>, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Pair<Uri, VrVideoView.Options>... fileInformation) {
            try {
                if (fileInformation == null || fileInformation.length < 1
                        || fileInformation[0] == null || fileInformation[0].first == null) {
                    // No intent was specified, so we default to playing the local stereo-over-under video.
                    VrVideoView.Options options = new VrVideoView.Options();
                    options.inputType = VrVideoView.Options.FORMAT_DEFAULT;
                    cVideoView.loadVideo(Uri.parse("https://d2v9y0dukr6mq2.cloudfront.net/video/preview/GTYSdDW/videoblocks-gili-meno-turtles-underwater-360-vr-underwater-360-vr_H5tBLnQaW__WM.mp4"), options);
                } else {
                    cVideoView.loadVideo(fileInformation[0].first, fileInformation[0].second);
                }
            } catch (IOException e) {
                // An error here is normally due to being unable to locate the file.
                loadVideoStatus = LOAD_VIDEO_STATUS_ERROR;
                // Since this is a background thread, we need to switch to the main thread to show a toast.
                cVideoView.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CVideoActivity.this, "Error opening file. ",
                                Toast.LENGTH_LONG).show();
                    }
                });
                Log.e(TAG, "Could not open video: " + e);
            }

            return true;
        }
    }
}
