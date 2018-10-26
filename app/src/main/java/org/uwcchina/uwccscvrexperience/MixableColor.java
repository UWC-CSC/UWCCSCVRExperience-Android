package org.uwcchina.uwccscvrexperience;

import android.graphics.Color;

/**
 * Class used for mixing two colors together based on a delta.
 */
public class MixableColor {
    private int r, g, b;

    private int r1, g1, b1, r2, g2, b2, dr, dg, db;

    MixableColor(int colorPrimary, int colorSecondary) {
        r1 = Color.red(colorPrimary);
        g1 = Color.green(colorPrimary);
        b1 = Color.blue(colorPrimary);

        r2 = Color.red(colorSecondary);
        g2 = Color.green(colorSecondary);
        b2 = Color.blue(colorSecondary);

        dr = r2 - r1;
        dg = g2 - g1;
        db = b2 - b1;

        r = r1;
        g = g1;
        b = b1;
    }

    /**
     * Mixes the secondary color into the primary.
     *
     * @param delta The amount of the secondary color to add to the primary color. From 0 to 1.
     * @return self Returns self for chain usage.
     */
    public MixableColor setMixin(float delta) {
        r = (int) (dr * delta) + r1;
        g = (int) (dg * delta) + g1;
        b = (int) (db * delta) + b1;

        return this;
    }

    /**
     * Returns an integer color
     *
     * @return The final mixed color
     */
    public int toInt() {
        return Color.rgb(r, g, b);
    }
}

