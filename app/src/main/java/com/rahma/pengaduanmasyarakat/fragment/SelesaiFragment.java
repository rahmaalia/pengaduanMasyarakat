package com.rahma.pengaduanmasyarakat.fragment;

import android.content.Context;
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

import com.rahma.pengaduanmasyarakat.Adapter.ProsesAdapter;
import com.rahma.pengaduanmasyarakat.Adapter.SelesaiAdapter;
import com.rahma.pengaduanmasyarakat.R;
import com.rahma.pengaduanmasyarakat.apihelper.BaseApiService;
import com.rahma.pengaduanmasyarakat.apihelper.RetrofitClient;
import com.rahma.pengaduanmasyarakat.model_entity.E_Proses;
import com.rahma.pengaduanmasyarakat.model_entity.E_Selesai;
import com.rahma.pengaduanmasyarakat.model_entity.M_Selesai;
import com.rahma.pengaduanmasyarakat.sharedpref.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelesaiFragment extends Fragment {
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    SelesaiAdapter selesaiAdapter;
    TextView tvGone;
    RecyclerView rvSelesai;
    List<E_Selesai> selesai;
    String nik;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selesai, container, false);
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(BaseApiService.class);

        mContext = getContext();
        sharedPrefManager = new SharedPrefManager(mContext);
        nik = sharedPrefManager.getSpNik();

        tvGone = view.findViewById(R.id.tv_gones);
        rvSelesai = view.findViewById(R.id.rv_selesai);

        getSelesai();
        mApiService.getSelesai(nik).enqueue(new Callback<M_Selesai>() {
            @Override
            public void onResponse(Call<M_Selesai> call, Response<M_Selesai> response) {
                selesai = response.body().getData();
                if (response.body().getData().isEmpty()){
                    tvGone.setVisibility(View.VISIBLE);
                }
                selesaiAdapter = new SelesaiAdapter(mContext,selesai);
                rvSelesai.setAdapter(selesaiAdapter);
                Log.d( "onResponse: " , selesai.toString());
                selesaiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<M_Selesai> call, Throwable t) {
                Toast.makeText(getActivity(),"gagal",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void getSelesai() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        rvSelesai.setLayoutManager(layoutManager);
        rvSelesai.setItemAnimator(new DefaultItemAnimator());
    }

}
