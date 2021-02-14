package com.servebbs.amazarashi.kangtangdotterzero.views.drawer;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotFont;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotIconDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotTextView;

public class DrawerItemView extends DotTextView {
    private static final int padding = ScreenSize.getDotSize() * 2;
    private static final int indent = ScreenSize.getIconSize() / 2;

    public DrawerItemView(Context context) {
        super(context);

        setTextColor(0xff000000);
        setBackgroundColor(0xffffffff);

        setCompoundDrawablePadding(padding);
    }

    public void setLevel(int level) {
        setPadding(level * indent, padding, 0, padding);
    }

    public static class ChildItemView extends DrawerItemView {
        private final DotIconDrawable drawable;

        public ChildItemView(Context context) {
            super(context);

            setPadding(padding + indent, padding, padding, padding);

            DotIconDrawable drawable = this.drawable = new DotIconDrawable();
            drawable.setBounds(0, 0, DotFont.normalHeight, DotFont.normalHeight);
            setCompoundDrawables(drawable, null, null, null);
        }

        public void setIcon(DotIcon.DotIconData dotIconData) {
            drawable.setSrcRect(dotIconData);
        }
    }

    public static class GroupItemView extends DrawerItemView {
        private final GroupIndicatorDrawable indicator;

        public GroupItemView(Context context) {
            super(context);

            setPadding(padding, padding, padding, padding);

            GroupIndicatorDrawable indicator = this.indicator = new GroupIndicatorDrawable();
            indicator.setBounds(0, 0, DotFont.normalHeight, DotFont.normalHeight);
            setCompoundDrawables(indicator, null, null, null);
        }

        public void setExpanded(boolean expanded) {
            indicator.setExpanded(expanded);
        }
    }

    private static class GroupIndicatorDrawable extends DotIconDrawable {
        public void setExpanded(boolean isExpanded) {
            if (isExpanded) {
                DotIcon.groupIndicator.setRect(getSrc(), 0, DotIcon.groupIndicator.getHeight());
            } else {
                setSrcRect(DotIcon.groupIndicator);
            }
        }
    }
}
