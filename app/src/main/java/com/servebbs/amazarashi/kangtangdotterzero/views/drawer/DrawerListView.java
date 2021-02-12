package com.servebbs.amazarashi.kangtangdotterzero.views.drawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.Action;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.CallLoadProjectDialogAction;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.CallSaveProjectDialogAction;

import lombok.Setter;

public class DrawerListView extends ExpandableListView {
    public static final Item[] items = {
            new Item("FILE", new Item[]{
                    new Item("NEW", new CallSaveProjectDialogAction()),
                    new Item("SAVE", new CallSaveProjectDialogAction()),
                    new Item("LOAD", new CallLoadProjectDialogAction())
            }),
            new Item("TEST", new Item[]{
                    new Item("SAMPLE", new CallSaveProjectDialogAction()),
                    new Item("SAMPLE", new CallSaveProjectDialogAction()),
            }),
    };

    @Setter
    private OnItemClick onItemClick = null;

    public DrawerListView(Context context) {
        super(context);

        setAdapter(new ListAdapter(context, items));
        setOnChildClickListener(this::onChildClick);
    }

    private boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        items[groupPosition].children[childPosition].action.action(getContext());
        if (onItemClick != null) {
            onItemClick.onClick();
        }
        return true;
    }

    public interface OnItemClick {
        void onClick();
    }

    private static class ListAdapter extends BaseExpandableListAdapter {
        private final Context context;
        private final Item[] items;

        ListAdapter(Context context, Item[] items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getGroupCount() {
            return items.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            Item parent = items[groupPosition];
            return parent.children == null ? 0 : parent.children.length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return items[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return items[groupPosition].children[childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            DrawerItemView.GroupItemView itemView;
            if (convertView == null) {
                itemView = new DrawerItemView.GroupItemView(context);
            } else {
                itemView = (DrawerItemView.GroupItemView) convertView;
            }

            Item parentItem = items[groupPosition];
            itemView.setText(parentItem.title);
            itemView.setExpanded(isExpanded);
            return itemView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            DrawerItemView.ChildItemView itemView;
            if (convertView == null) {
                itemView = new DrawerItemView.ChildItemView(context);
            } else {
                itemView = (DrawerItemView.ChildItemView) convertView;
            }

            Item item = items[groupPosition].children[childPosition];
            itemView.setText(item.title);
            itemView.setIcon(item.action.getIcon());
            return itemView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public static class Item {
        private final String title;
        private final Action action;
        private final Item[] children;

        public Item(String title, Item[] children) {
            this.title = title;
            this.action = null;
            this.children = children;
        }

        public Item(String title, Action action) {
            this.title = title;
            this.action = action;
            this.children = null;
        }
    }
}
