package com.servebbs.amazarashi.kangtangdotterzero.domains.files;

import com.servebbs.amazarashi.kangtangdotterzero.repositories.project.FileRepository;
import com.servebbs.amazarashi.kangtangdotterzero.repositories.project.PngRepository;
import com.servebbs.amazarashi.kangtangdotterzero.repositories.project.ProjectRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Extension {
    PNG(".png", "image/png", new PngRepository(), true),
    KTDZ_PROJECT(".ktd", "image/prs.ktd+zip", new ProjectRepository(), false);

    private final String extension;
    @Getter
    private final String mimeType;
    @Getter
    private final FileRepository repository;
    @Getter
    private final boolean needRegister;

    public String toString() {
        return extension;
    }

    public static Extension fromExtension(String extension) {
        for (Extension element : values()) {
            if (element.extension.equals(extension)) {
                return element;
            }
        }
        return null;
    }
}
