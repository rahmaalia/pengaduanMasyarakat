package com.rahma.pengaduanmasyarakat.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rahma.pengaduanmasyarakat.Adapter.ProsesAdapter;
import com.rahma.pengaduanmasyarakat.FormPengaduanActivity;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_Proses;
import com.rahma.pengaduanmasyarakat.model_entity.M_Proses;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProsesFragment extends Fragment {
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    ProsesAdapter prosesAdapter;
    TextView tvGone;
    RecyclerView rvProses;
    List<E_Proses> proses;
    String nik;
    Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proses, container, false);
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);

        mContext = getContext();
        sharedPrefManager = new SharedPrefManager(mContext);
        nik = sharedPrefManager.getSpNik();

        tvGone = view.findViewById(R.id.tv_gone);
        rvProses = view.findViewById(R.id.rv_proses);


        getProses();
        mApiService.getProses(nik).enqueue(new Callback<M_Proses>() {
            @Override
            public void onResponse(Call<M_Proses> call, Response<M_Proses> response) {
                proses = response.body().getData();
                if (response.body().getData().isEmpty()){
                    tvGone.setVisibility(View.VISIBLE);
                }
                prosesAdapter = new ProsesAdapter(mContext,proses);
                rvProses.setAdapter(prosesAdapter);
                Log.d( "onResponse: " , proses.toString());
                prosesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<M_Proses> call, Throwable t) {
                Toast.makeText(getActivity(),"gagal",Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FormPengaduanActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getProses() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        rvProses.setLayoutManager(layoutManager);
        rvProses.setItemAnimator(new DefaultItemAnimator());
    }
}
