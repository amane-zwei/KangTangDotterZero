package com.servebbs.amazarashi.kangtangdotterzero.models.files;

import com.servebbs.amazarashi.kangtangdotterzero.repositories.project.FileRepository;
import com.servebbs.amazarashi.kangtangdotterzero.repositories.project.PngRepository;
import com.servebbs.amazarashi.kangtangdotterzero.repositories.project.ProjectRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Extension {
    PNG(".png", "image/png", new PngRepository()),
    KTDZ_PROJECT(".ktd", "image/prs.ktd+zip", new ProjectRepository());

    private final String extension;
    @Getter
    private final String mimeType;
    @Getter
    private final FileRepository repository;

    public String toString() {
        return extension;
    }
}
