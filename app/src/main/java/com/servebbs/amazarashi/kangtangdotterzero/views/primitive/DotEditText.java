package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.servebbs.amazarashi.kangtangdotterzero.R;
import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;

public class DotEditText extends androidx.appcompat.widget.AppCompatEditText {

    public DotEditText(Context context) {
        super(context);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(ScreenSize.getIconSize()/16, 0xff000000);
        setBackground(gradientDrawable);
    }
}
