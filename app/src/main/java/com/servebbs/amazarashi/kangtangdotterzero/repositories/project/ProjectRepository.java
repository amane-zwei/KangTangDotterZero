package com.servebbs.amazarashi.kangtangdotterzero.repositories.project;

import android.graphics.Bitmap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.servebbs.amazarashi.kangtangdotterzero.models.files.Extension;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ProjectRepository extends FileRepository {
    private static final Charset charset = Charset.forName("UTF-8");

    private final String thumbnailFileName = "thumbnail.png";
    private final String projectFileName = "project.json";
    private final String paletteFileName = "palette.json";
    private final String imagesDirectoryName = "images/";

    public void save(Project project, OutputStream outputStream) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // put thumbnail
        putFile(zipOutputStream, thumbnailFileName,
                tmpStream -> project.renderBitmap().compress(Bitmap.CompressFormat.PNG, 100, tmpStream));

        // put project data
        putFile(zipOutputStream, projectFileName,
                tmpStream -> {
                    String json = objectMapper.writeValueAsString(project);
                    tmpStream.write(json.getBytes(charset));
                });

        // put palette
        putFile(zipOutputStream, paletteFileName,
                tmpStream -> {
                    String json = objectMapper.writeValueAsString(project.getPalette());
                    tmpStream.write(json.getBytes(charset));
                });

        // put layer bitmaps
        for (Layer layer : project.layers()) {
            putFile(zipOutputStream,
                    imagesDirectoryName + layer.getId() + Extension.PNG,
                    tmpStream -> {
                        Bitmap bitmap = project.isIndexedColor()
                                ? layer.getIndexed().getBitmap()
                                : layer.getDisplay();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, tmpStream);
                    });
        }

        zipOutputStream.close();
    }

    private void putFile(ZipOutputStream zipOutputStream, String fileName, Consumer consumer) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOutputStream.putNextEntry(zipEntry);
        consumer.accept(zipOutputStream);
    }

    private interface Consumer {
        void accept(ZipOutputStream zipOutputStream) throws IOException;
    }
}
