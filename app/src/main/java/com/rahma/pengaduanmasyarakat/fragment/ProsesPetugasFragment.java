package com.rahma.pengaduanmasyarakat.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rahma.pengaduanmasyarakat.Adapter.ProsesPetugasAdapter;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_ProsesPetugas;
import com.rahma.pengaduanmasyarakat.model_entity.M_Beranda;
import com.rahma.pengaduanmasyarakat.model_entity.M_ProsesPetugas;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProsesPetugasFragment extends Fragment {
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    ProsesPetugasAdapter prosesPetugasAdapter;
    TextView tvGone;
    RecyclerView rvProsesPetugas;
    List<E_ProsesPetugas> prosesPetugas;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_proses_petugas_fragment, container, false);
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);

        mContext = getContext();
        sharedPrefManager = new SharedPrefManager(mContext);

        tvGone = view.findViewById(R.id.tv_goneP);
        rvProsesPetugas = view.findViewById(R.id.rv_prosesPetugas);

        getProses();
        mApiService.getProsesPetugas().enqueue(new Callback<M_ProsesPetugas>() {
            @Override
            public void onResponse(Call<M_ProsesPetugas> call, Response<M_ProsesPetugas> response) {
                prosesPetugas = response.body().getData();
                if (response.body().getData().isEmpty()){
                    tvGone.setVisibility(View.VISIBLE);
                }
                prosesPetugasAdapter = new ProsesPetugasAdapter(mContext,prosesPetugas);
                rvProsesPetugas.setAdapter(prosesPetugasAdapter);
                prosesPetugasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<M_ProsesPetugas> call, Throwable t) {
                Toast.makeText(getActivity(),"gagal",Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    private void getProses() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        rvProsesPetugas.setLayoutManager(layoutManager);
        rvProsesPetugas.setItemAnimator(new DefaultItemAnimator());
    }

}