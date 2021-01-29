package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.servebbs.amazarashi.kangtangdotterzero.MainActivity;
import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.files.FileData;
import com.servebbs.amazarashi.kangtangdotterzero.domains.files.KTDZFile;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotRoundRectDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.files.FileListView;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.files.PathView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        String[] fileNames = path.list();
        if (fileNames == null) {
            return new ArrayList<>();
        }

        List<FileData> directories = new ArrayList<>();
        List<FileData> files = new ArrayList<>();
        String directoryPath = path.getPath();

        for (String fileName : fileNames) {
            if (new File(path, fileName).isDirectory()) {
                directories.add(FileData.directory(directoryPath, fileName));
            } else {
                FileData data = FileData.file(directoryPath, fileName);
                if (data.getExtension() != null) {
                    files.add(data);
                }
            }
        }
        directories.addAll(files);
        return directories;
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
                pathView.setPathSelectedListener(this::findFiles);
                addView(pathView);
            }
            {
                FileListView fileListView = this.fileListView = new FileListView(context);
                fileListView.setBackground(new DotRoundRectDrawable(0xffffffff, 0xff000000, 0x40000000));
                fileListView.setPadding(
                        DotRoundRectDrawable.paddingLeft,
                        DotRoundRectDrawable.paddingTop,
                        DotRoundRectDrawable.paddingRight,
                        DotRoundRectDrawable.paddingBottom);

                LayoutParams layoutParams = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        1
                );
                layoutParams.leftMargin = ScreenSize.getDotSize();
                fileListView.setLayoutParams(layoutParams);
                addView(fileListView);
            }
        }

        public ContentView findFiles() {
            return findFiles(pathView.getRoot());
        }

        public ContentView findFiles(File path) {
            fileListView.setAdapter(
                    new FileListView.FileViewAdapter(findFileList(path))
                            .setOnFileClickListener(this::onFileClick));
            return this;
        }

        public KTDZFile getFile() {
            return fileListView.getFile();
        }

        public void onFileClick(int position, FileData fileData) {
            if (fileData.isDirectory()) {
                pathView.addDirectory(fileData.getName());
                findFiles(fileData.translateToFile());
            } else {
                fileListView.setItemChecked(position, true);
                setButtonEnabled(KTDZDialogFragment.BUTTON_POSITIVE, true);
            }
        }
    }
}
