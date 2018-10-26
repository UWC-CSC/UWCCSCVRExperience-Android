package org.uwcchina.uwccscvrexperience;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Util {
    public static class Conversion {
        /**
         * This method converts dp unit to equivalent pixels, depending on device density.
         *
         * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
         * @param context Context to get resources and device specific display metrics
         * @return A float value to represent px equivalent to dp depending on device density
         */
        public static float dpToPx(float dp, Context context) {
            Resources resources = context.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            return px;
        }

        /**
         * This method converts device specific pixels to density independent pixels.
         *
         * @param px      A value in px (pixels) unit. Which we need to convert into db
         * @param context Context to get resources and device specific display metrics
         * @return A float value to represent dp equivalent to px value
         */
        public static float pxToDp(float px, Context context) {
            return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        }
    }


}
