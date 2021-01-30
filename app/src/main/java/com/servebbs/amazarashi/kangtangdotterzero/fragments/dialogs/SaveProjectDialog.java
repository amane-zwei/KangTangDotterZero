package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.servebbs.amazarashi.kangtangdotterzero.MainActivity;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DividerDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.files.KTDZFile;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.CreateFileView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import lombok.Getter;

public class SaveProjectDialog extends KTDZDialogFragment {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Getter
    private Project project = null;
    private SaveProjectDialogView contentView = null;

    public SaveProjectDialog() {
        super();
        setOnPositiveButton(this::saveFile);
    }

    @Override
    public String getTitle() {
        return "SAVE";
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
        return super.onCreateDialog(bundle);
    }

    public SaveProjectDialog attachProject(Project project) {
        this.project = project;
        return this;
    }

    public boolean saveFile() {
        KTDZFile ktdzFile = contentView.createFileView.getFile();
        if (ktdzFile == null) {
            return false;
        }

        File file = ktdzFile.translateToFile();
        if (file.exists()) {
            new ConfirmDialog()
                    .setMessage("the file is exist.")
                    .setOnPositive(() -> {
                        saveFile(ktdzFile, file);
                        dismiss();
                        return true;
                    })
                    .show(((AppCompatActivity) getContext()).getSupportFragmentManager(), "action_confirm_overwrite");
            return false;
        } else {
            return saveFile(ktdzFile, file);
        }
    }

    private boolean saveFile(KTDZFile ktdzFile, File file) {
        try (OutputStream outputStream = new FileOutputStream(file)){
            ktdzFile.getExtension().getRepository().save(project, outputStream);
        } catch (IOException e) {
            return false;
        }

        if (ktdzFile.getExtension().isNeedRegister()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, ktdzFile.toFileName());
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, ktdzFile.getExtension().getMimeType());
            contentValues.put(MediaStore.Images.Media.DATA, file.getPath());

            getContext().getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
        return true;
    }

    @Override
    public View createContentView(Context context) {
        this.contentView = new SaveProjectDialogView(context);
        return contentView;
    }

    public static class SaveProjectDialogView extends LinearLayout {

        private final CreateFileView createFileView;

        public SaveProjectDialogView(Context context) {
            super(context);

            final int iconSize = ScreenSize.getIconSize();
            final int padding = ScreenSize.getPadding();

            setOrientation(LinearLayout.VERTICAL);
            setBackgroundColor(0xfff0f0f0);

            {
                CreateFileView createFileView = this.createFileView = new CreateFileView(context);
                createFileView.setPadding(padding, padding, padding, padding);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(padding, padding, padding, padding);
                createFileView.setLayoutParams(layoutParams);
                addView(createFileView);
            }
            {
                View divider = new View(context);
                DividerDrawable drawable = new DividerDrawable(0xffa0a0ff);
                divider.setBackground(drawable);
                LayoutParams layoutParams = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        iconSize / 4
                );
                layoutParams.setMargins(padding * 2, 0, padding * 2, 0);
                divider.setLayoutParams(layoutParams);
                addView(divider);
            }
        }
    }

}
