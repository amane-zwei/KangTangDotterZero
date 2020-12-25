package com.servebbs.amazarashi.kangtangdotterzero.repositories.project;

import android.graphics.Bitmap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.servebbs.amazarashi.kangtangdotterzero.models.files.Extension;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ProjectRepository extends FileRepository {
    public void save(Project project, OutputStream outputStream) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        {
            ZipEntry zipEntry = new ZipEntry("thumbnail.png");
            zipOutputStream.putNextEntry(zipEntry);
            project.renderBitmap().compress(Bitmap.CompressFormat.PNG, 100, zipOutputStream);
        }
        {
            ZipEntry zipEntry = new ZipEntry("project.json");
            zipOutputStream.putNextEntry(zipEntry);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            zipOutputStream.write(objectMapper.writeValueAsBytes(project));
        }
        for (Layer layer : project.layers()) {
            ZipEntry zipEntry = new ZipEntry("images/" + layer.getId() + Extension.PNG);
            zipOutputStream.putNextEntry(zipEntry);
            layer.getDisplay().compress(Bitmap.CompressFormat.PNG, 100, zipOutputStream);

            if (!layer.isIndexedColor()) {
                continue;
            }
            ZipEntry zipEntry_indexed = new ZipEntry("images-i/" + layer.getId() + Extension.PNG);
            zipOutputStream.putNextEntry(zipEntry_indexed);
            layer.getIndexed().getBitmap().compress(Bitmap.CompressFormat.PNG, 100, zipOutputStream);
        }

        zipOutputStream.close();
    }
}
