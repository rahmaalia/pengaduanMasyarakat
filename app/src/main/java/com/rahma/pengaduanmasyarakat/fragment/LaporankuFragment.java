package com.rahma.pengaduanmasyarakat.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.rahma.pengaduanmasyarakat.FormPengaduanActivity;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.RegisterActivity;

public class LaporankuFragment extends Fragment {
    View view;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab, container, false);

        mContext = getContext();
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        sectionsPagerAdapter.addFragment(new ProsesFragment(),"Proses");
        sectionsPagerAdapter.addFragment(new SelesaiFragment(),"Selesai");

        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);



        return view;
    }
}
