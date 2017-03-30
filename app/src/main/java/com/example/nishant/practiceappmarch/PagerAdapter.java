package com.example.nishant.practiceappmarch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Nishant on 15-Mar-17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;
    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs=numOfTabs;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Tab_Fragment1 tab1=new Tab_Fragment1();
                return tab1;
            case 1:
                Tab_Fragment2 tab2=new Tab_Fragment2();
                return tab2;

            default:return null;
        }

    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return numOfTabs;
    }
}
