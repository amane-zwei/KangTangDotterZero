package com.servebbs.amazarashi.kangtangdotterzero.domains.files;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

public class FileData extends KTDZFile {

    @Getter
    private boolean isDirectory;
    @Getter
    @Setter
    private Bitmap thumbnail;
    @Getter
    private boolean requestLoad = false;

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

    public void loadThumbnail(OnLoadedListener onLoaded) {
        if (isDirectory || requestLoad) {
            return;
        }
        new LoadBitmapTask()
                .setOnLoaded((Bitmap bitmap) -> {
                    this.thumbnail = bitmap;
                    onLoaded.onLoaded(bitmap);
                })
                .execute(this);
        requestLoad = true;
    }

    private static class LoadBitmapTask extends AsyncTask<KTDZFile, Void, Bitmap> {
        private OnLoadedListener onLoaded;

        public LoadBitmapTask setOnLoaded(OnLoadedListener onLoaded) {
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
            onLoaded.onLoaded(result);
        }
    }

    public interface OnLoadedListener {
        void onLoaded(Bitmap bitmap);
    }
}
