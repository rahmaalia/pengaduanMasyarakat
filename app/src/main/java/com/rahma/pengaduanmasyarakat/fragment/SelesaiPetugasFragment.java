package com.rahma.pengaduanmasyarakat.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rahma.pengaduanmasyarakat.Adapter.ProsesPetugasAdapter;
import com.rahma.pengaduanmasyarakat.Adapter.SelesaiPetugasAdapter;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_ProsesPetugas;
import com.rahma.pengaduanmasyarakat.model_entity.E_SelesaiPetugas;
import com.rahma.pengaduanmasyarakat.model_entity.M_ProsesPetugas;
import com.rahma.pengaduanmasyarakat.model_entity.M_SelesaiPetugas;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelesaiPetugasFragment extends Fragment {
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    SelesaiPetugasAdapter selesaiPetugasAdapter;
    TextView tvGone;
    RecyclerView rvSelesaiPetugas;
    List<E_SelesaiPetugas> selesaiPetugas;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_selesai_petugas_fragment, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);

        mContext = getContext();
        sharedPrefManager = new SharedPrefManager(mContext);

        tvGone = view.findViewById(R.id.tv_gonePS);
        rvSelesaiPetugas = view.findViewById(R.id.rv_selsaiPetugas);

        getSelesai();
        mApiService.getSelesaiPetugas().enqueue(new Callback<M_SelesaiPetugas>() {
            @Override
            public void onResponse(Call<M_SelesaiPetugas> call, Response<M_SelesaiPetugas> response) {
                selesaiPetugas = response.body().getData();
                if (response.body().getData().isEmpty()){
                    tvGone.setVisibility(View.VISIBLE);
                }
                selesaiPetugasAdapter = new SelesaiPetugasAdapter(mContext,selesaiPetugas);
                rvSelesaiPetugas.setAdapter(selesaiPetugasAdapter);
                selesaiPetugasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<M_SelesaiPetugas> call, Throwable t) {
                Toast.makeText(getActivity(),"gagal",Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    private void getSelesai() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        rvSelesaiPetugas.setLayoutManager(layoutManager);
        rvSelesaiPetugas.setItemAnimator(new DefaultItemAnimator());
    }
}