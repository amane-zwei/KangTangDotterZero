package com.servebbs.amazarashi.kangtangdotterzero.views.modules.files;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.files.FileData;
import com.servebbs.amazarashi.kangtangdotterzero.models.files.KTDZFile;

import java.util.List;

public class FileListView extends ListView {

    public FileListView(Context context) {
        super(context);

        setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public KTDZFile getFile() {
        return ((FileData) getAdapter().getItem(getCheckedItemPosition()));
    }

    public static class FileViewAdapter extends BaseAdapter {

        private FileItemView.OnSelectedListener onSelectedListener;
        private final List<FileData> fileList;

        public FileViewAdapter(List<FileData> fileList) {
            super();
            this.fileList = fileList;
        }

        public FileViewAdapter setOnSelectedListener(FileItemView.OnSelectedListener onSelectedListener) {
            this.onSelectedListener = onSelectedListener;
            return this;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            FileItemView itemView;
            if (convertView == null) {
                final int margin = ScreenSize.getMargin();
                itemView = new FileItemView(parent.getContext());
                itemView.setPadding(margin, margin, margin, margin);
                itemView.setOnSelectedListener(onSelectedListener);
            } else {
                itemView = (FileItemView) convertView;
            }

            FileData fileData = fileList.get(position);
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
