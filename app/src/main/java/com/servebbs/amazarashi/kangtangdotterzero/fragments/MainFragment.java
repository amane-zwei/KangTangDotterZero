package com.servebbs.amazarashi.kangtangdotterzero.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.servebbs.amazarashi.kangtangdotterzero.views.MainView;

import lombok.Getter;

public class MainFragment extends Fragment {

    public static final String tag = "MainFragment";

    @Getter
    MainView mainView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return this.mainView = new MainView(inflater.getContext());
    }

    public static MainFragment get(Context context) {
        return (MainFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentByTag(tag);
    }
}
