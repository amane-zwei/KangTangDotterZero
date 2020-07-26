package com.servebbs.amazarashi.kangtangdotterzero.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.servebbs.amazarashi.kangtangdotterzero.views.MainView;

public class MainFragment extends Fragment {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new MainView(inflater.getContext());
    }
}
