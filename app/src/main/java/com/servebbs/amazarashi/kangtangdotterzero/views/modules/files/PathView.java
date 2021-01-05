package com.servebbs.amazarashi.kangtangdotterzero.views.modules.files;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;

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
        final int margin = ScreenSize.getDotSize();

        directoryName = directoryName + "/";
        DirectoryView view = new DirectoryView(getContext());
        view.setText(directoryName);
        view.setIndex(directories.size());
        view.setOnClickListener(this::onClick);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.leftMargin = margin;
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
        @Getter
        @Setter
        private int index;

        public DirectoryView(Context context) {
            super(context);
        }
    }

    public interface OnPathSelectedListener {
        void onSelected(File path);
    }
}
