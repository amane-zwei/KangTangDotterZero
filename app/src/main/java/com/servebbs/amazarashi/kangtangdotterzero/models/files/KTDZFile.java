package com.servebbs.amazarashi.kangtangdotterzero.models.files;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KTDZFile {
    private final String directoryPath;
    private final String name;
    private final Extension extension;

    public KTDZFile(String directoryPath, String fileName) {
        this.directoryPath = directoryPath;
        int index = fileName.lastIndexOf('.');
        name = fileName.substring(0, index);
        extension = Extension.fromExtension(fileName.substring(index));
    }

    public String toFileName() {
        return name + extension;
    }

    public File translateToFile() {
        return new File(directoryPath, toFileName());
    }
}
