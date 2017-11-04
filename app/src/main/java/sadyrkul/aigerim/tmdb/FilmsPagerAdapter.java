package sadyrkul.aigerim.tmdb;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by user on 15.10.2017.
 */

public class FilmsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public FilmsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        TabPopularFragment fragment = new TabPopularFragment();
        Bundle bundl = new Bundle();
        //популярные
        if (position == 0) {
            bundl.putString(TabPopularFragment.TYPE, TabPopularFragment.POPULAR);
            fragment.setArguments(bundl);
            return fragment;
        }
        //скоро на экранах
        else if (position == 1){
            bundl.putString(TabPopularFragment.TYPE, TabPopularFragment.SOON_ON_SCREENS);
            fragment.setArguments(bundl);
            return fragment;
        }
        //сейчас в кинотеатрах
        else if (position == 2){
            bundl.putString(TabPopularFragment.TYPE, TabPopularFragment.NOW_IN_CINEMAS);
            fragment.setArguments(bundl);
            return fragment;
        }

        return fragment;
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 3;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.category_popular);
            case 1:
                return mContext.getString(R.string.category_coming_soon);
            case 2:
                return mContext.getString(R.string.category_now_in_cinemas);


            default:
                return null;
        }
    }

}
