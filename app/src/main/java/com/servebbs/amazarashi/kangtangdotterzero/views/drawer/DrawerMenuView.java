package com.servebbs.amazarashi.kangtangdotterzero.views.drawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.Action;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.CallLoadProjectDialogAction;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.CallSaveProjectDialogAction;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

import lombok.AllArgsConstructor;

public class DrawerMenuView extends ConstraintLayout {
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

    public DrawerMenuView(Context context) {
        super(context);

        DrawerItemView prev;
        Queue<Tuple> queue = new ArrayDeque<>();
        Stack<View> views = new Stack<>();

        {
            int id = View.generateViewId();

            Item item = items[0];
            DrawerItemView itemView = new DrawerItemView(context);
            itemView.setId(id);
            itemView.setText(item.title);
            itemView.setLevel(0);

            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            itemView.setLayoutParams(layoutParams);
            views.add(itemView);

            queue.add(new Tuple(1, itemView, item.children));
            prev = itemView;
        }

        for (int index = 1; index < items.length; index++) {
            int id = View.generateViewId();

            Item item = items[index];
            DrawerItemView itemView = new DrawerItemView(context);
            itemView.setId(id);
            itemView.setText(item.title);
            itemView.setLevel(0);

            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToBottom = prev.getId();
            itemView.setLayoutParams(layoutParams);
            views.add(itemView);

            queue.add(new Tuple(1, itemView, item.children));
            prev = itemView;
        }

        while (queue.size() > 0) {
            Tuple tuple = queue.remove();
            Item[] children = tuple.children;
            DrawerItemView parent = tuple.parent;

            for (int index = 0; index < children.length; index++) {
                int id = View.generateViewId();

                Item item = children[index];
                DrawerItemView itemView = new DrawerItemView(context);
                itemView.setId(id);
                itemView.setText(item.title);
                itemView.setLevel(tuple.level);

                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutParams.topToBottom = parent.getId();
                itemView.setLayoutParams(layoutParams);
                views.add(itemView);

                if (item.children != null) {
                    queue.add(new Tuple(tuple.level, itemView, item.children));
                }
                parent = itemView;
            }

            while (views.size() > 0) {
                addView(views.pop());
            }
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

    @AllArgsConstructor
    public static class Tuple {
        private final int level;
        private final DrawerItemView parent;
        private final Item[] children;
    }
}
