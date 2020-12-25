package com.servebbs.amazarashi.kangtangdotterzero.repositories.project;

import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

import java.io.IOException;
import java.io.OutputStream;

public abstract class FileRepository {
    public abstract void save(Project project, OutputStream outputStream) throws IOException;

    protected void save(byte[] buffer, OutputStream outputStream) throws IOException {
        outputStream.write(buffer);
    }
}
