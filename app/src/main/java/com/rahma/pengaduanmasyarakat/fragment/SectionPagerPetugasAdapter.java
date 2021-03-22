package com.rahma.pengaduanmasyarakat.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPagerPetugasAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments = new ArrayList<>();
    List<String> strings = new ArrayList<>();

    public SectionPagerPetugasAdapter( FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public void addFragment (Fragment fragment, String title){
        fragments.add(fragment);
        strings.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
