package com.rahma.pengaduanmasyarakat.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.rahma.pengaduanmasyarakat.R;


public class BerandaPetugasFragment extends Fragment {

    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab_petugas, container, false);

        mContext = getContext();
        ViewPager viewPager = view.findViewById(R.id.view_pagerP);
        SectionPagerPetugasAdapter sectionPagerPetugasAdapter = new SectionPagerPetugasAdapter(getChildFragmentManager());
        sectionPagerPetugasAdapter.addFragment(new ProsesPetugasFragment(),"Proses");
        sectionPagerPetugasAdapter.addFragment(new SelesaiPetugasFragment(),"Selesai");

        viewPager.setAdapter(sectionPagerPetugasAdapter);
        TabLayout tabs = view.findViewById(R.id.tabsP);
        tabs.setupWithViewPager(viewPager);



        return view;
    }
}