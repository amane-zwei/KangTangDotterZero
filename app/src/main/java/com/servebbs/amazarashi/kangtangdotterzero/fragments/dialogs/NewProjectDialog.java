package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.content.Context;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.files.ProjectColorView;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.files.ProjectSizeView;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.setting.SettingView;

public class NewProjectDialog extends KTDZDialogFragment {

    private Project project = null;
    private ContentView contentView = null;

    @Override
    public String getTitle() {
        return "NEW";
    }

    public NewProjectDialog applyProject(Project project) {
        this.project = project;
        return this;
    }

    private Project createProject() {
        return Project.create(
                contentView.sizeView.getWidthValue(),
                contentView.sizeView.getHeightValue(),
                contentView.projectColorView.getBackgroundColor(),
                contentView.projectColorView.useIndexedColor());
    }

    @Override
    public View createContentView(Context context) {
        this.contentView = new ContentView(context);
        return contentView;
    }

    public NewProjectDialog setOnPositive(OnPositiveButtonListener onPositive) {
        setOnPositiveButton(() -> onPositive.onPositiveButton(createProject()));
        return this;
    }

    @FunctionalInterface
    public interface OnPositiveButtonListener {
        boolean onPositiveButton(Project project);
    }

    public class ContentView extends SettingView {

        private final ProjectSizeView sizeView;
        private final ProjectColorView projectColorView;

        public ContentView(Context context) {
            super(context);

            {
                ProjectSizeView sizeView = this.sizeView = new ProjectSizeView(context);
                sizeView.set(project.getWidth(), project.getHeight());
                addItem("SIZE", sizeView);
            }
            {
                ProjectColorView projectColorView = this.projectColorView = new ProjectColorView(context);
                projectColorView.set(project.getBackGroundColor(), project.isIndexedColor());
                addItem("COLOR", projectColorView);
            }
        }
    }
}
