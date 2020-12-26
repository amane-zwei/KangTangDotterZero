package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.content.Context;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;

public class ConfirmDialog extends KTDZDialogFragment {
    private String message;
    private ContentView contentView;

    public ConfirmDialog() {
        super();
    }

    public ConfirmDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public View createContentView(Context context) {
        return this.contentView = new ContentView(context).setMessage(message);
    }

    public ConfirmDialog setOnPositive(OnButtonFunction onPositiveButton) {
        setOnPositiveButton(onPositiveButton);
        return this;
    }

    public static class ContentView extends androidx.appcompat.widget.AppCompatTextView {

        public ContentView(Context context) {
            super(context);

            setBackgroundColor(0xffffffff);
        }

        public ContentView setMessage(String message) {
            setText(message);
            return this;
        }
    }

}
