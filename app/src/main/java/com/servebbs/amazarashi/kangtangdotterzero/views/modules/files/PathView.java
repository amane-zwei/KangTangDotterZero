package com.servebbs.amazarashi.kangtangdotterzero.views.modules.files;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PathView extends LinearLayout {
    @Getter
    private final File root;

    private final List<String> directories;

    private OnPathSelectedListener onPathSelectedListener;

    public PathView(Context context) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);

        root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        directories = new ArrayList<>();
        addDirectory(root.getPath());
    }

    public PathView setPathSelectedListener(OnPathSelectedListener onPathSelectedListener) {
        this.onPathSelectedListener = onPathSelectedListener;
        return this;
    }

    public void addDirectory(String directoryName) {
        final int margin = - ScreenSize.getDotSize();

        DirectoryView view = new DirectoryView(getContext());
        view.setText(directoryName);
        view.setIndex(directories.size());
        view.setOnClickListener(this::onClick);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ScreenSize.getIconSize() / 2
        );
        params.rightMargin = margin;
        view.setLayoutParams(params);
        addView(view);

        directories.add(directoryName);
    }

    private void onClick(View view) {
        int index = ((DirectoryView) view).getIndex();

        for (int lastIndex = getChildCount() - 1; lastIndex > index; lastIndex--) {
            directories.remove(lastIndex);
            removeViewAt(lastIndex);
        }
        if (onPathSelectedListener != null) {
            onPathSelectedListener.onSelected(createPath());
        }
    }

    public File createPath() {
        StringBuilder builder = new StringBuilder();
        for (int index = 1; index < directories.size(); index++) {
            builder.append(directories.get(index));
        }
        return new File(root, builder.toString());
    }

    private static class DirectoryView extends androidx.appcompat.widget.AppCompatTextView {

        private final static Paint paint = new Paint();
        private final static Rect srcLeft;
        private final static Rect srcMiddle;
        private final static Rect srcRight;

        static {
            final DotIcon.DotIconData dotIconData = DotIcon.breadcrumb;
            final int left = dotIconData.getLeft();
            final int top = dotIconData.getTop();
            final int bottom = dotIconData.getBottom();
            final int width = dotIconData.getWidth();
            final int subWidth = dotIconData.getHeight() / 2;
            srcLeft = new Rect(left, top, left + subWidth, bottom);
            srcMiddle = new Rect(left + subWidth, top, left + width - subWidth, bottom);
            srcRight = new Rect(left + width - subWidth, top, left + width, bottom);
        }

        @Getter
        @Setter
        private int index;

        private Rect left;
        private Rect middle;
        private Rect right;

        public DirectoryView(Context context) {
            super(context);
            final int padding = ScreenSize.getDotSize() * 6;
            setPaddingRelative(padding, - ScreenSize.getDotSize() / 2, 0, 0);
            setGravity(Gravity.CENTER_VERTICAL);
            setTextColor(0xff000000);
            setTextSize(ScreenSize.getIconSize() / 4);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            final int subWidth = ScreenSize.getDotSize() * 4;
            final int width = getMeasuredWidth() + subWidth + ScreenSize.getDotSize() * 2;
            final int height = getMeasuredHeight();

            setMeasuredDimension(width, height);

            left = new Rect(0, 0, subWidth, height);
            middle = new Rect(subWidth, 0, width - subWidth, height);
            right = new Rect(width - subWidth, 0, width, height);
        }

        @Override
        public void onDraw(Canvas canvas) {
            final Bitmap bitmap = DotIcon.getBitmap();

            canvas.drawBitmap(bitmap, srcLeft, left, paint);
            canvas.drawBitmap(bitmap, srcMiddle, middle, paint);
            canvas.drawBitmap(bitmap, srcRight, right, paint);

            super.onDraw(canvas);
        }
    }

    public interface OnPathSelectedListener {
        void onSelected(File path);
    }
}
