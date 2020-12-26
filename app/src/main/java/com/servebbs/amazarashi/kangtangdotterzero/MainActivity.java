package com.servebbs.amazarashi.kangtangdotterzero;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.MainFragment;
import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.util.ViewList;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class MainActivity extends AppCompatActivity {

    @Getter
    ViewList toolViews = new ViewList();

    List<OnPermissionResponse> onPermissionResponses = new ArrayList<>();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScreenSize.init(this);
        DotIcon.init(getResources());

//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

//        this.setContentView(new android.view.View(this));
        FrameLayout base = new FrameLayout(this);
        base.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        base.setId(1);

//        LinearLayout layout = new LinearLayout(this);
//        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        layout.setId(1);
        this.setContentView(base);

//        if (getSupportFragmentManager() != null && getSupportFragmentManager().getBackStackEntryCount() == 0) {
//            initFragments();
//        }
        if (savedInstanceState == null) {
            {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(1, new MainFragment(), MainFragment.tag);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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
