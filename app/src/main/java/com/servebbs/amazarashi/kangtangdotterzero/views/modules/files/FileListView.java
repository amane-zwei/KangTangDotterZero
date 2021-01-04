package com.servebbs.amazarashi.kangtangdotterzero.views.modules.files;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.files.FileData;
import com.servebbs.amazarashi.kangtangdotterzero.models.files.KTDZFile;

import java.util.List;

public class FileListView extends ListView {

    public FileListView(Context context) {
        super(context);

        setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        TextView empty = new TextView(context);
        empty.setText("no files");
        setEmptyView(empty);
    }

    public KTDZFile getFile() {
        return ((FileData) getAdapter().getItem(getCheckedItemPosition()));
    }

    public interface OnFileClickListener {
        void onClick(int position, FileData fileData);
    }

    public static class FileViewAdapter extends BaseAdapter {

        private OnFileClickListener onFileClickListener;
        private final List<FileData> fileList;

        public FileViewAdapter(List<FileData> fileList) {
            super();
            this.fileList = fileList;
        }

        public FileViewAdapter setOnFileClickListener(OnFileClickListener onFileClickListener) {
            this.onFileClickListener = onFileClickListener;
            return this;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            FileData fileData = fileList.get(position);

            FileItemView itemView;
            if (convertView == null) {
                final int margin = ScreenSize.getMargin();
                itemView = new FileItemView(parent.getContext());
                itemView.setPadding(margin, margin, margin, margin);
                itemView.setOnClickListener((View view) -> {
                    if (onFileClickListener != null) {
                        onFileClickListener.onClick(position, fileData);
                    }
                });
            } else {
                itemView = (FileItemView) convertView;
            }

            itemView.attacheFileData(fileData);
            if (!fileData.isRequestLoad()) {
                fileData.loadThumbnail((Bitmap bitmap) -> {
                    FileItemView tmpItemView = ((FileItemView) parent.getChildAt(position));
                    tmpItemView.attacheThumbnail(bitmap);
                });
            } else {
                itemView.attacheThumbnail(fileData.getThumbnail());
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
}
