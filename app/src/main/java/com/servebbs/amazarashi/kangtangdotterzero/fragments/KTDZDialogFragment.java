package com.servebbs.amazarashi.kangtangdotterzero.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotRectView;

public class KTDZDialogFragment extends Fragment {

    public static void show(Context context, @IdRes int containerViewId, KTDZDialogFragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(containerViewId, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        {
            View backView = createBackView(inflater.getContext());
            backView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            backView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            frameLayout.addView(backView);
        }
        {
            View mainView = createLayoutView(inflater.getContext());
            mainView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            frameLayout.addView(mainView);
        }

        return frameLayout;
    }

    public void dismiss() {
        assert getFragmentManager() != null;
        getFragmentManager().popBackStack();
    }

    public View createBackView(Context context) {
        return new View(context) {
            @Override
            public void onDraw(Canvas canvas) {
                canvas.drawColor(0x80000000);
            }
        };
    }

    public View createMainView(Context context) {
        return new View(context);
    }

    public LinearLayout createButtonListView(Context context) {
        LinearLayout buttonsView = createBase(context, LinearLayout.HORIZONTAL);
        {
            View button = createButtonView(context);
            buttonsView.addView(setLayout(button, 8, 0, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        return buttonsView;
    }

    public ViewGroup createLayoutView(Context context) {
        LinearLayout vertical = createBase(context, LinearLayout.VERTICAL);

        vertical.addView(setLayout(new View(context), 2, 0, 0));
        {
            LinearLayout horizontal = createBase(context, LinearLayout.HORIZONTAL);
            horizontal.addView(setLayout(new View(context), 1, 0, 0));
            horizontal.addView(setLayout(createMainView(context), 30, 0, ViewGroup.LayoutParams.MATCH_PARENT));
            horizontal.addView(setLayout(new View(context), 1, 0, 0));

            vertical.addView(setLayout(horizontal, 26, ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }
        vertical.addView(setLayout(createButtonListView(context), 4, ViewGroup.LayoutParams.MATCH_PARENT, 0));

        return vertical;
    }

    public RelativeLayout createButtonView(Context context) {
        RelativeLayout layout = new RelativeLayout(context);

        {
            RelativeLayout button = new RelativeLayout(context) {
                @Override
                public void dispatchDraw(Canvas canvas) {
                    DotRectView.drawFrame(canvas, 0xff000000, 0xffffffff);
                    super.dispatchDraw(canvas);
                }
            };
            {
                TextView text = new TextView(context);
                text.setText("hogehoge");
                text.setTextSize(50f);
                text.setPadding(8,8,8,8);
                RelativeLayout.LayoutParams textparams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                textparams.addRule(RelativeLayout.CENTER_IN_PARENT);
                button.addView(text, textparams);
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            layout.addView(button, params);
        }

        return layout;
    }

    public static LinearLayout createBase(Context context, int orientation) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(orientation);
        return layout;
    }

    public static View setLayout(View view, int weight, int width, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.weight = weight;
        view.setLayoutParams(params);
        return view;
    }
}
