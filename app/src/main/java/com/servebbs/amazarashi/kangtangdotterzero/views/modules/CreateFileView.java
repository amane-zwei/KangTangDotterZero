package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.servebbs.amazarashi.kangtangdotterzero.domains.files.Extension;
import com.servebbs.amazarashi.kangtangdotterzero.domains.files.KTDZFile;

public class CreateFileView extends LinearLayout {
    private final String directoryPath;
    private final EditText fileNameView;

    public CreateFileView(Context context) {
        super(context);

        directoryPath = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .getPath();

        setOrientation(LinearLayout.VERTICAL);

        {
            TextView text = new TextView(context);
            text.setText(directoryPath);
            text.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            addView(text);
        }
        {
            EditText editText = this.fileNameView = new EditText(context);
            editText.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            addView(editText);
        }
    }

    public KTDZFile getFile() {
        String fileName = fileNameView.getText().toString();
        if (fileName == null || fileName.length() < 1) {
            return null;
        }
        return new KTDZFile(
                directoryPath,
                fileName,
                Extension.KTDZ_PROJECT
        );
    }
}
