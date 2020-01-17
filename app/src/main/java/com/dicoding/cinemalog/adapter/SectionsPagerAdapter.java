package com.dicoding.cinemalog.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dicoding.cinemalog.R;
import com.dicoding.cinemalog.view.MovieFragment;
import com.dicoding.cinemalog.view.TvShowFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    /**
     * @param context
     * @param fm
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * set pager layout
     */
    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_movie,
            R.string.tab_tv_show
    };

    /**
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MovieFragment();
                break;
            case 1:
                fragment = new TvShowFragment();
                break;
        }
        return fragment;
    }

    /**
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }

    /**
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
