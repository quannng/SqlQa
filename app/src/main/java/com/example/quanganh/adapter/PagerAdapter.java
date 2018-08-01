package com.example.quanganh.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.quanganh.fragment.AddNhanVienFragment;
import com.example.quanganh.fragment.Fragment2;
import com.example.quanganh.fragment.Fragment3;

/**
 * Created by Administrator on 23/5/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AddNhanVienFragment();
            case 1:
                return new Fragment2();
            default:
                return new Fragment3();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "AddNhanVien";
            case 1:
                return "Fragment2";
            default:
                return "Fragment3";
        }
    }
}
