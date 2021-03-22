package com.rahma.pengaduanmasyarakat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rahma.pengaduanmasyarakat.Adapter.BerandaAdapter;
import com.rahma.pengaduanmasyarakat.Adapter.ProsesAdapter;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_Beranda;
import com.rahma.pengaduanmasyarakat.model_entity.E_Proses;
import com.rahma.pengaduanmasyarakat.model_entity.M_Beranda;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    BerandaAdapter berandaAdapter;
    TextView tvGone;
    RecyclerView rvBeranda;
    List<E_Beranda> berandas;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);
        mContext = getContext();

        rvBeranda = view.findViewById(R.id.rv_beranda);
        getBeranda();

        mApiService.getSemua().enqueue(new Callback<M_Beranda>() {
            @Override
            public void onResponse(Call<M_Beranda> call, Response<M_Beranda> response) {
                berandas = response.body().getData();

                berandaAdapter = new BerandaAdapter(mContext,berandas);
                rvBeranda.setAdapter(berandaAdapter);
                berandaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<M_Beranda> call, Throwable t) {
//                Toast.makeText(getActivity(),"gagal",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void getBeranda() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        rvBeranda.setLayoutManager(layoutManager);
        rvBeranda.setItemAnimator(new DefaultItemAnimator());
    }
}
