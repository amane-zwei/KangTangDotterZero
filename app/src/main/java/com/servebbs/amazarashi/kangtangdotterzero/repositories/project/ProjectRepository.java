package com.servebbs.amazarashi.kangtangdotterzero.repositories.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.ColorList;
import com.servebbs.amazarashi.kangtangdotterzero.models.files.Extension;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Palette;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ProjectRepository extends FileRepository {
    private static final Charset charset = Charset.forName("UTF-8");

    private final String thumbnailFileName = "thumbnail.png";
    private final String projectFileName = "project.json";
    private final String paletteFileName = "palette.json";
    private final String imagesDirectoryName = "images/";

    @Override
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

    @Override
    public Project load(InputStream inputStream) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);

        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
        objectMapper.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);

        Project project = null;
        Palette palette = null;
        Map<String, Bitmap> bitmaps = new HashMap<>();

        BitmapFactory.Options options = new  BitmapFactory.Options();
        options.inMutable = true;

        // get project data
        ZipEntry zipEntry = null;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            String fileName = zipEntry.getName();

            if (projectFileName.equals(fileName)) {
                project = objectMapper.readValue(zipInputStream, Project.class);
            } else if (paletteFileName.equals(fileName)) {
                palette = new Palette(objectMapper.readValue(zipInputStream, ColorList.class));
            } else if (fileName.startsWith(imagesDirectoryName) && !zipEntry.isDirectory()) {
                bitmaps.put(extractFileName(fileName), BitmapFactory.decodeStream(zipInputStream, null, options));
            }
            zipInputStream.closeEntry();
        }

        if (project == null) {
            throw new IOException("project not found.");
        }
        if (palette == null) {
            palette = Palette.createDefault();
        }

        zipInputStream.close();
        return project.restore(palette, bitmaps);
    }

    private void putFile(ZipOutputStream zipOutputStream, String fileName, OutputConsumer consumer) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOutputStream.putNextEntry(zipEntry);
        consumer.accept(zipOutputStream);
    }

    private String extractFileName(String src) {
        return src.substring(src.lastIndexOf('/') + 1, src.lastIndexOf('.'));
    }

    private interface OutputConsumer {
        void accept(ZipOutputStream zipOutputStream) throws IOException;
    }
}
