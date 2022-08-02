package com.my.tiktok.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.my.tiktok.fragments.ProfileLike;
import com.my.tiktok.fragments.ProfileVideo;

public class PagerViewAdapter extends FragmentPagerAdapter {
    public PagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new ProfileVideo();

            case 1: return new ProfileLike();
            default: return new ProfileVideo();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title=null;
        if (position==0)
        {
            title="POST";
        }
        else if (position==1)
        {
            title="LIKES" ;
        }
        return title;

    }
}
