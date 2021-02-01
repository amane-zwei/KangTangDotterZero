package com.servebbs.amazarashi.kangtangdotterzero;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotFont;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.MainFragment;
import com.servebbs.amazarashi.kangtangdotterzero.util.ViewList;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class MainActivity extends AppCompatActivity {

    @Getter
    ViewList toolViews = new ViewList();

    @Getter
    DrawerLayout drawer;

    List<OnPermissionResponse> onPermissionResponses = new ArrayList<>();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenSize.init(this);
        DotIcon.init(getResources());
//        DotFont.init(getResources());
        DotFont.setDotTypeface(Typeface.createFromAsset(getAssets(), "dotfont.ttf"));

        DrawerLayout drawer = this.drawer = new DrawerLayout(this);

        final int id = View.generateViewId();
        FrameLayout base = new FrameLayout(this);
        base.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        base.setId(id);
        drawer.addView(base);

        TextView text = new TextView(this);
        text.setText("Hello Hogehoge!");
        DrawerLayout.LayoutParams layoutParams = new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.LEFT;
        text.setLayoutParams(layoutParams);
        drawer.addView(text);

//        LinearLayout layout = new LinearLayout(this);
//        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        layout.setId(1);
        this.setContentView(drawer);

//        if (getSupportFragmentManager() != null && getSupportFragmentManager().getBackStackEntryCount() == 0) {
//            initFragments();
//        }
        if (savedInstanceState == null) {
            {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(id, new MainFragment(), MainFragment.tag);
                transaction.commit();
            }
//            {
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//
//                transaction.add(3, new KTDZDialogFragment.DammyFragment());
//                transaction.commit();
//            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }


    public void addPermissionRequest(OnPermissionResponse onPermissionResponse) {
        onPermissionResponses.add(onPermissionResponse);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int index = onPermissionResponses.size() - 1; index >= 0; index--) {
            OnPermissionResponse onPermissionResponse = onPermissionResponses.get(index);
            if (onPermissionResponse.apply(requestCode, permissions, grantResults)) {
                onPermissionResponses.remove(index);
            }
        }
    }

    public interface OnPermissionResponse {
        boolean apply(int requestCode, String[] permissions, int[] grantResults);
    }
}
