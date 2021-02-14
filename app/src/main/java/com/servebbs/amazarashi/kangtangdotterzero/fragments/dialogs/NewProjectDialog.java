package com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotRoundRectDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.KTDZDialogFragment;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.HeaderView;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.files.ProjectSizeView;

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
                contentView.sizeView.getHeightValue());
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

    public class ContentView extends LinearLayout {

        private final ProjectSizeView sizeView;

        public ContentView(Context context) {
            super(context);

            final int iconSize = ScreenSize.getIconSize();
            final int padding = ScreenSize.getPadding();

            setOrientation(LinearLayout.VERTICAL);

            {
                HeaderView headerView = new HeaderView(context);
                headerView.setText("SIZE");
                LayoutParams layoutParams = new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                headerView.setLayoutParams(layoutParams);
                addView(headerView);
            }
            {
                ProjectSizeView sizeView = this.sizeView = new ProjectSizeView(context);
                sizeView.set(project.getWidth(), project.getHeight());
                sizeView.setBackground(new DotRoundRectDrawable());
                sizeView.setPadding(
                        DotRoundRectDrawable.paddingLeft,
                        DotRoundRectDrawable.paddingTop,
                        DotRoundRectDrawable.paddingRight,
                        DotRoundRectDrawable.paddingBottom);

                LayoutParams layoutParams = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(padding, 0, padding, padding);
                sizeView.setLayoutParams(layoutParams);
                addView(sizeView);
            }
        }
    }
}
