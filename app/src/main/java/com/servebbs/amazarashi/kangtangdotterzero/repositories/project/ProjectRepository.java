package com.servebbs.amazarashi.kangtangdotterzero.repositories.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.servebbs.amazarashi.kangtangdotterzero.domains.bitmap.ColorList;
import com.servebbs.amazarashi.kangtangdotterzero.domains.files.Extension;
import com.servebbs.amazarashi.kangtangdotterzero.domains.histories.History;
import com.servebbs.amazarashi.kangtangdotterzero.domains.histories.HistoryList;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Palette;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ProjectRepository extends FileRepository {
    private final String thumbnailFileName = "thumbnail.png";
    private final String projectFileName = "project.json";
    private final String paletteFileName = "palette.json";
    private final String historyFileName = "history.json";
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
                tmpStream -> tmpStream.write(objectMapper.writeValueAsBytes(project)));

        // put palette
        putFile(zipOutputStream, paletteFileName,
                tmpStream -> tmpStream.write(objectMapper.writeValueAsBytes(project.getPalette())));

        // put history
        putFile(zipOutputStream, historyFileName,
                tmpStream -> tmpStream.write(objectMapper.writeValueAsBytes(project.getHistory().toArray())));

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
        HistoryList history = new HistoryList();
        Map<String, Bitmap> bitmaps = new HashMap<>();

        BitmapFactory.Options options = new  BitmapFactory.Options();
        options.inMutable = true;

        // get project data
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            String fileName = zipEntry.getName();

            if (projectFileName.equals(fileName)) {
                project = objectMapper.readValue(zipInputStream, Project.class);
            } else if (paletteFileName.equals(fileName)) {
                palette = new Palette(objectMapper.readValue(zipInputStream, ColorList.class));
            } else if (historyFileName.equals(fileName)) {
                history.setHistory(objectMapper.readValue(zipInputStream, History[].class));
            } else if (fileName.startsWith(imagesDirectoryName) && !zipEntry.isDirectory()) {
                bitmaps.put(extractFileName(fileName), BitmapFactory.decodeStream(zipInputStream, null, options));
            }
            zipInputStream.closeEntry();
        }
        zipInputStream.close();

        if (project == null) {
            throw new IOException("project not found.");
        }
        return project.restore(palette, history, bitmaps);
    }

    @Override
    public Bitmap loadThumbnail(InputStream inputStream) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            String fileName = zipEntry.getName();

            if (thumbnailFileName.equals(fileName)) {
                Bitmap result = BitmapFactory.decodeStream(zipInputStream);
                zipInputStream.close();
                return result;
            }
            zipInputStream.closeEntry();
        }
        zipInputStream.close();
        return null;
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
