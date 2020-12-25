package com.servebbs.amazarashi.kangtangdotterzero.repositories.project;

import android.graphics.Bitmap;

import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PngRepository extends FileRepository{
    public void save(Project project, OutputStream outputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        project.renderBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        save(byteArrayOutputStream.toByteArray(), outputStream);
    }
}
