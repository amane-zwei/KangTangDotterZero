package com.servebbs.amazarashi.kangtangdotterzero.util;

import android.annotation.SuppressLint;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.MainFragment;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.TestFragment;

public class Navigation {
    public static final String MAIN_FRAGMENT_TAG = "main_fragment_tag";

    public static void gotoMainFragment(FragmentManager fragmentManager) {
        boolean popped = fragmentManager.popBackStackImmediate(MAIN_FRAGMENT_TAG, 0);
        if (!popped) openMainFragment(fragmentManager);
    }

    public static void openMainFragment(FragmentManager fragmentManager) {

        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        defaultTransaction(fragmentManager)
                .replace(0, new MainFragment())
                .addToBackStack(MAIN_FRAGMENT_TAG)
                .commit();
    }

    @SuppressLint("ResourceType")
    public static void openTestFragment(FragmentManager fragmentManager) {
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        defaultTransaction(fragmentManager)
//                .add(new TestFragment(), "test_tag")
//                .add(1, new Test\Fragment())
                .replace(3, new TestFragment())
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }


    @SuppressLint("CommitTransaction")
    private static FragmentTransaction defaultTransaction(FragmentManager fragmentManager) {
        return fragmentManager.beginTransaction();
//                .setCustomAnimations(R.animator.custom_fade_in, R.animator.custom_fade_out, R.animator.custom_fade_in, R.animator.custom_fade_out);
    }


}
