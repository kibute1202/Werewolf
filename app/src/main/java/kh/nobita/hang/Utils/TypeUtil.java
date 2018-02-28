package kh.nobita.hang.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class TypeUtil {
    private static final String FONTS_FOLDER = "fonts";

    public static Typeface getFont(Context context, String fontFamily) {
        return Typeface.createFromAsset(context.getAssets(), getFontPath(fontFamily));
    }

    public static float getTextViewFontSize(Context context, TextView textView) {
        float textSize = textView.getTextSize();
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return textSize / scaledDensity;
    }

    private static String getFontPath(String fontFamily) {
        return FONTS_FOLDER + "/" + fontFamily + ".ttf";
    }
}

