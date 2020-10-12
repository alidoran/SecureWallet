package tools;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import static base.BaseApplication.getContext;

public class CustomSpan extends TypefaceSpan {
    private Context context = getContext();
    private Typeface typeface;
    private int size = 13;
    private int color = 0;

    public CustomSpan(int size, int color) {
        super("");
        this.typeface = Typeface.createFromAsset(context.getAssets(), MySettings.selectedFont);
        if (size != 0)
            this.size = size;
        this.color = color;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        applyTypeFace(ds);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyTypeFace(paint);
    }

    @SuppressWarnings("deprecation")
    private void applyTypeFace(Paint paint) {
        paint.setTypeface(typeface);
        paint.setTextSize(Common.getInstance().dpToPx(size));
        if (color != 0) {
            paint.setColor(context.getResources().getColor(color));
        }
    }
}
