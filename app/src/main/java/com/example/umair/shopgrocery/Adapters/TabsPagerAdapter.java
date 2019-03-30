package com.example.umair.shopgrocery.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList();
    private List<String> tabTitles = new ArrayList();

    public TabsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public int getCount() {
        return this.fragments.size();
    }

    public Fragment getItem(int position) {
        return (Fragment) this.fragments.get(position);
    }

    public CharSequence getPageTitle(int position) {
        return (CharSequence) this.tabTitles.get(position);
    }

    public void addFragment(Fragment fragment, String tabTitle) {
        this.fragments.add(fragment);
        this.tabTitles.add(tabTitle);
    }
}
