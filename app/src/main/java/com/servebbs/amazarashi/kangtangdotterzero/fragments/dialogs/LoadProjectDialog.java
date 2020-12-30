package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.servebbs.amazarashi.kangtangdotterzero.MainActivity;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.files.Extension;
import com.servebbs.amazarashi.kangtangdotterzero.models.files.KTDZFile;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

public class LoadProjectDialog extends KTDZDialogFragment {
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private ContentView contentView = null;

    public LoadProjectDialog() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ((MainActivity) getContext()).addPermissionRequest((int requestCode, String[] permissions, int[] grantResults) -> {
                if (requestCode == REQUEST_EXTERNAL_STORAGE) {
                    if (grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        dismiss();
                    }
                    return true;
                }
                return false;
            });
            ActivityCompat.requestPermissions(
                    (AppCompatActivity) getContext(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        Dialog dialog = super.onCreateDialog(bundle);
        setButtonEnabled(KTDZDialogFragment.BUTTON_POSITIVE, false);
        return dialog;
    }

    public LoadProjectDialog setOnPositive(LoadProjectDialog.OnPositiveButtonListener onPositive) {
        setOnPositiveButton(() -> {
            Project project = loadFile();
            if (project == null) {
                return false;
            }
            return onPositive.onPositiveButton(project);
        });
        return this;
    }

    @Override
    public View createContentView(Context context) {
        return this.contentView = new ContentView(context).findFiles();
    }

    private Project loadFile() {
        KTDZFile ktdzFile = contentView.getFile();
        return loadFile(ktdzFile, ktdzFile.translateToFile());
    }

    private Project loadFile(KTDZFile ktdzFile, File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            return ktdzFile.getExtension().getRepository().load(inputStream);
        } catch (IOException e) {
            return null;
        }
    }

    public static List<String> findFileList(File path) {
        List<String> result = new ArrayList<>();
        Collections.addAll(result, path.list());
        return result;
    }

    public void onSelected() {
        setButtonEnabled(KTDZDialogFragment.BUTTON_POSITIVE, true);
    }

    @FunctionalInterface
    public interface OnPositiveButtonListener {
        boolean onPositiveButton(Project project);
    }

    public class ContentView extends LinearLayout {

        private final PathView pathView;
        private final FileListView fileListView;

        public ContentView(Context context) {
            super(context);

            final int iconSize = ScreenSize.getIconSize();
            final int padding = ScreenSize.getPadding();

            setOrientation(LinearLayout.VERTICAL);
            setBackgroundColor(0xfff0f0f0);

            {
                PathView pathView = this.pathView = new PathView(context);
//                pathView.setPadding(padding, padding, padding, padding);

                LayoutParams layoutParams = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(padding, padding, padding, padding);
                pathView.setLayoutParams(layoutParams);
                addView(pathView);
            }
            {
                FileListView fileListView = this.fileListView = new FileListView(context);
//                pathView.setPadding(padding, padding, padding, padding);

                LayoutParams layoutParams = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        1
                );
                fileListView.setLayoutParams(layoutParams);
                addView(fileListView);
            }
        }

        public ContentView findFiles() {
            fileListView.setAdapter(new FileViewAdapter(findFileList(pathView.getPath())));
            return this;
        }

        public KTDZFile getFile() {
            return fileListView.getFile(pathView.getPath().getPath());
        }
    }

    private static class PathView extends LinearLayout {
        @Getter
        private final File path;

        public PathView(Context context) {
            super(context);

            setOrientation(LinearLayout.HORIZONTAL);

            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            add(path.getPath());
        }

        public void add(String directoryName) {
            TextView view = new TextView(getContext());
            view.setText(directoryName);
            addView(view);
        }
    }

    private static class FileListView extends ListView {
        public FileListView(Context context) {
            super(context);

            setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        public KTDZFile getFile(String directoryPath) {
            String fileName = ((String) getAdapter().getItem(getCheckedItemPosition()));
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            return new KTDZFile(directoryPath, fileName, Extension.KTDZ_PROJECT);
        }
    }

    private class FileViewAdapter extends BaseAdapter {

        private final List<String> fileList;

        private FileViewAdapter(List<String> fileList) {
            super();
            this.fileList = fileList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ListItemView itemView;
            if (convertView == null) {
//                final int iconSize = ScreenSize.getIconSize();
                itemView = new ListItemView(getContext());
            } else {
                itemView = (ListItemView) convertView;
            }

            itemView.getTextView().setText(fileList.get(position));

            return itemView;
        }

        @Override
        public int getCount() {
            return fileList.size();
        }

        @Override
        public Object getItem(int position) {
            return fileList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private class ListItemView extends LinearLayout implements Checkable {
        @Getter
        private final TextView textView;

        @Getter
        private boolean checked;

        public ListItemView(Context context) {
            super(context);

            setOrientation(LinearLayout.HORIZONTAL);

            addView(this.textView = new TextView(context));

            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_checked}, new ColorDrawable(0xffffa0a0));
            stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, new ColorDrawable(0x00000000));
            setBackground(stateListDrawable);
        }

        @Override
        public void setChecked(boolean checked) {
            if (this.checked != checked) {
                this.checked = checked;
                refreshDrawableState();
                if (checked) {
                    onSelected();
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
    }
}
