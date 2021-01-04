package com.servebbs.amazarashi.kangtangdotterzero.views.modules.files;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.files.FileData;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ThumbnailView;

import lombok.Getter;
import lombok.Setter;

public class FileItemView extends LinearLayout implements Checkable {

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    @Getter
    private FileData fileData;

    private final ThumbnailView thumbnail;
    private final TextView textView;

    @Getter
    private boolean checked;

    @Setter
    private OnSelectedListener onSelectedListener;

    public FileItemView(Context context) {
        super(context);

        final int margin = ScreenSize.getDotSize() * 2;
        final int size = ScreenSize.getIconSize() / 2 + 2;

        setOrientation(LinearLayout.HORIZONTAL);

        {
            ThumbnailView thumbnailView = this.thumbnail = new ThumbnailView(context).setMargin(2);
            thumbnailView.setLayoutParams(new ViewGroup.LayoutParams(size, size));
            addView(thumbnailView);
        }
        {
            TextView textView = this.textView = new TextView(getContext());
            LayoutParams layoutParams = new LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1
            );
            layoutParams.leftMargin = margin;
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            textView.setLayoutParams(layoutParams);
            addView(textView);
        }

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, new ColorDrawable(0xffffa0a0));
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, new ColorDrawable(0x00000000));
        setBackground(stateListDrawable);
    }

    public FileItemView attacheFileData(FileData fileData) {
        this.fileData = fileData;
        this.thumbnail.setExtension(fileData.isDirectory(), fileData.getExtension());

        if (fileData.isDirectory()) {
            textView.setText(fileData.getName());
        } else {
            textView.setText(fileData.toFileName());
        }
        return this;
    }

    public FileItemView attacheThumbnail(Bitmap bitmap) {
        thumbnail.setBitmap(bitmap);
        thumbnail.invalidate();
        return this;
    }

    @Override
    public void setChecked(boolean checked) {
        if (this.checked != checked) {
            this.checked = checked;
            refreshDrawableState();
            if (checked && onSelectedListener != null) {
                onSelectedListener.onSelected(fileData);
            }
        }
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    public interface OnSelectedListener {
        void onSelected(FileData fileData);
    }
}
