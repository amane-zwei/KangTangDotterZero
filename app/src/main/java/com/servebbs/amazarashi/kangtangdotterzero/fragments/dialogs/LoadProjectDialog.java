package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
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
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.ThumbnailView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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

    public static List<FileData> findFileList(File path) {
        List<FileData> result = new ArrayList<>();
        String directoryPath = path.getPath();
        for (String fileName : path.list()) {
            if (new File(path, fileName).isDirectory()) {
                result.add(FileData.directory(directoryPath, fileName));
            } else {
                FileData data = FileData.file(directoryPath, fileName);
                if (data.getExtension() != null) {
                    result.add(data);
                }
            }
        }
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
            return fileListView.getFile();
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

        public KTDZFile getFile() {
            return ((FileData) getAdapter().getItem(getCheckedItemPosition()));
        }
    }

    private class FileViewAdapter extends BaseAdapter {

        private final List<FileData> fileList;

        private FileViewAdapter(List<FileData> fileList) {
            super();
            this.fileList = fileList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ListItemView itemView;
            if (convertView == null) {
                final int margin = ScreenSize.getMargin();
                itemView = new ListItemView(getContext());
                itemView.setPadding(margin, margin, margin, margin);
            } else {
                itemView = (ListItemView) convertView;
            }

            FileData fileData = fileList.get(position);
            itemView.attacheFileData(fileData);
            if (!fileData.isRequestLoad()) {
                fileData.loadThumbnail((Bitmap bitmap) -> {
                    ListItemView tmpItemView = ((ListItemView)parent.getChildAt(position));
                    tmpItemView.attacheThumbnail(bitmap);
                });
            } else {
                itemView.attacheThumbnail(fileData.thumbnail);
            }

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
        private FileData fileData;

        private final ThumbnailView thumbnail;
        private final TextView textView;

        @Getter
        private boolean checked;

        public ListItemView(Context context) {
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
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
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

        public ListItemView attacheFileData(FileData fileData) {
            this.fileData = fileData;
            this.thumbnail.setExtension(fileData.isDirectory, fileData.getExtension());

            if (fileData.isDirectory) {
                textView.setText(fileData.getName());
            } else {
                textView.setText(fileData.toFileName());
            }
            return this;
        }

        public ListItemView attacheThumbnail(Bitmap bitmap) {
            thumbnail.setBitmap(bitmap);
            thumbnail.invalidate();
            return this;
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

    private static class LoadBitmapTask extends AsyncTask<KTDZFile, Void, Bitmap> {
        private OnLoadedBitmapListener onLoaded;

        public LoadBitmapTask setOnLoaded(OnLoadedBitmapListener onLoaded) {
            this.onLoaded = onLoaded;
            return this;
        }

        @Override
        protected Bitmap doInBackground(KTDZFile... files) {
            KTDZFile file = files[0];
            try (InputStream inputStream = new FileInputStream(file.translateToFile())) {
                return file.getExtension().getRepository().loadThumbnail(inputStream);
            } catch (IOException e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            onLoaded.onLoadedBitmap(result);
        }
    }

    private interface OnLoadedBitmapListener {
        void onLoadedBitmap(Bitmap bitmap);
    }

    private static class FileData extends KTDZFile {
        private FileData(String directoryPath, String fileName) {
            super(directoryPath, fileName);
        }
        private FileData(String directoryPath, String fileName, Extension extension) {
            super(directoryPath, fileName, extension);
        }
        public static FileData file(String directoryPath, String fileName) {
            FileData result = new FileData(directoryPath, fileName);
            result.isDirectory = false;
            result.thumbnail = null;
            return result;
        }
        public static FileData directory(String directoryPath, String fileName) {
            FileData result = new FileData(directoryPath, fileName, null);
            result.isDirectory = true;
            result.thumbnail = null;
            return result;
        }

        public void loadThumbnail(OnLoadedBitmapListener onLoaded) {
            if (isDirectory || requestLoad) {
                return;
            }
            new LoadBitmapTask()
                    .setOnLoaded((Bitmap bitmap) -> {
                        this.thumbnail = bitmap;
                        onLoaded.onLoadedBitmap(bitmap);
                    })
                    .execute(this);
            requestLoad = true;
        }

        @Getter
        private boolean isDirectory;
        @Getter
        @Setter
        private Bitmap thumbnail;
        @Getter
        private boolean requestLoad = false;
    }
}
