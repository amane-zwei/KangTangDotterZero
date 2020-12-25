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

    public String toFileName() {
        return name + extension;
    }

    public File translateToFile() {
        return new File(directoryPath, toFileName());
    }
}
