package com.servebbs.amazarashi.kangtangdotterzero.repositories.project;

import android.graphics.Bitmap;

import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class FileRepository {
    public abstract void save(Project project, OutputStream outputStream) throws IOException;

    public abstract Project load(InputStream inputStream) throws IOException;

    public abstract Bitmap loadThumbnail(InputStream inputStream) throws IOException;

    protected void save(byte[] buffer, OutputStream outputStream) throws IOException {
        outputStream.write(buffer);
    }
}
